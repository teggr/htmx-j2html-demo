package com.robintegg.htmx.demo.web.pages;

import com.robintegg.htmx.demo.archive.Archive;
import com.robintegg.htmx.demo.archive.ArchiveStatus;
import j2html.rendering.IndentedHtml;
import j2html.tags.DomContent;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.View;

import java.util.Map;

import static j2html.TagCreator.*;

@Component("archiveView")
public class ArchiveView implements View {

    @Override
    public String getContentType() {
        return MediaType.TEXT_HTML_VALUE;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {

        include(model).render(IndentedHtml.into(response.getWriter()));

    }

    public DomContent include(Map<String, ?> model) {

        Archive archive = (Archive) model.get("archive");
        double progress = archive.progress() * 100;

        return div()
                .withId("archive-ui")
                .attr("hx-target", "this")
                .attr("hx-swap", "outerHTML")
                .with(
                        iff(archive.status() == ArchiveStatus.Waiting,
                                button("Download Contact Archive")
                                        .attr("hx-post", "/contacts/archive")
                        ),
                        iff(archive.status() == ArchiveStatus.Running,
                                each(
                                        text("Creating archive..."),
                                        div().withClass("progress")
                                                .attr("hx-get", "/contacts/archive")
                                                .attr("hx-trigger", "load delay:500ms")
                                                .with(
                                                        div().withId("archive-progress").withClass("progress-bar")
                                                                .attr("role", "progressbar")
                                                                .attr("aria-value-now", "" + progress)
                                                                .withStyle("width:" + progress + "%")
                                                )
                                )
                        ),
                        iff(archive.status() == ArchiveStatus.Complete,
                                each(
                                        a()
                                        .attr("hx-boost", false)
                                        .withHref("/contacts/archive/file")
                                        .with(
                                                join(text("Click here to download. "), rawHtml("&downarrow;"))
                                        ),
                                        button("Clear Download")
                                                .attr("hx-delete", "/contacts/archive")
                                )
                        )
                );

    }

}
