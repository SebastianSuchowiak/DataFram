package DataFrame;

import java.util.*;

public class GroupByHashMap implements GroupBy {

    protected ArrayList<String> fColumnsNames = new ArrayList<>();
    protected ArrayList<String> fColumnsTypes = new ArrayList<>();
    protected LinkedHashMap<ArrayList<Value>, DataFrame> fDataFrames;
    protected String[] fKeyColumns;

    public GroupByHashMap(ArrayList<String> columnsNames, ArrayList<String> columnsTypes,
                          LinkedHashMap<ArrayList<Value>, DataFrame> hashMap, String[] keyColumns) {
        fDataFrames = hashMap;
        fKeyColumns = keyColumns;

        if (columnsNames.size() != columnsTypes.size()) {
            throw new IllegalArgumentException("Amount of names should be equal to amount of types");
        }

        for (int i = 0; i < columnsNames.size(); ++i) {
            fColumnsNames.add(columnsNames.get(i));
            fColumnsTypes.add(columnsTypes.get(i));
        }
    }

    public DataFrame applyFunction(OneArgumentArrayListOp op, String[] possibleTypes) {
        DataFrame currentDataFrame;
        String[] applyableCols = getapplyableColumns(possibleTypes).toArray(new String[getapplyableColumns(possibleTypes).size()]);
        DataFrame newDataFrame = new DataFrame(applyableCols, getColumnsTypes(applyableCols));
        ArrayList<Value> newRow = new ArrayList<>();

        for (ArrayList<Value> key : fDataFrames.keySet()) {
            currentDataFrame = fDataFrames.get(key);
            newRow.addAll(key);
            for (String colName: applyableCols) {
                if (!(Arrays.asList(fKeyColumns).contains(colName))) {
                    newRow.add(op.op(currentDataFrame.get(colName)));
                }
            }
            newDataFrame.add(newRow);
            newRow.clear();
        }

        return newDataFrame;
    }

    @Override
    public DataFrame max() {
        String[] possibleTypes = {"Integer", "Double", "Date", "Float"};
        return applyFunction(Collections::max, possibleTypes);
    }

    @Override
    public DataFrame min() {
        String[] possibleTypes = {"Integer", "Double", "Date", "Float"};
        return applyFunction(Collections::min, possibleTypes);
    }

    @Override
    public DataFrame sum() {
        String[] possibleTypes = {"Integer", "Double", "Float"};
        return applyFunction(this::sumFunc, possibleTypes);
    }

    public DataFrame mean() {
        String[] possibleTypes = {"Integer", "Double", "Float"};
        return applyFunction(this::meanFunc, possibleTypes);
    }


    @Override
    public DataFrame std() {
        String[] possibleTypes = {"Integer", "Double", "Float"};
        return applyFunction(this::stdFunc, possibleTypes);
    }



    @Override
    public DataFrame var() {
        String[] possibleTypes = {"Integer", "Double", "Float"};
        return applyFunction(this::varFunc, possibleTypes);
    }

    @Override
    public DataFrame apply(Applyable group) {
        return null;
    }

    private Value sumFunc(ArrayList<Value> values) {
        Value result = values.get(0);
        for (int i = 1; i < values.size(); ++i) {
            result = result.add(values.get(i));
        }
        return result;
    }

    private Value meanFunc(ArrayList<Value> values) {
        return sumFunc(values).div(new ValueDouble(values.size()));
    }

    private Value stdFunc(ArrayList<Value> values) {
        return varFunc(values).pow(new ValueDouble(0.5));
    }

    private Value varFunc(ArrayList<Value> values) {
        Value mean = meanFunc(values);
        Value result = values.get(0).sub(mean).pow(new ValueDouble(2));
        for (int i = 1; i < values.size(); i++) {
            result = result.add(values.get(i).sub(mean).pow(new ValueDouble(2)));
        }
        return result.div(new ValueDouble(values.size()));
    }

    private ArrayList<String> getapplyableColumns(String[] applyableTypes) {
        ArrayList<String> applyableColumns = new ArrayList<>();

        for (String n: fKeyColumns) {
            applyableColumns.add(n);
        }

        for (int i = 0; i < fColumnsTypes.size(); i++) {
            if (Arrays.asList(applyableTypes).contains(fColumnsTypes.get(i)) &&
                    !(Arrays.asList(fKeyColumns).contains(fColumnsNames.get(i)))) {
                applyableColumns.add(fColumnsNames.get(i));
            }
        }

        return applyableColumns;
    }

    private String[] getColumnsTypes(String[] colsNames) {
        String[] result = new String[colsNames.length];
        for (int i = 0; i < colsNames.length; i++) {
            result[i] = fColumnsTypes.get(fColumnsNames.indexOf(colsNames[i]));
        }
        return result;
    }
}
