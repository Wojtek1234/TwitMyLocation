package pl.wmaciejewski.twitmylocation.bus;

/**
 * Created by w.maciejewski on 2014-11-27.
 */
public class ReplayTweetEvent {

    private String username;
    private long replayId;

    public ReplayTweetEvent(String username, long replayId) {
        this.username = username;
        this.replayId = replayId;
    }

    public long getReplayId() {
        return replayId;
    }

    public String getUsername() {
        return username;
    }
}
