package model;

public enum Cuisine {
    OTHER(0),
    SOUTHINDIAN(1),
    NORTHINDIAN(2),
    CHINESE(3);

    private int value;

    Cuisine(int val){
        this.value = val;
    }
}
