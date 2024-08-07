package product_1.entities;

import java.math.BigDecimal;

public class PaymentRequestPayment extends Entity {

    private Long id;
    private PaymentRequest paymentRequest;
    private BigDecimal basePaymentAmount;
    private BigDecimal paymentAmount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PaymentRequest getPaymentRequest() {
        return paymentRequest;
    }

    public void setPaymentRequest(PaymentRequest paymentRequest) {
        this.paymentRequest = paymentRequest;
    }

    public BigDecimal getBasePaymentAmount() {
        return basePaymentAmount;
    }

    public void setBasePaymentAmount(BigDecimal basePaymentAmount) {
        this.basePaymentAmount = basePaymentAmount;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }
}
