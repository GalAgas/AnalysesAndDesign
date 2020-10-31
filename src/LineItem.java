import java.util.ArrayList;

public class LineItem {
    private Product product;
    private int quantity;
    private int price;
    private boolean isOrdered; // line item goes only to one order

    public LineItem(Product p, int quantity, int price) {
        this.product = p;
        this.quantity = quantity;
        this.price = price;
        this.isOrdered = false;
        product.addLineItem(this); // add the line item to product object.
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPrice() {
        return price;
    }

    public boolean isOrdered() {
        return isOrdered;
    }

    public void setOrdered(boolean ordered) {
        isOrdered = ordered;
    }

    @Override
    public String toString() {
        return product.toString() +" quantity:" + this.quantity + ", price: "+ this.price;
    }
}
