package de.logicline.adv.model.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ADV")
public class AdvDao {

    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;


    private Long customerId;


    private String physicalAccessControl;
    private String logicalAccessControl;
    private String dataAccessControl;
    private String dataTransferControl;
    private String dataEntryControl;
    private String controlOfProcessing;
    private String availablityControl;
    private String separationControl;

    private  boolean isPermanent;
    private boolean isPhysicalAccess;
    private boolean isLogicalAccess;
    private boolean isDataAccess;
    private boolean isDataTransfer;
    private boolean isDataEntry;
    private boolean isControlOfProcessing;
    private boolean isAvailability;
    private boolean isSeperation;

    private Date validFrom;
    private Date validTo;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "advId")
    private List<DataCategoryDao> dataCategoryDao;

    @JsonIgnore
    private byte[] advInPdfFormat;


//TODO fields & types need to be revisited once we have a front end




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

    public String getControlOfProcessing() {
        return controlOfProcessing;
    }

    public void setControlOfProcessing(String controlOfProcessing) {
        this.controlOfProcessing = controlOfProcessing;
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

    public boolean isPhysicalAccess() {
        return isPhysicalAccess;
    }

    public void setPhysicalAccess(boolean physicalAccess) {
        isPhysicalAccess = physicalAccess;
    }

    public boolean isLogicalAccess() {
        return isLogicalAccess;
    }

    public void setLogicalAccess(boolean logicalAccess) {
        isLogicalAccess = logicalAccess;
    }

    public boolean isDataAccess() {
        return isDataAccess;
    }

    public void setDataAccess(boolean dataAccess) {
        isDataAccess = dataAccess;
    }

    public boolean isDataTransfer() {
        return isDataTransfer;
    }

    public void setDataTransfer(boolean dataTransfer) {
        isDataTransfer = dataTransfer;
    }

    public boolean isDataEntry() {
        return isDataEntry;
    }

    public void setDataEntry(boolean dataEntry) {
        isDataEntry = dataEntry;
    }

    public boolean isControlOfProcessing() {
        return isControlOfProcessing;
    }

    public void setControlOfProcessing(boolean controlOfProcessing) {
        isControlOfProcessing = controlOfProcessing;
    }

    public boolean isAvailability() {
        return isAvailability;
    }

    public void setAvailability(boolean availability) {
        isAvailability = availability;
    }

    public boolean isSeperation() {
        return isSeperation;
    }

    public void setSeperation(boolean seperation) {
        isSeperation = seperation;
    }

    public List<DataCategoryDao> getDataCategoryDao() {
        return dataCategoryDao;
    }

    public void setDataCategoryDao(List<DataCategoryDao> dataCategoryDao) {
        this.dataCategoryDao = dataCategoryDao;
    }
}
