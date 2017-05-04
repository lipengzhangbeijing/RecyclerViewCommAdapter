package com.example.recyclerviewcommadapter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recyclerviewcommadapter.adapter.RecyclerViewCommAdapter;
import com.example.recyclerviewcommadapter.decoration.DividerDecoration;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerViewCommAdapter.RecyclerViewItemClickListener {
    private RecyclerView recyclerView;
    private List<String> datas = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       for (int i = 0; i <1000;i++){
           datas.add("当前条目=="+i);

        }
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerDecoration(this));
        RecyclerViewCommAdapter<String> adapter = new RecyclerViewCommAdapter<String>(datas,this) {
            @Override
            public int getMultiItemId(int viewType) {
                switch (viewType){
                    case 0:
                        return R.layout.item_one;
                    default:
                        return R.layout.item_tow;
                }
            }

            @Override
            public int getMultiItemViewType(int position) {
                if (position % 2 == 0){
                    return 0;
                }
                return 1;
            }

            @Override
            public void convert(RecyclerViewCommViewHolder holder, String model, int position, int viewType) {
                switch (viewType){
                    case 0:
                        holder.<TextView>getView(R.id.title1).setText("title1"+model);
                        holder.<TextView>getView(R.id.title2).setText("title2"+model);
                        break;
                    case 1:
                        holder.<TextView>getView(R.id.title).setText(model);
                        break;
                }

            }
        };
        adapter.setRecyclerViewItemClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClickListener(View view, int position) {
        Toast.makeText(this,"您点击了"+ position,Toast.LENGTH_LONG).show();
    }
}
