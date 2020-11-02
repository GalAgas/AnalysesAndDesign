import java.util.ArrayList;
import java.util.HashMap;

public class ShoppingSystem {
//    private HashMap<String, WebUser> webUsers;
//    private HashMap<String, Supplier> suppliers;
//    private HashMap<String, Product> idsToProducts;
    private HashMap<String, Object> allObjects;
    private static Integer autoIncreasingId = 0;

    private WebUser currentLoggedIn;

    public ShoppingSystem() {
//        this.webUsers = new HashMap<>();
//        this.suppliers = new HashMap<>();
//        this.idsToProducts = new HashMap<>();
        this.allObjects = new HashMap<>();
        this.initializeSystem();
        this.currentLoggedIn = null;


    }

    /**
     * Validation function - will be called from main before signing a new user in.
     * @param loginId String
     * @return boolean
     */
    public boolean idValidation(String loginId){
        return allObjects.containsKey(loginId);
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
        allObjects.put(loginId, newUser);
        allObjects.put(newUser.getCustomer().getId(), newUser.getCustomer());
        allObjects.put(newUser.getCustomer().getAccount().getId(), newUser.getCustomer().getAccount());
        allObjects.put(newUser.getShoppingCart().getID(), newUser.getShoppingCart());
    }

    /**
     * Removes a user from the system
     * @param loginId String
     * @throws Exception
     */
    public void removeUser(String loginId) throws Exception {
        if(!idValidation(loginId)) throw new Exception("Id is not valid");
        WebUser user = (WebUser)allObjects.get(loginId);
        user.getShoppingCart().setWebUser(null); //Blocks the way to webUser from ShoppingCart
        user.getCustomer().setWebUser(null); //Blocks the way to webUser from Customer
        allObjects.remove(user.getLogin_id());
        allObjects.remove(user.getCustomer().getId());
        allObjects.remove(user.getCustomer().getAccount().getId());
        allObjects.remove(user.getShoppingCart().getID());
    }

    /**
     * Logs a user into the system.
     * @param loginId String
     * @param password String
     * @throws Exception
     */
    public void logIn(String loginId, String password) throws Exception {
        if(!idValidation(loginId)) throw new Exception("Id is not valid");
        if(!((WebUser)(allObjects.get(loginId))).getPassword().equals(password)) throw new Exception("Incorrect password");
        this.currentLoggedIn = (WebUser)allObjects.get(loginId);
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


    public Order makeNewOrder(String premiumAccount) throws Exception {
        if(this.currentLoggedIn == null){
            throw new Exception("You are not logged in.");
        }
        Order o = new Order("order"+autoIncreasingId++, this.currentLoggedIn.getCustomer().getAccount());
        // dict - premiumAccount - products list 1,2,3,...
        // 1,
        //PremiumAccount buyFrom = dict.get(premiumAccount);
        return o;

    }
    public void displayPremiumProducts(ArrayList<Product> products){
        for(Product p: products){
            System.out.println(p);
        }
    }
    public void addlineItetmtoOrder(Order order, Product pToadd, int amount) {
        LineItem l = new LineItem("LineItem"+autoIncreasingId++, pToadd, amount, pToadd.getPrice() * amount,
                order, this.currentLoggedIn.getShoppingCart());
        allObjects.put(l.getID(), l);
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
        if(!this.allObjects.containsKey(productName)){
            //exception no such product in system!!!
        }
        Product p = (Product)this.allObjects.get(productName);
        if (p.getPremiumAccount() != null){
            //exception product allready connected to another preium account
        }
        PremiumAccount a = (PremiumAccount) this.currentLoggedIn.getCustomer().getAccount();
        p.setPremiumAccount(a);
        a.addProduct(p);

    }

    public void addProduct(String productName, String productid, String supplierId) throws Exception {
        if(!idValidation(supplierId) || !(allObjects.get(supplierId) instanceof Supplier)){
            throw new Exception("The supplier of the product is not valid");
        }
        Product p = new Product(productid, productName, (Supplier) (allObjects.get(supplierId)));
        this.allObjects.put(p.getId(), p);
    }

    public void deleteProduct(String productName){
        if (!this.allObjects.containsKey(productName)){
            //exception product doesnt exist
        }
        Product p = (Product)allObjects.get(productName);
        allObjects.remove(productName);

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
        for(Object wu : allObjects.values()){
            System.out.println(wu);
        }
    }

    /**
     * Displays a specific object
     * @param id String
     */
    public void showObject(String id){
        if(allObjects.containsKey(id)){
            Object o = allObjects.get(id);
            System.out.println(o);

        }
    }


    /**
     * Helper function that inserts objects into the system
     */
    public void initializeSystem(){
        Supplier moshe = new Supplier("123", "Moshe");
        allObjects.put(moshe.getId(), moshe);
        Product bamba = new Product("Bamba", "Bamba", moshe);
        Product ramen = new Product("Ramen", "Ramen", moshe);
        allObjects.put(bamba.getId(), bamba);
        allObjects.put(ramen.getId(), ramen);
        try {
            addUser("Dani", "Dani123", false,
                    "BGU", "123456", "dani@post.bgu.ac.il");
            addUser("Dana", "Dana123", true, "A", "1234", "dana@gmail.com");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        this.currentLoggedIn = (WebUser)allObjects.get("Dana");
        linkProductToPrem("Bamba");
    }

    public WebUser getCurrentLoggedIn() {
        return currentLoggedIn;
    }

    public void setCurrentLoggedIn(WebUser currentLoggedIn) {
        this.currentLoggedIn = currentLoggedIn;
    }

//    public HashMap<String, WebUser> getWebUsers() {
//        return webUsers;
//    }
//
//    public void setWebUsers(HashMap<String, WebUser> webUsers) {
//        this.webUsers = webUsers;
//    }
//
//    public HashMap<String, Supplier> getSuppliers() {
//        return suppliers;
//    }
//
//    public void setSuppliers(HashMap<String, Supplier> suppliers) {
//        this.suppliers = suppliers;
//    }
//
//    public HashMap<String, Product> getIdsToProducts() {
//        return idsToProducts;
//    }
//
//    public void setIdsToProducts(HashMap<String, Product> idsToProducts) {
//        this.idsToProducts = idsToProducts;
//    }
}
