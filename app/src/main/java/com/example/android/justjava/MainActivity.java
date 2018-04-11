
package com.example.android.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public int noOfCoffees = 0,price;
    public boolean isChecked = false;
    public String summary,subject;
    /**
     * This method is called when the order button is clicked.
     */
    public String calculatePrice(int price, int discount){
        int Q = noOfCoffees;
        int total = (price*noOfCoffees)-discount;
        return (getString(R.string.Quantity)+"\n"+getString(R.string.Total)+":"+ NumberFormat.getCurrencyInstance().format(total));
    }
    public void submitOrder(View view)
    {
        String messege,messege1;
        messege="";
        price = 5;
        subject = getString(R.string.s);
        EditText text = (EditText) findViewById(R.id.userinput);
        String name = text.getText().toString();
        messege1 = getString(R.string.Name)+":"+name+"\n";
        subject= subject + name;
        CheckBox checkboxWhippedCream = (CheckBox) findViewById(R.id.checkbox1);
        if(checkboxWhippedCream.isChecked())
        {
            messege1+=getString(R.string.Added_Whipped_Cream)+"\n";
            price+=1;
        }
        CheckBox checkboxChocolate = (CheckBox) findViewById(R.id.checkbox2);
        if(checkboxChocolate.isChecked())
        {
            messege1+=getString(R.string.Added_chocolate)+"\n";
            price+=2;
        }
        if(noOfCoffees%5==0 && noOfCoffees!=0) {
            messege =getString(R.string.Free);
            summary = calculatePrice(price,5);
            summary+=messege;
        }
        else {
            messege = "";
            summary = calculatePrice(price,0);
        }
        summary = messege1 + summary;
            displayPrice(messege);
    }

    public void Increment(View view) {
        noOfCoffees++;
        displayQuantity(noOfCoffees);
    }

    public void Decrement(View view) {
        noOfCoffees--;
        if(noOfCoffees < 0) {
            noOfCoffees = 0;
            Toast.makeText(getApplicationContext(),getString(R.string.Minimum_Limit),Toast.LENGTH_SHORT).show();
        }
        displayQuantity(noOfCoffees);
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(String messege) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        summary+="\n"+getString(R.string.thankyou);
        intent.putExtra(Intent.EXTRA_SUBJECT,subject);
        intent.putExtra(Intent.EXTRA_TEXT,summary);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}