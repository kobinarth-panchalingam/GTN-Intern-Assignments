package com.gtn.ejbjboss.servlet;

import com.gtn.ejbjboss.core.Student;
import com.gtn.ejbjboss.dao.StudentDao;
import com.gtn.ejbjboss.dao.StudentDaoWaitList;

import java.io.IOException;
import java.util.Collection;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Servlet implementation class WaitListServlet
 */
@WebServlet("/waitList")
public class WaitListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	@EJB
	private StudentDao studentDao;
	@Inject
	private StudentDaoWaitList studentDaoWaitList;
    public WaitListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		studentDaoWaitList.getAll().stream().forEach(p-> {
			studentDao.add(p);
		});
		session.invalidate();
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("surname");
		String dept = request.getParameter("dept");
				 
		Student student = new Student(firstName, lastName, dept, 0.00);
		studentDaoWaitList.add(student);
		Collection<Student> students = studentDaoWaitList.getAll();
		request.setAttribute("waitList", students);
				
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}

}
