/* Creates a new thread in the database assigning it an id and a new post.
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

import org.apache.commons.lang3.math.NumberUtils;

/**
 * Servlet implementation class CreateThreads
 */
@WebServlet("/CreateThread/*")
public class CreateThread extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Resource(name="jdbc/database")
	private DataSource dataSource;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateThread() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] options = request.getRequestURI().split("/");
		String url = options[options.length-1];
		int forumId = getInt(url, '.');
		if(request.getSession().getAttribute("logged")!=null && forumId!=-1) {
			request.setAttribute("forumId", forumId);
			String forum = url.substring(0, url.indexOf('.'));//getForum(forumId);
			request.setAttribute("forumLink", forum);
			request.setAttribute("forumName", forum.replace('-', ' '));
			request.setAttribute("newPost", true);
			request.getRequestDispatcher("/WEB-INF/newThread.jsp").forward(request, response);
		}
		else
			response.sendRedirect(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] options = request.getRequestURI().split("/");
		String url = options[options.length-1];
		int forumId = getInt(url, '.');
		if(request.getSession().getAttribute("logged")!=null && forumId!=-1) {
			String title = (String) request.getParameter("title");
			if(!title.contains("?")&&!title.contains("/")) {
				newThread(forumId, title);
				getThread(title);
				request.setAttribute("forumId", forumId);
				String forum = url.substring(0, url.indexOf('.'));//getForum(forumId);
				request.setAttribute("forumLink", forum);
				request.getRequestDispatcher("/Threads/" + getThread(title) + "/page-1").forward(request, response);
			}
			else
				doGet(request, response);
		}
	}

	public void newThread(int forumId, String title) {
		if(forumId!=-1) {
			Connection con = null;
			try {
				con = dataSource.getConnection();
				String update = "INSERT INTO thread (NAME, FORUMID) values('" + title + "', " + forumId + ")";
				Statement stmt = con.createStatement();
				stmt.execute(update);
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
		}
	}

	public String getThread(String title) {
		try {
			Connection con = dataSource.getConnection();
			String query = "SELECT name, id FROM thread WHERE name = '" + title + "'";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			if(rs.next()) {
				return rs.getString(1) + "." + rs.getInt(2);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public int getInt(String url, char split) {
		url = url.substring(url.lastIndexOf(split)+1, url.length());
		if(NumberUtils.isCreatable(url))
			return Integer.parseInt(url);
		return -1;
	}
}