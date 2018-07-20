package de.logicline.adv.config;

import de.logicline.adv.model.dao.AdvDao;
import de.logicline.adv.model.dao.CustomerDao;
import de.logicline.adv.model.dao.DataCategoryDao;
import de.logicline.adv.model.frontend.ContractInfo;
import de.logicline.adv.model.frontend.Customer;
import de.logicline.adv.model.frontend.CategoryDetails;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrikaConfig {

    @Bean
    public MapperFacade getMapper(){

        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.classMap(Customer.class, CustomerDao.class)
                //personal details
                .field("personDetails.salutation", "salutation")
                .field("personDetails.firstName", "firstName")
                .field("personDetails.lastName", "lastName")
                .field("personDetails.position", "position")
                .field("personDetails.email", "emailAddress")
                .field("personDetails.phoneNumber", "phoneNumber")
                //company info starts
                .field("companyInfo.companyName", "companyName")
                .field("companyInfo.street", "street")
                .field("companyInfo.no", "buildingNumber")
                .field("companyInfo.address", "addressLine2")
                .field("companyInfo.zip", "zipCode")
                .field("companyInfo.city", "city")
                .field("companyInfo.country", "country")

                .field("contractInfo", "advDao[0]")
                .field("emailDetails.cc", "cc")
                .field("emailDetails.bcc", "bcc")
                .field("emailDetails.emailTemplate", "emailTemplate")
                .byDefault()
                .register();

        mapperFactory.classMap(ContractInfo.class, AdvDao.class)
                .field("dataCategories","dataCategoryDao")
                .byDefault()
                .register();

        mapperFactory.classMap(CategoryDetails.class, DataCategoryDao.class)

                .byDefault()
                .register();


        return mapperFactory.getMapperFacade();



    }
}
