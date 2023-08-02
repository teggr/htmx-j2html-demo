package com.robintegg.htmx.demo.web;

import com.robintegg.htmx.demo.contacts.Contact;
import com.robintegg.htmx.demo.contacts.Contacts;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.annotation.*;
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
            contacts.add(c);
            redirectAttributes.addAttribute("msg", "Created New Contact");
            return "redirect:/contacts";
        }
    }

    @GetMapping("/contacts/{id}")
    public String viewContact(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("contact", contacts.find(id));
        return "contactPage";
    }

    @GetMapping("/contacts/{id}/edit")
    public String editContact(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("contact", contacts.find(id));
        model.addAttribute("errors", new MapBindingResult(Map.of(), "contact"));
        return "editContactPage";
    }

    @PostMapping("/contacts/{id}/edit")
    public String newContacts(@PathVariable("id") Integer id, @ModelAttribute Contact c, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("contact", c);
            model.addAttribute("errors", bindingResult);
            return "editContactPage";
        } else {
            contacts.update(id, c);
            redirectAttributes.addAttribute("msg", "Updated Contact");
            return "redirect:/contacts/" + id;
        }
    }

    @PostMapping("/contacts/{id}/delete")
    public String deleteContact(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        contacts.delete(id);
        redirectAttributes.addAttribute("msg", "Deleted Contact");
        return "redirect:/contacts";
    }

}
