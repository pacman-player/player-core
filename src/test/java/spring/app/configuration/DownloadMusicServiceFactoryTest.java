package spring.app.configuration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class DownloadMusicServiceFactoryTest {

    @Autowired
    private DownloadMusicServiceFactory factory;

    @Test
    void getDownloadServices() {
        System.out.println(" сервисы  из application.properties:");
        System.out.println(factory.getDownloadServices());
        // 1 - пишем чушь, должен вернуть default сет
        factory.setOne("foubfsjbsdlv");
        System.out.println("Сервисы после изменения:");
        System.out.println(factory.getDownloadServices());
        // 2 - сетим null, должен вернуть default сет
        factory.setOne(null);
        System.out.println("Сервисы после изменения:");
        System.out.println(factory.getDownloadServices());
        // 3 - сетим дубль, должно быть сет из трех сервисов
        factory.setOne("zaycevSaitServiceImpl");
        System.out.println("Сервисы после изменения:");
        System.out.println(factory.getDownloadServices());
    }
}