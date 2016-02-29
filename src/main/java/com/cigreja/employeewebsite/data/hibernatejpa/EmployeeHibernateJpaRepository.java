
package com.cigreja.employeewebsite.data.hibernatejpa;

import com.cigreja.employeewebsite.business.Address;
import com.cigreja.employeewebsite.business.Employee;
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
public class EmployeeHibernateJpaRepository {
    
    @PersistenceContext
    EntityManager em;
    
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
    
    public void save(Employee employee, Address address){
        em.persist(employee);
        em.persist(address);
    }
   
    public boolean containsAddress(Employee employee, Address address){
        
        List<Address> addresses;
        addresses = employee.getAddresses();
            
        for(Address a : addresses){
            if(address.getAddress().equals(a.getAddress())){
                return true;
            }
        }
        return false;
    }
}
