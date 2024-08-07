package product_2.DTO;

import product_2.entities.GLVoucher;

import java.util.List;

public class VoucherDTO {
    private List<VoucherArticleDTO> glVoucherArticleSet;
    private GLVoucher glVoucher;
    public List<VoucherArticleDTO> getGlVoucherArticleSet() {
        return glVoucherArticleSet;
    }

    public void setGlVoucherArticleSet(List<VoucherArticleDTO> glVoucherArticleSet) {
        this.glVoucherArticleSet = glVoucherArticleSet;
    }

    public GLVoucher getGlVoucher() {
        return glVoucher;
    }

    public void setGlVoucher(GLVoucher glVoucher) {
        this.glVoucher = glVoucher;
    }
}
