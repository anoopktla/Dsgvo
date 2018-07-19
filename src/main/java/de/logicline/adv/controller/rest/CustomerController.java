package de.logicline.adv.controller.rest;

import de.logicline.adv.model.frontend.Customer;
import de.logicline.adv.service.CustomerService;
import io.swagger.models.HttpMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("adv-service/v1")
public class CustomerController {



    @Autowired
    CustomerService customerService;

    @PostMapping("/customers")
    public ResponseEntity<Customer> addCustomer(@Valid @RequestBody Customer customer){
        //TODO server side validation
        HttpHeaders headers = getHttpHeaders();
        ResponseEntity<Customer> response;
        headers.setContentType(MediaType.APPLICATION_JSON);
        customer = customerService.addOrModifyCustomer(customer);

        if(!(customer == null)){
            response = new  ResponseEntity<>(customer,headers,HttpStatus.CREATED);
        }
        else {
            response = new ResponseEntity<>(null ,headers,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }

    @GetMapping("/customers")
    public Iterable<Customer> getCustomers(){

        return customerService.getAllCustomers();

    }

    private  HttpHeaders getHttpHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST");
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, HttpHeaders.CONTENT_TYPE);
        headers.add(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");
        headers.add(HttpHeaders.PRAGMA, "no-cache");
        headers.add(HttpHeaders.EXPIRES, "0");
        return headers;

    }
}
