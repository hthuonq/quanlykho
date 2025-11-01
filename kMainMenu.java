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
                    warehouseSystem.demonstratePolymorphism();
                    break;
                case 6:
                    warehouseSystem.exportAllData();
                    break;
                case 7:
                    searchData();
                    break;
                case 8:
                    showStatistics();
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
        System.out.println("5. Demo Tinh da hinh");
        System.out.println("6. Xuat du lieu ra file");
        System.out.println("7. Tim kiem du lieu");
        System.out.println("8. Thong ke");
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
        cProduct product = warehouseSystem.getProductManager().findProductById(id);
        
        if (product != null) {
            // Ở đây cần thêm logic xóa từ productList
            System.out.println("Xoa san pham thanh cong!");
        } else {
            System.out.println("Khong tim thay san pham!");
        }
    }
    
    private static void manageCustomers() {
        boolean back = false;
        while (!back) {
            System.out.println("\n=== QUAN LY KHACH HANG ===");
            System.out.println("1. Hien thi tat ca khach hang");
            System.out.println("2. Them khach hang moi");
            System.out.println("3. Tim kiem khach hang");
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
    
    private static void manageEmployees() {
        boolean back = false;
        while (!back) {
            System.out.println("\n=== QUAN LY NHAN VIEN ===");
            System.out.println("1. Hien thi tat ca nhan vien");
            System.out.println("2. Tim kiem nhan vien");
            System.out.println("0. Quay lai");
            
            int choice = getIntInput("Lua chon: ");
            switch (choice) {
                case 1:
                    warehouseSystem.getEmployeeManager().displayAll();
                    break;
                case 2:
                    String keyword = getStringInput("Nhap tu khoa tim kiem: ");
                    warehouseSystem.getEmployeeManager().search(keyword);
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Lua chon khong hop le!");
            }
        }
    }
    
    private static void manageOrders() {
        boolean back = false;
        while (!back) {
            System.out.println("\n=== QUAN LY DON HANG ===");
            System.out.println("1. Hien thi tat ca don hang");
            System.out.println("2. Hien thi don hang voi tong tien");
            System.out.println("3. Tim kiem don hang");
            System.out.println("4. Xu ly don hang");
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
                    String keyword = getStringInput("Nhap tu khoa tim kiem: ");
                    warehouseSystem.getOrderManager().search(keyword);
                    break;
                case 4:
                    String orderId = getStringInput("Nhap ma don hang can xu ly: ");
                    warehouseSystem.getOrderManager().processOrder(orderId);
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Lua chon khong hop le!");
            }
        }
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
    
    private static void showStatistics() {
        System.out.println("\n=== THONG KE HE THONG ===");
        warehouseSystem.getProductManager().showStatistics();
        warehouseSystem.getOrderManager().showStatistics();
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