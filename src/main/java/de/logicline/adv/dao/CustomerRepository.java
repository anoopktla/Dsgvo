package de.logicline.adv.dao;

import de.logicline.adv.model.dao.CustomerDao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(collectionResourceRel = "customer", path = "customers")
public interface CustomerRepository extends PagingAndSortingRepository<CustomerDao, Long> {


    /**
     * Method that returns a client with only its name doing a search by the passed id parameter. * * @param id * @return client of the id passed as parameter.
     */
    @Query("SELECT c.firstName FROM CustomerDao c where c.id = :id")
    CustomerDao findCustomerById(@Param("id") Long id);

    @Query("SELECT c FROM CustomerDao c where c.emailAddress = :emailId")
    CustomerDao findCustomerByEmailId(@Param("emailId") String emailId);




}


