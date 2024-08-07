package com.gtn.ejbjboss.dao;

import com.gtn.ejbjboss.core.Student;

import java.util.Collection;

public interface StudentDaoWaitList {
	void add(Student student);
	Collection<Student> getAll();
}
