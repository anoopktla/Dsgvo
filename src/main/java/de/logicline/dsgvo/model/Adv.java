package de.logicline.dsgvo.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "ADV")
public class Adv {

    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;


    private Long customerId;
    private String purposeOfCollection;
    private String categoryOfDataSubjects;
    private Date validFrom;
    private Date validTo;



  /*  private Set<String> tom;*/
    /*private byte[] advDocument;*/


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getPurposeOfCollection() {
        return purposeOfCollection;
    }

    public void setPurposeOfCollection(String purposeOfCollection) {
        this.purposeOfCollection = purposeOfCollection;
    }

    public String getCategoryOfDataSubjects() {
        return categoryOfDataSubjects;
    }

    public void setCategoryOfDataSubjects(String categoryOfDataSubjects) {
        this.categoryOfDataSubjects = categoryOfDataSubjects;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidTo() {
        return validTo;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

   /* public Set<String> getTom() {
        return tom;
    }

    public void setTom(Set<String> tom) {
        this.tom = tom;
    }*/

    /*public byte[] getAdvDocument() {
        return advDocument;
    }

    public void setAdvDocument(byte[] advDocument) {
        this.advDocument = advDocument;
    }*/
}
