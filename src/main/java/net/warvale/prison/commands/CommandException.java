package net.warvale.prison.commands;

public class CommandException extends Exception {

    /**
     * Create a new CommandException
     *
     * @param message The error message.
     */
    public CommandException(String message) {
        super(message);
    }
}