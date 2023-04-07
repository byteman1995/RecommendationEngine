import model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import services.RecommendationService;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class GetRestaurantRecommendationsTests {
    @InjectMocks
    RecommendationService recommendationService;

    @Before
    public void initMocks() {
        MockitoAnnotations.openMocks(this);
    }

    private Restaurant getRestaurant(String id, Cuisine cuisine, Integer costBracket, Float rating, boolean isRecommended){
        return new Restaurant(id, cuisine, costBracket, rating, isRecommended, Instant.now());
    }

    private CostTracking getCostTrackings(Integer type, Integer noOfOrders){
        return new CostTracking(type, noOfOrders);
    }

    private CuisineTracking getCuisineTrackings(Cuisine cuisine, Integer noOfOrders){
        return new CuisineTracking(cuisine, noOfOrders);
    }

    @Test
    public void testRestaurantRecommendation1(){
        Restaurant restaurant1 = getRestaurant("1", Cuisine.NORTHINDIAN, 1, 4.5F, false);
        Restaurant restaurant2 = getRestaurant("2", Cuisine.CHINESE, 2, 4.5F, false);
        List<Restaurant> restaurants = Arrays.asList(restaurant1, restaurant2);

        CostTracking costTracking1 = getCostTrackings(1, 5);
        CostTracking costTracking2 = getCostTrackings(2, 1);
        List<CostTracking> costTrackings = Arrays.asList(costTracking1, costTracking2);

        CuisineTracking cuisineTracking1 = getCuisineTrackings(Cuisine.NORTHINDIAN, 5);
        CuisineTracking cuisineTracking2 = getCuisineTrackings(Cuisine.CHINESE, 1);
        List<CuisineTracking> cuisineTrackings = Arrays.asList(cuisineTracking1, cuisineTracking2);

        User user = new User("1", cuisineTrackings, costTrackings);
        List<String> expectedRecommendations = Arrays.asList("1", "2");
        List<String> actualRecommendations = recommendationService.getRestaurantRecommendations(user, restaurants);
        Assert.assertEquals(expectedRecommendations.toString(), actualRecommendations.toString());
    }

    @Test
    public void testRestaurantRecommendation2(){
        Restaurant restaurant1 = getRestaurant("1", Cuisine.NORTHINDIAN, 1, 4.5F, false);
        Restaurant restaurant2 = getRestaurant("2", Cuisine.CHINESE, 2, 4.5F, true);
        List<Restaurant> restaurants = Arrays.asList(restaurant1, restaurant2);

        CostTracking costTracking1 = getCostTrackings(1, 5);
        CostTracking costTracking2 = getCostTrackings(2, 1);
        List<CostTracking> costTrackings = Arrays.asList(costTracking1, costTracking2);

        CuisineTracking cuisineTracking1 = getCuisineTrackings(Cuisine.NORTHINDIAN, 5);
        CuisineTracking cuisineTracking2 = getCuisineTrackings(Cuisine.CHINESE, 1);
        List<CuisineTracking> cuisineTrackings = Arrays.asList(cuisineTracking1, cuisineTracking2);

        User user = new User("1", cuisineTrackings, costTrackings);
        List<String> expectedRecommendations = Arrays.asList("1", "2");
        List<String> actualRecommendations = recommendationService.getRestaurantRecommendations(user, restaurants);
        Assert.assertEquals(expectedRecommendations.toString(), actualRecommendations.toString());
    }

}
