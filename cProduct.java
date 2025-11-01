public abstract class cProduct implements aIManageable {
    protected String id;
    protected String name;
    protected double price;
    protected int quantity;
    
    private static int totalProducts = 0;
    
    public cProduct(String id, String name, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        totalProducts++;
    }
    
    public static int getTotalProducts() {
        return totalProducts;
    }
    
    public abstract String getProductType();
    
    public String getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    
    public void setName(String name) { this.name = name; }
    public void setPrice(double price) { this.price = price; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    
    public void updateQuantity(int amount) {
        this.quantity += amount;
        if (this.quantity < 0) this.quantity = 0;
    }
    
    public double calculateValue() {
        return quantity * price;
    }
    
    public static int countByType(java.util.List<cProduct> products, String productType) {
        int count = 0;
        for (cProduct product : products) {
            if (product.getProductType().equals(productType)) {
                count++;
            }
        }
        return count;
    }
    
    public static double calculateTotalInventoryValue(java.util.List<cProduct> products) {
        double total = 0;
        for (cProduct product : products) {
            total += product.calculateValue();
        }
        return total;
    }
    
    @Override
    public void displayInfo() {
        System.out.println(this.toString());
    }
    
    @Override
    public String toString() {
        return String.format("Ma SP: %s | Ten: %-15s | Gia: %,-8.0f | SL: %d | Thanh tien: %,-10.0f",
                           id, name, price, quantity, calculateValue());
    }
    
    @Override
    public abstract String toFileString();
}