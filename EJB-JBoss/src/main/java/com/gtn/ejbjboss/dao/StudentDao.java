package com.gtn.ejbjboss.dao;

import com.gtn.ejbjboss.core.Student;

import java.util.Collection;

public interface StudentDao {
    Student getOne(long id);
    Collection<Student> getAll();
    void add(Student student);
}