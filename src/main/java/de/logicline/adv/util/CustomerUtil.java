package de.logicline.adv.util;

import de.logicline.adv.model.dao.AdvDao;
import de.logicline.adv.model.dao.CustomerDao;
import org.apache.commons.io.IOUtils;

import java.util.ArrayList;
import java.util.List;

public class CustomerUtil {

    //TODO for testing purpose only
    public static CustomerDao createDummyCustomer(String id,PdfUtil pdfUtil) throws Exception{

        CustomerDao customerDao = new CustomerDao();
        customerDao.setSalutation("Mr");
        customerDao.setFirstName("Bibin");
        customerDao.setLastName("Paul");
        customerDao.setPosition("VP");
        customerDao.setEmailAddress(id);
        customerDao.setPhoneNumber(944701887L);
        customerDao.setCompanyName("LogicLine");
        customerDao.setStreet("W madison street");
        customerDao.setBuildingNumber("500");
        customerDao.setAddressLine2("chicago");
        customerDao.setZipCode("ec1a1hq");
        customerDao.setCity("Chicago");
        customerDao.setCountry("USA");
        customerDao.setCc("anoop.krishnapillai@logicline.de");
        customerDao.setBcc("anoop.krishnapillai@heidelsoft.de");
        customerDao.setEmailTemplate("Test email body");
        AdvDao advDao = new AdvDao();
        //advDao.setAdvInPdfFormat( IOUtils.toByteArray(pdfUtil.createPdf(customerDao)));
        List<AdvDao> advDaos = new ArrayList<>();
        advDaos.add(advDao);
        customerDao.setAdvDao(advDaos);
        //advDao.set
        return customerDao;
    }
}
