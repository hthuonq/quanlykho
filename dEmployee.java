public class dEmployee extends bAbstractPerson {
    private String contactInfo;
    private String position;
    
    public dEmployee(String id, String name, String contactInfo, String position) {
        super(id, name);
        this.contactInfo = contactInfo;
        this.position = position;
    }
    
    // Getter methods
    public String getContactInfo() { return contactInfo; }
    public String getPosition() { return position; }
    
    // Setter methods
    public void setContactInfo(String contactInfo) { this.contactInfo = contactInfo; }
    public void setPosition(String position) { this.position = position; }
    
    @Override
    public String getType() {
        return "Nhan vien";
    }
    
    @Override
    public String toString() {
        return String.format("Ma NV: %s | Ten: %-15s | Chuc vu: %-10s | SDT: %s",
                           id, name, position, contactInfo);
    }
    
    @Override
    public String toFileString() {
        return String.format("%s,%s,%s,%s", id, name, contactInfo, position);
    }
}