package com.donbosco.studentapp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Driver;

/**
 * Servlet implementation class ChangePassword
 */
@WebServlet("/ChangePassword")
public class ChangePassword extends HttpServlet {
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int regnoVal=Integer.parseInt(req.getParameter("regno"));
		String currpassVal = req.getParameter("currpass");
		String newpassVal = req.getParameter("newpass");
		String renewpassVal = req.getParameter("renewpass");
		
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
	
		if(newpassVal.equals(renewpassVal))
		{
			Connection con = null;
			PreparedStatement pstmt = null;
			
			try {
//				1.load the driver
				Driver ref = (Driver)Class.forName("com.mysql.jdbc.Driver").newInstance();
				
//				2.get the db connection
				String dburl = "jdbc:mysql://localhost:3306/studentApplication?user=root&password=root";
				con=DriverManager.getConnection(dburl);
				
//				3.issue sql queries
				String query= " update student_otherinfo set password = ? where regno= ? and password= ?";
				pstmt =con.prepareStatement(query);
				pstmt.setString(1, newpassVal);
				pstmt.setInt(2, regnoVal);
				pstmt.setString(3, currpassVal);
				int count = pstmt.executeUpdate();
				

				//				4.process the result
				if (count == 1) {
					out.print("<h1 style=\"color:white;\">Password Successfully Updated</h1>");
				} else {
					out.print(" <h1 style=\"color:white;\">wrong username or password</h1	> <br>");
					out.println("<a href=\"http://localhost:8080/studentApplication/changepassword.html\">click me</a> To try again");
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally {
//				5.close all jdbc objects
				try {
					if(con!=null)
						con.close();
				} catch (Exception e2) {
					e2.printStackTrace();
					// TODO: handle exception
				}
				try {
					if(pstmt!=null)
						pstmt.close();
				} catch (Exception e2) {
					e2.printStackTrace();
					// TODO: handle exception
				}
			}
		}
		else
		{
			out.print("<html><body style=\"color:white;\"><h1>Passwords Dont Match</h1><br>");
			out.print("<h2>New Password Entered = "+newpassVal+"</h2>");
			out.print("<br><h2>Re Entered New Password = "+renewpassVal+"</h2>");
			out.println("<br><h3><a style=\"text-decoration: none; color: tomato;\" href=\"http://localhost:8080/studentApplication/changepassword.html\">click me</a> To try again</h3></body></html>");
		}
	
	}

}
