package com.robintegg.htmx.demo.contacts;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.min;

@Component
public class Contacts {

    private List<Contact> contactList = new ArrayList<>();

    private int idSequence = 4;

    @PostConstruct
    void loadContacts() {
        contactList.add(new Contact(1, "John", "Smith", "123-456-7890", "john@example.comz"));
        contactList.add(new Contact(2, "Dana", "Crandith", "123-456-7890", "dcran@example.com"));
        contactList.add(new Contact(3, "Edith", "Neutvaar", "123-456-7890", "an@example.com"));
        contactList.add(new Contact(1, "John", "Smith", "123-456-7890", "john@example.comz"));
        contactList.add(new Contact(4, "Henry", "Pire", "123-456-7890", "dcran@example.com"));
        contactList.add(new Contact(5, "Jayne", "Tweet", "123-456-7890", "an@example.com"));
        contactList.add(new Contact(6, "Terry", "Thomson", "123-456-7890", "john@example.comz"));
        contactList.add(new Contact(7, "Joseph", "Crandith", "123-456-7890", "dcran@example.com"));
        contactList.add(new Contact(8, "Anna", "Banner", "123-456-7890", "an@example.com"));
        contactList.add(new Contact(9, "Abigail", "Polite", "123-456-7890", "john@example.comz"));
        contactList.add(new Contact(10, "Jenna", "Henda", "123-456-7890", "dcran@example.com"));
        contactList.add(new Contact(11, "George", "Tunst", "123-456-7890", "an@example.com"));
        contactList.add(new Contact(12, "Peppa", "Jenstter", "123-456-7890", "an@example.com"));

    }

    public PagedList<Contact> all(int page) {
        int end = page * 10;
        int start = end - 10;
        return new PagedList<>(
                new ArrayList<>(contactList.subList(start, min(contactList.size(), end))),
                page > 1,
                end <= contactList.size(),
                contactList.size()
        );
    }

    public PagedList<Contact> search(String q, int page) {
        int end = page * 10;
        int start = end - 10;
        List<Contact> filteredList = contactList.stream()
                .filter(c -> c.email().toLowerCase().contains(q.toLowerCase()) ||
                        c.phone().toLowerCase().contains(q.toLowerCase()) ||
                        c.first().toLowerCase().contains(q.toLowerCase()) ||
                        c.last().toLowerCase().contains(q.toLowerCase()))
                .toList();
        return new PagedList<>(
                new ArrayList<>(filteredList.subList(start, min(filteredList.size(), end))),
                page > 1,
                end <= filteredList.size(),
                filteredList.size());
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
