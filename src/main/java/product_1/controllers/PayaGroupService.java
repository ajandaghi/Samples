package product_1.controllers;

import product_1.Facade.XRMFacade;
import Repository.CategoryLoader;
import Utils.DateUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import product_1.DTO.BankAccountDTO;
import product_1.entities.*;
import Repository.DBHelper;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.olingo.odata2.api.commons.HttpStatusCodes;
import org.json.JSONArray;
import org.json.JSONObject;
import exception.ProcessException;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class PayaGroupService {
    public static Payment prepare(){
        PaymentRequest paymentRequest=new PaymentRequest();
        paymentRequest.setId(5571L);
        List<PaymentRequestDetailList> paymentRequestDetailLists=new ArrayList<>();
        PaymentRequestDetailList paymentRequestDetailList=new PaymentRequestDetailList();
        paymentRequestDetailList.setId(1L);
        paymentRequestDetailList.setAmount(new BigDecimal("50"));
        paymentRequestDetailList.setDescription("rr");
        paymentRequestDetailList.setFirstName("");
        paymentRequestDetailList.setLastName("");
        paymentRequestDetailList.setDestinationIban("IR170570024580014923860101");
        paymentRequestDetailList.setDestinationDepositNumber("120.8000.12345678.1");
        paymentRequestDetailList.setCustomerNumber("2010");
        paymentRequestDetailList.setDestinationBankCode("22");
        paymentRequestDetailLists.add(paymentRequestDetailList);

        paymentRequest.setPaymentRequestDetailSet(paymentRequestDetailLists);
        Payment payment=new Payment();
        PaymentRequestPayment paymentRequestPayment=new PaymentRequestPayment();
        paymentRequestPayment.setPaymentRequest(paymentRequest);

        List<PaymentRequestPayment> paymentRequestPaymentList=new ArrayList<>();
        paymentRequestPaymentList.add(paymentRequestPayment);
        payment.setPaymentRequestPaymentSet(paymentRequestPaymentList);
        BankAccount bankAccount=new BankAccount();
        bankAccount.setId(361L);
        payment.setBankAccount(bankAccount);
        return payment;
    }
    public static PaymentRequestPayment getPaymentRequestPaymentFromPayment(Payment payment){
        return payment.getPaymentRequestPaymentSet().stream().findFirst().orElse(null);
    }

    public static void updatePaymentRequestDetail(PaymentRequestPayment paymentRequestPayment){
        if(paymentRequestPayment!=null && paymentRequestPayment.getPaymentRequest() !=null && paymentRequestPayment.getPaymentRequest().getPaymentRequestDetailSet() !=null && !paymentRequestPayment.getPaymentRequest().getPaymentRequestDetailSet().isEmpty()) {
            PaymentRequest paymentRequest = paymentRequestPayment.getPaymentRequest();
            List<PaymentRequestDetailList> paymentRequestDetailLists = paymentRequest.getPaymentRequestDetailSet();
            CategoryLoader categoryLoader = new CategoryLoader();
            paymentRequestDetailLists.forEach(a -> {
                PaymentRequestDetailList prdl = DBHelper.getSession().findById(PaymentRequestDetailList.class, a.getId());
                if (!prdl.getPaymentRequestDetailStatus().getCode().equals("FCS_POD_STATUS_BANK_PAID_CONFIRMED")) {
                    prdl.merge(a);
                    prdl.updateOnlyMe();
                }

            });
            PaymentRequest paymentRequest1 = DBHelper.getSession().findById(PaymentRequest.class, paymentRequest.getId());

            if(!paymentRequest1.getRequestAmount().equals(paymentRequest.getTotalPaymentAmount())){
                paymentRequest1.setRequestStatusCtge(categoryLoader.loadCategoryElementByCode("FCS_REQUEST_REMAINED"));
            }else{
                paymentRequest1.setRequestStatusCtge(categoryLoader.loadCategoryElementByCode("FCS_REQUEST_COMPLETED"));
            }

            paymentRequest1.setTotalPaymentAmount(paymentRequest.getTotalPaymentAmount());
            paymentRequest1.updateOnlyMe();


            PaymentRequestPayment prp = DBHelper.getSession().findById(PaymentRequestPayment.class, paymentRequestPayment.getId());
            prp.setPaymentAmount(paymentRequestPayment.getPaymentAmount());
            prp.setBasePaymentAmount(paymentRequestPayment.getBasePaymentAmount());
            prp.updateOnlyMe();
        }
    }
    public  static Payment callPayaGroup(Payment payment) throws Exception {
        // payment=prepare();
        CategoryLoader categoryLoader=new CategoryLoader();
        ObjectMapper objectMapper = new ObjectMapper();
        XRMFacade xrmFacade = new XRMFacade();
        BankAccountDTO bankAccount = xrmFacade.getBankAccountById(payment.getBankAccount().getVrPartyBankAccount());
        String bankAccountNo=bankAccount.getAccountNo();  //"120.8000.12345678.1"

        List<PaymentRequestDetailList> paymentRequestDetailLists= payment.getPaymentRequestPaymentSet().stream()
                .map(a->a.getPaymentRequest().getPaymentRequestDetailSet()).flatMap(Collection::stream)
                .filter(b->!b.getPaymentRequestDetailStatus().getCode().equals("FCS_POD_STATUS_BANK_PAID_CONFIRMED"))
                .collect(Collectors.toList());

        String asyncTrackerId= generateTrackingCodes();

        int i=0;
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject=null;
        for (PaymentRequestDetailList paymentRequestDetail : paymentRequestDetailLists) {
            jsonObject=new JSONObject();

            jsonObject.put("DetailType",paymentRequestDetail.getDetailType().getExtension());

            jsonObject.put("Amount",paymentRequestDetail.getAmount().toString());

            jsonObject.put("DestinationIban",paymentRequestDetail.getDestinationIban());

            jsonObject.put("SourceDepNum",bankAccountNo);

            jsonObject.put("RecieverFullName",(paymentRequestDetail.getFirstName() + " " + paymentRequestDetail.getLastName()).trim());

            jsonObject.put("Description",paymentRequestDetail.getDescription());

            jsonObject.put("TransactionDate", DateUtil.getCurrentDateInSolar("YYYY/MM/dd"));
            //  requestMap.put("transactionId",paymentRequestDetail.getVrReference().toString());
            String transactionCode=generateTrackingCodes();
            paymentRequestDetailLists.stream().filter(a->a.getId().equals(paymentRequestDetail.getId())).forEach(b->{
                b.setTransactionCode(transactionCode);
                b.setAsyncTrackerId(asyncTrackerId); });

            jsonObject.put("TransactionId",transactionCode);

            jsonObject.put("DestBankCode",paymentRequestDetail.getDestinationBankCode());

            jsonObject.put("customerNumber",bankAccountNo.split("\\.")[2]);

            jsonObject.put("isAutoVerify",Boolean.FALSE.toString());

            jsonArray.put(i++, jsonObject);
        }


        CloseableHttpClient client = HttpClients.createDefault();
// create request object

        String token= payment.getBankAccount().getBankBranch().getBank().fetchPodServiceConfigOfBank().stream().filter(a->a.getPodServiceCtge().getCode().equals("FCS_POD_SERVICE_TOKEN") && a.getActive()).findFirst().orElse(null).getScTokenKey();
        String scApiKey= payment.getBankAccount().fetchPodServiceConfigOfBankAccount().stream().filter(a->a.getPodServiceCtge().getCode().equals("FCS_POD_SERVICE_PAYA_TRANSFER_GROUP")&& a.getActive()).findFirst().orElse(null).getScApiKey();

        //post.addHeader("Content-Type","application/x-www-form-urlencoded");
        // create key-value pairs
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("scProductId", categoryLoader.loadCategoryElementByCode("FCS_POD_SERVICE_PAYA_TRANSFER_GROUP").getExtension()));
        params.add(new BasicNameValuePair("scApiKey", scApiKey));  //apiKey
        params.add(new BasicNameValuePair("request", jsonArray.toString()));
        params.add(new BasicNameValuePair("AsyncTrackerId",asyncTrackerId));

        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(params, "UTF-8");
        //urlEncodedFormEntity.setContentEncoding("UTF-8");
        HttpPost post = new HttpPost(
                "https://api.pod.ir/srv/sc/nzh/doServiceCall");  //https://api.pod.ir/srv/sc/nzh/doServiceCall
        post.addHeader("_token_",token);
        post.addHeader("_token_issuer_","1");
        post.addHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
        // add to request
        post.setEntity(urlEncodedFormEntity);

        // get response
        //
        //
        CloseableHttpResponse closeableHttpResponse =client.execute(post);
        if(closeableHttpResponse.getStatusLine().getStatusCode()== HttpStatusCodes.OK.getStatusCode()) {
            String response = EntityUtils.toString(closeableHttpResponse.getEntity());

            JsonNode node = objectMapper.readTree(response);
            if (node.has("hasError") && !node.get("hasError").asBoolean() && node.has("result") && node.get("result").has("result")) {

                if (objectMapper.readTree(node.get("result").get("result").asText()).get("IsSuccess").asBoolean()) {
                    paymentRequestDetailLists.forEach(a->{a.setPaymentRequestDetailStatus(categoryLoader.loadCategoryElementByCode("FCS_POD_STATUS_BANK_PAID_PENDING"));
                        a.setVrReference(Long.valueOf(node.get("referenceNumber").asText()));});

                } else{
                    throw new ProcessException("FCS_PAYMENT_REQUEST_DETAIL_POD_REQUEST_DETAIL_ERROR",node.get("message").asText());
                }
            } else {
                throw new ProcessException("FCS_PAYMENT_REQUEST_DETAIL_POD_REQUEST_DETAIL_ERROR", node.get("message").asText());
            }
        }else{
            throw new ProcessException("FCS_PAYMENT_REQUEST_DETAIL_POD_REQUEST_ERROR");

        }
        return payment;
    }
    public static Boolean ifReturnGettingResult(Payment payment)  {
        CategoryElement categoryElement=payment.getPaymentRequestPaymentSet().stream().map(a->a.getPaymentRequest().getPaymentRequestDetailSet())
                .flatMap(Collection::stream).map(b->b.getPaymentRequestDetailStatus())
                .filter(c->c.getCode().equals("FCS_POD_STATUS_BANK_PAID_PENDING")).findAny().orElse(null);
        return categoryElement != null;

    }
    public static Payment gettingPodResult(Payment payment,BigDecimal beforeAmount) throws Exception {
        BigDecimal amount;
        if(beforeAmount!=null)
            amount=beforeAmount;
        else
            amount=BigDecimal.ZERO;

        ObjectMapper objectMapper=new ObjectMapper();
        CategoryLoader categoryLoader=new CategoryLoader();
        List<PaymentRequestDetailList> paymentRequestDetailLists=payment.getPaymentRequestPaymentSet().stream().map(a->a.getPaymentRequest().getPaymentRequestDetailSet()).flatMap(Collection::stream)
                .filter(b->b.getPaymentRequestDetailStatus().getCode().equals("FCS_POD_STATUS_BANK_PAID_PENDING"))
                .collect(Collectors.toList());
        for(PaymentRequestDetailList prdl:paymentRequestDetailLists) {

            CloseableHttpClient client = HttpClients.createDefault();


            String token= payment.getBankAccount().getBankBranch().getBank().fetchPodServiceConfigOfBank().stream().filter(a->a.getPodServiceCtge().getCode().equals("FCS_POD_SERVICE_TOKEN")).findFirst().orElse(null).getScTokenKey();
            String scApiKey= payment.getBankAccount().fetchPodServiceConfigOfBankAccount().stream().filter(a->a.getPodServiceCtge().getCode().equals("FCS_POD_SERVICE_QUERY_RESULT_GROUP")).findFirst().orElse(null).getScApiKey();
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("scProductId", categoryLoader.loadCategoryElementByCode("FCS_POD_SERVICE_QUERY_RESULT_GROUP").getExtension()));
            params.add(new BasicNameValuePair("scApiKey",scApiKey ));  //apiKey
            JSONObject request=new JSONObject();
            request.put("AsyncTrackerId",prdl.getAsyncTrackerId());
            request.put("TransactionId",prdl.getTransactionCode());
            request.put("FromDate", DateUtil.getCurrentDateInSolar("YYYY/MM/dd"));
            request.put("ToDate", DateUtil.getCurrentDateInSolar("YYYY/MM/dd"));

            params.add(new BasicNameValuePair("request", request.toString()));
            UrlEncodedFormEntity urlEncodedFormEntity  =  new UrlEncodedFormEntity(params,"UTF-8");

            HttpPost post;
            post = new HttpPost("https://api.pod.ir/srv/sc/nzh/doServiceCall");
            /*
            if(prdl.getAmount().equals(new BigDecimal("3")))
                post = new HttpPost("https://b7167f3e-18d0-40d5-aa83-be6e64decfb6.mock.pstmn.io/unsuccess");
            else
                post = new HttpPost("https://b7167f3e-18d0-40d5-aa83-be6e64decfb6.mock.pstmn.io/success");
            */

            post.addHeader("_token_", token);
            post.addHeader("_token_issuer_", "1");
            post.addHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
            post.setEntity(urlEncodedFormEntity);
            CloseableHttpResponse closeableHttpResponse = client.execute(post);
            if (closeableHttpResponse.getStatusLine().getStatusCode() == HttpStatusCodes.OK.getStatusCode()) {
                String response = EntityUtils.toString(closeableHttpResponse.getEntity());

                JsonNode node = objectMapper.readTree(response);
                if (node.has("hasError") && !node.get("hasError").asBoolean() && node.has("result") && node.get("result").has("result")) {

                    if (objectMapper.readTree(node.get("result").get("result").asText()).get("IsSuccess").asBoolean() && objectMapper.readTree(node.get("result").get("result").asText()).has("ResultData") && objectMapper.readTree(node.get("result").get("result").asText()).get("ResultData").get("TransactionStatus").asText().equals("Success") && (objectMapper.readTree(node.get("result").get("result").asText()).has("ResultData") && objectMapper.readTree(node.get("result").get("result").asText()).get("ResultData").has("Result")
                            && objectMapper.readTree(node.get("result").get("result").asText()).get("ResultData").get("Result").has("PayaResult")) && (objectMapper.readTree(node.get("result").get("result").asText()).get("ResultData").get("Result").get("PayaResult").get("NewFormatState").asText().startsWith("ACH_CT_ACCEPTED_BY_RR")||(objectMapper.readTree(node.get("result").get("result").asText()).get("ResultData").get("Result").get("PayaResult").get("NewFormatState").asText().startsWith("ACH_CT_REGISTER_AND_PAID_STATE")))) {
                        amount=amount.add(prdl.getAmount());
                        prdl.setPaymentRequestDetailStatus(categoryLoader.loadCategoryElementByCode("FCS_POD_STATUS_BANK_PAID_CONFIRMED"));

                    } else if (!objectMapper.readTree(node.get("result").get("result").asText()).get("IsSuccess").asBoolean() || objectMapper.readTree(node.get("result").get("result").asText()).get("ResultData").get("TransactionStatus").asText().equals("Reversed") || objectMapper.readTree(node.get("result").get("result").asText()).get("ResultData").get("TransactionStatus").asText().equals("Unsuccess") ||
                            ((objectMapper.readTree(node.get("result").get("result").asText()).has("ResultData") && objectMapper.readTree(node.get("result").get("result").asText()).get("ResultData").has("Result")
                                    && objectMapper.readTree(node.get("result").get("result").asText()).get("ResultData").get("Result").has("PayaResult")) &&
                                    (objectMapper.readTree(node.get("result").get("result").asText()).get("ResultData").get("Result").get("PayaResult").get("NewFormatState").asText().startsWith("ACH_CT_REGISTER_AND_ERROR_STATE") ||
                                            objectMapper.readTree(node.get("result").get("result").asText()).get("ResultData").get("Result").get("PayaResult").get("NewFormatState").asText().startsWith("ACH_CT_REJECTED_BY_REJ_NOT_ISSUED")  ||
                                            objectMapper.readTree(node.get("result").get("result").asText()).get("ResultData").get("Result").get("PayaResult").get("NewFormatState").asText().startsWith("ACH_CT_CANCEL_STATE") ||
                                            objectMapper.readTree(node.get("result").get("result").asText()).get("ResultData").get("Result").get("PayaResult").get("NewFormatState").asText().startsWith("ACH_CT_CANCEL_IN_ACH_BRANCH_STATE") ||
                                            objectMapper.readTree(node.get("result").get("result").asText()).get("ResultData").get("Result").get("PayaResult").get("NewFormatState").asText().startsWith("ACH_CT_REJECTED_BY_RR") ||
                                            objectMapper.readTree(node.get("result").get("result").asText()).get("ResultData").get("Result").get("PayaResult").get("NewFormatState").asText().startsWith("ACH_CT_REJECTED_BY_REJ")  ||
                                            objectMapper.readTree(node.get("result").get("result").asText()).get("ResultData").get("Result").get("PayaResult").get("NewFormatState").asText().startsWith("ACH_CT_REJECTED_BY_DEST_BANK_STATE") ||
                                            objectMapper.readTree(node.get("result").get("result").asText()).get("ResultData").get("Result").get("PayaResult").get("NewFormatState").asText().startsWith("ACH_CT_REJECTED_BY_DEST_BANK_STATE_NOT_ISSUED")))){

                        prdl.setPaymentRequestDetailStatus(categoryLoader.loadCategoryElementByCode("FCS_POD_STATUS_BANK_PAID_REJECT"));
                    }

                }
            }
        }
        PaymentRequest paymentRequest=payment.getPaymentRequestPaymentSet().stream().map(a->a.getPaymentRequest()).distinct().findFirst().orElse(null);
        BigDecimal totalAmount=amount;
        payment.getPaymentRequestPaymentSet().stream().filter(c->c.getPaymentRequest().getId().equals(paymentRequest.getId())).forEach(b ->{ b.setBasePaymentAmount(totalAmount);b.setPaymentAmount(totalAmount); b.getPaymentRequest().setTotalPaymentAmount(totalAmount.add(b.getPaymentRequest().getTotalPaymentAmount()));});

        payment.setTotalAmount(amount);
        payment.setBaseTotalAmount(amount);
        payment.setBaseBankAmount(amount);
        payment.setBaseTotalPaymentAmount(amount);
        payment.setBankAmount(amount);


        return payment;
    }

    public static String generateTrackingCodes(){
        String a= UUID.randomUUID().toString().replace("-","");
        String b=String.valueOf(System.currentTimeMillis());
        return "4321-"+a+"-"+b+"-"+ Arrays.stream(("4321"+a+b).split("")).map(c->(int)c.charAt(0)).reduce(Integer::sum).orElse(0);
    }

    public static BigDecimal getTotalBeforeAmount(Payment payment){
        return payment.getTotalAmount();
    }

    public static BigDecimal setBeforeTotalAmount(){
        return BigDecimal.ZERO;
    }

    public static Long getCategoryIdOfPayaDetailTypes(){
        CategoryLoader categoryLoader=new CategoryLoader();
        return categoryLoader.loadCategoryElementByCode("FCS_PAYA_DETAIL_TYPE_DEPOSIT_SALARY").fetchCategoryOfCategoryElementSet().getId();
    }

    public static List<PodServiceConfig> getPodServiceConfigByBankId(BankAccountDTO bank) {
        if(bank!=null && bank.getId()!=null && !bank.getId().toString().equals("") ) {
            List<PodServiceConfig> podServiceConfigs=DBHelper.getSession().findById(BankAccount.class, bank.getId()).fetchPodServiceConfigOfBankAccount();
            if(podServiceConfigs!=null && !podServiceConfigs.isEmpty()) {
                List<PodServiceConfig> podServiceConfigList=new ArrayList<>();
                PodServiceConfig podServiceConfig = new PodServiceConfig();
                podServiceConfig.setId(podServiceConfigs.stream().filter(a->a.getPodServiceCtge().getCode().equals("FCS_POD_SERVICE_PAYA_TRANSFER_GROUP")).findFirst().orElse(null).getId());
                podServiceConfig.setScApiKey(podServiceConfigs.stream().filter(a->a.getPodServiceCtge().getCode().equals("FCS_POD_SERVICE_PAYA_TRANSFER_GROUP")).findFirst().orElse(null).getScApiKey());
                podServiceConfig.setPodServiceCtge(podServiceConfigs.stream().filter(a->a.getPodServiceCtge().getCode().equals("FCS_POD_SERVICE_PAYA_TRANSFER_GROUP")).findFirst().orElse(null).getPodServiceCtge());
                podServiceConfigList.add(podServiceConfig);

                PodServiceConfig podServiceConfig1 = new PodServiceConfig();
                podServiceConfig1.setId(podServiceConfigs.stream().filter(a->a.getPodServiceCtge().getCode().equals("FCS_POD_SERVICE_QUERY_RESULT_GROUP")).findFirst().orElse(null).getId());
                podServiceConfig1.setScApiKey(podServiceConfigs.stream().filter(a->a.getPodServiceCtge().getCode().equals("FCS_POD_SERVICE_QUERY_RESULT_GROUP")).findFirst().orElse(null).getScApiKey());
                podServiceConfig1.setPodServiceCtge(podServiceConfigs.stream().filter(a->a.getPodServiceCtge().getCode().equals("FCS_POD_SERVICE_QUERY_RESULT_GROUP")).findFirst().orElse(null).getPodServiceCtge());
                podServiceConfigList.add(podServiceConfig1);


                PodServiceConfig podServiceConfig2 = new PodServiceConfig();
                podServiceConfig2.setId(podServiceConfigs.stream().filter(a->a.getPodServiceCtge().getCode().equals("FCS_POD_SERVICE_TOKEN")).findFirst().orElse(null).getId());
                podServiceConfig2.setScTokenKey(podServiceConfigs.stream().filter(a->a.getPodServiceCtge().getCode().equals("FCS_POD_SERVICE_TOKEN")).findFirst().orElse(null).getScTokenKey());
                podServiceConfig2.setPodServiceCtge(podServiceConfigs.stream().filter(a->a.getPodServiceCtge().getCode().equals("FCS_POD_SERVICE_TOKEN")).findFirst().orElse(null).getPodServiceCtge());
                podServiceConfigList.add(podServiceConfig2);
                return podServiceConfigList;
            }
        }
        return null;
    }

    public static PodServiceConfig getCallPodServiceConfig(BankAccountDTO bank) {
        if (bank != null && bank.getId() != null && !bank.getId().toString().equals("")) {
            List<PodServiceConfig> podServiceConfigs = DBHelper.getSession().findById(BankAccount.class, bank.getId()).fetchPodServiceConfigOfBankAccount();
            if (podServiceConfigs != null && !podServiceConfigs.isEmpty()) {
                PodServiceConfig podServiceConfig = new PodServiceConfig();
                podServiceConfig.setId(podServiceConfigs.stream().filter(a -> a.getPodServiceCtge().getCode().equals("FCS_POD_SERVICE_PAYA_TRANSFER_GROUP")).findFirst().orElse(null).getId());
                podServiceConfig.setScApiKey(podServiceConfigs.stream().filter(a -> a.getPodServiceCtge().getCode().equals("FCS_POD_SERVICE_PAYA_TRANSFER_GROUP")).findFirst().orElse(null).getScApiKey());
                podServiceConfig.setPodServiceCtge(podServiceConfigs.stream().filter(a -> a.getPodServiceCtge().getCode().equals("FCS_POD_SERVICE_PAYA_TRANSFER_GROUP")).findFirst().orElse(null).getPodServiceCtge());
                podServiceConfig.setActive(podServiceConfigs.stream().filter(a -> a.getPodServiceCtge().getCode().equals("FCS_POD_SERVICE_PAYA_TRANSFER_GROUP")).findFirst().orElse(null).getActive());

                return podServiceConfig;
            }
        }
        return null;

    }
    public static PodServiceConfig getQueryPodServiceConfig(BankAccountDTO bank) {
        if (bank != null && bank.getId() != null && !bank.getId().toString().equals("")) {
            List<PodServiceConfig> podServiceConfigs = DBHelper.getSession().findById(BankAccount.class, bank.getId()).fetchPodServiceConfigOfBankAccount();
            if (podServiceConfigs != null && !podServiceConfigs.isEmpty()) {
                PodServiceConfig podServiceConfig = new PodServiceConfig();
                podServiceConfig.setId(podServiceConfigs.stream().filter(a -> a.getPodServiceCtge().getCode().equals("FCS_POD_SERVICE_QUERY_RESULT_GROUP")).findFirst().orElse(null).getId());
                podServiceConfig.setScApiKey(podServiceConfigs.stream().filter(a -> a.getPodServiceCtge().getCode().equals("FCS_POD_SERVICE_QUERY_RESULT_GROUP")).findFirst().orElse(null).getScApiKey());
                podServiceConfig.setPodServiceCtge(podServiceConfigs.stream().filter(a -> a.getPodServiceCtge().getCode().equals("FCS_POD_SERVICE_QUERY_RESULT_GROUP")).findFirst().orElse(null).getPodServiceCtge());
                podServiceConfig.setActive(podServiceConfigs.stream().filter(a -> a.getPodServiceCtge().getCode().equals("FCS_POD_SERVICE_QUERY_RESULT_GROUP")).findFirst().orElse(null).getActive());
                return podServiceConfig;
            }
        }
        return null;

    }

    public static PodServiceConfig getTokenOfPodServiceConfig(BankAccountDTO bank) {
        if(bank!=null && bank.getId()!=null && !bank.getId().toString().equals("") ) {
            List<PodServiceConfig> podServiceConfigs = DBHelper.getSession().findById(BankAccount.class, bank.getId()).fetchPodServiceConfigOfBankAccount();
            if (podServiceConfigs != null && !podServiceConfigs.isEmpty()) {
                PodServiceConfig podServiceConfig = new PodServiceConfig();
                podServiceConfig.setId(podServiceConfigs.stream().filter(a -> a.getPodServiceCtge().getCode().equals("FCS_POD_SERVICE_TOKEN")).findFirst().orElse(null).getId());
                podServiceConfig.setScTokenKey(podServiceConfigs.stream().filter(a -> a.getPodServiceCtge().getCode().equals("FCS_POD_SERVICE_TOKEN")).findFirst().orElse(null).getScTokenKey());
                podServiceConfig.setPodServiceCtge(podServiceConfigs.stream().filter(a -> a.getPodServiceCtge().getCode().equals("FCS_POD_SERVICE_TOKEN")).findFirst().orElse(null).getPodServiceCtge());
                podServiceConfig.setActive(podServiceConfigs.stream().filter(a -> a.getPodServiceCtge().getCode().equals("FCS_POD_SERVICE_TOKEN")).findFirst().orElse(null).getActive());
                return podServiceConfig;
            }
        }
        return null;

    }
    public static void updatePodServiceConfig(BankAccountDTO bank,PodServiceConfig tokenConfig,PodServiceConfig callConfig,PodServiceConfig queryConfig) {
        BankAccount bankAccount=DBHelper.getSession().findById(BankAccount.class,bank.getId());
        bankAccount.fetchPodServiceConfigOfBankAccount().forEach(a->{
            if(callConfig.getId()!=null && a.getId().equals(callConfig.getId()) && a.getPodServiceCtge().getCode().equals("FCS_POD_SERVICE_PAYA_TRANSFER_GROUP")){
                PodServiceConfig call=DBHelper.getSession().findById(PodServiceConfig.class,a.getId());
                call.setScApiKey(callConfig.getScApiKey());
                call.setActive(callConfig.getActive());
                call.updateOnlyMe();
            }else if(tokenConfig.getId()!=null  && a.getId().equals(tokenConfig.getId()) && a.getPodServiceCtge().getCode().equals("FCS_POD_SERVICE_TOKEN")){
                PodServiceConfig token=DBHelper.getSession().findById(PodServiceConfig.class,a.getId());
                token.setScTokenKey(tokenConfig.getScTokenKey());
                token.setActive(tokenConfig.getActive());
                token.updateOnlyMe();
            }else if(queryConfig.getId()!=null  && a.getId().equals(queryConfig.getId()) && a.getPodServiceCtge().getCode().equals("FCS_POD_SERVICE_QUERY_RESULT_GROUP")){
                PodServiceConfig query=DBHelper.getSession().findById(PodServiceConfig.class,a.getId());
                query.setScApiKey(queryConfig.getScApiKey());
                query.setActive(queryConfig.getActive());
                query.updateOnlyMe();
            }
        });
        CategoryLoader categoryLoader=new CategoryLoader();
        if(tokenConfig.getId()==null){
            BankAccount ba=DBHelper.getSession().findById(BankAccount.class,bank.getId());
            PodServiceConfig podConfig=new PodServiceConfig();
            tokenConfig.setBank(ba.getBankBranch().getBank());
            tokenConfig.setBankAccount(ba);
            tokenConfig.setPodServiceCtge(categoryLoader.loadCategoryElementByCode("FCS_POD_SERVICE_TOKEN"));
            podConfig=(PodServiceConfig)podConfig.merge(tokenConfig);
            podConfig.insertAll();

        }

        if(callConfig.getId()==null){
            BankAccount ba=DBHelper.getSession().findById(BankAccount.class,bank.getId());
            PodServiceConfig podConfig=new PodServiceConfig();
            callConfig.setBank(ba.getBankBranch().getBank());
            callConfig.setBankAccount(ba);
            callConfig.setPodServiceCtge(categoryLoader.loadCategoryElementByCode("FCS_POD_SERVICE_PAYA_TRANSFER_GROUP"));
            podConfig=(PodServiceConfig)podConfig.merge(callConfig);
            podConfig.insertAll();

        }
        if(queryConfig.getId()==null){
            BankAccount ba=DBHelper.getSession().findById(BankAccount.class,bank.getId());
            PodServiceConfig podConfig=new PodServiceConfig();
            queryConfig.setBank(ba.getBankBranch().getBank());
            queryConfig.setBankAccount(ba);
            queryConfig.setPodServiceCtge(categoryLoader.loadCategoryElementByCode("FCS_POD_SERVICE_QUERY_RESULT_GROUP"));
            podConfig=(PodServiceConfig)podConfig.merge(queryConfig);
            podConfig.insertAll();

        }
    }

}

