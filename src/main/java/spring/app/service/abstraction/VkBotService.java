package spring.app.service.abstraction;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;

public interface VkBotService {
    void sendMessage(Integer id, String message) throws ClientException, ApiException;
}
