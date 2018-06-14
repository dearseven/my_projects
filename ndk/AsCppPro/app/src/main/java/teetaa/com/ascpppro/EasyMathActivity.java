package teetaa.com.ascpppro;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

import teetaa.com.ascpppro.nativefuncs.Cyan_Hello;


public class EasyMathActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_math);


        ((TextView) findViewById(R.id.easy_math_jia)).setText("1+2=" + Cyan_Hello.add(1, 2));
        ((TextView) findViewById(R.id.easy_math_jian)).setText("6-2=" + Cyan_Hello.subtract(6, 2));
        ((TextView) findViewById(R.id.easy_math_cheng)).setText("24*2=" + Cyan_Hello.multiply(24, 2));
        ((TextView) findViewById(R.id.easy_math_chu)).setText("100/2=" + Cyan_Hello.divide(100, 2));

        //
        ArrayList<Integer> list = new ArrayList<Integer>(10);
        for (int i = 1; i <= 10; i++) {
            list.add(i);
        }
        ((TextView) findViewById(R.id.easy_math_arrayList)).setText("1+2...+10=" + Cyan_Hello.sumOfIntList(list));

    }
}
