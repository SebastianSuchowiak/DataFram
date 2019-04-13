package DataFrame;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class DataFrameDB extends DataFrame {

    private DB fDB;
    private String tableName;

    DataFrameDB(DB db) {
        fDB = db;
        fDB.connect();
    }

    private void getCurrentState() {
        ArrayList<ArrayList<String>> arrayDB = fDB.query("SELECT * FROM " + tableName);
        if (arrayDB == null) {
            return;
        }

    }

    public static DataFrame getDataFrameFromQuery(DB db, String query) {
        db.connect();
        ArrayList<ArrayList<String>> arrayDB = db.query(query);
        if (arrayDB == null) {
            return null;
        }

        DataFrame newDF;
        ArrayList<ArrayList<Value>> valueCols = new ArrayList<>();
        ArrayList<String> colNames = new ArrayList<>();
        ArrayList<String> newRow;

        for (int i = 0; i < arrayDB.size(); i++) {
            colNames.add(arrayDB.get(i).get(0));
            arrayDB.get(i).remove(0);
        }

        newDF = new DataFrame(colNames, getTypesFromColumns(arrayDB));

        for (int i = 0; i < arrayDB.get(0).size(); i++) {
            newRow = new ArrayList<>();
            for (int j = 0; j < arrayDB.size(); j++) {
                newRow.add(arrayDB.get(j).get(i));
            }
            newDF.add(newDF.parseValues(newRow.toArray(new String[newRow.size()])));
        }
        return newDF;
    }

    protected static ArrayList<String> getTypesFromColumns(ArrayList<ArrayList<String>> arrayDB) {

        Integer numOfRows = arrayDB.get(0).size();
        Integer numOfCols = arrayDB.size();
        Double trialPercentage = 0.2;
        if (numOfRows < 100) {
            trialPercentage = 1.;
        }
        ArrayList<String> types = new ArrayList<>();
        Integer trialCircle = (int) (1 / trialPercentage);
        String[][] values = new String[numOfCols][(int) (numOfRows * trialPercentage - 1)];
        Integer valuesCounter = 0;
        String kappa;


        for (int i = 0; i < numOfRows-1; i += trialCircle) {
            for (int j = 0 ; j < numOfCols; j++) {
                kappa = arrayDB.get(j).get(i+1);
                values[j][valuesCounter] = arrayDB.get(j).get(i+1);
            }
            valuesCounter++;
        }

        for (int i = 0; i < numOfCols; i++) {
            types.add(checkColumnType(values[i], "yyyy"));
        }

        return types;
    }

    public static void dataFrameToDataBase(DB db, DataFrame df, String name) {
        db.connect();
        try {
            db.createTable(name, df.fColumnsNames, df.fColumnsTypes);
            db.insertToTable(name, new String[]{"sd", "s", "hjhj", "888"}, df.fColumnsNames);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
