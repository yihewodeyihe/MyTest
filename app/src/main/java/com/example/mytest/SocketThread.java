package com.example.mytest;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.TimeZone;

public class SocketThread extends Thread {
    public static BluetoothSocket socket=null;
    public static BluetoothDevice device=null;
    private InputStream is;
    private OutputStream os;
    private boolean isOpen = false;


    public SocketThread(BluetoothSocket socket,BluetoothDevice device) {
        try {
            SocketThread.socket =socket;
            SocketThread.device=device;

            if(!SocketThread.socket.isConnected())
            {
                //尝试连接，阻塞
                SocketThread.socket.connect();
            }
            //是否连接
            if(SocketThread.socket.isConnected())
            {
                MyTestActivity.handler.obtainMessage(1,0,0,"已连上设备："+SocketThread.device.getName()).sendToTarget();
                //获取流
                is = SocketThread.socket.getInputStream();
                os = SocketThread.socket.getOutputStream();
                isOpen = true;
            }
            else
            {
                isOpen=false;
            }

        } catch (IOException e) {
            e.printStackTrace();
            release();
        }
    }

    @Override
    public void run() {

            int readLen = 0;
            byte[] buffer = new byte[1024];

            try {
                while (isOpen) {
                    while ((readLen = is.read(buffer)) != -1) {
                        System.out.println(buffer);
                        MainActivity.handler.obtainMessage(1, readLen,
                                0, buffer).sendToTarget();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                release();
        }
        release();
    }

    public void release(){
        try{
            isOpen=false;
            device=null;
            MyTestActivity.handler.obtainMessage(2,0,0,"断开连接").sendToTarget();
            if(os!=null){
                try{
                    os.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
                os=null;
            }
            if(is!=null){
                try{
                    is.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
                is=null;
            }
            if(socket!=null){
                try{
                    socket.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
                socket=null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}