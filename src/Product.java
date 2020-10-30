import java.util.ArrayList;

public class Product {
    private String id;
    private String name;

    // Associations
    private Supplier supplier;
    private ArrayList<LineItem> lineItems;
    private PremiumAccount premiumAccount;
}
