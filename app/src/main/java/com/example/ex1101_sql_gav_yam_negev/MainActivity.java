package com.example.ex1101_sql_gav_yam_negev;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
/**
 * @author Maya Leibovich
 * @version	1.4
 * @since 18.4.24
 * SQL project
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /**
     * click the button will take the user to the update mode
     * @param	view View
     */
    
    public void gotoUpdateMenu(View view) {
        Intent si = new Intent(this,UpdateOrAdd.class);
        startActivity(si);
    }
    /**
     * click the button will take the user to the show mode
     * @param	view View
     */
    public void gotoDetailsMenu(View view) {
        Intent si = new Intent(this,ShowDetailsMenu.class);
        startActivity(si);
    }

    /**
     * creats menu
     * @param	menu Menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * handles menu item selection
     * @param	item MenuItem
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        String st = item.getTitle().toString();
        if(st.equals("Credits"))
        {
            Intent creditesIntent = new Intent(this, Credits.class);
            startActivity(creditesIntent);
        }
        return super.onOptionsItemSelected(item);
    }
}