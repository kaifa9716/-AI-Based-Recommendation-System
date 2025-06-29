import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;

import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;

import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.File;
import java.io.IOException;
import java.util.*;




public class RecommendationService {
    
    private DataModel model;
    private UserSimilarity similarity;
    private UserNeighborhood neighborhood;
    private UserBasedRecommender recommender;
    private Map<Long, Product> productCatalog;
    
    public RecommendationService() {
        this.productCatalog = new HashMap<>();
        initializeProductCatalog();
    }
    
    // Initialize the recommendation system with data from CSV file
    //CSV format: userID,itemID,rating

    public void initializeRecommender(String dataFilePath) throws IOException, TasteException {
        try {
            // Load data model from CSV file
            model = new FileDataModel(new File(dataFilePath));
            
            // Use Pearson correlation for user similarity
            similarity = new PearsonCorrelationSimilarity(model);
            
            // Define neighborhood (users with similarity > 0.1)
            neighborhood = new ThresholdUserNeighborhood(0.1, similarity, model);
            
            // Create the recommender
            recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
            
            System.out.println("Recommendation system initialized successfully!");
            System.out.println("Number of users: " + model.getNumUsers());
            System.out.println("Number of items: " + model.getNumItems());
            
        } catch (Exception e) {
            System.err.println("Error initializing recommender: " + e.getMessage());
            throw e;
        }
    }
    

    //Get recommendations for a specific user
    public List<RecommendedItem> getRecommendations(long userId, int numberOfRecommendations) 
            throws TasteException {
        
        if (recommender == null) {
            throw new IllegalStateException("Recommender not initialized. Call initializeRecommender() first.");
        }
        
        List<RecommendedItem> recommendations = 
            recommender.recommend(userId, numberOfRecommendations);
        
        return recommendations;
    }
    

    //Get detailed product recommendations with product information
    public void displayRecommendations(long userId, int numberOfRecommendations) 
            throws TasteException {
        
        List<RecommendedItem> recommendations = getRecommendations(userId, numberOfRecommendations);
        
        System.out.println("\n=== Recommendations for User " + userId + " ===");
        
        if (recommendations.isEmpty()) {
            System.out.println("No recommendations available for this user.");
            return;
        }
        
        for (int i = 0; i < recommendations.size(); i++) {
            RecommendedItem item = recommendations.get(i);
            Product product = productCatalog.get(item.getItemID());
            
            System.out.printf("%d. Product ID: %d | Predicted Rating: %.2f\n", 
                             i + 1, item.getItemID(), item.getValue());
            
            if (product != null) {
                System.out.printf("   Name: %s | Category: %s | Price: $%.2f\n", 
                                 product.getName(), product.getCategory(), product.getPrice());
            }
            System.out.println();
        }
    }
    

    //Estimate preference for a user-item pair
    public float estimatePreference(long userId, long itemId) throws TasteException {
        if (recommender == null) {
            throw new IllegalStateException("Recommender not initialized.");
        }
        
        return recommender.estimatePreference(userId, itemId);
    }
    

    //Initialize sample product catalog
    private void initializeProductCatalog() {
        // Sample products - in a real system, this would come from a database
        productCatalog.put(1L, new Product(1L, "Laptop", "Electronics", 999.99, "High-performance laptop"));
        productCatalog.put(2L, new Product(2L, "Smartphone", "Electronics", 699.99, "Latest smartphone"));
        productCatalog.put(3L, new Product(3L, "Coffee Maker", "Appliances", 89.99, "Automatic coffee maker"));
        productCatalog.put(4L, new Product(4L, "Book: Java Programming", "Books", 45.99, "Learn Java programming"));
        productCatalog.put(5L, new Product(5L, "Wireless Headphones", "Electronics", 199.99, "Noise-cancelling headphones"));
        productCatalog.put(6L, new Product(6L, "Running Shoes", "Sports", 129.99, "Comfortable running shoes"));
        productCatalog.put(7L, new Product(7L, "Tablet", "Electronics", 399.99, "10-inch tablet"));
        productCatalog.put(8L, new Product(8L, "Camera", "Electronics", 549.99, "Digital camera"));
        productCatalog.put(9L, new Product(9L, "Fitness Tracker", "Sports", 249.99, "Track your fitness"));
        productCatalog.put(10L, new Product(10L, "Bluetooth Speaker", "Electronics", 79.99, "Portable speaker"));
    }
    
    //Add a product to the catalog
    public void addProduct(Product product) {
        productCatalog.put(product.getProductId(), product);
    }
    
    //Get product by ID
    public Product getProduct(long productId) {
        return productCatalog.get(productId);
    }
    
    //Display all available products
    public void displayAllProducts() {
        System.out.println("\n=== Available Products ===");
        for (Product product : productCatalog.values()) {
            System.out.println(product);
        }
    }
}