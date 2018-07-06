package de.logicline.dsgvo.service;

import de.logicline.dsgvo.dao.CustomerRepository;
import de.logicline.dsgvo.model.Customer;
import de.logicline.dsgvo.util.PdfUtil;
import org.apache.commons.io.IOUtils;
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

    public Customer addOrModifyCustomer(Customer customer){

        try {

            //TODO for phase 1, we dont have user login feature, so just taking first element to create pdf
            customer.getAdv().get(0).setPdfDocument(IOUtils.toByteArray(pdfUtil.createPdf(customer)));
            emailService.sendEmail(customer);

        }

        catch (Exception e){
            e.printStackTrace();

        }

        return customerRepository.save(customer);
    }

    public  Iterable<Customer> getAllCustomers(){

        return customerRepository.findAll();
    }
}
