package com.boshko.jrspringproject1.controllers;

import com.boshko.jrspringproject1.domain.Task;
import com.boshko.jrspringproject1.dto.TaskDTO;
import com.boshko.jrspringproject1.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@Controller
public class TasksController {
    private final TaskService taskService;

    @Autowired
    public TasksController(TaskService taskService) {
        this.taskService = taskService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getAllTask(Model model,
                             @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                             @RequestParam(value = "limitOnPage", required = false, defaultValue = "10") int limitOnPage) {
        List<Task> tasks = taskService.findAll((page - 1) * limitOnPage, limitOnPage);
        model.addAttribute("tasks", tasks);
        model.addAttribute("newTask", new TaskDTO());
        model.addAttribute("updateTask", new TaskDTO());
        List<Long> pages = new LinkedList<>();
        for (int i = 0; i < (int) Math.ceil(1.0 * taskService.getCount() / limitOnPage); i++) {
            pages.add((long) (i + 1));
        }
        model.addAttribute("countOfPages", pages);
        return "task";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String deleteTaskById(@PathVariable("id") Integer id) {
        taskService.findById(id).ifPresent(taskService::deleteById);
        return "redirect:/";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addNewTask(@ModelAttribute("newTask") TaskDTO taskDTO) {
        taskService.save(taskDTO);
        return "redirect:/";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateTask(@ModelAttribute("updateTask") TaskDTO taskDTO) {
        taskService.update(taskDTO);
        return "redirect:/";
    }
}
