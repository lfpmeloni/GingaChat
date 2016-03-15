package xyz.felipemeloni.android.gingachat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        populate();
    }

    public void populate(){

        List values = DataStorage.getMsgList();
        Log.d("LIST_VIEW_POPULATE", values.toString());
        ArrayAdapter<String> arrayAdapter;
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, values);
        ListView listView = (ListView)findViewById(R.id.listView1);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ListActivity.this,"Posicao "+position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void returnScreen(View view){
        finish();
    }
}
