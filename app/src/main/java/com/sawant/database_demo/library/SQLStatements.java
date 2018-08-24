package com.sawant.database_demo.library;

public class SQLStatements {

	private String prepareQuery;
	private Object[] colValueList;

	public String getPrepareQuery() {
		return prepareQuery;
	}

	public void setPrepareQuery(String prepareQuery) {
		this.prepareQuery = prepareQuery;
	}

	public Object[] getColValueList() {
		return colValueList;
	}

	public void setColValueList(Object[] colValueList) {
		this.colValueList = colValueList;
	}

}
