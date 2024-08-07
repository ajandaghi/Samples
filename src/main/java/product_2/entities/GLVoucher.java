package product_2.entities;

import java.util.List;

public class GLVoucher extends Entity {
    private String TempVoucherNo;
    private List<GLVoucherArticle> glVoucherArticleSet;

    public List<GLVoucherArticle> getGlVoucherArticleSet() {
        return glVoucherArticleSet;
    }

    public void setGlVoucherArticleSet(List<GLVoucherArticle> glVoucherArticleSet) {
        this.glVoucherArticleSet = glVoucherArticleSet;
    }

    public String getTempVoucherNo() {
        return TempVoucherNo;
    }

    public void setTempVoucherNo(String tempVoucherNo) {
        TempVoucherNo = tempVoucherNo;
    }
}
