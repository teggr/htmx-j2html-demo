package com.robintegg.htmx.demo.web;

import com.robintegg.htmx.demo.contacts.Contact;
import com.robintegg.htmx.demo.contacts.Contacts;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Map;

@Controller
public class ContactsController {

    private Contacts contacts;

    public ContactsController(Contacts contacts) {
        this.contacts = contacts;
    }

    @GetMapping("/contacts")
    public String contacts(
            @RequestHeader(name = "HX-Trigger", required = false) String hxTrigger,
            @RequestParam(name = "q", required = false) String q,
            @RequestParam(name = "msg", required = false) String msg,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            Model model
    ) {
        if (q == null) {
            model.addAttribute("contacts", contacts.all(page));
        } else {
            model.addAttribute("contacts", contacts.search(q, page));
        }
        model.addAttribute("q", q);
        model.addAttribute("msg", msg);
        model.addAttribute("page", page);
        if (hxTrigger != null) {
            return "searchResults";
        } else {
            return "indexPage";
        }
    }

    @GetMapping("/contacts/new")
    public String newContacts(Model model) {
        model.addAttribute("contact", new Contact(0, "", "", "", ""));
        model.addAttribute("errors", new MapBindingResult(Map.of(), "contact"));
        return "newContactPage";
    }

    @PostMapping("/contacts/new")
    public String newContacts(@ModelAttribute @Valid Contact c, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
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
    public String newContacts(@PathVariable("id") Integer id, @ModelAttribute @Valid Contact c, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
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
    public String postDeleteContact(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        contacts.delete(id);
        redirectAttributes.addAttribute("msg", "Deleted Contact");
        return "redirect:/contacts";
    }

    @DeleteMapping("/contacts/{id}")
    public ModelAndView deleteContact(
            @PathVariable("id") Integer id,
            @RequestHeader(name = "HX-Trigger", required = false) String hxTrigger,
            Model model,
            RedirectAttributes redirectAttributes) {
        contacts.delete(id);
        if ("delete-btn".equals(hxTrigger)) {
            redirectAttributes.addAttribute("msg", "Deleted Contact");
            return new ModelAndView(new RedirectView("/contacts", true, false));
        } else {
            return new ModelAndView("emptyView", model.asMap());
        }
    }

    @GetMapping(path = "/contacts/{id}/email", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String contactEmail(
            @PathVariable("id") Integer id,
            @RequestParam("email") String email) {
        return "this is an error";
    }

    @GetMapping("/contacts/{id}/email")
    @ResponseBody
    public String contactEmail(@PathVariable("id") Integer id, @ModelAttribute @Valid Contact c, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors("email")) {
            return bindingResult.getFieldError("email").toString();
        } else {
            return "";
        }
    }

    @GetMapping(path = "/contacts/count", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String contactCount() {
        return "(" + contacts.count() + " total contacts)";
    }

    @DeleteMapping("/contacts")
    public String deleteAll(
            @RequestParam("selected_contact_ids") List<Integer> ids,
            Model model) {
        contacts.delete(ids);
        return contacts(null,null, "Deleted Contacts!", 1, model);
    }

}
