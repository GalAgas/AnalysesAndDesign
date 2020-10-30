import java.util.ArrayList;
import java.util.Date;

public class Order {

    private String number;
    private Date ordered;
    private Date shipped;
    private String ship_to; //Address???
    private OrderStatus orderStatus;
    private float total;

    // Associations
    private Account account;
    private ArrayList<Payment> payments;
    private ArrayList<LineItem> lineItems;
}
