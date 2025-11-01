import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class hOrderlist {
    private List<gOrder> orders;
    
    public hOrderlist() {
        this.orders = new ArrayList<>();
    }
    
    public hOrderlist(List<gOrder> orders) {
        this.orders = orders;
    }
    
    // === QUẢN LÝ ĐƠN HÀNG ===
    public void displayAllOrders() {
        System.out.println("\n=== DANH SACH DON HANG (" + orders.size() + ") ===");
        if (orders.isEmpty()) {
            System.out.println("Chua co don hang nao!");
            return;
        }
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
    
    // THÊM ĐƠN HÀNG MỚI
    public boolean addOrder(gOrder order) {
        if (findOrderById(order.getOrderId()) != null) {
            System.out.println("Ma don hang da ton tai: " + order.getOrderId());
            return false;
        }
        orders.add(order);
        System.out.println("Them don hang thanh cong: " + order.getOrderId());
        return true;
    }
    
    // CẬP NHẬT ĐƠN HÀNG
    public boolean updateOrder(String orderId, String newType, dEmployee newEmployee, eCustomer newCustomer) {
        gOrder order = findOrderById(orderId);
        if (order != null) {
            // Tạo đơn hàng mới với thông tin cập nhật
            gOrder updatedOrder = new gOrder(orderId, newType, newEmployee, newCustomer);
            
            // Giữ lại các chi tiết đơn hàng cũ
            List<fOrderDetail> oldDetails = order.getOrderDetails();
            for (fOrderDetail detail : oldDetails) {
                updatedOrder.addProduct(detail.getProduct(), detail.getQuantity(), detail.getUnitPrice());
            }
            
            // Thay thế đơn hàng cũ bằng đơn hàng mới
            orders.remove(order);
            orders.add(updatedOrder);
            
            System.out.println("Cap nhat don hang thanh cong: " + orderId);
            return true;
        }
        System.out.println("Khong tim thay don hang: " + orderId);
        return false;
    }
    
    // XÓA ĐƠN HÀNG
    public boolean deleteOrder(String orderId) {
        gOrder order = findOrderById(orderId);
        if (order != null) {
            orders.remove(order);
            System.out.println("Da xoa don hang: " + orderId);
            return true;
        }
        System.out.println("Khong tim thay don hang: " + orderId);
        return false;
    }
    
    // THÊM SẢN PHẨM VÀO ĐƠN HÀNG
    public boolean addProductToOrder(String orderId, cProduct product, int quantity, double unitPrice) {
        gOrder order = findOrderById(orderId);
        if (order != null) {
            order.addProduct(product, quantity, unitPrice);
            System.out.println("Da them san pham " + product.getName() + " vao don hang " + orderId);
            return true;
        }
        System.out.println("Khong tim thay don hang: " + orderId);
        return false;
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
    
    public List<gOrder> getOrders() {
        return orders;
    }
}