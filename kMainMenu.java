import java.util.*;

public class kMainMenu {
    private static hWarehouseManager warehouseSystem;
    private static Scanner scanner;
    
    public static void main(String[] args) {
        warehouseSystem = new hWarehouseManager();
        scanner = new Scanner(System.in);
        
        System.out.println("=========================================");
        System.out.println("    HE THONG QUAN LY KHO BEVERAGE");
        System.out.println("=========================================");
        
        // Load dữ liệu từ file
        warehouseSystem.loadDataFromFiles();
        
        boolean running = true;
        while (running) {
            displayMainMenu();
            int choice = getIntInput("Lua chon cua ban: ");
            
            switch (choice) {
                case 1:
                    manageProducts();
                    break;
                case 2:
                    manageCustomers();
                    break;
                case 3:
                    manageEmployees();
                    break;
                case 4:
                    manageOrders();
                    break;
                case 5:
                    warehouseSystem.exportAllData();
                    break;
                case 6:
                    searchData();
                    break;
                case 0:
                    running = false;
                    System.out.println("Cam on ban da su dung he thong!");
                    break;
                default:
                    System.out.println("Lua chon khong hop le! Vui long chon lai.");
            }
            
            if (running) {
                System.out.println("\nNhan Enter de tiep tuc...");
                scanner.nextLine();
            }
        }
        
        scanner.close();
    }
    
    private static void displayMainMenu() {
        System.out.println("\n=== MENU CHINH ===");
        System.out.println("1. Quan ly San pham");
        System.out.println("2. Quan ly Khach hang");
        System.out.println("3. Quan ly Nhan vien");
        System.out.println("4. Quan ly Don hang");
        System.out.println("5. Xuat du lieu ra file");
        System.out.println("6. Tim kiem du lieu");
        System.out.println("0. Thoat");
        System.out.println("==================");
    }
    
    private static void manageProducts() {
        boolean back = false;
        while (!back) {
            System.out.println("\n=== QUAN LY SAN PHAM ===");
            System.out.println("1. Hien thi tat ca san pham");
            System.out.println("2. Them san pham moi");
            System.out.println("3. Tim kiem san pham");
            System.out.println("4. Cap nhat san pham");
            System.out.println("5. Xoa san pham");
            System.out.println("0. Quay lai");
            
            int choice = getIntInput("Lua chon: ");
            switch (choice) {
                case 1:
                    warehouseSystem.getProductManager().displayAll();
                    break;
                case 2:
                    addNewProduct();
                    break;
                case 3:
                    String keyword = getStringInput("Nhap tu khoa tim kiem: ");
                    warehouseSystem.getProductManager().search(keyword);
                    break;
                case 4:
                    updateProduct();
                    break;
                case 5:
                    deleteProduct();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Lua chon khong hop le!");
            }
        }
    }
    
    private static void addNewProduct() {
        System.out.println("\n=== THEM SAN PHAM MOI ===");
        String id = getStringInput("Nhap ma san pham: ");
        String name = getStringInput("Nhap ten san pham: ");
        double price = getDoubleInput("Nhap gia: ");
        int quantity = getIntInput("Nhap so luong: ");
        String type = getStringInput("Nhap loai (NUOC/BANH/KEO): ");
        String attribute = getStringInput("Nhap dac trung (huong vi/do ngot/vi): ");
        
        cProduct product;
        switch (type.toUpperCase()) {
            case "NUOC":
                product = new cBeverage(id, name, price, quantity, attribute);
                break;
            case "BANH":
                product = new cSnack(id, name, price, quantity, attribute);
                break;
            case "KEO":
                product = new cCandy(id, name, price, quantity, attribute);
                break;
            default:
                System.out.println("Loai san pham khong hop le!");
                return;
        }
        
        warehouseSystem.getProductManager().addProduct(product);
        System.out.println("Them san pham thanh cong!");
    }
    
    private static void updateProduct() {
        String id = getStringInput("Nhap ma san pham can cap nhat: ");
        cProduct product = warehouseSystem.getProductManager().findProductById(id);
        
        if (product != null) {
            double newPrice = getDoubleInput("Nhap gia moi: ");
            int newQuantity = getIntInput("Nhap so luong moi: ");
            
            product.setPrice(newPrice);
            product.setQuantity(newQuantity);
            System.out.println("Cap nhat san pham thanh cong!");
        } else {
            System.out.println("Khong tim thay san pham!");
        }
    }
    
    private static void deleteProduct() {
        String id = getStringInput("Nhap ma san pham can xoa: ");
        
        // XÁC NHẬN TRƯỚC KHI XOÁ
        System.out.print("Ban co chac chan muon xoa san pham nay? (y/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        
        if (confirm.equals("y") || confirm.equals("yes")) {
            boolean success = warehouseSystem.getProductManager().deleteProduct(id);
            if (success) {
                System.out.println("Xoa san pham thanh cong!");
            } else {
                System.out.println("Xoa san pham that bai! Kiem tra lai ma san pham.");
            }
        } else {
            System.out.println("Da huy thao tac xoa san pham.");
        }
    }
    
    private static void manageCustomers() {
        boolean back = false;
        while (!back) {
        System.out.println("\n=== QUAN LY KHACH HANG ===");
        System.out.println("1. Hien thi tat ca khach hang");
        System.out.println("2. Them khach hang moi");
        System.out.println("3. Tim kiem khach hang");
        System.out.println("4. Cap nhat thong tin khach hang");
        System.out.println("5. Xoa khach hang");
        System.out.println("0. Quay lai");
            
            int choice = getIntInput("Lua chon: ");
            switch (choice) {
                case 1:
                    warehouseSystem.getCustomerManager().displayAll();
                    break;
                case 2:
                    addNewCustomer();
                    break;
                case 3:
                    String keyword = getStringInput("Nhap tu khoa tim kiem: ");
                    warehouseSystem.getCustomerManager().search(keyword);
                    break;
                case 4:
                    updateCustomer();
                    break;
                case 5:
                    deleteCustomer();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Lua chon khong hop le!");
            }
        }
    }
    
    private static void addNewCustomer() {
        System.out.println("\n=== THEM KHACH HANG MOI ===");
        String id = getStringInput("Nhap ma khach hang: ");
        String name = getStringInput("Nhap ten khach hang: ");
        String phone = getStringInput("Nhap so dien thoai: ");
        
        eCustomer customer = new eCustomer(id, name, phone);
        warehouseSystem.getCustomerManager().addCustomer(customer);
        System.out.println("Them khach hang thanh cong!");
    }
    private static void updateCustomer() {
        String id = getStringInput("Nhap ma khach hang can cap nhat: ");
        eCustomer customer = warehouseSystem.getCustomerManager().findCustomerById(id);
        
        if (customer != null) {
            String newName = getStringInput("Nhap ten moi: ");
            String newPhone = getStringInput("Nhap so dien thoai moi: ");
            
            customer.setName(newName);
            customer.setPhone(newPhone);
            System.out.println("Cap nhat khach hang thanh cong!");
        } else {
            System.out.println("Khong tim thay khach hang!");
        }
    }

    private static void deleteCustomer() {
    String id = getStringInput("Nhap ma khach hang can xoa: ");
    
    // XÁC NHẬN TRƯỚC KHI XOÁ
    System.out.print("Ban co chac chan muon xoa khach hang nay? (y/n): ");
    String confirm = scanner.nextLine().trim().toLowerCase();
    
    if (confirm.equals("y") || confirm.equals("yes")) {
        boolean success = warehouseSystem.getCustomerManager().deleteCustomer(id);
        if (success) {
            System.out.println("Xoa khach hang thanh cong!");
        } else {
            System.out.println("Xoa khach hang that bai! Kiem tra lai ma khach hang.");
        }
    } else {
        System.out.println("Da huy thao tac xoa khach hang.");
    }
}

    
    private static void manageEmployees() {
        boolean back = false;
        while (!back) {
        System.out.println("\n=== QUAN LY NHAN VIEN ===");
        System.out.println("1. Hien thi tat ca nhan vien");
        System.out.println("2. Them nhan vien moi");
        System.out.println("3. Tim kiem nhan vien");
        System.out.println("4. Cap nhat thong tin nhan vien");
        System.out.println("5. Xoa nhan vien");
        System.out.println("0. Quay lai");
            
            int choice = getIntInput("Lua chon: ");
            switch (choice) {
                case 1:
                warehouseSystem.getEmployeeManager().displayAll();
                break;
            case 2:
                addNewEmployee();
                break;
            case 3:
                String keyword = getStringInput("Nhap tu khoa tim kiem: ");
                warehouseSystem.getEmployeeManager().search(keyword);
                break;
            case 4:
                updateEmployee();
                break;
            case 5:
                deleteEmployee();
                break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Lua chon khong hop le!");
            }
        }
    }
    private static void addNewEmployee() {
    System.out.println("\n=== THEM NHAN VIEN MOI ===");
    String id = getStringInput("Nhap ma nhan vien: ");
    String name = getStringInput("Nhap ten nhan vien: ");
    String phone = getStringInput("Nhap so dien thoai: ");
    String position = getStringInput("Nhap chuc vu: ");
    
    dEmployee employee = new dEmployee(id, name, phone, position);
    warehouseSystem.getEmployeeManager().addEmployee(employee);
    }

    private static void updateEmployee() {
        String id = getStringInput("Nhap ma nhan vien can cap nhat: ");
        dEmployee employee = warehouseSystem.getEmployeeManager().findEmployeeById(id);
        
        if (employee != null) {
            String newName = getStringInput("Nhap ten moi: ");
            String newPhone = getStringInput("Nhap so dien thoai moi: ");
            String newPosition = getStringInput("Nhap chuc vu moi: ");
            
            employee.setName(newName);
            employee.setContactInfo(newPhone);
            employee.setPosition(newPosition);
            System.out.println("Cap nhat nhan vien thanh cong!");
        } else {
            System.out.println("Khong tim thay nhan vien!");
        }
    }

    private static void deleteEmployee() {
    String id = getStringInput("Nhap ma nhan vien can xoa: ");
    
    // XÁC NHẬN TRƯỚC KHI XOÁ
    System.out.print("Ban co chac chan muon xoa nhan vien nay? (y/n): ");
    String confirm = scanner.nextLine().trim().toLowerCase();
    
    if (confirm.equals("y") || confirm.equals("yes")) {
        boolean success = warehouseSystem.getEmployeeManager().deleteEmployee(id);
        if (success) {
            System.out.println("Xoa nhan vien thanh cong!");
        } else {
            System.out.println("Xoa nhan vien that bai! Kiem tra lai ma nhan vien.");
        }
    } else {
        System.out.println("Da huy thao tac xoa nhan vien.");
    }
}



    private static void manageOrders() {
        boolean back = false;
        while (!back) {
            System.out.println("\n=== QUAN LY DON HANG ===");
            System.out.println("1. Hien thi tat ca don hang");
            System.out.println("2. Hien thi don hang voi tong tien");
            System.out.println("3. Them don hang moi");
            System.out.println("4. Them san pham vao don hang");
            System.out.println("5. Xoa san pham khoi don hang");
            System.out.println("6. Cap nhat so luong san pham");
            System.out.println("7. Xoa don hang");
            System.out.println("8. Xu ly don hang (Nhap/Xuat kho)");
            System.out.println("9. Hien thi chi tiet don hang");
            System.out.println("10. Tim kiem don hang");
            System.out.println("0. Quay lai");
            
            int choice = getIntInput("Lua chon: ");
            switch (choice) {
                case 1:
                    warehouseSystem.getOrderManager().displayAll();
                    break;
                case 2:
                    warehouseSystem.getOrderManager().displayOrdersWithTotal();
                    break;
                case 3:
                    addNewOrder();
                    break;
                case 4:
                    addProductToOrder();
                    break;
                case 5:
                    removeProductFromOrder();
                    break;
                case 6:
                    updateProductQuantityInOrder();
                    break;
                case 7:
                    deleteOrder();
                    break;
                case 8:
                    processOrder();
                    break;
                case 9:
                    displayOrderDetails();
                    break;
                case 10:
                    String keyword = getStringInput("Nhap tu khoa tim kiem: ");
                    warehouseSystem.getOrderManager().search(keyword);
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Lua chon khong hop le!");
            }
        }
    }

    private static void addNewOrder() {
        System.out.println("\n=== THEM DON HANG MOI ===");
        String orderId = getStringInput("Nhap ma don hang: ");
        String type = getStringInput("Nhap loai don hang (NHAP/XUAT): ");
        
        // Hiển thị danh sách nhân viên
        warehouseSystem.getEmployeeManager().displayAll();
        String employeeId = getStringInput("Nhap ma nhan vien: ");
        dEmployee employee = warehouseSystem.getEmployeeManager().findEmployeeById(employeeId);
        
        // Hiển thị danh sách khách hàng
        warehouseSystem.getCustomerManager().displayAll();
        String customerId = getStringInput("Nhap ma khach hang: ");
        eCustomer customer = warehouseSystem.getCustomerManager().findCustomerById(customerId);
        
        if (employee != null) {
            gOrder order = warehouseSystem.getOrderManager().createOrder(orderId, type, employee, customer);
            System.out.println("Tao don hang thanh cong!");
            
            // Hỏi người dùng có muốn thêm sản phẩm ngay không
            System.out.print("Ban co muon them san pham vao don hang ngay bay gio? (y/n): ");
            String addProducts = scanner.nextLine().trim().toLowerCase();
            if (addProducts.equals("y") || addProducts.equals("yes")) {
                addProductToOrder(orderId);
            }
        } else {
            System.out.println("Khong tim thay nhan vien!");
        }
    }

     private static void addProductToOrder() {
        String orderId = getStringInput("Nhap ma don hang: ");
        addProductToOrder(orderId);
    }

    private static void addProductToOrder(String orderId) {
        // Hiển thị danh sách sản phẩm
        warehouseSystem.getProductManager().displayAll();
        String productId = getStringInput("Nhap ma san pham: ");
        cProduct product = warehouseSystem.getProductManager().findProductById(productId);
        
        if (product != null) {
            int quantity = getIntInput("Nhap so luong: ");
            double unitPrice = getDoubleInput("Nhap don gia: ");
            
            boolean success = warehouseSystem.getOrderManager().addProductToOrder(orderId, product, quantity, unitPrice);
            if (success) {
                System.out.println("Them san pham vao don hang thanh cong!");
            } else {
                System.out.println("Them san pham that bai! Kiem tra lai ma don hang.");
            }
        } else {
            System.out.println("Khong tim thay san pham!");
        }
    }

    private static void removeProductFromOrder() {
        String orderId = getStringInput("Nhap ma don hang: ");
        gOrder order = warehouseSystem.getOrderManager().findOrderById(orderId);
        
        if (order != null) {
            // Hiển thị chi tiết đơn hàng
            order.displayOrderWithTotal();
            int detailIndex = getIntInput("Nhap so thu tu san pham can xoa: ") - 1;
            
            boolean success = warehouseSystem.getOrderManager().removeProductFromOrder(orderId, detailIndex);
            if (success) {
                System.out.println("Xoa san pham khoi don hang thanh cong!");
            } else {
                System.out.println("Xoa san pham that bai! Kiem tra lai so thu tu.");
            }
        } else {
            System.out.println("Khong tim thay don hang!");
        }
    }

    private static void updateProductQuantityInOrder() {
        String orderId = getStringInput("Nhap ma don hang: ");
        gOrder order = warehouseSystem.getOrderManager().findOrderById(orderId);
        
        if (order != null) {
            // Hiển thị chi tiết đơn hàng
            order.displayOrderWithTotal();
            int detailIndex = getIntInput("Nhap so thu tu san pham can cap nhat: ") - 1;
            int newQuantity = getIntInput("Nhap so luong moi: ");
            
            boolean success = warehouseSystem.getOrderManager().updateProductQuantity(orderId, detailIndex, newQuantity);
            if (success) {
                System.out.println("Cap nhat so luong thanh cong!");
            } else {
                System.out.println("Cap nhat that bai! Kiem tra lai so thu tu.");
            }
        } else {
            System.out.println("Khong tim thay don hang!");
        }
    }

     private static void deleteOrder() {
        String orderId = getStringInput("Nhap ma don hang can xoa: ");
        
        // XÁC NHẬN TRƯỚC KHI XOÁ
        System.out.print("Ban co chac chan muon xoa don hang nay? (y/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        
        if (confirm.equals("y") || confirm.equals("yes")) {
            boolean success = warehouseSystem.getOrderManager().deleteOrder(orderId);
            if (success) {
                System.out.println("Xoa don hang thanh cong!");
            } else {
                System.out.println("Xoa don hang that bai! Kiem tra lai ma don hang.");
            }
        } else {
            System.out.println("Da huy thao tac xoa don hang.");
        }
    }

    private static void processOrder() {
        String orderId = getStringInput("Nhap ma don hang can xu ly: ");
        warehouseSystem.getOrderManager().processOrder(orderId);
    }
    
    private static void displayOrderDetails() {
        String orderId = getStringInput("Nhap ma don hang can xem: ");
        warehouseSystem.getOrderManager().displayOrderDetails(orderId);
    }
    
    private static void searchData() {
        System.out.println("\n=== TIM KIEM DU LIEU ===");
        String keyword = getStringInput("Nhap tu khoa tim kiem: ");
        
        System.out.println("\n=== KET QUA TIM KIEM TOAN HE THONG ===");
        warehouseSystem.getProductManager().search(keyword);
        warehouseSystem.getCustomerManager().search(keyword);
        warehouseSystem.getEmployeeManager().search(keyword);
        warehouseSystem.getOrderManager().search(keyword);
    }
    
    
    
    
    
    // Utility methods
    private static int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("Vui long nhap so nguyen!");
            scanner.next();
            System.out.print(prompt);
        }
        int value = scanner.nextInt();
        scanner.nextLine(); // Clear buffer
        return value;
    }
    
    private static double getDoubleInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextDouble()) {
            System.out.println("Vui long nhap so!");
            scanner.next();
            System.out.print(prompt);
        }
        double value = scanner.nextDouble();
        scanner.nextLine(); // Clear buffer
        return value;
    }
    
    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}
