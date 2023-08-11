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
                style().with(rawHtml("""
                        tr.htmx-swapping {
                            opacity: 0;
                            transition: opacity 1s ease-out;
                        }
                        .progress {
                            height: 20px;
                            margin-bottom: 20px;
                            overflow: hidden;
                            background-color: #f5f5f5;
                            border-radius: 4px;
                            box-shadow: inset 0 1px 2px rgba(0,0,0,.1);
                        }
                        .progress-bar {
                            float: left;
                            width: 0%;
                            height: 100%;
                            font-size: 12px;
                            line-height: 20px;
                            color: #fff;
                            text-align: center;
                            background-color: #337ab7;
                            box-shadow: inset 0 -px 0 rgba(0,0,0,.15);
                            transition: width .6s ease;
                        }
                        """)),
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
