package com.gtn.ejbjboss.servlet;

import java.io.IOException;
import java.util.Collection;
import java.util.Objects;

import com.gtn.ejbjboss.core.Student;
import com.gtn.ejbjboss.dao.StudentDao;
import com.gtn.ejbjboss.ejb.AppMessageProducer;
//import com.gtn.ejbjboss.ejb.AppMessageProducer;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class StudentServlet
 */
@WebServlet("/student")
public class StudentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Inject
    private AppMessageProducer appMessageProducer;
    @EJB
    private StudentDao studentDao;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StudentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        String strId = request.getParameter("id");
        if(Objects.isNull(strId)) {
            Collection<Student> students = studentDao.getAll();
            request.setAttribute("students", students);
        } else {
            Student student = studentDao.getOne(Long.parseLong(strId));
            appMessageProducer.sendMessage(student);
            request.setAttribute("student", student);
        }
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }


}
