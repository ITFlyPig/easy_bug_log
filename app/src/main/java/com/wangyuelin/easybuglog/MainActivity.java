package com.wangyuelin.easybuglog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.wangyuelin.easybug.log.AopLog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.whole).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < 1000; i++) {
                    AopLog.methodEnter("com.wangyuelin.easybuglog.MainActivity", "onClick" + i, new Object[]{savedInstanceState},
                            new IllegalStateException("状态不对"), null);
                }
            }
        });
    }
}
