package com.example.contacts;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
/*新建联系人界面*/
public class Information extends AppCompatActivity {
    private EditText editText_name;
    private EditText editText_tel;
    private EditText editText_comp;
    private EditText editText_email;
    DatabaseHelper helper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
    }
/*完成输入按钮，执行添加*/
    public void finish(View v) {
        editText_name = (EditText) findViewById(R.id.editText_name);//绑定
        editText_tel = (EditText) findViewById(R.id.editText_phone);
        editText_comp = (EditText) findViewById(R.id.editText_company);
        editText_email = (EditText) findViewById(R.id.editText_email);
        String name = editText_name.getText().toString().trim();//获取名字
        String tel = editText_tel.getText().toString().trim();
        String comp = editText_comp.getText().toString().trim();
        String email = editText_email.getText().toString().trim();
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();//用于存放新的数据
        values.put("name", name);//存放姓名下同
        values.put("Phone", tel);
        values.put("Company", comp);
        values.put("Email", email);
        db.insert("person", null, values);//插入语句
        Toast.makeText(getApplicationContext(), "成功添加", Toast.LENGTH_SHORT).show();
        db.close();
        Intent intent = new Intent(Information.this, MainActivity.class);
        startActivity(intent);
    }
/*回退按钮，回到主界面*/
    public void back(View v) {
        Intent intent = new Intent(Information.this, MainActivity.class);
        startActivity(intent);
    }
}
