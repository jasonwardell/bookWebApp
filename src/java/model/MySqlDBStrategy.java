package model;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jwardell
 */
public class MySqlDBStrategy implements DBStrategy {

    private Connection conn;

    @Override
    public void openConnection(String driverClass, String url,
            String userName, String password) throws ClassNotFoundException, SQLException {

        Class.forName(driverClass);
        conn = DriverManager.getConnection(url, userName, password);
    }

    @Override
    public void closeConnection() throws SQLException {
        conn.close();
    }

    /**
     * Make sure you open and close connection when using this method. Future
     * optimization may include changing the return type to an array.
     *
     * @param tableName
     * @param maxRecords - limits records found to first maxRecords or if
     * maxRecords is zero (0) then no limit.
     * @return
     * @throws java.sql.SQLException
     */
    @Override
    public List<Map<String, Object>> findAllRecords(String tableName,
            int maxRecords) throws SQLException {

        String sql;

        if (maxRecords < 1) {
            sql = "select * from " + tableName;
        } else {
            sql = "select * from " + tableName + " limit " + maxRecords;
        }
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        List<Map<String, Object>> records = new ArrayList<>();

        while (rs.next()) {
            Map<String, Object> record = new HashMap<>();
            for (int colNo = 1; colNo <= columnCount; colNo++) {
                Object colData = rs.getObject(colNo);
                String colName = rsmd.getColumnName(colNo);
                record.put(colName, colData);
            }
            records.add(record);
        }
        return records;
    }

    @Override
    public void deleteRecordById(String deleteId, String tableName) throws SQLException {
        Statement stmt = conn.createStatement();
        DatabaseMetaData dmd = conn.getMetaData();
        ResultSet rs = dmd.getPrimaryKeys(null, null, tableName);
        String pKName = "#";

        if (rs.next()) {
            pKName = rs.getString("COLUMN_NAME");
            System.out.println("Primary Key column name is " + pKName);
        }
        String sql = "DELETE FROM " + tableName + " WHERE " + pKName + " = " + deleteId;
        stmt.executeUpdate(sql);
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        DBStrategy db = new MySqlDBStrategy();
        db.openConnection("com.mysql.jdbc.Driver",
                "jdbc:mysql://localhost:3306/book", "root", "admin");

        db.deleteRecordById("1", "author");
        List<Map<String, Object>> rawData = db.findAllRecords("author", 0);

        db.closeConnection();

        System.out.println(rawData);
    }
}
