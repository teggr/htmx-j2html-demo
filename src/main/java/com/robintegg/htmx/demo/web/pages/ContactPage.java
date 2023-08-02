package com.robintegg.htmx.demo.web.pages;

import com.robintegg.htmx.demo.contacts.Contact;
import j2html.rendering.IndentedHtml;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.View;

import java.util.Map;

import static j2html.TagCreator.*;

@Component
public class ContactPage implements View {

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {

        Contact contact = (Contact) model.get("contact");

        Layout.withContent(
                model,
                request,
                h1(contact.first() + " " + contact.last()),
                div(
                        div("Phone: " + contact.phone()),
                        div("Email: " + contact.email())
                ),
                p(
                        a("Edit").withHref("/contacts/" + contact.id()),
                        a("Back").withHref("/contacts")
                )
        ).render(IndentedHtml.into(response.getWriter()));

    }

    @Override
    public String getContentType() {
        return MediaType.TEXT_HTML_VALUE;
    }

}
