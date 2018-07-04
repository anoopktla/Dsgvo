package de.logicline.dsgvo.service;

import de.logicline.dsgvo.model.Customer;
import de.logicline.dsgvo.util.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    EmailUtil emailUtil;

    public void sendEmail(Customer customer){

        emailUtil.sendEmail(customer.getToEmail(),"subject","body");


    }
}
