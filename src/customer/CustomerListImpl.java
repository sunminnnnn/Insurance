package customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerListImpl implements CustomerList {
    // attributes
    private ArrayList<Customer> customerList;

    public CustomerListImpl() {
        this.customerList = new ArrayList<Customer>();
    }

    // getter & setter
    public ArrayList<Customer> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(ArrayList<Customer> customerList) {
        this.customerList = customerList;
    }

    public void finalize() throws Throwable {
    }

    public boolean add(Customer customer) {
        if (this.customerList.add(customer)) return true;
        else return false;
    }

    public boolean delete(int customerID) {
        for (Customer customer : this.customerList) {
            if (customer.getCustomerID() == customerID) {
                if (this.customerList.remove(customer)) return true;
                break;
            }
        }
        return false;
    }

    public Customer search(int customerID) {
        for (Customer customer : this.customerList) {
            if (customer.getCustomerID() == customerID) return customer;
        }
        return null;
    }

    public Customer search(Customer customer) {
        for (Customer scustomer : this.customerList) {
            if (scustomer.getCustomerName().equals(customer.getCustomerName()) && scustomer.getCustomerNumber().equals(customer.getCustomerNumber()))
                return scustomer;
        }
        return null;
    }

    public boolean update(Customer customer, int customerID) {
        for (Customer uCustomer : this.customerList) {
            if (uCustomer.getCustomerID() == customerID) {
                uCustomer.setCustomerName(customer.getCustomerName());
                uCustomer.setPhoneNumber(customer.getPhoneNumber());
            }
        }
        return false;
    }

    @Override
    public List<Customer> getList() {
        // TODO Auto-generated method stub
        return null;
    }
}