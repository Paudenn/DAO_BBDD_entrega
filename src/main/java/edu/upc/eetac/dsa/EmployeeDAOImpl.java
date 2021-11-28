package edu.upc.eetac.dsa;

import edu.upc.eetac.dsa.model.Employee;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;

public class EmployeeDAOImpl implements IEmployeeDAO {

    private static IEmployeeDAO instance;

    private EmployeeDAOImpl(){

    }

    public static IEmployeeDAO getInstance(){
        if(instance == null)
            instance = new EmployeeDAOImpl();
        return instance;
    }

    public void clear(){
        instance = new EmployeeDAOImpl();
    }

    //mostra informacio amb log4j
    final Logger log = Logger.getLogger(EmployeeDAOImpl.class);

    public int addEmployee(String name, String surname, double salary) {
        Session session = null;
        int employeeID = 0;
        try {
            session = FactorySession.openSession();
            Employee employee = new Employee(name, surname, salary);
            session.save(employee);
            log.info("name: " + name + "surname: " + surname); //per veure si el employee esta be

        }
        catch (Exception e) {
            log.error("Error al afegir un nou employee");
        }
        finally {
            session.close();
        }

        return employeeID;
    }


    public Employee getEmployee(int employeeID) {
        Session session = null;
        Employee employee = null;
        try {
            session = FactorySession.openSession();
            employee = (Employee)session.get(Employee.class, employeeID);
            log.info("Employee: "+ employee.getName() + " Surname: "+employee.getSurname() + " Salari: "+Double.toString(employee.getSalary()));

        }
        catch (Exception e) {
            log.error("Error al obtenir un employee");
        }
        finally {
            session.close();
        }

        return employee;
    }


    public void updateEmployee(int employeeID, String name, String surname, double salary) {
        Employee employee = this.getEmployee(employeeID);
        employee.setName(name);
        employee.setSurname(surname);
        employee.setSalary(salary);

        Session session = null;
        try {
            session = FactorySession.openSession();
            session.update(Employee.class);
        }
        catch (Exception e) {
            log.error("Error al actualitzar un employee");
        }
        finally {
            session.close();
        }
    }


    public void deleteEmployee(int employeeID) {
        Employee employee = this.getEmployee(employeeID);
        Session session = null;
        try {
            session = FactorySession.openSession();
            session.delete(Employee.class);
        }
        catch (Exception e) {
            log.error("Error al borrar un employee");
        }
        finally {
            session.close();
        }

    }


    public List<Employee> getEmployees() {
        Session session = null;
        List<Employee> employeeList=null;
        try {
            session = FactorySession.openSession();
            employeeList = session.findAll(Employee.class);
        }
        catch (Exception e) {
            log.error("Error al obtenir la llista de employee");
        }
        finally {
            session.close();
        }
        return employeeList;
    }


    public List<Employee> getEmployeeByDept(int deptID) {

        Session session = null;
        List<Employee> employeeList=null;
        try {
            session = FactorySession.openSession();

            HashMap<String, Integer> params = new HashMap<String, Integer>();
            params.put("deptID", deptID);

            employeeList = session.findAll(Employee.class, params);
        }
        catch (Exception e) {
            log.error("Error al obtenir llista employee by dept");
        }
        finally {
            session.close();
        }
        return employeeList;
    }


}
