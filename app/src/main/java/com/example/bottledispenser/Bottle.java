package com.example.bottledispenser;


public class Bottle
{
    private String name = "Pepsi Max";
    private String manufacturer = "Pepsi";
    private double total_energy = 0.3;
    private double price = 1.80;
    private double size = 0.5;
    private int amount = 1;

    public Bottle()
    {
    }

    public Bottle(String n, String manf, double totE, double prc, double sz, int amnt)
    {
        price = prc;
        size = sz;
        name = n;
        manufacturer = manf;
        total_energy = totE;
        amount = amnt;
    }

    public String toString()
    {
        return (name + ", " + size + ", " + price + "€");
    }

    public String getName()
    {
        return name;
    }

    public double getSize()
    {
        return size;
    }

    public double getPrice()
    {
        return price;
    }

    public int getAmount()
    {
        return amount;
    }

    public void setAmount(int amnt)
    {
        amount = amnt;
    }
}
