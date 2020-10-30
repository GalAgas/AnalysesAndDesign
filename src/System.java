import java.util.ArrayList;
import java.util.HashMap;

public class System {
    private HashMap<String, WebUser> webUsers;
    private HashMap<String, Supplier> suppliers;
    private HashMap<String, Product> idsToProducts;

    private WebUser currentLoggedIn;
//    private ArrayList<Payment> payments;

    public System() {
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

    public void showAllObjects(){

    }

    public void showObject(String id){

    }
}
