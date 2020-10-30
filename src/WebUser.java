

public class WebUser {
    private String login_id;
    private String password;
    private UserState state;

    // Associations
    private Customer customer;
    private ShoppingCart shoppingCart;

    public WebUser(String login_id, String password, boolean isPremium,
                   String address, String phone, String email) {
        this.login_id = login_id;
        this.password = password;
        this.state = UserState.New;

        this.customer = new Customer(address,phone,email,this, isPremium);
        this.shoppingCart = this.customer.getAccount().getShoppingCart();
    }

    public String getLogin_id() {
        return login_id;
    }

    public void setLogin_id(String login_id) {
        this.login_id = login_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserState getState() {
        return state;
    }

    public void setState(UserState state) {
        this.state = state;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }
}
