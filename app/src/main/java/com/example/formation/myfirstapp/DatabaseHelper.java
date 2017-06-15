package com.example.formation.myfirstapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Formation on 13/06/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME="StarbucksManager";
    private static final String TABLE_ORDERS="orders";
    private static final String KEY_ID="id";
    private static final String KEY_QUANTITIES_COFFEES="quantities_coffees";
    private static final String KEY_QUANTITIES_CHANTILLY_COFFEES="quantities_chantilly_coffees";
    private static final String KEY_QUANTITIES_CHOCOLATES="quantities_chocolates";
    private static final String KEY_QUANTITIES_CHANTILLY_CHOCOLATES="quantities_chantilly_chocolates";
    private static final String KEY_SUM_ORDER="quantities_sum_order";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Create Table orders
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ORDERS_TABLE = "CREATE TABLE "+ TABLE_ORDERS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_QUANTITIES_COFFEES + " TEXT,"
                + KEY_QUANTITIES_CHANTILLY_COFFEES + " TEXT,"
                + KEY_QUANTITIES_CHOCOLATES + " TEXT,"
                + KEY_QUANTITIES_CHANTILLY_CHOCOLATES + " TEXT,"
                + KEY_SUM_ORDER + " TEXT NOT NULL)";

        db.execSQL(CREATE_ORDERS_TABLE);

    }

    /**
     * Updgrade Table orders
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
        onCreate(db);
    }

    /**
     * Insert order
     * @param order
     */
    public void addOrder(Order order){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put( KEY_QUANTITIES_COFFEES, order.getQuantitiesCoffees() );
        contentValues.put( KEY_QUANTITIES_CHOCOLATES, order.getQuantitiesChocolates() );
        contentValues.put( KEY_QUANTITIES_CHANTILLY_COFFEES, order.getQuantitiesChantillyCoffees() );
        contentValues.put( KEY_QUANTITIES_CHANTILLY_CHOCOLATES, order.getQuantitiesChantillyChocolates() );
        contentValues.put( KEY_SUM_ORDER, order.getSumOrder() );

        db.insert(TABLE_ORDERS,null,contentValues);

        db.close();

    }

    /**
     * Select an order
     * @param id
     * @return
     */
    public Order getOrder(int id){
        SQLiteDatabase db = getReadableDatabase();
        Order order = new Order();
        Cursor cursor = db.query(
                TABLE_ORDERS,
                new String[]{
                        KEY_ID,
                        KEY_QUANTITIES_COFFEES,
                        KEY_QUANTITIES_CHANTILLY_COFFEES,
                        KEY_QUANTITIES_CHOCOLATES,
                        KEY_QUANTITIES_CHANTILLY_CHOCOLATES
                },
                KEY_ID + "=?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null,
                null);
        if (cursor != null){
            cursor.moveToFirst();
            order = new Order(
                    Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    cursor.getString(3),
                    cursor.getString(2),
                    cursor.getString(4),
                    cursor.getString(5));

        }

        return order;
    }

    /**
     * Select all orders
     * @return
     */
    public List<Order> getAllOrders(){
        List<Order> orderList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_ORDERS;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do {

                Order order = new Order();

                order.setId(Integer.parseInt(cursor.getString(0)));
                order.setQuantitiesCoffees(cursor.getString(1));
                order.setQuantitiesChantillyCoffees(cursor.getString(3));
                order.setQuantitiesChocolates(cursor.getString(2));
                order.setQuantitiesChantillyChocolates(cursor.getString(4));
                order.setSumOrder(cursor.getString(5));

                orderList.add(order);
            }while (cursor.moveToNext());
        }
        return orderList;
    }

    /**
     * Update order
     * @param order
     * @return
     */
    public int updateOrder(Order order){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put( KEY_QUANTITIES_COFFEES, order.getQuantitiesCoffees() );
        contentValues.put( KEY_QUANTITIES_CHANTILLY_COFFEES, order.getQuantitiesChantillyCoffees() );
        contentValues.put( KEY_QUANTITIES_CHOCOLATES, order.getQuantitiesChocolates() );
        contentValues.put( KEY_QUANTITIES_CHANTILLY_CHOCOLATES, order.getQuantitiesChantillyChocolates() );
        contentValues.put( KEY_SUM_ORDER, order.getSumOrder() );

        return db.update(TABLE_ORDERS, contentValues, KEY_ID + "=?",
                new String[]{String.valueOf(order.getId())});
    }

    /**
     * Delete order
     * @param order
     */
    public void deleteOrder(Order order){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ORDERS,KEY_ID + "=?", new String[]{String.valueOf(order.getId())});
        db.close();
    }

    /**
     * Empty table orders
     */
    public void deleteAllOrders(){
        SQLiteDatabase db = this.getWritableDatabase();
//        db.execSQL("delete from "+ TABLE_ORDERS); OR
        db.delete(TABLE_ORDERS, null, null);
        db.close();
    }

    /**
     * Number of orders
     * @return
     */
    public int getOrdersCount(){
        String countQuery = "SELECT * FROM " + TABLE_ORDERS;
        SQLiteDatabase db =  this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery,null);
        cursor.close();
        return cursor.getCount();
    }
}
