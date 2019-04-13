package DataFrame;

public class ValueInteger extends Value {

    public Integer fValue;

    public ValueInteger(Integer fValue) {
        this.fValue = fValue;
    }

    public ValueInteger(Double fValue) {
        this.fValue = fValue.intValue();
    }

    public ValueInteger(Float fValue) {
        this.fValue = fValue.intValue();
    }

    public ValueInteger(String stringValue) {
        fValue = Integer.parseInt(stringValue);
    }

    @Override
    public String toString() {
        return fValue.toString();
    }

    @Override
    public Value add(Value v) {
        if (v instanceof ValueInteger) {
            return new ValueInteger(fValue + (Integer) v.getfValue());
        } else if (v instanceof ValueFloat) {
            return new ValueInteger(fValue + ((Float) v.getfValue()).intValue());
        } else if (v instanceof ValueDouble) {
            return new ValueInteger(fValue + ((Double) v.getfValue()).intValue());
        } else {
            throw new IllegalArgumentException("Class of value to add is: " + v.getClass() +
                    " which is not compatible with ValueInteger");
        }
    }

    @Override
    public Value sub(Value v) {
        if (v instanceof ValueInteger) {
            return new ValueInteger(fValue - (Integer) v.getfValue());
        } else if (v instanceof ValueFloat) {
            return new ValueInteger(fValue - ((Float) v.getfValue()).intValue());
        } else if (v instanceof ValueDouble) {
            return new ValueInteger(fValue - ((Double) v.getfValue()).intValue());
        } else {
            throw new IllegalArgumentException("Class of value to subtract is: " + v.getClass() +
                    " which is not compatible with ValueInteger");
        }
    }

    @Override
    public Value mul(Value v) {
        if (v instanceof ValueInteger) {
            return new ValueInteger(fValue * (Integer) v.getfValue());
        } else if (v instanceof ValueFloat) {
            return new ValueInteger(fValue * ((Float) v.getfValue()).intValue());
        } else if (v instanceof ValueDouble) {
            return new ValueInteger(fValue * ((Double) v.getfValue()).intValue());
        } else {
            throw new IllegalArgumentException("Class of value to multiply by is: " + v.getClass() +
                    " which is not compatible with ValueInteger");
        }
    }

    @Override
    public Value div(Value v) {
        if (v instanceof ValueInteger) {
            return new ValueInteger(fValue / (Integer) v.getfValue());
        } else if (v instanceof ValueFloat) {
            return new ValueInteger(fValue / ((Float) v.getfValue()).intValue());
        } else if (v instanceof ValueDouble) {
            return new ValueInteger(fValue / ((Double) v.getfValue()).intValue());
        } else {
            throw new IllegalArgumentException("Class of value to divide by is: " + v.getClass() +
                    " which is not compatible with ValueInteger");
        }
    }

    @Override
    public Value pow(Value v) {
        if (v instanceof ValueInteger) {
            return new ValueInteger(Math.pow(fValue, ((Integer) v.getfValue()).doubleValue()));
        } else if (v instanceof ValueFloat) {
            return new ValueInteger(Math.pow(fValue, ((Float) v.getfValue()).doubleValue()));
        } else if (v instanceof ValueDouble) {
            return new ValueInteger(Math.pow(fValue, (Double) v.getfValue()));
        } else {
            throw new IllegalArgumentException("Class of value to power by is: " + v.getClass() +
                    " which is not compatible with ValueInteger");
        }
    }

    @Override
    public boolean eq(Value v) {
        if (v instanceof ValueInteger) {
            return fValue.equals(v.getfValue());
        } else if (v instanceof ValueFloat) {
            return fValue.equals(((Float) v.getfValue()).intValue());
        } else if (v instanceof ValueDouble) {
            return fValue.equals(((Double) v.getfValue()).intValue());
        } else {
            throw new IllegalArgumentException("Class of value to compare with is: " + v.getClass() +
                    " which is not compatible with ValueInteger");
        }
    }

    @Override
    public boolean lte(Value v) {
        if (v instanceof ValueInteger) {
            return fValue < (Integer) v.getfValue();
        } else if (v instanceof ValueFloat) {
            return fValue < ((Float) v.getfValue()).intValue();
        } else if (v instanceof ValueDouble) {
            return fValue < (((Double) v.getfValue()).intValue());
        } else {
            throw new IllegalArgumentException("Class of value to compare with is: " + v.getClass() +
                    " which is not compatible with ValueInteger");
        }
    }

    @Override
    public boolean gte(Value v) {
        if (v instanceof ValueInteger) {
            return fValue > (Integer) v.getfValue();
        } else if (v instanceof ValueFloat) {
            return fValue > ((Float) v.getfValue()).intValue();
        } else if (v instanceof ValueDouble) {
            return fValue > (((Double) v.getfValue()).intValue());
        } else {
            throw new IllegalArgumentException("Class of value to compare with is: " + v.getClass() +
                    " which is not compatible with ValueInteger");
        }
    }

    @Override
    public boolean neq(Value v) {
        if (v instanceof ValueInteger) {
            return !(fValue.equals(v.getfValue()));
        } else if (v instanceof ValueFloat) {
            return !(fValue.equals(((Float) v.getfValue()).intValue()));
        } else if (v instanceof ValueDouble) {
            return !(fValue.equals(((Double) v.getfValue()).intValue()));
        } else {
            throw new IllegalArgumentException("Class of value to compare with is: " + v.getClass() +
                    " which is not compatible with ValueInteger");
        }
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ValueInteger)) {
            return false;
        }
        return fValue.equals(((ValueInteger) other).getfValue());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        String fValueString = fValue.toString();
        for (int i = 0; i < fValueString.length(); i++) {
            hash = hash * 31 + fValueString.charAt(i);
        }
        return hash;
    }

    @Override
    public Value create(String s) {
        return new ValueInteger(Integer.getInteger(s));
    }

    public Integer getfValue() {
        return this.fValue;
    }

    @Override
    public int compareTo(Value other) {
        if(fValue.equals(((ValueInteger) other).fValue))
            return 0;
        else if(fValue>((ValueInteger) other).fValue)
            return 1;
        else
            return -1;
    }
}
