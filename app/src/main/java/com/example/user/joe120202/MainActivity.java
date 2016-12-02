package com.example.user.joe120202;

import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void click1(View v)
    {
        File f1 = getFilesDir(); //抓取手機內部 file資料的儲存路徑
        Log.d("FILE", f1.toString());
        File f2 = getCacheDir(); //抓取手機內部 cache資料的儲存路徑
        Log.d("FILE", f2.toString());
        File f3 = getExternalFilesDir("");//抓取外部SD卡(手機模擬的)的儲存路徑
        Log.d("FILE", f3.toString());
    }
    public void Write(View v)
    {
        //用FileOutputStream 儲存myadat.txt資料至手機內部
        FileOutputStream fOut = null;
        try {

            fOut = openFileOutput("mydata.txt", MODE_PRIVATE);
            OutputStreamWriter osw = new OutputStreamWriter(fOut);  // 寫入資料
            osw.write("She sell sea shells on the sea shore .");
            osw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void Read(View v)
    //用FileReader讀取myadat.txt資料至:用read(buffer)將字元一個一個放入至stringBuilder sb,然後再顯示
    {
        char[] buffer = new char[1];
        FileReader fr = null;
        StringBuilder sb = new StringBuilder();
        File file = new File(getFilesDir() + "/" + "mydata.txt");

        try {
            fr = new FileReader(file);
            while (fr.read(buffer)!= -1) {
                sb.append(new String(buffer));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("READFILE", sb.toString());

    }
    public void clickReadRaw(View v)
    {
        //打開res下的raw資料夾的文件內容
        InputStream is = null;
        InputStreamReader reader = null;
        StringBuilder sb = new StringBuilder();
        is = getResources().openRawResource(R.raw.test);

        char[] buffer = new char[1];
        try {
            reader = new InputStreamReader(is, "UTF-8");
            while (reader.read(buffer) != -1) {
                sb.append(new String(buffer));
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d("RAWREAD", sb.toString());

    }

    public void click5(View v)
          //將文件資料 寫入至外部SD卡(手機模擬)
    {
        File f3 = getExternalFilesDir("");
        Log.d("FILE", f3.toString());
        String wFile = f3.toString() + File.separator + "myfile2.txt";
        Log.d("FILE", "wFile:" + wFile);
        try {
            FileOutputStream fos = new FileOutputStream(wFile);
            OutputStreamWriter osw = new OutputStreamWriter(fos);  // 寫入資料
            osw.write("She sell sea shells on the sea shore .");
            osw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void click6(View v)
    {
        //在外部SD卡(手機模擬) 建立資料夾
        File f3 = getExternalFilesDir("");
        File f4 = new File(f3.toString() + File.separator + "test6");
        f4.mkdir();
    }

    public void click7(View v)
    {
        //在外部SD卡根目錄建立資料夾,須設定可以讀取SD卡的權限
        int permission = ActivityCompat.checkSelfPermission(this,
                WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            //未取得權限，向使用者要求允許權限
            Log.d("PERM", "沒有權限");

            ActivityCompat.requestPermissions(this,
                    new String[] {WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE},
                    123 // RequestCode
            );
        }
        else
        {
            Log.d("PERM", "有權限");
            File f3 = Environment.getExternalStorageDirectory();
            Log.d("EXT", f3.toString());
            File f4 = new File(f3.toString() + File.separator + "test7");
            f4.mkdir();
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 123)
        {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                File f3 = Environment.getExternalStorageDirectory();
                Log.d("EXT", f3.toString());
                File f4 = new File(f3.toString() + File.separator + "test7");
                f4.mkdir();
            }
        }
    }
}
