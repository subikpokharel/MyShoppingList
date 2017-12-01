package com.example.subik.myshoppinglist.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.URLSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.subik.myshoppinglist.Dashboard;
import com.example.subik.myshoppinglist.R;
import com.example.subik.myshoppinglist.parsing.Product;

import java.util.ArrayList;

/**
 * Created by subik on 11/30/17.
 */

public class ProductAdapter extends ArrayAdapter<Product> {

    Context mcontext;
    ArrayList<Product> list;
    LayoutInflater mlayoutInflater;
    int resource;


    public ProductAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Product> objects) {
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
        ViewHolder mViewHolder;

        if (convertView == null) {
            convertView= mlayoutInflater.inflate(resource, parent, false);
            mViewHolder = new ViewHolder(convertView);
            convertView.setTag(mViewHolder);

        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        mViewHolder.editPrice.setText(product.getPrice());
        mViewHolder.textName.setText(product.getProduct());
        mViewHolder.count.setText(String.valueOf(position+1));
        //mViewHolder.checkBox.setId(product.getId());
        //Toast.makeText(getContext(), String.valueOf(mViewHolder.checkBox.getId()),Toast.LENGTH_LONG).show();
        mViewHolder.textName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Product product = list.get(position);
                int id = product.getId();
                Toast.makeText(getContext(),String.valueOf(id)/*String.valueOf(product)*/,Toast.LENGTH_LONG).show();
            }
        });

       /* mViewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Product product = list.get(position);
                int id = product.getId();
                Toast.makeText(getContext(),String.valueOf(id)+" " +String.valueOf(product.getPrice()),Toast.LENGTH_LONG).show();
            }
        });*/
        /*SpannableStringBuilder ssb = new SpannableStringBuilder( );
        ssb.append( mViewHolder.textName.getText( ) );
        ssb.setSpan( new URLSpan("#"), 0, ssb.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE );
        mViewHolder.textName.setText( ssb, TextView.BufferType.SPANNABLE );

        mViewHolder.textName.setText(product.getProduct());*/
        return convertView;
    }


    private static class ViewHolder{
        public TextView textName, count;
        public EditText editPrice;//, textid;
        //public CheckBox checkBox;
        //public TextView textPrice;

        public ViewHolder(View view){
            textName = view.findViewById(R.id.tvName);
            editPrice = view.findViewById(R.id.etPrice);
            count = view.findViewById(R.id.slNo);
            //checkBox = view.findViewById(R.id.checkbox);
            //mcontext.makeTextViewHyperlink
            //mcontext.getContext()
            //Dashboard.makeTextViewHyperlink(textName);



        }
    }



}
