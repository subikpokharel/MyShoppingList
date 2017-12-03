package com.example.subik.myshoppinglist.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.subik.myshoppinglist.R;
import com.example.subik.myshoppinglist.parsing.Product;

import java.util.ArrayList;

/**
 * Created by subik on 12/2/17.
 */

public class ListCouponByIDAdapter extends ArrayAdapter<Product> {
    Context mContext;
    private int resource;
    private ArrayList<Product> list;
    private LayoutInflater mlayoutInflater;

    public ListCouponByIDAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Product> objects) {
        super(context, resource, objects);
        this.list = objects;
        this.resource = resource;
        this.mContext = context;
        mlayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Product product = getItem(position);
        final ListCouponByIDAdapter.ViewHolder mViewHolder;

        if (convertView == null) {
            convertView= mlayoutInflater.inflate(resource, parent, false);
            mViewHolder = new ViewHolder(convertView);
            convertView.setTag(mViewHolder);

        } else {
            mViewHolder = (ListCouponByIDAdapter.ViewHolder) convertView.getTag();
        }

        mViewHolder.textId.setText(product.getId().toString());
        mViewHolder.textName.setText(product.getProduct());
        mViewHolder.textPrice.setText(product.getPrice());
        return convertView;
    }

    private static class ViewHolder{
        TextView textId, textName, textPrice;
        public ViewHolder(View view) {

            textId = view.findViewById(R.id.textProductId);
            textName = view.findViewById(R.id.textProductName);
            textPrice = view.findViewById(R.id.textProductPrice);

        }
    }
}
