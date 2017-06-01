package com.buptant.calculator;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Created by chenlei on 2017/5/28.
 */

public class CalculatorFragment extends Fragment implements View.OnClickListener{

    private static final String TAG = CalculatorFragment.class.getSimpleName();

    private Button bt_0;
    private Button bt_1;
    private Button bt_2;
    private Button bt_3;
    private Button bt_4;
    private Button bt_5;
    private Button bt_6;
    private Button bt_7;
    private Button bt_8;
    private Button bt_9;
    private Button bt_dot;
    private Button bt_plus;
    private Button bt_minu;
    private Button bt_multiply;
    private Button bt_divide;
    private Button bt_equal;
    private Button bt_clear;
    private Button bt_del;
    private TextView tv_his;
    private TextView tv_input;

    private String lastResult = "";
    private StringBuilder sb;
    private Counts opertation;
    private SharedPreferences sharedPreferences;
    private int operationCount;
    private Set<Character> set;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sb = new StringBuilder();
        set = new HashSet<>();
        set.add('.');
        set.add('+');
        set.add('-');
        set.add('x');
        set.add('÷');
        sharedPreferences = getActivity().getSharedPreferences("history", Context.MODE_APPEND);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calculator, container, false);
        bt_0 = (Button) view.findViewById(R.id.bt_0);
        bt_1 = (Button) view.findViewById(R.id.bt_1);
        bt_2 = (Button) view.findViewById(R.id.bt_2);
        bt_3 = (Button) view.findViewById(R.id.bt_3);
        bt_4 = (Button) view.findViewById(R.id.bt_4);
        bt_5 = (Button) view.findViewById(R.id.bt_5);
        bt_6 = (Button) view.findViewById(R.id.bt_6);
        bt_7 = (Button) view.findViewById(R.id.bt_7);
        bt_8 = (Button) view.findViewById(R.id.bt_8);
        bt_9 = (Button) view.findViewById(R.id.bt_9);
        bt_dot = (Button) view.findViewById(R.id.bt_dot);
        bt_plus = (Button) view.findViewById(R.id.bt_plus);
        bt_minu = (Button) view.findViewById(R.id.bt_minus);
        bt_multiply = (Button) view.findViewById(R.id.bt_multiply);
        bt_divide = (Button) view.findViewById(R.id.bt_divide);
        bt_equal = (Button) view.findViewById(R.id.bt_equal);
        bt_clear = (Button) view.findViewById(R.id.bt_clear);
        bt_del = (Button) view.findViewById(R.id.bt_del);
        tv_input = (TextView) view.findViewById(R.id.tv_input);
        tv_his = (TextView) view.findViewById(R.id.tv_history);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bt_0.setOnClickListener(this);
        bt_1.setOnClickListener(this);
        bt_2.setOnClickListener(this);
        bt_3.setOnClickListener(this);
        bt_4.setOnClickListener(this);
        bt_5.setOnClickListener(this);
        bt_6.setOnClickListener(this);
        bt_7.setOnClickListener(this);
        bt_8.setOnClickListener(this);
        bt_9.setOnClickListener(this);
        bt_dot.setOnClickListener(this);
        bt_plus.setOnClickListener(this);
        bt_minu.setOnClickListener(this);
        bt_multiply.setOnClickListener(this);
        bt_divide.setOnClickListener(this);
        bt_equal.setOnClickListener(this);
        bt_clear.setOnClickListener(this);
        bt_del.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_0:
            case R.id.bt_1:
            case R.id.bt_2:
            case R.id.bt_3:
            case R.id.bt_4:
            case R.id.bt_5:
            case R.id.bt_6:
            case R.id.bt_7:
            case R.id.bt_8:
            case R.id.bt_9:
            case R.id.bt_dot:
                if(!lastResult.equals("")){
                    lastResult = "";
                }
                String s = ((Button)view).getText().toString();
                sb.append(s);
                tv_input.setText(sb.toString());
                break;
            case R.id.bt_plus:
                if(sb.toString().length() > 0 || !lastResult.equals("")){
                    if(!lastResult.equals("")){
                        sb.append(lastResult);
                        tv_input.setText(sb.toString());
                    }
                    operationCount++;
                    opertation = Counts.ADD;
                    String plus = ((Button)view).getText().toString();
                    sb.append(plus);
                    tv_input.setText(sb.toString());
                }
                break;
            case R.id.bt_minus:
                if(sb.toString().length() > 0 || !lastResult.equals("")){
                    if(!lastResult.equals("")){
                        sb.append(lastResult);
                        tv_input.setText(sb.toString());
                    }
                    operationCount++;
                    opertation = Counts.MINUS;
                    String minus = ((Button)view).getText().toString();
                    sb.append(minus);
                    tv_input.setText(sb.toString());
                }
                break;
            case R.id.bt_multiply:
                if(sb.toString().length() > 0 || !lastResult.equals("")){
                    if(!lastResult.equals("")){
                        sb.append(lastResult);
                        tv_input.setText(sb.toString());
                    }
                    operationCount++;
                    opertation = Counts.MULTIPLY;
                    String multiply = ((Button)view).getText().toString();
                    sb.append(multiply);
                    tv_input.setText(sb.toString());
                }
                break;
            case R.id.bt_divide:
                if(sb.toString().length() > 0 || !lastResult.equals("")){
                    if(!lastResult.equals("")){
                        sb.append(lastResult);
                        tv_input.setText(sb.toString());
                    }
                    operationCount++;
                    opertation = Counts.DIVIDE;
                    String divide = ((Button)view).getText().toString();
                    sb.append(divide);
                    tv_input.setText(sb.toString());
                }
                break;
            case R.id.bt_equal:
                if(sb.toString().length() > 0){
                    if(isValid(sb)){
                        String equal = ((Button)view).getText().toString();
                        String result = Calculate.getResult(sb.toString());
                        sb.append(equal).append(result);
                        saveResult(sb);
                        lastResult = result;
                        tv_his.setText(sb.toString());
                        tv_input.setText("");
                        sb = new StringBuilder();
                        operationCount = 0;
                        opertation = null;
                    }else {
                        Toast.makeText(getActivity(), "输入有误！", Toast.LENGTH_SHORT).show();
                    }
//                    if(!isValid(sb) || getSecondParam(sb).equals("")){
//                        Toast.makeText(getActivity(), "输入有误！", Toast.LENGTH_SHORT).show();
//                    }else if(operationCount == 0){
//                        String equal = ((Button)view).getText().toString();
//                        String result = sb.toString();
//                        sb.append(equal).append(result);
//                        saveResult(sb);
//                        tv_his.setText(sb.toString());
//                        tv_input.setText("0");
//                        sb = new StringBuilder();
//                        operationCount = 0;
//                        opertation = null;
//                    }else {
//                        getOperation(sb);
//                        if(opertation.equals(Counts.DIVIDE) && getSecondParam(sb).equals("0")){
//                            Toast.makeText(getActivity(), "除数不能为0", Toast.LENGTH_SHORT).show();
//                        }else {
//                            String equal = ((Button)view).getText().toString();
//                            String result = opertation.values(getFirstParam(sb), getSecondParam(sb));
//                            sb.append(equal).append(result);
//                            saveResult(sb);
//                            lastResult = result;
//                            tv_his.setText(sb.toString());
//                            tv_input.setText("");
//                            sb = new StringBuilder();
//                            operationCount = 0;
//                            opertation = null;
//                        }
//                    }
                }
                break;
            case R.id.bt_clear:
                sb = new StringBuilder();
                tv_input.setText(sb.toString());
                tv_his.setText("");
                break;
            case R.id.bt_del:
                if(sb.length() > 0){
                    sb.deleteCharAt(sb.length() - 1);
                    tv_input.setText(sb.toString());
                }
                break;
        }
    }

    private boolean isValid(StringBuilder sb){
        String s = sb.toString();
        char[] chars = s.toCharArray();
        int count = 0;
        char tmp = '0';
        for(int i = 0; i < chars.length; i++){
            char last =chars[chars.length - 1];
            if(isOperator(last)){
                return false;
            }

            char c = chars[i];
            if(c == '÷' && chars[i + 1] == '0'){
                return false;
            }

            if(isOperator(c) && isOperator(chars[i + 1])){
                return false;
            }
        }
        return true;
    }

    private boolean isOperator(char c){
        if(c == '+' || c == '-' || c == 'x' || c == '÷'){
            return true;
        }else {
            return false;
        }
    }


//    private boolean isValid(StringBuilder sb) {
//        int count = 0;
//        String str = sb.toString();
//        if(str.contains("+")){
//            count++;
//        }
//        if(str.contains("-")){
//            count++;
//        }
//        if(str.contains("x")){
//            count++;
//        }
//        if(str.contains("÷")){
//            count++;
//        }
//
//        if(count > 1){
//            return false;
//        }else {
//            boolean b1 = isValid(sb, '.');
//            boolean b2 = isValid(sb, '+');
//            boolean b3 = isValid(sb, '-');
//            boolean b4 = isValid(sb, 'x');
//            boolean b5 = isValid(sb, '÷');
//            return b1 && b2 && b3 && b4 && b5;
//        }
//    }

//    private boolean isValid(StringBuilder sb, char ch) {
//        String str = sb.toString();
//        char[] array = str.toCharArray();
//        int count = 0;
//        for(char c :  array){
//            if(c == ch){
//                count++;
//            }
//        }
//        if(count > 1){
//            return false;
//        }else {
//            return true;
//        }
//    }

    private void saveResult(StringBuilder sb) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getTime(), sb.toString());
        editor.apply();
        Intent i = new Intent(HistoryFragment.ACTION_UPDATE);
        getActivity().sendBroadcast(i);
    }

//    private String getFirstParam(StringBuilder sb){
//        getOperation(sb);
//        String s = "";
//        if(opertation.equals(Counts.ADD)){
//            s = sb.toString().substring(0, sb.toString().lastIndexOf("+"));
//        }else if(opertation.equals(Counts.MINUS)){
//            s = sb.toString().substring(0, sb.toString().lastIndexOf("-"));
//        }else if(opertation.equals(Counts.MULTIPLY)){
//            s = sb.toString().substring(0, sb.toString().lastIndexOf("x"));
//        }else if(opertation.equals(Counts.DIVIDE)){
//            s = sb.toString().substring(0, sb.toString().lastIndexOf("÷"));
//        }
//        return s;
//    }
//
//    private void getOperation(StringBuilder sb) {
//        if(sb.toString().contains("+")){
//            opertation = Counts.ADD;
//        }else if(sb.toString().contains("-")){
//            opertation = Counts.MINUS;
//        }else if(sb.toString().contains("x")){
//            opertation = Counts.MULTIPLY;
//        }else if(sb.toString().contains("÷")){
//            opertation = Counts.DIVIDE;
//        }
//    }
//
//    private String getSecondParam(StringBuilder sb){
//        String s = null;
//        if(opertation.equals(Counts.ADD)){
//            s = sb.toString().substring(sb.toString().lastIndexOf("+") + 1);
//        }else if(opertation.equals(Counts.MINUS)){
//            s = sb.toString().substring(sb.toString().lastIndexOf("-") + 1);
//        }else if(opertation.equals(Counts.MULTIPLY)){
//            s = sb.toString().substring(sb.toString().lastIndexOf("x") + 1);
//        }else if(opertation.equals(Counts.DIVIDE)){
//            s = sb.toString().substring(sb.toString().lastIndexOf("÷") + 1);
//        }
//        return s;
//    }
//
    private static String getTime(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(date);
        return time;
    }

}
