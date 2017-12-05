/* Logs in using password and username given.
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
import model.MyException;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Resource(name="jdbc/database")
	private DataSource dataSource;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession().getAttribute("logged")==null) {
			request.getRequestDispatcher("Account").forward(request, response);
		}
		else
			response.sendRedirect("");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			Connection con;
			con = dataSource.getConnection();
			String query = "SELECT * FROM account WHERE UPPER(name) = UPPER('" + name + "') AND password = '" + password + "'";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			if(rs.next()) {
				int id = rs.getInt(1);
				String logName = rs.getString(2);
				long regDate = rs.getLong(4);
				int messCount = rs.getInt(5);
				int type = rs.getInt(6);
				Account temp = new Account(logName, regDate, messCount, type);
				try {
					temp.setName(null);
				}
				catch (MyException e) {
					System.out.println("My Own Exception");
					e.printStackTrace();
				}
				temp.setId(id);
				request.getSession().setAttribute("logged", temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		doGet(request, response);
	}

}
