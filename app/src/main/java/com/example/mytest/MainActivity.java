package com.example.mytest;


import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private static TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout linearLayout=new LinearLayout(this);

        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        textView =new TextView(this);
        textView.setTextSize(20);
        textView.setTextColor(Color.rgb(0x8E,0x74,0xCD));
        textView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        TextView textView2 =new TextView(this);
        textView2.setTextSize(20);
        textView2.setTextColor(Color.rgb(0x8E,0x74,0xCD));
        textView2.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));


        Button button=new Button(this);
        button.setBackgroundColor(Color.GRAY);
        button.setText("连接设备");
        button.setTextSize(20);
        button.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,MyTestActivity.class);
                startActivityForResult(intent,100);
            }
        });


        linearLayout.addView(button);
        linearLayout.addView(textView);
        linearLayout.addView(textView2);

        setContentView(linearLayout);
    }



    public static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    byte[] readBuf = (byte[]) msg.obj;
                    String readMessage = new String(readBuf, 0, msg.arg1);
                    textView.setText("readMessage"+"\n"+readMessage);
                    System.out.println(readMessage);
                    break;
            }
        }
    };


}

