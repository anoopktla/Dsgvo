package de.logicline.dsgvo.controller;

import de.logicline.dsgvo.dao.CustomerRepository;
import de.logicline.dsgvo.model.Adv;
import de.logicline.dsgvo.model.Customer;
import de.logicline.dsgvo.util.PdfUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CustomerController {

    @Autowired
    CustomerRepository customerRepository;

    @PostMapping("/customers")
    public Customer addCustomer(@Valid @RequestBody Customer customer){

        try {
            customer.setPdfDocument(IOUtils.toByteArray(new PdfUtil().createPdf(customer)));
        }
        catch (Exception e){

        }

        return customerRepository.save(customer);

    }
    @GetMapping("/customers")
    public Iterable<Customer> getCustomers(){
        return customerRepository.findAll();
    }
}
