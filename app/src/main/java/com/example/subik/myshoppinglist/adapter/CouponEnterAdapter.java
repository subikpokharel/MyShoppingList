package com.example.subik.myshoppinglist.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.subik.myshoppinglist.R;
import com.example.subik.myshoppinglist.parsing.Product;

import java.util.ArrayList;

/**
 * Created by subik on 12/1/17.
 */

public class CouponEnterAdapter extends ArrayAdapter<Product> {

    Context mcontext;
    ArrayList<Product> list;
    LayoutInflater mlayoutInflater;
    int resource;


    public CouponEnterAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Product> objects) {
        super(context, resource, objects);
        this.list = objects;
        this.resource = resource;
        this.mcontext = context;
        mlayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }



    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Product product = getItem(position);
        final CouponEnterAdapter.ViewHolder mViewHolder;

        if (convertView == null) {
            convertView= mlayoutInflater.inflate(resource, parent, false);
            mViewHolder = new CouponEnterAdapter.ViewHolder(convertView);
            convertView.setTag(mViewHolder);

        } else {
            mViewHolder = (CouponEnterAdapter.ViewHolder) convertView.getTag();
        }
        mViewHolder.productName.setText(product.getProduct());
        //mViewHolder.checkBox.setId(product.getId());
        //Toast.makeText(getContext(), String.valueOf(mViewHolder.checkBox.getId()),Toast.LENGTH_LONG).show();

        mViewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((mViewHolder.checkBox).isChecked()){
                    //Toast.makeText(getContext(),"Hello" ,Toast.LENGTH_LONG).show();
                    mViewHolder.productName.setEnabled(true);
                }else{
                    mViewHolder.productName.setEnabled(false);
                }
            }
        });
        return convertView;
    }


    private static class ViewHolder{
        public TextView productName;
        public CheckBox checkBox;

        public ViewHolder(View view){
            productName = view.findViewById(R.id.productName);
            checkBox = view.findViewById(R.id.chkCouponProduct);
            productName.setEnabled(false);

        }
    }

}
