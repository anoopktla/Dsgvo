package de.logicline.adv.service;

import de.logicline.adv.controller.TestController;
import de.logicline.adv.dao.CustomerRepository;
import de.logicline.adv.model.Customer;
import de.logicline.adv.util.PdfUtil;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    PdfUtil pdfUtil;
    private  static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

    public Customer addOrModifyCustomer(Customer customer){

        try {

            //TODO for phase 1, we don't have user login feature, so just taking first adv from list to create pdf
            customer.getAdv().get(0).setAdvInPdfFormat(IOUtils.toByteArray(pdfUtil.createPdf(customer)));
            emailService.sendEmail(customer);

        }

        catch (Exception e){
            LOGGER.error("error saving customer details and adv",e);

        }

        return customerRepository.save(customer);
    }

    public  Iterable<Customer> getAllCustomers(){

        return customerRepository.findAll();
    }
}
