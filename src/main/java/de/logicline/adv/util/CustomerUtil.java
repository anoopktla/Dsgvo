package de.logicline.adv.util;

import de.logicline.adv.model.dao.AdvDao;
import de.logicline.adv.model.dao.CustomerDao;
import de.logicline.adv.model.dao.DataCategoryDao;
import org.apache.commons.io.IOUtils;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustomerUtil {

    //TODO for testing purpose only
    public static CustomerDao createDummyCustomer(String id,PdfUtil pdfUtil) throws Exception{

        CustomerDao customerDao = new CustomerDao();
        customerDao.setSalutation("Mr");
        customerDao.setFirstName("Bibin");
        customerDao.setLastName("Paul");
        customerDao.setPosition("Vice president");
        customerDao.setEmailAddress(id);
        customerDao.setPhoneNumber(944701887L);
        customerDao.setCompanyName("LogicLine");
        customerDao.setStreet("500 W madison street");
        customerDao.setBuildingNumber("501");
        customerDao.setAddressLine2("Chicago");
        customerDao.setZipCode("ec1a1hq");
        customerDao.setCity("Chicago");
        customerDao.setCountry("USA");
        customerDao.setCc("anoop.krishnapillai@logicline.de");
        customerDao.setBcc("anoop.krishnapillai@heidelsoft.de");
        customerDao.setEmailTemplate("Test email body");
        AdvDao advDao = new AdvDao();
        advDao.setValidFrom(new Date());
        DataCategoryDao dataCategoryDao = new DataCategoryDao();
        dataCategoryDao.setCategoryOfData("Non sensitive");
        dataCategoryDao.setCategoryOfSubjects("category of subjects");
        dataCategoryDao.setPurposeOfCollection("To investigate into the TPMS system");
        List<DataCategoryDao> dataCategoryDaos = new ArrayList<>();
        dataCategoryDaos.add(dataCategoryDao);
        DataCategoryDao dataCategoryDao2 = new DataCategoryDao();
        dataCategoryDao2.setCategoryOfData("Non PII");
        dataCategoryDao2.setCategoryOfSubjects("category of subjects2");
        dataCategoryDao2.setPurposeOfCollection("To troubleshoot the broken report generation");
        dataCategoryDaos.add(dataCategoryDao2);

        advDao.setDataCategoryDao(dataCategoryDaos);
        //advDao.setAdvInPdfFormat( IOUtils.toByteArray(pdfUtil.createPdf(customerDao)));
        List<AdvDao> advDaos = new ArrayList<>();
        advDaos.add(advDao);
        customerDao.setAdvDao(advDaos);
        //advDao.set
        return customerDao;
    }
}
