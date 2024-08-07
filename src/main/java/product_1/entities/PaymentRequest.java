package product_1.entities;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class PaymentRequest extends Entity {
    private Long id;
    private String vrCurrency;
    private Timestamp requestDate;
    private BigDecimal totalSettledAmount = new BigDecimal((0));

    private BusinessObjectDefinition businessObjectDefinition;
    private String vrFinancialParty;
    private String vrExchangeRateType;
    private Timestamp requestDueDate;

    private CategoryElement requestPriorityCtge;
    private String cancelReferenceNo;
    private String disapprovalReason;
    private String cancelReason;
    private String vrReason;

    private CategoryElement paymentTypeCtge;
    private String engineName;
    private String requestReason;
    private BigDecimal exchangeCurrencyRate;
    private String vrRequesterPerson;

    private List<ApprovalLog> approvalOpLogSet;

    private CategoryElement guaranteeTypeCtge;
    private String vrRequesterUnit;
    private String vrAttachment;

    private CategoryElement requestStatusCtge;
    private BigDecimal totalPaymentAmount;

    private CategoryElement creditTypeCtge;

    private CategoryElement paymentRequestTypeCtge;
    private String financialPartyName;

    private CategoryElement cancelReferenceTypeCtge;

    private CategoryElement referenceTypeCtge;
    private BigDecimal requestAmount;
    private String requestNo;
    private String vrOrganUnit;
    private String referenceNo;
    private List<PaymentRequestDetailList> paymentRequestDetailSet;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVrCurrency() {
        return vrCurrency;
    }

    public void setVrCurrency(String vrCurrency) {
        this.vrCurrency = vrCurrency;
    }

    public Timestamp getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Timestamp requestDate) {
        this.requestDate = requestDate;
    }

    public BigDecimal getTotalSettledAmount() {
        return totalSettledAmount;
    }

    public void setTotalSettledAmount(BigDecimal totalSettledAmount) {
        this.totalSettledAmount = totalSettledAmount;
    }

    public BusinessObjectDefinition getBusinessObjectDefinition() {
        return businessObjectDefinition;
    }

    public void setBusinessObjectDefinition(BusinessObjectDefinition businessObjectDefinition) {
        this.businessObjectDefinition = businessObjectDefinition;
    }

    public String getVrFinancialParty() {
        return vrFinancialParty;
    }

    public void setVrFinancialParty(String vrFinancialParty) {
        this.vrFinancialParty = vrFinancialParty;
    }

    public String getVrExchangeRateType() {
        return vrExchangeRateType;
    }

    public void setVrExchangeRateType(String vrExchangeRateType) {
        this.vrExchangeRateType = vrExchangeRateType;
    }

    public Timestamp getRequestDueDate() {
        return requestDueDate;
    }

    public void setRequestDueDate(Timestamp requestDueDate) {
        this.requestDueDate = requestDueDate;
    }

    public CategoryElement getRequestPriorityCtge() {
        return requestPriorityCtge;
    }

    public void setRequestPriorityCtge(CategoryElement requestPriorityCtge) {
        this.requestPriorityCtge = requestPriorityCtge;
    }

    public String getCancelReferenceNo() {
        return cancelReferenceNo;
    }

    public void setCancelReferenceNo(String cancelReferenceNo) {
        this.cancelReferenceNo = cancelReferenceNo;
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

    public String getVrReason() {
        return vrReason;
    }

    public void setVrReason(String vrReason) {
        this.vrReason = vrReason;
    }

    public CategoryElement getPaymentTypeCtge() {
        return paymentTypeCtge;
    }

    public void setPaymentTypeCtge(CategoryElement paymentTypeCtge) {
        this.paymentTypeCtge = paymentTypeCtge;
    }

    public String getEngineName() {
        return engineName;
    }

    public void setEngineName(String engineName) {
        this.engineName = engineName;
    }

    public String getRequestReason() {
        return requestReason;
    }

    public void setRequestReason(String requestReason) {
        this.requestReason = requestReason;
    }

    public BigDecimal getExchangeCurrencyRate() {
        return exchangeCurrencyRate;
    }

    public void setExchangeCurrencyRate(BigDecimal exchangeCurrencyRate) {
        this.exchangeCurrencyRate = exchangeCurrencyRate;
    }

    public String getVrRequesterPerson() {
        return vrRequesterPerson;
    }

    public void setVrRequesterPerson(String vrRequesterPerson) {
        this.vrRequesterPerson = vrRequesterPerson;
    }

    public List<ApprovalLog> getApprovalOpLogSet() {
        return approvalOpLogSet;
    }

    public void setApprovalOpLogSet(List<ApprovalLog> approvalOpLogSet) {
        this.approvalOpLogSet = approvalOpLogSet;
    }

    public CategoryElement getGuaranteeTypeCtge() {
        return guaranteeTypeCtge;
    }

    public void setGuaranteeTypeCtge(CategoryElement guaranteeTypeCtge) {
        this.guaranteeTypeCtge = guaranteeTypeCtge;
    }

    public String getVrRequesterUnit() {
        return vrRequesterUnit;
    }

    public void setVrRequesterUnit(String vrRequesterUnit) {
        this.vrRequesterUnit = vrRequesterUnit;
    }

    public String getVrAttachment() {
        return vrAttachment;
    }

    public void setVrAttachment(String vrAttachment) {
        this.vrAttachment = vrAttachment;
    }

    public CategoryElement getRequestStatusCtge() {
        return requestStatusCtge;
    }

    public void setRequestStatusCtge(CategoryElement requestStatusCtge) {
        this.requestStatusCtge = requestStatusCtge;
    }

    public BigDecimal getTotalPaymentAmount() {
        return totalPaymentAmount;
    }

    public void setTotalPaymentAmount(BigDecimal totalPaymentAmount) {
        this.totalPaymentAmount = totalPaymentAmount;
    }

    public CategoryElement getCreditTypeCtge() {
        return creditTypeCtge;
    }

    public void setCreditTypeCtge(CategoryElement creditTypeCtge) {
        this.creditTypeCtge = creditTypeCtge;
    }

    public CategoryElement getPaymentRequestTypeCtge() {
        return paymentRequestTypeCtge;
    }

    public void setPaymentRequestTypeCtge(CategoryElement paymentRequestTypeCtge) {
        this.paymentRequestTypeCtge = paymentRequestTypeCtge;
    }

    public String getFinancialPartyName() {
        return financialPartyName;
    }

    public void setFinancialPartyName(String financialPartyName) {
        this.financialPartyName = financialPartyName;
    }

    public CategoryElement getCancelReferenceTypeCtge() {
        return cancelReferenceTypeCtge;
    }

    public void setCancelReferenceTypeCtge(CategoryElement cancelReferenceTypeCtge) {
        this.cancelReferenceTypeCtge = cancelReferenceTypeCtge;
    }

    public CategoryElement getReferenceTypeCtge() {
        return referenceTypeCtge;
    }

    public void setReferenceTypeCtge(CategoryElement referenceTypeCtge) {
        this.referenceTypeCtge = referenceTypeCtge;
    }

    public BigDecimal getRequestAmount() {
        return requestAmount;
    }

    public void setRequestAmount(BigDecimal requestAmount) {
        this.requestAmount = requestAmount;
    }

    public String getRequestNo() {
        return requestNo;
    }

    public void setRequestNo(String requestNo) {
        this.requestNo = requestNo;
    }

    public String getVrOrganUnit() {
        return vrOrganUnit;
    }

    public void setVrOrganUnit(String vrOrganUnit) {
        this.vrOrganUnit = vrOrganUnit;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public List<PaymentRequestDetailList> getPaymentRequestDetailSet() {
        return paymentRequestDetailSet;
    }

    public void setPaymentRequestDetailSet(List<PaymentRequestDetailList> paymentRequestDetailSet) {
        this.paymentRequestDetailSet = paymentRequestDetailSet;
    }
}
