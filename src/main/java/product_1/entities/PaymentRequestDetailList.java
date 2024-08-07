package product_1.entities;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class PaymentRequestDetailList extends Entity {
    private Long id;
    private String destinationBankCode;
    private String lastName;
    private Timestamp paymentDate;
    private CategoryElement detailType;
    private String customerNumber;
    private String firstName;

    private CategoryElement paymentRequestDetailStatus;
    private String description;
    private String destinationIban;
    private String transactionCode;
    private BigDecimal amount;
    private String asyncTrackerId;
    private String destinationDepositNumber;
    private Long vrReference;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDestinationBankCode() {
        return destinationBankCode;
    }

    public void setDestinationBankCode(String destinationBankCode) {
        this.destinationBankCode = destinationBankCode;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Timestamp getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Timestamp paymentDate) {
        this.paymentDate = paymentDate;
    }

    public CategoryElement getDetailType() {
        return detailType;
    }

    public void setDetailType(CategoryElement detailType) {
        this.detailType = detailType;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public CategoryElement getPaymentRequestDetailStatus() {
        return paymentRequestDetailStatus;
    }

    public void setPaymentRequestDetailStatus(CategoryElement paymentRequestDetailStatus) {
        this.paymentRequestDetailStatus = paymentRequestDetailStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDestinationIban() {
        return destinationIban;
    }

    public void setDestinationIban(String destinationIban) {
        this.destinationIban = destinationIban;
    }

    public String getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getAsyncTrackerId() {
        return asyncTrackerId;
    }

    public void setAsyncTrackerId(String asyncTrackerId) {
        this.asyncTrackerId = asyncTrackerId;
    }

    public String getDestinationDepositNumber() {
        return destinationDepositNumber;
    }

    public void setDestinationDepositNumber(String destinationDepositNumber) {
        this.destinationDepositNumber = destinationDepositNumber;
    }

    public Long getVrReference() {
        return vrReference;
    }

    public void setVrReference(Long vrReference) {
        this.vrReference = vrReference;
    }
}
