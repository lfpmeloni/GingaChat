package xyz.felipemeloni.android.gingachat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public final static String EXTRA_USER = "xyz.felipemeloni.android.gingachat.USER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataStorage.msgList = new ArrayList<String>();
    }

    public void goToChat(View view) {
        Intent intent = new Intent(this, ChatActivity.class);
        EditText editText = (EditText) findViewById(R.id.edit_user);
        String user = editText.getText().toString();
        intent.putExtra(EXTRA_USER, user);
        startActivity(intent);

    }

}
