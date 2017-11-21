package com.donbosco.studentapp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Driver;

/**
 * Servlet implementation class ViewAllStudent
 */
@WebServlet("/ViewAllStudent")
public class ViewAllStudent extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Connection con=null;
		Statement stmt =null;
		ResultSet rs = null;
		
		try {
//			1.load the driver
			Driver dref = new Driver();
			DriverManager.registerDriver(dref);
			
//			2.get the connection
			String dbUrl="jdbc:mysql://localhost:3306/studentApplication?user=root&password=root";
			con = DriverManager.getConnection(dbUrl);
			
//			3.issue sql queries
			String query = "select*from student_details si,student_otherinfo so "
					+ "where si.regno = so.regno ";
			
			stmt= con.createStatement();
			rs= stmt.executeQuery(query);
			
//			4.process the result
			resp.setContentType("text/html");
			PrintWriter out = resp.getWriter();
			
			out.print("<h1> Display all the information for Admin<h1><br>");
			out.print("<html> " +
						"<head>" +
						"<link rel=\"stylesheet\" href=\"viewall.css\">" +
						"</head>" + 
						"<body> " +
						"<fieldset> " +
						"<legend>All the info about student</legend>" + 
						"<table align=\"center\"> " + 
						"<tr> " + 
						"<th>Reg No</th> " + 
						"<th>First Name</th> " + 
						"<th>Middle Name</th> " + 
						"<th>Last Name</th> " + 
						"<th>Guardian First Name</th> " + 
						"<th>Guardian Middle Name</th> " + 
						"<th>Guardian Last Name</th> " + 
						"<th>isAdmin</th> " + 
						"<th>Delete Record</th> "+ 
						" <th>Edit Record</th> " + 
						"</tr> ");
			
			while(rs.next()) {
				
				int regno=rs.getInt("regno");
				String fnm=rs.getString("firstname");
				String mnm=rs.getString("middlename");
				String lnm=rs.getString("lastname");
				
				String gfnm=rs.getString("gfirstname");
				String gmnm=rs.getString("gmiddlename");
				String glnm=rs.getString("glastname");
				String isAdmin = rs.getString("isAdmin");
				
				out.print("<tr> " + 
						"<td>"+regno+"</td> " + 
						"<td>"+fnm+"</td> " + 
						"<td>"+mnm+"</td> " + 
						"<td>"+lnm+"</td> " + 
						"<td>"+gfnm+"</td> " + 
						"<td>"+gmnm+"</td> " + 
						"<td>"+glnm+"</td> " + 
						"<td>"+isAdmin+"</td> " + 
						"<td class=\"danger\"> <a href=\"http://localhost:8080/studentApplication/delete?regno="+regno+"\"> delete </a></td>"+
						"<td class=\"change\"> <a href=\"http://localhost:8080/studentApplication/editpage?regno="+regno+"\"> edit </a></td>"+
						"</tr> " );
			}
			out.print("</table> "
					+ "</fieldset>" + 
						"</body> " + 
						"</html>");
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
//			5.close all jdbc objects
			
			try {
				if(con!=null)
					con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			try {
				if(rs!=null)
					rs.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			try {
				if(stmt!=null)
					stmt.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
}
