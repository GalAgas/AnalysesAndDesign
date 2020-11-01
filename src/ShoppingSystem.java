import java.util.HashMap;

public class ShoppingSystem {
    private HashMap<String, WebUser> webUsers;
    private HashMap<String, Supplier> suppliers;
    private HashMap<String, Product> idsToProducts;

    private WebUser currentLoggedIn;

    public ShoppingSystem() {
        this.webUsers = new HashMap<>();
        this.suppliers = new HashMap<>();
        this.idsToProducts = new HashMap<>();
        this.initializeSystem();
        this.currentLoggedIn = null;


    }

    /**
     * Validation function - will be called from main before signing a new user in.
     * @param loginId String
     * @return boolean
     */
    public boolean idValidation(String loginId){
        return webUsers.containsKey(loginId);
    }

    /**
     * Signs up a new user into the system.
     * @param loginId String
     * @param password String
     * @param isPremium boolean
     * @param address String
     * @param phone String
     * @param email String
     * @throws Exception
     */
    public void addUser(String loginId, String password, boolean isPremium, String address,String phone,String email) throws Exception {
        if(idValidation(loginId)) throw new Exception("Id is not valid");
        WebUser newUser = new WebUser(loginId, password, isPremium, address, phone, email);
        webUsers.put(loginId, newUser);
    }

    /**
     * Removes a user from the system
     * @param loginId String
     * @throws Exception
     */
    public void removeUser(String loginId) throws Exception {
        if(!idValidation(loginId)) throw new Exception("Id is not valid");
        WebUser user = webUsers.get(loginId);
        user.getShoppingCart().setWebUser(null); //Blocks the way to webUser from ShoppingCart
        user.getCustomer().setWebUser(null); //Blocks the way to webUser from Customer
        webUsers.remove(user.getLogin_id());
    }

    /**
     * Logs a user into the system.
     * @param loginId String
     * @param password String
     * @throws Exception
     */
    public void logIn(String loginId, String password) throws Exception {
        if(!idValidation(loginId)) throw new Exception("Id is not valid");
        if(!webUsers.get(loginId).getPassword().equals(password)) throw new Exception("Incorrect password");
        this.currentLoggedIn = webUsers.get(loginId);
    }

    /**
     * Logs a user out of the system
     * @param loginId String
     * @throws Exception
     */
    public void logOut(String loginId) throws Exception {
        if(loginId.equals(this.currentLoggedIn.getLogin_id())) this.currentLoggedIn = null;
        else throw new Exception("You are not logged in.");
    }


    public Order makeNewOrder(){
        if(this.currentLoggedIn == null){
            //exception
        }
        return new Order(this.currentLoggedIn.getCustomer().getAccount());
    }
    public void addlineItetmtoOrder(Order order, Product pToadd, int amount){
        LineItem l = new LineItem(pToadd, amount, pToadd.getPrice()*amount,order, this.currentLoggedIn.getShoppingCart());


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

    /**
     * Displays all current objects
     */
    public void showAllObjects(){
        for(WebUser wu : webUsers.values()){
            System.out.println(wu);
            System.out.println(wu.getCustomer());
            System.out.println(wu.getCustomer().getAccount());
            System.out.println(wu.getShoppingCart());
            for(Payment p : wu.getCustomer().getAccount().getPayments().values()){
                p.displayPaymentId();
            }
            for(Order o : wu.getCustomer().getAccount().getOrders()){
                o.displayOrderNumber();
            }
        }
        for(Supplier supplier : suppliers.values()){
            System.out.println(supplier);
            for(Product p: supplier.getProducts()) {
                System.out.println(p);
                for(LineItem li : p.getLineItems()){
                    li.displayLineItem();
                }
            }
        }
    }

    /**
     * Displays a specific object
     * @param id String
     */
    public void showObject(String id){
        if(webUsers.containsKey(id)){
            WebUser wu = webUsers.get(id);
            System.out.println(wu.displayUser());

        }
        else if(suppliers.containsKey(id)) {
            Supplier s = suppliers.get(id);
            System.out.println(s.displaySupplier());
        }
        else if(idsToProducts.containsKey(id)) {
            Product p = (Product) idsToProducts.get(id);
            System.out.println(p.displayProduct());
        }
    }

    public WebUser getCurrentLoggedIn() {
        return currentLoggedIn;
    }

    /**
     * Helper function that inserts objects into the system
     */
    public void initializeSystem(){
        Supplier moshe = new Supplier("123", "Moshe");
        suppliers.put(moshe.getId(), moshe);
        Product bamba = new Product("Bamba", "Bamba", moshe);
        Product ramen = new Product("Ramen", "Ramen", moshe);
        idsToProducts.put(bamba.getId(), bamba);
        idsToProducts.put(ramen.getId(), ramen);
        try {
            addUser("Dani", "Dani123", false,
                    "BGU", "123456", "dani@post.bgu.ac.il");
            addUser("Dana", "Dana123", true, "A", "1234", "dana@gmail.com");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        this.currentLoggedIn = webUsers.get("Dana");
        linkProductToPrem("Bamba");
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
