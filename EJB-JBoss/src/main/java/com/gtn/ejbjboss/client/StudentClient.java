package com.gtn.ejbjboss.client;

import com.gtn.ejbjboss.core.Student;
import com.gtn.ejbjboss.ejb.StudentService;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

public class StudentClient {

	public static void main(String[] args) throws NamingException {
         System.out.println("Start EJB3 Client");
         Properties props = new Properties();           
         props.put("java.naming.factory.initial", "org.apache.openejb.client.LocalInitialContextFactory");
         Context ctx = new InitialContext(props);
         //TODO JNDI lookup for the EJB StudentService the name is name of Bean "studentService" suffixed with "Remote" ; 
	 	 StudentService studentService = (StudentService) ctx.lookup("studentServiceRemote");
         //TODO use handle on proxy to make a remote call to the EJB to get student with an id of 1L and print it out
	 	 Student student = studentService.getStudent(1L);
	 	 System.out.println(student);
	 	 System.out.println("END EJB3 Client"); 
	}

}
