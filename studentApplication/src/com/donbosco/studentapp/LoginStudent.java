package com.donbosco.studentapp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Driver;

/**
 * Servlet implementation class LoginStudent
 */
@WebServlet("/LoginStudent")
public class LoginStudent extends HttpServlet {
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int regno = Integer.parseInt(req.getParameter("regno"));
		String password = req.getParameter("passwd");
		PrintWriter out = resp.getWriter();
		
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try {
//			1.Load the Driver
			Driver driverRef = new Driver();
			DriverManager.registerDriver(driverRef);
			
//			2.getting the connection via driver
			String dburl = "jdbc:mysql://localhost:3306/studentApplication?user=root&password=root";
			con = DriverManager.getConnection(dburl);
			
//			3.issue the sql query with db connection 
			String query = "Select * from student_details si,student_otherinfo so  where si.regno = so.regno and so.regno = ? and so.password=? ";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, regno);
			pstmt.setString(2, password);
			rs = pstmt.executeQuery();
			
//			4.Process sql query
			String output="";
			if(rs.next()) {
			
				regno=rs.getInt("regno");
				String isAdmin = rs.getString("isAdmin");
				
				String fnm=rs.getString("firstname");
				String mnm=rs.getString("middlename");
				String lnm=rs.getString("lastname");
				String gfnm=rs.getString("gfirstname");
				String gmnm=rs.getString("gmiddlename");
				String glnm=rs.getString("glastname");
				
				String pswd=rs.getString("password");
				
				out.print("<html><head><title>Login Student</title><link rel=\"stylesheet\" href=\"LoginStudent.css\"></head>");
				out.print("<body align=\"center\">");
				
				out.print("<h2>Details Of "+fnm+"</h2><br>");
				out.print("<table align=\"center\">");
				out.print("<tr>");
				out.print("<th> Reg No. </th>");
				out.print("<th>Firstname</th>");
				out.print("<th>Middlename</th>");
				out.print("<th>Lastname</th>");
				out.print("<th>Guardian First Name</th>");
				out.print("<th>Guardian Middle Name</th>");
				out.print("<th>Guardian Last Name</th>");
				
				out.print("<th>Password</th>");
				out.print("</tr><tr>");
				out.print("<td>"+regno+"</td>");
				out.print("<td>"+fnm+"</td>");
				out.print("<td>"+mnm+"</td>");
				out.print("<td>"+lnm+"</td>");
				out.print("<td>"+gfnm+"</td>");
				out.print("<td>"+gmnm+"</td>");
				out.print("<td>"+glnm+"</td>");
				
				out.print("<td>"+pswd+"</td>");
				out.print("</tr>");
				out.print("</table>");
			
				out.print("</body>");
				out.print("</html>");
			
				resp.setContentType("text/html");
					
						
				if (isAdmin.equals("Yes")) {
					out.println("<a href = \"http://localhost:8080/studentsApp/viewall\"> click here </a> To view all Student info");
				} 
				
			} else {
				out.println("<html>"
						+ "<body>"
						+ "<h1 style=\"color:white;\">login Unsuccessfull</h1>"
						+ "</body>"
						+ "</html>");
				
				resp.setContentType("text/html");
				out.print(output);
			
			}	
				
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
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
				if(pstmt!=null)
					pstmt.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	
	}

}
