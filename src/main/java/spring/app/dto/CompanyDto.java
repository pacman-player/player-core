package spring.app.dto;

import java.time.LocalTime;

public class CompanyDto {

    private Long id;
    private String name;
    private String startTime;
    private String closeTime;
    private Long orgType;
    private String orgTypeName;
    private Long tariff;
    private Long userId;
    private String userLogin;

    public CompanyDto(Long id, String name, LocalTime startTime, LocalTime closeTime, Long orgType, String orgTypeName,
                      Long tariff, Long userId, String userLogin) {
        this.id = id;
        this.name = name;
        this.startTime = startTime.toString();
        this.closeTime = closeTime.toString();
        this.orgType = orgType;
        this.orgTypeName = orgTypeName;
        this.tariff = tariff;
        this.userId = userId;
        this.userLogin = userLogin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public Long getOrgType() {
        return orgType;
    }

    public void setOrgType(Long orgType) {
        this.orgType = orgType;
    }

    public Long getTariff() {
        return tariff;
    }

    public void setTariff(Long tariff) {
        this.tariff = tariff;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "CompanyDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startTime='" + startTime + '\'' +
                ", closeTime='" + closeTime + '\'' +
                ", orgType=" + orgType +
                ", tariff=" + tariff +
                ", userId=" + userId +
                '}';
    }

    public String getOrgTypeName() {
        return orgTypeName;
    }

    public void setOrgTypeName(String orgTypeName) {
        this.orgTypeName = orgTypeName;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }
}

