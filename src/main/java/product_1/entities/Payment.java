package product_1.entities;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class Payment {
    private Long id;
    private BigDecimal bankAmount;
    private CategoryElement paymentMethodCtge;
    private String bankPayTo;
    private String vrTempVoucherNo;
    private String vrCurrency;
    private CategoryElement paymentStatusCtge;
    private String cashNumber;
    private String paymentNumber;
    private String vrBankFundType;
    private BigDecimal baseChequeAmount;
    private String vrCashFundType;
    private BigDecimal baseCashAmount;
    private String chequeNumber;
    private String chequeDescription;

    private BankAccount bankAccount;
    private Cash cash;
    private String bankNumber;
    private BigDecimal cashAmount;
    private BigDecimal baseTotalPaymentAmount;
    private BigDecimal baseBankAmount;
    private String cashDescription;
    private BigDecimal baseTotalAmount;
    private String bankDescription;
    private BigDecimal chequeAmount;
    private String vrExchangeRateType;
    private List<PaymentRequestPayment> paymentRequestPaymentSet;
    private Boolean isMultyCurrency = ((Boolean) false);

    private ChequeFolio chequeFolio;
    private BigDecimal exchangeCurrencyRate;
    private String vrAttachment;

    private List<PaymentDetail> paymentDetailSet;
    private BigDecimal totalAmount;
    private Boolean isDeployment = ((Boolean) false);
    private String vrChequeFundType;
    private String disapprovalReason;
    private String cancelReason;
    private String cashPayTo;
    private Timestamp paymentDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBankAmount() {
        return bankAmount;
    }

    public void setBankAmount(BigDecimal bankAmount) {
        this.bankAmount = bankAmount;
    }

    public CategoryElement getPaymentMethodCtge() {
        return paymentMethodCtge;
    }

    public void setPaymentMethodCtge(CategoryElement paymentMethodCtge) {
        this.paymentMethodCtge = paymentMethodCtge;
    }

    public String getBankPayTo() {
        return bankPayTo;
    }

    public void setBankPayTo(String bankPayTo) {
        this.bankPayTo = bankPayTo;
    }

    public String getVrTempVoucherNo() {
        return vrTempVoucherNo;
    }

    public void setVrTempVoucherNo(String vrTempVoucherNo) {
        this.vrTempVoucherNo = vrTempVoucherNo;
    }

    public String getVrCurrency() {
        return vrCurrency;
    }

    public void setVrCurrency(String vrCurrency) {
        this.vrCurrency = vrCurrency;
    }

    public CategoryElement getPaymentStatusCtge() {
        return paymentStatusCtge;
    }

    public void setPaymentStatusCtge(CategoryElement paymentStatusCtge) {
        this.paymentStatusCtge = paymentStatusCtge;
    }

    public String getCashNumber() {
        return cashNumber;
    }

    public void setCashNumber(String cashNumber) {
        this.cashNumber = cashNumber;
    }

    public String getPaymentNumber() {
        return paymentNumber;
    }

    public void setPaymentNumber(String paymentNumber) {
        this.paymentNumber = paymentNumber;
    }

    public String getVrBankFundType() {
        return vrBankFundType;
    }

    public void setVrBankFundType(String vrBankFundType) {
        this.vrBankFundType = vrBankFundType;
    }

    public BigDecimal getBaseChequeAmount() {
        return baseChequeAmount;
    }

    public void setBaseChequeAmount(BigDecimal baseChequeAmount) {
        this.baseChequeAmount = baseChequeAmount;
    }

    public String getVrCashFundType() {
        return vrCashFundType;
    }

    public void setVrCashFundType(String vrCashFundType) {
        this.vrCashFundType = vrCashFundType;
    }

    public BigDecimal getBaseCashAmount() {
        return baseCashAmount;
    }

    public void setBaseCashAmount(BigDecimal baseCashAmount) {
        this.baseCashAmount = baseCashAmount;
    }

    public String getChequeNumber() {
        return chequeNumber;
    }

    public void setChequeNumber(String chequeNumber) {
        this.chequeNumber = chequeNumber;
    }

    public String getChequeDescription() {
        return chequeDescription;
    }

    public void setChequeDescription(String chequeDescription) {
        this.chequeDescription = chequeDescription;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public Cash getCash() {
        return cash;
    }

    public void setCash(Cash cash) {
        this.cash = cash;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public BigDecimal getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(BigDecimal cashAmount) {
        this.cashAmount = cashAmount;
    }

    public BigDecimal getBaseTotalPaymentAmount() {
        return baseTotalPaymentAmount;
    }

    public void setBaseTotalPaymentAmount(BigDecimal baseTotalPaymentAmount) {
        this.baseTotalPaymentAmount = baseTotalPaymentAmount;
    }

    public BigDecimal getBaseBankAmount() {
        return baseBankAmount;
    }

    public void setBaseBankAmount(BigDecimal baseBankAmount) {
        this.baseBankAmount = baseBankAmount;
    }

    public String getCashDescription() {
        return cashDescription;
    }

    public void setCashDescription(String cashDescription) {
        this.cashDescription = cashDescription;
    }

    public BigDecimal getBaseTotalAmount() {
        return baseTotalAmount;
    }

    public void setBaseTotalAmount(BigDecimal baseTotalAmount) {
        this.baseTotalAmount = baseTotalAmount;
    }

    public String getBankDescription() {
        return bankDescription;
    }

    public void setBankDescription(String bankDescription) {
        this.bankDescription = bankDescription;
    }

    public BigDecimal getChequeAmount() {
        return chequeAmount;
    }

    public void setChequeAmount(BigDecimal chequeAmount) {
        this.chequeAmount = chequeAmount;
    }

    public String getVrExchangeRateType() {
        return vrExchangeRateType;
    }

    public void setVrExchangeRateType(String vrExchangeRateType) {
        this.vrExchangeRateType = vrExchangeRateType;
    }

    public List<PaymentRequestPayment> getPaymentRequestPaymentSet() {
        return paymentRequestPaymentSet;
    }

    public void setPaymentRequestPaymentSet(List<PaymentRequestPayment> paymentRequestPaymentSet) {
        this.paymentRequestPaymentSet = paymentRequestPaymentSet;
    }

    public Boolean getMultyCurrency() {
        return isMultyCurrency;
    }

    public void setMultyCurrency(Boolean multyCurrency) {
        isMultyCurrency = multyCurrency;
    }

    public ChequeFolio getChequeFolio() {
        return chequeFolio;
    }

    public void setChequeFolio(ChequeFolio chequeFolio) {
        this.chequeFolio = chequeFolio;
    }

    public BigDecimal getExchangeCurrencyRate() {
        return exchangeCurrencyRate;
    }

    public void setExchangeCurrencyRate(BigDecimal exchangeCurrencyRate) {
        this.exchangeCurrencyRate = exchangeCurrencyRate;
    }

    public String getVrAttachment() {
        return vrAttachment;
    }

    public void setVrAttachment(String vrAttachment) {
        this.vrAttachment = vrAttachment;
    }

    public List<PaymentDetail> getPaymentDetailSet() {
        return paymentDetailSet;
    }

    public void setPaymentDetailSet(List<PaymentDetail> paymentDetailSet) {
        this.paymentDetailSet = paymentDetailSet;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Boolean getDeployment() {
        return isDeployment;
    }

    public void setDeployment(Boolean deployment) {
        isDeployment = deployment;
    }

    public String getVrChequeFundType() {
        return vrChequeFundType;
    }

    public void setVrChequeFundType(String vrChequeFundType) {
        this.vrChequeFundType = vrChequeFundType;
    }

    public String getDisapprovalReason() {
        return disapprovalReason;
    }

    public void setDisapprovalReason(String disapprovalReason) {
        this.disapprovalReason = disapprovalReason;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public String getCashPayTo() {
        return cashPayTo;
    }

    public void setCashPayTo(String cashPayTo) {
        this.cashPayTo = cashPayTo;
    }

    public Timestamp getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Timestamp paymentDate) {
        this.paymentDate = paymentDate;
    }
}
