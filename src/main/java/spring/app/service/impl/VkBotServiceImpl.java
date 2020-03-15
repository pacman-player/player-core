package spring.app.service.impl;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import spring.app.service.abstraction.VkBotService;

import java.io.IOException;

@Service
@PropertySource("classpath:application.properties")
public class VkBotServiceImpl implements VkBotService {
    private VkApiClient vkApiClient;
    private GroupActor actor;

    @Value("${group_id}")
    private String groupIdString;

    @Value("${access_token}")
    private String accessToken;

    public VkBotServiceImpl() throws IOException {
        TransportClient transportClient = HttpTransportClient.getInstance();
        vkApiClient = new VkApiClient(transportClient);

//        Properties prop = new Properties();
//        prop.load(new FileInputStream("src/main/resources/application.properties"));
//        int groupId = Integer.valueOf(prop.getProperty("group_id"));
//        String access_token = prop.getProperty("access_token");
        System.out.println(groupIdString);
        Integer groupId = Integer.valueOf(groupIdString);
        actor = new GroupActor(groupId, accessToken);
    }

    /*
     * id для каждой беседы вводит адсинистратор группы
     * vk.com/gim190607919?sel=c15 - здесь id = 15
     */

    @Override
    public void sendMessage(Integer id, String message) throws ClientException, ApiException {
        vkApiClient.messages().send(actor).chatId(id).message(message).execute();
    }
}
