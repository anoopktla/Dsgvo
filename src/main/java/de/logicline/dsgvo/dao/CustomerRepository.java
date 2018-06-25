package de.logicline.dsgvo.dao;

import de.logicline.dsgvo.model.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;


@RepositoryRestResource(collectionResourceRel = "customer", path = "customers")
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {


    /**
     * Method that returns a client with only its name doing a search by the passed id parameter. * * @param id * @return client of the id passed as parameter.
     */
    @Query("SELECT c.firstName FROM Customer c where c.id = :id")
    Customer findNameById(@Param("id") Long id);


}


