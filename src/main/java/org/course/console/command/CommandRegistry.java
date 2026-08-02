package org.course.console.command;

import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class CommandRegistry {

    private final Map<ConsoleCommandType, ConsoleCommand> commandMap;

    public CommandRegistry(List<ConsoleCommand> commands) {
        this.commandMap = commands.stream()
                .collect(Collectors.toMap(
                        ConsoleCommand::getType,
                        Function.identity()
                ));
    }

    public ConsoleCommand getCommand(ConsoleCommandType type) {
        return this.commandMap.get(type);
    }

    public Collection<ConsoleCommand> getAllCommands() {
        return commandMap.values();
    }
}
