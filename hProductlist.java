import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
public class hProductlist {
    private List<cProduct> products;

    public hProductlist(){
        this.products= new ArrayList<>();
    }
    public hProductlist(List<cProduct> products){
        this.products=products;
    }

     // === QUẢN LÝ SẢN PHẨM ===
    public void addProduct(cProduct product) {
        products.add(product);
    }
    
    public void displayAllProducts() {
        System.out.println("\n=== DANH SACH SAN PHAM (" + products.size() + ") ===");
        if (products.isEmpty()) {
            System.out.println("Chua co san pham nao!");
            return;
        }
        for (cProduct p : products) {
            System.out.println(p);
        }
        // Hiển thị tổng giá trị tồn kho
        double totalValue = calculateTotalInventoryValue();
        System.out.println("-----------------------------------------------------------");
        System.out.printf(" TONG GIA TRI TON KHO: %,-30.0f \n", totalValue);
        System.out.println("-----------------------------------------------------------");
    }
    
    

    public cProduct findProductById(String productId) {
        for (cProduct p : products) {
            if (p.getId().equals(productId)) {
                return p;
            }
        }
        return null;
    }
    
   public boolean deleteProduct(String productId) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(productId)) {
                cProduct removedProduct = products.remove(i);
                System.out.println("Da xoa san pham: " + removedProduct.getName() + " (ID: " + productId + ")");
                return true;
            }
        }
        System.out.println("Khong tim thay san pham voi ID: " + productId);
        return false;
    }
    
    public List<cProduct> searchProducts(String keyword) {
        List<cProduct> result = new ArrayList<>();
        for (cProduct p : products) {
            if (p.getName().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(p);
            }
        }
        return result;
    }
    public boolean updateProduct(String productId, double newPrice, int newQuantity) {
        cProduct product = findProductById(productId);
        if (product != null) {
            product.setPrice(newPrice);
            product.setQuantity(newQuantity);
            return true;
        }
        return false;
    }
    
    public double calculateTotalInventoryValue() {
        double total = 0;
        for (cProduct product : products) {
            total += product.calculateValue();
        }
        return total;
    }
     // Xuất dữ liệu ra file
    public void exportDataToFile(String filename, List<?> dataList, String header) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            // Ghi header nếu có
            if (header != null && !header.isEmpty()) {
                pw.println(header);
            }
            // Ghi dữ liệu
            for (Object obj : dataList) {
                pw.println(obj.toString());
            }
            System.out.println("Da xuat du lieu ra file: " + filename);
        } catch (IOException e) {
            System.out.println("Loi ghi file: " + e.getMessage());
        }
    }
    public List<cProduct> getProducts() { return products; }
 
}
