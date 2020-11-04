public class Customer {
    private String id;
    private String address; //Address???
    private String phone;
    private String email;

    // Associations
    private WebUser webUser;
    private Account account;

    public Customer(String address, String phone, String email, WebUser webUser, boolean isPremium) {
        this.id = webUser.getLogin_id()+"'s Customer";
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.webUser = webUser;
        if (isPremium){
            this.account = new PremiumAccount(webUser.getLogin_id()+"'s Account", address, webUser, this);
        }
        else {
            this.account = new Account(webUser.getLogin_id()+"'s Account", address, webUser, this);
        }

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public WebUser getWebUser() {
        return webUser;
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

    @Override
    public String toString() {
        return "Customer: \n\tID: " + id + ", Name: " + id;
    }

    public String displayCustomer(){
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
