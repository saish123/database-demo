package com.sawant.database_demo.library;

/**
 *
 * @author Saish Sawant Patel
 *
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface IDatabaseManager
{

	/**
	 * Create table in database with ON CONFLICT REPLACE. It request user to
	 * enter Table_name(entity),column names,respective column data-types and to
	 * apply on conflict on which column.
	 * 
	 * @param tableName
	 *            Name of the table.
	 * @param columnNames
	 *            list of columns to be created(Array of string which contains
	 *            column names).
	 * @param columnTypes
	 *            respective column type of columns specified (Array of string
	 *            which contains column names type).
	 * @param onConflictReplaceConstraint
	 *            pass string which specifies on which column to apply ON
	 *            CONFLICT REPLACE.
	 * @return returns true if table is created in database else returns false.
	 */
	public boolean createDbTable(String tableName, String[] columnNames, String[] columnTypes,
								 String onConflictReplaceConstraint);

	/**
	 * Execute a sql query into database which returns ArrayList<HashMap<String,
	 * Object>> containing respective data for the given query.
	 * 
	 * @param query
	 *            sql query to be executed.
	 * @return returns result of the query in ArrayList<HashMap<String, Object>>
	 */
	public ArrayList<HashMap<String, Object>> executeQuery(String query);

	/**
	 * Execute a prepared statement query into database which returns
	 * ArrayList<HashMap<String, Object>> containing respective data for the
	 * given query.
	 * 
	 * @param query
	 *            prepared statement query to be executed containing '?'.
	 * @param argumentList
	 *            String[] array containing values for the respective '?'
	 * @return returns result of the query in ArrayList<HashMap<String, Object>>
	 */
	public ArrayList<HashMap<String, Object>> executeQuery(String query, String[] argumentList);

	/**
	 * Execute a single SQL query that is NOT a SELECT or any other SQL
	 * statement that returns data.
	 * 
	 * @param query
	 *            sql query to be executed.
	 * @return returns true if Success else returns false.
	 */
	public boolean executeUpdate(String query);

	/**
	 * Execute a single SQL prepared statement query that is NOT a SELECT or any
	 * other SQL statement that returns data.
	 * 
	 * @param query
	 *            prepared statement query to be executed containing '?'.
	 * @param argumentList
	 *            String[] array containing values for the respective '?'
	 * @return returns true if Success else returns false.
	 */
	public boolean executeUpdate(String query, Object[] argumentList);

	/**
	 * Perform Generic Bulk Operations User need to send List of
	 * SQLStatements.It performs Bulk operation on list of queries in a Single
	 * Transaction.
	 * 
	 * @param sqlStatements
	 *            list of sqlStatements(each SQLStatements object containing
	 *            query and respective query data).
	 * @return returns true if operation is successful else returns false.
	 */
	public boolean performBulkOperations(ArrayList<SQLStatements> sqlStatements);

	/**
	 * Search a record in particular table.It request user to enter
	 * Table_name(entity) column Name and clause. returns
	 * ArrayList<HashMap<String, Object>> which contains number of record found
	 * for specified search clause(search condition)
	 * 
	 * @param tableName
	 *            Table from where to search
	 * @param clause
	 *            clauses i.e search conditions.
	 * @return returns ArrayList<HashMap<String, Object>> which contains number
	 *         of record found for specified search clause(search condition)
	 */
	public ArrayList<HashMap<String, Object>> search(String tableName, Clause clause);

	/**
	 * Get path of encrypted database
	 * 
	 * @param encryptedDbName
	 *            Name of the database whose path to be retrieve.
	 * @return returns complete path of the database if present else returns
	 *         null.
	 */
	public String getEncryptedDbPath(String encryptedDbName);

	/**
	 * Add or update data into table.This method internally retrieves column
	 * values from specified JSON and inserts them into respective columns.
	 * 
	 * @param tableName
	 *            Name of Table.
	 * @param columnNames
	 *            Column names of the table.
	 * @param dataJson
	 *            json formatted data in <b>String</b> format.
	 * @return returns <b>true</b> if data is inserted Successfully else returns
	 *         <b>false</b>.
	 */
	public boolean addOrUpdate(String tableName, String[] columnNames, String dataJson);

	/**
	 * Bulk insert or Update data into table.This method internally retrieves
	 * column values from list of JSON specified and inserts them into
	 * respective columns.
	 * 
	 * @param tableName
	 *            Name of Table.
	 * @param columnNames
	 *            Column names of the table.
	 * 
	 * @param dataJsonList
	 *            List of data-Json
	 * @return returns <b>true</b> if data is inserted Successfully else returns
	 *         <b>false</b>.
	 */
	public boolean bulkInsertOrUpdate(String tableName, String[] columnNames, List<String> dataJsonList);

}
