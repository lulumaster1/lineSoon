package lulu.lineSoon.controller;

import lulu.lineSoon.model.User;
import lulu.lineSoon.service.LineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {
    private final LineService service;
    @Autowired
    public UserController(LineService service) { this.service = service; }

    @PostMapping("/register")
    public String register(@RequestParam String nickname, Model model) {
        var u = service.enqueue(nickname);
        model.addAttribute("message", "등록 완료: " + u.getNickname());
        return "result";
    }

    @PostMapping("/position")
    public String position(@RequestParam String nickname, Model model) {
        int pos = service.getPosition(nickname);
        model.addAttribute("message", "현재 순서: " + pos + "번");
        return "result";
    }

    @PostMapping("/cancel")
    public String cancel(@RequestParam String nickname, Model model) {
        service.cancel(nickname);
        model.addAttribute("message", "취소 완료: " + nickname);
        return "result";
    }
}