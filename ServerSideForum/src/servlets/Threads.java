/* Gets all posts from a thread with the url given. Displays 10 at a time. Used each time you want to switch page on a thread as well.
 * 
 */
package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.commons.lang3.math.NumberUtils;

import model.Account;
import model.ForumPost;
import model.ThreadTraverse;

/**
 * Servlet implementation class Index
 */
@WebServlet("/Threads/*")
public class Threads extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Resource(name="jdbc/database")
	private DataSource dataSource;
	private boolean pageError, threadError, newPost;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Threads() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		pageError = false;
		threadError = false;
		request.setAttribute("current", request.getRequestURI());
		String[] options = request.getRequestURI().split("/");
		String url = options[options.length-2];
		int threadId = getInt(url, '.');
		if(threadId!=-1) {
			url = options[options.length-1];
			int page = getInt(url, '-');
			if(page!=-1)
				page=1;
			ArrayList<ForumPost<Account>> temp = getPosts(threadId, page);
			LinkedList<ForumPost<Account>> output = new LinkedList<>();
			if(newPost)
				page = (temp.size()/10+1);
			if(page <= (temp.size()/10+1)) {
				for(int i = (page-1)*10; i<temp.size()&&i<page*10; i++) {
					output.add(temp.get(i));
				}
			}
			else
				pageError = true;
			request.setAttribute("posts", output);
			ThreadTraverse pages = new ThreadTraverse(request.getRequestURI().substring(0, request.getRequestURI().lastIndexOf('/')+1), temp.size()/10, page);
			request.setAttribute("pages", pages);
			request.setAttribute("link", request.getRequestURI().substring(0, request.getRequestURI().lastIndexOf('/')+1));
			if(page!=1)
				request.setAttribute("firstPage", true);
			if(page!=temp.size()/10+1)
				request.setAttribute("lastPage", true);
			request.setAttribute("currentPage" , page);
		}
		else
			threadError = true;
		if(pageError) 
			response.sendRedirect("page-1");
		else if(threadError)
			response.sendRedirect(request.getContextPath());
		else {
			String forum = getForum(threadId);
			request.setAttribute("forumName", forum.substring(0, forum.indexOf('.')));
			request.setAttribute("forumLink", forum.replace(' ', '-'));
			request.setAttribute("threadName", getThread(threadId));
			request.getRequestDispatcher("/WEB-INF/threads.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] options = request.getRequestURI().split("/");
		String url = options[options.length-2];
		int threadId = getInt(url, '.');
		String post = request.getParameter("post");
		Account temp = (Account) request.getSession().getAttribute("logged");
		if(temp!=null) {
			int i = temp.getMessages();
			temp.setMessages(++i);
			newPost(threadId, post, temp.getId(), i);
			newPost = true;
		}
		else {
			request.setAttribute("error", true);
		}
		doGet(request, response);
	}

	public void newPost(int threadId, String post, int id, int postCount) {
		if(threadId!=-1) {
			Connection con = null;
			try {
				con = dataSource.getConnection();
				String update = "INSERT INTO post (POST, POSTTIME, THREADID, POSTERID) values('" + post + "', " + System.currentTimeMillis() + ", " + threadId + ", " + id + ")";
				Statement stmt = con.createStatement();
				stmt.execute(update);
				update = "UPDATE account set messages = " + postCount + " WHERE id = " + id;
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

	private ArrayList<ForumPost<Account>> getPosts(int threadId, int page) {
		ArrayList<ForumPost<Account>> output = new ArrayList<>();
		Connection con = null;
		try {
			con = dataSource.getConnection();
			//Derby does not support limit to get results from 50-100
			String query = "SELECT * FROM post WHERE threadId = " + threadId;
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				int id = rs.getInt(1);
				String post = rs.getString(2);
				long time = rs.getLong(3);
				int accountId = rs.getInt(5);
				query = "SELECT * FROM account WHERE id = " + accountId;
				Statement stmt2 = con.createStatement();
				ResultSet rs2 = stmt2.executeQuery(query);
				Account tempAcc = new Account("Error", 0, 0, -1);
				if(rs2.next()) {
					String logName = rs2.getString(2);
					long regDate = rs2.getLong(4);
					int messCount = rs2.getInt(5);
					int type = rs2.getInt(6);
					tempAcc= new Account(logName, regDate, messCount, type);
				}
				ForumPost<Account> tempForum = new ForumPost<>(post, time, tempAcc);
				tempForum.setId(id);
				output.add(tempForum);
			}
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
		return output;
	}

	public String getForum(int threadId) {
		try {
			Connection con = dataSource.getConnection();
			String query = "SELECT name, id FROM forum WHERE id IN (SELECT forumId FROM thread WHERE id = " + threadId + ")";
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

	public String getThread(int threadId) {
		try {
			Connection con = dataSource.getConnection();
			String query = "SELECT name FROM thread WHERE id = " + threadId;
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			if(rs.next()) {
				return rs.getString(1);
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