package product_1.DTO;

import product_1.entities.Entity;

public class BankAccountDTO extends Entity {
    private String accountNo;

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String bankAccountNo) {
        this.accountNo = bankAccountNo;
    }
}
