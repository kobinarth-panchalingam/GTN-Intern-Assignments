package com.gtn.ejbjboss.ejb;

import com.gtn.ejbjboss.core.Student;
import com.gtn.ejbjboss.dao.StudentDao;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.enterprise.inject.Any;
import javax.inject.Inject;


//TODO Annotations the name for the bean is "studentService"
@Stateless(name="studentService")
//TODO Remote is studentService
@Remote(StudentService.class)
public class StudentServiceBean implements StudentService {
	//TODO inject the StudentDao bean into this EJB,  
	@Inject
	private StudentDao studentDao;

	@Override
	public Student getStudent(Long id) {
		return studentDao.getOne(id);
	}
 
}
