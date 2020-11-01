import java.util.HashMap;

public class ShoppingSystem {
    private HashMap<String, WebUser> webUsers;
    private HashMap<String, Supplier> suppliers;
    private HashMap<String, Product> idsToProducts;

    private WebUser currentLoggedIn;
//    private ArrayList<Payment> payments;

    public ShoppingSystem() {
        this.webUsers = new HashMap<>();
        this.suppliers = new HashMap<>();
        this.idsToProducts = new HashMap<>();
        this.currentLoggedIn = null;


    }

    public void addUser(String login_id){

    }

    public void removeUser(String login_id){

    }

    public void logIn(String login_id){

    }

    public void logOut(String login_id){

    }

    public Order makeNewOrder(){
        if(this.currentLoggedIn == null){
            //exception
        }
        return new Order(this.currentLoggedIn.getCustomer().getAccount());
    }
    public void addlineItetmtoOrder(Order order, Product pToadd, int amount){
        LineItem l = new LineItem(pToadd, amount, pToadd.getPrice()*amount,order, this.currentLoggedIn.getShoppingCart());
    }

    public void DisplayOrder(){
        Account cur_account = this.currentLoggedIn.getCustomer().getAccount();
        Order o = cur_account.getOrders().get(cur_account.getOrders().size()-1);

        System.out.println(o);

    }

    public void linkProductToPrem(String productName){
        if(!(this.currentLoggedIn.getCustomer().getAccount() instanceof PremiumAccount)){
            // exception!!!
        }
        if(!this.idsToProducts.containsKey(productName)){
            //exception no such product in system!!!
        }
        Product p = this.idsToProducts.get(productName);
        if (p.getPremiumAccount() != null){
            //exception product allready connected to another preium account
        }
        PremiumAccount a = (PremiumAccount) this.currentLoggedIn.getCustomer().getAccount();
        p.setPremiumAccount(a);
        a.addProduct(p);

    }

    public void addProduct(String productName, String productid, Supplier supplier){
        Product p = new Product(productid, productName, supplier);
        this.idsToProducts.put(p.getId(), p);
    }

    public void deleteProduct(String productName){
        if (!this.idsToProducts.containsKey(productName)){
            //exception product doesnt exist
        }
        Product p = idsToProducts.get(productName);
        idsToProducts.remove(productName);

        p.getPremiumAccount().removeProduct(p);
        p.setPremiumAccount(null);

        p.getSupplier().removeProduct(p);
        p.setSupplier(null);

        for (LineItem l:p.getLineItems()) {
            l.getOrder().removeLineItem(l);
            l.setOrder(null);

            l.getShoppingCart().removeLineItem(l);
            l.setShoppingCart(null);

            l.setProduct(null);
        }

    }

    public void showAllObjects(){

    }

    public void showObject(String id){

    }

    public WebUser getCurrentLoggedIn() {
        return currentLoggedIn;
    }

    public void setCurrentLoggedIn(WebUser currentLoggedIn) {
        this.currentLoggedIn = currentLoggedIn;
    }

    public HashMap<String, WebUser> getWebUsers() {
        return webUsers;
    }

    public void setWebUsers(HashMap<String, WebUser> webUsers) {
        this.webUsers = webUsers;
    }

    public HashMap<String, Supplier> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(HashMap<String, Supplier> suppliers) {
        this.suppliers = suppliers;
    }

    public HashMap<String, Product> getIdsToProducts() {
        return idsToProducts;
    }

    public void setIdsToProducts(HashMap<String, Product> idsToProducts) {
        this.idsToProducts = idsToProducts;
    }
}
