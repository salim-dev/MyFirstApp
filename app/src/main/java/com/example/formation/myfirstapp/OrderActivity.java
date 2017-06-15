package com.example.formation.myfirstapp;


import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class OrderActivity extends AppCompatActivity implements View.OnClickListener {
    int priceUnitCoffee = 5;
    int priceUnitChantillyCoffee = 1;
    int priceUnitChocolate = 5;
    int priceUnitChantillyChocolate = 1;
    int sumOrder = 0;

    int quantityCurrentCoffee = 0;
    int quantityCurrentChocolate = 0;
    int quantityCurrentChantillyCoffee = 0;
    int quantityCurrentChantillyChocolate = 0;


    final int INIT_CHANTILLY_COFFEE_QUANTITIES = 1;
    final int INIT_CHANTILLY_CHOCOLATE_QUANTITIES = 1;


    final int MIN_QUANTITIES = 1;
    final int MAX_QUANTITIES = 9;

    CheckBox checkBoxChantillyCoffee;
    CheckBox checkBoxChantillyChocolate;
    LinearLayout wrapperChantillyCoffee;
    LinearLayout wrapperChantillyChocolate;
    LinearLayout wrapperCheckboxChantillyCoffee;
    LinearLayout wrapperCheckboxChantillyChocolate;

    TextView pseudoTextView;
    TextView orderSumTextView;

    TextView currentQuantityCoffeTextView;
    TextView currentQuantityChantillyCoffeTextView;
    TextView currentQuantityChocolateTextView;
    TextView currentQuantityChantillyChocolateTextView;


    TextView currentSumCoffeTextView;
    TextView currentSumChantillyCoffeTextView;
    TextView currentSumChocolateTextView;
    TextView currentSumChantillyCholocateTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        setTitle("ORDER");

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        this.init();
    }

    /**
     * Insert menu in ActionBar
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    /**
     *  Manage actions click in ActionBar
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_save:
                saveActionOrder();
                return true;
            case R.id.action_import:
                importActionOrder();
                return true;
            case R.id.action_empty:
                emptyActionOrder();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Save order
     */
    private void saveActionOrder(){
        DatabaseHelper db = new DatabaseHelper(this);
        // inserting orders
        db.addOrder(new Order(
        String.valueOf(quantityCurrentCoffee),
        String.valueOf(quantityCurrentChantillyCoffee),
        String.valueOf(quantityCurrentChocolate),
        String.valueOf(quantityCurrentChantillyChocolate),
        String.valueOf(sumOrder)
        ));
        Toast.makeText(this,R.string.action_save,Toast.LENGTH_LONG).show();
    }

    /**
     * Import order
     */
    private void importActionOrder(){
//        Toast.makeText(this,R.string.action_import,Toast.LENGTH_LONG).show();
        Intent intent = new Intent(OrderActivity.this, ImportActivity.class);
        startActivity(intent);
    }

    /**
     * Drop all orders in database
     */
    private void emptyActionOrder(){
        DatabaseHelper db = new DatabaseHelper(this);
        db.deleteAllOrders();
        Toast.makeText(this,R.string.action_empty,Toast.LENGTH_LONG).show();
    }

    /**
     * init order
     */
    private void init(){

        this.initAllTextView();
        this.initAllWrapper();
        this.initAllChecbox();
        this.initAllBtn();
        Intent intent = getIntent();
        String getPseudo = intent.getStringExtra(MainActivity.INTENT_KEY_PSEUDO);
        if (intent.hasExtra(MainActivity.INTENT_KEY_PSEUDO)){
            pseudoTextView.setText(getPseudo);
        }
        if(intent.hasExtra(ImportActivity.INTENT_KEY_ORDER)){
            HashMap<String,Integer> orderHashMap = (HashMap<String,Integer>) intent.getSerializableExtra(ImportActivity.INTENT_KEY_ORDER);
            this.updateImportOrder(orderHashMap);
        }
    }


    /**
     * init all TextView
     */
    private void initAllTextView(){
        orderSumTextView = (TextView) findViewById(R.id.order_text_sum_order);
        pseudoTextView = (TextView) findViewById(R.id.order_text_pseudo);

        currentQuantityCoffeTextView = (TextView) findViewById(R.id.order_text_coffees_quantities);
        currentQuantityChantillyCoffeTextView = (TextView) findViewById(R.id.order_text_quantity_chantilly_for_coffee);

        currentQuantityChocolateTextView = (TextView) findViewById(R.id.order_text_chocolates_quantities);
        currentQuantityChantillyChocolateTextView = (TextView) findViewById(R.id.order_text_quantity_chantilly_for_chocolate);

        currentSumCoffeTextView = (TextView) findViewById(R.id.order_text_sum_coffees);
        currentSumChantillyCoffeTextView = (TextView) findViewById(R.id.order_text_sum_chantilly_for_coffee);

        currentSumChocolateTextView = (TextView) findViewById(R.id.order_text_sum_chocolates);
        currentSumChantillyCholocateTextView = (TextView) findViewById(R.id.order_text_sum_chantilly_for_chocolate);
    }

    /**
     * init all Wrapper in layout order
     */
    private void initAllWrapper(){
        wrapperChantillyCoffee = (LinearLayout) findViewById(R.id.wrapper_order_chantilly_for_coffee);
        wrapperCheckboxChantillyCoffee = (LinearLayout) findViewById(R.id.wrapper_checkbox_chantilly_for_coffee);
        wrapperChantillyChocolate = (LinearLayout) findViewById(R.id.wrapper_order_chantilly_for_chocolate);
        wrapperCheckboxChantillyChocolate = (LinearLayout) findViewById(R.id.wrapper_checkbox_chantilly_for_chocolate);
    }

    /**
     *  init all buttons
     */
    private void initAllBtn(){
        ArrayList<Button> listButtons= new ArrayList<>();

        // buttons for coffees quantities (less and more)
        Button btnQteCoffeeLess = (Button) findViewById(R.id.btn_order_coffees_quantities_less);
        listButtons.add(btnQteCoffeeLess);
        Button btnQteCoffeeMore = (Button) findViewById(R.id.btn_order_coffees_quantities_more);
        listButtons.add(btnQteCoffeeMore);

        // buttons for chantilly for coffee quantities (less and more)
        Button btnQteChantillyCoffeeLess = (Button) findViewById(R.id.btn_order_quantity_chantilly_for_coffee_less);
        listButtons.add(btnQteChantillyCoffeeLess);
        Button btnQteChantillyCoffeeMore = (Button) findViewById(R.id.btn_order_quantity_chantilly_for_coffee_more);
        listButtons.add(btnQteChantillyCoffeeMore);



        // buttons for chocolates quantities (less and more)
        Button btnQteChocolateMore = (Button) findViewById(R.id.btn_order_chocolates_quantities_more);
        listButtons.add(btnQteChocolateMore);
        Button btnQteChocolateLess = (Button) findViewById(R.id.btn_order_chocolates_quantities_less);
        listButtons.add(btnQteChocolateLess);


        // buttons for chantilly for chocolate quantities (less and more)
        Button btnQteChantillyChocolateLess = (Button) findViewById(R.id.btn_order_quantity_chantilly_for_chocolate_less);
        listButtons.add(btnQteChantillyChocolateLess);
        Button btnQteChantillyChocolateMore = (Button) findViewById(R.id.btn_order_quantity_chantilly_for_chocolate_more);
        listButtons.add(btnQteChantillyChocolateMore);

        // loop for listener all buttons in listButtons
        for (Button button: listButtons){
            button.setOnClickListener(this);
        }

        // button order
        Button btnOrder = (Button) findViewById(R.id.btn_order);
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });
    }

    /**
     * It send email
     */
    private void sendEmail() {
        Log.i("Send email", "");

        String[] TO = {"someone@gmail.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");


        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Commande Starbucks");
        emailIntent.putExtra(Intent.EXTRA_TEXT, this.getBodyOrderForEmail());

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished sending email...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(OrderActivity.this,
                    "There is no email otfrt installed.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * prepare the email body
     * @return
     */
    public String getBodyOrderForEmail(){
        String body;
        body = "Hello "+ pseudoTextView.getText().toString() +"\n";
        body += "your ordered\n\n";
        if(quantityCurrentCoffee > 0){
            body += quantityCurrentCoffee + " coffee(s)\n";
            if(quantityCurrentChantillyCoffee > 0){
                body += quantityCurrentChantillyCoffee + " coffee(s) with chantilly\n";
            }

        }
        if(quantityCurrentChocolate > 0){
            body += quantityCurrentChocolate + " chocolate(s)\n";
            if(quantityCurrentChantillyChocolate > 0){
                body += quantityCurrentChantillyChocolate + " chocolate(s) with chantilly\n\n";
            }
        }

        if(sumOrder > 0){
            body += "Total of your order "+ sumOrder +"€";
        }
        return body;
    }
    /**
     * init all checkbox
     */
    private void initAllChecbox(){

        checkBoxChantillyCoffee = (CheckBox) findViewById(R.id.checkbox_order_chantilly_for_coffee);
        checkBoxChantillyChocolate = (CheckBox) findViewById(R.id.checkbox_order_chantilly_for_chocolate);
        checkBoxChantillyCoffee.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
               @Override
               public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {


                    if(isChecked){
                        showWrapperChantilly(wrapperChantillyCoffee,true);
                        quantityCurrentChantillyCoffee = INIT_CHANTILLY_COFFEE_QUANTITIES;

                    }else{
                        showWrapperChantilly(wrapperChantillyCoffee,false);
                        quantityCurrentChantillyCoffee =0;
                    }

                    currentQuantityChantillyCoffeTextView.setText("" + quantityCurrentChantillyCoffee);
                    currentSumChantillyCoffeTextView.setText(""+(quantityCurrentChantillyCoffee*priceUnitChantillyCoffee));
                    updateSumOrder();
               }
           }
        );
        checkBoxChantillyChocolate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
               @Override
               public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                   if(isChecked){
                       showWrapperChantilly(wrapperChantillyChocolate,true);
                       quantityCurrentChantillyChocolate = INIT_CHANTILLY_CHOCOLATE_QUANTITIES;
                   }else{
                       showWrapperChantilly(wrapperChantillyChocolate,false);
                       quantityCurrentChantillyChocolate = 0;
                   }

                   currentQuantityChantillyChocolateTextView.setText(""+quantityCurrentChantillyChocolate);
                   currentSumChantillyCholocateTextView.setText(""+(quantityCurrentChantillyChocolate*priceUnitChantillyChocolate));
                   updateSumOrder();
               }
           }
        );
    }
    /**
     * More quantity in quantityTextView and update sum in priceTextView
     */
    private int moreQuantityForTexView(int currentQuantity,TextView quantityTextView, TextView priceTextView,int price, int limitQuantityMin, int limitQuantityMax){
       int finalCurrentQuantity = currentQuantity ;
        currentQuantity++;

        if(currentQuantity >= limitQuantityMin && currentQuantity <= limitQuantityMax){
            quantityTextView.setText(""+currentQuantity);
            priceTextView.setText(""+(currentQuantity*price));
            finalCurrentQuantity = currentQuantity ;

        }
        return finalCurrentQuantity;
    }


    /**
     * Less quantity in quantityTextView and update sum in priceTextView
     */
    private int lessQuantityForTexView(int currentQuantity,TextView quantityTextView, TextView sumTextView,int price, int limitQuantityMin, int limitQuantityMax){
        int finalCurrentQuantity = currentQuantity ;
        currentQuantity--;

        if(currentQuantity >= limitQuantityMin && currentQuantity <= limitQuantityMax) {
            quantityTextView.setText("" + currentQuantity);
            sumTextView.setText("" + (currentQuantity * price));
            finalCurrentQuantity = currentQuantity;
        }
        this.updateSumOrder();
        return finalCurrentQuantity;
    }

    /**
     * It checks whether chantilly quantities are  upper coffees or chocolates quantities,
     * if so,
     * it reduce less chantilly quantities for equal
     * @param currentChantillyQuantity
     * @param quantityChantillyTextView
     * @param sumChantillyTextView
     * @param price
     * @param limitQuantityChantillyMin
     * @param limitQuantityChantillyMax
     * @param currentQuantityParent
     * @return
     */
    private int lessCheckQuantityForChantillyTexView(int currentChantillyQuantity,TextView quantityChantillyTextView, TextView sumChantillyTextView,int price, int limitQuantityChantillyMin, int limitQuantityChantillyMax, int currentQuantityParent){
        int finalCurrentQuantity = currentChantillyQuantity ;

        if(
           currentChantillyQuantity >= limitQuantityChantillyMin &&
                   currentChantillyQuantity > currentQuantityParent ) {
            currentChantillyQuantity--;
            quantityChantillyTextView.setText("" + currentChantillyQuantity);
            sumChantillyTextView.setText("" + (currentChantillyQuantity * price));
            finalCurrentQuantity = currentChantillyQuantity ;

        }
        return finalCurrentQuantity;
    }

    /**
     * Updat Sum order
     */
    private void updateSumOrder(){
        sumOrder = 0;
        sumOrder += (priceUnitCoffee*quantityCurrentCoffee);
        sumOrder += (priceUnitChantillyCoffee*quantityCurrentChantillyCoffee);
        sumOrder += (priceUnitChocolate*quantityCurrentChocolate);
        sumOrder += (priceUnitChantillyChocolate*quantityCurrentChantillyChocolate);
        orderSumTextView.setText(""+sumOrder);

    }

    /**
     * it checks wether current coffees or chocolates quantities are 0 quantities, if so, it unchecks the checkbox
     * @param CheckboxChantilly
     * @param currentQuantity
     */
    public void checkedCheckboxChantilly(CheckBox CheckboxChantilly,int currentQuantity){

       if(currentQuantity == 0){
           CheckboxChantilly.setChecked(false);
        }
    }

    /**
     *  it checks wether current coffees or chocolates quantities are 0 quantities, if so, it gone wrapper checkbox chantilly in layout else it displays
     * @param wrapperCheckboxChantilly
     * @param currentQuantity
     */
    public void showWrapperCheckboxChantilly(LinearLayout wrapperCheckboxChantilly,int currentQuantity){

       if(currentQuantity == 0){
            wrapperCheckboxChantilly.setVisibility(View.GONE);
        } else if(currentQuantity >= 1){
            wrapperCheckboxChantilly.setVisibility(View.VISIBLE);
        }
    }

    /**
     * it checks wether current coffees or chocolates quantities are 0 quantities, if so, it gone wrapper chantilly quantities  in layout else it displays

     * @param wrapperChantilly
     * @param currentQuantity
     */
    public void showWrapperChantilly(LinearLayout wrapperChantilly,int currentQuantity){
        if(currentQuantity == 0){
            wrapperChantilly.setVisibility(View.GONE);
        }
    }

    /**
     *  True displays wrapper chantilly quantities in layout else it displays or false gone wrapper

     * @param wrapperChantilly
     * @param showWrapper
     */
    public void showWrapperChantilly(LinearLayout wrapperChantilly,boolean showWrapper){
        if(showWrapper){
            wrapperChantilly.setVisibility(View.VISIBLE);
        } else{
            wrapperChantilly.setVisibility(View.GONE);

        }
    }

    /**
     * it update layout order after get back date item in import
     * @param orderHashMap
     */

    public void updateImportOrder(HashMap orderHashMap){


        quantityCurrentCoffee = (int) orderHashMap.get("quantitiesCoffees");
        quantityCurrentChantillyCoffee = (int) orderHashMap.get("quantitiesChantillyCoffees");
        quantityCurrentChocolate = (int) orderHashMap.get("quantitiesChocolates");
        quantityCurrentChantillyChocolate = (int) orderHashMap.get("quantitiesChantillyChocolates");
        sumOrder = (int) orderHashMap.get("sumOrder");
        currentQuantityCoffeTextView.setText(""+quantityCurrentCoffee);
        currentQuantityChantillyCoffeTextView.setText(""+quantityCurrentChantillyCoffee);
        currentQuantityChocolateTextView.setText(""+quantityCurrentChocolate);
        currentQuantityChantillyChocolateTextView.setText(""+quantityCurrentChantillyChocolate);


        currentSumCoffeTextView.setText(""+(quantityCurrentCoffee * priceUnitCoffee));
        currentSumChantillyCoffeTextView.setText(""+(quantityCurrentChantillyCoffee * priceUnitChantillyCoffee ));
        currentSumChocolateTextView.setText(""+(quantityCurrentChocolate * priceUnitChocolate ));
        currentSumChantillyCholocateTextView.setText(""+(quantityCurrentChantillyChocolate * priceUnitChantillyChocolate ));

        this.updateSumOrder();


        this.showWrapperCheckboxChantilly(wrapperCheckboxChantillyCoffee,quantityCurrentCoffee);
        if(quantityCurrentCoffee > 0  && quantityCurrentChantillyCoffee > 0 ){
            wrapperCheckboxChantillyCoffee.setVisibility(View.VISIBLE);
            checkBoxChantillyCoffee.setChecked(true);
        }else{
            wrapperCheckboxChantillyCoffee.setVisibility(View.GONE);
            checkBoxChantillyCoffee.setChecked(false);
        }


        this.showWrapperCheckboxChantilly(wrapperCheckboxChantillyChocolate,quantityCurrentChocolate);
        if(quantityCurrentChocolate > 0  && quantityCurrentChantillyChocolate > 0 ){
            wrapperChantillyChocolate.setVisibility(View.VISIBLE);
            checkBoxChantillyChocolate.setChecked(true);
        }else{
            wrapperChantillyChocolate.setVisibility(View.GONE);
            checkBoxChantillyChocolate.setChecked(false);
        }

    }
    @Override
    public void onClick(View v) {
//        int id = getResources().getIdentifier("arr_name"+positionSelected,"array", rootview.getContext().getPackageName());
        switch (v.getId()) {

            case R.id.btn_order_coffees_quantities_less:

                quantityCurrentCoffee = lessQuantityForTexView(
                        quantityCurrentCoffee,
                        currentQuantityCoffeTextView,
                        currentSumCoffeTextView,
                        priceUnitCoffee,
                        0,
                        MAX_QUANTITIES);
                quantityCurrentChantillyCoffee =  lessCheckQuantityForChantillyTexView(
                        quantityCurrentChantillyCoffee,
                        currentQuantityChantillyCoffeTextView,
                        currentSumChantillyCoffeTextView,
                        priceUnitChantillyCoffee,
                        MIN_QUANTITIES,
                        MAX_QUANTITIES,
                        quantityCurrentCoffee);
                        this.showWrapperCheckboxChantilly(wrapperCheckboxChantillyCoffee,quantityCurrentCoffee);
                        this.showWrapperChantilly(wrapperChantillyCoffee,quantityCurrentCoffee);
                        this.checkedCheckboxChantilly(checkBoxChantillyCoffee,quantityCurrentCoffee);
                break;

            case R.id.btn_order_coffees_quantities_more:
                quantityCurrentCoffee = moreQuantityForTexView(
                        quantityCurrentCoffee,
                        currentQuantityCoffeTextView,
                        currentSumCoffeTextView,
                        priceUnitCoffee,
                        0,
                        MAX_QUANTITIES);

                    this.showWrapperCheckboxChantilly(wrapperCheckboxChantillyCoffee,quantityCurrentCoffee);

                break;

            case R.id.btn_order_quantity_chantilly_for_coffee_less:

                quantityCurrentChantillyCoffee =  lessQuantityForTexView(
                        quantityCurrentChantillyCoffee,
                        currentQuantityChantillyCoffeTextView,
                        currentSumChantillyCoffeTextView,
                        priceUnitChantillyCoffee,
                        MIN_QUANTITIES,
                        MAX_QUANTITIES);
                break;

            case R.id.btn_order_quantity_chantilly_for_coffee_more:
                quantityCurrentChantillyCoffee = moreQuantityForTexView(
                        quantityCurrentChantillyCoffee,
                        currentQuantityChantillyCoffeTextView,
                        currentSumChantillyCoffeTextView,
                        priceUnitChantillyCoffee,
                        MIN_QUANTITIES,
                        quantityCurrentCoffee);
                break;

            case R.id.btn_order_chocolates_quantities_less:
                quantityCurrentChocolate = lessQuantityForTexView(
                        quantityCurrentChocolate,
                        currentQuantityChocolateTextView,
                        currentSumChocolateTextView,
                        priceUnitChocolate,
                        0,
                        MAX_QUANTITIES);
                quantityCurrentChantillyChocolate = lessCheckQuantityForChantillyTexView(
                        quantityCurrentChantillyChocolate,
                        currentQuantityChantillyChocolateTextView,
                        currentSumChantillyCholocateTextView,
                        priceUnitChantillyChocolate,
                        MIN_QUANTITIES,
                        MAX_QUANTITIES,
                        quantityCurrentChocolate);
                    this.showWrapperCheckboxChantilly(wrapperCheckboxChantillyChocolate,quantityCurrentChocolate);
                    this.showWrapperChantilly(wrapperChantillyChocolate,quantityCurrentChocolate);
                    this.checkedCheckboxChantilly(checkBoxChantillyChocolate,quantityCurrentChocolate);
                break;

            case R.id.btn_order_chocolates_quantities_more:
                quantityCurrentChocolate = moreQuantityForTexView(
                        quantityCurrentChocolate,
                        currentQuantityChocolateTextView,
                        currentSumChocolateTextView,
                        priceUnitChocolate,
                        MIN_QUANTITIES,
                        MAX_QUANTITIES);

                    this.showWrapperCheckboxChantilly(wrapperCheckboxChantillyChocolate,quantityCurrentChocolate);

                break;

            case R.id.btn_order_quantity_chantilly_for_chocolate_less:
                quantityCurrentChantillyChocolate = lessQuantityForTexView(
                        quantityCurrentChantillyChocolate,
                        currentQuantityChantillyChocolateTextView,
                        currentSumChantillyCholocateTextView,
                        priceUnitChantillyChocolate,
                        MIN_QUANTITIES,
                        MAX_QUANTITIES);
                break;

            case R.id.btn_order_quantity_chantilly_for_chocolate_more:
                quantityCurrentChantillyChocolate = moreQuantityForTexView(
                        quantityCurrentChantillyChocolate,
                        currentQuantityChantillyChocolateTextView,
                        currentSumChantillyCholocateTextView,
                        priceUnitChantillyChocolate,
                        MIN_QUANTITIES,
                        quantityCurrentChocolate);
                break;

            default:
                break;
        }
        this.updateSumOrder();
    }
}
