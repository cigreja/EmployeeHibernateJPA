
package com.cigreja.employeewebsite.controllers;

import com.cigreja.employeewebsite.entities.Address;
import com.cigreja.employeewebsite.entities.Employee;
import com.cigreja.employeewebsite.data.repositories.AddressDAO;
import com.cigreja.employeewebsite.data.repositories.EmployeeDAO;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.servlet.ModelAndView;

/**
 * AddController
 * @author Carlos Igreja
 * @since  Feb 21, 2016
 */
@Controller
@RequestMapping(value = "/Add")
public class AddController {
    
    @Autowired
    EmployeeDAO employeeDAO;

    @Autowired
    AddressDAO addressDAO;

    @RequestMapping(method = POST)
    public ModelAndView add(HttpServletRequest request){
        
        HashMap<String,Object> model = new HashMap<>();
        String view = "home";
            
        // get posted information
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        Address address = new Address(request.getParameter("address"));
        
        // check if user is already in the database
        Employee employee = employeeDAO.getEmployee(firstName,lastName);
        if(employee == null){

            // create new employee
            employee = new Employee(firstName, lastName);
            employeeDAO.persist(employee);
            addressDAO.persist(address);
            employee.getAddresses().add(address);
            employeeDAO.merge(employee);
            address.getEmployees().add(employee);
            addressDAO.merge(address);
        }
        else{
            List<Address> addresses = addressDAO.getAddresses(employee);
            if(!addressDAO.containsAddress(addresses,address)){

                employee = employeeDAO.merge(employee);
                addressDAO.persist(address);

                employee.getAddresses().add(address);
                employeeDAO.merge(employee);

                address.getEmployees().add(employee);
                addressDAO.merge(address);
            }
            else{
                // display error employee address already exists
                model.put("addErrMsg", "Employee address already exists");
            }
        }

        return new ModelAndView(view,model);
    }
}
