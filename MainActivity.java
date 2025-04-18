package com.example.calcprogram;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.*;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //電卓のボタンに対応した変数
    private Button btn1;//1
    private Button btn2;//2
    private Button btn3;//3
    private Button btn4;//4
    private Button btn5;//5
    private Button btn6;//6
    private Button btn7;//7
    private Button btn8;//8
    private Button btn9;//9
    private Button btn0;//0
    private Button btnAC;//AC
    private Button btnBS;//BS
    private Button btnDiv;//'/'
    private Button btnMulti;//'*'
    private Button btnPlus;//'+'
    private Button btnMinus;//'-'
    private Button btnEqual;//'='
    private Button btnKakko1;//'('
    private Button btnKakko2;//')'
    private Button btnPoint;//'・'

    private StringBuilder line = new StringBuilder();//ボタンから入力された数字や演算子を受け取る

    //数式を表示するテキストビューの変数
    private TextView CalcText;

    //lineの中の計算結果を返すメソッド
    public static double evaluate(StringBuilder expressionBuilder) {
        //StringBuilderをString型の文字列に変換
        String expression = expressionBuilder.toString();

        // 数値スタックと演算子スタック
        Stack<Double> numStack = new Stack<>();
        Stack<Character> signStack = new Stack<>();

        // 式を1文字ずつ解析
        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);

            // 数値の場合（小数点対応）
            if (Character.isDigit(ch) || ch == '.') {
                StringBuilder numBuilder = new StringBuilder();
                // 数字をすべて解析（複数桁と小数点対応）
                while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    numBuilder.append(expression.charAt(i));
                    i++;
                }
                numStack.push(Double.parseDouble(numBuilder.toString())); // 数値をスタックに保存
                i--; // 解析ポインタを戻す
            }
            // 括弧の開始
            else if (ch == '(') {
                signStack.push(ch);
            }
            // 括弧の終了
            else if (ch == ')') {
                while (signStack.peek() != '(') {
                    calculate(numStack, signStack); // 括弧内計算を実行
                }
                signStack.pop(); // '('を取り除く
            }
            // 演算子の場合
            else if (ch == '+' || ch == '-' || ch == '*' || ch == '/') {
                while (!signStack.isEmpty() && precedence(signStack.peek()) >= precedence(ch)) {
                    calculate(numStack, signStack);
                }
                signStack.push(ch); // 演算子をスタックに保存
            }
        }

        // 残りの演算子を処理
        while (!signStack.isEmpty()) {
            calculate(numStack, signStack);
        }

        // 結果を返す
        return numStack.pop();
    }

    // 演算子の優先順位を返す
    private static int precedence(char operator) {
        if (operator == '+' || operator == '-') {
            return 1;
        } else if (operator == '*' || operator == '/') {
            return 2;
        }
        return 0;
    }

    // スタックを使った計算処理
    private static void calculate(Stack<Double> numStack, Stack<Character> signStack) {
        double b = numStack.pop();
        double a = numStack.pop();
        char operator = signStack.pop();

        if (operator == '+') {
            numStack.push(a + b);
        } else if (operator == '-') {
            numStack.push(a - b);
        } else if (operator == '*') {
            numStack.push(a * b);
        } else if (operator == '/') {
            if (b == 0) {
                throw new ArithmeticException("ゼロで割ることはできません");
            }
            numStack.push(a / b);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btnAC = findViewById(R.id.btnAC);
        btnAC.setOnClickListener(this);

        btnKakko1 = findViewById(R.id.btnKakko1);
        btnKakko1.setOnClickListener(this);

        btnKakko2 = findViewById(R.id.btnKakko2);
        btnKakko2.setOnClickListener(this);

        btnBS = findViewById(R.id.btnBS);
        btnBS.setOnClickListener(this);

        btn1 = findViewById(R.id.btn1);
        btn1.setOnClickListener(this);

        btn2 = findViewById(R.id.btn2);
        btn2.setOnClickListener(this);

        btn3 = findViewById(R.id.btn3);
        btn3.setOnClickListener(this);

        btn4 = findViewById(R.id.btn4);
        btn4.setOnClickListener(this);

        btn5 = findViewById(R.id.btn5);
        btn5.setOnClickListener(this);

        btn6 = findViewById(R.id.btn6);
        btn6.setOnClickListener(this);

        btn7 = findViewById(R.id.btn7);
        btn7.setOnClickListener(this);

        btn8 = findViewById(R.id.btn8);
        btn8.setOnClickListener(this);

        btn9 = findViewById(R.id.btn9);
        btn9.setOnClickListener(this);

        btn0 = findViewById(R.id.btn0);
        btn0.setOnClickListener(this);

        btnPoint = findViewById(R.id.btnPoint);
        btnPoint.setOnClickListener(this);

        btnEqual = findViewById(R.id.btnEqual);
        btnEqual.setOnClickListener(this);

        btnDiv = findViewById(R.id.btnDiv);
        btnDiv.setOnClickListener(this);

        btnMulti = findViewById(R.id.btnMulti);
        btnMulti.setOnClickListener(this);

        btnPlus = findViewById(R.id.btnPlus);
        btnPlus.setOnClickListener(this);

        btnMinus = findViewById(R.id.btnMinus);
        btnMinus.setOnClickListener(this);

        CalcText = findViewById(R.id.CalcText);
        CalcText.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn1) {
            line.append("1");
        } else if (view.getId() == R.id.btn2) {
            line.append("2");
        } else if (view.getId() == R.id.btn3) {
            line.append("3");
        } else if (view.getId() == R.id.btn4) {
            line.append("4");
        } else if (view.getId() == R.id.btn5) {
            line.append("5");
        } else if (view.getId() == R.id.btn6) {
            line.append("6");
        } else if (view.getId() == R.id.btn7) {
            line.append("7");
        } else if (view.getId() == R.id.btn8) {
            line.append("8");
        } else if (view.getId() == R.id.btn9) {
            line.append("9");
        } else if (view.getId() == R.id.btn0) {
            line.append("0");
        } else if (view.getId() == R.id.btnKakko1) {
            line.append("(");
        } else if (view.getId() == R.id.btnKakko2) {
            line.append(")");
        } else if (view.getId() == R.id.btnDiv) {
            line.append("/");
        } else if (view.getId() == R.id.btnMulti) {
            line.append("*");
        } else if (view.getId() == R.id.btnPlus) {
            line.append("+");
        } else if (view.getId() == R.id.btnMinus) {
            line.append("-");
        } else if (view.getId() == R.id.btnPoint) {
            line.append(".");
        } else if (view.getId() == R.id.btnEqual) {
            try {
                double result = evaluate(line);
                line.setLength(0);
                if (result == (int) result) {
                    int integerResult = (int) result; // 整数部分を取得
                    line.append(integerResult);
                } else {
                    line.append(result);
                }
            } catch (Exception e) {
                line.setLength(0);
                line.append("Error");
            }
        } else if (view.getId() == R.id.btnBS) {
            if (line.length() > 0) { // 中身が空でない場合のみ削除
                line.deleteCharAt(line.length() - 1);
            }
        } else if (view.getId() == R.id.btnAC) {
            line.setLength(0);
        }

        //テキストビューに表示
        if (line.length() == 0) {
            CalcText.setText("0");
        } else {
            CalcText.setText(line);
            if (line.toString().equals("Error")) {
                line.setLength(0);
            }
        }

    }
}