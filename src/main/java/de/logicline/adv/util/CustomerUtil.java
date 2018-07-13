package de.logicline.adv.util;

import de.logicline.adv.model.Customer;

public class CustomerUtil {

    //TODO for testing purpose only
    public static Customer createDummyCustomer(){

        Customer customer = new Customer();
        customer.setFirstName("Bibin");
        customer.setLastName("Paul");
        return customer;
    }
}
