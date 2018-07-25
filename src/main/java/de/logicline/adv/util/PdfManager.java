package de.logicline.adv.util;

import de.logicline.adv.model.dao.CustomerDao;
import de.logicline.adv.model.dao.DataCategoryDao;
import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class PdfManager {
    private MessageSource messageSource;
    private ResourceLoader resourceLoader;
    private static final String FILE_SEPARATOR = System.getProperty("file.separator");
    private static final String PATH_PREFIX = "classpath:pdf";
    private static final String FILE_NAME = "Template_ADV_DE.pdf";
    private static final PDType1Font font = PDType1Font.HELVETICA;
    private static final DateFormat DATE_FORMAT = DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.GERMAN);

    @Autowired
    public PdfManager(MessageSource messageSource, ResourceLoader resourceLoader) {
        this.messageSource = messageSource;
        this.resourceLoader = resourceLoader;
    }

    public byte[] generatePdf(CustomerDao customerDao) throws Exception {
        Resource resource = resourceLoader.getResource(PATH_PREFIX + FILE_SEPARATOR + FILE_NAME);
        InputStream fileAsInputStream = resource.getInputStream();
        PDDocument advAsPdfDocument = PDDocument.load(fileAsInputStream);
        fileAsInputStream.close();
        /*PDPage firstPage = advAsPdfDocument.getPage(0);

        PDPageContentStream contentStream = new PDPageContentStream(advAsPdfDocument, firstPage,true,true);
        PDStreamUtils.write(contentStream, customerDao.getSalutation(), font,
                9.0f, 240, 470, Color.BLACK);

        PDStreamUtils.write(contentStream, customerDao.getFirstName(), font,
                9.0f, 260, 470, Color.BLACK);
        PDStreamUtils.write(contentStream, customerDao.getLastName(), font,
                9.0f, 290, 470, Color.BLACK);


        contentStream.close();
        PDPage thirdPage = advAsPdfDocument.getPage(2);
        contentStream = new PDPageContentStream(advAsPdfDocument, thirdPage,true,true);
        PDStreamUtils.write(contentStream, "123456789", font,
                9.0f, 160, 695, Color.BLACK);
        PDStreamUtils.write(contentStream, "1/1/1999", font,
                9.0f, 240, 695, Color.BLACK);
        contentStream.close(); ;
*/


        /* FontFileFinder fontFinder = new FontFileFinder();
        List<URI> fontURIs = fontFinder.find();
        for(URI uri:fontURIs){
            System.out.println(uri.toString());
        }*/
        List<PDField> fields = advAsPdfDocument.getDocumentCatalog().getAcroForm().getFields();
        Map<String, String> acroFormFiledmap = new HashMap<>();
        Map<String, String> aggregatedStringMaps = getDataCategoryDetails(customerDao);
        acroFormFiledmap.put("name", customerDao.getSalutation()+" "+customerDao.getFirstName()+" "+customerDao.getLastName());
        acroFormFiledmap.put("name1", customerDao.getSalutation()+" "+customerDao.getFirstName()+" "+customerDao.getLastName());
        acroFormFiledmap.put("address",customerDao.getCompanyName()+", "+customerDao.getStreet()+", "+customerDao.getBuildingNumber()+", "+customerDao.getCity()+", "+customerDao.getCountry());
        acroFormFiledmap.put("date111",getFormattedDate(customerDao.getAdvDao().get(0).getValidFrom()));
        acroFormFiledmap.put("date1", getFormattedDate(customerDao.getAdvDao().get(0).getValidFrom()));
        acroFormFiledmap.put("date11",getFormattedDate(customerDao.getAdvDao().get(0).getValidFrom()));
        acroFormFiledmap.put("designation",customerDao.getPosition());
        acroFormFiledmap.put("designation1",customerDao.getPosition());
        acroFormFiledmap.put("artUnd",aggregatedStringMaps.get("categoryOfData"));
        acroFormFiledmap.put("dieDat",aggregatedStringMaps.get("categoryOfSubjects"));
        acroFormFiledmap.put("dieVer",aggregatedStringMaps.get("purposeOfCollection"));
        acroFormFiledmap.put("nameOfCompany","Logicline GmbH");
        acroFormFiledmap.put("addressOfCompany","Planiestraße 10,71063, Sindelfingen, Germany");
        for (PDField field : fields) {
            for (Map.Entry<String, String> entry : acroFormFiledmap.entrySet()) {
                if (entry.getKey().equals(field.getFullyQualifiedName())) {
                    field.setValue(entry.getValue());
                    field.setReadOnly(true);
                }
            }
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        advAsPdfDocument.save(out);
        advAsPdfDocument.close();

        fileAsInputStream.close();
        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());

        byte []  result  = IOUtils.toByteArray(in);
        advAsPdfDocument.close();
        return result;
    }
    private Map<String,String> getDataCategoryDetails(CustomerDao customerDao){

        Map<String,String> mappings = new HashMap<>();
        String lineSeparator = System.getProperty("line.separator");
        String categoryOfData = customerDao.getAdvDao().get(0).getDataCategoryDao().stream().map(DataCategoryDao::getCategoryOfData).collect(Collectors.joining(lineSeparator));
        String categoryOfSubjects = customerDao.getAdvDao().get(0).getDataCategoryDao().stream().map(DataCategoryDao::getCategoryOfSubjects).collect(Collectors.joining(lineSeparator));
        String purposeOfCollection = customerDao.getAdvDao().get(0).getDataCategoryDao().stream().map(DataCategoryDao::getPurposeOfCollection).collect(Collectors.joining(lineSeparator));
        mappings.put("categoryOfData",categoryOfData);
        mappings.put("categoryOfSubjects",categoryOfSubjects);
        mappings.put("purposeOfCollection",purposeOfCollection);
        return mappings;
    }

    private String getFormattedDate(Date date){
        return DATE_FORMAT.format(date);

    }

}
