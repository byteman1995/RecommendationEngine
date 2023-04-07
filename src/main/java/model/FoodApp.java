package model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FoodApp {
    List<Restaurant> restaurants;
    List<User> users;
}
