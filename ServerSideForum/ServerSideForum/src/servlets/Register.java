/* Registers a new user account in the database with the name and password given.
 * 
 */
package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import model.Account;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Register() {
		super();
	}

	@Resource(name="jdbc/database")
	private DataSource dataSource;
	private boolean register;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getQueryString()!=null&&request.getQueryString().equals("true")) {
			register = true;
		}
		if(request.getSession().getAttribute("logged")!=null) {
			response.sendRedirect("");
		}
		else if(register) {
			request.setAttribute("register", true);
			request.getRequestDispatcher("Account").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Account temp = null;
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			Connection con = dataSource.getConnection();
			String query = "SELECT * FROM account WHERE UPPER(name) = UPPER('" + name + "')";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			if(!rs.next()) {
				query = "INSERT INTO account (name, password, register, messages, type) VALUES('" + name +"', '" + password +"', " + System.currentTimeMillis() + ", 0, 0)";
				stmt = con.createStatement();
				stmt.execute(query);
				temp = new Account(query, 0);
				query = "SELECT id FROM account WHERE name = '" + name + "'";
				Statement stmt2 = con.createStatement();
				ResultSet rs2 = stmt2.executeQuery(query);
				while(rs2.next()) {
					temp.setId(rs2.getInt(1));
				}
				request.getSession().setAttribute("logged", temp);
			}
			else {
				register = true;
				request.setAttribute("error", "Account Already Exists");
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		doGet(request, response);
	}
}