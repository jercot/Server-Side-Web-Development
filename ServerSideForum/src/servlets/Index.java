/* Main index showing all the forums available.
 * 
 */
package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import model.Account;
import model.Forum;

/**
 * Servlet implementation class Index
 */
@WebServlet("/index.htm")
public class Index extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Resource(name="jdbc/database")
	private DataSource dataSource;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Index() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection con = null;
		try {
			con = dataSource.getConnection();
			int type = 0;
			Account logged = (Account) request.getSession().getAttribute("logged");
			if(logged!=null)
				type = logged.getType();
			String query = "SELECT * FROM forum WHERE type <= " + type;
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			ArrayList<Forum> temp = new ArrayList<>();
			while(rs.next()) {
				String name = rs.getString(2);
				int forumType = rs.getInt(3);
				Forum tempForum = new Forum(name, forumType);
				int id = rs.getInt(1);
				tempForum.setId(id);
				temp.add(tempForum);
			}
			request.setAttribute("forums", temp);
		}
		catch (SQLException e) {
				e.printStackTrace();
		}
		finally {
			try {
				if(con!=null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}