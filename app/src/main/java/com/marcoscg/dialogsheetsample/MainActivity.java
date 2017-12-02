package com.marcoscg.dialogsheetsample;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.marcoscg.dialogsheet.DialogSheet;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogSheet dialogSheet = new DialogSheet(MainActivity.this)
                        .setTitle(R.string.app_name)
                        .setMessage(R.string.lorem)
                        .setPositiveButton(android.R.string.ok, new DialogSheet.OnPositiveClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(MainActivity.this, "Positive button clicked!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, null);
                        //.setButtonsColorRes(R.color.colorPrimary)

                if(((AppCompatCheckBox)findViewById(R.id.iconCheckBox)).isChecked())
                    dialogSheet.setIcon(R.mipmap.ic_launcher);

                dialogSheet.show();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_github:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/marcoscgdev/DialogSheet")));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
