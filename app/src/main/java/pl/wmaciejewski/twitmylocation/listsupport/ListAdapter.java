package pl.wmaciejewski.twitmylocation.listsupport;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import pl.wmaciejewski.twitmylocation.R;
import twitter4j.Status;
import twitter4j.StatusUpdate;

/**
 * Created by Wojtek on 2014-11-28.
 */
public class ListAdapter extends ArrayAdapter<Status> {

    private final List<Status> list;
    private final Activity context;

    public ListAdapter(Activity context, int resource,  List<Status> objects) {
        super(context, resource, objects);
        this.list=objects;
        this.context=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=convertView;
        ViewHolder viewHolder;
        if(convertView==null){
            LayoutInflater layoutInflater=context.getLayoutInflater();
            view= layoutInflater.inflate(R.layout.single_list_dialog,null);
            viewHolder=new ViewHolder();
            viewHolder.textView=(TextView)view.findViewById(R.id.dialogTextView);
            viewHolder.imageView=(ImageView)view.findViewById(R.id.dialogImageView);
            view.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }
        TextView textView=(TextView)viewHolder.textView;
        textView.setText(list.get(position).getUser().getName()+" : "+list.get(position).getText());
        ImageView imageView=(ImageView)viewHolder.imageView;
        Picasso.with(context)
                .load(list.get(position).getUser().getMiniProfileImageURL())
                .into(imageView);

        return view;
    }



    class ViewHolder{
        TextView textView;
        ImageView imageView;
    }
}
