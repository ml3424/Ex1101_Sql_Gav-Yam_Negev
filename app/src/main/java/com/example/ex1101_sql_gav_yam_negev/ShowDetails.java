package com.example.ex1101_sql_gav_yam_negev;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class ShowDetails extends AppCompatActivity implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {
    static int mode, spinner_pos;
    static ArrayList<String> userOptions =new ArrayList<>();

    Spinner optionList;
    LinearLayout order, moreDetails;
    TextView title, sub_info_field;
    ListView screen_list;
    Switch order_pointer;

    SQLiteDatabase db;
    HelperDB hlp;
    static Cursor crsr;
    static ArrayList<String> tbl = new ArrayList<>();
    static ArrayList<String> sub_tbl = new ArrayList<>();
    static ArrayList<Integer>d1 =new ArrayList<>();
    static ArrayList<String>d1_help =new ArrayList<>();
    static ArrayList<Integer>d2 =new ArrayList<>();
    static ArrayList<String>d2_help =new ArrayList<>();
    static ArrayList<String>d3 =new ArrayList<>();
    static ArrayList<Integer> d3_index_helper =new ArrayList<>();
    static ArrayList<String>d3helper_copy =new ArrayList<>();
    static ArrayList<String>empty =new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);

        optionList = findViewById(R.id.sort_list_options);
        order = findViewById(R.id.orderLyaut);
        moreDetails = findViewById(R.id.moreDetails);
        title = findViewById(R.id.titleShowDetails);
        sub_info_field = findViewById(R.id.f1);
        screen_list = findViewById(R.id.show_filed);
        order_pointer = findViewById(R.id.switch_order);

        hlp = new HelperDB(this);
        db = hlp.getWritableDatabase();
        db.close();
        empty.add("");

        Intent gi = getIntent();
        mode = gi.getIntExtra("mode",-1);
        connect_spinner_values();


        order.setVisibility(View.INVISIBLE);
        moreDetails.setVisibility(View.INVISIBLE);
        tbl.clear();
        sub_tbl.clear();
        d1.clear();
        d2.clear();
        d3.clear();
        d1_help.clear();
        d2_help.clear();
        d3_index_helper.clear();
        d3helper_copy.clear();
        if (mode == 0){
            Log.e("r","r");
            title.setText("show details of: worker");
            db = hlp.getWritableDatabase();
            crsr = db.query(Workers.TABLE_WORKERS, null, null, null, null, null, null);
            int col1 = crsr.getColumnIndex(Workers.CARD_ID);
            int col2 = crsr.getColumnIndex(Workers.LAST_NAME);
            int col3 = crsr.getColumnIndex(Workers.NAME);
            int col4 = crsr.getColumnIndex(Workers.WORKER_COMPANY);
            int col5 = crsr.getColumnIndex(Workers.PERSONAL_ID);
            int col6 = crsr.getColumnIndex(Workers.PHONE_NUMBER);
            int col7 = crsr.getColumnIndex(Workers.IS_WORKING);
            crsr.moveToFirst();
            int index = 0;
            while (!crsr.isAfterLast()) {
                String details;
                String sub_details;
                String card = crsr.getString(col1);
                String last_name =  crsr.getString(col2);
                String first_name =  crsr.getString(col3);
                String worker_company =  crsr.getString(col4);
                String personal_id =  crsr.getString(col5);
                String phone_number =  crsr.getString(col6);
                int work_mode =  crsr.getInt(col7);
                if (work_mode == 1){
                    details = ""+personal_id+", "+first_name+", "+last_name+", "+worker_company;
                    sub_details = ""+card+", "+phone_number+", working";
                    Log.e("activity_show_details",String.valueOf(index));
                    d1.add(index);
                    d1_help.add(sub_details);
                }else{
                   details = ""+personal_id+", "+first_name+" "+last_name+", "+worker_company;
                    sub_details = ""+card+", "+phone_number+", not working";
                    d2.add(index);
                    d2_help.add(sub_details);
                }
                index++;
                tbl.add(details);
                sub_tbl.add(sub_details);
                d3.add(first_name+" "+last_name);
                crsr.moveToNext();
            }
            crsr.close();
            db.close();
            d3helper_copy = d3;
            System.out.println(d1_help);
        }
        else if (mode == 1){
            title.setText("show details of: orders");
            Log.e("g","g");
            db = hlp.getWritableDatabase();
            crsr = db.query(Order_Details.TABLE_ORDER_DETAILS, null, null, null, null, null, null);
            int col1 = crsr.getColumnIndex(Order_Details.KEY_ID_OD);
            int col2 = crsr.getColumnIndex(Order_Details.DATE);
            int col3 = crsr.getColumnIndex(Order_Details.TIME);
            int col4 = crsr.getColumnIndex(Order_Details.WORKER_ID);
            int col5 = crsr.getColumnIndex(Order_Details.FOOD_COMPANY);
            crsr.moveToFirst();
            while (!crsr.isAfterLast()) {
                String details;
                int id = crsr.getInt(col1);
                String date =  crsr.getString(col2);
                String time = crsr.getString(col3);
                String worker_id = crsr.getString(col4);
                String food_company = crsr.getString(col5);
                tbl.add(String.valueOf(id));
                d1_help.add(food_company);
                d2_help.add(time.substring(0,2));
                details = ""+date+", "+time+", "+worker_id+", "+food_company;
                tbl.add(details);
                crsr.moveToNext();
            }
            crsr.close();
            db.close();
            db = hlp.getWritableDatabase();
            crsr = db.query(Meals.TABLE_MEALS, null, null, null, null, null, null);
            col1 = crsr.getColumnIndex(Meals.APPETIZER);
            col2 = crsr.getColumnIndex(Meals.MAIN_COURSE);
            col3 = crsr.getColumnIndex(Meals.EXTRA);
            col4 = crsr.getColumnIndex(Meals.DESSERT);
            col5 = crsr.getColumnIndex(Meals.DRINK);
            crsr.moveToFirst();
            while (!crsr.isAfterLast()) {
                String sub_details;
                String appetizer = crsr.getString(col1);
                String main_course = crsr.getString(col2);
                String extra = crsr.getString(col3);
                String dessert = crsr.getString(col4);
                String drink = crsr.getString(col5);
                sub_details = ""+appetizer+", "+main_course+", "+extra+", "+dessert+", "+drink;
                sub_tbl.add(sub_details);
                crsr.moveToNext();
            }
            crsr.close();
            db.close();
        }else if (mode == 2){
            title.setText("show details of: food companies");
            db = hlp.getWritableDatabase();
            crsr = db.query(FoodCompany.TABLE_FOOD_COMPANY, null, null, null, null, null, null);
            int col1 = crsr.getColumnIndex(FoodCompany.COMPANY_NUMBER);
            int col2 = crsr.getColumnIndex(FoodCompany.COMPANY_NAME);
            int col3 = crsr.getColumnIndex(FoodCompany.C_FIRST_PHONE_NUMBER);
            int col4 = crsr.getColumnIndex(FoodCompany.C_SECOND_PHONE_NUMBER);
            int col5 = crsr.getColumnIndex(FoodCompany.IS_WORKING_COMPANY);
            crsr.moveToFirst();
            int index = 0;
            while (!crsr.isAfterLast()) {
                String details;
                String sub_details;
                String company_number = crsr.getString(col1);
                String company_name =  crsr.getString(col2);
                String phone_number =  crsr.getString(col3);
                String second_phone_number =  crsr.getString(col4);
                int work_mode =  crsr.getInt(col5);
                details = ""+company_number+", "+company_name;
                if (work_mode == 1){
                    sub_details = ""+phone_number+", "+second_phone_number+", working";
                    d1.add(index);
                    d1_help.add(sub_details);
                }else {
                    sub_details = ""+phone_number+", "+second_phone_number+", not working";
                    d2.add(index);
                    d2_help.add(sub_details);
                }
                index++;
                tbl.add(details);
                sub_tbl.add(sub_details);
                d3.add(company_name);
                crsr.moveToNext();
            }
            crsr.close();
            db.close();
            d3helper_copy = d3;



        }

        optionList.setOnItemSelectedListener(this);
        ArrayAdapter<String> adp_spinner = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, userOptions);
        optionList.setAdapter(adp_spinner);

        screen_list.setOnItemClickListener(this);
        screen_list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        ArrayAdapter<String> adp_list = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, tbl);
        screen_list.setAdapter(adp_list);

    }
    /**
     * make to the spinner the list of options to the user.
     */
    public static void connect_spinner_values(){
        userOptions.clear();
        userOptions.add("all:");
        if (mode == 0){
            userOptions.add("Show only active employees");
            userOptions.add("Show only inactive employees");
            userOptions.add("sort by name");
        }else if (mode == 1){
            userOptions.add("by time frame");
            userOptions.add("company");
        }else if (mode == 2){
            userOptions.add("Show only active food companies");
            userOptions.add("Show only inactive food companies");
            userOptions.add("sort by name");
        }
    }
    /**
     * mirror the array list.
     * @param arr
     * @return ArrayList<String> reverse arr.
     */
    public static ArrayList<String> mirror(ArrayList<String> arr){
        ArrayList<String> rev = new ArrayList<String>();
        for (int i = arr.size()-1;i>=0;i--){
            rev.add(arr.get(i));
        }
        return rev;
    }
    /**
     * sort an array by popularity of String.
     * @param arr array of all the elements.
     * @return ArrayList<String> array of all the elements by popularity of their appearance
     * in the array
     */
    public static ArrayList<String> sortList2(ArrayList<String>arr){
        ArrayList<String> arrElements = new ArrayList<String>();
        ArrayList<String> arr2 = new ArrayList<String>();
        ArrayList<Integer> arr3 = new ArrayList<Integer>();

        for (int i =0;i<arr.size();i++){
            if (arrElements.isEmpty()){
                arrElements.add(arr.get(i));
            }else if (!arrElements.contains(arr.get(i))){
                arrElements.add(arr.get(i));
            }
        }
        System.out.println(arrElements);
        for (int i =0;i<arrElements.size();i++){
            String current = arrElements.get(i);
            int occurrences = Collections.frequency(arr, current);
            arr3.add(occurrences);
        }
        System.out.println(arr3);
        while (!arrElements.isEmpty()){
            int biggest = arr3.get(0);
            int index = 0;
            for (int i =0;i<arr3.size();i++){
                if (arr3.get(i) > biggest){
                    biggest = arr3.get(i);
                    index = i;
                }
            }
            arr2.add(arrElements.get(index));
            arr3.remove(index);
            arrElements.remove(index);
        }

        return arr2;
    }

    /**
     * listView on click function.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        System.out.println(sub_tbl);
        moreDetails.setVisibility(View.INVISIBLE);
        Log.e("dsfds",String.valueOf(spinner_pos));
        if (mode == 0 || mode == 2){
            moreDetails.setVisibility(View.VISIBLE);
            if (spinner_pos == 0){
                System.out.println(sub_tbl);

                sub_info_field.setText(sub_tbl.get(position));


            }else if (spinner_pos == 1){
                System.out.println(d1_help);
                sub_info_field.setText(d1_help.get(position));

            }else if (spinner_pos == 2){
                sub_info_field.setText(d2_help.get(position));
            }else{
                sub_info_field.setText(sub_tbl.get(d3_index_helper.get(position)));
            }
        }else if (mode == 1 && spinner_pos == 0){
            moreDetails.setVisibility(View.VISIBLE);
            sub_info_field.setText(sub_tbl.get(position));
        }
    }

    /**
     * on item selected in the spinner.
     * <p>
     *
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ArrayList<String> result =new ArrayList<>();
        order.setVisibility(View.INVISIBLE);
        spinner_pos = position;
        if (mode == 0 || mode == 2){
            if (position == 0){
                ArrayAdapter<String> adp_list = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, tbl);
                screen_list.setAdapter(adp_list);
            }else if(position == 1){

                for (int i =0;i<d1.size();i++){
                    result.add(tbl.get(d1.get(i)));
                }
                ArrayAdapter<String> adp_list = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, result);
                screen_list.setAdapter(adp_list);
            }else if(position == 2){
                for (int i =0;i<d2.size();i++){
                    result.add(tbl.get(d2.get(i)));
                }
                ArrayAdapter<String> adp_list = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, result);
                screen_list.setAdapter(adp_list);
            }else{
                order.setVisibility(View.VISIBLE);
                d3helper_copy = new ArrayList<String>(d3);
                Collections.sort(d3);
                if (order_pointer.isChecked()){
                    d3_index_helper.clear();
                    d3 = mirror(d3);
                }
                organize_after_shorting();
                ArrayAdapter<String> adp_list = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, d3);
                screen_list.setAdapter(adp_list);
            }
        }else if(mode == 1){
            if (position == 0){
                for (int i = 0;i<tbl.size();i++){
                    if (i%2 != 0){
                        result.add(tbl.get(i));
                    }
                }
                ArrayAdapter<String> adp_list = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, result);
                screen_list.setAdapter(adp_list);
            }else if (position == 2){
                order.setVisibility(View.VISIBLE);
                result = sortList2(d1_help);
                if (!order_pointer.isChecked()){
                    result = mirror(result);
                }
                ArrayAdapter<String> adp_list = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, result);
                screen_list.setAdapter(adp_list);
            }else if (position == 1){
                order.setVisibility(View.VISIBLE);
                result = return_time_frame(d2_help);
                if (!order_pointer.isChecked()){
                    result = mirror(result);
                }
                ArrayAdapter<String> adp_list = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, result);
                screen_list.setAdapter(adp_list);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    /**
     * after the sorting of d3 the function organize the index list in order to see the right sub-information.
     * <p>
     *
     */
    public static void organize_after_shorting(){
        for (int i =0;i<d3helper_copy.size();i++){
            String current = d3.get(i);
            for (int j=0;j<d3helper_copy.size();j++){
                if (current.equals(d3helper_copy.get(j))){
                    d3_index_helper.add(j);
                }
            }
        }
    }
    /**
     * organize the time frames to show the frames ond not the hour.
     * <p>
     * @param arr not organized array
     * @return organized array
     *
     */
    public static ArrayList<String> return_time_frame(ArrayList<String>arr){
        ArrayList<String> arr2 = new ArrayList<String>();
        arr = sortList2(arr);
        for (int i=0;i<arr.size();i++){
            if (is_number(arr.get(i))){
                arr2.add(arr.get(i)+"<t<"+String.valueOf(Integer.parseInt(arr.get(i))+1));
            }
        }
        return arr2;
    }
    /**
     * check if the input is number.
     * <p>
     * @param num random string
     * @return if the string is a number
     */
    public static boolean is_number(String num){
        try {
            int value = Integer.parseInt(num);
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }
    /**
     * make an action on click in the switch.
     * <p>
     * @param view switch
     */
    public void actionToDiffrence_sw(View view) {
        if ((mode == 0 || mode == 2) && spinner_pos == 3){
            Collections.sort(d3);
            d3_index_helper.clear();
            if (order_pointer.isChecked()){
                d3 = mirror(d3);
            }
            organize_after_shorting();
            ArrayAdapter<String> adp_list = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, d3);
            screen_list.setAdapter(adp_list);
        }else if (mode == 1 && spinner_pos == 2){
            ArrayList<String>result = sortList2(d1_help);
            if (!order_pointer.isChecked()){
                result = mirror(result);
            }
            ArrayAdapter<String> adp_list = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, result);
            screen_list.setAdapter(adp_list);
        }else if (mode == 1 && spinner_pos == 1){
            ArrayList<String>result = return_time_frame(d2_help);
            if (!order_pointer.isChecked()){
                result = mirror(result);
            }
            ArrayAdapter<String> adp_list = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, result);
            screen_list.setAdapter(adp_list);
        }
    }
    /**
     * return to the main menu
     * <p>
     *
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