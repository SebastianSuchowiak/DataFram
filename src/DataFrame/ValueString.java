package DataFrame;

public class ValueString extends Value {

    public String fValue;

    public ValueString(String fValue) {
        this.fValue = fValue;
    }

    @Override
    public String toString() {
        return fValue;
    }

    @Override
    public Value add(Value v) {
        return new ValueString(fValue + ((ValueString) v).fValue);
    }

    @Override
    public Value sub(Value v) {
        return new ValueString(fValue);
    }

    @Override
    public Value mul(Value v) {
        return new ValueString(fValue);
    }

    @Override
    public Value div(Value v) {
        return new ValueString(fValue);
    }

    @Override
    public Value pow(Value v) {
        return new ValueString(fValue);
    }

    @Override
    public boolean eq(Value v) {
        return fValue.equals(((ValueString) v).fValue);
    }

    @Override
    public boolean lte(Value v) {
        int res = fValue.compareToIgnoreCase(((ValueString) v).fValue);
        return res > 0;
    }

    @Override
    public boolean gte(Value v) {
        int res = ((ValueString) v).fValue.compareToIgnoreCase(fValue);
        return res > 0;
    }

    @Override
    public boolean neq(Value v) {
        return !(fValue.equals(((ValueString) v).getfValue()));
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ValueString)) {
            return false;
        }
        return fValue.equals(((ValueString) other).getfValue());
    }

    @Override
    public int hashCode() {
        int hash = 13;
        for (int i = 0; i < fValue.length(); i++) {
            hash = hash * 37 + fValue.charAt(i);
        }
        return hash;
    }

    @Override
    public Value create(String s) {
        return new ValueString(s);
    }

    public String getfValue() {
        return this.fValue;
    }

    @Override
    public int compareTo(Value other) {
        return 0;
    }

}
