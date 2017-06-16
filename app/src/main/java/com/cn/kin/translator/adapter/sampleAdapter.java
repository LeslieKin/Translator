package com.cn.kin.translator.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cn.kin.translator.R;
import com.cn.kin.translator.model.BingModel;

import java.util.ArrayList;

/**
 * Created by Kin on 17.6.8.
 */

public class SampleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public final Context context;
    public final LayoutInflater inflater;
    public ArrayList<BingModel.Sample> samples;

    //构造函数 把需要需要展示的数据源传进来
    public SampleAdapter (@NonNull Context context, ArrayList<BingModel.Sample> samples) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.samples = samples;
    }

    public class SampleViewHolder extends  RecyclerView.ViewHolder{

        TextView textView;
        //内部类 textView为RecyclerView的子项
        public SampleViewHolder(View itemView){
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textView);
        }
    }


    @Override
    //用于加载ViewHolder实例
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SampleViewHolder(inflater.inflate(R.layout.sample_item, parent, false));
    }

    @Override
    //对RecyclerView子项的数据进行赋值
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String s = samples.get(position).getEng() + "\n" + samples.get(position).getChn();
        ((SampleViewHolder)holder).textView.setText(s);
    }

    @Override
    public int getItemCount() {
        return samples.size();
    }
}
