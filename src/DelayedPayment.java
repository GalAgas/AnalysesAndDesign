import java.util.Date;

public class DelayedPayment extends Payment {
    private Date paymentDate;

    public DelayedPayment(Account account, Order order, float total) {
        super(account, order, total);
    }

    @Override
    public String toString() {
        return super.toString() +
                ", paymentDate=" + paymentDate;
    }
}
