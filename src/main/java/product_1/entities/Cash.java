package product_1.entities;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Cash {
    private BigDecimal cashOutLimitAmountPerDay;
    private String vrCurrency;
    private String description;
    private String code;
    private BigDecimal launchAmount = new BigDecimal((0));
    private Timestamp launchDate;
    private BigDecimal minimumBalanceAmount;
    private String vrCashier;
    private String vrAttachment;
    private BigDecimal cashOutLimitAmountPerTransaction;
    private String title;
    private BigDecimal maximumBalanceAmount;
}
