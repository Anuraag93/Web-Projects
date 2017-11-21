package com.donbosco.studentapp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Driver;

/**
 * Servlet implementation class CreateStudentServlet
 */
@WebServlet("/CreateStudentServlet")
public class CreateStudentServlet extends HttpServlet {
		
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Connection con=null;
		PreparedStatement pstmt1=null;
		PreparedStatement pstmt2=null;
		
		int regnoVal = Integer.parseInt(req.getParameter("regno"));
		
		String fnameVal = req.getParameter("fname");
		String mnameVal = req.getParameter("mname");
		String lnameVal = req.getParameter("lname");
		
		String gfnameVal = req.getParameter("gfname");
		String gmnameVal = req.getParameter("gmname");
		String glnameVal = req.getParameter("glname");
		String psswrdVal = req.getParameter("psswrd");
		String isAdminVal = req.getParameter("isAdmin");
		
		
		//set these value in the data base using jdbc 5 steps
		PrintWriter out = resp.getWriter();
		try {
			

			//		1.load the driver
			Driver driverRef = new Driver();
			DriverManager.registerDriver(driverRef);

			//		2.get the db connection
			String dbUrl = "jdbc:mysql://localhost:3306/studentApplication?user=root&password=root";
			con = DriverManager.getConnection(dbUrl);

			//		3.issue sql query
			String query1=" insert into student_details (regno,firstname,middlename,lastname,gfirstname,gmiddlename,glastname) values(?,?,?,?,?,?,?)";
			
			String query2=" insert into student_otherinfo (regno,isadmin,password) values(?,?,?)";
			
			
			pstmt1= con.prepareStatement(query1);
			pstmt1.setInt(1, regnoVal);
			pstmt1.setString(2, fnameVal);
			pstmt1.setString(3, mnameVal);
			pstmt1.setString(4, lnameVal);
			pstmt1.setString(5, gfnameVal);
			pstmt1.setString(6, gmnameVal);
			pstmt1.setString(7, glnameVal);
			int count1 = pstmt1.executeUpdate();
			
			pstmt2 = con.prepareStatement(query2);
			pstmt2.setInt(1, regnoVal);
			pstmt2.setString(2, isAdminVal);
			pstmt2.setString(3, psswrdVal);
			int count2 = pstmt2.executeUpdate();
			
			resp.setContentType("text/html");
			
			if (count1==1&&count2==1) {
				
				out.print("<h1 style=\"color:white;\">Data Successfully Updated</h1>");
			}
			
		} catch (SQLException e) {
			out.print("<h1  style=\"color:white;\">Data not Updated</h1>");
			e.printStackTrace();
		}finally {
			
			//		5.close all jdbc objects
			try {
				if(con!=null)
					con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			try {
				if(pstmt1!=null)
					pstmt1.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}

			try {
				if(pstmt2!=null)
					pstmt2.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}

			

		}
		
	
	}

}
