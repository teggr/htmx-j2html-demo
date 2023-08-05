package com.robintegg.htmx.demo.web.pages;

import com.robintegg.htmx.demo.contacts.Contact;
import com.robintegg.htmx.demo.contacts.PagedList;
import j2html.rendering.IndentedHtml;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.View;

import java.util.Map;

import static j2html.TagCreator.*;

@Component
public class IndexPage implements View {

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {

        PagedList<Contact> contacts = (PagedList<Contact>) model.get("contacts");
        String q = (String) model.get("q");
        int page = (int) model.get("page");

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
                                each(contacts.items(), contact -> {
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
                div().withStyle("float:right").with(
                        iff(contacts.hasPrevious(),
                                a("Previous").withHref("/contacts?page=" + (page - 1))
                        ),
                        iff(contacts.hasNext(),
                                a("Next").withHref("/contacts?page=" + (page + 1))
                        )
                ),
                p(
                        a("Add Contact").withHref("/contacts/new")
                )
        ).render(IndentedHtml.into(response.getWriter()));

    }

    @Override
    public String getContentType() {
        return MediaType.TEXT_HTML_VALUE;
    }

}
