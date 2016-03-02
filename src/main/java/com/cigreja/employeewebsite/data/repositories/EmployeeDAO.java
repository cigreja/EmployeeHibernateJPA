
package com.cigreja.employeewebsite.data.repositories;

import com.cigreja.employeewebsite.entities.Address;
import com.cigreja.employeewebsite.entities.Employee;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * EmployeeHibernateJpaRepository
 * @author Carlos Igreja
 * @since  Feb 19, 2016
 */
@Repository
@Transactional
public class EmployeeDAO {
    
    @PersistenceContext
    EntityManager em;

    public void persist(Employee employee){
        em.persist(employee);
    }

    public Employee merge(Employee employee){
        return em.merge(employee);
    }

    public Employee getEmployee(String firstName, String lastName){
        Query query = em.createQuery("from Employee "
                                         + "where FIRST_NAME =:firstName "
                                         + "and LAST_NAME =:lastName");
        query.setParameter("firstName", firstName);
        query.setParameter("lastName", lastName);
        List employees = query.getResultList();
        if (employees.isEmpty()) {
            return null;
        } else {
            return (Employee) employees.get(0);
        }
    }

    public List<Address> getAddresses(Employee employee){

        List<Address> addresses = new ArrayList<>();
        return addresses;
    }
}
