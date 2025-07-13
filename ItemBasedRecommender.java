package task4;

import java.util.*;

public class ItemBasedRecommender {

    // Item -> (User -> Rating)
    static Map<String, Map<String, Integer>> itemRatings = new HashMap<>();

    public static void main(String[] args) {
        // Step 1: Set up item-user-rating data
        itemRatings.put("Book", Map.of("U1", 5, "U2", 3, "U3", 4));
        itemRatings.put("Movie", Map.of("U1", 4, "U2", 2, "U3", 5));
        itemRatings.put("Game", Map.of("U1", 2, "U2", 5, "U3", 3));

        // Step 2: Get item name from user
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the item name to get similar items (e.g., Book): ");
        String inputItem = scanner.nextLine();

        if (!itemRatings.containsKey(inputItem)) {
            System.out.println("Item not found in database.");
            return;
        }

        // Step 3: Compare with other items
        System.out.println("\nItems similar to '" + inputItem + "':");
        for (String otherItem : itemRatings.keySet()) {
            if (!otherItem.equals(inputItem)) {
                double similarity = cosineSimilarity(itemRatings.get(inputItem), itemRatings.get(otherItem));
                System.out.printf(" - %s (Similarity: %.2f)\n", otherItem, similarity);
            }
        }
    }

    // Cosine Similarity between two item rating vectors
    static double cosineSimilarity(Map<String, Integer> a, Map<String, Integer> b) {
        Set<String> commonUsers = new HashSet<>(a.keySet());
        commonUsers.retainAll(b.keySet());

        double dotProduct = 0, normA = 0, normB = 0;
        for (String user : commonUsers) {
            int valA = a.get(user);
            int valB = b.get(user);
            dotProduct += valA * valB;
        }

        for (int val : a.values()) normA += val * val;
        for (int val : b.values()) normB += val * val;

        return (normA == 0 || normB == 0) ? 0 : dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }
}
