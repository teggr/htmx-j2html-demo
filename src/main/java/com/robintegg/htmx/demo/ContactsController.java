package com.robintegg.htmx.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ContactsController {

  private Contacts contacts;

  public ContactsController(Contacts contacts) {
    this.contacts = contacts;
  }

  @GetMapping("/contacts")
  public String contacts(@RequestParam(name = "q", required = false) String q, Model model) {
    if (q == null) {
      model.addAttribute("contacts", contacts.all());
    } else {
      model.addAttribute("contacts", contacts.search(q));
    }
    model.addAttribute("q", q);
    return "indexPage";
  }

  @GetMapping("/contacts/new")
  public String newContacts(Model model) {
    model.addAttribute("contact", new Contact(0,"","","",""));
    return "newContactPage";
  }

}
