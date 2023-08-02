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
public class NewContactPage implements View {

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {

        Layout.withContent(
                model, request, form()
                        .withAction("/contacts/new")
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
                                                        .withValue(((Contact) model.get("contact")).email()),
                                                span().withClass("error")
                                                        .withText(
                                                                Optional.ofNullable(((BindingResult) model.get("errors")).getFieldError("email")).map(FieldError::toString).orElse("")
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
                                                        .withValue(((Contact) model.get("contact")).first()),
                                                span().withClass("error")
                                                        .withText(
                                                                Optional.ofNullable(((BindingResult) model.get("errors")).getFieldError("first")).map(FieldError::toString).orElse("")
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
                                                        .withValue(((Contact) model.get("contact")).last()),
                                                span().withClass("error")
                                                        .withText(
                                                                Optional.ofNullable(((BindingResult) model.get("errors")).getFieldError("last")).map(FieldError::toString).orElse("")
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
                                                        .withValue(((Contact) model.get("contact")).phone()),
                                                span().withClass("error")
                                                        .withText(
                                                                Optional.ofNullable(((BindingResult) model.get("errors")).getFieldError("phone")).map(FieldError::toString).orElse("")
                                                        )
                                        ),
                                        button("Save")
                                )
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
