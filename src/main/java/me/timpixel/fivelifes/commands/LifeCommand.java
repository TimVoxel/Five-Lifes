package me.timpixel.fivelifes.commands;

import me.timpixel.fivelifes.LifeBase;

public class LifeCommand extends RootCommand {

    private final LifeBase lifeBase;

    public LifeCommand(LifeBase lifeBase) {
        this.lifeBase = lifeBase;
    }

    @Override
    public SubCommand[] getSubCommands() {
        SubCommand[] subCommands = new SubCommand[5];
        subCommands[0] = new SetLifeCommand(this);
        subCommands[1] = new AddLifeCommand(this);
        subCommands[2] = new RemoveLifeCommand(this);
        subCommands[3] = new SaveLifesCommand(this);
        subCommands[4] = new ReloadLifesCommand(this);
        return subCommands;
    }

    public LifeBase getLifeBase() {
        return lifeBase;
    }
}
