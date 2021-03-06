package com.example.owner.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AD_List extends AppCompatActivity {
    static final String username = "ECASystem";
    static final String password = "ECASystem";
    private int A1=0;
    private String AccEditToString="";
    static Connection conn;
    static Statement statement;
    static ResultSet rs,updateRS;

    ListAD_Listview adapter;
    ListView lvone;

    ArrayList<String> Ser = new ArrayList<String>();   //動態陣列，放rp資料
    ArrayList<String> Name = new ArrayList<String>();
    ArrayList<String> Type = new ArrayList<String>();
    ArrayList<Integer> Status = new ArrayList<Integer>();
    ArrayList<String> Loc = new ArrayList<String>();
    ArrayList<String> Det = new ArrayList<String>();
    ArrayList<Integer> RPSer = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0,0);
        setContentView(R.layout.activity_ad__list);
        StrictMode.ThreadPolicy policy =
                new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Intent intent = getIntent();
        AccEditToString = intent.getStringExtra(     //接
                "AccEditToString");
        A1 = intent.getIntExtra("A1", 0);

//        try {
//            Class.forName("org.mariadb.jdbc.Driver").newInstance();
            try {
//                final String URL = "jdbc:mariadb://120.105.161.89/ECASystem";
//                System.out.println(URL);
//                Connection conn = DriverManager.getConnection(URL, username, password); //呼叫Connection物件，進行資料庫連線
                statement = Login.statement;
                System.out.println("資料庫連結成功");
                Log.e("TEST", "資料庫連結成功");
                //Toast.makeText(RP_List.this, "資料庫連結成功",Toast.LENGTH_SHORT).show();

                int re = 0;

                rs = statement.executeQuery("select ARD_Serial, ARD_Name, ARD_Type, ARD_Status, ARD_Loc, ARD_Det, ARD_RaspSerial  from  ARD WHERE ARD_RaspSerial= '" + A1 + "'");   // WHERE RASP_Acc= '" + AA + "'

                while (rs.next()) {
                    Ser.add(re, rs.getString("ARD_Serial"));
                    Name.add(re, rs.getString("ARD_Name"));
                    Type.add(re, rs.getString("ARD_Type"));
                    Status.add(re, rs.getInt("ARD_Status"));
                    Loc.add(re, rs.getString("ARD_Loc"));
                    Det.add(re, rs.getString("ARD_Det"));
                    RPSer.add(re, rs.getInt("ARD_RaspSerial"));
                    re++;
                }
                System.out.println("ADSer=" + Ser.get(re - 1));
                adapter = new ListAD_Listview(AD_List.this, Ser, Name, Type, Status, Loc, Det, RPSer, conn, A1, AccEditToString);
                lvone = (ListView) findViewById(R.id.lvone);
                lvone.setAdapter((ListAdapter) adapter);
                lvone.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        System.out.println("");
                    }
                });
//                statement.close();
//                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
//        } catch (Exception k) {
//            k.printStackTrace();
//        }


        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBarA);
        bottomBar.setDefaultTab(R.id.tab_ard);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                switch (tabId) {
                    case R.id.tab_rasp:
                        Intent intentR = new Intent();
                        intentR.setClass(AD_List.this,
                                RP_List.class);
                        intentR.putExtra("A1", A1);
                        intentR.putExtra("AccEditToString", AccEditToString);
                        startActivity(intentR);
                        finish();
                        break;
                    case R.id.tab_soc:
                        Intent intentS = new Intent();
                        intentS.setClass(AD_List.this,
                                SOC_List.class);
                        intentS.putExtra("A1", A1);
                        intentS.putExtra("AccEditToString", AccEditToString);
                        startActivity(intentS);
                        finish();
                        break;
                    case R.id.tab_eca:
                        Intent intentE = new Intent();
                        intentE.setClass(AD_List.this,
                                ECA_List.class);
                        intentE.putExtra("A1", A1);
                        intentE.putExtra("AccEditToString", AccEditToString);
                        startActivity(intentE);
                        finish();
                        break;
                    case R.id.tab_dev:
                        Intent intentD = new Intent();
                        intentD.setClass(AD_List.this,
                                DEV_List.class);
                        intentD.putExtra("A1", A1);
                        intentD.putExtra("AccEditToString", AccEditToString);
                        startActivity(intentD);
                        finish();
                        break;
                }
            }
        });

        bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(@IdRes int tabId) {
                Toast.makeText(getApplicationContext(), TabMessage.get(tabId, true), Toast.LENGTH_LONG).show();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.UserInfo_title:
                Toast.makeText(this, "使用者資料", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(this,User.class);
                intent.putExtra("A1", A1);
                intent.putExtra("AccEditToString", AccEditToString);
                startActivity(intent);
                finish();
                return true;
            case R.id.Explain_title:
                Toast.makeText(this, "說明", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.LogOut_title:
                Toast.makeText(this, "已登出", Toast.LENGTH_SHORT).show();
                intent = new Intent();
                intent.setClass(this,Login.class);
                intent.putExtra("A1", A1);
                intent.putExtra("AccEditToString", AccEditToString);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
