package com.example.caculator;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.caculate.R;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements OnClickListener {
    private EditText editText; //实例输入框,下同
    private Button button_0;
    private Button button_1;
    private Button button_2;
    private Button button_3;
    private Button button_4;
    private Button button_5;
    private Button button_6;
    private Button button_7;
    private Button button_8;
    private Button button_9;
    private Button button_add;//加
    private Button button_sub;//减
    private Button button_mul;//乘
    private Button button_div;//除
    private Button button_ac;//清空
    private Button button_del;//删除
    private Button button_sin;
    private Button button_cos;
    private Button button_tan;
    private Button button_eql;//等于
    private Button button_lbracket;//左括号
    private Button button_rbracket;//右括号
    private Button button_point;//小数点

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button_0 = (Button) findViewById(R.id.button_0);
        button_1 = (Button) findViewById(R.id.button_1);
        button_2 = (Button) findViewById(R.id.button_2);
        button_3 = (Button) findViewById(R.id.button_3);
        button_4 = (Button) findViewById(R.id.button_4);
        button_5 = (Button) findViewById(R.id.button_5);
        button_6 = (Button) findViewById(R.id.button_6);
        button_7 = (Button) findViewById(R.id.button_7);
        button_8 = (Button) findViewById(R.id.button_8);
        button_9 = (Button) findViewById(R.id.button_9);
        button_add = (Button) findViewById(R.id.button_add);
        button_sub = (Button) findViewById(R.id.button_sub);
        button_mul = (Button) findViewById(R.id.button_mul);
        button_div = (Button) findViewById(R.id.button_div);
        button_ac = (Button) findViewById(R.id.button_ac);
        button_del = (Button) findViewById(R.id.button_del);
        button_sin = (Button) findViewById(R.id.button_sin);
        button_cos = (Button) findViewById(R.id.button_cos);
        button_tan = (Button) findViewById(R.id.button_tan);
        button_eql = (Button) findViewById(R.id.button_eql);
        button_point = (Button) findViewById(R.id.button_point);
        button_lbracket = (Button) findViewById(R.id.button_lbracket);
        button_rbracket = (Button) findViewById(R.id.button_rbracket);
        editText = (EditText) findViewById(R.id.edit_text);
        button_0.setOnClickListener(this);
        button_1.setOnClickListener(this);
        button_2.setOnClickListener(this);
        button_3.setOnClickListener(this);
        button_4.setOnClickListener(this);
        button_5.setOnClickListener(this);
        button_6.setOnClickListener(this);
        button_7.setOnClickListener(this);
        button_8.setOnClickListener(this);
        button_9.setOnClickListener(this);
        button_add.setOnClickListener(this);
        button_sub.setOnClickListener(this);
        button_mul.setOnClickListener(this);
        button_div.setOnClickListener(this);
        button_sin.setOnClickListener(this);
        button_cos.setOnClickListener(this);
        button_tan.setOnClickListener(this);
        button_ac.setOnClickListener(this);
        button_del.setOnClickListener(this);
        button_point.setOnClickListener(this);
        button_lbracket.setOnClickListener(this);
        button_rbracket.setOnClickListener(this);
        button_eql.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String input = editText.getText().toString();
        DecimalFormat df = new DecimalFormat("0.000000000000000");//输出控制
        String re;
        float res;
        switch (v.getId()) {
            case R.id.button_0:
            case R.id.button_1:
            case R.id.button_2:
            case R.id.button_3:
            case R.id.button_4:
            case R.id.button_5:
            case R.id.button_6:
            case R.id.button_7:
            case R.id.button_8:
            case R.id.button_9:
            case R.id.button_add:
            case R.id.button_sub:
            case R.id.button_mul:
            case R.id.button_div:
            case R.id.button_point:
            case R.id.button_lbracket:
            case R.id.button_rbracket:
                editText.setText(input + ((Button) v).getText());
                break;
            case R.id.button_ac:
                editText.setText("");
                break;
            case R.id.button_del:
                if (input.length() > 0)
                    editText.setText(input.substring(0, input.length() - 1));
                break;
            case R.id.button_eql:
                if(editText.length()!=0) {
                    re = getresult();
                    if (re == "Infinity")
                        editText.setText("Error");
                    else {
                        res = Float.parseFloat(re);
                        if ((int) res == res)
                            editText.setText(Integer.toString((int) res));
                        else
                            editText.setText(String.valueOf(res));
                    }
                }
                break;
            case R.id.button_sin:
                if (input.length() > 0) {
                    res = Float.valueOf(editText.getText().toString());
                    editText.setText(String.valueOf(df.format(Math.sin(Math.PI * res / 180))));
                }
                break;
            case R.id.button_cos:
                if (input.length() > 0) {
                    res = Float.valueOf(editText.getText().toString());
                    editText.setText(String.valueOf(df.format(Math.cos(Math.PI * res / 180))));
                }
                break;
            case R.id.button_tan:
                if (input.length() > 0) {
                    res = Float.valueOf(editText.getText().toString());
                    editText.setText(String.valueOf(df.format(Math.tan(Math.PI * res / 180))));
                }
                break;
        }
    }

    public String getresult() {
        String exp = editText.getText().toString();
        editText.setText("");
        eval ca = new eval();//调用eval函数进行计算
        return ca.caculate(ca.inorder(exp)).toString();
    }
}
