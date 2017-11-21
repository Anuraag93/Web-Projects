package com.donbosco.studentapp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Driver;

/**
 * Servlet implementation class SearchStudent
 */
@WebServlet("/SearchStudent")
public class SearchStudent extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int regno = Integer.parseInt(req.getParameter("regno"));
		
		PrintWriter out = resp.getWriter();
		
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {		
			//JDBC Steps
			/*
			 *1. Load the Driver 
			 *Driver Class : com.mysql.jdbc.Driver 
			 */
			//Driver driverRef = new Driver();

				Driver driverRef = (Driver)(Class.forName("com.mysql.jdbc.Driver").newInstance());
				DriverManager .registerDriver(driverRef);
				
				String dBUrl = "jdbc:mysql://localhost:3306/studentApplication?user=root&password=root";
				con = DriverManager.getConnection(dBUrl);
				
				/*
				 * 3. issue the query
				 * 
				 */
				
				String query = " select * from student_details si,student_otherinfo so "
						+ " where si.regno = so.regno "
						+ " and si.regno=?";
				pstmt = con.prepareStatement(query);
				pstmt.setInt(1, regno);
				rs = pstmt.executeQuery();
				
				/*
				 * 4. Process the query
				 * 
				 */
				String output = "";
				if(rs.next()) {
					regno = rs.getInt("regno");
					String fName = rs.getString("firstname");
					String mName = rs.getString("middlename");
					String lName = rs.getString("lastname");
					
					/*String gfName = rs.getString("gfirstname");
					String gmName = rs.getString("gmiddlename");
					String glName = rs.getString("glastname");
					String isAdmin = rs.getString("isadmin");
					*/
					output = "<html>"
							+ "<head>"
							+ "<link rel=\"stylesheet\" href=\"viewall.css\">"
							+ "</head>"
							+"<body style=\"padding-top: 150px;\">"
							+"<table align=\"center\" style=\"font-size: 30px;\">"
							+"<tr><td>Registration No.</td><td>First Name</td><td>Middle Name</td><td>Last Name</td></tr>"
							+"<tr><td>"+regno+"</td><td>"+fName+"</td><td>"+mName+"</td><td>"+lName+"</td><tr>"
							+"</table>"
							+"</html>";
					out.println(output);
					
				} else {
					output = "<html>"
							+"<body style=\"background-color: lavender;text-align: center;\">"
							+"<h1>Data is not Available</h1>"
							+"</body>"
							+"</html>";
					out.println(output);
				}
		
		
		} catch (Exception e) {
				e.printStackTrace();
		}
		finally {
				//5.close all the jdbc objects
				if(con!= null)
					try {
						con.close();
					} catch (SQLException e2) {
						e2.printStackTrace();
					}
				if(rs!=null)
					try {
						rs.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				if(pstmt!= null)
					try {
				
					pstmt.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
			
		}
		
	}

}
