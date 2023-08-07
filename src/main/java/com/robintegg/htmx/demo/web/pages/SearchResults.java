package com.robintegg.htmx.demo.web.pages;

import com.robintegg.htmx.demo.contacts.Contact;
import com.robintegg.htmx.demo.contacts.PagedList;
import j2html.rendering.IndentedHtml;
import j2html.tags.DomContent;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.View;

import java.util.Map;

import static j2html.TagCreator.*;

@Component
public class SearchResults implements View {

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        include(model).render(IndentedHtml.into(response.getWriter()));
    }

    public DomContent include(Map<String, ?> model) throws Exception {

        PagedList<Contact> contacts = (PagedList<Contact>) model.get("contacts");
        String q = (String) model.get("q");
        int page = (int) model.get("page");

        return each(
                each(contacts.items(), contact -> {
                    return tr(
                            td(contact.first()),
                            td(contact.last()),
                            td(contact.phone()),
                            td(contact.email()),
                            td(
                                    a("Edit").withHref("/contacts/" + contact.id() + "/edit"),
                                    a("View").withHref("/contacts/" + contact.id()),
                                    a("Delete").withHref("#")
                                            .attr("hx-confirm", "Are you sure you want to delete this contact?")
                                            .attr("hx-delete","/contacts/" + contact.id())
                                            .attr("hx-target", "closest tr")
                                            .attr("hx-swap", "outerHTML")
                            )
                    );
                }),
                iff(contacts.hasNext(),
                        tr(
                                td().withColspan("5").withStyle("text-align: center;").with(
                                        span("Loading More...")
                                                .attr("hx-trigger", "revealed")
                                                .attr("hx-get", "/contacts?page=" + (page + 1) + (q != null ? "&q=" + q : ""))
                                                .attr("hx-select", "tbody > tr")
                                                .attr("hx-target", "closest tr")
                                                .attr("hx-swap", "outerHTML")
                                )
                        )
                )
        );

    }

    @Override
    public String getContentType() {
        return MediaType.TEXT_HTML_VALUE;
    }


}
