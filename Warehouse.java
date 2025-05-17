import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Warehouse extends Entity {
    @JsonProperty("cells")
    private List<WarehouseCell> cells;
    @JsonProperty("employees")
    private List<Employee> employees;
    @JsonProperty("mainEmployee")
    private Employee mainEmployee;

    public Warehouse(){
        this.cells = new ArrayList<>();
        this.employees = new ArrayList<>();
    }

    public Warehouse(int id, String name){
        super(id,name);
        this.cells = new ArrayList<>();
        this.employees = new ArrayList<>();
        this.mainEmployee = null;
    }

    public void addEmployee(Employee employee){employees.add(employee);}
    public void addCell(WarehouseCell cell){cells.add(cell);}

    public void fireEmployee(Employee employee){
        if (mainEmployee.equals(employee)){mainEmployee = null;}
        employees.remove(employee);
    }

    public List<WarehouseCell> getCells(){return cells;}
    public List<Employee> getEmployees(){return employees;}
    public Employee getMainEmployee(){return mainEmployee;}

    public void setMainEmployee(Employee employee){mainEmployee = employee;}
    public void setEmployees(List<Employee> employees){this.employees = employees;}
    public void setCells(List<WarehouseCell> cells){this.cells = cells;}
}
