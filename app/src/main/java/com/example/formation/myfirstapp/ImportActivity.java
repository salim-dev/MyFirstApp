package com.example.formation.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.HashMap;
import java.util.List;

public class ImportActivity extends AppCompatActivity {
//    private String[] listOrders = {
//            "Commande 1",
//            "Commande 2",
//            "Commande 3",
//            "Commande 4",
//            "Commande 5",
//            "Commande 6",
//            "Commande 7",
//            "Commande 8",
//            "Commande 9",
//            "Commande 10"
//    };
    public static final String INTENT_KEY_ORDER = "com.example.formation.myfirstapp.ImportActivity.INTENT_KEY_ORDER";
    private List<Order> ordersList;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import);
        setTitle("Import");
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        DatabaseHelper db = new DatabaseHelper(this);
        ordersList = db.getAllOrders();
        actionBar.setDisplayHomeAsUpEnabled(true);
        listView = (ListView) findViewById(R.id.list);
//        this.showSimpleListViewOrders();
        this.showRecycleViewOrders();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    /**
//     * Show orders in simple listview
//     */
//    private void showSimpleListViewOrders(){
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ImportActivity.this, android.R.layout.simple_list_item_1, listOrders);
//        listView.setAdapter(adapter);
//    }
//    /**
//     * Generate list of orders for recycleview without database
//     */
//    private List<Order> generateCommandes(){
//        List<Order> listOrders = new ArrayList<>();
//
//        listOrders.add(new Order("1","1","0","0","10"));
//        listOrders.add(new Order("2","1","1","0","16"));
//        listOrders.add(new Order("0","1","0","1","6"));
//        listOrders.add(new Order("3","2","1","1","27"));
//
//        return listOrders;
//    }

    /**
     * Show order in recycleview
     */
    private void showRecycleViewOrders(){

//        List<Order> listOrders = generateCommandes();
//        ListOrdersAdapter adapter = new ListOrdersAdapter(ImportActivity.this, listOrders);


        final ListOrdersAdapter adapter = new ListOrdersAdapter(ImportActivity.this, ordersList);
        listView.setAdapter(adapter);

        /**
         * listen click an item and we are going get back data of the item and show on order layout
         */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Order order = adapter.getItem(position);
                HashMap<String,Integer> orderHashMap = new HashMap<>();

                orderHashMap.put("id", order.getId());
                orderHashMap.put("quantitiesCoffees", Integer.parseInt(order.getQuantitiesCoffees()));
                orderHashMap.put("quantitiesChantillyCoffees", Integer.parseInt(order.getQuantitiesChantillyCoffees()));
                orderHashMap.put("quantitiesChocolates", Integer.parseInt(order.getQuantitiesChocolates()));
                orderHashMap.put("quantitiesChantillyChocolates", Integer.parseInt(order.getQuantitiesChantillyChocolates()));
                orderHashMap.put("sumOrder", Integer.parseInt(order.getSumOrder()));


                Intent intent = new Intent(ImportActivity.this,OrderActivity.class);
                intent.putExtra(INTENT_KEY_ORDER,orderHashMap);
                startActivity(intent);
            }
        });
    }
}
