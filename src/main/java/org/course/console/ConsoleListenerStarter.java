package org.course.console;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

@Component
public class ConsoleListenerStarter {

    private final ConsoleListener consoleListener;
    private Thread listenerThread;

    public ConsoleListenerStarter(
            ConsoleListener consoleListener
    ) {
        this.consoleListener = consoleListener;
    }

    @PostConstruct
    public void postConstruct() {
        this.listenerThread = new Thread(this.consoleListener::run);
    }

    @PreDestroy
    public void preDestroy() {
        this.listenerThread.interrupt();
    }
}
