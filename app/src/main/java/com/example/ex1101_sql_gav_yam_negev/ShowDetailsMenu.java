
package com.example.ex1101_sql_gav_yam_negev;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class ShowDetailsMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details_menu);
    }
    /**
     * click the button will take the user to see all the details of workers
     * mode 0 = show workers details
     * mode 1 = show order details
     * mode 2 = show food company details
     * @param	view Description	button show worker details
     */
    public void show_workers(View view) {
        Intent si = new Intent(this,ShowDetails.class);
        si.putExtra("mode",0);
        startActivity(si);
    }
    /**
     * click the button will take the user to see all the details of orders
     * mode 0 = show workers details
     * mode 1 = show order details
     * mode 2 = show food company details
     * @param	view Description	button show order details
     */
    public void show_orders(View view) {
        Intent si = new Intent(this,ShowDetails.class);
        si.putExtra("mode",1);
        startActivity(si);
    }
    /**
     * click the button will take the user to see all the details of food companies
     * mode 0 = show workers details
     * mode 1 = show order details
     * mode 2 = show food company details
     * @param	view Description	button show food company details
     */
    public void show_food_comp(View view) {
        Intent si = new Intent(this,ShowDetails.class);
        si.putExtra("mode",2);
        startActivity(si);
    }

    public void back_to_main_menu(View view) {
        finish();
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
        else if (st.equals("Main")) {
            Intent mainIntent = new Intent(this, MainActivity.class);
            startActivity(mainIntent);
        }
        return super.onOptionsItemSelected(item);
    }
}