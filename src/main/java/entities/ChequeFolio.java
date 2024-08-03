package entities;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class ChequeFolio {
    private String vrCurrency;

    private CategoryElement chequeFolioTypeCtge;
    private String payTo2;
    private Timestamp validityDate;
    private Boolean isGuarantee = ((Boolean) false);
    private String statusReason;

    private Bank receiveBank;
    private String vrPurchaseOrder;
    private String chequeFolioNo;
    private Timestamp dueDate;
    private String vrLoan;

    private ChequeOperation lastChequeOperation;

    private CategoryElement guaranteeUsageCtge;

    private BankAccount depositBankAccount;
    private Boolean isRegisteredOnPichak = ((Boolean) false);

    private MasterVariableCategoryElement voidReasonMvce;
    private Timestamp issueDate;
    private String payTo1;

    private CategoryElement chequeFolioStatusCtge;

    private List<ChequeOperation> chequeOperationSet;
    private String behalf;
    private BigDecimal amount;
    private String uniqueCode;
}
