package spring.app.actuator.endpoints;

import org.springframework.boot.actuate.endpoint.mvc.EndpointMvcAdapter;
import org.springframework.stereotype.Component;

@Component
public class CustomHibHealthMVCEndpoint extends EndpointMvcAdapter {
    public CustomHibHealthMVCEndpoint(HibHealthEndPoint hibHealthEndPoint) {
        super(hibHealthEndPoint);
    }
}
