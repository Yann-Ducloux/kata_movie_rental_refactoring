package movierental;

public enum PriceCode {

    REGULAR(0),
    NEW_RELEASE(1),
    CHILDRENS(2);

    private final int state;

    PriceCode(int state) {
        this.state = state;
    }
}
