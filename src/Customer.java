public class Customer {
    private String id;
    private Address address;
    private String phone;
    private String email;

    // Associations
    private WebUser webUser;
    private Account account;


    public Customer(String city, String street, String number, String phone, String email, WebUser webUser, boolean isPremium) {
        this.id = webUser.getLogin_id()+"'s Customer";
        this.address = new Address(city, street, number);
        this.phone = phone;
        this.email = email;
        this.webUser = webUser;
        String billingAddress = this.address.getStreet()+ " " +this.address.getNumber() + ", " +this.address.getCity();
        if (isPremium){
            this.account = new PremiumAccount(webUser.getLogin_id()+"'s Account", billingAddress, webUser, this);
        }
        else {
            this.account = new Account(webUser.getLogin_id()+"'s Account", billingAddress, webUser, this);
        }

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setWebUser(WebUser webUser) {
        this.webUser = webUser;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Address getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "Customer: \n\tID: " + id + ", Name: " + id;
    }

    public String showAllDetails(){
        return this.toString() + ", Address: " + this.address + ", Phone: " + this.phone
                + ", Email: " + this.email;
    }

    public void removeAssociated() {
        this.webUser = null;
        this.account.removeAssociated();
        this.account = null;
    }

    public void showAssociated(){
        if (this.webUser != null) System.out.println(this.webUser);
        if (this.account != null) System.out.println(this.account);
    }

}
