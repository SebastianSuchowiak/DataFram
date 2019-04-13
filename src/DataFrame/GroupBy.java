package DataFrame;

public interface GroupBy {
    public DataFrame max();
    public DataFrame min();
    public DataFrame mean();
    public DataFrame std();
    public DataFrame sum();
    public DataFrame var();
    public DataFrame apply(Applyable group);
}
