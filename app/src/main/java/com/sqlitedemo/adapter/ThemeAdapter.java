package com.sqlitedemo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sqlitedemo.DBHelper.DataBase;
import com.sqlitedemo.Model.ThemelModel;
import com.sqlitedemo.R;
import java.util.ArrayList;

public class ThemeAdapter extends RecyclerView.Adapter<ThemeAdapter.ItemRowHolder> {
    private Context context;
    private ArrayList<ThemelModel> themelModel;
    private DataBase dataBase;

    public class ItemRowHolder extends RecyclerView.ViewHolder {
        TextView txt_id, txt_themename;
        ImageView iv_themeimg;

        public ItemRowHolder(View itemView) {
            super(itemView);

            this.txt_id = (TextView) itemView.findViewById(R.id.txt_id);
            this.txt_themename = (TextView) itemView.findViewById(R.id.txt_theme_name);
            this.iv_themeimg = (ImageView) itemView.findViewById(R.id.iv_themeimg);
        }
    }

    public ThemeAdapter(Context context, ArrayList<ThemelModel> themelModel) {
        this.themelModel = themelModel;
        this.context = context;
        this.dataBase = new DataBase(context);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ThemeAdapter.ItemRowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ThemeAdapter.ItemRowHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_layout, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemRowHolder itemHolder, int position) {

        final ThemelModel themelModel = (ThemelModel) this.themelModel.get(position);

        itemHolder.txt_id.setText(themelModel.getThemeid());
        itemHolder.txt_themename.setText(themelModel.getThemeName());

        try {
            itemHolder.iv_themeimg.setImageBitmap(ConvertToBitmap(themelModel.getPic()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*if (dataBase.Isidexists(themelModel)) {
            try {
                itemHolder.iv_themeimg.setImageBitmap(ConvertToBitmap(themelModel.getPic()));
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            Log.e("TAG", "Adp Exists" + themelModel.getThemeName());
            Toast.makeText(context, "Already Added", Toast.LENGTH_SHORT).show();
        }*/
    }

    @Override
    public int getItemCount() {
        return this.themelModel != null ? this.themelModel.size() : 0;
    }

    //get bitmap image from byte array

    private Bitmap ConvertToBitmap(byte[] b) {

        return BitmapFactory.decodeByteArray(b, 0, b.length);
    }
}
