package pl.wmaciejewski.twitmylocation.listsupport;

import android.support.v4.app.FragmentManager;
import android.view.View;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.wmaciejewski.twitmylocation.R;
import twitter4j.Status;

/**
 * Created by Wojtek on 2014-11-28.
 */
public class SupportFourButtons {
    private View view;
    private List<Status> statusList;
    private FragmentManager supportedFragmentManager;



    public SupportFourButtons(View view,FragmentManager supportedFragmentManager) {
        this.view = view;
        ButterKnife.inject(this,view);
        this.supportedFragmentManager = supportedFragmentManager;
    }

    public void showPanel() {
        if (this.view.getVisibility() == View.GONE) this.view.setVisibility(View.VISIBLE);
        else this.view.setVisibility(View.GONE);
    }

    public List<Status> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<Status> statusList) {
        this.statusList = statusList;
    }

    @OnClick(R.id.showTwitList)
    public void onShowList(){
        ListDialog listDialog=new ListDialog();
        listDialog.setStatusList(statusList);
        listDialog.show(supportedFragmentManager,null);
    }


    @OnClick(R.id.showMyTwits)
    public void onShowMyList(){

    }

    @OnClick(R.id.showUserTwits)
    public void onShowCustomList(){

    }

    @OnClick(R.id.showInDistance)
    public void onShowInDistance(){

    }

}
