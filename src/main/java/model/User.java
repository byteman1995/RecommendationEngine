package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class User {
    private String userId;
    private List<CuisineTracking> cuisineTracking;
    private List<CostTracking> costBracket;

    public User(String userId){
        this.userId = userId;
    }
    //More fields can be accordingly added
}
