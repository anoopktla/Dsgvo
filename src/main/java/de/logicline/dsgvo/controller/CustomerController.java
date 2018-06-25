package de.logicline.dsgvo.controller;

import de.logicline.dsgvo.dao.CustomerRepository;
import de.logicline.dsgvo.model.Adv;
import de.logicline.dsgvo.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class CustomerController {

    @Autowired
    CustomerRepository customerRepository;

   /* @PostMapping("/customers")*/
    public Customer addCustomer(@Valid @RequestBody Customer customer){

        Adv adv = customer.getAdv().get(0);
       // adv.setId(customer.getId());
        return customerRepository.save(customer);

    }
}
