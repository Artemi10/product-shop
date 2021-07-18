package devanmejia.productshop.transfer.filter;

public class ProductRange {
    private int start;
    private int end;

    public ProductRange(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public boolean isRangeValid(){
        return end >= start;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }
}
