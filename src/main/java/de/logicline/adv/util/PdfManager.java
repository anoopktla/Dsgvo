package de.logicline.adv.util;

import be.quodlibet.boxable.utils.PDStreamUtils;
import de.logicline.adv.model.dao.AdvDao;
import de.logicline.adv.model.dao.CustomerDao;
import de.logicline.adv.model.dao.DataCategoryDao;
import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import static de.logicline.adv.constants.Constants.*;


import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
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
    private static final PDType1Font FONT_HELVETICA = PDType1Font.HELVETICA;
    private static final PDType1Font FONT_BOLD = PDType1Font.HELVETICA_BOLD;
    private static final DateFormat DATE_FORMAT = DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.GERMAN);
    private String LINE_SEPARATOR = System.getProperty("line.separator");
    private static final Logger LOGGER =  LoggerFactory.getLogger(PdfManager .class);
    private static final String CATEGORY_OF_DATA = "categoryOfData";
    private static final String CATEGORY_OF_SUBJECTS = "categoryOfSubjects";
    private static final String PURPOSE_OF_COLLECTION = "purposeOfCollection";

    @Autowired
    public PdfManager(MessageSource messageSource, ResourceLoader resourceLoader) {
        this.messageSource = messageSource;
        this.resourceLoader = resourceLoader;
    }
    /*created acroforms in the pdf template /resources/pdf/Template_ADV_DE.pdf using https://www.pdfescape.com,good
    if we have adobe dc subscription, added key value into a map, set the value in the corresponding form fields,
    acroform element name is the key, and value will be set as the display string.For the last page we are adding
    strings dynamically, hard coded positions and all.Can be generalised ?
      */
    public byte[] generatePdf(CustomerDao customerDao) throws Exception {
        Resource resource = resourceLoader.getResource(PATH_PREFIX + FILE_SEPARATOR + FILE_NAME);
        InputStream fileAsInputStream = resource.getInputStream();
        PDDocument advAsPdfDocument = PDDocument.load(fileAsInputStream);
        fileAsInputStream.close();
        float  fontSize = 8.0f;
        //todo need to get locale from localecontextholder once other languages are ready
        Locale locale = Locale.GERMAN;
        AdvDao advDao = customerDao.getAdvDao().get(0);
        List<PDField> fields = advAsPdfDocument.getDocumentCatalog().getAcroForm().getFields();
        Map<String, String> acroFormFieldmap = new HashMap<>();
        Map<String, String> aggregatedStringMaps = getDataCategoryDetails(customerDao);
        acroFormFieldmap.put(NAME, getNameAsString(customerDao));
        acroFormFieldmap.put(NAME1, getNameAsString(customerDao));
        acroFormFieldmap.put(ADDRESS,getAddressAsString(customerDao));
        acroFormFieldmap.put(DATE,getFormattedDate(advDao.getValidFrom()));
        acroFormFieldmap.put(DATE1, getFormattedDate(advDao.getValidFrom()));
        acroFormFieldmap.put(DATE11,getFormattedDate(advDao.getValidFrom()));
        acroFormFieldmap.put(DESIGNATION,customerDao.getPosition());
        acroFormFieldmap.put(DESIGNATION1,"Logicline");
        acroFormFieldmap.put(TEXT_DESCRIPTION1,aggregatedStringMaps.get(CATEGORY_OF_DATA));
        acroFormFieldmap.put(TEXT_DESCRIPTION2,aggregatedStringMaps.get(CATEGORY_OF_SUBJECTS));
        acroFormFieldmap.put(TEXT_DESCRIPTION3,aggregatedStringMaps.get(PURPOSE_OF_COLLECTION));
        acroFormFieldmap.put(NAME_OF_COMPANY,"Logicline GmbH");
        acroFormFieldmap.put(ADDRESS_OF_COMPANY,"Planiestraße 10,71063, Sindelfingen, Germany");
        acroFormFieldmap.put(NAME11,"Logicline GmbH");
        acroFormFieldmap.put(ADDRESS_OF_CUSTOMER1,getAddressAsString(customerDao));
        acroFormFieldmap.put(PHONE_NUMBER_OF_CUSTOMER,"Tel. "+customerDao.getPhoneNumber().toString());
        acroFormFieldmap.put(ADDRESS_OF_COMPANY2,"Planiestraße 10,71063, Sindelfingen, Germany");
        acroFormFieldmap.put(PHONE_NUMBER_OF_COMPANY2,"Tel. +49 7031 611 77 0");
        acroFormFieldmap.put("id","1234");
        acroFormFieldmap.put(DATE_IN_PARAGRAPH,getFormattedDate(advDao.getValidFrom()));

        for (PDField field : fields) {
            for (Map.Entry<String, String> entry : acroFormFieldmap.entrySet()) {
                if (entry.getKey().equals(field.getFullyQualifiedName())) {
                    field.setValue(entry.getValue());
                    field.setReadOnly(true);
                }
            }
        }
        //to add text to the last page
        PDPage lastPage = advAsPdfDocument.getPage(6);
        // deprecated method used for appending to existing page.Else we will have to add header & footer by code
        PDPageContentStream contentStream = new PDPageContentStream(advAsPdfDocument, lastPage,true,true);
        PDStreamUtils.write(contentStream, messageSource.getMessage(CONFIDENTIALITY,null,locale), FONT_BOLD,
                fontSize, 55, 710, Color.BLACK);
         int y = 690;
         AdvDao adv = advDao;
        if(adv.isPhysicalAccess()){
            PDStreamUtils.write(contentStream, messageSource.getMessage(ACCESS_CONTROL,null,locale), FONT_HELVETICA,
                    fontSize, 60, y, Color.BLACK);
            y = getYPosition(y);
            PDStreamUtils.write(contentStream,messageSource.getMessage(UN_AUTHORIZED,null,locale), FONT_HELVETICA,
                    fontSize, 65, y, Color.BLACK);

            y = getYPosition(y);;
            PDStreamUtils.write(contentStream,advDao.getPhysicalAccessControl() , FONT_HELVETICA,
                    fontSize, 65, y, Color.BLACK);
            y = getYPosition(y);;
        }
        if (adv.isLogicalAccess()){
            PDStreamUtils.write(contentStream, messageSource.getMessage(ACCESS_CONTROL2,null,locale), FONT_HELVETICA,
                    fontSize, 60, y, Color.BLACK);
            y = getYPosition(y);

            PDStreamUtils.write(contentStream,
                    messageSource.getMessage(UN_AUTHORIZED2,null,locale), FONT_HELVETICA,
                    fontSize, 65, y, Color.BLACK);
            y = getYPosition(y);

            PDStreamUtils.write(contentStream, messageSource.getMessage(TWO_FACTOR,
                    null,locale), FONT_HELVETICA,fontSize, 65, y, Color.BLACK);
            y = getYPosition(y);
            PDStreamUtils.write(contentStream, advDao.getLogicalAccessControl()
                            , FONT_HELVETICA,
                    fontSize, 65, y, Color.BLACK);
            y = getYPosition(y);

        }
        if (adv.isDataAccess()){
            PDStreamUtils.write(contentStream, messageSource.getMessage(ACCESS_CONTROL3,
                    null,locale), FONT_HELVETICA, fontSize, 60, y, Color.BLACK);
            y = getYPosition(y);
            PDStreamUtils.write(contentStream, messageSource.getMessage(UN_AUTHORIZED3,
                    null,locale) , FONT_HELVETICA,fontSize, 65, y, Color.BLACK);
            y = getYPosition(y);

            PDStreamUtils.write(contentStream, messageSource.getMessage(AUTHORIZATION_CONCEPTS,
                    null,locale), FONT_HELVETICA, fontSize, 65, y, Color.BLACK);
            y = getYPosition(y);
            PDStreamUtils.write(contentStream, advDao.getDataAccessControl(), FONT_HELVETICA,
                    fontSize, 65, y, Color.BLACK);
            y = getYPosition(y);

        }
        if (adv.isSeparation()){
            PDStreamUtils.write(contentStream,  messageSource.getMessage(SEPARATION_CONTROL,null,
                    locale), FONT_HELVETICA, fontSize, 60, y, Color.BLACK);
            y = getYPosition(y);
            PDStreamUtils.write(contentStream,  messageSource.getMessage(SEPARATE,null,
                    locale), FONT_HELVETICA, fontSize, 65, y, Color.BLACK);
            y = getYPosition(y);
            PDStreamUtils.write(contentStream, messageSource.getMessage(MULTI_TENEANCY,null,
                    locale), FONT_HELVETICA, fontSize, 65, y, Color.BLACK);
            y = getYPosition(y);
            PDStreamUtils.write(contentStream, advDao.getSeparationControl(),FONT_HELVETICA,
                    fontSize, 65, y, Color.BLACK);
            y = getYPosition(y);

        }
        if (adv.isControlOfProcessing()){
            PDStreamUtils.write(contentStream, messageSource.getMessage(PSEUDONYMISIERUNG,null,
                    locale), FONT_HELVETICA, fontSize, 60, y, Color.BLACK);
            y = getYPosition(y);
            PDStreamUtils.write(contentStream,

                    messageSource.getMessage(PROCESSING,null, locale), FONT_HELVETICA,
                    fontSize, 65, y, Color.BLACK);
            y = getYPosition(y);
            PDStreamUtils.write(contentStream,messageSource.getMessage(INFORMATION,null,
                    locale) , FONT_HELVETICA, fontSize, 65, y, Color.BLACK);
            y = getYPosition(y);

            PDStreamUtils.write(contentStream,messageSource.getMessage(ADDITIONAL,null,
                    locale) , FONT_HELVETICA,  fontSize, 65, y, Color.BLACK);
            y = getYPosition(y);

            PDStreamUtils.write(contentStream,messageSource.getMessage(ORGANIZATIONAL,null,
                    locale), FONT_HELVETICA, fontSize, 65, y, Color.BLACK);
            y = getYPosition(y);

            PDStreamUtils.write(contentStream,advDao.getControlOfProcessing(), FONT_HELVETICA,
                    fontSize, 65, y, Color.BLACK);
            y = getYPosition(y);;
        }
        PDStreamUtils.write(contentStream, messageSource.getMessage(INTEGRITY,null,
                locale), FONT_BOLD, fontSize, 55, y, Color.BLACK);
        y = getYPosition(y);
        if (adv.isDataTransfer()){
            PDStreamUtils.write(contentStream, messageSource.getMessage(RELAY_CONTROL,null,
                    locale), FONT_HELVETICA, fontSize, 60, y, Color.BLACK);
            y = getYPosition(y);
            PDStreamUtils.write(contentStream, messageSource.getMessage(READ,null,
                    locale), FONT_HELVETICA, fontSize, 65, y, Color.BLACK);
            y = getYPosition(y);
            PDStreamUtils.write(contentStream, messageSource.getMessage(TRANSPORT,null,
                    locale), FONT_HELVETICA, fontSize, 65, y, Color.BLACK);
            y = getYPosition(y);

            PDStreamUtils.write(contentStream, advDao.getSeparationControl(), FONT_HELVETICA,
                    fontSize, 65, y, Color.BLACK);
            y = getYPosition(y);
        }
        if (adv.isDataEntry()){
            PDStreamUtils.write(contentStream, messageSource.getMessage(ENTRY_CONTROL,null,
                    locale), FONT_HELVETICA, fontSize, 60, y, Color.BLACK);
            y = getYPosition(y);
            PDStreamUtils.write(contentStream,  messageSource.getMessage(STATEMENT,null,
                    locale), FONT_HELVETICA, fontSize, 65, y, Color.BLACK);
            y = getYPosition(y);
            PDStreamUtils.write(contentStream, messageSource.getMessage(CHANGED,null,
                    locale), FONT_HELVETICA, fontSize, 65, y, Color.BLACK);
            y = getYPosition(y);
            PDStreamUtils.write(contentStream, advDao.getDataEntryControl(), FONT_HELVETICA,
                    fontSize, 65, y, Color.BLACK);
            y = getYPosition(y);
        }
        PDStreamUtils.write(contentStream, messageSource.getMessage(AVAILABILITY,null,
                locale), FONT_BOLD, fontSize, 55, y, Color.BLACK);
        y = getYPosition(y);
        if (adv.isAvailability()){
            PDStreamUtils.write(contentStream, messageSource.getMessage(AVAILABILITY_CONTROL,null,
                    locale), FONT_HELVETICA, fontSize, 60, y, Color.BLACK);
            y = getYPosition(y);
            PDStreamUtils.write(contentStream, messageSource.getMessage(PROTECTION,null,
                    locale), FONT_HELVETICA, fontSize, 65, y, Color.BLACK);
            y = getYPosition(y);
            PDStreamUtils.write(contentStream,  messageSource.getMessage(ONSITE,null,
                    locale), FONT_HELVETICA, fontSize, 65, y, Color.BLACK);
            y = getYPosition(y);
            PDStreamUtils.write(contentStream, messageSource.getMessage(EMERGENCY_PLANS,null,
                    locale), FONT_HELVETICA, fontSize, 65, y, Color.BLACK);
            y = getYPosition(y);
            PDStreamUtils.write(contentStream, advDao.getAvailabilityControl(),FONT_HELVETICA,
                    fontSize, 65, y, Color.BLACK);
            y = getYPosition(y);
        }
        PDStreamUtils.write(contentStream, messageSource.getMessage(RASCHE,null,
                locale), FONT_HELVETICA, fontSize, 60, y, Color.BLACK);
        y = getYPosition(y);
        PDStreamUtils.write(contentStream, messageSource.getMessage(PROCESS,null,
                locale), FONT_BOLD, fontSize, 55, y, Color.BLACK);
        y = getYPosition(y);

        PDStreamUtils.write(contentStream, messageSource.getMessage(DATA_PROTECTION,null,
                locale), FONT_HELVETICA, fontSize, 60, y, Color.BLACK);
        y = getYPosition(y);
        PDStreamUtils.write(contentStream, messageSource.getMessage(INCIDENT_RESPONSE,null,
                locale), FONT_HELVETICA, fontSize, 60, y, Color.BLACK);
        y = getYPosition(y);
        PDStreamUtils.write(contentStream,  messageSource.getMessage(PRIVACY_FRIENDLY,null,
                locale), FONT_HELVETICA, fontSize, 60, y, Color.BLACK);
        //y = getYPosition(y);
        contentStream.close();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        advAsPdfDocument.save(out);
        advAsPdfDocument.close();
        fileAsInputStream.close();
        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        byte []  result  = IOUtils.toByteArray(in);
        LOGGER.debug("returning pdf as byte array of size {}",result.length);
        advAsPdfDocument.close();
        return result;
    }
    private int getYPosition(int y){
        return y - 15;
    }
    private Map<String,String> getDataCategoryDetails(CustomerDao customerDao){

        Map<String,String> mappings = new HashMap<>();
        AdvDao advDao = customerDao.getAdvDao().get(0);
        String categoryOfData = advDao.getDataCategoryDao().stream()
                .map(DataCategoryDao::getCategoryOfData).collect(Collectors.joining(LINE_SEPARATOR));
        String categoryOfSubjects = advDao.getDataCategoryDao().stream()
                .map(DataCategoryDao::getCategoryOfSubjects).collect(Collectors.joining(LINE_SEPARATOR));
        String purposeOfCollection = advDao.getDataCategoryDao().stream()
                .map(DataCategoryDao::getPurposeOfCollection).collect(Collectors.joining(LINE_SEPARATOR));
        mappings.put(CATEGORY_OF_DATA,categoryOfData);
        mappings.put(CATEGORY_OF_SUBJECTS,categoryOfSubjects);
        mappings.put(PURPOSE_OF_COLLECTION,purposeOfCollection);
        return mappings;
    }

    private String getFormattedDate(Date date){
        return DATE_FORMAT.format(date);

    }

    private String getNameAsString(CustomerDao customer){
        StringBuffer name = new StringBuffer();
        name.append(customer.getSalutation())
                .append(" ")
                .append(customer.getFirstName())
                .append(" ")
                .append(customer.getLastName());
        return name.toString();
    }

    private String getAddressAsString(CustomerDao customer){
        StringBuffer address = new StringBuffer();
        address.append(customer.getCompanyName())
                .append(", ")
                .append(customer.getStreet())
                .append(", ")
                .append(customer.getBuildingNumber())
                .append("-")
                .append(customer.getZipCode())
                .append(", ")
                .append(customer.getCity())
                .append(", ")
                .append(customer.getCountry());
        return address.toString();
    }

}
