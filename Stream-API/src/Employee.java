
public class Employee {

	private String employeeId;
	private String employeeName;
	private String designation;
	private double Salary;
	public Employee(String employeeId, String employeeName, String designation, double salary) {
		super();
		this.employeeId = employeeId;
		this.employeeName = employeeName;
		this.designation = designation;
		Salary = salary;
	}
	public Employee() {
		
	}
	public String getEmployeeId() {
		return employeeId;
	}
	
	public String getEmployeeName() {
		return employeeName;
	}
	
	public String getDesignation() {
		return designation;
	}
	
	public double getSalary() {
		return Salary;
	}
	
	@Override
	public String toString() {
		return "Employee [employeeId=" + employeeId + ", employeeName=" + employeeName + ", designation=" + designation
				+ ", Salary=" + Salary + "]";
	}

}
