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

    public Payment(Account account, Order order, float total) {
        this.autoID += 1;
        this.id = autoID.toString();
        this.account = account;
        this.order = order;
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
        }

    }


    @Override
    public String toString() {
        return "Payment id: "+this.id +", paid at " +this.paid
                + ", Total paid: "+this.total+", Details: "+this.details;
    }

    public String displayPaymentId(){
        return "Payment id: "+this.id;
    }
}
