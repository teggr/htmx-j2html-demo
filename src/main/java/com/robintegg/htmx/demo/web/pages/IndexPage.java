package com.robintegg.htmx.demo.web.pages;

import com.robintegg.htmx.demo.contacts.Contact;
import j2html.rendering.IndentedHtml;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.View;

import java.util.List;
import java.util.Map;

import static j2html.TagCreator.*;

@Component
public class IndexPage implements View {

  @Override
  public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {

      List<Contact> contacts = (List<Contact>) model.get("contacts");
      String q = (String) model.get("q");

      Layout.withContent(
        model,
        request,
        form()
            .withAction("/contacts")
            .withMethod("get")
            .withClass("tool-bar")
            .with(
                label("Search Term")
                    .withFor("search"),
                input()
                    .withId("search")
                    .withType("search")
                    .withName("q")
                    .withValue(q),
                input()
                    .withType("submit")
                    .withValue("Search")
            ),
        table(
            thead(
                tr(
                    th("First"),
                    th("Last"),
                    th("Phone"),
                    th("Email")
                )
            ),
            tbody(
                each(contacts, contact -> {
                  return tr(
                      td(contact.first()),
                      td(contact.last()),
                      td(contact.phone()),
                      td(contact.email()),
                      td(
                          a("Edit").withHref("/contacts/" + contact.id() + "/edit"),
                          a("View").withHref("/contacts/" + contact.id())
                      )
                  );
                })
            )
        ),
        p(
            a("Add Contact").withHref("/contacts/new")
        )
    ).render(IndentedHtml.into(response.getWriter()));

  }

  @Override
  public String getContentType() {
    return "text/html";
  }

}
