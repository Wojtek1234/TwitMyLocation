package pl.wmaciejewski.twitmylocation.bus;

/**
 * Created by w.maciejewski on 2014-11-17.
 */
public class MessageLogin {
    public boolean isLogginStatus() {
        return logginStatus;
    }

    public void setLogginStatus(boolean logginStatus) {
        this.logginStatus = logginStatus;
    }

    private boolean logginStatus=false;
    public MessageLogin(boolean logginStatus){
        this.logginStatus=logginStatus;
    }
}
