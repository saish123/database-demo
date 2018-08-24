package com.sawant.database_demo.library;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseConnectionManager
{

	private int DATABASE_VERSION = 1;

	private String _dbName;

	int dbOpenCloseCount;

	private static DatabaseConnectionManager instance;

	private SQLiteDatabase database;

	private Context context;

	private DataBaseOpenHelper dbHelper;

	private DatabaseConnectionManager(Context context)
	{
		this.context = context;
	}

	public synchronized static DatabaseConnectionManager getInstance(Context context)
	{

		if (instance == null)
		{
			instance = new DatabaseConnectionManager(context);
		}
		return instance;
	}

	public void setDbConfiguration(String dbName)
	{
		this._dbName = dbName;
		dbOpenCloseCount = 0;
	}

	public SQLiteDatabase openDatabase()
	{
		if (dbOpenCloseCount == 0)
		{
			dbHelper = new DataBaseOpenHelper(context, _dbName, DATABASE_VERSION);
			database = dbHelper.getWritableDatabase();
		}
		dbOpenCloseCount++;

		return database;
	}

	public void closeDatabase()
	{
		 dbOpenCloseCount--;
		 if (dbOpenCloseCount == 0) {
		 database.close();
		 }
	}
}
