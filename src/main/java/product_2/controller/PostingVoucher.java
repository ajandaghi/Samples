package product_2.controller;

import Repository.DBHelper;
import Repository.RunnerClass;
import Utils.DaivaServiceFactory;
import Utils.DateUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import exception.ProcessException;
import product_2.DTO.PostingVoucherDTO;
import product_2.DTO.VoucherArticleDTO;
import product_2.DTO.VoucherDTO;
import product_2.GLSCategoryLoader;
import product_2.entities.*;
import product_2.enums.GLSBooleanType;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class PostingVoucher {
    private static AtomicReference<Long> draftId;

    public static GLVoucher getGlVoucherFromGlVoucherDTO(PostingVoucherDTO postingVoucherDTO,VoucherDTO voucherDTO ){
        return null;
    }

    public static UserAcceptanceDTO throwFinanceException(PostingVoucherDTO postingVoucherDTO){
        return null;
    }

    public static PostingVoucherDTO validatePostingVoucher(GLVoucher postingVoucher,PostingVoucherDTO postingVoucherDTO,Object finEntity){
        return null;
    }

    public static GLVoucher financialDraftReview(GLVoucher postingVoucher,UserVo CURRENT_USER_DATA){
        return new GLVoucher();
    }
    public static Long registerVoucherDraft(PostingVoucherDTO postingVoucherDTO, Object finEntity, UserVo CURRENT_USER_DATA, Long draftId0, String param) throws IOException, SQLException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        // PostingVoucherDTO postingVoucherDTO= GLVoucherTemplateUtil.getPostingVoucher(bizTransTagCode,finEntity);
        AtomicReference<Long> id = new AtomicReference<>(0L);
        draftId = new AtomicReference<>(0L);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(param).get("voucherDTO");

        ((ObjectNode) jsonNode.get("glVoucher")).put("date", DateUtil.getDateFromStringInSolar(jsonNode.get("glVoucher").get("date").asText(), "yyyy/MM/dd").getTime());
        String article = "";
        for (int i = 0; i < ((ArrayNode) jsonNode.get("glVoucherArticleSet")).size(); i++) {
            JsonNode a = ((ArrayNode) jsonNode.get("glVoucherArticleSet")).get(i);
            ((ObjectNode) a.get("glVoucherArticle")).put("creditAmount", a.get("glVoucherArticle").get("creditAmount").asText().replace(",", ""));
            ((ObjectNode) a.get("glVoucherArticle")).put("debitAmount", a.get("glVoucherArticle").get("debitAmount").asText().replace(",", ""));
            ((ObjectNode) a.get("glVoucherArticle")).put("exchangeCurrencyRate", a.get("glVoucherArticle").get("exchangeCurrencyRate").asText().replace(",", ""));
            if (i == jsonNode.get("glVoucherArticleSet").size() - 1) {
                article += (a.get("glVoucherArticle") + "").replace("\\", "");
            } else {
                article += (a.get("glVoucherArticle") + ",").replace("\\", "");
            }
        }
        // ((ObjectNode) jsonNode.get("glVoucher")).put("glVoucherArticleSet",article);
        VoucherDTO voucherDTO = objectMapper.treeToValue(jsonNode, VoucherDTO.class);
        List<GLVoucherArticle> glVoucherArticles = new ArrayList<>();
        for (VoucherArticleDTO VoucherArticle : voucherDTO.getGlVoucherArticleSet()) {

            glVoucherArticles.add(VoucherArticle.getGlVoucherArticle());
        }
        voucherDTO.getGlVoucher().setGlVoucherArticleSet(glVoucherArticles);
        AtomicReference<GLVoucher> voucher = new AtomicReference<>();
        new RunnerClass(false).run(session -> {
            session.beginTransaction();
            try {
                if (draftId0 != null) {
                    voucher.set(session.findById(GLVoucher.class, draftId0));
                }
                if (voucher.get() == null) {
                    GLVoucher postingVoucher = getGlVoucherFromGlVoucherDTO(postingVoucherDTO, voucherDTO);
                    PostingVoucherDTO postingVoucherDTO0 = validatePostingVoucher(postingVoucher, postingVoucherDTO, finEntity);
                    UserAcceptanceDTO financialError = PostingVoucher.throwFinanceException(postingVoucherDTO0);
                    if (postingVoucherDTO0.getResult().equals("0")) {
                        id.set(financialDraftReview(postingVoucher, CURRENT_USER_DATA).getId());
                        DaivaServiceFactory.getEngineService().sendTextMessage("GLS_GLVOUCHER_REGISTER_VOUCHER_AS_DRAFT", DBHelper.getSession().findById(GLVoucher.class, id.get()).getTempVoucherNo());
                    }
                } else {
                    DaivaServiceFactory.getEngineService().sendTextMessage("GLS_GLVOUCHER_REGISTER_VOUCHER_AS_DRAFT_BEFORE", voucher.get().getTempVoucherNo());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            session.commitTransaction();
            session.close();
        });
        if (voucher.get() == null) {
            draftId.set(id.get());
            return id.get();
        }
        draftId.set(draftId0);
        return draftId0;
    }
    public static void deleteDraft() throws IOException, SQLException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {

    }

    public static Boolean deleteVoucherDraft(Long draftId0) throws IOException, SQLException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        AtomicBoolean isDeleted = new AtomicBoolean(true);
        new RunnerClass(false).run(session -> {
            if (session != null) {
                session.beginTransaction();
                try {
                    if (draftId0 != null) {
                        GLVoucher voucher = session.findById(GLVoucher.class, draftId0);
                        if (voucher != null) {
                            isDeleted.set(voucher.deleteAll());
                            DaivaServiceFactory.getEngineService().sendTextMessage("GLS_GLVOUCHER_REGISTER_VOUCHER_AS_DRAFT_DELETED", voucher.getTempVoucherNo());
                        } else {
                            isDeleted.set(false);
                            DaivaServiceFactory.getEngineService().sendTextMessage("GLS_GLVOUCHER_REGISTER_VOUCHER_AS_DRAFT_NOT_FOUND_DELETED");

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                session.commitTransaction();
                session.close();
            }
        });
        return isDeleted.get();
    }

    public static Boolean deleteVoucherDraftForTempVoucher() {
        AtomicBoolean isDeleted = new AtomicBoolean(true);
        new RunnerClass(false).run(session -> {
            if (session != null) {
                session.beginTransaction();
                try {
                    if (draftId != null && draftId.get()!=null) {
                        GLVoucher voucher = session.findById(GLVoucher.class, draftId.get());
                        if (voucher != null) {
                            isDeleted.set(voucher.deleteAll());
                            //  DaivaServiceFactory.getEngineService().sendTextMessage("GLS_GLVOUCHER_REGISTER_VOUCHER_AS_DRAFT_DELETED", voucher.getTempVoucherNo());

                        } else {
                            isDeleted.set(false);

                        }
                    }
                } catch (Exception e) {
                    throw new ProcessException(e.getMessage());
                }
                session.commitTransaction();
                session.close();
            }
        });
        return isDeleted.get();
    }

    public static Boolean isNeedToSaveDraftVoucher() {
        AccountingConfiguration accountingConfiguration=new AccountingConfiguration();
        accountingConfiguration=AccountingConfigurationController.findByCode("IsNeedToSaveDraftVoucher");
        if(accountingConfiguration!=null && accountingConfiguration.getValue()!=null){
            return   GLSCategoryLoader.loadCategoryElementByCode(GLSBooleanType.GLS_BOOLEAN_TRUE.code()).getId().equals(Long.parseLong(accountingConfiguration.getValue()));
        }
        return Boolean.FALSE;
    }

}
