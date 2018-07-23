package de.logicline.adv.model.frontend;

public class Customer {

    private CompanyInfo companyInfo;
    private PersonDetails personDetails;
    private EmailDetails emailDetails;
    private ContractInfo contractInfo;

    public CompanyInfo getCompanyInfo() {
        return companyInfo;
    }

    public void setCompanyInfo(CompanyInfo companyInfo) {
        this.companyInfo = companyInfo;
    }

    public PersonDetails getPersonDetails() {
        return personDetails;
    }

    public void setPersonDetails(PersonDetails personDetails) {
        this.personDetails = personDetails;
    }

    public EmailDetails getEmailDetails() {
        return emailDetails;
    }

    public void setEmailDetails(EmailDetails emailDetails) {
        this.emailDetails = emailDetails;
    }

    public ContractInfo getContractInfo() {
        return contractInfo;
    }

    public void setContractInfo(ContractInfo contractInfo) {
        this.contractInfo = contractInfo;
    }
}
