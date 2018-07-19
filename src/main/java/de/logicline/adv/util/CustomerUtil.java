package de.logicline.adv.util;

import de.logicline.adv.model.dao.AdvDao;
import de.logicline.adv.model.dao.CustomerDao;

public class CustomerUtil {

    //TODO for testing purpose only
    public static CustomerDao createDummyCustomer(){

        CustomerDao customerDao = new CustomerDao();
        customerDao.setSalutation("Mr");
        customerDao.setFirstName("Bibin");
        customerDao.setLastName("Paul");
        customerDao.setPosition("VP");
        customerDao.setEmailAddress("anoopktla@gmail.com");
        customerDao.setPhoneNumber(944701887);
        customerDao.setCompanyName("LogicLine");
        customerDao.setStreet("W madison street");
        customerDao.setBuildingNumber(500);
        customerDao.setAddressLine2("chicago");
        customerDao.setZipCode("ec1a1hq");
        customerDao.setCity("Chicago");
        customerDao.setCountry("USA");
        customerDao.setCc("anoop.krishnapillai@logicline.de");
        customerDao.setBcc("anoop.krishnapillai@heidelsoft.de");
        AdvDao advDao = new AdvDao();
        //advDao.set
        return customerDao;
    }
}
