package spring.app.controller.restController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.app.model.OrgType;
import spring.app.service.abstraction.OrgTypeService;

import java.util.List;

@RestController
@RequestMapping("api/user/orgType")
public class UserOrgTypeRestController {

    private final OrgTypeService orgTypeService;

    public UserOrgTypeRestController(OrgTypeService orgTypeService) {
        this.orgTypeService = orgTypeService;
    }

    @GetMapping(value = "/get_all_orgType")
    public List<OrgType> getAllOrgType() {
        return orgTypeService.getAllOrgType();
    }
}
