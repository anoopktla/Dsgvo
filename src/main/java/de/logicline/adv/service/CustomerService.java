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

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

    public Customer addOrModifyCustomer(Customer customer) {



        try {
            /*This is done because the front-end team requested a different model structure and Orika takes care
             of mapping back & forth conversions*/
            CustomerDao customerDaoToBeSaved = mapperFacade.map(customer, CustomerDao.class);

            //TODO for phase 1, we don't have user login feature, so just taking first adv from list to create pdf
            LOGGER.info("customerDao ",customerDaoToBeSaved.toString());
            LOGGER.info("adv size in customerDao is {} ",customerDaoToBeSaved.getAdvDao().size());
            byte[] pdfInByteFormat = IOUtils.toByteArray(pdfUtil.createPdf(customerDaoToBeSaved));
            LOGGER.info("size of pdf in bytes is {}",pdfInByteFormat.length);
            if (pdfInByteFormat.length == 0) {
                LOGGER.error("error while rendering adv in pdf format");
                return null;
            }

            CustomerDao customerFromDb = customerRepository.findCustomerByEmailId(customerDaoToBeSaved.getEmailAddress());
            if(customerFromDb != null){
                //adding adv
                List<AdvDao> advDaoList = customerFromDb.getAdvDao();
                AdvDao newAdvDao = customerDaoToBeSaved.getAdvDao().get(0);
                newAdvDao.setAdvInPdfFormat(pdfInByteFormat);
                advDaoList.add(newAdvDao);
                //company datails
                customerFromDb.setCountry(customer.getCompanyInfo().getCountry());
                customerFromDb.setCity(customer.getCompanyInfo().getCity());
                customerFromDb.setCompanyName(customer.getCompanyInfo().getCompanyName());
                customerFromDb.setZipCode(customer.getCompanyInfo().getZipCode());
                customerFromDb.setAddressLine2(customer.getCompanyInfo().getAddressLine2());
                customerFromDb.setBuildingNumber(customer.getCompanyInfo().getBuildingNumber());
                customerFromDb.setStreet(customer.getCompanyInfo().getStreet());
                //personal details
                customerFromDb.setPhoneNumber(customer.getPersonDetails().getPhoneNumber());
                customerFromDb.setEmailAddress(customer.getPersonDetails().getEmail());
                customerFromDb.setPosition(customer.getPersonDetails().getPosition());
                customerFromDb.setLastName(customer.getPersonDetails().getLastName());
                customerFromDb.setFirstName(customer.getPersonDetails().getFirstName());
                customerFromDb.setSalutation(customer.getPersonDetails().getSalutation());
                //email details
                customerFromDb.setCc(customer.getEmailDetails().getCc());
                customerFromDb.setBcc(customer.getEmailDetails().getBcc());
                customerFromDb.setEmailTemplate(customer.getEmailDetails().getEmailTemplate());

                emailService.sendEmail(customerFromDb);
                return mapperFacade.map(customerRepository.save(customerFromDb), Customer.class);
            }

           else {

                customerDaoToBeSaved.getAdvDao().get(0).setAdvInPdfFormat(pdfInByteFormat);
                emailService.sendEmail(customerDaoToBeSaved);
                return mapperFacade.map(customerRepository.save(customerDaoToBeSaved), Customer.class);
            }
        } catch (Exception e) {
            LOGGER.error("error while processing customer details and adv", e);

        }

        return null;
    }

    public Iterable<Customer> getAllCustomers() {


        return mapperFacade.mapAsList(customerRepository.findAll(), Customer.class);
    }


}
