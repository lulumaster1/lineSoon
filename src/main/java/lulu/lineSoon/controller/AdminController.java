package lulu.lineSoon.controller;

import lulu.lineSoon.model.User;
import lulu.lineSoon.service.LineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final LineService service;

    @Autowired
    public AdminController(LineService service) { this.service = service; }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("users", service.waitingList());
        return "admin_list";
    }

    @PostMapping("/dequeue")
    public String dequeue() {
        service.dequeue();
        return "redirect:/admin/list";
    }

    @PostMapping("/remove")
    public String remove(@RequestParam int order) {
        service.removeByOrder(order);
        return "redirect:/admin/list";
    }
}