package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Info
 */
@WebServlet("/Info")
public class Info extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Info() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter output = response.getWriter();
		output.println( "<!DOCTYPE html><HTML><HEAD><TITLE>" );
		output.println( "Header Details" );
		output.println( "</TITLE></HEAD><BODY>" );
		output.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + request.getContextPath() + "/CSS/header.css\" />");
		output.println("<div class=\"centerOne\"><div class=\"centerTwo\"><div class=\"outter\"><div class=\"inner\">");
		output.println("<table style=\"width:100%\"><tr><th>Name</th><th>Value</th></tr>");
		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String name = headerNames.nextElement();
			String header = request.getHeader(name);
			output.println("<tr><td>" + name + "</td><td>" + header + "</td></tr>");
		}
		output.println("</table><br><div class = \"sessionSet\"><form action=\"SessionScope\" method=\"POST\">");
		output.println("Add to session scope: <input type=\"text\" name=\"session\" class = \"textfield\">");
		output.println("<input type=\"submit\" value=\"Main Page\">");
		output.println("</form></div><br>");
		output.println("</div></div></div></div>");
		output.println( "</BODY></HTML>" );
		output.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
