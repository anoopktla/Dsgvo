package de.logicline.dsgvo.util;

import de.logicline.dsgvo.model.Customer;

public class CustomerUtil {
    public static Customer createDummyCustomer(){

        Customer customer = new Customer();
        customer.setFirstName("Bibin");
        customer.setLastName("Paul");
        return customer;
    }
}
