import java.util.ArrayList;
import java.util.Date;

public class ShoppingCart {
    private Date created;

    // Associations
    private ArrayList<LineItem> lineItems;
    private WebUser webUser;
    private Account account;

    public ShoppingCart(WebUser webUser, Account account) {
        this.webUser = webUser;
        this.account = account;
    }
}
