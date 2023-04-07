package services;

import model.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RecommendationService {

    public List<String> getRestaurantRecommendations(User user,
                                                     List<Restaurant> restaurantList){

        Cuisine primaryCuisine;
        Cuisine secondaryCuisine1;
        Cuisine secondaryCuisine2;
        Integer primaryCostBracket;
        Integer secondaryCostBracket1;
        Integer secondaryCostBracket2;

        List<CuisineTracking> cuisineTracking = user.getCuisineTracking();
        Collections.sort(cuisineTracking, Comparator.comparingInt(CuisineTracking::getNoOfOrders).reversed());
        List<CostTracking> costTrackings = user.getCostBracket();
        Collections.sort(costTrackings, Comparator.comparingInt(CostTracking::getNoOfOrders).reversed());

        primaryCuisine = cuisineTracking.size() > 0 ? cuisineTracking.get(0).getCuisine() : null;
        secondaryCuisine1 = cuisineTracking.size() > 1 ? cuisineTracking.get(1).getCuisine() : null;
        secondaryCuisine2 = cuisineTracking.size() > 2 ? cuisineTracking.get(2).getCuisine() : null;
        List<Cuisine> secondaryCuisines = Arrays.asList(secondaryCuisine1, secondaryCuisine2);

        primaryCostBracket = costTrackings.size() > 0 ? costTrackings.get(0).getType() : null;
        secondaryCostBracket1 = costTrackings.size() > 1 ? costTrackings.get(1).getType() : null;
        secondaryCostBracket2 = costTrackings.size() > 2 ? costTrackings.get(2).getType() : null;
        List<Integer> secondaryCostBrackets = Arrays.asList(secondaryCostBracket1, secondaryCostBracket2);


        PriorityQueue<Restaurant> recommendedPrimaryCuisinePrimaryCostBracket =
                new PriorityQueue<>((o1, o2) -> Math.round(o2.getRating() - o1.getRating()));

        PriorityQueue<Restaurant> recommendedPrimaryCuisineSecondaryCostBracket =
                new PriorityQueue<>((o1, o2) -> Math.round(o2.getRating() - o1.getRating()));

        PriorityQueue<Restaurant> recommendedSecondaryCuisinePrimaryCostBracket =
                new PriorityQueue<>((o1, o2) -> Math.round(o2.getRating() - o1.getRating()));

        PriorityQueue<Restaurant> primaryCuisinePrimaryCostBracketRatingMore4 =
                new PriorityQueue<>((o1, o2) -> Math.round(o2.getRating() - o1.getRating()));

        PriorityQueue<Restaurant> primaryCuisineSecondaryCostBracketRatingMore4_5 =
                new PriorityQueue<>((o1, o2) -> Math.round(o2.getRating() - o1.getRating()));

        PriorityQueue<Restaurant> secondaryCuisinePrimaryCostBracketRatingMore4_5 =
                new PriorityQueue<>((o1, o2) -> Math.round(o2.getRating() - o1.getRating()));

        PriorityQueue<Restaurant> newlyRatedRestuarant =
                new PriorityQueue<>((o1, o2) -> Math.round(o2.getRating() - o1.getRating()));

        PriorityQueue<Restaurant> primaryCuisinePrimaryCostBracketRatingLess4 =
                new PriorityQueue<>((o1, o2) -> Math.round(o2.getRating() - o1.getRating()));

        PriorityQueue<Restaurant> primaryCuisineSecondaryCostBracketRatingLess4_5 =
                new PriorityQueue<>((o1, o2) -> Math.round(o2.getRating() - o1.getRating()));

        PriorityQueue<Restaurant> secondaryCuisinePrimaryCostBracketRatingLess4_5 =
                new PriorityQueue<>((o1, o2) -> Math.round(o2.getRating() - o1.getRating()));

        PriorityQueue<Restaurant> remainingRestaurants =
                new PriorityQueue<>((o1, o2) -> Math.round(o2.getRating() - o1.getRating()));

        for(Restaurant restaurant: restaurantList){
            //Order 1
            if(Boolean.TRUE.equals(restaurant.getIsRecommended()) && restaurant.getCuisine().equals(primaryCuisine) && restaurant.getCostBracket().equals(primaryCostBracket))
                recommendedPrimaryCuisinePrimaryCostBracket.add(restaurant);
            else if(Boolean.TRUE.equals(restaurant.getIsRecommended()) && restaurant.getCuisine().equals(primaryCuisine) && secondaryCostBrackets.contains(restaurant.getCostBracket()))
                recommendedPrimaryCuisineSecondaryCostBracket.add(restaurant);
            else if(Boolean.TRUE.equals(restaurant.getIsRecommended()) && secondaryCuisines.contains(restaurant.getCuisine()) && restaurant.getCostBracket().equals(primaryCostBracket))
                recommendedSecondaryCuisinePrimaryCostBracket.add(restaurant);
            //Order 2
            else if(restaurant.getCuisine().equals(primaryCuisine) && restaurant.getCostBracket().equals(primaryCostBracket) && restaurant.getRating() >= 4)
                primaryCuisinePrimaryCostBracketRatingMore4.add(restaurant);
            //Order 3
            else if(restaurant.getCuisine().equals(primaryCuisine) && secondaryCostBrackets.contains(restaurant.getCostBracket()) && restaurant.getRating() >= 4.5)
                primaryCuisineSecondaryCostBracketRatingMore4_5.add(restaurant);
            //Order 4
            else if(secondaryCuisines.contains(restaurant.getCuisine()) && restaurant.getCostBracket().equals(primaryCostBracket) && restaurant.getRating() >= 4.5)
                secondaryCuisinePrimaryCostBracketRatingMore4_5.add(restaurant);
            //Order 5
            else if(restaurant.getOnboardedTime().isAfter(Instant.now().minus(2, ChronoUnit.DAYS)))
                newlyRatedRestuarant.add(restaurant);
            //Order 6
            else if(restaurant.getCuisine().equals(primaryCuisine) && restaurant.getCostBracket().equals(primaryCostBracket) && restaurant.getRating() < 4)
                primaryCuisinePrimaryCostBracketRatingLess4.add(restaurant);
            //Order 7
            else if(restaurant.getCuisine().equals(primaryCuisine) && secondaryCostBrackets.contains(restaurant.getCostBracket()) && restaurant.getRating() < 4.5)
                primaryCuisineSecondaryCostBracketRatingLess4_5.add(restaurant);
            //Order 8
            else if(secondaryCuisines.contains(restaurant.getCuisine()) && restaurant.getCostBracket().equals(primaryCostBracket) && restaurant.getRating() < 4.5)
                secondaryCuisinePrimaryCostBracketRatingLess4_5.add(restaurant);
            //Order 9
            else
                remainingRestaurants.add(restaurant);
        }
        List<Restaurant> sortedRestaurants = Stream.of(
                recommendedPrimaryCuisinePrimaryCostBracket.stream().collect(Collectors.toList()),
                recommendedPrimaryCuisineSecondaryCostBracket.stream().collect(Collectors.toList()),
                recommendedSecondaryCuisinePrimaryCostBracket.stream().collect(Collectors.toList()),
                primaryCuisinePrimaryCostBracketRatingMore4.stream().collect(Collectors.toList()),
                primaryCuisineSecondaryCostBracketRatingMore4_5.stream().collect(Collectors.toList()),
                secondaryCuisinePrimaryCostBracketRatingMore4_5.stream().collect(Collectors.toList()),
                newlyRatedRestuarant.stream().collect(Collectors.toList()),
                primaryCuisinePrimaryCostBracketRatingLess4.stream().collect(Collectors.toList()),
                primaryCuisineSecondaryCostBracketRatingLess4_5.stream().collect(Collectors.toList()),
                secondaryCuisinePrimaryCostBracketRatingLess4_5.stream().collect(Collectors.toList()),
                remainingRestaurants.stream().collect(Collectors.toList())
        )
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        return sortedRestaurants.stream().map(restaurant -> restaurant.getRestaurantId()).collect(Collectors.toList());
    }
}
