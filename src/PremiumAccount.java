import java.util.ArrayList;

public class PremiumAccount extends Account {

    // Associations
    private ArrayList<Product> products;

    public PremiumAccount(String id, String billing_address, WebUser webUser, Customer customer) {
        super(id, billing_address, webUser, customer);
        products = new ArrayList<>();
    }

    public void addProduct(Product product){
        if (!products.contains(product)){
            products.add(product);
        }

    }

    public void removeAssociated(){
        super.removeAssociated();
        for(Product p: products){
            p.setPremiumAccount(null, 0 );
        }
        this.products = null;
    }

    public void removeProduct(Product product){
        if (product != null)
        {
            products.remove(product);
        }
    }

    public void showAssociated(){
        super.showAssociated();
        if (this.products != null){
            for (Product p: this.products){
                System.out.println(p);
            }
        }
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }
}
