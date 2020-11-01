import java.util.ArrayList;

public class LineItem {
    private int quantity;
    private int price;
    private boolean isOrdered; // line item goes only to one order
    private static Integer autoID=0;
    private String ID;

    // Associationsssss
    private Order order;
    private ShoppingCart shoppingCart;
    private Product product;



    public LineItem(Product p, int quantity, int price, Order order, ShoppingCart shoppingCart) {
        this.autoID += 1;
        this.ID = autoID.toString();
        this.product = p;
        this.order = order;
        this.shoppingCart = shoppingCart;
        this.quantity = quantity;
        this.price = price;
        this.isOrdered = false;
        this.product.addLineItem(this); // add the line item to product object.
        this.order.addLineItem(this);
        this.shoppingCart.addLineItem(this);

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
