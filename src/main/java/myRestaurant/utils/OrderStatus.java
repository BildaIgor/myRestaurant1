package myRestaurant.utils;

public enum OrderStatus {
    OPENED ("OPENED"),
    CLOSED("CLOSED"),
    PAID("PAID"),
    READY_FOR_REPORTED("READY_FOR_REPORTED"),
    REPORTED("REPORTED");
    private String title;

    OrderStatus(String title){
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
