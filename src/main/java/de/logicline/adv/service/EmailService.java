package de.logicline.adv.service;

import de.logicline.adv.model.dao.CustomerDao;
import de.logicline.adv.util.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    EmailUtil emailUtil;

    public void sendEmail(CustomerDao customerDao){
        //TODO hardcoded values

        emailUtil.sendEmail(customerDao.getToEmail(),"subject","body", customerDao);


    }
}
