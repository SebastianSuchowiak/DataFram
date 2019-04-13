package DataFrame;

public class ValueDouble extends Value {

    public Double fValue;

    public ValueDouble(Double fValue) {
        this.fValue = fValue;
    }

    public ValueDouble(Integer fValue) {
        this.fValue = fValue.doubleValue();
    }

    public ValueDouble(Float fValue) {
        this.fValue = fValue.doubleValue();
    }

    public ValueDouble(String stringValue) {
        fValue = Double.parseDouble(stringValue);
    }

    @Override
    public String toString() {
        return fValue.toString();
    }

    @Override
    public Value add(Value v) {
        if (v instanceof ValueInteger) {
            return new ValueDouble(fValue + ((Integer) v.getfValue()).doubleValue());
        } else if (v instanceof ValueFloat) {
            return new ValueDouble(fValue + ((Float) v.getfValue()).doubleValue());
        } else if (v instanceof ValueDouble) {
            return new ValueDouble(fValue + (Double) v.getfValue());
        } else {
            throw new IllegalArgumentException("Class of value to add is: " + v.getClass() +
                    " which is not compatible with ValueDouble");
        }
    }

    @Override
    public Value sub(Value v) {
        if (v instanceof ValueInteger) {
            return new ValueDouble(fValue - ((Integer) v.getfValue()).doubleValue());
        } else if (v instanceof ValueFloat) {
            return new ValueDouble(fValue - ((Float) v.getfValue()).doubleValue());
        } else if (v instanceof ValueDouble) {
            return new ValueDouble(fValue - (Double) v.getfValue());
        } else {
            throw new IllegalArgumentException("Class of value to subtract is: " + v.getClass() +
                    " which is not compatible with ValueDouble");
        }
    }

    @Override
    public Value mul(Value v) {
        if (v instanceof ValueInteger) {
            return new ValueDouble(fValue * ((Integer) v.getfValue()).doubleValue());
        } else if (v instanceof ValueFloat) {
            return new ValueDouble(fValue * ((Float) v.getfValue()).doubleValue());
        } else if (v instanceof ValueDouble) {
            return new ValueDouble(fValue * (Double) v.getfValue());
        } else {
            throw new IllegalArgumentException("Class of value to multiply by is: " + v.getClass() +
                    " which is not compatible with ValueDouble");
        }
    }

    @Override
    public Value div(Value v) {
        if (v instanceof ValueInteger) {
            return new ValueDouble(fValue / ((Integer) v.getfValue()).doubleValue());
        } else if (v instanceof ValueFloat) {
            return new ValueDouble(fValue / ((Float) v.getfValue()).doubleValue());
        } else if (v instanceof ValueDouble) {
            return new ValueDouble(fValue / (Double) v.getfValue());
        } else {
            throw new IllegalArgumentException("Class of value to divide by is: " + v.getClass() +
                    " which is not compatible with ValueDouble");
        }
    }

    @Override
    public Value pow(Value v) {
        if (v instanceof ValueInteger) {
            return new ValueDouble(Math.pow(fValue, ((Integer) v.getfValue()).doubleValue()));
        } else if (v instanceof ValueFloat) {
            return new ValueDouble(Math.pow(fValue, ((Float) v.getfValue()).doubleValue()));
        } else if (v instanceof ValueDouble) {
            return new ValueDouble(Math.pow(fValue, ((ValueDouble) v).fValue));
        } else {
            throw new IllegalArgumentException("Class of value to power by is: " + v.getClass() +
                    " which is not compatible with ValueDouble");
        }
    }

    @Override
    public boolean eq(Value v) {
        if (v instanceof ValueInteger) {
            return fValue.equals(((Integer) v.getfValue()).doubleValue());
        } else if (v instanceof ValueFloat) {
            return fValue.equals(((Float) v.getfValue()).doubleValue());
        } else if (v instanceof ValueDouble) {
            return fValue.equals(((ValueDouble) v).fValue);
        } else {
            throw new IllegalArgumentException("Class of value to compare with is: " + v.getClass() +
                    " which is not compatible with ValueDouble");
        }
    }

    @Override
    public boolean lte(Value v) {
        if (v instanceof ValueInteger) {
            return fValue < ((Integer) v.getfValue()).doubleValue();
        } else if (v instanceof ValueFloat) {
            return fValue < ((Float) v.getfValue()).doubleValue();
        } else if (v instanceof ValueDouble) {
            return fValue < ((ValueDouble) v).fValue;
        } else {
            throw new IllegalArgumentException("Class of value to compare with is: " + v.getClass() +
                    " which is not compatible with ValueDouble");
        }
    }

    @Override
    public boolean gte(Value v) {
        if (v instanceof ValueInteger) {
            return fValue > ((Integer) v.getfValue()).doubleValue();
        } else if (v instanceof ValueFloat) {
            return fValue > ((Float) v.getfValue()).doubleValue();
        } else if (v instanceof ValueDouble) {
            return fValue > ((ValueDouble) v).fValue;
        } else {
            throw new IllegalArgumentException("Class of value to compare with is: " + v.getClass() +
                    " which is not compatible with ValueDouble");
        }
    }

    @Override
    public boolean neq(Value v) {
        if (v instanceof ValueInteger) {
            return !(fValue.equals(((Integer) v.getfValue()).doubleValue()));
        } else if (v instanceof ValueFloat) {
            return !(fValue.equals(((Float) v.getfValue()).doubleValue()));
        } else if (v instanceof ValueDouble) {
            return !(fValue.equals(((ValueDouble) v).fValue));
        } else {
            throw new IllegalArgumentException("Class of value to compare with is: " + v.getClass() +
                    " which is not compatible with ValueDouble");
        }
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ValueDouble)) {
            return false;
        }
        return fValue.equals(((ValueDouble) other).getfValue());
    }

    @Override
    public int hashCode() {
        int hash = 11;
        String fValueString = fValue.toString();
        for (int i = 0; i < fValueString.length(); i++) {
            hash = hash * 41 + fValueString.charAt(i);
        }
        return hash;
    }

    @Override
    public Value create(String s) {
        return new ValueDouble(Double.parseDouble(s));
    }

    public Double getfValue() {
        return this.fValue;
    }

    @Override
    public int compareTo(Value other) {
        if(fValue.equals( (Double) other.getfValue()))
            return 0;
        else if(fValue>(Double) other.getfValue())
            return 1;
        else
            return -1;
    }
}
