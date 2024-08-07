package product_1.entities;

public class PodServiceConfig extends Entity {
    private String scApiKey;
    private BankAccount bankAccount;
    private String scTokenKey;
    private Bank bank;
    private CategoryElement podServiceCtge;
    private Boolean Active;

    public Boolean getActive() {
        return Active;
    }

    public void setActive(Boolean active) {
        Active = active;
    }

    public String getScApiKey() {
        return scApiKey;
    }

    public void setScApiKey(String scApiKey) {
        this.scApiKey = scApiKey;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getScTokenKey() {
        return scTokenKey;
    }

    public void setScTokenKey(String scTokenKey) {
        this.scTokenKey = scTokenKey;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public CategoryElement getPodServiceCtge() {
        return podServiceCtge;
    }

    public void setPodServiceCtge(CategoryElement podServiceCtge) {
        this.podServiceCtge = podServiceCtge;
    }
}
