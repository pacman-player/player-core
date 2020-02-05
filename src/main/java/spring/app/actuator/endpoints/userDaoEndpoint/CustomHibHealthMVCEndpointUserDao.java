package spring.app.actuator.endpoints.userDaoEndpoint;

import org.springframework.boot.actuate.endpoint.mvc.EndpointMvcAdapter;
import org.springframework.stereotype.Component;

@Component
public class CustomHibHealthMVCEndpointUserDao extends EndpointMvcAdapter {
    public CustomHibHealthMVCEndpointUserDao(HibHealthEndPointUserDao hibHealthEndPoint) {
        super(hibHealthEndPoint);
    }
}
