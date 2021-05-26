package com.maximuscooke.lib.common.rdbms;

import java.sql.*;

import com.maximuscooke.lib.common.IRdbms;

public class CMySql implements IRdbms
{
	public static final int DEFAULT_PORT = 3306;

	private int m_Port = DEFAULT_PORT;
	private String m_ServerNameOrIP = null;
	private String m_Database = null;
	private String m_User = null;
	private String m_Password = null;
	private Connection m_Connection = null;
	private int m_ResultSetType = ResultSet.TYPE_FORWARD_ONLY;
	private int m_ResultSetConcurrency = ResultSet.CONCUR_READ_ONLY;

	public CMySql(String serverNameOrIPAddress, String database, String user, String password) throws ClassNotFoundException
	{
		this(serverNameOrIPAddress, database, user, password, DEFAULT_PORT);
	}

	public CMySql(String serverNameOrIPAddress, String database, String user, String password, int port) throws ClassNotFoundException
	{
		m_ServerNameOrIP = serverNameOrIPAddress;

		m_Database = database;

		m_User = user;

		m_Password = password;

		m_Port = port;
	}

	public Connection getConnection() throws SQLException, ClassNotFoundException
	{
		return getConnection(this.m_ServerNameOrIP, m_Database, m_User, m_Password, m_Port);
	}

	public static Connection getConnection(String serverNameOrIPAddress, String database, String user, String password, int port) throws SQLException, ClassNotFoundException
	{
		Class.forName("com.mysql.jdbc.Driver");

		String url = getConnectionString(serverNameOrIPAddress, database, user, password, port);

		return DriverManager.getConnection(url, user, password);
	}

	public static String getConnectionString(String serverNameOrIPAddress, String database, String user, String password, int port)
	{
		StringBuilder url = new StringBuilder(100);

		url.append("jdbc:mysql://");

		url.append(serverNameOrIPAddress);

		url.append(":");

		url.append(port);

		url.append("/");

		url.append(database);

		return url.toString();
	}

	@Override
	public boolean testConnection()
	{
		try
		{
			getConnection();

			return true;
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public void connect() throws SQLException, ClassNotFoundException
	{
		if (this.m_Connection == null)
		{
			m_Connection = this.getConnection();
		}
	}

	@Override
	public void close() throws SQLException
	{
		if (this.m_Connection != null)
		{
			this.m_Connection.close();

			this.m_Connection = null;
		}
	}

	@Override
	public ResultSet executeQuery(String query) throws SQLException
	{
		Statement s = m_Connection.createStatement(this.getResultSetType(), this.getResultSetConcurrency());

		return s.executeQuery(query);
	}

	@Override
	public int executeSQL(String sql) throws SQLException
	{
		Statement s = m_Connection.createStatement();

		return s.executeUpdate(sql);
	}

	public final int getResultSetType()
	{
		return this.m_ResultSetType;
	}

	public final void setResultSetType(int type)
	{
		this.m_ResultSetType = type;
	}

	public final int getResultSetConcurrency()
	{
		return this.m_ResultSetConcurrency;
	}

	public final void setResultSetConcurrency(int concurrency)
	{
		this.m_ResultSetConcurrency = concurrency;
	}
}
