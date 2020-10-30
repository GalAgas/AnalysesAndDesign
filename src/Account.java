import java.util.ArrayList;
import java.util.Date;

public class Account {
    private String id;
    private String billing_address;
    private boolean is_closed;
    private Date open;
    private Date closed;
    private int balance;

    // Associations
    private ArrayList<Payment> payments;
    private ArrayList<Order> orders;
    private ShoppingCart shoppingCart;

}
