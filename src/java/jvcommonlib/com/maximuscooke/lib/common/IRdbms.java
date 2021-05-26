package com.maximuscooke.lib.common;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IRdbms
{
	boolean testConnection();

	void connect() throws SQLException, ClassNotFoundException;

	void close() throws SQLException;

	ResultSet executeQuery(String query) throws SQLException;

	int executeSQL(String sql) throws SQLException;

}
