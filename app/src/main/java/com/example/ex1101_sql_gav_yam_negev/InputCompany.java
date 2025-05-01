package com.example.ex1101_sql_gav_yam_negev;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;


public class InputCompany extends AppCompatActivity {
    int mode,key;
    String company_name, company_id, phone_number, second_phone_number;
    static boolean firstStep = true;;

    LinearLayout serviceMode,phoneLL, secondPhoneLL;
    TextView title;
    EditText company_name_field, company_id_field, phone_number_field, second_phone_number_field;
    Button saveAndContinue;
    Switch workMode;

    AlertDialog.Builder adb;

    SQLiteDatabase db;
    HelperDB hlp;
    Cursor crsr;
    ArrayList<String> company_id_tb = new ArrayList<>();
    ArrayList<String> company_name_tb = new ArrayList<>();
    String[] columns = {"COMPANY_NAME","COMPANY_NUMBER"};
    String selectionId = FoodCompany.COMPANY_NUMBER+"=?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_company);
        firstStep = true;

        serviceMode = findViewById(R.id.serviceLL);
        phoneLL = findViewById(R.id.phone_numberLL);
        secondPhoneLL = findViewById(R.id.second_phoneLL);

        company_id_field = findViewById(R.id.Company_id);
        company_name_field = findViewById(R.id.Company_name);
        phone_number_field = findViewById(R.id.phone_num);
        second_phone_number_field = findViewById(R.id.second_num);

        workMode = findViewById(R.id.switch2);
        saveAndContinue = findViewById(R.id.saveAndContinueButtonFC);

        title = findViewById(R.id.titleUpdateCompany);
        hlp = new HelperDB(this);
        db = hlp.getWritableDatabase();
        db.close();

        Intent gi = getIntent();
        mode = gi.getIntExtra("mode",-1);
        serviceMode.setVisibility(View.INVISIBLE);
        if (mode == 0){
               title.setText("add a new food company:");
        }else if (mode == 1){
            title.setText("edit food company details:");
            phoneLL.setVisibility(View.INVISIBLE);
            secondPhoneLL.setVisibility(View.INVISIBLE);
            saveAndContinue.setText("next");
        }
    }
    /**
     * check if the company Id and company Name are exist in the database
     * @param	companyId Description	String personal Id
     * @param companyName Description String card Id
     */
    public int isAlreadyExist(String companyId, String companyName){
        int good = 0;
        company_id_tb = new ArrayList<>();
        company_name_tb = new ArrayList<>();
        db = hlp.getWritableDatabase();
        crsr = db.query(FoodCompany.TABLE_FOOD_COMPANY, columns, null, null, null, null, null);
        int col2 = crsr.getColumnIndex(FoodCompany.COMPANY_NAME);
        int col1 = crsr.getColumnIndex(FoodCompany.COMPANY_NUMBER);
        crsr.moveToFirst();
        while (!crsr.isAfterLast()) {
            String company_id1 = crsr.getString(col1);
            String company_name1 = crsr.getString(col2);
            company_id_tb.add(company_id1);
            company_name_tb.add(company_name1);
            crsr.moveToNext();
        }
        crsr.close();
        db.close();
        for (int i = 0;i<company_id_tb.size();i++){
            if (companyId.equals(company_id_tb.get(i))){
                good++;
            }
        }
        for (int i = 0;i<company_name_tb.size();i++){
            if (companyName.equals(company_name_tb.get(i))){
                good++;
            }
        }
        return good;
    }
    /**
     * read a line from the database by existing food company id
     * @param	id Description	String food company id
     * @return  String[]result Description include all the details  [KEY_ID, COMPANY_NUMBER, COMPANY_NAME, C_FIRST_PHONE_NUMBER, C_SECOND_PHONE_NUMBER, IS_WORKING_COMPANY, IS_WORKING_COMPANY]
     */
    public String[] readById(String id){
        String[]selectionArg = {id};
        String[]result = new String[6];
        db=hlp.getReadableDatabase();
        crsr = db.query(FoodCompany.TABLE_FOOD_COMPANY, null, selectionId, selectionArg, null, null, null);
        int col = crsr.getColumnIndex(FoodCompany.KEY_ID_FoodC);
        int col1 =crsr.getColumnIndex(FoodCompany.COMPANY_NUMBER);
        int col2 = crsr.getColumnIndex(FoodCompany.COMPANY_NAME);
        int col3 = crsr.getColumnIndex(FoodCompany.C_FIRST_PHONE_NUMBER);
        int col4 = crsr.getColumnIndex(FoodCompany.C_SECOND_PHONE_NUMBER);
        int col5 = crsr.getColumnIndex(FoodCompany.IS_WORKING_COMPANY);
        crsr.moveToFirst();
        result[0] = String.valueOf(crsr.getInt(col));
        result[1] = crsr.getString(col1);
        result[2] = crsr.getString(col2);
        result[3] = crsr.getString(col3);
        result[4] = crsr.getString(col4);
        result[5] = String.valueOf(crsr.getInt(col5));
        crsr.close();
        db.close();
        return result;
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
     * save the data of a new food company on the database
     */
    public void save_data() {
        company_id = company_id_field.getText().toString();
        company_name = company_name_field.getText().toString();
        phone_number = phone_number_field.getText().toString();
        second_phone_number = second_phone_number_field.getText().toString();
        if (check_inputs()) {
            if (mode == 0) {
                ContentValues cv = new ContentValues();
                cv.put(FoodCompany.COMPANY_NAME,company_name);
                cv.put(FoodCompany.COMPANY_NUMBER,company_id);
                cv.put(FoodCompany.C_FIRST_PHONE_NUMBER,phone_number);
                cv.put(FoodCompany.C_SECOND_PHONE_NUMBER,second_phone_number);
                cv.put(FoodCompany.IS_WORKING_COMPANY,1);
                db = hlp.getWritableDatabase();

                db.insert(FoodCompany.TABLE_FOOD_COMPANY, null, cv);

                db.close();
                finish();
            }

        } else {
            popErrorMassage();
        }
    }
    /**
     * save all the updated info of a food company by removing there line and rewrite the info
     */
    public void update_food_company_details(){
        int isWorking;
        company_id = company_id_field.getText().toString();
        company_name = company_name_field.getText().toString();
        phone_number = phone_number_field.getText().toString();
        second_phone_number = second_phone_number_field.getText().toString();
        if (workMode.isChecked()){
            isWorking = 0;
        }else{
            isWorking = 1;
        }
        if (company_name.length() == 0 || phone_number.length() == 0 || phone_number.equals(second_phone_number)){
            popErrorMassage();
            return;
        }
        db = hlp.getWritableDatabase();
        db.delete(FoodCompany.TABLE_FOOD_COMPANY, FoodCompany.KEY_ID_FoodC +"=?", new String[]{Integer.toString(key)});
        db.close();

        ContentValues cv = new ContentValues();
        cv.put(FoodCompany.COMPANY_NAME,company_name);
        cv.put(FoodCompany.COMPANY_NUMBER,company_id);
        cv.put(FoodCompany.C_FIRST_PHONE_NUMBER,phone_number);
        cv.put(FoodCompany.C_SECOND_PHONE_NUMBER,second_phone_number);
        cv.put(FoodCompany.IS_WORKING_COMPANY,isWorking);
        db = hlp.getWritableDatabase();

        db.insert(FoodCompany.TABLE_FOOD_COMPANY, null, cv);

        db.close();
        finish();

    }
    /**
     * check if the program can save data in the database.
     * by checking the length of each parameter is bigger then 0 and the id  and/ or company name is possible
     * @return true/false Description false - cannot save, true - can save.
     */
    public boolean check_inputs(){
        if (company_id.length() == 0 || company_name.length() == 0 || phone_number.length() == 0 || isAlreadyExist(company_id,company_name) !=0 || phone_number.equals(second_phone_number)){
            return false;
        }
        return true;
    }
    /**
     * back to menu
     * @param view button
     */
    public void back_to_main_menu(View view) {
        finish();
    }

    /**
     * for each mode it is changing - mode 0 - add food company -
     * on click it will begin the saving posses and jump to save_data() function
     * if the code is run on mode 1 - update food company details - the process dividing to two parts:
     * part one is enter only the id and the name.
     * second part is show the user all the current food company data in the right place and save when he click save.
     */
    public void saveWorker(View view) {
        if (mode == 0){
            save_data();
        }else if(mode == 1){
            if(firstStep){
                company_id = company_id_field.getText().toString();
                company_name = company_name_field.getText().toString();
                if (isAlreadyExist(company_id,company_name) == 2){
                    String[]details = readById(company_id);
                    if (details[2].equals(company_name)){
                        phoneLL.setVisibility(View.VISIBLE);
                        secondPhoneLL.setVisibility(View.VISIBLE);
                        serviceMode.setVisibility(View.VISIBLE);
                        phone_number_field.setText(details[3]);
                        second_phone_number_field.setText(details[4]);
                        int wM = Integer.parseInt(details[5]);
                        if (wM == 1){
                            workMode.setChecked(false);
                        }else{
                            workMode.setChecked(true);
                        }
                        key = Integer.parseInt(details[0]);
                        company_id_field.setFocusable(false);
                        saveAndContinue.setText("Save");
                        firstStep = false;
                    }else{
                        popErrorMassage();
                    }
                }else{
                    popErrorMassage();
                }
            }else{
                update_food_company_details();
            }
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