import java.util.ArrayList;

public class PremiumAccount extends Account {

    // Associations
    private ArrayList<Product> products;

    public PremiumAccount(String id, String billing_address, WebUser webUser, Customer customer) {
        super(id, billing_address, webUser, customer);
        products = new ArrayList<>();
    }

    public void addProduct(Product product){
        products.add(product);
    }

    public void removeProduct(Product product){
        products.remove(product);
    }
}
