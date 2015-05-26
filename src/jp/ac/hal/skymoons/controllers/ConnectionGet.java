package jp.ac.hal.skymoons.controllers;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionGet {

	public Connection getCon() {


		InitialContext context = null;
		Connection con = null;

		try{
			context = new InitialContext();
			DataSource ds =
				(DataSource) context.lookup("java:comp/env/jdbc/skymoons");
			con = ds.getConnection();
		}catch (NamingException e) {
			// TODO: handle exception
			e.printStackTrace();
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return con;

	}
}
