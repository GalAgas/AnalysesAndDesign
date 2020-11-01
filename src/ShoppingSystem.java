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


    public void makeOrder(){

    }

    public void DisplayOrder(){

    }

    public void linkProductToPrem(String productName){

    }

    public void addProduct(String productName){

    }

    public void deleteProduct(String productName){

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
}
