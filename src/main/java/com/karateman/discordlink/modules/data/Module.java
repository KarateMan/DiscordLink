package com.karateman.discordlink.modules.data;

public interface Module {

    void setup();
    boolean isSetup();
    boolean isEnabled();
    void runStartup();
    void runShutdown();

}
