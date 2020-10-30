import java.util.ArrayList;

public class LineItem {
    private Product product;
    public LineItem(Product p) {
        product = p;
        product.addLineItem(this);
    }
}
