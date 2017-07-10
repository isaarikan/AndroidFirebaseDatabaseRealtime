package dev.edmt.firebasedemo;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by reale on 31/10/2016.
 */

public class ListViewAdapter extends BaseAdapter {

    Activity activity;
    List<Note> lstNotes;
    LayoutInflater inflater;


    public ListViewAdapter(Activity activity, List<Note> lstNotes) {
        this.activity = activity;
        this.lstNotes = lstNotes;
    }

    @Override
    public int getCount() {
        return lstNotes.size();
    }

    @Override
    public Object getItem(int i) {
        return lstNotes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        inflater = (LayoutInflater)activity.getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.listview_item,null);

        TextView txtTitle = (TextView)itemView.findViewById(R.id.list_title);
        TextView txtContet = (TextView)itemView.findViewById(R.id.list_content);

        txtTitle.setText(lstNotes.get(i).getTitle());
        txtContet.setText(lstNotes.get(i).getContent());

        return itemView;
    }
}
