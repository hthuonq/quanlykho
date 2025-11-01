import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class hEmployeelist {
    private List<dEmployee> employees;

    public hEmployeelist(){
        this.employees= new ArrayList<>();
    }
    public hEmployeelist(List<dEmployee> employees){
        this.employees=employees;
    }
    public void addEmployee(dEmployee employee) {
        employees.add(employee);
    }
    public void displayAllEmployees() {
        System.out.println("\n=== DANH SACH NHAN VIEN (" + employees.size() + ") ===");
        for (dEmployee e : employees) {
            System.out.println(e);
        }
    }
     public dEmployee findEmployeeById(String employeeId) {
        for (dEmployee e : employees) {
            if (e.getId().equals(employeeId)) {
                return e;
            }
        }
        return null;
    }
    // TÌM KIẾM NHÂN VIÊN
    public List<dEmployee> searchEmployees(String keyword) {
        List<dEmployee> result = new ArrayList<>();
        for (dEmployee e : employees) {
            if (e.getName().toLowerCase().contains(keyword.toLowerCase()) ||
                e.getPosition().toLowerCase().contains(keyword.toLowerCase()) ||
                e.getContactInfo().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(e);
            }
        }
        return result;
    }
    // SỬA NHÂN VIÊN
    public boolean updateEmployee(String employeeId, String newName, String newPhone, String newPosition) {
        dEmployee employee = findEmployeeById(employeeId);
        if (employee != null) {
            employee.setName(newName);
            employee.setContactInfo(newPhone);
            employee.setPosition(newPosition);
            return true;
        }
        return false;
    }
    // XÓA NHÂN VIÊN
    public boolean deleteEmployee(String employeeId) {
        dEmployee employee = findEmployeeById(employeeId);
        if (employee != null) {
            employees.remove(employee);
            return true;
        }
        return false;
    }
     // Xuất dữ liệu ra file
    public void exportDataToFile(String filename, List<?> dataList, String header) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            // Ghi header nếu có
            if (header != null && !header.isEmpty()) {
                pw.println(header);
            }
            // Ghi dữ liệu
            for (Object obj : dataList) {
                pw.println(obj.toString());
            }
            System.out.println("Da xuat du lieu ra file: " + filename);
        } catch (IOException e) {
            System.out.println("Loi ghi file: " + e.getMessage());
        }
    }
    public List<dEmployee> getEmployees() { return employees; }


}
