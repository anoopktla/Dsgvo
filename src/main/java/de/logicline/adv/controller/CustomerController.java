package de.logicline.adv.controller;

import de.logicline.adv.model.Customer;
import de.logicline.adv.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/adv-service/v1")
public class CustomerController {



    @Autowired
    CustomerService customerService;

    @PostMapping("/customers")
    public Customer addCustomer(@Valid @RequestBody Customer customer){
        //TODO server side validation

       return customerService.addOrModifyCustomer(customer);

    }
    @GetMapping("/customers")
    public Iterable<Customer> getCustomers(){

        return customerService.getAllCustomers();

    }
}
