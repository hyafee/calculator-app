package com.example.mycalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvResult, tvSolution;
    AppCompatButton btnCalculate, btnOpen, btnClose, btnDivide, btnSeven, btnEight, btnNine,
            btnMultiple, btnFour, btnFive, btnSix, btnPlus, btnOne, btnTwo, btnThree,
            btnMinus, btnAC, btnZero, btnDot, btnEqual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inisialisasi TextView
        tvResult = findViewById(R.id.tv_result);
        tvSolution = findViewById(R.id.tv_solution);

        // Inisialisasi MaterialButton
        assignId(btnCalculate, R.id.btn_calculate);
        assignId(btnOpen, R.id.btn_open);
        assignId(btnClose, R.id.btn_close);
        assignId(btnDivide, R.id.btn_divide);
        assignId(btnSeven, R.id.btn_seven);
        assignId(btnEight, R.id.btn_eight);
        assignId(btnNine, R.id.btn_nine);
        assignId(btnMultiple, R.id.btn_multiple);
        assignId(btnFour, R.id.btn_four);
        assignId(btnFive, R.id.btn_five);
        assignId(btnSix, R.id.btn_six);
        assignId(btnPlus, R.id.btn_plus);
        assignId(btnOne, R.id.btn_one);
        assignId(btnTwo, R.id.btn_two);
        assignId(btnThree, R.id.btn_three);
        assignId(btnMinus, R.id.btn_minus);
        assignId(btnAC, R.id.btn_ac);
        assignId(btnZero, R.id.btn_zero);
        assignId(btnDot, R.id.btn_dot);
        assignId(btnEqual, R.id.btn_equal);

    }

    void assignId(AppCompatButton btn, int id){
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    public void onClick(View view) {
        AppCompatButton button =(AppCompatButton) view;
        String buttonText = button.getText().toString();
        String dataToCalculate = tvSolution.getText().toString();

        if(buttonText.equals("AC")){
            tvSolution.setText("");
            tvResult.setText("0");
            return;
        }
        if(buttonText.equals("=")){
            tvSolution.setText(tvResult.getText());
            return;
        }
        if(buttonText.equals("C")){
            dataToCalculate = dataToCalculate.substring(0,dataToCalculate.length()-1);
        }else{
            dataToCalculate = dataToCalculate+buttonText;
        }
        tvSolution.setText(dataToCalculate);

        String finalResult = getResult(dataToCalculate);

        if(!finalResult.equals("Err")){
            tvResult.setText(finalResult);
        }

    }
    String getResult(String data){
        try{
            Context context  = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String finalResult =  context.evaluateString(scriptable,data,"Javascript",1,null).toString();
            if(finalResult.endsWith(".0")){
                finalResult = finalResult.replace(".0","");
            }
            return finalResult;
        }catch (Exception e){
            return "Err";
        }
    }
}
