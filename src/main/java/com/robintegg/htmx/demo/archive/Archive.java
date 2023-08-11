package com.robintegg.htmx.demo.archive;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
public class Archive {

    private ArchiveStatus status = ArchiveStatus.Waiting;
    private double progress = 0.0;
    private ArchiveJob archiveJob;
    private Path archiveFile;

    public ArchiveStatus status() {
        return status;
    }

    public double progress() {
        return progress;
    }

    public void run() {
        if (status != ArchiveStatus.Waiting) {
            throw new RuntimeException("can't start a job as not waiting");
        }
        archiveJob = new ArchiveJob();
        new Thread(archiveJob).start();
    }

    public void reset() {
        if (archiveJob != null) {
            archiveJob.cancel();
        }
        archiveJob = null;
        status = ArchiveStatus.Waiting;
        progress = 0.0;
        archiveFile = null;
    }

    public Resource archiveFile() {
        return new ClassPathResource(archiveFile.toString());
    }

    private class ArchiveJob implements Runnable {

        private boolean interrupt = false;

        @Override
        public void run() {

            status = ArchiveStatus.Running;
            archiveFile = null;
            progress = 0.0;

            try {

                for (int i = 0; i < 5; i++) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    if (interrupt) throw new RuntimeException("user cancelled job");
                    progress += 0.2;
                }

                progress = 1.0;
                archiveFile = Path.of(new ClassPathResource("contacts.xml").getPath());

            } finally {
                status = ArchiveStatus.Complete;
            }

        }

        public void cancel() {
            interrupt = true;
        }

    }

}
