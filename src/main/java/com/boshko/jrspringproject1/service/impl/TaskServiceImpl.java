package com.boshko.jrspringproject1.service.impl;

import com.boshko.jrspringproject1.dao.TaskDAO;
import com.boshko.jrspringproject1.domain.Status;
import com.boshko.jrspringproject1.domain.Task;
import com.boshko.jrspringproject1.dto.TaskDTO;
import com.boshko.jrspringproject1.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskDAO taskRepository;

    @Autowired
    public TaskServiceImpl(TaskDAO taskRepository) {
        this.taskRepository = taskRepository;
    }


    @Override
    public List<Task> findAll(int offset, int limit) {
        return taskRepository.findAll(offset, limit);
    }

    @Override
    public Long getCount() {
        return taskRepository.getCount();
    }

    @Override
    public Optional<Task> findById(Integer id) {
        return taskRepository.findById(id);
    }

    @Override
    @Transactional
    public void deleteById(Task task) {
        findById(task.getId()).ifPresent(taskRepository::deleteById);
    }

    @Override
    @Transactional
    public void update(TaskDTO taskDTO) {
        Task updatedTask = findById(taskDTO.getId()).orElse(null);
        if (updatedTask != null) {
            if (taskDTO.getDescription() != null) {
                updatedTask.setDescription(taskDTO.getDescription());
            }
            if (taskDTO.getStatus() != null) {
                updatedTask.setStatus(Status.values()[taskDTO.getStatus()]);
            }
            taskRepository.update(updatedTask);
        }
    }

    @Override
    public void save(TaskDTO taskDTO) {
        Task newTask = new Task();
        newTask.setDescription(taskDTO.getDescription());
        newTask.setStatus(Status.values()[taskDTO.getStatus()]);
        taskRepository.save(newTask);
    }
}
