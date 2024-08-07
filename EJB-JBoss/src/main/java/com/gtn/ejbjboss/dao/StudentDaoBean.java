package com.gtn.ejbjboss.dao;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.gtn.ejbjboss.core.Student;
import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

//TODO Annotations this is a Stateless bean
@Stateless
//TODO This is a Local bean only
@Local(StudentDao.class)
@Interceptors(value=StudentInterceptor.class)
public class StudentDaoBean implements StudentDao {
    @Resource
    private SessionContext sessionContext;
    @PersistenceContext(unitName="student")
    private EntityManager em;
    @Override
    public Student getOne(long id) {
        return em.find(Student.class,id);
    }
    @Override
    public Collection<Student> getAll() {
        return em.createQuery("SELECT S FROM Student s").getResultList();
    }
    @Override
    public void add(Student student) {
        // TODO Auto-generated method stub
//		long maxId = students.keySet().stream().count();
        student.setFees(100.00);
//		student.setId(++maxId);
//		students.put(maxId, student);
        em.persist(student);

    }

}

