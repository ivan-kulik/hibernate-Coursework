package org.course.console;

import org.springframework.stereotype.Component;

@Component
public class ApplicationLifecycle {

    private volatile boolean isRunning = true;

    public boolean isRunning() {
        return this.isRunning;
    }

    public void shutdown() {
        this.isRunning = false;
    }
}
