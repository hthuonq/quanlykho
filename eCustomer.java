public class eCustomer extends bAbstractPerson {
    private String phone;
    
    public eCustomer(String id, String name, String phone) {
        super(id, name);
        this.phone = phone;
    }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    @Override
    public String getType() {
        return "Khach hang";
    }
    
    @Override
    public String toString() {
        return String.format("Ma KH: %s | Ten: %-15s | SDT: %s", id, name, phone);
    }
    
    @Override
    public String toFileString() {
        return String.format("%s,%s,%s", id, name, phone);
    }
}