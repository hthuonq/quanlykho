public class fOrderDetail {
    private String detailId;
    private String orderId;
    private cProduct product;
    private int quantity;
    private double unitPrice;
    
    public fOrderDetail(String detailId, String orderId, cProduct product, int quantity, double unitPrice) {
        this.detailId = detailId;
        this.orderId = orderId;
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }
    
    // Getter methods
    public String getDetailId() { return detailId; }
    public String getOrderId() { return orderId; }
    public cProduct getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public double getUnitPrice() { return unitPrice; }
    
    public double getSubTotal() {
        return quantity * unitPrice;
    }
    
    @Override
    public String toString() {
        return String.format("SP: %s | SL: %d | Don gia: %,-8.0f | Thanh tien: %,-8.0f",
                           product.getName(), quantity, unitPrice, getSubTotal());
    }
    
    public String toFileString() {
        return String.format("%s,%s,%s,%d,%.0f", detailId, orderId, product.getId(), quantity, unitPrice);
    }  
}