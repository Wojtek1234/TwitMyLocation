package pl.wmaciejewski.twitmylocation.bus;

import java.util.List;

import twitter4j.Status;

/**
 * Created by w.maciejewski on 2014-11-18.
 */
public class ListOfStatusEvent {


    private List<Status> statusList;

    public ListOfStatusEvent(List<Status> statusList){
        this.statusList=statusList;
    }

    public List<Status> getStatusList() {
        return statusList;
    }
}

