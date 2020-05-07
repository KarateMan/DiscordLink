package com.karateman.discordlink.modules.data;

public interface Module {

    boolean setup();
    boolean isSetup();
    boolean isEnabled();
    void runStartup();
    void runShutdown();

}
