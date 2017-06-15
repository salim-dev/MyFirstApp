package com.example.formation.myfirstapp;


import java.io.Serializable;

/**
 * Created by Formation on 12/06/2017.
 */

public class Order {
    private int id;
    private String quantitiesCoffees;
    private String quantitiesChocolates;
    private String quantitiesChantillyCoffees;
    private String quantitiesChantillyChocolates;
    private String sumOrder;

    public Order() {
    }

    public Order(int id,String quantitiesCoffees, String quantitiesChocolates, String quantitiesChantillyCoffees, String quantitiesChantillyChocolates, String sumOrder){
        this.id = id;
        this.quantitiesCoffees = quantitiesCoffees;
        this.quantitiesChocolates = quantitiesChocolates;
        this.quantitiesChantillyCoffees = quantitiesChantillyCoffees;
        this.quantitiesChantillyChocolates = quantitiesChantillyChocolates;
        this.sumOrder = sumOrder;
    }
    public Order(String quantitiesCoffees, String quantitiesChocolates, String quantitiesChantillyCoffees, String quantitiesChantillyChocolates, String sumOrder){
        this.quantitiesCoffees = quantitiesCoffees;
        this.quantitiesChocolates = quantitiesChocolates;
        this.quantitiesChantillyCoffees = quantitiesChantillyCoffees;
        this.quantitiesChantillyChocolates = quantitiesChantillyChocolates;
        this.sumOrder = sumOrder;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuantitiesCoffees() {
        return quantitiesCoffees;
    }

    public void setQuantitiesCoffees(String quantitiesCoffees) {
        this.quantitiesCoffees = quantitiesCoffees;
    }

    public String getQuantitiesChocolates() {
        return quantitiesChocolates;
    }

    public void setQuantitiesChocolates(String quantitiesChocolates) {
        this.quantitiesChocolates = quantitiesChocolates;
    }

    public String getQuantitiesChantillyCoffees() {
        return quantitiesChantillyCoffees;
    }

    public void setQuantitiesChantillyCoffees(String quantitiesChantillyCoffees) {
        this.quantitiesChantillyCoffees = quantitiesChantillyCoffees;
    }

    public String getQuantitiesChantillyChocolates() {
        return quantitiesChantillyChocolates;
    }

    public void setQuantitiesChantillyChocolates(String quantitiesChantillyChocolates) {
        this.quantitiesChantillyChocolates = quantitiesChantillyChocolates;
    }

    public String getSumOrder() {
        return sumOrder;
    }

    public void setSumOrder(String sumOrder) {
        this.sumOrder = sumOrder;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", quantitiesCoffees='" + quantitiesCoffees + '\'' +
                ", quantitiesChocolates='" + quantitiesChocolates + '\'' +
                ", quantitiesChantillyCoffees='" + quantitiesChantillyCoffees + '\'' +
                ", quantitiesChantillyChocolates='" + quantitiesChantillyChocolates + '\'' +
                ", sumOrder='" + sumOrder + '\'' +
                '}';
    }
}
