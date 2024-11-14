package me.timpixel.fivelifes.commands;

public class SessionCommand extends RootCommand {

    private final int sessionLengthMinutes;

    public SessionCommand(int sessionLengthMinutes) {
        this.sessionLengthMinutes = sessionLengthMinutes;
    }

    @Override
    public SubCommand[] getSubCommands() {
        SubCommand[] subCommands = new SubCommand[2];
        subCommands[0] = new SessionStartCommand(this);
        subCommands[1] = new SessionStopCommand();
        return subCommands;
    }

    public int getSessionLengthMinutes() {
        return sessionLengthMinutes;
    }
}

