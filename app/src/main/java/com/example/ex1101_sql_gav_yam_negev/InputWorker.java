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

public class InputWorker extends AppCompatActivity {
    LinearLayout workModeLL, firstNameLL, lastNameLL, compenyLL, phoneLL;
    TextView titlePage;
    Button saveAndContinue;
    Switch workMode;
    EditText pId,cId, firstNameField,lastNameField,comp,phone_numberField;

    AlertDialog.Builder adb;

    SQLiteDatabase db;
    HelperDB hlp;
    Cursor crsr;
    ArrayList<String> personal_id_tb = new ArrayList<>();
    ArrayList<String> card_id_tb = new ArrayList<>();
    String[] columns = {"PERSONAL_ID","CARD_ID"};
    String selectionId = Workers.PERSONAL_ID+"=?";

    int key;

    static int mode;
    static boolean fistStep = true;
    static String personal_id,card_id,first_name,last_name, worker_company,phone_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_worker);

        fistStep = true;
        hlp = new HelperDB(this);
        db = hlp.getWritableDatabase();
        db.close();

        workModeLL = findViewById(R.id.workingLL);
        firstNameLL = findViewById(R.id.firstNameLL);
        lastNameLL = findViewById(R.id.lastNameLL);
        compenyLL = findViewById(R.id.compenyLL);
        phoneLL = findViewById(R.id.phoneLL);

        titlePage = findViewById(R.id.titleUpdateWorkers);
        saveAndContinue = findViewById(R.id.saveAndContinueButton);
        workMode = findViewById(R.id.switch1);

        pId = findViewById(R.id.Personal_id);
        cId = findViewById(R.id.card_id);
        firstNameField = findViewById(R.id.first_name);
        lastNameField = findViewById(R.id.last_name);
        comp = findViewById(R.id.compeny);
        phone_numberField = findViewById(R.id.Phone);

        Intent gi = getIntent();
        mode = gi.getIntExtra("mode",-1);
        workModeLL.setVisibility(View.INVISIBLE);
        if (mode == 0){
            titlePage.setText("add a new worker:");
        }else if (mode == 1){
            titlePage.setText("edit worker details:");
            firstNameLL.setVisibility(View.INVISIBLE);
            lastNameLL.setVisibility(View.INVISIBLE);
            compenyLL.setVisibility(View.INVISIBLE);
            phoneLL.setVisibility(View.INVISIBLE);
            saveAndContinue.setText("next");
        }
    }


    /**
     * check if the personal id and card id are exist in the database
     * @param	personalId Description	String personal Id
     * @param cardId Description String card Id
     */
    public int isAlreadyExist(String personalId, String cardId){
        int good = 0;
        personal_id_tb = new ArrayList<>();
        card_id_tb = new ArrayList<>();
        db = hlp.getWritableDatabase();
        crsr = db.query(Workers.TABLE_WORKERS, columns, null, null, null, null, null);
        int col2 = crsr.getColumnIndex(Workers.CARD_ID);
        int col1 = crsr.getColumnIndex(Workers.PERSONAL_ID);
        crsr.moveToFirst();
        while (!crsr.isAfterLast()) {
            String id = crsr.getString(col1);
            String cId = crsr.getString(col2);
            personal_id_tb.add(id);
            card_id_tb.add(cId);
            crsr.moveToNext();
        }
        crsr.close();
        db.close();
        for (int i = 0;i<personal_id_tb.size();i++){
            if (personalId.equals(personal_id_tb.get(i))){
                good++;
            }
        }
        for (int i = 0;i<card_id_tb.size();i++){
            if (cardId.equals(card_id_tb.get(i))){
                good++;
            }
        }
        return good;
    }
    /**
     * read a line from the database by existing personal id
     * @param	id Description	String personal Id
     * @return  String[]result Description include all the details  [KEY_ID, PERSONAL_ID, CARD_ID, FIRST_NAME, LAST_NAME, WORKER_COMPENY, PHONE_NUMBER, IS_WORKING]
     */
    public String[] readById(String id){
        String[]selectionArg = {id};
        String[]result = new String[8];
        db=hlp.getReadableDatabase();
        crsr = db.query(Workers.TABLE_WORKERS, null, selectionId, selectionArg, null, null, null);
        int col = crsr.getColumnIndex(Workers.KEY_ID);
        int col1 = crsr.getColumnIndex(Workers.PERSONAL_ID);
        int col2 = crsr.getColumnIndex(Workers.CARD_ID);
        int col3 = crsr.getColumnIndex(Workers.NAME);
        int col4 = crsr.getColumnIndex(Workers.LAST_NAME);
        int col5 = crsr.getColumnIndex(Workers.WORKER_COMPANY);
        int col6 = crsr.getColumnIndex(Workers.PHONE_NUMBER);
        int col7 = crsr.getColumnIndex(Workers.IS_WORKING);
        crsr.moveToFirst();
        result[0] = String.valueOf(crsr.getInt(col));
        result[1] = crsr.getString(col1);
        result[2] = crsr.getString(col2);
        result[3] = crsr.getString(col3);
        result[4] = crsr.getString(col4);
        result[5] = crsr.getString(col5);
        result[6] = crsr.getString(col6);
        result[7] = String.valueOf(crsr.getInt(col7));
        crsr.close();
        db.close();
        return result;
    }
    /**
     * save the data of a new worker on the database
     */
    public void save_data(){
        personal_id = pId.getText().toString();
        card_id = cId.getText().toString();
        first_name = firstNameField.getText().toString();
        last_name = lastNameField.getText().toString();
        worker_company = comp.getText().toString();
        phone_number = phone_numberField.getText().toString();
        if (check_inputs(personal_id, card_id, first_name, last_name,worker_company)){
            if (mode == 0){
                ContentValues cv = new ContentValues();
                cv.put(Workers.CARD_ID,card_id);
                cv.put(Workers.LAST_NAME,last_name);
                cv.put(Workers.NAME,first_name);
                cv.put(Workers.WORKER_COMPANY,worker_company);
                cv.put(Workers.PERSONAL_ID,personal_id);
                cv.put(Workers.PHONE_NUMBER,phone_number);
                cv.put(Workers.IS_WORKING,1);
                db = hlp.getWritableDatabase();

                db.insert(Workers.TABLE_WORKERS, null, cv);

                db.close();
                finish();
            }

        }else {
            popErrorMassage();
        }


    }
    /**
     * check if the program can save data in the database.
     * by checking the length of each parameter is bigger then 0 and the id is possible (by the israeli format)

     * @param	id Description	String personal Id
     * @param	cId Description	String card id
     * @param	fn Description	String fist name
     * @param	ln Description	String last name
     * @param	wc Description	String worker company
     * @return true/false Description false - cannot save, true - can save.
     */
    public boolean check_inputs(String id, String cId, String fn, String ln, String wc){
        if (id.length() == 0 || !check_id(id) || cId.length() == 0 || fn.length() == 0 || ln.length() == 0 || wc.length() == 0 || isAlreadyExist(id,cId) !=0){
            return false;
        }
        return true;
    }
    /**
     * checking the id by the israeli format
     * @param	id_num Description	String personal Id
     * @return  true/false true - ok id, false - not israeli id
     */
    public boolean check_id(String id_num){
        if (id_num.length()<9){
            String zero ="";
            for (int i=0;i>id_num.length()-9;i--){
                zero+="0";
            }
            id_num = zero+id_num;
        }
        int counter = 0;
        int counter2 = 0;
        int num = 1;
        int last_digit;
        for (int i =0;i<id_num.length();i++){
            int current_num =Character.getNumericValue(id_num.charAt(i));
            current_num *= num;
            if (num == 1){
                num = 2;
            }else{
                num = 1;
            }
            if (current_num >9){
                current_num = (current_num/10)+(current_num%10);
            }
            counter+=current_num;
            if (i != id_num.length()-1){
                counter2+=current_num;
            }
        }
        if (10-(counter2%10) != 10){
            last_digit = 10-(counter2%10);
        }else{
            last_digit = 0;
        }

        if (last_digit == (int) Character.getNumericValue(id_num.charAt(id_num.length()-1))){
            if (counter%10 == 0){
                return true;
            }
            return false;
        }else{
            return false;
        }
    }
    /**
     * save all the updated info of a worker by removing there line and rewrite the info
     */
    public void update_worker_details(){
        int isWorking;
        personal_id = pId.getText().toString();
        card_id = cId.getText().toString();
        first_name = firstNameField.getText().toString();
        last_name = lastNameField.getText().toString();
        worker_company = comp.getText().toString();
        phone_number = phone_numberField.getText().toString();
        if (workMode.isChecked()){
            isWorking = 0;
        }else{
            isWorking = 1;
        }
        if ( first_name.length() == 0 || last_name.length() == 0 || worker_company.length() == 0){
            popErrorMassage();
            return;
        }
        db = hlp.getWritableDatabase();
        db.delete(Workers.TABLE_WORKERS, Workers.KEY_ID+"=?", new String[]{Integer.toString(key)});
        db.close();

        ContentValues cv = new ContentValues();
        cv.put(Workers.CARD_ID,card_id);
        cv.put(Workers.LAST_NAME,last_name);
        cv.put(Workers.NAME,first_name);
        cv.put(Workers.WORKER_COMPANY,worker_company);
        cv.put(Workers.PERSONAL_ID,personal_id);
        cv.put(Workers.PHONE_NUMBER,phone_number);
        cv.put(Workers.IS_WORKING,isWorking);
        db = hlp.getWritableDatabase();

        db.insert(Workers.TABLE_WORKERS, null, cv);

        db.close();
        finish();
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
     * for each mode it is changing - mode 0 - add worker -
     * on click it will begin the saving posses and jump to save_data() function
     * if the code is run on mode 1 - update worker details - the process dividing to two parts:
     * part one is enter only the card and personal ids.
     * second part is show the user all the current user data in the right place and save when he click save
     * @param	view Description	button
     */
    public void saveWorker(View view) {
        if (mode == 0){
            save_data();
        }else if (mode == 1){
            if (fistStep){
                personal_id = pId.getText().toString();
                card_id = cId.getText().toString();
                if (isAlreadyExist(personal_id,card_id) == 2){
                    String[]details = readById(personal_id);
                    if (details[2].equals(card_id)){
                        firstNameLL.setVisibility(View.VISIBLE);
                        lastNameLL.setVisibility(View.VISIBLE);
                        compenyLL.setVisibility(View.VISIBLE);
                        phoneLL.setVisibility(View.VISIBLE);
                        workModeLL.setVisibility(View.VISIBLE);
                        firstNameField.setText(details[3]);
                        lastNameField.setText(details[4]);
                        comp.setText(details[5]);
                        phone_numberField.setText(details[6]);
                        int wM = Integer.parseInt(details[7]);
                        if (wM == 1){
                            workMode.setChecked(false);
                        }else{
                            workMode.setChecked(true);
                        }
                        pId.setFocusable(false);
                        cId.setFocusable(false);
                        key = Integer.parseInt(details[0]);
                        saveAndContinue.setText("save");
                        fistStep = false;
                    }else{
                        popErrorMassage();
                    }
                }else{
                    popErrorMassage();
                }
            }else{
                update_worker_details();
            }

        }

    }
    /**
     * back to menu
     * @param view button
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