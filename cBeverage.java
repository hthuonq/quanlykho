public class cBeverage extends cProduct {
    private String flavor;
    
    public cBeverage(String id, String name, double price, int quantity, String flavor) {
        super(id, name, price, quantity);
        this.flavor = flavor;
    }
    
    @Override
    public String getProductType() {
        return "NUOC";
    }
    
    public String getFlavor() { return flavor; }
    public void setFlavor(String flavor) { this.flavor = flavor; }
    
    @Override
    public String toString() {
        return super.toString() + String.format(" | Huong vi: %s", flavor);
    }
    
    @Override
    public String toFileString() {
        return String.format("%s,%s,%.0f,%d,%s,NUOC", id, name, price, quantity, flavor);
    }
}