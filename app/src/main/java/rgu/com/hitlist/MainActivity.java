package rgu.com.hitlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnWatchList = findViewById(R.id.btnWatchList);
        btnWatchList.setOnClickListener(this);
        Button btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnWatchList:
                startActivity(new Intent(this, WatchListActivity.class));
                break;
            case R.id.btnSearch:
                startActivity(new Intent(this, SearchActivity.class));
                break;
        }
    }
}
