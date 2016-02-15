package model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jwardell
 */
public interface DBStrategy {

    public abstract void openConnection(String driverClass, String url,
            String userName, String password)
            throws ClassNotFoundException, SQLException;

    public abstract void closeConnection() throws SQLException;

    public abstract List<Map<String, Object>> findAllRecords(String tableName,
            int maxRecords) throws SQLException;
    
    public void deleteRecordById(String deleteId, String tableName) throws SQLException;
}
