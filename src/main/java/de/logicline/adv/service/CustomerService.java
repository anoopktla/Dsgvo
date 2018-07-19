package de.logicline.adv.service;

import de.logicline.adv.dao.CustomerRepository;
import de.logicline.adv.model.dao.AdvDao;
import de.logicline.adv.model.dao.CustomerDao;
import de.logicline.adv.model.frontend.Customer;
import de.logicline.adv.util.PdfUtil;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    PdfUtil pdfUtil;

    @Autowired
    MapperFacade mapperFacade;

    private  static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

    public Customer addOrModifyCustomer(Customer customer){
        //This is done because the front-end team requested a different model structure and Orika takes care
        // of mapping back & forth conversions
        CustomerDao customerDao = mapperFacade.map(customer,CustomerDao.class);

        try {

            //TODO for phase 1, we don't have user login feature, so just taking first adv from list to create pdf

            byte[] pdfInByteFormat = IOUtils.toByteArray(pdfUtil.createPdf(customerDao));
            if (pdfInByteFormat.length == 0){
                LOGGER.error("error while rendering adv in pdf format");
                return null;


            }

            emailService.sendEmail(customerDao);

        }

        catch (Exception e){
            LOGGER.error("error while processing customer details and adv",e);

        }

        return mapperFacade.map(customerRepository.save(customerDao),Customer.class);
    }

    public  Iterable<Customer> getAllCustomers(){


        return mapperFacade.mapAsList(customerRepository.findAll(),Customer.class);
    }
}
