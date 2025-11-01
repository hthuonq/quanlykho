import java.util.*;
import java.text.SimpleDateFormat;

public class gOrder implements aIManageable {
    private String orderId;
    private Date orderDate;
    private Date receiveDate;
    private String type; // "NHAP" hoặc "XUAT"
    private eCustomer customer;
    private dEmployee employee;
    private List<fOrderDetail> orderDetails;
    
     public gOrder(String orderId, String type, dEmployee employee, eCustomer customer) {
        this.orderId = orderId;
        this.type = type;
        this.employee = employee;
        this.customer = customer;
        this.orderDate = new Date();
        this.orderDetails = new ArrayList<>();
    }

    // Getter & Setter methods
    public String getOrderId() { return orderId; }
    public Date getOrderDate() { return orderDate; }
    public String getType() { return type; }
    public dEmployee getEmployee() { return employee; }
    public eCustomer getCustomer() { return customer; }
    public List<fOrderDetail> getOrderDetails() { return orderDetails; }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }
    
    // Thêm sản phẩm vào đơn hàng
    public void addProduct(cProduct product, int quantity, double unitPrice) {
        String detailId = "CT" + orderId + "_" + (orderDetails.size() + 1);
        fOrderDetail detail = new fOrderDetail(detailId, orderId, product, quantity, unitPrice);
        orderDetails.add(detail);
    }
    
    // Tính tổng tiền đơn hàng
    public double calculateTotal() {
        double total = 0;
        for (fOrderDetail detail : orderDetails) {
            total += detail.getSubTotal();
        }
        return total;
    }
    
    // Xử lý đơn hàng
    public boolean processOrder() {
        try {
            for (fOrderDetail detail : orderDetails) {
                cProduct product = detail.getProduct();
                int quantity = detail.getQuantity();
                
                if (type.equals("XUAT")) {
                    if (product.getQuantity() < quantity) {
                        System.out.println("Khong du ton kho cho SP: " + product.getName());
                        return false;
                    }
                    product.updateQuantity(-quantity);
                } else if (type.equals("NHAP")) {
                    product.updateQuantity(quantity);
                }
            }
            this.receiveDate = new Date();
            
            // Hiển thị thông tin đơn hàng sau khi xử lý
            System.out.println((type.equals("NHAP") ? "Nhap kho" : "Xuat kho") + " thanh cong!");
            displayOrderWithTotal();
            
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    // Hiển thị chi tiết đơn hàng với tổng tiền
    public void displayOrderWithTotal() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String customerInfo = (customer != null) ? customer.getName() : "N/A";
        
        System.out.println("-----------------------------------------------------------");
        System.out.println("                     THONG TIN DON HANG                  ");
        System.out.println("-----------------------------------------------------------");
        System.out.printf("│ Ma don: %-45s │\n", orderId);
        System.out.printf("│ Loai: %-47s │\n", type);
        System.out.printf("│ Ngay dat: %-42s │\n", sdf.format(orderDate));
        System.out.printf("│ Nhan vien: %-42s │\n", employee.getName());
        System.out.printf("│ Khach hang: %-41s │\n", customerInfo);
        System.out.println("-----------------------------------------------------------");
        System.out.println("                      CHI TIET DON HANG                  ");
        System.out.println("-----------------------------------------------------------");
        
        // Hiển thị chi tiết sản phẩm
        for (int i = 0; i < orderDetails.size(); i++) {
            fOrderDetail detail = orderDetails.get(i);
            System.out.printf("│ %-2d. %-20s SL: %-3d Don gia: %,-8.0f Thanh tien: %,-8.0f │\n",
                i + 1, detail.getProduct().getName(), detail.getQuantity(),
                detail.getUnitPrice(), detail.getSubTotal());
        }
        
        System.out.println("-----------------------------------------------------------");
        System.out.printf(" TONG TIEN: %,-43.0f \n", calculateTotal());
        System.out.println("-----------------------------------------------------------");
    }
    
    @Override
    public void displayInfo() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String customerInfo = (customer != null) ? customer.getName() : "N/A";
        System.out.printf("Ma don: %s | Loai: %-5s | Ngay: %s | NV: %-10s | KH: %-15s | Tong: %,-10.0f\n",
            orderId, type, sdf.format(orderDate), employee.getName(), customerInfo, calculateTotal());
    }
    
    @Override
    public String toFileString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String customerId = (customer != null) ? customer.getId() : "null";
        String receiveDateStr = (receiveDate != null) ? sdf.format(receiveDate) : "null";
        return String.format("%s,%s,%s,%s,%s,%s", 
                           orderId, sdf.format(orderDate), receiveDateStr, customerId, employee.getId(), type);
    }
}