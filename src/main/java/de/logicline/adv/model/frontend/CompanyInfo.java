package de.logicline.adv.model.frontend;


import javax.validation.constraints.NotNull;

public class CompanyInfo {

    @NotNull
    private String companyName;

    @NotNull
    private String street;

    @NotNull
    private Long no;

    @NotNull
    private String address;

    @NotNull
    private String zip;

    @NotNull
    private String city;

    @NotNull
    private String country;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Long getNo() {
        return no;
    }

    public void setNo(Long no) {
        this.no = no;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
