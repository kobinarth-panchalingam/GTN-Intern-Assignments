package com.gtn.ejbjboss.dao;

import com.gtn.ejbjboss.core.Student;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Local;
import javax.ejb.PostActivate;
import javax.ejb.PrePassivate;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

//TODO
@Stateful
@SessionScoped
@Local
@Named("studentDaoWaitList")
public class StudentDaoWaitListBean implements StudentDaoWaitList {

	private List<Student> provisionalStudents = new ArrayList<>();

	@Override
	public void add(Student student) {
		this.provisionalStudents.add(student);

	}

	@Override
	public Collection<Student> getAll() {
		return provisionalStudents;
	}
 
     //TODO
	@PostConstruct
	public void init() {
		System.out.println("WaitList PostConstruct method called");
	}

     //TODO
	@PreDestroy
	public void exit() {
		System.out.println("WaitList PreDestroy method called");
	}

 

}
