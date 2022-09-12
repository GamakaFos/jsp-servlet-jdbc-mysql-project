package web;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


import dao.UserDAO;

/**
 * Servlet implementation class UserServlet
 */
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDAO userDAO;
	
	public void init() {
		userDAO = new UserDAO();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		
		try {
		switch (action) {
			case "/new":
				showNewForm(request, response);
				break;
			case "/insert":
				insertUser(request, response);
				break;
			case "/update":
				updateUser(request, response);
				break;
			case "/delete":
				deleteUser(request, response);
				break;
			case "/edit":
				showEditForm(request, response);
				break;
			default:
				listOfUsers(request, response);
				break;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//return list of users to view layer
	private void listOfUsers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<User> users = new ArrayList<>();
		users = userDAO.selectAllUsers();
		
//		request.setAttribute("listUsers", users);
//		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/user-list.jsp");
//		dispatcher.forward(request,  response);
		
		response.setContentType("text/html");
		PrintWriter printWriter = response.getWriter();
		
		for(User elem: users) {
			printWriter.println("id: " + elem.getId() + "| name: " + elem.getName() + "| email:" + elem.getEmail() + "|country: " + elem.getCountry() + "<br>");
		}
	}

	
	//display user-form
	private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext servletContext = getServletContext();
		RequestDispatcher dispatcher = servletContext.getRequestDispatcher("/WEB-INF/user-form.jsp");
		dispatcher.forward(request, response);
		
//		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/user-form.jsp");
//		dispatcher.forward(request,  response);
	}
	
//    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
//        dispatcher.forward(request, response);
//    }
	
	
	// handle create user request
	private void insertUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ClassNotFoundException {
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String country = request.getParameter("country");
		
		userDAO.insertUser(new User(name, email, country));
		response.sendRedirect("list");
	}
	
	
	//edit user request
	private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		int id = Integer.parseInt(request.getParameter("id"));
		User existingUser = userDAO.selectUser(id);
		//what the hell is request dispatcher
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/user-form.jsp");
		request.setAttribute("user", existingUser);
		dispatcher.forward(request, response);
		
//		String UserId = request.getParameter("id");
//		userDAO.deleteUser(Integer.valueOf(UserId));
//		
//		response.sendRedirect("list");
	}
	
	
	// handle update request
	private void updateUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String country = request.getParameter("country");
		userDAO.updateUser(new User(id, name, email, country));
		response.sendRedirect("list");
	}
	
	
	
	// handle delete user
	private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int userId = Integer.valueOf(request.getParameter("id"));
		userDAO.deleteUser(userId);
		response.sendRedirect("list");
	}
	

	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
