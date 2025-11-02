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
            orders.remove(order);
            return true;
        }
        return false;
    }
    
    // HIỂN THỊ CHI TIẾT ĐƠN HÀNG
    public void displayOrderDetails(String orderId) {
        gOrder order = findOrderById(orderId);
        if (order != null) {
            order.displayOrderWithTotal();
        } else {
            System.out.println("Khong tim thay don hang: " + orderId);
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
