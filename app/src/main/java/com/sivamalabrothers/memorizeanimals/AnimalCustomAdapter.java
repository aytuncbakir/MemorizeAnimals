package com.sivamalabrothers.memorizeanimals;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;

public class AnimalCustomAdapter extends RecyclerView.Adapter<AnimalCustomAdapter.MyViewHolder> {

    ArrayList<AnimalMenuItem> mDataList;
    LayoutInflater inflater;
    int pos;
    Context context;

    public AnimalCustomAdapter(Context context, ArrayList<AnimalMenuItem> data){
        this.context = context;
        //inflater = (Layoutiflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        inflater = LayoutInflater.from(context);
        this.mDataList = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.giris_list_item,parent,false);
        MyViewHolder holder = new MyViewHolder(v);
        return holder;
    }



    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        AnimalMenuItem tiklananItem = mDataList.get(position);
        holder.setData(tiklananItem,position);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public void tiklananMenuCagir(int pos){

        if(context.getApplicationContext().equals(VeryEasyGame.class)){
            VeryEasyGame girisSayfasi;
            girisSayfasi = (VeryEasyGame) context;
            girisSayfasi.tiklananMenuItem(pos);
        }else if(context.getApplicationContext().equals(EasyGame.class)){
            EasyGame girisSayfasi;
            girisSayfasi = (EasyGame) context;
            girisSayfasi.tiklananMenuItem(pos);
        }/*else if(context.getApplicationContext().equals(NormalGame.class)){
            NormalGame girisSayfasi;
            girisSayfasi = (NormalGame) context;
            girisSayfasi.tiklananMenuItem(pos);
        }else if(context.getApplicationContext().equals(HardGame.class)){
            HardGame girisSayfasi;
            girisSayfasi = (HardGame) context;
            girisSayfasi.tiklananMenuItem(pos);
        }
        */
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView mText;
        ImageView mImg;
        int tiklananPos;
        LinearLayout list_layout;

        public MyViewHolder(View itemView) {
            super(itemView);

            list_layout = itemView.findViewById(R.id.list_layout);
            mImg = itemView.findViewById(R.id.list_img);
            mText = itemView.findViewById(R.id.list_text);

            list_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tiklananMenuCagir(tiklananPos);
                }
            });
            mText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tiklananMenuCagir(tiklananPos);
                }
            });

            mImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tiklananMenuCagir(tiklananPos);
                }
            });

        }

        public void setData(AnimalMenuItem tiklananItem, int position) {
            this.mImg.setImageResource(tiklananItem.getImgId());
            Typeface font = Typeface.createFromAsset(context.getAssets(),"fonts/Candy_Shop_Black.ttf");
            mText.setTextSize(14);
            mText.setTypeface(font, Typeface.BOLD);
            this.mText.setText(tiklananItem.getAdi());
            this.tiklananPos = position;
        }

    }
}
