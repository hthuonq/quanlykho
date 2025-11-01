public abstract class bAbstractPerson implements aIManageable {
    protected String id;
    protected String name;
    
    protected static int totalPersons = 0;
    
    public bAbstractPerson(String id, String name) {
        this.id = id;
        this.name = name;
        totalPersons++;
    }
    
    public abstract String getType();
    
    public String getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public static int getTotalPersons() {
        return totalPersons;
    }
    
    public static boolean isValidId(String id) {
        return id != null && !id.trim().isEmpty();
    }
    
    public static boolean isValidName(String name) {
        return name != null && name.length() >= 2 && name.length() <= 50;
    }

    @Override
    public void displayInfo() {
        System.out.println(this.toString());
    }
    
    @Override
    public abstract String toFileString();
}