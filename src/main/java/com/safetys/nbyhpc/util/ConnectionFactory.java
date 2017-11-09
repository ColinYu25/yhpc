package com.safetys.nbyhpc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.log4j.Logger;

import com.safetys.framework.util.ConfigUtil;

public class ConnectionFactory {

	private Connection connection = null;

	/**
	 * 创建连接
	 * 
	 * @return
	 */
	public Connection createConnection() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection = DriverManager.getConnection(ConfigUtil.getPropertyValue("datasource.url"), ConfigUtil.getPropertyValue("datasource.userName"), ConfigUtil.getPropertyValue("datasource.userPass"));
			if (connection == null) {
				connection = DriverManager.getConnection(ConfigUtil.getPropertyValue("datasource.url"), ConfigUtil.getPropertyValue("datasource.userName"), ConfigUtil.getPropertyValue("datasource.userPass"));
			}
			return connection;
		} catch (ClassNotFoundException e) {
			Logger.getLogger(this.getClass()).error(e.getMessage());
			return null;
		} catch (SQLException e) {
			Logger.getLogger(this.getClass()).error(e.getMessage());
			return null;
		}
	}

	/**
	 * 释放连接
	 */
	public void releaseConnection() {
		if (connection != null)
			try {
				connection.close();
			} catch (SQLException e) {
				Logger.getLogger(this.getClass()).error(e.getMessage());
			}
	}
}
