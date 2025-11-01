import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
public class hCustomerlist {
    private List<eCustomer> customers;

    public hCustomerlist(){
        this.customers= new ArrayList<>();
    }
    public hCustomerlist(List<eCustomer> customers){
        this.customers=customers;
    }

     public void addCustomer(eCustomer customer) {
        customers.add(customer);
    }
     public void displayAllCustomers() {
        System.out.println("\n=== DANH SACH KHACH HANG (" + customers.size() + ") ===");
        for (eCustomer c : customers) {
            System.out.println(c);
        }
    }
    public eCustomer findCustomerById(String customerId) {
        for (eCustomer c : customers) {
            if (c.getId().equals(customerId)) {
                return c;
            }
        }
        return null;
    }
    // TÌM KIẾM KHÁCH HÀNG
    public List<eCustomer> searchCustomers(String keyword) {
        List<eCustomer> result = new ArrayList<>();
        for (eCustomer c : customers) {
            if (c.getName().toLowerCase().contains(keyword.toLowerCase()) ||
                c.getPhone().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(c);
            }
        }
        return result;
    }
    // SỬA KHÁCH HÀNG
    public boolean updateCustomer(String customerId, String newName, String newPhone) {
        eCustomer customer = findCustomerById(customerId);
        if (customer != null) {
            customer.setName(newName);
            customer.setPhone(newPhone);
            return true;
        }
        return false;
    }
    // XÓA KHÁCH HÀNG
    public boolean deleteCustomer(String CustomerId) {
        eCustomer customer = findCustomerById(CustomerId);
        if (customer != null) {
            customers.remove(customer);
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
    public List<eCustomer> getCustomers() { return customers; }
}
