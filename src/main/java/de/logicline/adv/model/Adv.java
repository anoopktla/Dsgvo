package de.logicline.adv.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

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

    private String physicalAccessControl;
    private String logicalAccessControl;
    private String dataAccessControl;
    private String dataTransferControl;
    private String dataEntryControl;
    private String controlOfProcessingInstructions;
    private String availablityControl;
    private String separationControl;

    private  boolean isPermanent;
    private Date validFrom;
    private Date validTo;

    @JsonIgnore
    private byte[] advInPdfFormat;


//TODO fields & types need to be revisited once we have a front end
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

    public String getPhysicalAccessControl() {
        return physicalAccessControl;
    }

    public void setPhysicalAccessControl(String physicalAccessControl) {
        this.physicalAccessControl = physicalAccessControl;
    }

    public String getLogicalAccessControl() {
        return logicalAccessControl;
    }

    public void setLogicalAccessControl(String logicalAccessControl) {
        this.logicalAccessControl = logicalAccessControl;
    }

    public String getDataAccessControl() {
        return dataAccessControl;
    }

    public void setDataAccessControl(String dataAccessControl) {
        this.dataAccessControl = dataAccessControl;
    }

    public String getDataTransferControl() {
        return dataTransferControl;
    }

    public void setDataTransferControl(String dataTransferControl) {
        this.dataTransferControl = dataTransferControl;
    }

    public String getDataEntryControl() {
        return dataEntryControl;
    }

    public void setDataEntryControl(String dataEntryControl) {
        this.dataEntryControl = dataEntryControl;
    }

    public String getControlOfProcessingInstructions() {
        return controlOfProcessingInstructions;
    }

    public void setControlOfProcessingInstructions(String controlOfProcessingInstructions) {
        this.controlOfProcessingInstructions = controlOfProcessingInstructions;
    }

    public String getAvailablityControl() {
        return availablityControl;
    }

    public void setAvailablityControl(String availablityControl) {
        this.availablityControl = availablityControl;
    }

    public String getSeparationControl() {
        return separationControl;
    }

    public void setSeparationControl(String separationControl) {
        this.separationControl = separationControl;
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

    public byte[] getAdvInPdfFormat() {
        return advInPdfFormat;
    }

    public void setAdvInPdfFormat(byte[] advInPdfFormat) {
        this.advInPdfFormat = advInPdfFormat;
    }

    public boolean isPermanent() {
        return isPermanent;
    }

    public void setPermanent(boolean permanent) {
        isPermanent = permanent;
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
