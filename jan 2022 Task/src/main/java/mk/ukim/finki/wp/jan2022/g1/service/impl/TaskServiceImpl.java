package mk.ukim.finki.wp.jan2022.g1.service.impl;

import mk.ukim.finki.wp.jan2022.g1.model.Task;
import mk.ukim.finki.wp.jan2022.g1.model.TaskCategory;
import mk.ukim.finki.wp.jan2022.g1.model.User;
import mk.ukim.finki.wp.jan2022.g1.model.exceptions.InvalidTaskIdException;
import mk.ukim.finki.wp.jan2022.g1.model.exceptions.InvalidUserIdException;
import mk.ukim.finki.wp.jan2022.g1.repository.TaskRepository;
import mk.ukim.finki.wp.jan2022.g1.repository.UserRepository;
import mk.ukim.finki.wp.jan2022.g1.service.TaskService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService
{
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Task> listAll() {
        return taskRepository.findAll();
    }

    @Override
    public Task findById(Long id) {
        return taskRepository.findById(id).orElseThrow(InvalidTaskIdException::new);
    }

    @Override
    public Task create(String title, String description, TaskCategory category, List<Long> assignees, LocalDate dueDate) {
        List<User> assigneesList = userRepository.findAllById(assignees);
        Task task = new Task(title, description, category, assigneesList, dueDate);
        return taskRepository.save(task);
    }

    @Override
    public Task update(Long id, String title, String description, TaskCategory category, List<Long> assignees) {
        List<User> assigneesList = userRepository.findAllById(assignees);
        Task task = findById(id);
        task.setTitle(title);
        task.setDescription(description);
        task.setCategory(category);
        task.setAssignees(assigneesList);
        return taskRepository.save(task);
    }

    @Override
    public Task delete(Long id) {
        Task task = findById(id);
        taskRepository.delete(task);
        return task;
    }

    @Override
    public Task markDone(Long id) {
        Task task = findById(id);
        task.setDone(true);
        return taskRepository.save(task);
    }

//    Потребно е да овозможите пребарување на задачи според assignee и п
//    реостанати денови (dueDate < now + filtering days) преку формата со id="filter-form" во темплејтот list.html.
    @Override
    public List<Task> filter(Long assigneeId, Integer lessThanDayBeforeDueDate) {
        User assignee = assigneeId != null ? userRepository.findById(assigneeId).orElseThrow(InvalidUserIdException::new) : null;
        if (assigneeId != null && lessThanDayBeforeDueDate != null) {
            LocalDate date = LocalDate.now().plusDays(lessThanDayBeforeDueDate);
            return taskRepository.findAllByAssigneesContainingAndDueDateBefore(assignee, date);
        }

        if (assigneeId != null) {
            return taskRepository.findAllByAssigneesContaining(assignee);
        }

        if (lessThanDayBeforeDueDate != null) {
            LocalDate date = LocalDate.now().plusDays(lessThanDayBeforeDueDate);
            return taskRepository.findAllByDueDateBefore(date);
        }

        return listAll();

    }
}
