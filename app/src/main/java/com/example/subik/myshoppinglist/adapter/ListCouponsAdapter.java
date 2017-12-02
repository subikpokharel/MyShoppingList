package com.example.subik.myshoppinglist.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.subik.myshoppinglist.R;

import java.util.ArrayList;

/**
 * Created by subik on 12/2/17.
 */

public class ListCouponsAdapter extends ArrayAdapter<Integer> {
    private Context mContex;
    private int resource;
    private ArrayList<Integer> list;
    private LayoutInflater layoutInflater;
    DataTransferInterface dataTransferInterface;
    public ListCouponsAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Integer> objects) {
        super(context, resource, objects);
        this.mContex = context;
        this.resource = resource;
        this.list = objects;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //this.onClickListener = (View.OnClickListener) context;
        this.dataTransferInterface = (DataTransferInterface) context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Integer id = getItem(position);
        final ListCouponsAdapter.ViewHolder mViewHolder;


        if (convertView == null) {
            convertView= layoutInflater.inflate(resource, parent, false);
            mViewHolder = new ListCouponsAdapter.ViewHolder(convertView);
            convertView.setTag(mViewHolder);

        } else {
            mViewHolder = (ListCouponsAdapter.ViewHolder) convertView.getTag();
        }

        mViewHolder.textId.setText(id.toString());

        mViewHolder.imageDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = list.get(position);
                dataTransferInterface.sendID(pos);
            }
        });

        

        return convertView;
    }

    public class ViewHolder {
        TextView textId;
        ImageView imageDelete;
        public ViewHolder(View view) {
            textId = view.findViewById(R.id.couponId);
            imageDelete = view.findViewById(R.id.couponDelete);
        }
    }


    public interface DataTransferInterface{
       void sendID(int id);
    }
}
