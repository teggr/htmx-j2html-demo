package com.robintegg.htmx.demo.web.pages;

import j2html.tags.DomContent;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

import static j2html.TagCreator.*;

public class Layout {

  public static DomContent withContent(Map<String, ?> model, HttpServletRequest request, DomContent... content) {
    return new Layout(model, request, content).getLayout();
  }

  private final Map<String, ?> model;
  private final HttpServletRequest request;
  private final DomContent[] content;

  public Layout(Map<String, ?> model, HttpServletRequest request, DomContent[] content) {
    this.model = model;
    this.request = request;
    this.content = content;
  }

  private DomContent getLayout() {

    String msg = (String) model.get("msg");

    return join(document(), html(
        head()
            .withTitle("Contacts App")
            .with(
                link().withRel("stylesheet").withHref("https://unpkg.com/missing.css@1.0.9/dist/missing.min.css"),
                script().withSrc("https://unpkg.com/htmx.org")
            ),
        body().attr("hx-boost", true).with(
            h1("Contacts.app"),
            h2("A Demo Contacts Application"),
            hr(),
            iff(msg != null,
                div((String) msg).withClass("box")
            ),
            div().withClass("container").with(
                content
            )
        )
    ));
  }

}
