package spring.app.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import spring.app.service.abstraction.TrashApiService;
import spring.app.util.GenericHelper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Этот Generic-класс осуществляет соединение с нашим приложением Корзина (player-trash),
 * которому посрдством HttpClient передает нашу сущность для сохранения в базе данных.
 *
 * @param <E> сущность, которой будет параметризован данный класс.
 */

@Service
@PropertySource("classpath:trash.properties")
public class TrashApiServiceImpl<E> implements TrashApiService<E> {
    private Logger LOGGER = LoggerFactory.getLogger(TrashApiServiceImpl.class);

    @Value("${trash.server}")
    private String URL;
    private GenericHelper<E> genericHelper;

    // Появившийся в Java 11 абстрактный класс для передачи/получения данных по протоколу HTTP.
    // Является immutable.
    private HttpClient client = HttpClient.newBuilder().build();

    public TrashApiServiceImpl(GenericHelper<E> genericHelper) {
        this.genericHelper = genericHelper;
    }

    @Override
    public boolean moveToTrash(E entity) throws IOException, InterruptedException {
        // Инстанцируем объект для создания json
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        // Создадим json из нашей сущности
        String json = ow.writeValueAsString(entity);
        // Так как в нашем приложении Корзина (player-trash) настроен Spring Security с базовой
        // аутентификацией, то мы должны сгенерировать строку, состоящую из логина и пароля и
        // закодированную по алгоритму Base64.
        String encoded = new String(Base64.getEncoder()
                .encode("core:core".getBytes(StandardCharsets.ISO_8859_1)), StandardCharsets.ISO_8859_1);
        // Так как рест-контроллеры корзины принимают запросы по адресам типа "/api/song" или
        // "/api/author", то нам нужно к базовому URL, равному http://localhost:8082/api/,
        // добавлять имя класса в нижнем регистре, чтобы получить http://localhost:8082/api/song.
        String resultUrl = URL + genericHelper.getEntityClassNameInLowerCase(entity);
        // Формируем HttpRequest, который будет отправлен нашим HttpClient
        HttpRequest request = HttpRequest.newBuilder()
                // Устанавливаем ресурс - URL, к которому будет обращение.
                .uri(URI.create(resultUrl))
                // Устанавливаем Http-метод и передаем строку json, описывающую
                // сохраняемый нами объект.
                .POST(HttpRequest.BodyPublishers.ofString(json))
                // При базовой аутентификации логин и пароль передаются в заголовке с
                // названием "Authorization" и значением, равным строке:
                // "Basic " + зашифрованные логин и пароль, например:
                // Authorization: Basic Y29yZTpjb3Jl
                .setHeader("Authorization", "Basic " + encoded)
                // Для рест-контроллера Spring в заголовке нужно явно объявить, что
                // мы передаем ему json
                .setHeader("Content-Type", "application/json")
                .build();
        // Отправляем запрос и записываем ответ response, из которого можем достать статус ответа,
        // тело ответа, если оно есть, заголовки и т.д.
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            return true;
        } else {
            LOGGER.info("Connection problem: " + response.body());
            throw new IOException("Connection problem: " + response.body());
        }
    }
}
