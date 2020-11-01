import java.util.ArrayList;

public class Product {
    private String id;
    private String name;

    // Associations
    private Supplier supplier;
    private ArrayList<LineItem> lineItems;
    private PremiumAccount premiumAccount;


    public ArrayList<LineItem> getLineItems() {
        return lineItems;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

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
        if (!lineItems.contains(lineItem))
        {
            lineItems.add(lineItem);
        }

    }

    @Override
    public String toString() {
        return "Product: \nProduct id: " + id + ", name: " + name;
    }

    public String displayProduct(){
        String details = this.toString() + "\nSupplier:\n";
        details+=this.supplier.getName() +"\n";
        if(this.premiumAccount != null){
            details += this.premiumAccount.getId() + "\n";
        }
        for(LineItem li: this.getLineItems()){
            details+= li.getID() + ", ";
        }
        if(this.getLineItems().size() > 1) return details.substring(0, details.length()-2);
        return details;
    }
}
