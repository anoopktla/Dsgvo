package de.logicline.adv.service;

import de.logicline.adv.model.Customer;
import de.logicline.adv.util.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    EmailUtil emailUtil;

    public void sendEmail(Customer customer){
        //TODO hardcoded values

        emailUtil.sendEmail(customer.getToEmail(),"subject","body",customer);


    }
}
