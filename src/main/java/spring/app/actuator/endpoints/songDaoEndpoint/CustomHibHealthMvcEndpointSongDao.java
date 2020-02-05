package spring.app.actuator.endpoints.songDaoEndpoint;

import org.springframework.boot.actuate.endpoint.mvc.EndpointMvcAdapter;
import org.springframework.stereotype.Component;

@Component
public class CustomHibHealthMvcEndpointSongDao extends EndpointMvcAdapter {
    public CustomHibHealthMvcEndpointSongDao(HibHealthEndpointSongDao healthEndpointSongDao) {
        super(healthEndpointSongDao);
    }
}
