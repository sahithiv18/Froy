package com.uniquedeveloper.signup;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/contact")
public class contact extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uname = request.getParameter("name");
		String uemail = request.getParameter("email");
		String uphonenumber = request.getParameter("PhoneNumber");
		String utext = request.getParameter("textarea");
	
		RequestDispatcher dispatcher = null;
		Connection con = null;
		
		if(uname == null || uname.equals("")) {
			 request.setAttribute("status","invalidName");
			 dispatcher = request.getRequestDispatcher("signin.jsp");
			 dispatcher.forward(request, response);
		 }
		
		if(uemail == null || uemail.equals("")) {
			 request.setAttribute("status","invalidEmail");
			 dispatcher = request.getRequestDispatcher("signin.jsp");
			 dispatcher.forward(request, response);
		 }
		
		
		if(utext == null || utext.equals("")) {
			 request.setAttribute("status","invalidText");
			 dispatcher = request.getRequestDispatcher("signin.jsp");
			 dispatcher.forward(request, response);
		 }
		 
		 
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/signin","root","suchithra");
			PreparedStatement pst = con.prepareStatement("insert into contactform(uname,uemail,uphonenumber,utext) values(?,?,?,?)");
			pst.setString(1,uname);
			pst.setString(2, uemail);
			pst.setString(3, uphonenumber);
			pst.setString(4, utext);
			int rowCount = pst.executeUpdate();
			dispatcher = request.getRequestDispatcher("contact.jsp");
			if (rowCount >0) {
				request.setAttribute("status", "success");
				
			}else {
				request.setAttribute("status", "failed");
			}
			dispatcher.forward(request, response);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
		   con.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}

	}

}
