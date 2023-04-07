import model.*;
import services.RecommendationService;

import java.io.IOException;
import java.time.Instant;
import java.util.*;

public class RestaurantRecommendation {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        FoodApp foodApp = new FoodApp();

        while(true) {
            System.out.println("Enter choice - 1 Add Restaurant, 2 Add User Order, 3 Get User Recommendation");
            int ch = sc.nextInt();
            switch (ch) {
                case 1:
                {
                    List<Restaurant> restaurants = null != foodApp.getRestaurants() ? foodApp.getRestaurants() : new ArrayList<>();

                    System.out.println("Enter number of restaurants : ");
                    int countOfRestaurants = sc.nextInt();
                    for (int i = 1; i <= countOfRestaurants; i++) {
                        System.out.println("Enter details for restaurant: " + i);
                        System.out.println("Restaurant Id(String): ");
                        String restaurantId = sc.next();
                        System.out.println("Cuisine(String): SouthIndian/NorthIndian/Chinese/Other");
                        String cuisine = sc.next();
                        System.out.println("Cost Bracket (Integer): ");
                        Integer costBracket = sc.nextInt();
                        System.out.println("Rating (Double): ");
                        Float rating = sc.nextFloat();
                        System.out.println("Is Recommended (true/false): ");
                        Boolean isRecommended = sc.nextBoolean();

                        Restaurant restaurant =
                                new Restaurant(restaurantId, null != cuisine ?
                                        Cuisine.valueOf(cuisine.toUpperCase(Locale.ROOT)) : null, costBracket, rating, isRecommended, Instant.now());
                        restaurants.add(restaurant);
                    }
                    foodApp.setRestaurants(restaurants);
                }
                break;

                case 2:
                {
                    List<User> onboardedUsers = null != foodApp.getUsers() ? foodApp.getUsers() : new ArrayList<>();
                    List<Restaurant> onboardedRestaurants = null != foodApp.getRestaurants() ? foodApp.getRestaurants() : new ArrayList<>();
                    System.out.println("Enter number of user orders : ");
                    int countOfUserOrders = sc.nextInt();
                    for (int i = 1; i <= countOfUserOrders; i++) {
                        System.out.println("Enter order details for user [UserId, RestaurantId]: ");
                        System.out.println("User Id(String): ");
                        String userId = sc.next();
                        System.out.println("Restuarant Id(String): ");
                        String restaurantId = sc.next();
                        Optional<Restaurant> restaurant = onboardedRestaurants.stream()
                                .filter(restaurant1 -> restaurant1.getRestaurantId().equals(restaurantId)).findFirst();
                        if (restaurant.isEmpty()) {
                            System.out.println("Error: Enter existing restaurant id");
                            continue;
                        }

                        Cuisine cuisine = restaurant.get().getCuisine();
                        Integer costBracket = restaurant.get().getCostBracket();

                        Optional<User> user = onboardedUsers.stream().filter(user1 -> user1.getUserId().equalsIgnoreCase(userId)).findFirst();
                        if (user.isEmpty()) {
                            user = Optional.of(new User(userId));
                            onboardedUsers.add(user.get());
                        }

                        List<CuisineTracking> cuisineTrackings = null != user.get().getCuisineTracking() ? user.get().getCuisineTracking() : new ArrayList<>();
                        Optional<CuisineTracking> existingCuisineTracking = cuisineTrackings
                                .stream()
                                .filter(cuisineTracking -> cuisineTracking.getCuisine().equals(cuisine))
                                .findFirst();
                        if (existingCuisineTracking.isPresent()) {
                            int noOfOrder = existingCuisineTracking.get().getNoOfOrders();
                            existingCuisineTracking.get().setNoOfOrders(noOfOrder + 1);
                        } else {
                            cuisineTrackings.add(new CuisineTracking(cuisine, 1));
                        }

                        List<CostTracking> costTrackings = null != user.get().getCostBracket() ? user.get().getCostBracket() : new ArrayList<>();
                        Optional<CostTracking> existingCostTracking = costTrackings
                                .stream()
                                .filter(costTracking -> costTracking.getType() == costBracket)
                                .findFirst();
                        if (existingCostTracking.isPresent()) {
                            int noOfOrder = existingCostTracking.get().getNoOfOrders();
                            existingCostTracking.get().setNoOfOrders(noOfOrder + 1);
                        } else {
                            costTrackings.add(new CostTracking(costBracket, 1));
                        }
                        user.get().setCostBracket(costTrackings);
                        user.get().setCuisineTracking(cuisineTrackings);
                    }
                    foodApp.setUsers(onboardedUsers);
                }
                break;
                case 3:
                {
                    List<User> onboardedUsers = null != foodApp.getUsers() ? foodApp.getUsers() : new ArrayList<>();
                    List<Restaurant> onboardedRestaurants = null != foodApp.getRestaurants() ? foodApp.getRestaurants() : new ArrayList<>();

                    System.out.println("Enter user id:");
                    String userId = sc.next();
                    Optional<User> user = onboardedUsers.stream().filter(user1 -> user1.getUserId().equalsIgnoreCase(userId)).findFirst();
                    if (user.isEmpty()) {
                        System.out.println("Error: Enter valid user id");
                    }
                    RecommendationService recommendationService = new RecommendationService();
                    List<String> recommendations = recommendationService.getRestaurantRecommendations(user.get(), onboardedRestaurants);
                    System.out.println(recommendations.toString());
                }
                break;
            }
        }

    }
}
