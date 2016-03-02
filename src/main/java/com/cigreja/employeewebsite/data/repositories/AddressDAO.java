package com.cigreja.employeewebsite.data.repositories;

import com.cigreja.employeewebsite.entities.Address;
import com.cigreja.employeewebsite.entities.Employee;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * AddressDAO
 * @author Carlos Igreja
 * @since  Feb 29, 2016
 */
@Repository
@Transactional
public class AddressDAO {

    @PersistenceContext
    EntityManager em;

    public void persist(Address address){
        em.persist(address);
    }

    public Address merge(Address address){
        return em.merge(address);
    }

    public List<Address> getAddresses(Employee employee){

        int id = employee.getEmployeeID();
        Query query = em.createQuery("SELECT a " +
                "FROM Address a " +
                "JOIN a.employees e " +
                "WHERE e.employeeID =:id");
        query.setParameter("id",id);
        List<Address> addresses = query.getResultList();
        return addresses;
    }

    public boolean containsAddress(List<Address> addresses, Address address) {
        for(Address a : addresses){
            if(address.getAddress().equals(a.getAddress())){
                return true;
            }
        }
        return false;
    }
}
