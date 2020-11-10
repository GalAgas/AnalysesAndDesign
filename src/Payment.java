import java.util.Date;

public abstract class Payment {
    private String id;
    private Date paid;
    private float total;
    private String details;
    private static Integer autoID=0;

    // Associations
    protected Account account;
    protected Order order;

    public Payment(String id, Account account, Order order, float total) {
        this.autoID += 1;
        this.id = id+this.autoID;
        this.account = account;
        this.order = order;
        this.details = "paid by account: " + this.account.getId();
        this.account.addPayment(this);
        this.pay(total);
    }

    public String getId() {
        return id;
    }

    public float getTotal() {
        return total;
    }

    /**
     * implements a new payment
     * @param topay
     */
    public void pay(float topay) {
        this.paid = new Date();
        if (topay <= this.order.getTotal()) {
            this.total = topay;
            this.order.addPayment(this);
            this.account.addPayment(this);
        }

    }


    @Override
    public String toString() {
        return "Payment:\n\tID: "+this.id;
    }

    public String showAllDetails(){
        return this.toString() +", paid at " +this.paid
                + ", Total paid: "+this.total+", Details: "+this.details;

    }

    public void removeAssociated(){
        this.account = null;
        this.order = null;
    }

    public void showAssociated() {
        if (this.account != null) System.out.println(this.account);
        if (this.order != null) System.out.println(this.order);
    }
}
