package com.sawant.database_demo.library;

public class QueryItem
{
	private String key;

	private Object value;

	private SearchCondition searchCondition;

	public QueryItem()
	{
	}

	public QueryItem(String key, Object value, SearchCondition searchCondition)
	{
		super();
		this.key = key;
		this.value = value;
		this.searchCondition = searchCondition;
	}

	public String getKey()
	{
		return key;
	}

	public void setKey(String key)
	{
		this.key = key;
	}

	public Object getValue()
	{
		return value;
	}

	public void setValue(Object value)
	{
		this.value = value;
	}

	public SearchCondition getSearchCondition()
	{
		return searchCondition;
	}

	public void setSearchCondition(SearchCondition searchCondition)
	{
		this.searchCondition = searchCondition;
	}

	@Override
	public String toString()
	{
		return "QueryItem [fieldName=" + key + ", queryValue=" + value + ", searchCondition=" + searchCondition + "]";
	}
}
