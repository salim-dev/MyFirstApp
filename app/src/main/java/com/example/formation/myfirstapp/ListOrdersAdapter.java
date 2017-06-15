package com.example.formation.myfirstapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Formation on 12/06/2017.
 */

public class ListOrdersAdapter extends ArrayAdapter<Order> {
    Context context;
    List<Order> orders;
    public ListOrdersAdapter(Context context, List<Order> orders){
        super(context,0, orders);
        this.context = context;
        this.orders = orders;

    }

    @Override
    public int getCount() {
        return orders.size(); //returns total of orders in the list
    }

    @Override
    public Order getItem(int position) {
        return orders.get(position); //returns list order at the specified position
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_order,parent, false);
        }
        OrderViewHolder viewHolder = (OrderViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new OrderViewHolder();
            viewHolder.quantitiesCoffees = (TextView) convertView.findViewById(R.id.list_row_quantities_coffees);
            viewHolder.quantitiesChocolates = (TextView) convertView.findViewById(R.id.label_list_row_chocolates);
            viewHolder.quantitiesChantillyCoffees = (TextView) convertView.findViewById(R.id.list_row_quantities_chantilly_coffees);
            viewHolder.quantitiesChantillyChocolates = (TextView) convertView.findViewById(R.id.list_row_quantities_chantilly_chocolates);
            viewHolder.sumOrder = (TextView) convertView.findViewById(R.id.list_row_sum_order);

            convertView.setTag(viewHolder);
        }

        //getItem(position) it will recover position the order of list
        Order order = getItem(position);
        viewHolder.quantitiesCoffees.setText(order.getQuantitiesCoffees());
        viewHolder.quantitiesChocolates.setText(order.getQuantitiesChocolates());
        viewHolder.quantitiesChantillyCoffees.setText(order.getQuantitiesChantillyCoffees());
        viewHolder.quantitiesChantillyChocolates.setText(order.getQuantitiesChantillyChocolates());
        viewHolder.sumOrder.setText(order.getSumOrder());

        return convertView;
    }

    private class OrderViewHolder {
        public TextView quantitiesCoffees;
        public TextView quantitiesChocolates;
        public TextView quantitiesChantillyCoffees;
        public TextView quantitiesChantillyChocolates;
        public TextView sumOrder;

        @Override
        public String toString() {
            return "ListViewHolder{" +
                    "quantitiesCoffees=" + quantitiesCoffees.getText().toString() +
                    ", quantitiesChocolates=" + quantitiesChocolates.getText().toString() +
                    ", quantitiesChantillyCoffees=" + quantitiesChantillyCoffees.getText().toString() +
                    ", quantitiesChantillyChocolates=" + quantitiesChantillyChocolates.getText().toString() +
                    ", sumOrder=" + sumOrder.getText().toString() +
                    '}';
        }
    }

}
