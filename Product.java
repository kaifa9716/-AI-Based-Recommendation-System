

public class Product {
    long productId;
    String name;
    String category;
    double price;
    String description;
    
    public Product(long productId, String name, String category, double price) {

        this.productId = productId;
        this.name = name;
        this.category = category;
        this.price = price;
        this.description = "";
    }
    
    public Product(long productId, String name, String category, double price, String description) {
        
        this.productId = productId;
        this.name = name;
        this.category = category;
        this.price = price;
        this.description = description;
    }
    

    public long getProductId() {
        return productId;
    }
    
    public String getName() {
        return name;
    }
    
    public String getCategory() {
        return category;
    }
    
    public double getPrice() {
        return price;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setProductId(long productId) {
        this.productId = productId;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                '}';
    }
    

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Product product = (Product) obj;
        return productId == product.productId;
    }
    

    public int hashCode() {
        return Long.hashCode(productId);
    }
}