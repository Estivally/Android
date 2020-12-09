package com.example.contacts;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;
import java.util.ArrayList;
/*主类，实现主界面的交互*/
public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase sd;//数据库
    private ArrayList<Person> personlist;//存储数据库里读出的数据
    private ListView lv;//显示数据库里的列表
    private SearchView searchView;//搜索框

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchView = (SearchView) findViewById(R.id.searchview);//绑定搜索框
        DatabaseHelper helper = new DatabaseHelper(this);
        helper.getWritableDatabase();//数据库的创建
        sd = helper.getWritableDatabase();
        personlist = new ArrayList<>();
        /*数据查询，用游标从数据库读取出来*/
        final Cursor cursor = sd.query("person", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Person pe = new Person();    //存一个条目的数据
            pe.setId(cursor.getInt(0));//获取第一列的数据，即id，下同
            pe.setName(cursor.getString(1));
            pe.setTel(cursor.getString(2));
            pe.setComp(cursor.getString(3));
            pe.setEmail(cursor.getString(4));
            personlist.add(pe);//把数据库的每一行加入数组中
            pe = null;//清空
        }
        cursor.close();//游标关闭
        final ListAdapt adapter = new ListAdapt(MainActivity.this, R.layout.listview, personlist);//适配器
        lv = (ListView) findViewById(R.id.listview_content);//绑定
        lv.setAdapter(adapter);//设置适配器
        //lv.setTextFilterEnabled(true);
        searchView.setIconifiedByDefault(false);//设置搜索框图标是放大还是缩小
        searchView.setSubmitButtonEnabled(true);//添加一个提交按钮
        /*监听搜索框*/
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override/*当搜索框提交的时候*/
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this, "you choose:" + query, Toast.LENGTH_SHORT).show();
                return false;
            }
            @Override/*当搜索框里的文本发生改变，newtext为查询内容*/
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {/*无内容，退回到所有数据*/
                    Toast.makeText(MainActivity.this, "null", Toast.LENGTH_SHORT).show();
                    cursors(newText);
                } else {/*有内容，传入参数进行查询筛选*/
                    Toast.makeText(MainActivity.this, adapter.toString(), Toast.LENGTH_SHORT).show();
                    cursors(newText);
                }
                return true;
            }
        });
        /*listview点击监听事件*/
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Person pe = adapter.getItem(position);//获取当前点击列表的所有信息
                String string = pe.getName();
                searchView.setQuery(string, true);
                Intent intent = new Intent(MainActivity.this, Editview.class);//跳转到编辑页面
                intent.putExtra("extra_name", pe.getName());//用extra_name把name传到下一个活动，下同
                intent.putExtra("extra_phone", pe.getTel());
                intent.putExtra("extra_comp", pe.getComp());
                intent.putExtra("extra_email", pe.getEmail());
                startActivity(intent);//启动Intent
            }
        });
       /* 长按产生菜单*/
        lv.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override/*第一个数字是组别，第二个参数是菜单id，第三个为排列顺序，为0默认排序，第四个参数为显示信息*/
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.add(0, 0, 0, "拨打电话");
                menu.add(0, 1, 0, "发送短信");
                menu.add(0, 2, 0, "查看详细");
                menu.add(0, 3, 0, "编辑信息");
                menu.add(0, 4, 0, "删除");
            }
        });
    }
/*添加事件，跳到添加页面*/
    public void add(View v) {
        Intent intent = new Intent(MainActivity.this, Information.class);
        startActivity(intent);
    }
/*列表显示函数，用于筛选到数据输出到listview中*/
    public void cursors(String newText) {
        ArrayList<Person> personlist = new ArrayList<>();
        String[] selectionArgs = new String[]{"%" + newText + "%"};/*用模糊搜索，这样写为了将数据传入，下面的like也是配合，？为占位符，传入第四个参数*/
        Cursor cursor = sd.query("person", null, "name like ?", selectionArgs, null, null, null);
        /*游标循环查找*/
        while (cursor.moveToNext()) {
            Person pe = new Person();    //存一个条目的数据
            pe.setId(cursor.getInt(0));
            pe.setName(cursor.getString(1));
            pe.setTel(cursor.getString(2));
            pe.setComp(cursor.getString(3));
            pe.setEmail(cursor.getString(4));
            personlist.add(pe);//把数据库的每一行加入数组中
            pe = null;
        }
        cursor.close();//游标关闭，下面与Oncreate里的一致，设置listview里的内容
        final ListAdapt adapter = new ListAdapt(MainActivity.this, R.layout.listview, personlist);
        lv = (ListView) findViewById(R.id.listview_content);
        lv.setAdapter(adapter);
    }
/*对于编辑菜单的处理*/
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        DatabaseHelper helper = new DatabaseHelper(this);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;
        final Person pe = personlist.get(position);
        Intent intent;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);//弹出窗口
        switch (item.getItemId()) {
            case 0:/*拨打电话*/
                intent = new Intent(Intent.ACTION_DIAL);//调用系统自带拨号界面
                Uri data = Uri.parse("tel:" + pe.getTel());//传送号码
                intent.setData(data);
                startActivity(intent);
                return true;
            case 1:/*发送信息*/
                intent = new Intent(Intent.ACTION_SENDTO);//调用系统自带发短信界面
                Uri data1 = Uri.parse("smsto:" + pe.getTel());//传送要发送的对象号码
                intent.setData(data1);
                startActivity(intent);
                return true;
            case 2:/*显示信息*/
                builder.setTitle(pe.getName());//设置显示标题
                builder.setMessage("电话：" + pe.getTel() + "\n" + "工作单位：" + pe.getComp()+"\n" + "电子邮箱：" + pe.getEmail());//设置输出信息
                builder.setCancelable(false);//取消取消按钮
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.show();//输出对话框
                return true;
            case 3:/*编辑，同上*/
                intent = new Intent(this, Editview.class);
                intent.putExtra("extra_name", pe.getName());
                intent.putExtra("extra_phone", pe.getTel());
                intent.putExtra("extra_comp", pe.getComp());
                intent.putExtra("extra_email", pe.getEmail());
                startActivity(intent);
                return true;
            case 4:/*删除信息*/
                builder.setIcon(R.drawable.ic_launcher_foreground);
                builder.setTitle("弹出警告框");
                builder.setMessage("确定删除" + pe.getName() + "的信息吗？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {/*确定按钮，执行删除*/
                        long id = sd.delete("person", "name=?", new String[]{pe.getName()});//删除语句，如果成功，输出大于0的数
                        if (id > 0) {
                            Toast.makeText(getApplicationContext(), "删除" + pe.getName() + "成功", Toast.LENGTH_SHORT).show();
                        }
                        Intent intent = new Intent();
                        intent.setClassName("com.example.contacts", "com.example.contacts.MainActivity");//刷新页面
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {/*取消按钮*/
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.setClassName("com.example.contacts", "com.example.contacts.MainActivity");
                        startActivity(intent);
                    }
                });
                builder.show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}