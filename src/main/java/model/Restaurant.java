package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
public class Restaurant {
        private String restaurantId;
        private Cuisine cuisine;
        private Integer costBracket;
        private Float rating;
        private Boolean isRecommended;
        private Instant onboardedTime;

        public Float getRating(){
                return this.rating == null ? 0.0F : this.rating;
        }
}
