package employee;

public class Employee {
    private int EmployeeID;
    private String name;

    public Employee() {
    }

    public int getEmployeeID() {
        return EmployeeID;
    }
    public void setEmployeeID(int employeeID) {
        EmployeeID = employeeID;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
