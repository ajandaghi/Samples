package product_1.entities;

import java.sql.Timestamp;

public class ChequeOperation {
    private String vrPerson;
    private String chequeOperationNo;
    private String disapprovalReason;

    private CategoryElement chequeOperationTypeCtge;

    private CategoryElement chequeOperationStatusCtge;
    private Timestamp date;
    private Boolean isDeployment = ((Boolean) false);
    private String vrAttachment;
    private String vrTempVoucherNo;
    private String operationActor;
    private String description;

}
