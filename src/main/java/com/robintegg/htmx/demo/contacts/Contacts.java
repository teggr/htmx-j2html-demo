package com.robintegg.htmx.demo.contacts;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Contacts {

  private List<Contact> contactList = new ArrayList<>();

  private int idSequence = 4;

  @PostConstruct
  void loadContacts() {
    contactList.add(new Contact(1, "John", "Smith", "123-456-7890", "john@example.comz"));
    contactList.add(new Contact(2, "Dana", "Crandith", "123-456-7890", "dcran@example.com"));
    contactList.add(new Contact(3, "Edith", "Neutvaar", "123-456-7890", "an@example.com"));
  }

  public List<Contact> all() {
    return new ArrayList<>(contactList);
  }

  public List<Contact> search(String q) {
    return contactList.stream()
        .filter(c -> c.email().toLowerCase().contains(q.toLowerCase()) ||
            c.phone().toLowerCase().contains(q.toLowerCase()) ||
            c.first().toLowerCase().contains(q.toLowerCase()) ||
            c.last().toLowerCase().contains(q.toLowerCase()))
        .toList();
  }

  public void add(Contact c) {
    contactList.add(new Contact(++idSequence, c.first(), c.last(), c.phone(), c.email()));
  }

  public Contact find(Integer id) {
    return contactList.stream().filter(c -> c.id().equals(id)).findFirst().orElseThrow();
  }

  public void update(Integer id, Contact c) {
    Contact contact = find(id);
    contactList.remove(contact);
    contactList.add(new Contact(id, c.first(), c.last(), c.phone(), c.email()));
  }

  public void delete(Integer id) {
    Contact contact = find(id);
    contactList.remove(contact);
  }
}
