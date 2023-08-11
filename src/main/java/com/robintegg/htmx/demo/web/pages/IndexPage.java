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

    private final RowsView rowsView;
    private final ArchiveView archiveView;

    public IndexPage(RowsView rowsView, ArchiveView archiveView) {
        this.rowsView = rowsView;
        this.archiveView = archiveView;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {

        PagedList<Contact> contacts = (PagedList<Contact>) model.get("contacts");
        String q = (String) model.get("q");

        Layout.withContent(
                model,
                request,
                archiveView.include(model),
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
                                        .withValue(q)
                                        .attr("hx-trigger", "search, keyup delay:200ms changed")
                                        .attr("hx-get", "/contacts")
                                        .attr("hx-target", "tbody")
                                        .attr("hx-select", "tbody tr")
                                        .attr("hx-push-url", true)
                                        .attr("hx-indicator", "#spinner"),
                                img()
                                        .withId("spinner")
                                        .withClass("htmx-indicator")
                                        .withSrc("https://raw.githubusercontent.com/n3r4zzurr0/svg-spinners/main/svg-css/90-ring.svg"),
                                input()
                                        .withType("submit")
                                        .withValue("Search")
                        ),
                form(
                        table(
                                thead(
                                        tr(
                                                th(),
                                                th("First"),
                                                th("Last"),
                                                th("Phone"),
                                                th("Email")
                                        )
                                ),
                                tbody(
                                        rowsView.include(model)
                                )
                        ),
                        button("Delete selected contacts")
                                .attr("hx-confirm", "Are you sure you want to delete these contacts?")
                                .attr("hx-delete", "/contacts")
                                .attr("hx-target", "body")
                ),
                p(
                        a("Add Contact").withHref("/contacts/new"),
                        text(" "),
                        span()
                                .attr("hx-trigger", "revealed")
                                .attr("hx-get", "/contacts/count")
                                .with(
                                        img()
                                                .withId("spinner")
                                                .withClass("htmx-indicator")
                                                .withSrc("https://raw.githubusercontent.com/n3r4zzurr0/svg-spinners/main/svg-css/90-ring.svg")
                                )
                )
        ).render(IndentedHtml.into(response.getWriter()));

    }

    @Override
    public String getContentType() {
        return MediaType.TEXT_HTML_VALUE;
    }

}
