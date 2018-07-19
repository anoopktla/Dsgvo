package de.logicline.adv.model.dao;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "CUSTOMER")
public class CustomerDao {
    //TODO fields & types need to be revisited once we have a front end
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String salutation;
    private String firstName;
    private String lastName;
    private String position;
    private String emailAddress;
    private int phoneNumber;
    private String companyName;
    private String street;
    private int buildingNumber;
    private String addressLine2;
    private String zipCode;
    private String city;
    private String country;


    private String cc;
    private String bcc;
    private String toEmail;

    @ElementCollection
    @CollectionTable(name = "customer_email_address", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "cc_email")
    private Set<String> ccEmail = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "customer_email_address", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "bcc_email")
    private Set<String> bccEmail = new HashSet<>();


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "customerId")
    private List<AdvDao> advDao;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(int buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
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

    public List<AdvDao> getAdvDao() {
        return advDao;
    }

    public void setAdvDao(List<AdvDao> advDao) {
        this.advDao = advDao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSalutation() {
        return salutation;
    }

    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getBcc() {
        return bcc;
    }

    public void setBcc(String bcc) {
        this.bcc = bcc;
    }

    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    public Set<String> getCcEmail() {
        return ccEmail;
    }

    public void setCcEmail(Set<String> ccEmail) {
        this.ccEmail = ccEmail;
    }

    public Set<String> getBccEmail() {
        return bccEmail;
    }

    public void setBccEmail(Set<String> bccEmail) {
        this.bccEmail = bccEmail;
    }
}
