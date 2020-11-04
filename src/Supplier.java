import java.util.ArrayList;

public class Supplier {
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    // Associations
    private ArrayList<Product> products;

    public Supplier(String id, String name) {
        this.id = id;
        this.name = name;
        products = new ArrayList<>();
    }

    public void addProduct(Product product)
    {
        products.add(product);
    }

    public void removeProduct(Product product)
    {
        products.remove(product);
    }

    @Override
    public String toString() {
        return "Supplier:\n"+
                "\tID: " + id +
                ", name: " + name;
    }

    public String showAllDetails(){
        return this.toString();
    }

    public void showAssociated() {
        if (this.products != null){
            for (Product p: this.products){
                System.out.println(p);
            }
        }
    }
}
