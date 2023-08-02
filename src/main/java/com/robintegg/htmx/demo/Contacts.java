package com.robintegg.htmx.demo;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Contacts {

  private List<Contact> contactList = new ArrayList<>();

  @PostConstruct
  void loadContacts() {
    contactList.add(new Contact(1, "John", "Smith", "123-456-7890", "john@example.comz" ));
    contactList.add(new Contact(2, "Dana", "Crandith", "123-456-7890", "dcran@example.com" ));
    contactList.add(new Contact(3, "Edith", "Neutvaar", "123-456-7890", "an@example.com" ));
  }

  public List<Contact> all() {
    return new ArrayList<>(contactList);
  }

  public List<Contact> search(String q) {
    return contactList.stream().toList();
  }

  public void save(Contact c) {
    contactList.add(c);
  }
}
