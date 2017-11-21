package com.donbosco.studentapp;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/GoogleSearch")
public class GoogleSearch extends HttpServlet {
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String searchVal=req.getParameter("search");
		resp.setContentType("text/html");
		resp.sendRedirect("https://www.google.co.in/search?hl=en&source=hp&ei=zRcTWpTcEoTP0gTSmIroDg&q="+searchVal+"&oq="+searchVal+"&gs_l=psy-ab.3..0l2j0i46k1j46l2j0l7.7277.8287.0.8759.3.3.0.0.0.0.326.949.3-3.3.0....0...1.1.64.psy-ab..0.3.945...0i131k1.0.BcYj2x7NgBI");
		
		
	}

}
