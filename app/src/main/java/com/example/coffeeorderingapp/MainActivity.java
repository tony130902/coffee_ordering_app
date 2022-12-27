package com.example.coffeeorderingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    int quantity = 2;
    TextView coffee_Quantity, order_Summary;
    Button increment , decrement , orderNow;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        increment = findViewById(R.id.increment);
        decrement = findViewById(R.id.decrement);
        order_Summary = findViewById(R.id.order_Summary);
        coffee_Quantity = findViewById(R.id.coffee_Quantity);
        orderNow = findViewById(R.id.order);

    }

    public void increment(View view){
        if (quantity == 25){
            Toast.makeText(this, "You Cannot order more than 25 coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        display(quantity);
    }

    public void decrement(View view){
        if (quantity == 1){
            Toast.makeText(this, "You Cannot order less than 1 coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        display(quantity);
    }

    public void orderNow(View view){
        EditText name = (EditText) findViewById(R.id.name);
        String customer_Name = name.getText().toString();

        CheckBox whipped_cream = (CheckBox) findViewById(R.id.whippedCream_checkbox);
        boolean hasWhipped_cream = whipped_cream.isChecked();
        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolate.isChecked();

        String message = "Rs. " + price(hasWhipped_cream , hasChocolate) ;
        String summary = order_Summary(message ,hasWhipped_cream ,hasChocolate ,customer_Name );

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_TEXT, summary);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee Order for " + customer_Name);
        startActivity(intent);
//        if (intent.resolveActivity(getPackageManager()) != null) {
//        }
    }

    private void display(int number){
        coffee_Quantity = findViewById(R.id.coffee_Quantity);
        coffee_Quantity.setText("" + number);
    }

    private int price(boolean addWhipped_cream_price , boolean addChocolate_price){
        int basePrice = 5;
        if (addChocolate_price){
            basePrice = basePrice + 2;
        }

        if (addWhipped_cream_price){
            basePrice = basePrice + 1;
        }

        return quantity * basePrice;

    }

    private String order_Summary(String number ,boolean addWipped_cream , boolean addchocolate ,String Customer_Name ){
        String order_detail = "Name : " + Customer_Name + "\n"
                        + "Quantity: " + quantity
                        + "\n" + "Price : " + number
                        + "\n" + "Add Whipped Cream ? : " + addWipped_cream
                        + "\n" + "Add Chocolate : " + addchocolate
                        + "\n" + "Thankyou!!";
        return order_detail;
    }

}