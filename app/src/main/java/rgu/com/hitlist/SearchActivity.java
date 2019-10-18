package rgu.com.hitlist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {

    MyRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setTitle(R.string.titleSearch);
        handleIntent(getIntent());

        // data to populate the RecyclerView with
        ArrayList<Movie> data = new ArrayList<>();
        data.add(new Movie("The Irishman", "World War II veteran and mob hitman Frank \"The Irishman\" Sheeran recalls his possible involvement with the slaying of union leader Jimmy Hoffa."));
        data.add(new Movie("title2", "description2"));
        data.add(new Movie("title3", "description3"));
        data.add(new Movie("title4", "description4"));


        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvSearch);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, data);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        //Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, FilmDescriptionActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_search_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionMovies:
                Toast.makeText(this, "Movies filter", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.actionPeople:
                Toast.makeText(this, "People filter", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.actionTV:
                Toast.makeText(this, "TV filter", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.actionCompanies:
                Toast.makeText(this, "Companies filter", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
        Toast.makeText(this, "new intent", Toast.LENGTH_LONG).show();
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
            setTitle(getString(R.string.titleSearchQuery, query));
        }
    }


}
