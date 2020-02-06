package myRestaurant.utils;

public enum RemainderDishStatus {
    PLAY_LIST(500),
    STOP_LIST(0),
    RESTRICTION(10),

    NORMAL(100);

    private int quantity;

    RemainderDishStatus(int quantity){
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
