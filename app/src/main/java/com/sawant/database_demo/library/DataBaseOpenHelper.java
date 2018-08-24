package com.sawant.database_demo.library;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseOpenHelper extends SQLiteOpenHelper
{
	public DataBaseOpenHelper(Context context, String dataBaseName, int version)
	{
		super(context, dataBaseName, null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		onCreate(db);
	}
}
