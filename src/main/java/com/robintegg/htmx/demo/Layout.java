package com.robintegg.htmx.demo;

import j2html.rendering.IndentedHtml;
import j2html.tags.DomContent;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.View;

import java.util.List;
import java.util.Map;

import static j2html.TagCreator.*;

public class Layout {

  public static DomContent withContent(DomContent... content) {
    return new Layout(content).getLayout();
  }

  private final DomContent[] content;

  public Layout(DomContent[] content) {
    this.content = content;
  }

  private DomContent getLayout() {
    return join(document(), html(
        head()
            .withTitle("Contacts App")
            .with(
                link().withRel("stylesheet").withHref("https://unpkg.com/missing.css@1.0.9/dist/missing.min.css")
            ),
        body(
            h1("Contacts.app"),
            h2("A Demo Contacts Application"),
            hr(),
            div().withClass("container").with(
                content
            )
        )
    ));
  }

}
