package com.example.scannerproject;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dropbox.sync.android.DbxAccountManager;
import com.dropbox.sync.android.DbxException;
import com.dropbox.sync.android.DbxFile;
import com.dropbox.sync.android.DbxFileSystem;
import com.dropbox.sync.android.DbxPath;
import com.google.zxing.Result;

import java.io.IOException;
import java.util.ArrayList;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class MainActivity extends AppCompatActivity {

    private static final String APP_KEY = "s8fu8ptwrylyhpb";
    private static final String APP_SECRET = "2b5ghqfmbls3fmb";
    static final int REQUEST_LINK_TO_DBX = 0;

    Button btnSound;
    TextView textView;
    String all = "";
    MediaPlayer beapSound;
    private DbxAccountManager dbxAccountManager;
    DbxFileSystem dbxFs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbxAccountManager = DbxAccountManager.getInstance(getApplicationContext(), APP_KEY, APP_SECRET);
        try {
            dbxFs = DbxFileSystem.forAccount(dbxAccountManager.getLinkedAccount());
        } catch (DbxException.Unauthorized unauthorized) {
            unauthorized.printStackTrace();
        }
        btnSound = findViewById(R.id.btnSound);
        textView = findViewById(R.id.textView);
        beapSound = MediaPlayer.create(this, R.raw.censor_beep_01);
        btnSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText(all);
            }
        });
    }

    public void scan(View view) {
        Intent intent = new Intent(MainActivity.this.getApplicationContext(), Scanner.class);
        startActivityForResult(intent, 2404);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 2404 && data != null) {
                all = data.getStringExtra("param");
            } else if (requestCode == REQUEST_LINK_TO_DBX) {
                DbxFile testFile = null;
                try {
                    testFile = dbxFs.create(new DbxPath("hello.txt"));
                } catch (DbxException e) {
                    e.printStackTrace();
                }
                try {
                    testFile.writeString("Hello Dropbox!");
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    testFile.close();
                }
            }
        }
    }

    public void onClickLinkToDropbox(View view) {
        dbxAccountManager.startLink((Activity) this, REQUEST_LINK_TO_DBX);
    }
}
