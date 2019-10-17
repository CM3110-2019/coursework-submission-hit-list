package rgu.com.hitlist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionSearch:
                startActivity(new Intent(this, SearchActivity.class));
                return true;
            case R.id.actionWatchList:
                startActivity(new Intent(this, WatchListActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
