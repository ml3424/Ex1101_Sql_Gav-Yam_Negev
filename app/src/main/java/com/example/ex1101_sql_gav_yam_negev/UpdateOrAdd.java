package com.example.ex1101_sql_gav_yam_negev;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class UpdateOrAdd extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_or_add);
    }
    /**
     * click the button will take the user to add a new worker
     * mode 0 = add
     * mode 1 = update
     * @param	view button add worker
     */
    public void add_worker(View view) {
        Intent si = new Intent(this,InputWorker.class);
        si.putExtra("mode",0);
        startActivity(si);
    }
    /**
     * click the button will take the user to update a worker
     * mode 0 = add
     * mode 1 = update
     * @param	view View
     */
    public void update_worker(View view) {
        Intent si = new Intent(this,InputWorker.class);
        si.putExtra("mode",1);
        startActivity(si);
    }
    /**
     * click the button will take the user to make a new order screen (input meal)
     * @param	view View
     */
    public void make_new_order(View view) {
        Intent si = new Intent(this,InputMeal.class);
        startActivity(si);
    }
    /**
     * click the button will take the user to add a new food company
     * mode 0 = add
     * mode 1 = update
     * @param	view View
     */
    public void add_food_company(View view) {
        Intent si = new Intent(this,InputCompany.class);
        si.putExtra("mode",0);
        startActivity(si);
    }
    /**
     * click the button will take the user to update food company details
     * mode 0 = add
     * mode 1 = update
     * @param	view View
     */
    public void update_fc_details(View view) {
        Intent si = new Intent(this,InputCompany.class);
        si.putExtra("mode",1);
        startActivity(si);
    }
    /**
     * click the button will take the user to the main menu
     * @param	view View
     */
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