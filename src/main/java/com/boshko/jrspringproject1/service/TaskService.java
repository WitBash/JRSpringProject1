package com.boshko.jrspringproject1.service;

import com.boshko.jrspringproject1.domain.Task;
import com.boshko.jrspringproject1.dto.TaskDTO;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    List<Task> findAll(int offset, int limit);

    Long getCount();
    Optional<Task> findById(Integer id);
    void deleteById(Task task);

    void update(TaskDTO taskDTO);

    void save(TaskDTO taskDTO);
}
