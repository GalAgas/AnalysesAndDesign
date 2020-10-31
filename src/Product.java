import java.util.ArrayList;

public class Product {
    private String id;
    private String name;

    // Associations
    private Supplier supplier;
    private ArrayList<LineItem> lineItems;
    private PremiumAccount premiumAccount;


    public Product(String id, String name, Supplier supplier) {
        this.id = id;
        this.name = name;
        this.supplier = supplier;
        this.supplier.addProduct(this);
        this.lineItems = new ArrayList<>();
        this.premiumAccount = null;

    }

    public PremiumAccount getPremiumAccount() {
        return premiumAccount;
    }

    //connects prem
    public void setPremiumAccount(PremiumAccount premiumAccount) {
        this.premiumAccount = premiumAccount;
    }

    //need to connect also from lineItem, !!
    public void addLineItem(LineItem lineItem){
        lineItems.add(lineItem);
    }

    @Override
    public String toString() {
        return "Product id: " + id + ", name: " + name;
    }
}
