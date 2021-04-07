package be.kuleuven.javabean;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button btnPlus;
    private TextView tvQtyHolder;
    private Button btnMinus;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnPlus = (Button) findViewById(R.id.btnPlus);
        btnMinus = (Button) findViewById(R.id.btnMinus);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        tvQtyHolder = (TextView) findViewById(R.id.tvQtyHolder);
    }

    public void onClick_btnPlus (View caller ){
        int quantity = Integer.parseInt(tvQtyHolder.getText().toString())+ 1;
        tvQtyHolder.setText(Integer.toString(quantity));
        enableSubmit(quantity);
    }
    public void onClick_btnMlus (View caller ){
        int quantity = Integer.parseInt(tvQtyHolder.getText().toString());
        if(quantity >0 ) quantity--;
        tvQtyHolder.setText(Integer.toString(quantity));
        enableSubmit(quantity);
    }
    public void enableSubmit (int quantity ){
        btnSubmit.setEnabled(quantity > 0);
    }
    public void onClick_Submit(View caller){
        EditText txtName = (EditText) findViewById(R.id.txtName);
        Spinner spCoffee = (Spinner) findViewById(R.id.spCoffee);
        CheckBox cbSugar = (CheckBox) findViewById(R.id.cbSugar);
        CheckBox cbWhipCream = (CheckBox) findViewById(R.id.cbWhipCream);

        Intent intent = new Intent(this,QueueActivity.class);
        intent.putExtra("Name",txtName.getText());
        intent.putExtra("Coffee",spCoffee.getSelectedItem().toString());
        intent.putExtra("Sugar",cbSugar.isChecked());
        intent.putExtra("Whip Cream",cbWhipCream.isChecked());
        intent.putExtra("Quantity",Integer.parseInt(tvQtyHolder.getText().toString()));

        startActivity(intent);
    }

}