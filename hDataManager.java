import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class hDataManager {
    
    private static final String DATA_DIR = "data/";
    
    // Đọc dữ liệu sản phẩm từ file
    public static hProductlist readProductsFromFile(String filename) {
        hProductlist productList = new hProductlist();
        String filePath = DATA_DIR + filename;
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;
            
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Bỏ qua header
                }
                
                String[] parts = line.split(",");
                if (parts.length >= 6) {
                    String id = parts[0].trim();
                    String name = parts[1].trim();
                    double price = Double.parseDouble(parts[2].trim());
                    int quantity = Integer.parseInt(parts[3].trim());
                    String attribute = parts[4].trim();
                    String type = parts[5].trim();
                    
                    cProduct product;
                    switch (type) {
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
                            product = new cBeverage(id, name, price, quantity, attribute); // default
                    }
                    productList.addProduct(product);
                }
            }
            System.out.println("Da doc " + productList.getProducts().size() + " san pham tu file: " + filePath);
        } catch (IOException e) {
            System.out.println("Loi doc file san pham: " + filePath + " - " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Loi dinh dang so trong file san pham: " + e.getMessage());
        }
        return productList;
    }
    
    // Đọc dữ liệu nhân viên từ file
    public static hEmployeelist readEmployeesFromFile(String filename) {
        hEmployeelist employeeList = new hEmployeelist();
        String filePath = DATA_DIR + filename;
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;
            
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    String id = parts[0].trim();
                    String name = parts[1].trim();
                    String phone = parts[2].trim();
                    String position = parts[3].trim();
                    
                    dEmployee employee = new dEmployee(id, name, phone, position);
                    employeeList.addEmployee(employee);
                }
            }
            System.out.println("Da doc " + employeeList.getEmployees().size() + " nhan vien tu file: " + filePath);
        } catch (IOException e) {
            System.out.println("Loi doc file nhan vien: " + filePath + " - " + e.getMessage());
        }
        return employeeList;
    }
    
    // Đọc dữ liệu khách hàng từ file
    public static hCustomerlist readCustomersFromFile(String filename) {
        hCustomerlist customerList = new hCustomerlist();
        String filePath = DATA_DIR + filename;
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;
            
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String id = parts[0].trim();
                    String name = parts[1].trim();
                    String phone = parts[2].trim();
                    
                    eCustomer customer = new eCustomer(id, name, phone);
                    customerList.addCustomer(customer);
                }
            }
            System.out.println("Da doc " + customerList.getCustomers().size() + " khach hang tu file: " + filePath);
        } catch (IOException e) {
            System.out.println("Loi doc file khach hang: " + filePath + " - " + e.getMessage());
        }
        return customerList;
    }
    
    // Đọc dữ liệu đơn hàng từ file
    public static hOrderlist readOrdersFromFile(String filename, hEmployeelist employeeList, 
                                              hCustomerlist customerList, hProductlist productList) {
        hOrderlist orderList = new hOrderlist();
        String filePath = DATA_DIR + filename;
        
        // Đọc danh sách đơn hàng
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;
            
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    String orderId = parts[0].trim();
                    String dateStr = parts[1].trim();
                    String customerId = parts[2].trim();
                    String employeeId = parts[3].trim();
                    String type = parts[4].trim();
                    
                    dEmployee employee = employeeList.findEmployeeById(employeeId);
                    eCustomer customer = customerList.findCustomerById(customerId);
                    
                    if (employee != null) {
                        gOrder order = new gOrder(orderId, type, employee, customer);
                        
                        // Xử lý ngày tháng nếu có
                        try {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            Date orderDate = sdf.parse(dateStr);
                            order.setOrderDate(orderDate);
                        } catch (Exception e) {
                            // Sử dụng ngày mặc định nếu có lỗi
                        }
                        
                        orderList.getOrders().add(order);
                    }
                }
            }
            System.out.println("Da doc " + orderList.getOrders().size() + " don hang tu file: " + filePath);
        } catch (IOException e) {
            System.out.println("Loi doc file don hang: " + filePath + " - " + e.getMessage());
        }
        
        // Đọc chi tiết đơn hàng
        readOrderDetailsFromFile("order_details.txt", orderList, productList);
        
        return orderList;
    }
    
    // Đọc chi tiết đơn hàng từ file
    private static void readOrderDetailsFromFile(String filename, hOrderlist orderList, hProductlist productList) {
        String filePath = DATA_DIR + filename;
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;
            
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    String detailId = parts[0].trim();
                    String orderId = parts[1].trim();
                    String productId = parts[2].trim();
                    int quantity = Integer.parseInt(parts[3].trim());
                    double unitPrice = Double.parseDouble(parts[4].trim());
                    
                    gOrder order = orderList.findOrderById(orderId);
                    cProduct product = productList.findProductById(productId);
                    
                    if (order != null && product != null) {
                        order.addProduct(product, quantity, unitPrice);
                    }
                }
            }
            System.out.println("Da doc chi tiet don hang tu file: " + filePath);
        } catch (IOException e) {
            System.out.println("Loi doc file chi tiet don hang: " + filePath + " - " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Loi dinh dang so trong file chi tiet don hang: " + e.getMessage());
        }
    }
    
    // Ghi dữ liệu ra file (tổng quát)
    public static void writeToFile(String filename, List<?> dataList, String header) {
        String filePath = DATA_DIR + filename;
        
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {
            if (header != null && !header.isEmpty()) {
                pw.println(header);
            }
            for (Object obj : dataList) {
                if (obj instanceof aIManageable) {
                    pw.println(((aIManageable) obj).toFileString());
                } else {
                    pw.println(obj.toString());
                }
            }
            System.out.println("Da ghi du lieu ra file: " + filePath);
        } catch (IOException e) {
            System.out.println("Loi ghi file: " + filePath + " - " + e.getMessage());
        }
    }
    
    // Kiểm tra xem thư mục data có tồn tại không
    public static void checkDataDirectory() {
        File dataDir = new File(DATA_DIR);
        if (!dataDir.exists()) {
            dataDir.mkdir(); // Tạo thư mục nếu chưa tồn tại
            System.out.println("Da tao thu muc: " + DATA_DIR);
        }
    }
}