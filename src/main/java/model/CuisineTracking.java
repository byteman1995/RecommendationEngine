package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CuisineTracking {
    private Cuisine cuisine;
    private int noOfOrders;

}
