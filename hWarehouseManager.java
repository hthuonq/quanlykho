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
    
    // Interface cho tính đa hình - Quản lý hiển thị
    public interface IManageable {
        void displayAll();
        void search(String keyword);
        void exportToFile(String filename);
    }
    
    // Interface cho tính đa hình - Thống kê
    public interface IStatistic {
        void showStatistics();
        double calculateTotalValue();
    }
    
    // Wrapper class cho ProductList với đa hình
    public class ProductManager implements IManageable, IStatistic {
        @Override
        public void displayAll() {
            productList.displayAllProducts();
        }
        
        @Override
        public void search(String keyword) {
            List<cProduct> results = productList.searchProducts(keyword);
            System.out.println("\n=== KET QUA TIM KIEM SAN PHAM: '" + keyword + "' (" + results.size() + " ket qua) ===");
            for (cProduct product : results) {
                product.displayInfo();
            }
        }
        
        @Override
        public void exportToFile(String filename) {
            productList.exportDataToFile(filename, productList.getProducts(), "id,name,price,quantity,attribute,type");
        }
        
        @Override
        public void showStatistics() {
            double totalValue = calculateTotalValue();
            System.out.println("\n=== THONG KE SAN PHAM ===");
            System.out.printf("Tong so san pham: %d\n", productList.getProducts().size());
            System.out.printf("Tong gia tri ton kho: %,.0f VND\n", totalValue);
            
            // Thống kê theo loại sản phẩm
            Map<String, Integer> typeCount = new HashMap<>();
            Map<String, Double> typeValue = new HashMap<>();
            
            for (cProduct product : productList.getProducts()) {
                String type = product.getProductType();
                typeCount.put(type, typeCount.getOrDefault(type, 0) + 1);
                typeValue.put(type, typeValue.getOrDefault(type, 0.0) + product.calculateValue());
            }
            
            for (String type : typeCount.keySet()) {
                System.out.printf("- %s: %d san pham, Gia tri: %,.0f VND\n", 
                    type, typeCount.get(type), typeValue.get(type));
            }
        }
        
        @Override
        public double calculateTotalValue() {
            return productList.calculateTotalInventoryValue();
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
    }
    
    // Wrapper class cho CustomerList với đa hình
    public class CustomerManager implements IManageable {
        @Override
        public void displayAll() {
            customerList.displayAllCustomers();
        }
        
        @Override
        public void search(String keyword) {
            List<eCustomer> results = customerList.searchCustomers(keyword);
            System.out.println("\n=== KET QUA TIM KIEM KHACH HANG: '" + keyword + "' (" + results.size() + " ket qua) ===");
            for (eCustomer customer : results) {
                customer.displayInfo();
            }
        }
        
        @Override
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
    }
    
    // Wrapper class cho EmployeeList với đa hình
    public class EmployeeManager implements IManageable {
        @Override
        public void displayAll() {
            employeeList.displayAllEmployees();
        }
        
        @Override
        public void search(String keyword) {
            List<dEmployee> results = employeeList.searchEmployees(keyword);
            System.out.println("\n=== KET QUA TIM KIEM NHAN VIEN: '" + keyword + "' (" + results.size() + " ket qua) ===");
            for (dEmployee employee : results) {
                employee.displayInfo();
            }
        }
        
        @Override
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
    }
    
    // Wrapper class cho OrderList với đa hình
    public class OrderManager implements IManageable, IStatistic {
        @Override
        public void displayAll() {
            orderList.displayAllOrders();
        }
        
        @Override
        public void search(String keyword) {
            List<gOrder> results = orderList.searchOrders(keyword);
            System.out.println("\n=== KET QUA TIM KIEM DON HANG: '" + keyword + "' (" + results.size() + " ket qua) ===");
            for (gOrder order : results) {
                order.displayInfo();
            }
        }
        
        @Override
        public void exportToFile(String filename) {
            orderList.exportDataToFile(filename, orderList.getOrders(), "id,date,customerId,employeeId,type");
        }
        
        @Override
        public void showStatistics() {
            orderList.displayRevenueStatistics();
        }
        
        @Override
        public double calculateTotalValue() {
            double total = 0;
            for (gOrder order : orderList.getOrders()) {
                total += order.calculateTotal();
            }
            return total;
        }
        
        // Phương thức riêng của OrderManager
        public void addOrder(gOrder order) {
            orderList.getOrders().add(order);
        }
        
        public gOrder findOrderById(String id) {
            return orderList.findOrderById(id);
        }
        
        public void displayOrdersWithTotal() {
            orderList.displayAllOrdersWithTotal();
        }
        
        public void processOrder(String orderId) {
            gOrder order = findOrderById(orderId);
            if (order != null) {
                order.processOrder();
            } else {
                System.out.println("Khong tim thay don hang: " + orderId);
            }
        }

        // THÊM ĐƠN HÀNG MỚI
    public gOrder createOrder(String orderId, String type, dEmployee employee, eCustomer customer) {
        return orderList.createOrder(orderId, type, employee, customer);
    }
    
    // THÊM SẢN PHẨM VÀO ĐƠN HÀNG
    public boolean addProductToOrder(String orderId, cProduct product, int quantity, double unitPrice) {
        return orderList.addProductToOrder(orderId, product, quantity, unitPrice);
    }
    
    // XÓA SẢN PHẨM KHỎI ĐƠN HÀNG
    public boolean removeProductFromOrder(String orderId, int detailIndex) {
        return orderList.removeProductFromOrder(orderId, detailIndex);
    }
    
    // CẬP NHẬT SỐ LƯỢNG SẢN PHẨM
    public boolean updateProductQuantity(String orderId, int detailIndex, int newQuantity) {
        return orderList.updateProductQuantity(orderId, detailIndex, newQuantity);
    }
    
    // XÓA ĐƠN HÀNG
    public boolean deleteOrder(String orderId) {
        return orderList.deleteOrder(orderId);
    }
    
    // HIỂN THỊ CHI TIẾT ĐƠN HÀNG
    public void displayOrderDetails(String orderId) {
        orderList.displayOrderDetailsWithTotal(orderId);
    }
    }
    
    // Getter methods
    public ProductManager getProductManager() {
        return new ProductManager();
    }
    
    public CustomerManager getCustomerManager() {
        return new CustomerManager();
    }
    
    public EmployeeManager getEmployeeManager() {
        return new EmployeeManager();
    }
    
    public OrderManager getOrderManager() {
        return new OrderManager();
    }
    
    // Phương thức thể hiện tính đa hình
    public void demonstratePolymorphism() {
        System.out.println("=== DEMO TINH DA HINH ===");
        
        // Sử dụng interface IManageable
        List<IManageable> managers = Arrays.asList(
            getProductManager(),
            getCustomerManager(),
            getEmployeeManager(),
            getOrderManager()
        );
        
        for (IManageable manager : managers) {
            manager.displayAll();
            System.out.println();
        }
        
        // Sử dụng interface IStatistic
        List<IStatistic> statistics = Arrays.asList(
            getProductManager(),
            getOrderManager()
        );
        
        for (IStatistic statistic : statistics) {
            statistic.showStatistics();
            System.out.println();
        }
    }
    
    // Load dữ liệu từ file
    public void loadDataFromFiles() {
        this.productList = hDataManager.readProductsFromFile("products.txt");
        this.employeeList = hDataManager.readEmployeesFromFile("employees.txt");
        this.customerList = hDataManager.readCustomersFromFile("customers.txt");
        this.orderList = hDataManager.readOrdersFromFile("orders.txt", employeeList, customerList, productList);
    }
    
    // Export tất cả dữ liệu
    public void exportAllData() {
        getProductManager().exportToFile("export_products.txt");
        getCustomerManager().exportToFile("export_customers.txt");
        getEmployeeManager().exportToFile("export_employees.txt");
        getOrderManager().exportToFile("export_orders.txt");
    }
}
