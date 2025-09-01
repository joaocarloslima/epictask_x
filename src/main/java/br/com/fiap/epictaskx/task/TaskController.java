package br.com.fiap.epictaskx.task;

import br.com.fiap.epictaskx.helper.MessageHelper;
import br.com.fiap.epictaskx.user.User;
import br.com.fiap.epictaskx.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final MessageSource messageSource;
    private final MessageHelper messageHelper;
    private final UserService userService;

    @GetMapping
    public String index(Model model, @AuthenticationPrincipal OAuth2User user){
        var avatar = user.getAttributes().get("picture") != null ?
                        user.getAttributes().get("picture") :
                        user.getAttributes().get("avatar_url");
        model.addAttribute("tasks", taskService.getAllTasks());
        model.addAttribute("user", user);
        model.addAttribute("avatar", avatar);
        return "index";
    }

    @GetMapping("/form")
    public String form(Task task){
        return "form";
    }

    @PostMapping("/form")
    public String create(@Valid Task task, BindingResult result, RedirectAttributes redirect){ //TODO DTO
        if(result.hasErrors()) return "form";
        taskService.save(task);
        redirect.addFlashAttribute("message", messageHelper.getMessage("task.save.success"));
        return "redirect:/task";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirect){
        taskService.deleteById(id);
        redirect.addFlashAttribute("message", messageHelper.getMessage("task.delete.success"));
        return "redirect:/task";
    }

    @PutMapping("/pick/{id}")
    public String pick(@PathVariable Long id, @AuthenticationPrincipal OAuth2User principal){
        taskService.pickTask(id, userService.register(principal));
        return "redirect:/task";
    }

    @PutMapping("/drop/{id}")
    public String drop(@PathVariable Long id, @AuthenticationPrincipal OAuth2User principal){
        taskService.dropTask(id, userService.register(principal));
        return "redirect:/task";
    }

    @PutMapping("/inc/{id}")
    public String increment(@PathVariable Long id, @AuthenticationPrincipal OAuth2User principal){
        taskService.incrementTaskStatus(id, userService.register(principal));
        return "redirect:/task";
    }

    @PutMapping("/dec/{id}")
    public String decremetn(@PathVariable Long id, @AuthenticationPrincipal OAuth2User principal){
        taskService.decrementTaskStatus(id, userService.register(principal));
        return "redirect:/task";
    }

}
