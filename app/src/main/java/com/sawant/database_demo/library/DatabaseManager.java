package com.sawant.database_demo.library;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.sawant.database_demo.library.DatabaseConstants.*;


public class DatabaseManager implements IDatabaseManager {

    private static boolean _isLogEnable;
    private DatabaseConnectionManager dbInstance;
    private Context _context;
    private SQLiteDatabase database;

    public DatabaseManager(Context context) {
        _context = context;
        dbInstance = DatabaseConnectionManager.getInstance(context);
    }

    /**
     * Set Database Configuration
     *
     * @param databaseName Database Name.
     * @param isLogEnable  enable/disable database-component specific logs.
     * @param context      set context.
     */

    public static void setDatabaseConfiguration(String databaseName, boolean isLogEnable, Context context) {
        DatabaseConnectionManager.getInstance(context).setDbConfiguration(databaseName);
        _isLogEnable = true;
    }

    @Override
    public boolean createDbTable(String tableName, String[] columnNames, String[] columnTypes,
                                 String onConflictReplaceConstraint) {

        try {
            if (tableName == null || tableName.isEmpty() || columnNames == null || columnTypes == null) {
                if (_isLogEnable) {
                    DbLogger.error("Exception : ", DatabaseConstants.INVALID_INPUT);
                }
                return false;
            }

            StringBuilder queryBuilder = new StringBuilder("CREATE TABLE IF NOT EXISTS " + tableName + " ("
                    + DatabaseConstants.COLUMN_ROWID + " INTEGER primary key autoincrement");
            for (int i = 0; i < columnNames.length; i++) {
                queryBuilder.append(" ,");
                queryBuilder.append(columnNames[i]);
                queryBuilder.append(" ");
                queryBuilder.append(columnTypes[i]);
            }

            if (onConflictReplaceConstraint != null) {
                if (!onConflictReplaceConstraint.equals("")) {
                    queryBuilder.append(",");
                    queryBuilder.append(onConflictReplaceConstraint);
                }
            }

            queryBuilder.append(");");

            if (_isLogEnable) {
                DbLogger.debug(CURRENT_ENTITY + " " + tableName);
                DbLogger.debug(CREATE_QUERRY + " " + queryBuilder.toString());

            }

            database = dbInstance.openDatabase();
            database.execSQL(queryBuilder.toString());
            return true;
        } catch (Exception exception) {
            if (_isLogEnable) {
                DbLogger.error(exception);
            }
            return false;
        } finally {
            if (database != null) {
                if (database.isOpen()) {
                    dbInstance.closeDatabase();
                }
            }
        }

    }

    @Override
    public ArrayList<HashMap<String, Object>> executeQuery(String query) {

        ArrayList<HashMap<String, Object>> resultSet = new ArrayList<HashMap<String, Object>>();
        if (_isLogEnable) {
            DbLogger.debug(QUERY + query);
        }
        Cursor cursor = null;
        try {

            database = dbInstance.openDatabase();
            cursor = database.rawQuery(query, null);

            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {
                        HashMap<String, Object> map = new HashMap<String, Object>();

                        for (int i = 0; i < cursor.getColumnCount(); i++) {
                            String columnName = cursor.getColumnName(i);
                            Object columnValue = cursor.getString(cursor.getColumnIndex(columnName));
                            map.put(columnName, columnValue);
                        }
                        resultSet.add(map);
                        cursor.moveToNext();
                    }
                }
            }
            return resultSet;

        } catch (Exception exception) {

            if (_isLogEnable) {
                DbLogger.error(exception);
            }
            return resultSet;

        } finally {
            if (cursor != null) {
                if (!cursor.isClosed()) {
                    cursor.close();
                }
            }
            if (database != null) {
                if (database.isOpen()) {
                    dbInstance.closeDatabase();
                }
            }

        }
    }

    @Override
    public ArrayList<HashMap<String, Object>> executeQuery(String query, String[] selectionArgs) {
        Cursor cursor = null;
        ArrayList<HashMap<String, Object>> resultSet = new ArrayList<HashMap<String, Object>>();

        if (_isLogEnable) {
            DbLogger.debug(QUERY + query);
        }

        try {
            database = dbInstance.openDatabase();
            cursor = database.rawQuery(query, selectionArgs);

            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {
                        HashMap<String, Object> map = new HashMap<String, Object>();

                        for (int i = 0; i < cursor.getColumnCount(); i++) {
                            String columnName = cursor.getColumnName(i);
                            Object columnValue = cursor.getString(cursor.getColumnIndex(columnName));
                            map.put(columnName, columnValue);
                        }
                        resultSet.add(map);
                        cursor.moveToNext();
                    }
                }
            }
            return resultSet;
        } catch (Exception exception) {

            if (_isLogEnable) {
                DbLogger.error(exception);
            }
            return resultSet;

        } finally {
            if (cursor != null) {
                if (!cursor.isClosed()) {
                    cursor.close();
                }
            }
            if (database != null) {
                if (database.isOpen()) {
                    dbInstance.closeDatabase();
                }
            }
        }
    }

    @Override
    public boolean executeUpdate(String query) {
        if (_isLogEnable) {
            DbLogger.debug(QUERY + query);
        }
        try {
            database = dbInstance.openDatabase();
            database.execSQL(query);
            return true;
        } catch (Exception exception) {
            if (_isLogEnable) {
                DbLogger.error(exception);
            }
            return false;
        } finally {
            if (database != null) {
                if (database.isOpen()) {
                    dbInstance.closeDatabase();
                }
            }
        }
    }

    @Override
    public boolean executeUpdate(String query, Object[] whereArgs) {
        SQLiteStatement statement = null;

        try {
            if (_isLogEnable) {
                DbLogger.debug(QUERY + " " + query);
                StringBuilder builder = new StringBuilder();
                for (int index = 0; index < whereArgs.length; index++) {
                    if (whereArgs[index] != null) {
                        builder.append(whereArgs[index]);
                        builder.append(",");
                    }
                }
                DbLogger.debug("whereArgs" + " " + builder.toString());
            }
            database = dbInstance.openDatabase();
            statement = database.compileStatement(query);
            if (statement != null) {
                int occurrence = countOccurrences(query);
                if (whereArgs.length != occurrence) {
                    throw new IllegalStateException("Invalid query value, number of '?' are not equal to values");
                }
                int index = 1;
                for (int i = 0; i < whereArgs.length; i++, index++) {
                    DatabaseUtils.bindObjectToProgram(statement, index, whereArgs[i]);
                }
                statement.execute();
                return true;
            }
            return false;
        } catch (Exception exception) {

            if (_isLogEnable) {
                DbLogger.error(exception);
            }

            return false;
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (database != null) {
                if (database.isOpen()) {
                    dbInstance.closeDatabase();
                }
            }
        }
    }

    @Override
    public boolean performBulkOperations(ArrayList<SQLStatements> sqlStatements) {

        database = dbInstance.openDatabase();
        try {

            database.beginTransaction();

            for (int index = 0; index < sqlStatements.size(); index++) {
                String query = sqlStatements.get(index).getPrepareQuery();
                if (_isLogEnable) {
                    DbLogger.debug("Bulk Querry : ", query);
                }
                Object[] args = sqlStatements.get(index).getColValueList();
                database.execSQL(query, args);
            }
            database.setTransactionSuccessful();
            return true;

        } catch (Exception exception) {

            if (_isLogEnable) {
                DbLogger.error(exception);
            }
            return false;

        } finally {
            if (database != null) {
                database.endTransaction();
                if (database.isOpen()) {
                    dbInstance.closeDatabase();
                }
            }

        }

    }

    private int countOccurrences(String querry) {
        int counter = 0;
        for (int i = 0; i < querry.length(); i++) {
            if (querry.charAt(i) == '?') {
                counter++;
            }
        }

        return counter;
    }

    @Override
    public ArrayList<HashMap<String, Object>> search(String tableName, Clause clause) {
        Cursor cursor = null;
        ArrayList<HashMap<String, Object>> resultSet = new ArrayList<HashMap<String, Object>>();

        try {
            database = dbInstance.openDatabase();
            cursor = database.rawQuery(createQuery(tableName, clause), null);

            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {
                        HashMap<String, Object> resultmap = new HashMap<String, Object>();

                        for (int i = 0; i < cursor.getColumnCount(); i++) {
                            String columnName = cursor.getColumnName(i);
                            Object columnValue = cursor.getString(cursor.getColumnIndex(columnName));
                            resultmap.put(columnName, columnValue);
                        }
                        resultSet.add(resultmap);
                        cursor.moveToNext();
                    }
                }
            }
            return resultSet;

        } catch (Exception exception) {
            if (_isLogEnable) {
                DbLogger.error(exception);
            }
        } finally {
            if (cursor != null) {
                if (!cursor.isClosed()) {
                    cursor.close();
                }
            }
            if (database != null) {

                if (database.isOpen()) {
                    dbInstance.closeDatabase();
                }
            }
        }
        return null;
    }

    private String createQuery(String tableName, Clause clause) {
        boolean isFirstClause = true;
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT * FROM ");
        queryBuilder.append(tableName);

        Clause currentClause = clause;
        Operator currentOperator = null;

        while (currentClause != null) {
            QueryItem currentQueryItem = currentClause.getLeftItem();
            Clause nextClause = currentClause.getRightItem();

            if (currentQueryItem == null) {
                break;
            }

            if (isFirstClause) {
                queryBuilder.append(" WHERE ");
                isFirstClause = false;
            } else {
                switch (currentOperator) {
                    case AND:
                        queryBuilder.append(" AND ");
                        break;
                    case OR:
                        queryBuilder.append(" OR ");
                        break;
                    default:
                        queryBuilder.append(" ");
                        break;
                }
            }
            queryBuilder.append(DatabaseConstants.COLUMN_DATA);
            queryBuilder.append(" LIKE ");
            queryBuilder.append("'");

            String key = currentQueryItem.getKey();

            if (key != null) {
                queryBuilder.append("%");
                queryBuilder.append("\"");
                queryBuilder.append(currentQueryItem.getKey());
                queryBuilder.append("\"");
                queryBuilder.append(":");
            }

            SearchCondition searchCondition = currentQueryItem.getSearchCondition();

            switch (searchCondition) {
                case CONTAINS:
                    queryBuilder.append("%");
                    queryBuilder.append(currentQueryItem.getValue());
                    queryBuilder.append("%");
                    break;
                case STARTS_WITH:
                    queryBuilder.append(currentQueryItem.getValue());
                    queryBuilder.append("%");
                    break;
                case ENDS_WITH:
                    queryBuilder.append("%");
                    queryBuilder.append(currentQueryItem.getValue());
                    break;
                case EQUALS:
                    queryBuilder.append("\"");
                    queryBuilder.append(currentQueryItem.getValue());
                    queryBuilder.append("\"");
                    break;
                default:
                    break;
            }

            if (key != null) {
                queryBuilder.append("%");
            }

            queryBuilder.append("'");

            currentOperator = currentClause.getOperator();
            currentClause = nextClause;
        }

        queryBuilder.append(";");
        if (_isLogEnable) {
            DbLogger.debug("Query :", queryBuilder.toString());
        }

        return queryBuilder.toString();
    }

    @Override
    public String getEncryptedDbPath(String encryptedDbName) {

        try {
            File encryptedDb = _context.getDatabasePath(encryptedDbName);

            if (encryptedDb.exists()) {
                if (_isLogEnable) {
                    DbLogger.debug("EncryptedDbPath", encryptedDb.toString());
                }
                return encryptedDb.toString();
            }
        } catch (Exception exception) {

            if (_isLogEnable) {
                DbLogger.error(exception);
            }
        }
        return null;
    }

    @Override
    public boolean addOrUpdate(String tableName, String[] columnNames, String dataJson) {
        if (dataJson != null) {
            ArrayList<String> dataJsonList = new ArrayList<String>();
            dataJsonList.add(dataJson);
            return bulkInsertOrUpdate(tableName, columnNames, dataJsonList);
        } else {
            if (_isLogEnable) {
                DbLogger.error(DatabaseConstants.DATA_JSON_NULL);
            }
            return false;
        }
    }

    @Override
    public boolean bulkInsertOrUpdate(String tableName, String[] columnNames, List<String> dataJsonList) {

        String sqlQuery = generateBulkInsertQuery(tableName, columnNames);

        ArrayList<SQLStatements> sqlStatements = new ArrayList<SQLStatements>();

        try {
            for (int dataListindex = 0; dataListindex < dataJsonList.size(); dataListindex++) {

                SQLStatements insertStatement = new SQLStatements();
                insertStatement.setPrepareQuery(sqlQuery);
                JSONObject jsonObject = new JSONObject(dataJsonList.get(dataListindex));
                String[] columnValues = new String[columnNames.length];

                for (int columnIndex = 0; columnIndex < columnNames.length; columnIndex++) {

                    if (jsonObject.has(columnNames[columnIndex])) {
                        columnValues[columnIndex] = jsonObject.getString(columnNames[columnIndex]);

                    } else if (columnNames[columnIndex].equalsIgnoreCase("data")) {
                        columnValues[columnIndex] = dataJsonList.get(dataListindex);
                    }
                }
                insertStatement.setColValueList(columnValues);
                sqlStatements.add(insertStatement);
            }
            return performBulkOperations(sqlStatements);
        } catch (Exception exception) {
            if (_isLogEnable) {
                DbLogger.error(exception);
            }
            return false;
        }
    }

    private String generateBulkInsertQuery(String tableName, String[] columnNames) {
        StringBuilder sql = new StringBuilder();
        try {
            sql.append("INSERT OR REPLACE INTO ");
            sql.append(tableName);
            sql.append(" (");

            StringBuilder sqlColoums = new StringBuilder();
            StringBuilder sqlColoumsType = new StringBuilder();

            for (int i = 0; i < columnNames.length; i++) {

                if (i == columnNames.length - 1) {
                    sqlColoums.append(columnNames[i]);
                    sqlColoums.append(")");

                    // appending string to create '?' type query
                    sqlColoumsType.append("?");
                    sqlColoumsType.append(")");
                } else {
                    sqlColoums.append(columnNames[i] + ",");
                    // appending string to create '?' type query
                    sqlColoumsType.append("?,");
                }
            }
            sql.append(sqlColoums);
            sql.append(" VALUES (");
            sql.append(sqlColoumsType);
            sql.append(";");

            if (_isLogEnable) {
                DbLogger.debug("Query : ", sql.toString());
            }
        } catch (Exception exception) {
            if (_isLogEnable) {
                DbLogger.error(exception);
            }
            return sql.toString();
        }
        return sql.toString();
    }
}
