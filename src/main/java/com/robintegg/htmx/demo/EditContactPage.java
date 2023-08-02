package com.robintegg.htmx.demo;

import j2html.rendering.IndentedHtml;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.View;

import java.util.Map;
import java.util.Optional;

import static j2html.TagCreator.*;

@Component
public class EditContactPage implements View {

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {

        Contact contact = (Contact) model.get("contact");
        BindingResult bindingResult = (BindingResult) model.get("errors");

        Layout.withContent(
                model, request, form()
                        .withAction("/contacts/" + contact.id() + "/edit")
                        .withMethod("post")
                        .with(
                                fieldset(
                                        legend("Contact Values"),
                                        p(
                                                label("Email")
                                                        .withFor("email"),
                                                input()
                                                        .withName("email")
                                                        .withId("email")
                                                        .withType("email")
                                                        .withPlaceholder("Email")
                                                        .withValue(contact.email()),
                                                span().withClass("error")
                                                        .withText(
                                                                Optional.ofNullable(bindingResult.getFieldError("email")).map(FieldError::toString).orElse("")
                                                        )
                                        ),
                                        p(
                                                label("First Name")
                                                        .withFor("first_name"),
                                                input()
                                                        .withName("first")
                                                        .withId("first_name")
                                                        .withType("text")
                                                        .withPlaceholder("First Name")
                                                        .withValue(contact.first()),
                                                span().withClass("error")
                                                        .withText(
                                                                Optional.ofNullable(bindingResult.getFieldError("first")).map(FieldError::toString).orElse("")
                                                        )
                                        ),
                                        p(
                                                label("Last Name")
                                                        .withFor("last_name"),
                                                input()
                                                        .withName("last")
                                                        .withId("last_name")
                                                        .withType("text")
                                                        .withPlaceholder("Last Name")
                                                        .withValue(contact.last()),
                                                span().withClass("error")
                                                        .withText(
                                                                Optional.ofNullable(bindingResult.getFieldError("last")).map(FieldError::toString).orElse("")
                                                        )
                                        ),
                                        p(
                                                label("Phone")
                                                        .withFor("phone"),
                                                input()
                                                        .withName("phone")
                                                        .withId("phone")
                                                        .withType("text")
                                                        .withPlaceholder("Phone")
                                                        .withValue(contact.phone()),
                                                span().withClass("error")
                                                        .withText(
                                                                Optional.ofNullable(bindingResult.getFieldError("phone")).map(FieldError::toString).orElse("")
                                                        )
                                        ),
                                        button("Save")
                                )
                        ),
                form()
                        .withAction("/contacts/" + contact.id() + "/delete")
                        .withMethod("post")
                        .with(
                                button("Delete Contact")
                        ),
                p(
                        a("Back").withHref("/contacts")
                )
        ).render(IndentedHtml.into(response.getWriter()));

    }

    @Override
    public String getContentType() {
        return "text/html";
    }

}
