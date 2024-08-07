package product_1.entities;

public class BankBranch {
    private String supervisor;

    private CategoryElement branchTypeCtge;
    private String code;
    private String vrOrganization;
    private Bank bank;

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public CategoryElement getBranchTypeCtge() {
        return branchTypeCtge;
    }

    public void setBranchTypeCtge(CategoryElement branchTypeCtge) {
        this.branchTypeCtge = branchTypeCtge;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getVrOrganization() {
        return vrOrganization;
    }

    public void setVrOrganization(String vrOrganization) {
        this.vrOrganization = vrOrganization;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }
}
