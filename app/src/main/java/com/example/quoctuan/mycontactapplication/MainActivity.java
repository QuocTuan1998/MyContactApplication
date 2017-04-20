package com.example.quoctuan.mycontactapplication;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText editPhone, editND;
    Button btnQuaySo, btnGoiLuon, btnNhanTin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
        addEvents();
    }

    private void addControls() {

        editPhone = (EditText) findViewById(R.id.editPhone);
        editND = (EditText) findViewById(R.id.editND);

        btnGoiLuon = (Button) findViewById(R.id.btnGoiLuon);
        btnQuaySo = (Button) findViewById(R.id.btnQuaySo);
        btnNhanTin = (Button) findViewById(R.id.btnNhanTin);

    }

    private void addEvents() {

        btnQuaySo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyQuaySo();
            }
        });
        btnGoiLuon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyGoi();
            }
        });
        btnNhanTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyNhanTinVaQuanLyKetQua();
            }
        });

    }

    private void xuLyNhanTinVaQuanLyKetQua() {
//        lấy mặc định SmsManager
        final SmsManager sms = SmsManager.getDefault();
//        lấy lệnh gởi tin nhắn
        Intent msgSent = new Intent(Intent.ACTION_SEND);
//        khai báo pendingintent để kiểm tra kết quả
//        là intent delay : chờ khi nào xong thì mới kích hoạt
        final PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,msgSent,0);
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int result = getResultCode();
                String msg = "Đã gửi tin nhắn thành công";
                if(result != Activity.RESULT_OK){
                    msg = "Gửi tin nhắn thất bại";
                }
                Toast.makeText(MainActivity.this,msg,
                        Toast.LENGTH_SHORT).show();
            }

        }, new IntentFilter("ACTION_MSG_SENT"));
//        những chỗ null là những chỗ không quan tâm kết quả trả về
        sms.sendTextMessage(editPhone.getText().toString(),
                null, editND.getText() + "",
                pendingIntent,null);
    }

    private void xuLyGoi() {

        Uri uri = Uri.parse("tel:" + editPhone.getText().toString());
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(uri);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(intent);
    }

    private void xuLyQuaySo() {
//      cú pháp chuẩn không được sửa
        Uri uri = Uri.parse("tel:"+editPhone.getText().toString());
//      khởi tạo Intent(intent không tường minh)
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(uri);
//      để android quản lý
        startActivity(intent);
    }
}
