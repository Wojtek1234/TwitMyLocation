package pl.wmaciejewski.twitmylocation;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Status;

/**
 * Created by Wojtek on 2014-11-28.
 */
public class ListOfStatusHolder {
    public List<Status> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<Status> statusList) {
        this.statusList = statusList;
    }
    public void clearStatusList(){
         statusList=new ArrayList<Status>();
    }
    private List<Status> statusList=new ArrayList<Status>();
    private static ListOfStatusHolder ourInstance = new ListOfStatusHolder();

    public static ListOfStatusHolder getInstance() {
        return ourInstance;
    }

    private ListOfStatusHolder() {
    }
}
