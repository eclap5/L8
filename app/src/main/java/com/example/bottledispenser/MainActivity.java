package com.example.bottledispenser;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity
{
    EditText mainOutput;
    SeekBar seekBar;
    TextView amountText;
    Spinner spinner;
    Context context = null;

    private ArrayList<Bottle> bottle_array;
    private double money = 0;
    private int bottles = 0;
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainOutput = (EditText)findViewById(R.id.mainOutput);
        seekBar = (SeekBar)findViewById(R.id.seekBar);
        amountText = (TextView)findViewById(R.id.amountText);
        spinner = (Spinner)findViewById(R.id.spinner);
        context = MainActivity.this;

        bottle_array = new ArrayList<Bottle>();
        bottle_array.add(new Bottle("Pepsi Max", "Pepsi", 0.1, 1.8, 0.5, 2));
        bottle_array.add(new Bottle("Pepsi Max", "Pepsi", 0.3, 2.2, 1.5, 1));
        bottle_array.add(new Bottle("Coca-Cola Zero", "Coca-Cola", 0.1, 2.0, 0.5, 1));
        bottle_array.add(new Bottle("Coca-Cola Zero", "Coca-Cola", 0.3, 2.5, 1.5, 1));
        bottle_array.add(new Bottle("Fanta Zero", "Fanta", 0.1, 1.95, 0.5, 1));

        for(int i = 0; i < bottle_array.size(); i++)
        {
            bottles += bottle_array.get(i).getAmount();
        }

        ArrayAdapter<Bottle> adapter = new ArrayAdapter<Bottle>(this, android.R.layout.simple_spinner_item, bottle_array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                index = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b)
            {
                //i = i / 10;
                float amount = (float)i / 10;
                amountText.setText(String.valueOf(amount) + "€");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {

            }
        });
    }


    public void writeReceipt()
    {
        try
        {
            OutputStreamWriter osw = new OutputStreamWriter(context.openFileOutput("receipt.txt", MODE_PRIVATE));
            osw.write("*** RECEIPT ***\n\n" +
                    "Latest purchase:\n" +
                    "Product: " + bottle_array.get(index).getName() +
                    " " + String.valueOf(bottle_array.get(index).getSize()) +
                    "\nPrice: " + String.valueOf(bottle_array.get(index).getPrice()) + "€");
            osw.close();
        }
        catch (IOException e)
        {
            Log.e("IOException", "Virhe syötteessä.");
        }
    }


    public void addMoney(View v)
    {
        float num = 0;
        num = seekBar.getProgress() / 10;
        money += num;
        seekBar.setProgress(0);
        mainOutput.setText("Klink! Added more money!");
    }


    public void buyBottle(View v)
    {
        if (bottles == 0)
        {
            mainOutput.setText("No more bottles!");
        }
        else
        {
            if (bottle_array.get(index).getAmount() == 0)
            {
                mainOutput.setText("Not available.");
            }
            else
            {
                double diff;
                diff = bottle_array.get(index).getPrice();

                if (money < diff)
                {
                    mainOutput.setText("Add money first!");
                }
                else
                {
                    mainOutput.setText("KACHUNK! " + bottle_array.get(index).getName() + " " + bottle_array.get(index).getSize() + " came out of the dispenser!");
                    money -= bottle_array.get(index).getPrice();
                    bottle_array.get(index).setAmount(bottle_array.get(index).getAmount() - 1);
                    bottles -= 1;
                    writeReceipt();
                }
            }
        }
    }


    public void returnMoney(View v)
    {
        String strMoney = String.format("%.2f", money);
        mainOutput.setText("Klink klink. Money came out! You got " + strMoney + "€ back");
        money = 0;
    }
}