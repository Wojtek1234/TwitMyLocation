package pl.wmaciejewski.twitmylocation.listsupport;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;


import java.util.List;

import pl.wmaciejewski.twitmylocation.R;
import pl.wmaciejewski.twitmylocation.bus.BusProvider;
import pl.wmaciejewski.twitmylocation.bus.ShowStatusEvent;
import pl.wmaciejewski.twitmylocation.sendtwitpackage.SetUpBundle;
import twitter4j.Status;

/**
 * Created by Wojtek on 2014-11-28.
 */
public class ListDialog extends DialogFragment implements AdapterView.OnItemClickListener {
    private ListView mylist;



    private List<Status> statusList;


    public void setStatusList(List<Status> statusList) {
        this.statusList = statusList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_list, null, false);
        mylist = (ListView) view.findViewById(R.id.listDialog);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ListAdapter listAdapter=new ListAdapter(getActivity(),R.layout.single_list_dialog,statusList);
        mylist.setAdapter(listAdapter);
        mylist.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle= SetUpBundle.bundleForTwitDialog(statusList.get(position));
        dismiss();
        BusProvider.getInstance().post(new ShowStatusEvent(bundle));
    }
}


