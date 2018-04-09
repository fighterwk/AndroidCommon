package com.kyle.carch.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @Description描述:
 * @Author作者: Kyle
 * @Date日期: 2018/4/9
 */
public class DetailsActivity extends AppCompatActivity {

    public static final String EXTRA_EXCEPTION = "EXTRA_EXCEPTION";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView textView = new TextView(this);
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        setContentView(textView);


        String info = getIntent().getStringExtra(EXTRA_EXCEPTION);

        textView.setText(info);
    }
}
