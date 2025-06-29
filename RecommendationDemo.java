import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


//  RecommendationSystemDemo.java
// Main application class that demonstrates the recommendation system


public class RecommendationDemo {
    
    private static RecommendationService recommendationService;
    private static final String DATA_FILE = "ratings.csv";
    
    public static void main(String[] args) {
        try {
            System.out.println("=== Product Recommendation System Demo ===");
            System.out.println("Using Apache Mahout for Collaborative Filtering\n");
            
            // Initialize the recommendation service
            recommendationService = new RecommendationService();
            
            // Create sample data file
            createSampleDataFile();
            
            // Initialize the recommender with the data file
            recommendationService.initializeRecommender(DATA_FILE);
            
            // Display available products
            recommendationService.displayAllProducts();
            
            // Run interactive demo
            runInteractiveDemo();
            
        } catch (Exception e) {
            System.err.println("Error running recommendation system: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    //Create a sample CSV file with user ratings
    //Format: userID,itemID,rating
    private static void createSampleDataFile() throws IOException {
        System.out.println("Creating sample data file...");
        
        try (FileWriter writer = new FileWriter(DATA_FILE)) {
            // Sample rating data (userID, itemID, rating)
            // Users 1-5 rating products 1-10 with ratings 1-5
            String[] sampleData = {
                "1,1,5.0",  // User 1 loves laptops
                "1,2,4.0",  // User 1 likes smartphones
                "1,5,5.0",  // User 1 loves headphones
                "1,7,4.0",  // User 1 likes tablets
                "1,8,3.0",  // User 1 neutral on cameras
                
                "2,1,4.0",  // User 2 likes laptops
                "2,3,5.0",  // User 2 loves coffee makers
                "2,4,5.0",  // User 2 loves programming books
                "2,6,3.0",  // User 2 neutral on shoes
                "2,10,4.0", // User 2 likes speakers
                
                "3,2,5.0",  // User 3 loves smartphones
                "3,5,4.0",  // User 3 likes headphones
                "3,7,5.0",  // User 3 loves tablets
                "3,8,4.0",  // User 3 likes cameras
                "3,9,5.0",  // User 3 loves fitness trackers
                
                "4,3,4.0",  // User 4 likes coffee makers
                "4,4,3.0",  // User 4 neutral on books
                "4,6,5.0",  // User 4 loves running shoes
                "4,9,4.0",  // User 4 likes fitness trackers
                "4,10,3.0", // User 4 neutral on speakers
                
                "5,1,3.0",  // User 5 neutral on laptops
                "5,2,4.0",  // User 5 likes smartphones
                "5,6,4.0",  // User 5 likes shoes
                "5,8,5.0",  // User 5 loves cameras
                "5,9,3.0",  // User 5 neutral on fitness trackers
                
                // Additional users for better recommendations
                "6,1,4.0",
                "6,5,5.0",
                "6,7,4.0",
                "6,10,5.0",
                
                "7,2,3.0",
                "7,3,4.0",
                "7,4,5.0",
                "7,8,4.0",
                
                "8,6,5.0",
                "8,9,5.0",
                "8,10,3.0",
                "8,1,2.0"
            };
            
            for (String data : sampleData) {
                writer.write(data + "\n");
            }
        }
        
        System.out.println("Sample data file created: " + DATA_FILE);
    }
    
    //Run interactive demo allowing user to get recommendations
    private static void runInteractiveDemo() {
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            try {
                System.out.println("\n=== Interactive Demo ===");
                System.out.println("1. Get recommendations for a user");
                System.out.println("2. Estimate rating for user-item pair");
                System.out.println("3. Show all products");
                System.out.println("4. Exit");
                System.out.print("Choose an option: ");
                
                int choice = scanner.nextInt();
                
                switch (choice) {
                    case 1:
                        getRecommendationsForUser(scanner);
                        break;
                    case 2:
                        estimateRating(scanner);
                        break;
                    case 3:
                        recommendationService.displayAllProducts();
                        break;
                    case 4:
                        System.out.println("Thank you for using the Recommendation System!");
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
                
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
                scanner.nextLine(); // Clear the scanner buffer
            }
        }
    }
    
    //Get recommendations for a specific user
    private static void getRecommendationsForUser(Scanner scanner) throws TasteException {
        System.out.print("Enter user ID (1-8): ");
        long userId = scanner.nextLong();
        
        System.out.print("Enter number of recommendations (1-10): ");
        int numRecommendations = scanner.nextInt();
        
        recommendationService.displayRecommendations(userId, numRecommendations);
        
        // Also show basic list
        List<RecommendedItem> recommendations = 
            recommendationService.getRecommendations(userId, numRecommendations);
        
        System.out.println("\nBasic recommendation list:");
        for (RecommendedItem item : recommendations) {
            System.out.printf("Item %d: Rating %.2f\n", item.getItemID(), item.getValue());
        }
    }
    

    //Estimate rating for a user-item pair

    private static void estimateRating(Scanner scanner) throws TasteException {
        System.out.print("Enter user ID (1-8): ");
        long userId = scanner.nextLong();
        
        System.out.print("Enter product ID (1-10): ");
        long itemId = scanner.nextLong();
        
        try {
            float estimatedRating = recommendationService.estimatePreference(userId, itemId);
            Product product = recommendationService.getProduct(itemId);
            
            System.out.printf("\nEstimated rating for User %d and Product %d: %.2f\n", 
                             userId, itemId, estimatedRating);
            
            if (product != null) {
                System.out.println("Product: " + product.getName());
            }
            
        } catch (Exception e) {
            System.out.println("Could not estimate preference: " + e.getMessage());
        }
    }
}