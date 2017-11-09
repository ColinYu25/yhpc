package com.test;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * 返回列表   存储过程实例
 * @author llj
 * @create_time: 2014-7-11
 * @Description:rcp
 */
public class TestProcedureTHREE {

	public TestProcedureTHREE() {

	}

	public static void main(String[] args) {

		String driver = "oracle.jdbc.driver.OracleDriver";

		String strUrl = "jdbc:oracle:thin:@60.190.2.243:1521:orcl";

		Statement stmt = null;

		ResultSet rs = null;

		Connection conn = null;

		try {

			Class.forName(driver);

			conn = DriverManager.getConnection(strUrl, "new_nbyhpc1", "new_nbyhpc1");

			CallableStatement proc = null;

			proc = conn.prepareCall("{ call new_nbyhpc1.testc(?) }");

			proc.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);

			proc.execute();

			rs = (ResultSet) proc.getObject(1);

			while (rs.next())

			{

				System.out.println("<tr><td>" + rs.getString(1) + "</td><td>" + rs.getString(2) + "</td><td>" + rs.getString(3) + "</td><td>" + rs.getString(4) + "</td><td>" + rs.getString(5) + "</td><td>" + rs.getString(6) + "</td><td>" + rs.getString(7) + "</td><td>" + rs.getString(8) + "</td><td>" + rs.getString(9) + "</td><td>" + rs.getString(10) + "</td><td>" + rs.getString(11) + "</td><td>" + rs.getString(12) + "</td><td>" + rs.getString(13) + "</td><td>" + rs.getString(14) + "</td></tr>");

			}

		}

		catch (SQLException ex2) {

			ex2.printStackTrace();

		}

		catch (Exception ex2) {

			ex2.printStackTrace();

		}

		finally {

			try {

				if (rs != null) {

					rs.close();

					if (stmt != null) {

						stmt.close();

					}

					if (conn != null) {

						conn.close();

					}

				}

			}

			catch (SQLException ex1) {

			}

		}

	}

}
