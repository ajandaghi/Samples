package product_1.entities;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class BankAccount {
    private Long id;
    private String title;
    private String vrOrganization;
    private Boolean haveChequeBook = ((Boolean) false);
    private Timestamp openingDate;

    private BankAccount backupAccount;
    private String internetUser;
    private BigDecimal cashOutLimitAmountPerTransaction;

    private List<BankAccountOwner> bankAccountOwnerSet;
    private String internetPassword;

    private List<DepositCard> depositCardSet;
    private BigDecimal minimumBalanceAmount;
    private String description;
    private BigDecimal maximumBalanceAmount;

    private BankBranch bankBranch;
    private BigDecimal chequeOutLimitAmountPerTransaction;

    private CategoryElement accountStatusCtge;
    private String vrPartyBankAccount;
    private BigDecimal cashOutLimitAmountPerMonth;
    private BigDecimal chequeOutLimitAmountPerDate;
    private BigDecimal cashOutLimitAmountPerDay;
    private String customerNo;
    private Boolean isNonCorporate = ((Boolean) false);

    public List<PodServiceConfig> fetchPodServiceConfigOfBankAccount(){
        return null;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVrOrganization() {
        return vrOrganization;
    }

    public void setVrOrganization(String vrOrganization) {
        this.vrOrganization = vrOrganization;
    }

    public Boolean getHaveChequeBook() {
        return haveChequeBook;
    }

    public void setHaveChequeBook(Boolean haveChequeBook) {
        this.haveChequeBook = haveChequeBook;
    }

    public Timestamp getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(Timestamp openingDate) {
        this.openingDate = openingDate;
    }

    public BankAccount getBackupAccount() {
        return backupAccount;
    }

    public void setBackupAccount(BankAccount backupAccount) {
        this.backupAccount = backupAccount;
    }

    public String getInternetUser() {
        return internetUser;
    }

    public void setInternetUser(String internetUser) {
        this.internetUser = internetUser;
    }

    public BigDecimal getCashOutLimitAmountPerTransaction() {
        return cashOutLimitAmountPerTransaction;
    }

    public void setCashOutLimitAmountPerTransaction(BigDecimal cashOutLimitAmountPerTransaction) {
        this.cashOutLimitAmountPerTransaction = cashOutLimitAmountPerTransaction;
    }

    public List<BankAccountOwner> getBankAccountOwnerSet() {
        return bankAccountOwnerSet;
    }

    public void setBankAccountOwnerSet(List<BankAccountOwner> bankAccountOwnerSet) {
        this.bankAccountOwnerSet = bankAccountOwnerSet;
    }

    public String getInternetPassword() {
        return internetPassword;
    }

    public void setInternetPassword(String internetPassword) {
        this.internetPassword = internetPassword;
    }

    public List<DepositCard> getDepositCardSet() {
        return depositCardSet;
    }

    public void setDepositCardSet(List<DepositCard> depositCardSet) {
        this.depositCardSet = depositCardSet;
    }

    public BigDecimal getMinimumBalanceAmount() {
        return minimumBalanceAmount;
    }

    public void setMinimumBalanceAmount(BigDecimal minimumBalanceAmount) {
        this.minimumBalanceAmount = minimumBalanceAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getMaximumBalanceAmount() {
        return maximumBalanceAmount;
    }

    public void setMaximumBalanceAmount(BigDecimal maximumBalanceAmount) {
        this.maximumBalanceAmount = maximumBalanceAmount;
    }

    public BankBranch getBankBranch() {
        return bankBranch;
    }

    public void setBankBranch(BankBranch bankBranch) {
        this.bankBranch = bankBranch;
    }

    public BigDecimal getChequeOutLimitAmountPerTransaction() {
        return chequeOutLimitAmountPerTransaction;
    }

    public void setChequeOutLimitAmountPerTransaction(BigDecimal chequeOutLimitAmountPerTransaction) {
        this.chequeOutLimitAmountPerTransaction = chequeOutLimitAmountPerTransaction;
    }

    public CategoryElement getAccountStatusCtge() {
        return accountStatusCtge;
    }

    public void setAccountStatusCtge(CategoryElement accountStatusCtge) {
        this.accountStatusCtge = accountStatusCtge;
    }

    public String getVrPartyBankAccount() {
        return vrPartyBankAccount;
    }

    public void setVrPartyBankAccount(String vrPartyBankAccount) {
        this.vrPartyBankAccount = vrPartyBankAccount;
    }

    public BigDecimal getCashOutLimitAmountPerMonth() {
        return cashOutLimitAmountPerMonth;
    }

    public void setCashOutLimitAmountPerMonth(BigDecimal cashOutLimitAmountPerMonth) {
        this.cashOutLimitAmountPerMonth = cashOutLimitAmountPerMonth;
    }

    public BigDecimal getChequeOutLimitAmountPerDate() {
        return chequeOutLimitAmountPerDate;
    }

    public void setChequeOutLimitAmountPerDate(BigDecimal chequeOutLimitAmountPerDate) {
        this.chequeOutLimitAmountPerDate = chequeOutLimitAmountPerDate;
    }

    public BigDecimal getCashOutLimitAmountPerDay() {
        return cashOutLimitAmountPerDay;
    }

    public void setCashOutLimitAmountPerDay(BigDecimal cashOutLimitAmountPerDay) {
        this.cashOutLimitAmountPerDay = cashOutLimitAmountPerDay;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public Boolean getNonCorporate() {
        return isNonCorporate;
    }

    public void setNonCorporate(Boolean nonCorporate) {
        isNonCorporate = nonCorporate;
    }
}
