import java.util.*;

public class hWarehouseManager {
    private hProductlist productList;
    private hCustomerlist customerList;
    private hEmployeelist employeeList;
    private hOrderlist orderList;
    
    public hWarehouseManager() {
        this.productList = new hProductlist();
        this.customerList = new hCustomerlist();
        this.employeeList = new hEmployeelist();
        this.orderList = new hOrderlist();
    }
    
    // Wrapper class cho ProductList
    public class ProductManager {
        public void displayAll() {
            productList.displayAllProducts();
        }
        
        public void search(String keyword) {
            List<cProduct> results = productList.searchProducts(keyword);
            System.out.println("\n=== KET QUA TIM KIEM SAN PHAM: '" + keyword + "' (" + results.size() + " ket qua) ===");
            for (cProduct product : results) {
                product.displayInfo();
            }
        }
        
        public void exportToFile(String filename) {
            productList.exportDataToFile(filename, productList.getProducts(), "id,name,price,quantity,attribute,type");
        }
        
        public void showStatistics() {
            double totalValue = productList.calculateTotalInventoryValue();
            System.out.println("\n=== THONG KE SAN PHAM ===");
            System.out.printf("Tong so san pham: %d\n", productList.getProducts().size());
            System.out.printf("Tong gia tri ton kho: %,.0f VND\n", totalValue);
        }
        
        // Phương thức riêng của ProductManager
        public void addProduct(cProduct product) {
            productList.addProduct(product);
        }
        
        public cProduct findProductById(String id) {
            return productList.findProductById(id);
        }
        
        public boolean deleteProduct(String productId) {
            return productList.deleteProduct(productId);
        }
        
        public boolean updateProduct(String productId, double newPrice, int newQuantity) {
            return productList.updateProduct(productId, newPrice, newQuantity);
        }
        
        public List<cProduct> getAllProducts() {
            return productList.getProducts();
        }
    }
    
    // Wrapper class cho CustomerList
    public class CustomerManager {
        public void displayAll() {
            customerList.displayAllCustomers();
        }
        
        public void search(String keyword) {
            List<eCustomer> results = customerList.searchCustomers(keyword);
            System.out.println("\n=== KET QUA TIM KIEM KHACH HANG: '" + keyword + "' (" + results.size() + " ket qua) ===");
            for (eCustomer customer : results) {
                customer.displayInfo();
            }
        }
        
        public void exportToFile(String filename) {
            customerList.exportDataToFile(filename, customerList.getCustomers(), "id,name,phone");
        }
        
        // Phương thức riêng của CustomerManager
        public void addCustomer(eCustomer customer) {
            customerList.addCustomer(customer);
        }
        
        public eCustomer findCustomerById(String id) {
            return customerList.findCustomerById(id);
        }
        
        public boolean deleteCustomer(String customerId) {
            return customerList.deleteCustomer(customerId);
        }
        
        public boolean updateCustomer(String customerId, String newName, String newPhone) {
            return customerList.updateCustomer(customerId, newName, newPhone);
        }
        
        public List<eCustomer> getAllCustomers() {
            return customerList.getCustomers();
        }
    }
    
    // Wrapper class cho EmployeeList
    public class EmployeeManager {
        public void displayAll() {
            employeeList.displayAllEmployees();
        }
        
        public void search(String keyword) {
            List<dEmployee> results = employeeList.searchEmployees(keyword);
            System.out.println("\n=== KET QUA TIM KIEM NHAN VIEN: '" + keyword + "' (" + results.size() + " ket qua) ===");
            for (dEmployee employee : results) {
                employee.displayInfo();
            }
        }
        
        public void exportToFile(String filename) {
            employeeList.exportDataToFile(filename, employeeList.getEmployees(), "id,name,phone,position");
        }
        
        // Phương thức riêng của EmployeeManager
        public void addEmployee(dEmployee employee) {
            employeeList.addEmployee(employee);
        }
        
        public dEmployee findEmployeeById(String id) {
            return employeeList.findEmployeeById(id);
        }
        
        public boolean deleteEmployee(String employeeId) {
            return employeeList.deleteEmployee(employeeId);
        }
        
        public boolean updateEmployee(String employeeId, String newName, String newPhone, String newPosition) {
            return employeeList.updateEmployee(employeeId, newName, newPhone, newPosition);
        }
        
        public List<dEmployee> getAllEmployees() {
            return employeeList.getEmployees();
        }
    }
    
    // Wrapper class cho OrderList
    public class OrderManager {
        public void displayAll() {
            orderList.displayAllOrders();
        }
        
        public void search(String keyword) {
            List<gOrder> results = orderList.searchOrders(keyword);
            System.out.println("\n=== KET QUA TIM KIEM DON HANG: '" + keyword + "' (" + results.size() + " ket qua) ===");
            for (gOrder order : results) {
                order.displayInfo();
            }
        }
        
        // Phương thức riêng của OrderManager
        public boolean addOrder(gOrder order) {
            return orderList.addOrder(order);
        }
        
        public gOrder findOrderById(String id) {
            return orderList.findOrderById(id);
        }
        
        public boolean updateOrder(String orderId, String newType, dEmployee newEmployee, eCustomer newCustomer) {
            return orderList.updateOrder(orderId, newType, newEmployee, newCustomer);
        }
        
        public boolean deleteOrder(String orderId) {
            return orderList.deleteOrder(orderId);
        }
        
        public boolean addProductToOrder(String orderId, cProduct product, int quantity, double unitPrice) {
            return orderList.addProductToOrder(orderId, product, quantity, unitPrice);
        }
        
        public List<gOrder> getAllOrders() {
            return orderList.getOrders();
        }
        
        public List<dEmployee> getAllEmployees() {
            return employeeList.getEmployees();
        }
        
        public List<eCustomer> getAllCustomers() {
            return customerList.getCustomers();
        }
        
        public List<cProduct> getAllProducts() {
            return productList.getProducts();
        }
    }
    
    // Getter methods
    public OrderManager getOrderManager() {
        return new OrderManager();
    }
    
    // Load dữ liệu từ file
    public void loadDataFromFiles() {
        this.productList = hDataManager.readProductsFromFile("products.txt");
        this.employeeList = hDataManager.readEmployeesFromFile("employees.txt");
        this.customerList = hDataManager.readCustomersFromFile("customers.txt");
        this.orderList = hDataManager.readOrdersFromFile("orders.txt", employeeList, customerList, productList);
    }
}