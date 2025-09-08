package br.com.fiap.epictaskx.task;

import br.com.fiap.epictaskx.helper.MessageHelper;
import br.com.fiap.epictaskx.user.User;
import br.com.fiap.epictaskx.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final MessageSource messageSource;
    private final MessageHelper messageHelper;
    private final UserService userService;

    public TaskService(TaskRepository taskRepository, MessageSource messageSource, MessageHelper messageHelper, UserService userService) {
        this.taskRepository = taskRepository;
        this.messageSource = messageSource;
        this.messageHelper = messageHelper;
        this.userService = userService;
    }

    public List<Task> getUndoneTask(){
        return taskRepository.findByStatusLessThan(100);
    }

    public Task save(Task task) {
        return taskRepository.save(task);
    }

    public void deleteById(Long id) {
        taskRepository.delete(getTask(id));
    }

    public void pickTask(Long id, User user) {
        var task = getTask(id);
        task.setUser(user);
        taskRepository.save(task);
    }

    public void dropTask(Long id, User user) {
        var task = getTask(id);
        if(!task.getUser().equals(user)){
            throw new IllegalStateException(messageHelper.getMessage("task.notowner"));
        }
        task.setUser(null);
        taskRepository.save(task);
    }

    public void incrementTaskStatus(Long id, User user) {
        var task = getTask(id);
        task.setStatus(task.getStatus() + 10);
        if (task.getStatus() > 100) task.setStatus(100);

        if (task.getStatus() == 100) {
            userService.addScore(user, task.getScore());
        }

        taskRepository.save(task);
    }

    public void decrementTaskStatus(Long id, User user) {
        var task = getTask(id);
        task.setStatus(task.getStatus() - 10);
        if (task.getStatus() < 0) task.setStatus(0);

        taskRepository.save(task);
    }

    private Task getTask(Long id) {
        return taskRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(messageHelper.getMessage("task.notfound"))
        );
    }
}
