/* Gets the forum using the link given. Returns all the threads in that forum
 * 
 */
package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import model.ForumThread;

/**
 * Servlet implementation class Forum
 */
@WebServlet("/Forums/*")
public class Forums extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Resource(name="jdbc/database")
	private DataSource dataSource;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Forums() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = request.getRequestURI();
		if(url.contains(".")) {
			int forumId = Integer.parseInt(url.substring(url.lastIndexOf('.')+1, url.length()));
			String forumName = url.substring(url.lastIndexOf('/')+1, url.lastIndexOf('.'));
			request.setAttribute("forumLink", forumName);
			request.setAttribute("forumName", forumName.replace('-', ' '));
			request.setAttribute("forumId", forumId);
			Connection con = null;
			try {
				con = dataSource.getConnection();
				String query = "SELECT * FROM thread WHERE forumId = " + forumId + " ORDER BY (SELECT MAX(postTime) FROM post WHERE threadId = thread.id)";
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				Vector<ForumThread> temp = new Vector<>();
				while(rs.next()) {
					String name = rs.getString(2);
					ForumThread tempForum = new ForumThread(name);
					int id = rs.getInt(1);
					tempForum.setId(id);
					temp.add(0, tempForum);
				}
				request.setAttribute("threads", temp);
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
			request.getRequestDispatcher("/WEB-INF/forums.jsp").forward(request, response);
		}
		else
			response.sendRedirect(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}