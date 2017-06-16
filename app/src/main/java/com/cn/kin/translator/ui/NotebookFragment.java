package com.cn.kin.translator.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cn.kin.translator.R;

public class NotebookFragment extends Fragment {

    private RecyclerView recyclerView;

    public NotebookFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_notebook_fragment,container,false);

        initViews(view);

        return view;
    }

    private void initViews(View view){

        recyclerView=(RecyclerView)view.findViewById(R.id.recycler_view_notebook);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}
