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

import java.security.InvalidParameterException;
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
        boolean  isUpdate = false;


        try {
            /*This is done because the front-end team requested a different model structure and Orika takes care
             of mapping back & forth conversions*/
            CustomerDao customerDaoToBeSaved = mapperFacade.map(customer, CustomerDao.class);

            //TODO for phase 1, we don't have user login feature, so just taking first adv from list to create pdf
            byte[] pdfInByteFormat = IOUtils.toByteArray(pdfUtil.createPdf(customerDaoToBeSaved));
            if (pdfInByteFormat.length == 0) {
                LOGGER.error("error while rendering adv in pdf format");
                return null;
            }

            CustomerDao customerFromDb = customerRepository.findCustomerByEmailId(customerDaoToBeSaved.getEmailAddress());
            if(customerFromDb != null){
                List<AdvDao> advDaoList = customerFromDb.getAdvDao();
                AdvDao newAdvDao = customerDaoToBeSaved.getAdvDao().get(0);
                newAdvDao.setAdvInPdfFormat(pdfInByteFormat);
                advDaoList.add(newAdvDao);
                isUpdate = true;
            }

            if (isUpdate) {
                emailService.sendEmail(customerFromDb);
                return mapperFacade.map(customerRepository.save(customerFromDb), Customer.class);


            } else {
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
