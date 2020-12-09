package com.example.contacts;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
/*更新查看联系人信息界面*/
public class Editview extends AppCompatActivity {
    private EditText editText_name1;
    private EditText editText_tel1;
    private EditText editText_comp1;
    private EditText editText_email1;
    private String oldname;//改之前的老名字用于当名字也被修改时使用
    DatabaseHelper helper = new DatabaseHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editview);
        Intent intent = getIntent();
        editText_name1 = (EditText) findViewById(R.id.editText_name1);
        editText_tel1 = (EditText) findViewById(R.id.editText_phone1);
        editText_comp1 = (EditText) findViewById(R.id.editText_company1);
        editText_email1 = (EditText) findViewById(R.id.editText_email1);
        oldname = intent.getStringExtra("extra_name");//获取传送的数据下同
        editText_name1.setText(intent.getStringExtra("extra_name"));
        editText_tel1.setText(intent.getStringExtra("extra_phone"));
        editText_comp1.setText(intent.getStringExtra("extra_comp"));
        editText_email1.setText(intent.getStringExtra("extra_email"));
    }
/*完成编辑按钮事件*/
    public void finish(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Editview.this);
        builder.setIcon(R.drawable.ic_launcher_foreground);
        builder.setTitle("弹出警告框");
        builder.setMessage("确定修改吗？");
        editText_name1 = (EditText) findViewById(R.id.editText_name1);//绑定
        editText_tel1 = (EditText) findViewById(R.id.editText_phone1);
        editText_comp1 = (EditText) findViewById(R.id.editText_company1);
        editText_email1 = (EditText) findViewById(R.id.editText_email1);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {/*确定按钮，执行更新*/
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = editText_name1.getText().toString().trim();//获取姓名，下同
                String tel = editText_tel1.getText().toString().trim();
                String comp = editText_comp1.getText().toString().trim();
                String email= editText_email1.getText().toString().trim();
                SQLiteDatabase db = helper.getWritableDatabase();
                ContentValues values = new ContentValues();//用于存放更新的数据
                values.put("name", name);//存放姓名
                values.put("Phone", tel);
                values.put("Company", comp);
                values.put("Email", email);
                db.update("person", values, "name=?", new String[]{oldname});//更新语句
                Toast.makeText(getApplicationContext(), "更新成功", Toast.LENGTH_SHORT).show();
                db.close();//关闭数据库
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);//返回界面
                startActivity(intent);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {/*取消按钮神秘也不执行*/
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }
/*回退按钮，回到主界面*/
    public void back(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
