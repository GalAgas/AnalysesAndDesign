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

    public void setWebUser(WebUser webUser) {
        this.webUser = webUser;
    }

    public ShoppingCart(WebUser webUser, Account account) {
        this.autoID += 1;
        this.ID = autoID.toString();
        this.webUser = webUser;
        this.account = account;
        this.lineItems = new ArrayList<>();
    }

    public WebUser getWebUser() {
        return webUser;
    }

    public void addLineItem(LineItem lineItem) {
        if (!lineItems.contains(lineItem))
        {
            this.lineItems.add(lineItem);
        }

    }

    @Override
    public String toString() {
        return "ShoppingCart:\n" +
                "ID: " + ID;
    }

    public String getID() {
        return ID;
    }

    public String displayCart(){
        return this.toString() + "Created at: " + this.created;
    }

    public ArrayList<LineItem> getLineItems() {
        return lineItems;
    }
}
