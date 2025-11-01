public class cCandy extends cProduct {
    private String sweetness;
    
    public cCandy(String id, String name, double price, int quantity, String sweetness) {
        super(id, name, price, quantity);
        this.sweetness = sweetness;
    }
    
    @Override
    public String getProductType() {
        return "KEO";
    }
    
    public String getSweetness() { return sweetness; }
    public void setSweetness(String sweetness) { this.sweetness = sweetness; }
    
    @Override
    public String toString() {
        return super.toString() + String.format(" | Do ngot: %s", sweetness);
    }
    
    @Override
    public String toFileString() {
        return String.format("%s,%s,%.0f,%d,%s,KEO", id, name, price, quantity, sweetness);
    }
}