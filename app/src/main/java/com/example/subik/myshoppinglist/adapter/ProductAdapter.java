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
import java.util.HashMap;
import java.util.Map;

/**
 * Created by subik on 11/30/17.
 */

public class ProductAdapter extends ArrayAdapter<Product> {

    Context mcontext;
    ArrayList<Product> list;
    LayoutInflater mlayoutInflater;
    int resource;
    public final Map<Integer,String> checkmap = new HashMap<>();
    public final Map<Integer,String> editMap = new HashMap<>();


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
        final ViewHolder mViewHolder;

        if (convertView == null) {
            convertView= mlayoutInflater.inflate(resource, parent, false);
            mViewHolder = new ViewHolder(convertView);
            convertView.setTag(mViewHolder);

        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        mViewHolder.editPrice.setText(product.getPrice());
        mViewHolder.textName.setText(product.getProduct());
        //mViewHolder.checkBox.setId(product.getId());


        //Linkify.addLinks(mViewHolder.textName,Linkify.ALL);
        /*View itemView = super.getView(position,convertView,parent);
        TextView textView = itemView.findViewById(R.id.tvName);
        ListProductActivity.makeTextViewHyperlink(textView);*/


        mViewHolder.textName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Product product = list.get(position);
                int id = product.getId();
                Toast.makeText(getContext(),String.valueOf(id)/*String.valueOf(product)*/,Toast.LENGTH_LONG).show();
            }
        });

       mViewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((mViewHolder.checkBox).isChecked()){
                    //Toast.makeText(getContext(),"Hello" ,Toast.LENGTH_LONG).show();
                    //mViewHolder.editPrice.setEnabled(true);
                    checkmap.put(position,mViewHolder.textName.getText().toString());
                    //Toast.makeText(getContext(),mViewHolder.editPrice.getText().toString() ,Toast.LENGTH_LONG).show();
                    editMap.put(position,mViewHolder.editPrice.getText().toString());
                }else{
                    //mViewHolder.editPrice.setEnabled(false);
                    checkmap.remove(position);
                    editMap.remove(position);
                }
            }
        });

        if(checkmap.containsKey(position)){
            mViewHolder.checkBox.setChecked(true);
            mViewHolder.editPrice.setText(editMap.get(position));
            //Toast.makeText(getContext(),editMap.get(position) ,Toast.LENGTH_LONG).show();

            //mViewHolder.editPrice.setEnabled(true);
        }else{
            mViewHolder.checkBox.setChecked(false);
            //mViewHolder.editPrice.setEnabled(false);
        }
        return convertView;
    }


    private static class ViewHolder{
        public TextView textName;
        public EditText editPrice;//, textid;
        public CheckBox checkBox;
        //public TextView textPrice;

        public ViewHolder(View view){
            textName = view.findViewById(R.id.tvName);
            editPrice = view.findViewById(R.id.etPrice);
            checkBox = view.findViewById(R.id.chkBoxDashboad);
            //editPrice.setEnabled(false);

        }
    }



}
