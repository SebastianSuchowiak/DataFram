package DataFrame;


import java.io.*;
import java.lang.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class DataFrame {

    private ArrayList<ArrayList<Value>> fColumns = new ArrayList<>();
    protected ArrayList<String> fColumnsNames = new ArrayList<>();
    protected ArrayList<String> fColumnsTypes = new ArrayList<>();
    protected Map<String, String> fGenericTypesMap = new HashMap<>();
    private String fDateFormat = "yyyy-MM-dd";

    public DataFrame() {
        initGenericTypesMap();
    }

    public DataFrame(String[] columnsNames, String[] columnsTypes) {

        if (columnsNames.length != columnsTypes.length) {
            throw new IllegalArgumentException("names: " + columnsNames.length + ", types: " + columnsTypes.length);
        }

        for (int i = 0; i < columnsNames.length; ++i) {
            fColumnsNames.add(columnsNames[i]);
            fColumnsTypes.add(columnsTypes[i]);
            fColumns.add(new ArrayList<>());
        }

        fDateFormat = "yyyy-MM-dd";
        initGenericTypesMap();
    }

    public DataFrame(ArrayList<String> columnsNames, ArrayList<String> columnsTypes) {

        if (columnsNames.size() != columnsTypes.size()) {
            throw new IllegalArgumentException("Amount of names should be equal to amount of types");
        }

        for (int i = 0; i < columnsNames.size(); ++i) {
            fColumnsNames.add(columnsNames.get(i));
            fColumnsTypes.add(columnsTypes.get(i));
            fColumns.add(new ArrayList<>());
        }

        initGenericTypesMap();
    }

    private void initGenericTypesMap() {
        fGenericTypesMap.put("int", "Integer");
        fGenericTypesMap.put("DateTime", "Date");
        fGenericTypesMap.put("double", "Double");
        fGenericTypesMap.put("float", "Float");
    }

    public int size() {
        return fColumns.get(0).size();
    }

    public ArrayList<Value> get(String columnName) {
        checkNames(new String[]{columnName});
        return fColumns.get(this.getIndex(columnName));
    }

    public void add(ArrayList<Value> newRow) {

        checkRow(newRow);
        for (int i = 0; i < newRow.size(); i++) {
            fColumns.get(i).add(newRow.get(i));
        }
    }

    public void add(DataFrame toAdd) {
        checkIfSameColNames(toAdd);
        if (!(fColumnsNames.equals(toAdd.fColumnsNames) && fColumnsTypes.equals(toAdd.fColumnsTypes))) {
            throw new IllegalArgumentException("Columns names or types are not equal");
        }

        ArrayList<Value> currentColumn;
        for (int i = 0; i < fColumns.size(); i++) {
            currentColumn = toAdd.fColumns.get(i);
            for (Value v : currentColumn) {
                fColumns.get(i).add(v);
            }
        }
    }

    private void checkIfSameColNames(DataFrame other) {

    }

    protected void checkRow(ArrayList<Value> newRow) {
        if (newRow.size() != fColumnsNames.size()) {
            throw new IllegalArgumentException("Number of columns should be equal to " + fColumnsNames.size() +
                    ", but is " + newRow.size());
        }
        for (int i = 0; i < newRow.size(); i++) {
            if (!(getObjectClassShortName(newRow.get(i).getfValue())).equals(
                    getClassEvenIfGeneric(fColumnsTypes.get(i)))) { // Przypisać zmienną?
                throw new IllegalArgumentException("Class in column " + i + " should be " + fColumnsTypes.get(i) +
                        " but is " + getObjectClassShortName(newRow.get(i).getfValue()));
            }
        }
    }

    private String getClassEvenIfGeneric(String name) {
        return (fGenericTypesMap.get(name) != null) ? fGenericTypesMap.get(name) : name; // Czytelność?
    }


    private String getObjectClassShortName(Object object) {
        String name = object.getClass().getName();
        Pattern pattern = Pattern.compile(".*\\.(.+$)");
        Matcher matcher = pattern.matcher(name);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }

    public DataFrame get(String[] columns, boolean copy) {
        checkNames(columns);

        DataFrame result;
        int indexOfColumnToAdd;
        ArrayList<ArrayList<Value>> columnsResult = new ArrayList<>();
        String[] namesResult = new String[columns.length];
        String[] typesResult = new String[columns.length];
        int currentNumberOfColumns = 0;


        for (String currentColumn : columns) {
            indexOfColumnToAdd = getIndex(currentColumn);
            if (indexOfColumnToAdd >= 0) {
                columnsResult.add((copy) ? getDeepCopyOfColumn(indexOfColumnToAdd) : fColumns.get(indexOfColumnToAdd));
                namesResult[currentNumberOfColumns] = fColumnsNames.get(indexOfColumnToAdd);
                typesResult[currentNumberOfColumns] = fColumnsTypes.get(indexOfColumnToAdd);
                currentNumberOfColumns++;
            } else {
                throw new IllegalArgumentException("No column named: " + currentColumn);
            }
        }

        result = new DataFrame(namesResult, typesResult);
        result.fColumns = columnsResult;
        return result;
    }

    private ArrayList<Value> getDeepCopyOfColumn(int columnIndex) {
        ArrayList<Value> deepCopy = new ArrayList<>();
        for (Value currentValue : fColumns.get(columnIndex)) {
            deepCopy.add(currentValue);
        }
        return deepCopy;
    }

    private int getIndex(String columnName) {
        for (int i = 0; i < fColumnsNames.size(); ++i) {
            if (columnName.equals(fColumnsNames.get(i))) {
                return i;
            }
        }
        return -1;
    }

    public DataFrame iloc(int rowNumber) {
        checkIlocArgument(rowNumber);

        ArrayList<Value> newRecord = new ArrayList<>();
        DataFrame resultRow = new DataFrame(fColumnsNames, fColumnsTypes);

        for (int i = 0; i < fColumnsNames.size(); i++) { // for each?
            newRecord.add(fColumns.get(i).get(rowNumber));
        }
        resultRow.add(newRecord);

        return resultRow;
    }

    protected void checkIlocArgument(int toCheck) {
        if (toCheck < 0 || toCheck > fColumns.get(0).size()) {
            throw new IndexOutOfBoundsException("");
        }
    }

    public DataFrame iloc(int from, int to) {
        checkIlocArguments(from, to);

        String[] namesArray = new String[fColumnsNames.size()];
        String[] typesArray = new String[fColumnsTypes.size()];
        ArrayList<Value> newRecord = new ArrayList<>();
        DataFrame resultRow = new DataFrame(fColumnsNames.toArray(namesArray), fColumnsTypes.toArray(typesArray));

        for (int j = from; j < to; j++) {
            for (int i = 0; i < fColumnsNames.size(); i++) {
                newRecord.add(fColumns.get(i).get(j));
            }
            resultRow.add(newRecord);
            newRecord.clear();
        }

        return resultRow;
    }

    protected void checkIlocArguments(int from, int to) {
        if (from < 0 || to > fColumns.get(0).size() || from > to) {
            throw new IndexOutOfBoundsException("");
        }
    }

    public DataFrame(String strFile, String separator) {
        try {
            String strLine;
            BufferedReader br = new BufferedReader(new FileReader(new File(strFile)));
            int rows = -1;

            while (br.readLine() != null) rows++;
            br.close();

            br = new BufferedReader(new FileReader(new File(strFile)));
            if ((strLine = br.readLine()) != null) {
                fColumnsNames = new ArrayList<>(Arrays.asList(strLine.split(separator)));
            }
            fColumnsTypes = getTypesFromColumn(br, separator, rows,fColumnsNames.size());
            for (int i = 0; i < fColumnsNames.size(); i++) {
                fColumns.add(new ArrayList<>());
            }

            initGenericTypesMap();
            read(strFile, separator);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void read(String strFile, String separator) {
        try {
            File f = new File(strFile);
            BufferedReader br = new BufferedReader(new FileReader(f));
            String strLine;
            if ((strLine = br.readLine()) != null) {
                checkNames(strLine.split(separator));
            }
            try {
                while ((strLine = br.readLine()) != null) {
                    String[] stringValues = strLine.split(separator);
                    checkValues(stringValues);
                    this.add(parseValues(stringValues));
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                throw new IllegalArgumentException("Fields do not have fixed types");
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkNames(String[] colNames) {
        for (String col : colNames) {
            if (!(fColumnsNames.contains(col))) {
                throw new IllegalArgumentException("No column named: " + col);
            }
        }
    }

    private void checkValues(String[] values) {

    }

    protected ArrayList<Value> parseValues(String[] stringValues) {
        ArrayList<Value> result = new ArrayList<>();

        for (int i = 0; i < stringValues.length; i++) {
            switch (fColumnsTypes.get(i)) {
                case "int":
                    result.add(new ValueInteger(stringValues[i]));
                    break;
                case "Integer":
                    result.add(new ValueInteger(stringValues[i]));
                    break;

                case "Double":
                    result.add(new ValueDouble(stringValues[i]));
                    break;
                case "double":
                    result.add(new ValueDouble(stringValues[i]));
                    break;

                case "String":
                    result.add(new ValueString(stringValues[i]));
                    break;

                case "Date":
                    result.add(new ValueDataTime(stringValues[i], fDateFormat));
                    break;
                case "date":
                    result.add(new ValueDataTime(stringValues[i], fDateFormat));
                    break;
                case "DateTime":
                    result.add(new ValueDataTime(stringValues[i], fDateFormat));
                    break;

                case "Float":
                    result.add(new ValueFloat(stringValues[i]));
                    break;
                case "float":
                    result.add(new ValueFloat(stringValues[i]));
                    break;
            }
        }

        return result;
    }

    public GroupByHashMap groupBy(String[] colNames) {
        checkNames(colNames);

        LinkedHashMap<ArrayList<Value>, DataFrame> result = new LinkedHashMap<>();
        ArrayList<Value> key = new ArrayList<>();

        DataFrame keyColumns = this.get(colNames, false);
        for (int i = 0; i < keyColumns.fColumns.get(0).size(); i++) {
            for (int j = 0; j < keyColumns.getfColumnsNames().size(); j++) {
                key.add(keyColumns.fColumns.get(j).get(i));
            }
            if (result.containsKey(key)) {
                result.get(key).add(this.iloc(i));
            } else {
                result.put(key, this.iloc(i));
            }
            key = new ArrayList<>();
        }

        return new GroupByHashMap(fColumnsNames, fColumnsTypes, result, colNames);
    }

    public void printDataFrame(String file) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < fColumnsNames.size(); i++) {
            result.append(fColumnsNames.get(i));
            if (i != fColumnsNames.size()- 1) {
                result.append(",");
            }
        }
        for (int i = 0; i < this.size(); i++) {
            result.append("\n");
            for (int j = 0; j < fColumnsNames.size(); j++) {
                result.append(fColumns.get(j).get(i));
                if (j != fColumnsNames.size()- 1) {
                    result.append(",");
                }
            }
        }
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(result.toString());
            writer.close();
        } catch (Exception e) {

        }
    }

    @Override
    public String toString() {
        return "DataFrame{" +
                ", fColumnsNames=" + fColumnsNames +
                ", fColumnsTypes=" + fColumnsTypes +
                "\nfColumns=" + fColumns +
                '}';
    }

    public void applyOperationToColumn(String colName, TwoArgumentValueOp op, Value value) {
        ArrayList<Value> colToMultiply = fColumns.get(this.getIndex(colName));

        try {
            fColumns.set(this.getIndex(colName), colToMultiply.stream()
                    .map(v -> op.op(v, value))
                    .collect(Collectors.toCollection(ArrayList::new)));
        } catch (Exception e) {
            throw new IllegalArgumentException("Class of argument and column are not compatible");
        }
    }

    public void multiplyColumnBy(String colName, Value value) {
        this.applyOperationToColumn(colName, Value::mul, value);
    }

    public void divColumnBy(String colName, Value value) {
        this.applyOperationToColumn(colName, Value::div, value);
    }

    public void addToColumn(String colName, Value value) {
        this.applyOperationToColumn(colName, Value::add, value);
    }

    public void subFromColumn(String colName, Value value) {
        this.applyOperationToColumn(colName, Value::sub, value);
    }

    public void powerColumnTo(String colName, Value value) {
        this.applyOperationToColumn(colName, Value::pow, value);
    }

    public ArrayList<String> getfColumnsNames() {
        return fColumnsNames;
    }

    public ArrayList<String> getfColumnsTypes() {
        return fColumnsTypes;
    }

    public ArrayList<ArrayList<Value>> getfColumns() {
        return fColumns;
    }

    protected ArrayList<String> getTypesFromColumn(
            BufferedReader br, String separator, Integer numOfRows, Integer numOfCols) {

        Double trialPercentage = 0.2;
        if (numOfRows < 100) {
            trialPercentage = 1.;
        }
        ArrayList<String> types = new ArrayList<>();
        Integer trialCircle = (int) (1 / trialPercentage);
        String[][] values = new String[numOfCols][(int) (numOfRows * trialPercentage )];
        String strLine;
        Integer lineCounter = 0;
        Integer valuesCounter = 0;

        try {
            while ((strLine = br.readLine()) != null && lineCounter < numOfRows) {
                if (lineCounter % trialCircle == 0) {
                    String[] stringValues = strLine.split(separator);
                    if (stringValues.length != numOfCols) {
                        throw new Exception("Row number " + lineCounter + " is invalid");
                    }
                    for (int i = 0; i < numOfCols; i++) {
                        values[i][valuesCounter] = stringValues[i];
                    }
                    valuesCounter++;
                }
                lineCounter++;
            }
            br.close();
        } catch (Exception e) {

        }

        for (int i = 0; i < numOfCols; i++) {
            types.add(checkColumnType(values[i], fDateFormat));
        }

        return types;
    }

    protected static String checkColumnType(String[] col, String strDateFormat) {

        try {
            for (String value: col) {
                new SimpleDateFormat(strDateFormat).parse(value);
            }
            return "Date";
        } catch (Exception e) {

        }
        try {
            for (String value: col) {
                Integer.parseInt(value);
            }
            return "Integer";
        } catch (Exception e) {

        }
        try {
            for (String value: col) {
                Float.parseFloat(value);
            }
            return "Float";
        } catch (Exception e) {

        }
        try {
            for (String value: col) {
                Double.parseDouble(value);
            }
            return "Double";
        } catch (Exception e) {

        }



        return "String";
    }

    public String getfDateFormat() {
        return fDateFormat;
    }

    public void setfDateFormat(String fDateFormat) {
        this.fDateFormat = fDateFormat;
    }
}
