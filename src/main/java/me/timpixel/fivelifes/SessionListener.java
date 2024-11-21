package me.timpixel.fivelifes;

public interface SessionListener {

    void onSessionStarted();
    void onSessionEnded();
    void onSessionTimeRanOut();
    void onSessionTick(int tick);
}
