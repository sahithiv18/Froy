package com.uniquedeveloper.signup;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;




@WebServlet("/signin")
public class SigninServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		String uemail = request.getParameter("email");
		String upwd = request.getParameter("password");
		String Reupwd = request.getParameter("re_pass");
		RequestDispatcher dispatcher = null;
		Connection con = null;
		
		if(uemail == null || uemail.equals("")) {
			 request.setAttribute("status","invalidEmail");
			 dispatcher = request.getRequestDispatcher("signin.jsp");
			 dispatcher.forward(request, response);
		 }
		 if(upwd == null || upwd.equals("")) {
			 request.setAttribute("status","invalidPwd");
			 dispatcher = request.getRequestDispatcher("signin.jsp");
			 dispatcher.forward(request, response);
		 } 
		 if(Reupwd == null || !upwd.equals(Reupwd)) {
			 request.setAttribute("status","invalidConfirmPwd");
			 dispatcher = request.getRequestDispatcher("signin.jsp");
			 dispatcher.forward(request, response);
		 }
		 
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/signin","root","suchithra");
			PreparedStatement pst = con.prepareStatement("insert into users(uemail,upwd) values(?,?)");
			pst.setString(1,uemail);
			pst.setString(2, upwd);
			
			int rowCount = pst.executeUpdate();
			dispatcher = request.getRequestDispatcher("signin.jsp");
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
