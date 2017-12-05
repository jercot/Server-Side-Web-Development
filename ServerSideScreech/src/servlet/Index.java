package servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.math.NumberUtils;

import model.Calculate;
import model.FileOutput;
import model.IndexBean;

/**
 * Servlet implementation class Response
 */
@WebServlet("/Index")
public class Index extends HttpServlet {

	private static final long serialVersionUID = 1L;

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
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String skid = request.getParameter("skid");
		String surface = request.getParameter("surface");
		String cookieSave = request.getParameter("cookies");
		Calculate speedCalculator = null;
		IndexBean bean = new IndexBean();
		bean.setChecked(surface);
		if(NumberUtils.isCreatable(skid)) {
			Double temp = Double.parseDouble(skid);
			if(temp>0) {
				HttpSession sesh = request.getSession();
				Date createTime = new Date(sesh.getCreationTime());
				speedCalculator = new Calculate(skid, surface);
				request.setAttribute("answer", speedCalculator);
				bean.setCalculation("");
				FileOutput fileWrite = FileOutput.getFile();
				fileWrite.writeFile(speedCalculator.getAnswer(), createTime);
			}
			else
				bean.setCalculation("Error");
		}
		else
			bean.setCalculation("Error");
		if(speedCalculator!=null&&cookieSave!=null) {
			int cookieLife = 60;
			response.addCookie(createCookie("savedSpeed", "Starting Speed: " + speedCalculator.getAnswer(), cookieLife));
			response.addCookie(createCookie("savedSurface", "Surface: " + speedCalculator.getSurfaceS(), cookieLife));
			response.addCookie(createCookie("savedSkid", "Distance: " + speedCalculator.getLength() + " feet", cookieLife));
		}
		request.setAttribute("index", bean);
		doGet(request, response);
	}

	private Cookie createCookie(String name, String details, int life) {
		Cookie temp = new Cookie(name, details);
		temp.setMaxAge(life);
		return temp;
	}
}
