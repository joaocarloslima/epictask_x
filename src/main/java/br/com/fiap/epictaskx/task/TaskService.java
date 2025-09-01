package br.com.fiap.epictaskx.task;

import br.com.fiap.epictaskx.helper.MessageHelper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final MessageSource messageSource;
    private final MessageHelper messageHelper;

    public TaskService(TaskRepository taskRepository, MessageSource messageSource, MessageHelper messageHelper) {
        this.taskRepository = taskRepository;
        this.messageSource = messageSource;
        this.messageHelper = messageHelper;
    }

    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }

    public Task save(Task task) {
        return taskRepository.save(task);
    }

    public void deleteById(Long id) {

        if (!taskRepository.existsById(id)){
            var message = messageSource.getMessage("task.notfound", null, LocaleContextHolder.getLocale());
            throw new EntityNotFoundException(messageHelper.getMessage("task.notfound"));
        }
        taskRepository.deleteById(id);
    }
}
