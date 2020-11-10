import java.util.ArrayList;
import java.util.Date;

public class ShoppingCart {
    private Date created;
//    private static Integer autoID=0;
    private String ID;


    // Associations
    private ArrayList<LineItem> lineItems;
    private WebUser webUser;
    private Account account;

    public void setWebUser(WebUser webUser) {
        this.webUser = webUser;
    }

    public ShoppingCart(String id, WebUser webUser, Account account) {
//        this.autoID += 1;
        this.ID = id;
        this.created = new Date();
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

    public ArrayList<LineItem> getLineItems() {
        return lineItems;
    }

    public String getID() {
        return ID;
    }

    @Override
    public String toString() {
        return "ShoppingCart:\n" +
                "\tID: " + ID;
    }

    public String showAllDetails(){
        return this.toString() + ", Created at: " + this.created;
    }

    public void removeAssociated() {
        this.webUser = null;
        this.account = null;
        for(LineItem li: this.lineItems){
            li.removeAssociated();
        }
        this.lineItems = null;
    }

    public void showAssociated() {
        if (this.webUser != null) System.out.println(this.webUser);
        if (this.account != null) System.out.println(this.account);
        if (this.lineItems != null) {
            for (LineItem li : this.lineItems) {
                System.out.println(li);
            }
        }
    }
}
