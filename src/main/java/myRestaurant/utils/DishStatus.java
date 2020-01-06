package myRestaurant.utils;

public enum DishStatus {
    NEW("NEW"),
    IN_COOKING("IN_COOKING"),
    COOKED("COOKED"),
    CLOSED("CLOSED");

    private String title;

    DishStatus(String title){
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    }

