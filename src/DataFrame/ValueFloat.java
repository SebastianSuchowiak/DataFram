package DataFrame;

public class ValueFloat extends Value {

    public Float fValue;

    public ValueFloat(Float fValue) {
        this.fValue = fValue;
    }

    public ValueFloat(Double fValue) {
        this.fValue = fValue.floatValue();
    }

    public ValueFloat(Integer fValue) {
        this.fValue = fValue.floatValue();
    }

    public ValueFloat(String stringValue) {this.fValue = Float.parseFloat(stringValue);}

    @Override
    public String toString() {
        return fValue.toString();
    }

    @Override
    public Value add(Value v) {
        if (v instanceof ValueInteger) {
            return new ValueFloat(fValue + ((Integer) v.getfValue()).floatValue());
        } else if (v instanceof ValueFloat) {
            return new ValueFloat(fValue + (Float) v.getfValue());
        } else if (v instanceof ValueDouble) {
            return new ValueFloat(fValue + ((Double) v.getfValue()).floatValue());
        } else {
            throw new IllegalArgumentException("Class of value to add is: " + v.getClass() +
                    " which is not compatible with ValueFloat");
        }
    }

    @Override
    public Value sub(Value v) {
        if (v instanceof ValueInteger) {
            return new ValueFloat(fValue - ((Integer) v.getfValue()).floatValue());
        } else if (v instanceof ValueFloat) {
            return new ValueFloat(fValue - (Float) v.getfValue());
        } else if (v instanceof ValueDouble) {
            return new ValueFloat(fValue - ((Double) v.getfValue()).floatValue());
        } else {
            throw new IllegalArgumentException("Class of value to subtract is: " + v.getClass() +
                    " which is not compatible with ValueFloat");
        }
    }

    @Override
    public Value mul(Value v) {
        if (v instanceof ValueInteger) {
            return new ValueFloat(fValue * ((Integer) v.getfValue()).floatValue());
        } else if (v instanceof ValueFloat) {
            return new ValueFloat(fValue * (Float) v.getfValue());
        } else if (v instanceof ValueDouble) {
            return new ValueFloat(fValue * ((Double) v.getfValue()).floatValue());
        } else {
            throw new IllegalArgumentException("Class of value to multiply by is: " + v.getClass() +
                    " which is not compatible with ValueFloat");
        }
    }

    @Override
    public Value div(Value v) {
        if (v instanceof ValueInteger) {
            return new ValueFloat(fValue / ((Integer) v.getfValue()).floatValue());
        } else if (v instanceof ValueFloat) {
            return new ValueFloat(fValue / (Float) v.getfValue());
        } else if (v instanceof ValueDouble) {
            return new ValueFloat(fValue / ((Double) v.getfValue()).floatValue());
        } else {
            throw new IllegalArgumentException("Class of value to divide by is: " + v.getClass() +
                    " which is not compatible with ValueFloat");
        }
    }

    @Override
    public Value pow(Value v) {
        if (v instanceof ValueInteger) {
            return new ValueFloat(Math.pow(fValue, ((Integer) v.getfValue()).floatValue()));
        } else if (v instanceof ValueFloat) {
            return new ValueFloat(Math.pow(fValue,(Float) v.getfValue()));
        } else if (v instanceof ValueDouble) {
            return new ValueFloat(Math.pow(fValue, ((Double) v.getfValue()).floatValue()));
        } else {
            throw new IllegalArgumentException("Class of value to power by is: " + v.getClass() +
                    " which is not compatible with ValueFloat");
        }
    }

    @Override
    public boolean eq(Value v) {
        if (v instanceof ValueInteger) {
            return fValue.equals(((Integer) v.getfValue()).floatValue());
        } else if (v instanceof ValueFloat) {
            return fValue.equals(v.getfValue());
        } else if (v instanceof ValueDouble) {
            return fValue.equals(((Double) v.getfValue()).floatValue());
        } else {
            throw new IllegalArgumentException("Class of value to compare with is: " + v.getClass() +
                    " which is not compatible with ValueFloat");
        }
    }

    @Override
    public boolean lte(Value v) {
        if (v instanceof ValueInteger) {
            return fValue < ((Integer) v.getfValue()).floatValue();
        } else if (v instanceof ValueFloat) {
            return fValue < (Float) v.getfValue();
        } else if (v instanceof ValueDouble) {
            return fValue < (((Double) v.getfValue()).floatValue());
        } else {
            throw new IllegalArgumentException("Class of value to compare with is: " + v.getClass() +
                    " which is not compatible with ValueFloat");
        }
    }

    @Override
    public boolean gte(Value v) {
        if (v instanceof ValueInteger) {
            return fValue > ((Integer) v.getfValue()).floatValue();
        } else if (v instanceof ValueFloat) {
            return fValue > (Float) v.getfValue();
        } else if (v instanceof ValueDouble) {
            return fValue > (((Double) v.getfValue()).floatValue());
        } else {
            throw new IllegalArgumentException("Class of value to compare with is: " + v.getClass() +
                    " which is not compatible with ValueFloat");
        }
    }

    @Override
    public boolean neq(Value v) {
        if (v instanceof ValueInteger) {
            return !(fValue.equals(((Integer) v.getfValue()).floatValue()));
        } else if (v instanceof ValueFloat) {
            return !(fValue.equals(v.getfValue()));
        } else if (v instanceof ValueDouble) {
            return !(fValue.equals(((Double) v.getfValue()).floatValue()));
        } else {
            throw new IllegalArgumentException("Class of value to compare with is: " + v.getClass() +
                    " which is not compatible with ValueFloat");
        }
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ValueFloat)) {
            return false;
        }
        return fValue.equals(((ValueFloat) other).getfValue());
    }

    @Override
    public int hashCode() {
        int hash = 13;
        String fValueString = fValue.toString();
        for (int i = 0; i < fValueString.length(); i++) {
            hash = hash * 37 + fValueString.charAt(i);
        }
        return hash;
    }

    @Override
    public Value create(String s) {
        return new ValueFloat(Float.parseFloat(s));
    }

    public Float getfValue() {
        return this.fValue;
    }

    @Override
    public int compareTo(Value other) {
        if(fValue.equals(((ValueFloat) other).fValue))
            return 0;
        else if(fValue>((ValueFloat) other).fValue)
            return 1;
        else
            return -1;
    }

}
