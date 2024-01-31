package com.clj.blesample.operation;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.clj.blesample.R;
import com.clj.blesample.comm.Observer;
import com.clj.blesample.comm.ObserverManager;
import com.clj.fastble.BleManager;

import com.clj.fastble.data.BleDevice;
import com.clj.fastble.exception.BleException;
import com.clj.fastble.utils.HexUtil;

import com.clj.fastble.callback.BleNotifyCallback;
import com.clj.fastble.callback.BleWriteCallback;

import java.util.ArrayList;
import java.util.List;



public class OperationActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String KEY_DATA = "key_data";
    float scaleAccel = 0.00478515625f;
    float scaleAngle = 0.0054931640625f;
    float scalePos = 1000.0f;
    private BleDevice bleDevice;
    private BluetoothGatt gatt;
    private BluetoothGattService Theservice;
    private BluetoothGattCharacteristic characteristic1;
    private BluetoothGattCharacteristic characteristic2;
    private Button btn_keep;
    private Button btn_notify;
    private TextView txt_x1;
    private TextView txt_y1;
    private TextView txt_z1;
    private TextView txt_x2;
    private TextView txt_y2;
    private TextView txt_z2;
    private TextView txt_x3;
    private TextView txt_y3;
    private TextView txt_z3;
    private TextView txt_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation);
        init();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_keep:
                BleManager.getInstance().write(
                        bleDevice,
                        Theservice.getUuid().toString(),
                        characteristic1.getUuid().toString(),
                        HexUtil.hexStringToBytes("29"),
                        new BleWriteCallback(){

                            @Override
                            public void onWriteSuccess(final int current, final int total, final byte[] justWrite) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(OperationActivity.this, "保持连接", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                            @Override
                            public void onWriteFailure(final BleException exception) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(OperationActivity.this, "保持连接失败", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                );

                break;

            case R.id.btn_notify:
                if(btn_notify.getText().toString().equals("打开通知")){
                    btn_notify.setText("关闭通知");
                    BleManager.getInstance().notify(
                        bleDevice,
                        Theservice.getUuid().toString(),
                        characteristic2.getUuid().toString(),
                        new BleNotifyCallback(){

                            @Override
                            public void onNotifySuccess() {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(OperationActivity.this, "Notify success", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                            @Override
                            public void onNotifyFailure(final BleException exception) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(OperationActivity.this, "Notify failure", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                            @Override
                            public void onCharacteristicChanged(byte[] data) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    //此处接收数据更新
                                    public void run() {
                                        String temp = HexUtil.formatHexString(characteristic2.getValue());
                                        char[] arr=temp.toCharArray();
                                        int L = 7;

                                        String x1 = String.format("%.3f", ((Integer.valueOf((temp.substring(16,18)+temp.substring(14,16)),16)).shortValue() *scaleAccel));
                                        String y1 = String.format("%.3f", ((Integer.valueOf((temp.substring(20,22)+temp.substring(18,20)),16)).shortValue() *scaleAccel));
                                        String z1 = String.format("%.3f", ((Integer.valueOf((temp.substring(24,26)+temp.substring(22,24)),16)).shortValue() *scaleAccel));
                                        txt_x1.setText(x1);
                                        txt_y1.setText(y1);
                                        txt_z1.setText(z1);

                                        String x2 = String.format("%.3f", ((Integer.valueOf((temp.substring(28,30)+temp.substring(26,28)),16)).shortValue() *scaleAngle));
                                        String y2 = String.format("%.3f", ((Integer.valueOf((temp.substring(32,34)+temp.substring(30,32)),16)).shortValue() *scaleAngle));
                                        String z2 = String.format("%.3f", ((Integer.valueOf((temp.substring(36,38)+temp.substring(34,36)),16)).shortValue() *scaleAngle));
                                        txt_x2.setText(x2);
                                        txt_y2.setText(y2);
                                        txt_z2.setText(z2);

                                        String x3 = String.format("%.2f", ((Integer.valueOf((temp.substring(40,42)+temp.substring(38,40)),16)).shortValue() *0.001));
                                        String y3 = String.format("%.2f", ((Integer.valueOf((temp.substring(44,46)+temp.substring(42,44)),16)).shortValue() *0.001));
                                        String z3 = String.format("%.2f", ((Integer.valueOf((temp.substring(48,50)+temp.substring(46,48)),16)).shortValue() *0.001));
                                        //此处更新位置信息
                                        txt_x3.setText(x3);
                                        txt_y3.setText(y3);
                                        txt_z3.setText(z3);
                                        //To do
                                        //应该要同时在此处更新轨迹

//                                        addText(txt_data, HexUtil.formatHexString(characteristic2.getValue(), true));
                                    }
                                });
                            }
                        }


                    );
                }
                else{
                    btn_notify.setText("打开通知");
                    BleManager.getInstance().stopNotify(
                            bleDevice,
                            Theservice.getUuid().toString(),
                            characteristic2.getUuid().toString());
                }
                break;
        }
    }

    private void init(){

        bleDevice = getIntent().getParcelableExtra(KEY_DATA);
        if (bleDevice == null)
            finish();
        gatt = BleManager.getInstance().getBluetoothGatt(bleDevice);

        int Sflag = 0;
        for(BluetoothGattService service : gatt.getServices()){
            if(Sflag == 1)  Theservice = service;
            Sflag++;

        }
        int Cflag = 0;
        for(BluetoothGattCharacteristic characteristic : Theservice.getCharacteristics()){
            if(Cflag == 0)  characteristic1 = characteristic;
            if(Cflag == 1) characteristic2 = characteristic;
            Cflag++;
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btn_keep = (Button) findViewById(R.id.btn_keep);
        btn_keep.setText("保持连接");
        btn_keep.setOnClickListener(this);

        btn_notify = (Button) findViewById(R.id.btn_notify);
        btn_notify.setText("打开通知");
        btn_notify.setOnClickListener(this);

        txt_x1 = (TextView) findViewById(R.id.txt_x1);
        txt_y1 = (TextView) findViewById(R.id.txt_y1);
        txt_z1 = (TextView) findViewById(R.id.txt_z1);

        txt_x2 = (TextView) findViewById(R.id.txt_x2);
        txt_y2 = (TextView) findViewById(R.id.txt_y2);
        txt_z2 = (TextView) findViewById(R.id.txt_z2);

        txt_x3 = (TextView) findViewById(R.id.txt_x3);
        txt_y3 = (TextView) findViewById(R.id.txt_y3);
        txt_z3 = (TextView) findViewById(R.id.txt_z3);

        txt_data = (TextView) findViewById(R.id.txt_data);
        txt_data.setMovementMethod(ScrollingMovementMethod.getInstance());


    }

    private void addText(TextView textView, String content) {
        textView.append(content);
        textView.append("\n");
        int offset = textView.getLineCount() * textView.getLineHeight();
        if (offset > textView.getHeight()) {
            textView.scrollTo(0, offset - textView.getHeight());
        }
    }
}