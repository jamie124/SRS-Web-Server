package Server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Server.IOProcessor;

/**
 * Servlet implementation class Server
 */
@WebServlet("/Server")
public class Server extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private IOProcessor ioProcessor;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Server() {
		super();

		// IOProcessor is main class for running the server
		ioProcessor = new IOProcessor();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();

		String userStatus = "";

		userStatus = request.getParameter("userStatus");

		if (userStatus.equals("numberOfUsers")) {
			out.println(ioProcessor.userManager().getNumOfUsers());
		} else if (userStatus.equals("usersList")) {
			out.println(ioProcessor.userManager().convertUsersToJSON());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();

		String addUser = "";

		addUser = request.getParameter("addUser");

	}

}
