package com.example.contacts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.sql.Array;
import java.util.List;

/*自定义适配器*/
public class ListAdapt extends ArrayAdapter<Person> {
    private int recourceid;
    TextView phone;
    TextView name;

    public ListAdapt(@NonNull Context context, int resource, List<Person> objects) {
        super(context, resource, objects);
        recourceid = resource;
    }
/*重写*/
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Person pe = getItem(position);//获取当前listview的位置
        View view = LayoutInflater.from(getContext()).inflate(recourceid, parent, false);
        phone = (TextView) view.findViewById(R.id.textView_phone);//获取实例
        name = (TextView) view.findViewById(R.id.textView_name);
        name.setText(pe.getName());//设置输出文本
        phone.setText(pe.getTel());
        return view;
    }
}
