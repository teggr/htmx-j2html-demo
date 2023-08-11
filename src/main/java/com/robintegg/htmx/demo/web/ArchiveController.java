package com.robintegg.htmx.demo.web;

import com.robintegg.htmx.demo.archive.Archive;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.nio.charset.StandardCharsets;

@Controller
public class ArchiveController {
    private Archive archive;
    private ApplicationContext applicationContext;

    public ArchiveController(Archive archive, ApplicationContext applicationContext) {
        this.archive = archive;
        this.applicationContext = applicationContext;
    }

    @PostMapping("/contacts/archive")
    public String startArchive(Model model) {
        archive.run();
        model.addAttribute("archive", archive);
        return "archiveView";
    }

    @GetMapping("/contacts/archive")
    public String archive(Model model) {
        model.addAttribute("archive", archive);
        return "archiveView";
    }

    @GetMapping(value = "/contacts/archive/file", produces = "application/xml")
    @ResponseBody
    public ResponseEntity<Resource> archiveFile() {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentDisposition(ContentDisposition
                .attachment()
                .filename("contacts.xml", StandardCharsets.UTF_8)
                .build());

        return ResponseEntity
                .ok()
                .headers(httpHeaders)
                .body(archive.archiveFile());

    }

    @DeleteMapping(value = "/contacts/archive")
    public String deleteArchive(Model model) {
        archive.reset();
        model.addAttribute("archive", archive);
        return "archiveView";
    }

}
