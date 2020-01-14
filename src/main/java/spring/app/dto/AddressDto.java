package spring.app.dto;

public class AddressDto {
    private Long id;
    private String country;
    private String city;
    private String street;
    private String house;
    private String latitude;
    private String longitude;

    public AddressDto() {
    }

    public AddressDto(Long id, String country, String city, String street, String house, String latitude, String longitude) {
        this.id = id;
        this.country = country;
        this.city = city;
        this.street = street;
        this.house = house;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public AddressDto(String country, String city, String street, String house, String latitude, String longitude) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.house = house;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
