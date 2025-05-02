package com.example.ex1101_sql_gav_yam_negev;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class InputMeal extends AppCompatActivity {
    String appetizer,main_course,extra,dessert,drink;
    EditText app,mC,ex,des,dri;
    AlertDialog.Builder adb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_meal);

        app = findViewById(R.id.appetizerF);
        mC = findViewById(R.id.main_courseF);
        ex = findViewById(R.id.extraF);
        des = findViewById(R.id.dessertF);
        dri = findViewById(R.id.drinkF);
    }

    /**
     * back to last screen
     * @param view View
     */
    public void back_to_main_menu(View view) {
        finish();
    }

    /**
     * check if the user entered
     */
    public boolean check_input(){
        if (main_course.length() == 0){
            return false;
        }
        return true;

    }
    /**
     * pop error alert dialog massage to the user
     */
    public void popErrorMassage(){
        adb = new AlertDialog.Builder(this);
        adb.setCancelable(false);
        adb.setTitle("wrong input!");
        adb.setMessage("you entered wrong input to one or more of the input fields");
        adb.setNegativeButton("close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                dialog.dismiss();
            }
        });
        AlertDialog ad = adb.create();
        ad.show();

    }
    /**
     * continue to the next input screen.
     * in addition it move the last information to the next screen to continue the
     * order (input more details).
     */
    public void continueToNextPart(View view) {
        appetizer = app.getText().toString();
        main_course = mC.getText().toString();
        extra = ex.getText().toString();
        dessert = des.getText().toString();
        drink = dri.getText().toString();
        if (check_input()){
            Intent si = new Intent(this, CompleteOrder.class);
            si.putExtra("appetizer",appetizer);
            si.putExtra("main_course",main_course);
            si.putExtra("extra",extra);
            si.putExtra("dessert",dessert);
            si.putExtra("drink",drink);
            startActivity(si);
        }else{
            popErrorMassage();
        }
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