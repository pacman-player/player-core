package spring.app.actuator.endpoints.authoeDaoEndPoint;

import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.boot.actuate.endpoint.mvc.EndpointMvcAdapter;
import org.springframework.stereotype.Component;

@Component
public class CustomHibHealthMvcEndpointAuthorDao extends EndpointMvcAdapter {
    public CustomHibHealthMvcEndpointAuthorDao(HibHealthEndPointAuthorDao hibHealthEndPointAuthorDao) {
        super(hibHealthEndPointAuthorDao);
    }
}
