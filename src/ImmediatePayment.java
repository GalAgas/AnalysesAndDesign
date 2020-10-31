public class ImmediatePayment extends Payment{
    private boolean phoneConfirmation;

    public ImmediatePayment(Account account, Order order, float total) {
        super(account, order, total);
    }

    @Override
    public String toString() {
        return super.toString()+
                ", phoneConfirmation=" + phoneConfirmation;
    }
}
