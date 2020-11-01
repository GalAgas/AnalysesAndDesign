import java.util.ArrayList;
import java.util.Date;

public class ShoppingCart {
    private Date created;
    private static Integer autoID=0;
    private String ID;


    // Associations
    private ArrayList<LineItem> lineItems;
    private WebUser webUser;
    private Account account;

    public ShoppingCart(WebUser webUser, Account account) {
        this.autoID += 1;
        this.ID = autoID.toString();
        this.webUser = webUser;
        this.account = account;
        this.lineItems = new ArrayList<>();
    }

    public void addLineItem(LineItem lineItem) {
        if (!lineItems.contains(lineItem))
        {
            this.lineItems.add(lineItem);
        }

    }

    public void removeLineItem(LineItem lineItem) {
        this.lineItems.remove(lineItem);
    }
}
