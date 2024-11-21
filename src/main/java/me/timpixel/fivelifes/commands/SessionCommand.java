package me.timpixel.fivelifes.commands;

import me.timpixel.fivelifes.SessionManager;

public class SessionCommand extends RootCommand {

    private final SessionManager sessionManager;

    public SessionCommand(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public SubCommand[] getSubCommands() {
        SubCommand[] subCommands = new SubCommand[2];
        subCommands[0] = new SessionStartCommand(this);
        subCommands[1] = new SessionStopCommand(this);
        return subCommands;
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }
}

