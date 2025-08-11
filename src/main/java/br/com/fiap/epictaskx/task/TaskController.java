package br.com.fiap.epictaskx.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public String index(Model model){
        model.addAttribute("tasks", taskService.getAllTasks());
        return "index";
    }

    @GetMapping("/form")
    public String form(){
        return "form";
    }

    @PostMapping("/form")
    public String create(Task task, RedirectAttributes redirect){ //TODO DTO
        taskService.save(task);
        redirect.addFlashAttribute("message", "Tarefa cadastrada com sucesso !");
        return "redirect:/task";
    }

}
