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
    private Long requestSpamCounter;
    private Long userId;
    private String userLogin;
    private Long addressId;
    private String addressCountry;
    private String addressCity;
    private String addressStreet;
    private String addressHouse;
    private double addressLatitude;
    private double addressLongitude;


    public CompanyDto() {
    }

    public CompanyDto(Long id, String name, LocalTime startTime, LocalTime closeTime, Long orgType, String orgTypeName,
                      Long tariff, Long requestSpamCounter, Long userId, String userLogin, String addressCountry, String addressCity, String addressStreet, String addressHouse) {
        this.id = id;
        this.name = name;
        this.startTime = startTime.toString();
        this.closeTime = closeTime.toString();
        this.orgType = orgType;
        this.orgTypeName = orgTypeName;
        this.tariff = tariff;
        this.requestSpamCounter = requestSpamCounter;
        this.userId = userId;
        this.userLogin = userLogin;
        this.addressCountry = addressCountry;
        this.addressCity = addressCity;
        this.addressStreet = addressStreet;
        this.addressHouse = addressHouse;
    }

    //конструктор без пользователей
    public CompanyDto(Long id, String name, LocalTime startTime, LocalTime closeTime, Long orgType, String orgTypeName,
                      Long tariff, Long requestSpamCounter, String addressCountry, String addressCity, String addressStreet, String addressHouse) {
        this.id = id;
        this.name = name;
        this.startTime = startTime.toString();
        this.closeTime = closeTime.toString();
        this.orgType = orgType;
        this.orgTypeName = orgTypeName;
        this.tariff = tariff;
        this.requestSpamCounter = requestSpamCounter;
        this.addressCountry = addressCountry;
        this.addressCity = addressCity;
        this.addressStreet = addressStreet;
        this.addressHouse = addressHouse;
    }

    //конструктор без адресов
    public CompanyDto(Long id, String name, LocalTime startTime, LocalTime closeTime, Long orgType, String orgTypeName,
                      Long tariff, Long requestSpamCounter, Long userId, String userLogin) {
        this.id = id;
        this.name = name;
        this.startTime = startTime.toString();
        this.closeTime = closeTime.toString();
        this.orgType = orgType;
        this.orgTypeName = orgTypeName;
        this.tariff = tariff;
        this.requestSpamCounter = requestSpamCounter;
        this.userId = userId;
        this.userLogin = userLogin;
    }

    //конструктор без пользователей и адресов
    public CompanyDto(Long id, String name, LocalTime startTime, LocalTime closeTime, Long orgType, String orgTypeName,
                      Long tariff, Long requestSpamCounter) {
        this.id = id;
        this.name = name;
        this.startTime = startTime.toString();
        this.closeTime = closeTime.toString();
        this.orgType = orgType;
        this.orgTypeName = orgTypeName;
        this.tariff = tariff;
        this.requestSpamCounter = requestSpamCounter;
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

    public Long getRequestSpamCounter() {
        return requestSpamCounter;
    }

    public void setRequestSpamCounter(Long requestSpamCounter) {
        this.requestSpamCounter = requestSpamCounter;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getAddressCountry() {
        return addressCountry;
    }

    public void setAddressCountry(String addressCountry) {
        this.addressCountry = addressCountry;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public String getAddressStreet() {
        return addressStreet;
    }

    public void setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
    }

    public String getAddressHouse() {
        return addressHouse;
    }

    public void setAddressHouse(String addressHouse) {
        this.addressHouse = addressHouse;
    }

    @Override
    public String toString() {
        return "CompanyDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startTime='" + startTime + '\'' +
                ", closeTime='" + closeTime + '\'' +
                ", orgType=" + orgType +
                ", orgTypeName='" + orgTypeName + '\'' +
                ", tariff=" + tariff +
                ", requestSpamCounter=" + requestSpamCounter +
                ", userId=" + userId +
                ", userLogin='" + userLogin + '\'' +
                ", addressId=" + addressId +
                ", addressCountry='" + addressCountry + '\'' +
                ", addressCity='" + addressCity + '\'' +
                ", addressStreet='" + addressStreet + '\'' +
                ", addressHouse='" + addressHouse + '\'' +
                '}';
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public double getAddressLatitude() {
        return addressLatitude;
    }

    public void setAddressLatitude(double addressLatitude) {
        this.addressLatitude = addressLatitude;
    }

    public double getAddressLongitude() {
        return addressLongitude;
    }

    public void setAddressLongitude(double addressLongitude) {
        this.addressLongitude = addressLongitude;
    }

//    @Override
//    public String toString() {
//        return "CompanyDto{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", startTime='" + startTime + '\'' +
//                ", closeTime='" + closeTime + '\'' +
//                ", orgType=" + orgType +
//                ", tariff=" + tariff +
//                ", userId=" + userId +
//                '}';
//    }
}

