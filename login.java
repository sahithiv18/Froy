package com.uniquedeveloper.signup;


import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


@WebServlet("/login")
public class login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		 String uemail = request.getParameter("email");
		 String upwd = request.getParameter("password");
             HttpSession session = request.getSession();
		 RequestDispatcher dispatcher = null;
		 
		 if(uemail == null || uemail.equals("")) {
			 request.setAttribute("status","invalidEmail");
			 dispatcher = request.getRequestDispatcher("login.jsp");
			 dispatcher.forward(request, response);
		 }
		 if(upwd == null || upwd.equals("")) {
			 request.setAttribute("status","invalidPwd");
			 dispatcher = request.getRequestDispatcher("login.jsp");
			 dispatcher.forward(request, response);
		 }
		 
		 try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/signin","root","suchithra");
			PreparedStatement pst = con.prepareStatement("select * from users where uemail = ? and upwd = ?");
			pst.setString(1,  uemail);
			pst.setString(2,  upwd);
			
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
                        session.setAttribute("name",rs.getString("uemail"));
				dispatcher = request.getRequestDispatcher("index.jsp");
			}else {
				request.setAttribute("status","failed");
				dispatcher = request.getRequestDispatcher("login.jsp");
			}
			dispatcher.forward(request, response);
		 }catch ( Exception e )
		 {
		  e.printStackTrace();
		 }

}
}