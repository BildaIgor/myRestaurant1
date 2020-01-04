package myRestaurant.utils;

public enum OrderStatus {
    NEW ("NEW"),
    PROCESSED("PROCESSED"),
    READY_FOR_CLOSE("READY_FOR_CLOSE"),
    CLOSE("CLOSE");
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
