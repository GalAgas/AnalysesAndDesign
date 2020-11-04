import java.util.Date;

public class DelayedPayment extends Payment {
    private Date paymentDate;

    public DelayedPayment(String id, Account account, Order order, float total) {
        super(id, account, order, total);
        this.paymentDate = new Date();
    }

    @Override
    public String toString() {
        return super.toString() +
                ", paymentDate=" + paymentDate;
    }
}
