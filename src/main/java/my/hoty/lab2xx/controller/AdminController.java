package my.hoty.lab2xx.controller;

import my.hoty.lab2xx.repository.ClientRepo;
import my.hoty.lab2xx.repository.RoleRepo;
import my.hoty.lab2xx.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private ClientRepo clientRepo;
    @Autowired
    private ClientService clientService;

    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("clients", clientRepo.findAll());
        model.addAttribute("roles", roleRepo.findAll());
        return "admin";
    }

    @RequestMapping(value = "/admin/editRole", method = RequestMethod.POST)
    public String editRole(@RequestParam("username") String username, @RequestParam("roleX") String roleX, Model model) {
        clientService.updateClientRole(username, roleX);
        return "redirect:/admin?success";
    }

}
