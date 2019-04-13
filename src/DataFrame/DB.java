package DataFrame;

import java.sql.*;
import java.util.ArrayList;

public class DB {
    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet rs = null;

    public DB() {
    }

    public void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn =
                    DriverManager.getConnection("jdbc:mysql://mysql.agh.edu.pl/suchowia",
                            "suchowia", "cDsocTfrhZtp3Ukc");


        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ArrayList<String>> query(String query) {
        ArrayList<ArrayList<String>> result = new ArrayList<>();
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);

            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();

            for(int i = 1; i <= columnsNumber; i++) {
                result.add(new ArrayList<>());
                result.get(i-1).add(rsmd.getColumnName(i));
            }

            while (rs.next()) {
                for(int i = 1; i <= columnsNumber; i++) {
                    result.get(i-1).add(rs.getString(i));
                }
            }
            return result;
        } catch (SQLException ex) {
            // handle any errors

        } finally {
            // zwalniamy zasoby, które nie będą potrzebne
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) {
                } // ignore
                rs = null;
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) {
                } // ignore

                stmt = null;
            }
        }
        return null;
    }

    public void createTable(String tableName, ArrayList<String> names, ArrayList<String> types) throws Exception {
        String type;
        stmt = conn.createStatement();
        StringBuilder createString = new StringBuilder();
        createString.append("CREATE TABLE ").append(tableName).append(" (");
        for (int i = 0; i < names.size(); i++) {
            type = types.get(i);
            if (type.equals("String")) {
                type =  "VARCHAR(64)";
            }
            createString.append(names.get(i)).append(" ").append(type);
            if (i != (names.size() - 1)) {
                createString.append(", ");
            }
        }
        createString.append(")");
        stmt.executeUpdate(createString.toString());
    }

    public void insertToTable(String tableName, String[] values, ArrayList<String> colNames) {
        try {
            stmt = conn.createStatement();
            StringBuilder insertString = new StringBuilder();
            insertString.append("INSERT INTO ").append(tableName).append(" (");
            for (int i = 0; i < colNames.size(); i++) {
                insertString.append(colNames.get(i));
                if (i == colNames.size() - 1) {
                    insertString.append(") VALUES(");
                } else {
                    insertString.append(",");
                }
            }
            for (int i = 0; i < colNames.size(); i++) {
                insertString.append(values[i]);
                if (i != colNames.size() - 1) {
                    insertString.append(",");
                } else {
                    insertString.append(")");
                }
            }
            stmt.executeUpdate("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
