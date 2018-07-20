package de.logicline.adv.model.frontend;

import java.util.Date;
import java.util.List;

public class ContractInfo {
    private List<CategoryDetails> dataCategories;
    private Date validFrom;
    private Date validTo;
    private boolean isPermanent;
    private boolean isPhysicalAccess;
    private boolean isLogicalAccess;
    private boolean isDataAccess;
    private boolean isDataTransfer;
    private boolean isDataEntry;
    private boolean isControlOfProcessing;
    private boolean isAvailability;
    private boolean isSeparation;
    private String physicalAccessControl;
    private String logicalAccessControl;
    private String dataAccessControl;
    private String dataTransferControl;
    private String dataEntryControl;
    private String controlOfProcessing;
    private String availabilityControl;
    private String separationControl;

    public List<CategoryDetails> getDataCategories() {
        return dataCategories;
    }

    public void setDataCategories(List<CategoryDetails> dataCategories) {
        this.dataCategories = dataCategories;
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

    public boolean isSeparation() {
        return isSeparation;
    }

    public void setSeparation(boolean separation) {
        isSeparation = separation;
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

    public String getAvailabilityControl() {
        return availabilityControl;
    }

    public void setAvailabilityControl(String availabilityControl) {
        this.availabilityControl = availabilityControl;
    }

    public String getSeparationControl() {
        return separationControl;
    }

    public void setSeparationControl(String separationControl) {
        this.separationControl = separationControl;
    }
}
