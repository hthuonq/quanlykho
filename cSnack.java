public class cSnack extends cProduct {
    private String taste;
    
    public cSnack(String id, String name, double price, int quantity, String taste) {
        super(id, name, price, quantity);
        this.taste = taste;
    }
    
    @Override
    public String getProductType() {
        return "BANH";
    }
    
    public String getTaste() { return taste; }
    public void setTaste(String taste) { this.taste = taste; }
    
    @Override
    public String toString() {
        return super.toString() + String.format(" | Vi: %s", taste);
    }
    
    @Override
    public String toFileString() {
        return String.format("%s,%s,%.0f,%d,%s,BANH", id, name, price, quantity, taste);
    }
}