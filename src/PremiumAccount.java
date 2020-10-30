import java.util.ArrayList;

public class PremiumAccount extends Account {

    // Associations
    private ArrayList<Product> products;

    public PremiumAccount(String id, String billing_address, WebUser webUser, Customer customer) {
        super(id, billing_address, webUser, customer);
    }
}
