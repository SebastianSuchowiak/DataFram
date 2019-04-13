package DataFrame;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ValueDataTime extends Value {

    private Date fValue;
    private DateFormat fDateFormat;

    public ValueDataTime(String dateString, String format) {
        fDateFormat = new SimpleDateFormat(format);
        try {
            fValue = fDateFormat.parse(dateString);
        } catch (ParseException e) {
            throw new IllegalArgumentException("String is not in date format");
        }
    }

    public ValueDataTime(String dateString, DateFormat format) {
        fDateFormat = format;
        try {
            fValue = fDateFormat.parse(dateString);
        } catch (ParseException e) {
            throw new IllegalArgumentException("String is not in date format");
        }
    }

    public ValueDataTime(String format) {
        fDateFormat = new SimpleDateFormat(format);
    }

    @Override
    public String toString() {
        return fDateFormat.format(fValue);
    }

    @Override
    public Value add(Value v) {
        return null;
    }

    @Override
    public Value sub(Value v) {
        return null;
    }

    @Override
    public Value mul(Value v) {
        return null;
    }

    @Override
    public Value div(Value v) {
        return null;
    }

    @Override
    public Value pow(Value v) {
        return null;
    }

    @Override
    public boolean eq(Value v) {
        return fValue.equals(((ValueDataTime) v).fValue);
    }

    @Override
    public boolean lte(Value v) {
        return fValue.before(((ValueDataTime) v).fValue);
    }

    @Override
    public boolean gte(Value v) {
        return fValue.after(((ValueDataTime) v).fValue);
    }

    @Override
    public boolean neq(Value v) {
        return !(fValue.equals(((ValueDataTime) v).fValue));
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ValueDataTime)) {
            return false;
        }
        return fValue.equals(((ValueDataTime) other).fValue);
    }

    @Override
    public int hashCode() {
        return fValue.hashCode();
    }

    @Override
    public Value create(String s) {
        return new ValueDataTime(s, fDateFormat);
    }

    @Override
    public Date getfValue() {
        return fValue;
    }

    @Override
    public int compareTo(Value other) {
        if(fValue.equals(((ValueDataTime) other).fValue))
            return 0;
        else if(fValue.after(((ValueDataTime) other).fValue))
            return 1;
        else
            return -1;
    }
}
