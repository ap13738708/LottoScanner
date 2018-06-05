package com.example.scannerproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;

public class RemoveNumActivity extends AppCompatActivity {

    Context mContext = this;
    Button btn_showMatched;
    TextView name;
    ArrangeNum numSort;
    EditText numRemoved;
    Button btn_confirm;
    int requestCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_num);

        name = findViewById(R.id.name);
        btn_confirm = findViewById(R.id.btn_confirm);
        numRemoved = findViewById(R.id.num_removed);
        numRemoved.setHorizontallyScrolling(true);

        final Intent intent = getIntent();
        requestCode = intent.getIntExtra("requestCode",-1);
        numSort = (ArrangeNum) intent.getSerializableExtra("Obj");
        setTitle("Edit: " + numSort.lottogroup);
        setView();

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = numRemoved.getText().toString();
                String[] array = text.split("\\.");
                for (int i = 0 ; i < array.length ; i++){
                    if(array[i].length() != 4){
                        alertBox("ใส่ตัวเลขไม่ถูกต้อง",  array[i] + " ผิดรูปแบบ"+ "\n" + "ตัวอย่างรูปแบบที่ถูกต้อง : 1234.5678.9101.1213");
                        return;
                    }
                }
                try {
                    Log.i("checkRemove", Arrays.toString(array));
                    numSort.removeNum(array);
                    Intent intent1 = new Intent(RemoveNumActivity.this, MainActivity.class);
                    intent1.putExtra("requestCode", requestCode);
                    intent1.putExtra("Obj", numSort);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TASK );
                    startActivity(intent1);
                } catch (Exception e) {
                    alertBox("ใส่ตัวเลขไม่ถูกต้อง", "ตัวอย่างรูปแบบที่ถูกต้อง : 1234.5678.9101.1213");
                }
            }
        });

    }




    public void setView() {

        TableRow tableRow =  findViewById(R.id.row1);
        name.setText(numSort.name + "   " + numSort.phone + "  " + getDate2());


        String[] num;
        for(int i=0;i<10;i++) {
            int count = 0;
            num = numSort.arrayOfArrayList[i].toArray(new String[numSort.arrayOfArrayList[i].size()]);
            Log.i("check", String.valueOf(num.length));
            Log.i("check", Arrays.toString(num));
            String numText = "";
            while(num.length > count){
                TextView text = new TextView(this);
                text.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                for (int j = 0 ; j < 35 ; j++){
                    if(num.length == count) break;
                    Log.i("check",num[count] + " " + count);
                    numText += num[count] + "\n";
                    count++;

                }
                text.setText(numText);
                numText = "";
                text.setPadding(10,0,0,5);
                tableRow.addView(text);
            }
        }


    }

    public String getDate2() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String result = dateFormat.format(date);
        return result;
    }

    public void alertBox(String title, String msg){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set title
        alertDialogBuilder.setTitle(title);

        // set dialog message
        alertDialogBuilder
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, close
                        // current activity
                        dialog.cancel();
                    }
                });
//                    .setNegativeButton("No",new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            // if this button is clicked, just close
//                            // the dialog box and do nothing
//                            dialog.cancel();
//                        }
//                    });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
}
