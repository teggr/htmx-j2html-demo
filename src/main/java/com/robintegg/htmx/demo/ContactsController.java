package com.robintegg.htmx.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
public class ContactsController {

    private Contacts contacts;

    public ContactsController(Contacts contacts) {
        this.contacts = contacts;
    }

    @GetMapping("/contacts")
    public String contacts(@RequestParam(name = "q", required = false) String q, @RequestParam(name = "msg", required = false) String msg, Model model) {
        if (q == null) {
            model.addAttribute("contacts", contacts.all());
        } else {
            model.addAttribute("contacts", contacts.search(q));
        }
        model.addAttribute("q", q);
        model.addAttribute("msg", msg);
        return "indexPage";
    }

    @GetMapping("/contacts/new")
    public String newContacts(Model model) {
        model.addAttribute("contact", new Contact(0, "", "", "", ""));
        model.addAttribute("errors", new MapBindingResult(Map.of(), "contact"));
        return "newContactPage";
    }

    @PostMapping("/contacts/new")
    public String newContacts(@ModelAttribute Contact c, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("contact", c);
            model.addAttribute("errors", bindingResult);
            return "newContactPage";
        } else {
            contacts.save(c);
            redirectAttributes.addAttribute("msg", "Created New Contact");
            return "redirect:/contacts";
        }
    }

}
