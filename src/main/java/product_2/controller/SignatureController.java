package product_2.controller;

import Utils.DaivaServiceFactory;
import Utils.FileUtils;
import Utils.JsonUtil;
import com.aspose.words.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.UserSignatureVO;
import product_2.DTO.KeyValueDTO;
import product_2.DTO.SignatureDTO;
import product_2.facade.EMSFacade;
import product_2.facade.XRMFacade;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class SignatureController {
    public static Boolean saveSignatureFile(String fileName, String id) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode fn = objectMapper.readTree(fileName);

        String path = System.getProperty("user.dir");// + "\\sign\\" + System.currentTimeMillis();
        File newFile = new File(path);
        //  newFile.mkdir();
        File file = DaivaServiceFactory.getEngineService().download(newFile.getAbsolutePath(), fn.get(0).get("key").asText());

        byte[] fileContent = Files.readAllBytes(file.toPath());
        String sign = Base64.getEncoder().encodeToString(fileContent);
        FileUtils.deleteQuietly(file);
        UserSignatureVO userSignatureVO = new UserSignatureVO();
        userSignatureVO.setSignature(sign);
        EMSFacade emsFacade = new EMSFacade();
        XRMFacade xrmFacade = new XRMFacade();

        userSignatureVO.setUsername(DaivaServiceFactory.getUserService().getPersonUsername(id));
        DaivaServiceFactory.getUserService().addSignature(userSignatureVO);

        return DaivaServiceFactory.getUserService().getSignatureByUsername(userSignatureVO.getUsername()) != null;

    }

    public static String getSignFileName(SignatureDTO sign) {
        return sign.getFileName();
    }

    public static String getPersonId(SignatureDTO sign) {
        return sign.getName();
    }

    public static String getSignature(String id) throws Exception {
        XRMFacade xrmFacade = new XRMFacade();
        EMSFacade emsFacade = new EMSFacade();
        if (id != null && !id.equals("") && DaivaServiceFactory.getUserService().getPersonUsername(id) != null) {
            String sign = DaivaServiceFactory.getUserService().getSignatureByUsername(DaivaServiceFactory.getUserService().getPersonUsername(id));
            if (sign != null) {
                String path = System.getProperty("user.dir") + "\\sign\\" + System.currentTimeMillis();
                byte[] fileContent = Base64.getDecoder().decode(sign);
                File file = new File(path);
                FileUtils.writeByteArrayToFile(file, fileContent);
                String key = DaivaServiceFactory.getEngineService().upload(file);
                FileUtils.deleteQuietly(file);
                return key;
            }
        }
        return null;
    }

    public static String getSignatureString(String id) throws Exception {
        XRMFacade xrmFacade = new XRMFacade();
        EMSFacade emsFacade = new EMSFacade();
        if (id != null && !id.equals("") && DaivaServiceFactory.getUserService().getPersonUsername(id) != null) {
            String sign = DaivaServiceFactory.getUserService().getSignatureByUsername(DaivaServiceFactory.getUserService().getPersonUsername(id));
            if (sign != null) {

                return sign;
            }
        }
        return null;
    }

    public List<KeyValueDTO> getPrintTemplates(String tempt) throws IOException {
        List<KeyValueDTO> templates = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        for (JsonNode temp : objectMapper.readTree(tempt)) {
            KeyValueDTO keyValueDTO = new KeyValueDTO();
            keyValueDTO.setText(temp.get("name").asText());
            keyValueDTO.setCode(temp.get("key").asText());
            templates.add(keyValueDTO);
        }
        return templates;
    }

    public static Set<String> getClassNamesFromCrackedJarFile() throws IOException {
        Set<String> classNames = new HashSet<>();
        try (JarFile jarFile = new JarFile("E:\\Projects\\bpmn\\fanrp\\GLS\\src\\main\\resources\\aspose-words.jar")) {
            Enumeration<JarEntry> e = jarFile.entries();
            while (e.hasMoreElements()) {
                JarEntry jarEntry = e.nextElement();
                if (jarEntry.getName().endsWith(".class")) {
                    String className = jarEntry.getName()
                            .replace("/", ".")
                            .replace(".class", "");
                    classNames.add(className);
                }
            }
            return classNames;
        }
    }

    public String getPDFFile(String param, String temp) throws Exception {
        License license=new License();
        ClassLoader classLoader=getClass().getClassLoader();
        File file0 = new File(classLoader.getResource("AsposeLicense.xml").getFile());
        FileInputStream fstream = new FileInputStream(file0.getAbsolutePath());
        license.setLicense(fstream);

        Map<String, String> map = JsonUtil.getObject(param, Map.class);

        ObjectMapper objectMapper = new ObjectMapper();
        String key;
        if (objectMapper.readTree(temp).get(0) != null) {
            key = objectMapper.readTree(temp).get(0).get("key").asText();
        } else {
            key = objectMapper.readTree(temp).asText();
        }
        JsonNode items = objectMapper.readTree(JsonUtil.getJson(map.get("PrintVoucherDTO"))).get("printVoucherArticleDTOS");
        List<String> rows = new ArrayList<>();
        for (final JsonNode item : items) {
            rows.add(item.get("rowNumber").asText());
        }
        String path = System.getProperty("user.dir");
        File newFile = new File(path);


        File file = DaivaServiceFactory.getEngineService().download(newFile.getAbsolutePath(), key);
        if (file == null) {
            return "";
        }
        Document doc = new Document(file.getAbsolutePath());
        List<String> escapeTagList = new ArrayList<>();
        Table table = (Table) doc.getChild(NodeType.TABLE, 0, true);
        for (int i = 0; i < items.size(); i++) {
            Row clonedRow = (Row) table.getRows().get(3 + i).deepClone(true);
            for (int j = 0; j < clonedRow.getCells().getCount(); j++) {
                Node obj = table.getRows().get(3 + i).getChild(NodeType.STRUCTURED_DOCUMENT_TAG, j, true);
                StructuredDocumentTag sdt = (StructuredDocumentTag) obj;

                sdt.setTag(sdt.getTag().replace("[]", "[" + i + "]"));
                sdt.setTitle(sdt.getTitle().replace("[]", "[" + i + "]"));

            }
            if (i != items.size() - 1) {
                table.getRows().insert(4 + i, clonedRow);
            }
        }


        NodeCollection nodes = doc.getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, true);
        int k = 0;
        for (Object sdtObj : nodes) {
            StructuredDocumentTag sdt = (StructuredDocumentTag) sdtObj;
            String tag = sdt.getTag();
            if (escapeTagList.contains(tag)) {
                continue;
            }
            if (sdt.getSdtType() == SdtType.RICH_TEXT) {
                if (tag.contains("tempVoucherNumber") && sdt.getChildNodes().getCount() > 0) {
                    String tempVoucherNumber = objectMapper.readTree(JsonUtil.getJson(map.get("PrintVoucherDTO"))).get("tempVoucherNumber").asText();
                    Run run = ((Run) sdt.getChild(NodeType.RUN, 0, true));
                    run.setText(tempVoucherNumber);

                } else if (tag.contains("serialNumber") && sdt.getChildNodes().getCount() > 0) {
                    String serialNumber = objectMapper.readTree(JsonUtil.getJson(map.get("PrintVoucherDTO"))).get("tempVoucherNumber").asText();
                    Run run = ((Run) sdt.getChild(NodeType.RUN, 0, true));
                    run.setText(serialNumber);
                } else if (tag.contains("date") && sdt.getChildNodes().getCount() > 0) {
                    String date = objectMapper.readTree(JsonUtil.getJson(map.get("PrintVoucherDTO"))).get("date").asText();
                    Run run = ((Run) sdt.getChild(NodeType.RUN, 0, true));
                    run.setText(date);
                } else if (tag.contains("voucherDescription") && sdt.getChildNodes().getCount() > 0) {
                    String desc = objectMapper.readTree(JsonUtil.getJson(map.get("PrintVoucherDTO"))).get("voucherDescription").asText();
                    Run run = ((Run) sdt.getChild(NodeType.RUN, 0, true));
                    run.setText(desc);
                } else if (tag.contains("baseDebitAmountSum") && sdt.getChildNodes().getCount() > 0) {
                    String baseDebitSum = objectMapper.readTree(JsonUtil.getJson(map.get("PrintVoucherDTO"))).get("baseDebitAmountSum").asText();
                    Run run = ((Run) sdt.getChild(NodeType.RUN, 0, true));
                    run.setText(baseDebitSum);
                } else if (tag.contains("baseCreditAmountSum") && sdt.getChildNodes().getCount() > 0) {
                    String baseCreditSum = objectMapper.readTree(JsonUtil.getJson(map.get("PrintVoucherDTO"))).get("baseCreditAmountSum").asText();
                    Run run = ((Run) sdt.getChild(NodeType.RUN, 0, true));
                    run.setText(baseCreditSum);
                } else if (tag.contains("insertingUser1") && sdt.getChildNodes().getCount() > 0) {
                    String inserting = objectMapper.readTree(JsonUtil.getJson(map.get("PrintVoucherDTO"))).get("insertingUser1").asText();
                    Run run = ((Run) sdt.getChild(NodeType.RUN, 0, true));
                    run.setText(inserting);
                } else if (tag.contains("insertingUser") && sdt.getChildNodes().getCount() > 0) {
                    String inserting = objectMapper.readTree(JsonUtil.getJson(map.get("PrintVoucherDTO"))).get("insertingUser1").asText();
                    Run run = ((Run) sdt.getChild(NodeType.RUN, 0, true));
                    run.setText(inserting);
                } else if (tag.contains("vrPersonConfirmer1") && sdt.getChildNodes().getCount() > 0) {
                    String confirmer = objectMapper.readTree(JsonUtil.getJson(map.get("PrintVoucherDTO"))).get("vrPersonConfirmer1").asText();
                    Run run = ((Run) sdt.getChild(NodeType.RUN, 0, true));
                    run.setText(confirmer);
                } else if (tag.contains("vrPersonConfirmer") && sdt.getChildNodes().getCount() > 0) {
                    String confirmer = objectMapper.readTree(JsonUtil.getJson(map.get("PrintVoucherDTO"))).get("vrPersonConfirmer1").asText();
                    Run run = ((Run) sdt.getChild(NodeType.RUN, 0, true));
                    run.setText(confirmer);
                } else {
                    if (tag.contains("]") && !escapeTagList.contains(sdt.getTag())) {
                        //int k=Integer.parseInt(tag.substring(tag.indexOf("[")+1,tag.indexOf("]")));
                        //  Run run =  (Run) new Run(doc);
                        //run.setText(items.get(k).get("rowNumber").asText().replace("null", " "));
                        NodeCollection children = sdt.getParentNode().getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, true);

                        for (Object childObj : children) {
                            StructuredDocumentTag child = (StructuredDocumentTag) childObj;
                            String childTag = child.getTag();
                            if (!escapeTagList.contains(childTag)) {
                                String ii = childTag.substring(childTag.indexOf("[") + 1, childTag.indexOf("]"));
                                int i = 0;
                                if (ii.matches("[+-]?[0-9]+")) {
                                    i = Integer.parseInt(ii);
                                }
                                //  Run run = new Run(doc);
                                if (childTag.contains("rowNumber")) {
                                    table.getRows().get(i + 3).getCells().get(0).getFirstParagraph().removeAllChildren();

                                    table.getRows().get(i + 3).getCells().get(0).getFirstParagraph().appendChild(new Run(doc, items.get(i).get("rowNumber").asText().replace("null", " ")));


                                    //    run.setText(items.get(i).get("rowNumber").asText());
                                    // child.getChildNodes().add(run);
                                    escapeTagList.add(childTag);

                                } else if (childTag.contains("accountCode")) {
                                    table.getRows().get(i + 3).getCells().get(1).getFirstParagraph().removeAllChildren();

                                    table.getRows().get(i + 3).getCells().get(1).getFirstParagraph().appendChild(new Run(doc, items.get(i).get("accountCode").asText().replace("null", " ")));

                                    escapeTagList.add(childTag);


                                } else if (childTag.contains("accountName")) {
                                    table.getRows().get(i + 3).getCells().get(2).getFirstParagraph().removeAllChildren();

                                    table.getRows().get(i + 3).getCells().get(2).getFirstParagraph().appendChild(new Run(doc, items.get(i).get("accountName").asText().replace("null", " ")));

                                    escapeTagList.add(childTag);


                                } else if (childTag.contains("description")) {
                                    table.getRows().get(i + 3).getCells().get(3).getFirstParagraph().removeAllChildren();

                                    table.getRows().get(i + 3).getCells().get(3).getFirstParagraph().appendChild(new Run(doc, items.get(i).get("description").asText().replace("null", " ")));
                                    escapeTagList.add(childTag);


                                } else if (childTag.contains("minorAmountText")) {
                                    table.getRows().get(i + 3).getCells().get(4).getFirstParagraph().removeAllChildren();

                                    table.getRows().get(i + 3).getCells().get(4).getFirstParagraph().appendChild(new Run(doc, items.get(i).get("minorAmountText").asText().replace("null", " ")));
                                    escapeTagList.add(childTag);
                                } else if (childTag.contains("debitAmountText")) {
                                    table.getRows().get(i + 3).getCells().get(5).getFirstParagraph().removeAllChildren();

                                    table.getRows().get(i + 3).getCells().get(5).getFirstParagraph().appendChild(new Run(doc, items.get(i).get("debitAmountText").asText().replace("null", " ")));
                                    escapeTagList.add(childTag);


                                } else if (childTag.contains("creditAmountText")) {
                                    table.getRows().get(i + 3).getCells().get(6).getFirstParagraph().removeAllChildren();
                                    table.getRows().get(i + 3).getCells().get(6).getFirstParagraph().appendChild(new Run(doc, items.get(i).get("creditAmountText").asText().replace("null", " ")));

                                    escapeTagList.add(childTag);

                                }

                            }

                        }

//                    }else if (tag.contains( "].accountCode") && !escapeTagList.contains(sdt.getTag())) {
//                      //  int k=Integer.parseInt(tag.substring(tag.indexOf("[")+1,tag.indexOf("]")));
//                        Run run = ((Run) sdt.getChild(NodeType.RUN, 0, true));
//                            run.setText(items.get(k).get("accountCode").asText().replace("null", " "));
//                            escapeTagList.add(sdt.getTag());
//                        }
                    }
                }

            } else if (sdt.getSdtType() == SdtType.PICTURE) {
                if (sdt.getTag().equals("Producer.Sign")) {
                    Shape shape = (Shape) sdt.getChild(NodeType.SHAPE, 0, true);
                    String base64encodedstring = map.get("Producer").replace("\"", "");
                    if (base64encodedstring != null && base64encodedstring.trim().length() > 0) {
                        byte[] bytes = Base64.getDecoder().decode(base64encodedstring);
                        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
                        // FileOutputStream fileOuputStream = new FileOutputStream(path);
                        //fileOuputStream.write(bytes);
                        shape.getImageData().setImage(inputStream);
                    }
                } else if (sdt.getTag().equals("Confirmer.Sign")) {
                    Shape shape = (Shape) sdt.getChild(NodeType.SHAPE, 0, true);
                    String base64encodedstring = map.get("Confirmer").replace("\"", "");
                    if (base64encodedstring != null && base64encodedstring.trim().length() > 0) {
                        byte[] bytes = Base64.getDecoder().decode(base64encodedstring);
                        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
                        // FileOutputStream fileOuputStream = new FileOutputStream(path);
                        //fileOuputStream.write(bytes);
                        shape.getImageData().setImage(inputStream);
                    }
                }
            } else if (sdt.getTag().equals("Approver.Sign")) {
                Shape shape = (Shape) sdt.getChild(NodeType.SHAPE, 0, true);
                String base64encodedstring = map.get("Approver").replace("\"", "");
                if (base64encodedstring != null && base64encodedstring.trim().length() > 0) {
                    byte[] bytes = Base64.getDecoder().decode(base64encodedstring);
                    ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
                    // FileOutputStream fileOuputStream = new FileOutputStream(path);
                    //fileOuputStream.write(bytes);
                    shape.getImageData().setImage(inputStream);
                }
            }


            escapeTagList.add(tag);

        }
        doc.save(file.getAbsolutePath());//.replace("docx","pdf"));
        String keyFile = DaivaServiceFactory.getEngineService().upload(file);
        // DaivaServiceFactory.getEngineService().persistFile(keyFile);
        file.delete();
        return DaivaServiceFactory.getEngineService().convertFile(keyFile);
    }

}
