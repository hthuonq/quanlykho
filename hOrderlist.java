import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class hOrderlist {
    private List<gOrder> orders;
    private List<fOrderDetail> allOrderDetails;
    
    public hOrderlist() {
        this.orders = new ArrayList<>();
        this.allOrderDetails = new ArrayList<>();
    }
    
    public hOrderlist(List<gOrder> orders, List<fOrderDetail> allOrderDetails) {
        this.orders = orders;
        this.allOrderDetails = allOrderDetails;
    }
    
    // === QUẢN LÝ ĐƠN HÀNG ===
    public gOrder createOrder(String orderId, String type, dEmployee employee, eCustomer customer) {
        gOrder order = new gOrder(orderId, type, employee, customer);
        orders.add(order);
        return order;
    }
    
    public void displayAllOrders() {
        System.out.println("\n=== DANH SACH DON HANG (" + orders.size() + ") ===");
        for (gOrder o : orders) {
            o.displayInfo();
        }
    }
    
    // Hiển thị tất cả đơn hàng với tổng tiền
    public void displayAllOrdersWithTotal() {
        System.out.println("\n=== DANH SACH DON HANG VA TONG TIEN ===");
        if (orders.isEmpty()) {
            System.out.println("Chua co don hang nao!");
            return;
        }
        
        double grandTotal = 0;
        for (gOrder order : orders) {
            order.displayInfo();
            grandTotal += order.calculateTotal();
        }
        
        System.out.println("-----------------------------------------------------------");
        System.out.printf(" TONG DOANH THU TAT CA DON HANG: %,-25.0f \n", grandTotal);
        System.out.println("-----------------------------------------------------------");
    }
    
    // Hiển thị chi tiết 1 đơn hàng cụ thể
    public void displayOrderDetailsWithTotal(String orderId) {
        gOrder order = findOrderById(orderId);
        if (order != null) {
            order.displayOrderWithTotal();
        } else {
            System.out.println("Khong tim thay don hang: " + orderId);
        }
    }
    
    // Tìm đơn hàng theo ID
    public gOrder findOrderById(String orderId) {
        for (gOrder order : orders) {
            if (order.getOrderId().equals(orderId)) {
                return order;
            }
        }
        return null;
    }
    
    // TÌM KIẾM ĐƠN HÀNG
    public List<gOrder> searchOrders(String keyword) {
        List<gOrder> result = new ArrayList<>();
        for (gOrder o : orders) {
            if (o.getOrderId().toLowerCase().contains(keyword.toLowerCase()) ||
                o.getType().toLowerCase().contains(keyword.toLowerCase()) ||
                (o.getCustomer() != null && o.getCustomer().getName().toLowerCase().contains(keyword.toLowerCase())) ||
                o.getEmployee().getName().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(o);
            }
        }
        return result;
    }

    // THÊM SẢN PHẨM VÀO ĐƠN HÀNG
    public boolean addProductToOrder(String orderId, cProduct product, int quantity, double unitPrice) {
        gOrder order = findOrderById(orderId);
        if (order != null) {
            order.addProduct(product, quantity, unitPrice);
            return true;
        }
        return false;
    }

    // XÓA SẢN PHẨM KHỎI ĐƠN HÀNG
    public boolean removeProductFromOrder(String orderId, int detailIndex) {
        gOrder order = findOrderById(orderId);
        if (order != null && detailIndex >= 0 && detailIndex < order.getOrderDetails().size()) {
            order.getOrderDetails().remove(detailIndex);
            return true;
        }
        return false;
    }

    // CẬP NHẬT SỐ LƯỢNG SẢN PHẨM TRONG ĐƠN HÀNG
    public boolean updateProductQuantity(String orderId, int detailIndex, int newQuantity) {
        gOrder order = findOrderById(orderId);
        if (order != null && detailIndex >= 0 && detailIndex < order.getOrderDetails().size()) {
            fOrderDetail detail = order.getOrderDetails().get(detailIndex);
            detail = new fOrderDetail(detail.getDetailId(), orderId, detail.getProduct(), newQuantity, detail.getUnitPrice());
            order.getOrderDetails().set(detailIndex, detail);
            return true;
        }
        return false;
    }
    
    // XÓA ĐƠN HÀNG
    public boolean deleteOrder(String orderId) {
        gOrder order = findOrderById(orderId);
        if (order != null) {
            // Hoàn trả số lượng sản phẩm nếu là đơn xuất
            if (order.getType().equals("XUAT")) {
                for (fOrderDetail detail : order.getOrderDetails()) {
                    cProduct product = detail.getProduct();
                    product.updateQuantity(detail.getQuantity()); // Trả lại số lượng
                }
            } else if (order.getType().equals("NHAP")) {
                // Trừ đi số lượng nếu là đơn nhập
                for (fOrderDetail detail : order.getOrderDetails()) {
                    cProduct product = detail.getProduct();
                    product.updateQuantity(-detail.getQuantity());
                }
            }
            orders.remove(order);
            return true;
        }
        return false;
    }

    // XỬ LÝ ĐƠN HÀNG (NHẬP/XUẤT KHO)
    public boolean processOrder(String orderId) {
        gOrder order = findOrderById(orderId);
        if (order != null) {
            return order.processOrder();
        }
        return false;
    }
    
    // Thống kê doanh thu theo loại đơn hàng
    public void displayRevenueStatistics() {
        double importTotal = 0;
        double exportTotal = 0;
        
        for (gOrder order : orders) {
            double orderTotal = order.calculateTotal();
            if (order.getType().equals("NHAP")) {
                importTotal += orderTotal;
            } else if (order.getType().equals("XUAT")) {
                exportTotal += orderTotal;
            }
        }
        
        double profit = exportTotal - importTotal;
        
        System.out.println("\n=== THONG KE DOANH THU ===");
        System.out.printf("Tong gia tri nhap kho: %,-15.0f VND\n", importTotal);
        System.out.printf("Tong gia tri xuat kho: %,-15.0f VND\n", exportTotal);
        System.out.println("-----------------------------------------------------------");
        
        if (profit > 0) {
            System.out.printf("LOI NHUAN: %,-15.0f VND\n", profit);
        } else if (profit < 0) {
            System.out.printf("LO: %,-15.0f VND\n", Math.abs(profit));
        } else {
            System.out.printf("HOA VON: %,-15.0f VND\n", profit);
        }
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
    
    // Xuất tất cả order details
    public void exportAllOrderDetails(String filename) {
        List<fOrderDetail> allOrderDetails = new ArrayList<>();
        
        // Thu thập tất cả order details từ các đơn hàng
        for (gOrder order : orders) {
            List<fOrderDetail> details = order.getOrderDetails();
            allOrderDetails.addAll(details);
        }
        
        exportDataToFile(filename, allOrderDetails, "detailId,orderId,productId,numproduct,unitPrice");
    }
    
    public List<gOrder> getOrders() {
        return orders;
    }
    
    public List<fOrderDetail> getAllOrderDetails() {
        return allOrderDetails;
    }
}
