package com.boshko.jrspringproject1.dao.impl;

import com.boshko.jrspringproject1.dao.TaskDAO;
import com.boshko.jrspringproject1.domain.Task;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class TaskDAOImpl implements TaskDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public TaskDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> findAll(int offset, int limit) {
        Query<Task> taskQuery = sessionFactory.getCurrentSession().createQuery("from Task", Task.class);
        taskQuery.setFirstResult(offset);
        taskQuery.setMaxResults(limit);
        return taskQuery.getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Long getCount() {
        Query<Long> integerTaskQuery = sessionFactory.getCurrentSession()
                .createQuery("select count(t) from Task t", Long.class);
        return integerTaskQuery.uniqueResult();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Task> findById(Integer id) {
        Query<Task> findByIdTaskQuery = sessionFactory.getCurrentSession()
                .createQuery("from Task t where t.id = :id", Task.class);
        findByIdTaskQuery.setParameter("id", id);
        return Optional.of(findByIdTaskQuery.getSingleResult());
    }

    @Override
    @Transactional
    public void deleteById(Task task) {
        sessionFactory.getCurrentSession().remove(task);
    }

    @Override
    @Transactional
    public void save(Task task) {
        sessionFactory.getCurrentSession().persist(task);
    }

    @Override
    @Transactional
    public void update(Task task) {
        sessionFactory.getCurrentSession().update(task);
    }
}
