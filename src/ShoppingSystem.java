import java.util.ArrayList;
import java.util.HashMap;

public class ShoppingSystem {
    private HashMap<String, Object> allObjects;
    private HashMap<Integer, Object> objectsForPrint;
    private HashMap<Object, Integer> objectToId;

    private static Integer autoIncreasingId = 0;

    private WebUser currentLoggedIn;

    public ShoppingSystem() {
        this.allObjects = new HashMap<>();
        this.objectsForPrint = new HashMap<>();
        this.objectToId = new HashMap<>();
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
     * @param city String
     * @param street String
     * @param number String
     * @param phone String
     * @param email String
     * @throws Exception
     */
    public void addUser(String loginId, String password, boolean isPremium, String city, String street, String number ,String phone, String email) throws Exception {
        if(idValidation(loginId)) throw new Exception("Id is not valid");
        WebUser newUser = new WebUser(loginId, password, isPremium, city, street, number, phone, email);
        putObjectToMaps(loginId, newUser);
        putObjectToMaps(newUser.getCustomer().getId(), newUser.getCustomer());
        putObjectToMaps(newUser.getCustomer().getAccount().getId(), newUser.getCustomer().getAccount());
        putObjectToMaps(newUser.getShoppingCart().getID(), newUser.getShoppingCart());
    }


    public void deleteObjectFromMaps(String id, Object o){
        allObjects.remove(id);
        objectsForPrint.remove(objectToId.get(o));
        objectToId.remove(o);
    }

    public void putObjectToMaps(String id, Object o){
        allObjects.put(id, o);
        objectsForPrint.put(autoIncreasingId, o);
        objectToId.put(o, autoIncreasingId++);
    }

    /**
     * Removes a user from the system including all associated objects that can not exist without this user.
     * @param loginId String
     * @throws Exception
     */
    public void removeUser(String loginId) throws Exception {
        if(!idValidation(loginId)) throw new Exception("Id is not valid");
        WebUser user = (WebUser)allObjects.get(loginId);
        if (user == this.currentLoggedIn ) this.currentLoggedIn = null;
        user.getShoppingCart().setWebUser(null); //Blocks the way to webUser from ShoppingCart
        user.getCustomer().setWebUser(null); //Blocks the way to webUser from Customer
        deleteObjectFromMaps(loginId, user);
        deleteObjectFromMaps(user.getCustomer().getId(), user.getCustomer());
        deleteObjectFromMaps(user.getCustomer().getAccount().getId(), user.getCustomer().getAccount());
        ArrayList<Order> orders = user.getCustomer().getAccount().getOrders();
        HashMap<String,Payment> payments = user.getCustomer().getAccount().getPayments();
        ArrayList<LineItem> userLineItems = user.getShoppingCart().getLineItems();
        for(Order o : orders){
            deleteObjectFromMaps(o.getId(), o);
        }
        for(Payment p: payments.values()){
            deleteObjectFromMaps(p.getId(), p);
        }
        for(LineItem li: userLineItems){
            deleteObjectFromMaps(li.getID(), li);
        }
        deleteObjectFromMaps(user.getShoppingCart().getID(), user.getShoppingCart());
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
        if(this.currentLoggedIn!= null && loginId.equals(this.currentLoggedIn.getLogin_id())) this.currentLoggedIn = null;
        else throw new Exception("You are not logged in.");
    }


    public Order makeNewOrder(String premiumAccount) throws Exception {
        if(this.currentLoggedIn == null){
            throw new Exception("You are not logged in.");
        }
        if (!(allObjects.get(premiumAccount) instanceof PremiumAccount)){
            throw new Exception("This account isn't premium");
        }
        Order o = new Order("order", this.currentLoggedIn.getCustomer().getAccount());
        putObjectToMaps(o.getId(), o);
        if(allObjects.get(premiumAccount) == null){
            throw new Exception("You can't buy from this Account");
        }
        return o;

    }

    /**
     * Checks whether user's input is a number and it fits an existing product.
     * @param premiumAccount
     * @param itemNumber
     * @return
     */
    public boolean orderValidation(String premiumAccount, String itemNumber){
        if(!isNumeric(itemNumber)) return false;
        ArrayList<Product> products = ((PremiumAccount)allObjects.get(premiumAccount)).getProducts();
        return Integer.parseInt(itemNumber) < products.size();
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    public Product chooseProduct(String premiumAccount, String itemNumber){
        ArrayList<Product> products = ((PremiumAccount)allObjects.get(premiumAccount)).getProducts();
        return products.get(Integer.parseInt(itemNumber));
    }

    public void displayPremiumProducts(String premiumAccount){
        ArrayList<Product> products = ((PremiumAccount)allObjects.get(premiumAccount)).getProducts();
        for(int i=0;i<products.size();i++){
            System.out.println(i+". " + products.get(i) + ", amount: " + products.get(i).getAmountToSale());
        }
    }

    public void addlineItetmtoOrder(Order order, Product pToadd, int amount) {
        LineItem l = new LineItem("LineItem", pToadd, amount, pToadd.getPrice() * amount,
                order, this.currentLoggedIn.getShoppingCart());
        putObjectToMaps(l.getID(), l);
        pToadd.setAmountToSale(pToadd.getAmountToSale() - amount);
        Account a = order.getAccount();
        a.setBalance(a.getBalance() + l.getPrice());
    }


    public void displayOrder(){
        if (this.currentLoggedIn == null){
            System.out.println("You are not logged in.");
            return;
        }
        Account cur_account = this.currentLoggedIn.getCustomer().getAccount();
        if (cur_account.getOrders().size()!= 0) {
            Order o = cur_account.getOrders().get(cur_account.getOrders().size() - 1);
            System.out.println(o.showAllDetails());
        }
        else{
            System.out.println("There is no order to display");
        }


    }

    public void linkProductToPrem(String productName, Integer price, Integer amountToSale) throws Exception {
        if(this.currentLoggedIn == null || !(this.currentLoggedIn.getCustomer().getAccount() instanceof PremiumAccount)){
            throw new Exception("There's no logged in user or current user isn't Premium");
        }
        if(!this.allObjects.containsKey(productName)){
            throw new Exception("Product doesn't exist in the system");
        }
        Product p = (Product)this.allObjects.get(productName);
        if (p.getPremiumAccount() != null && p.getPremiumAccount() != this.currentLoggedIn.getCustomer().getAccount()){
            throw new Exception("Given product already exists to another Premium user");
        }
        PremiumAccount a = (PremiumAccount) this.currentLoggedIn.getCustomer().getAccount();
        p.setPremiumAccount(a, price,amountToSale);
        a.addProduct(p);

    }

    public void addProduct(String productName, String productid, String supplierId) throws Exception {
        if(!idValidation(supplierId) || !(allObjects.get(supplierId) instanceof Supplier)){
            throw new Exception("The supplier of the product is not valid");
        }
        Product p = new Product(productid, productName, (Supplier) (allObjects.get(supplierId)));
        putObjectToMaps(p.getId(), p);
    }

    public void deleteProduct(String productName) throws Exception {
        if (!this.allObjects.containsKey(productName)){
            throw new Exception("Product doesn't exist.");
        }
        Product p = (Product)allObjects.get(productName);
        deleteObjectFromMaps(productName, p);

        if(p.getPremiumAccount() != null) {
            p.getPremiumAccount().removeProduct(p);
        }
        p.setPremiumAccount(null, 0, 0);

        p.getSupplier().removeProduct(p);
        p.setSupplier(null);

        for (LineItem l:p.getLineItems()) {
            l.getOrder().getAccount().setBalance(l.getOrder().getAccount().getBalance()-l.getPrice());
            l.getOrder().removeLineItem(l);
            l.setOrder(null);

            l.getShoppingCart().removeLineItem(l);
            l.setShoppingCart(null);

            l.setProduct(null);
            deleteObjectFromMaps(l.getID(), l);
        }

    }

    /**
     * Displays all current objects
     */
    public void showAllObjects(){
        for(Integer objectId : objectsForPrint.keySet()){
            System.out.println("Object's Id for searching: " + objectId + ", " + objectsForPrint.get(objectId));
        }
    }

    /**
     * Displays a specific object
     * @param id String
     */
    public void showObject(String id) throws Exception {
        if(isNumeric(id) && objectsForPrint.containsKey(Integer.parseInt(id))){
            Object o = objectsForPrint.get(Integer.parseInt(id));
            System.out.println(showAllDetails(o));
            System.out.println("Associated to:");
            showAssociated(o);
        }
        else{
            throw new Exception("Given id is invalid, there is no such object in our system.\nPlease use object's Id.");
        }
    }

    public void showAssociated(Object o){
        if (o instanceof WebUser) ((WebUser) o).showAssociated();
        else if (o instanceof Customer) ((Customer) o).showAssociated();
        else if (o instanceof Account) ((Account) o).showAssociated();
        else if (o instanceof ShoppingCart) ((ShoppingCart) o).showAssociated();
        else if (o instanceof Order) ((Order) o).showAssociated();
        else if (o instanceof Payment) ((Payment) o).showAssociated();
        else if (o instanceof LineItem) ((LineItem) o).showAssociated();
        else if (o instanceof Product) ((Product) o).showAssociated();
        else if (o instanceof Supplier) ((Supplier) o).showAssociated();
    }

    public String showAllDetails(Object o){
        if (o instanceof WebUser) return ((WebUser) o).showAllDetails();
        else if (o instanceof Customer) return ((Customer) o).showAllDetails();
        else if (o instanceof Account) return ((Account) o).showAllDetails();
        else if (o instanceof ShoppingCart) return ((ShoppingCart) o).showAllDetails();
        else if (o instanceof Order) return ((Order) o).showAllDetails();
        else if (o instanceof Payment) return ((Payment) o).showAllDetails();
        else if (o instanceof LineItem) return ((LineItem) o).showAllDetails();
        else if (o instanceof Product) return ((Product) o).showAllDetails();
        else if (o instanceof Supplier) return ((Supplier) o).showAllDetails();
        return null;
    }


    /**
     * Helper function that inserts objects into the system
     */
    public void initializeSystem(){
        Supplier moshe = new Supplier("123", "Moshe");
        putObjectToMaps(moshe.getId(), moshe);
        Product bamba = new Product("Bamba", "Bamba", moshe);
        Product ramen = new Product("Ramen", "Ramen", moshe);
        putObjectToMaps(bamba.getId(), bamba);
        putObjectToMaps(ramen.getId(), ramen);
        try {
            addUser("Dani", "Dani123", false,
                    "Beer-Sheva", "BGU", "1", "1234567","dani@post.bgu.ac.il");
            addUser("Dana", "Dana123", true, "Tel-Aviv", "TAU","3" ,"03654738","dana@gmail.com");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        this.currentLoggedIn = (WebUser)allObjects.get("Dana");
        try{
            linkProductToPrem("Bamba", 3, 10);
        } catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

    public WebUser getCurrentLoggedIn() {
        return currentLoggedIn;
    }

    public void paymentMethod(Order o, Account account, String paymentType ,String toPay) throws Exception {
        if (!isNumeric(toPay)) throw new Exception("You didn't enter a number!");
        Payment p = null;
        float pay = Float.parseFloat(toPay);
        String id = "payment "; //+ this.autoIncreasingId++;
        if (paymentType.equals("1")){
            p = new ImmediatePayment(id,account,o,pay);
        }
        else if (paymentType.equals("2")){
            p = new DelayedPayment(id,account,o,pay);
        }
        if (p != null){
            putObjectToMaps(p.getId(), p);
        }
    }

    public void deleteWrongOrder(Order o) {
        deleteObjectFromMaps(o.getId(), o);
        for(LineItem li: o.getLineItems()){
            deleteObjectFromMaps(li.getID(), li);
            li.setOrder(null);
        }
    }
}
