package com.skyfree.bestcalculator.activity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.skyfree.bestcalculator.adapter.AdapterConvert;
import com.skyfree.bestcalculator.adapter.AdapterFunction;
import com.skyfree.bestcalculator.custom_interface.RecyclerItemClickListener;
import com.skyfree.bestcalculator.do_calculate.Calculator;
import com.skyfree.bestcalculator.do_calculate.Helpers;
import com.skyfree.bestcalculator.R;
import com.skyfree.bestcalculator.model.Convert;
import com.skyfree.bestcalculator.model.FunctionSetting;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private DrawerLayout mDrawer;
    private Toolbar mToolbar;
    private ActionBarDrawerToggle mToggle;
    private ListView mLvFunction;
    private TextView outputResult;
    private TextView shiftDisplay;
    private TextView degreeRad;
    private boolean isDegree = false;
    private boolean isInverse = false;
    private String lastResultObtain = "";
    private String currentDisplayedInput = "";
    private String inputToBeParsed = "";
    private Calculator mCalculator;
    private static String PREFS_NAME = "memory";
    private Button button0, button1, button2, button3, button4, button5, button6, button7,
            button8, button9, buttonClear, buttonDivide, buttonMultiply, buttonSubtract,
            buttonAdd, buttonPercentage, buttonEqual, buttonDecimal, closeParenthesis, openParenthesis, buttonAnswer,
            buttonSingleDelete, buttonExp;
    private TextView labelFactorial, labelCombination, labelPermutation, labelPi, labelE, labelComma, labelCubeRoot, labelCube,
            labelInverseX, labelInverseSin, labelInverseCos, labelInverseTan, labelExponential, labelTenPowerX, labelRCL,
            labelSTO, labelMMinus, labelFloat, labelDeg;
    private Button buttonSin, buttonLn, buttonCos, buttonLog, buttonTan, buttonSquareRoot, buttonXSquare, buttonYPowerX,
            buttonRnd;
    private Button buttonShift, buttonRad, buttonAbs, buttonMr, buttonMs, buttonMPlus;

    private AdapterFunction mAdapter;
    private AdapterConvert mAdapterConvert;
    private ArrayList<FunctionSetting> mListFunction;
    private ArrayList<Convert> mListConvert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        addToolbar();
        addInfoFunction();
    }

    private void theme1(){
    }

    private void addToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, 0, 0);
        mDrawer.addDrawerListener(mToggle);
        mToggle.syncState();
    }

    private void addInfoFunction() {
        mListFunction = new ArrayList<>();
        mListFunction.add(new FunctionSetting(R.drawable.ic_convert, "Convert"));
        mListFunction.add(new FunctionSetting(R.drawable.ic_invite, "Invite"));
        mListFunction.add(new FunctionSetting(R.drawable.ic_about, "About us"));
        mListFunction.add(new FunctionSetting(R.drawable.ic_rate, "Rate us"));
        mAdapter = new AdapterFunction(this, mListFunction);
        mLvFunction.setAdapter(mAdapter);
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mLvFunction = (ListView) findViewById(R.id.lvFunction);
        mLvFunction.setOnItemClickListener(this);
        mCalculator = new Calculator();
        outputResult = (TextView) findViewById(R.id.display);
        outputResult.setText("");
        shiftDisplay = (TextView) findViewById(R.id.shift_display);
        degreeRad = (TextView) findViewById(R.id.degree);
        button0 = (Button) findViewById(R.id.zero_button);
        button1 = (Button) findViewById(R.id.one_button);
        button2 = (Button) findViewById(R.id.two_button);
        button3 = (Button) findViewById(R.id.three_button);
        button4 = (Button) findViewById(R.id.four_button);
        button5 = (Button) findViewById(R.id.five_button);
        button6 = (Button) findViewById(R.id.six_button);
        button7 = (Button) findViewById(R.id.seven_button);
        button8 = (Button) findViewById(R.id.eight_button);
        button9 = (Button) findViewById(R.id.nine_button);
        buttonDivide = (Button) findViewById(R.id.division);
        buttonMultiply = (Button) findViewById(R.id.multiplication);
        buttonSubtract = (Button) findViewById(R.id.subtraction);
        buttonAdd = (Button) findViewById(R.id.addition);
        buttonPercentage = (Button) findViewById(R.id.percent);
        buttonDecimal = (Button) findViewById(R.id.dot);
        closeParenthesis = (Button) findViewById(R.id.close_bracket);
        openParenthesis = (Button) findViewById(R.id.open_bracket);
        buttonExp = (Button) findViewById(R.id.exp);
        buttonSquareRoot = (Button) findViewById(R.id.square_root);
        buttonXSquare = (Button) findViewById(R.id.x_square);
        buttonYPowerX = (Button) findViewById(R.id.x_power_y);
        buttonSin = (Button) findViewById(R.id.sin_sign);
        buttonCos = (Button) findViewById(R.id.cos_sign);
        buttonTan = (Button) findViewById(R.id.tan_sign);
        buttonLn = (Button) findViewById(R.id.natural_log);
        buttonLog = (Button) findViewById(R.id.log);
        buttonRnd = (Button) findViewById(R.id.hys);
        buttonDivide.setText(Html.fromHtml(Helpers.division));
        buttonSquareRoot.setText(Html.fromHtml(Helpers.squareRoot));
        buttonXSquare.setText(Html.fromHtml(Helpers.xSquare));
        buttonYPowerX.setText(Html.fromHtml(Helpers.yPowerX));
        buttonShift = (Button) findViewById(R.id.shift);
        buttonRad = (Button) findViewById(R.id.rad);
        buttonAbs = (Button) findViewById(R.id.abs);
        buttonMr = (Button) findViewById(R.id.mr);
        buttonMs = (Button) findViewById(R.id.ms);
        buttonMPlus = (Button) findViewById(R.id.m_plus);
        buttonClear = (Button) findViewById(R.id.clear);
        buttonSingleDelete = (Button) findViewById(R.id.single_delete);
        buttonEqual = (Button) findViewById(R.id.equal_sign);
        buttonAnswer = (Button) findViewById(R.id.ans);
        labelFactorial = (TextView) findViewById(R.id.factorial);
        labelCombination = (TextView) findViewById(R.id.combination);
        labelPermutation = (TextView) findViewById(R.id.permutation);
        labelPi = (TextView) findViewById(R.id.pi);
        labelE = (TextView) findViewById(R.id.e);
        labelComma = (TextView) findViewById(R.id.comma);
        labelCubeRoot = (TextView) findViewById(R.id.cube_root);
        labelCube = (TextView) findViewById(R.id.cube);
        labelInverseX = (TextView) findViewById(R.id.one_over_x);
        labelInverseSin = (TextView) findViewById(R.id.inverse_sin);
        labelInverseCos = (TextView) findViewById(R.id.inverse_cos);
        labelInverseTan = (TextView) findViewById(R.id.inverse_tan);
        labelExponential = (TextView) findViewById(R.id.expo);
        labelTenPowerX = (TextView) findViewById(R.id.ten_power_x);
        labelRCL = (TextView) findViewById(R.id.rcl);
        labelSTO = (TextView) findViewById(R.id.sto);
        labelMMinus = (TextView) findViewById(R.id.m_minus);
        labelFloat = (TextView) findViewById(R.id.float_number);
        labelDeg = (TextView) findViewById(R.id.degree);
        labelInverseSin.setText(Html.fromHtml(Helpers.inverseSin));
        labelInverseCos.setText(Html.fromHtml(Helpers.inverseCos));
        labelInverseTan.setText(Html.fromHtml(Helpers.inverseTan));
        labelExponential.setText(Html.fromHtml(Helpers.exponential));
        labelTenPowerX.setText(Html.fromHtml(Helpers.tenPowerX));
        labelCubeRoot.setText(Html.fromHtml(Helpers.cubeSquare));
        labelCube.setText(Html.fromHtml(Helpers.cubeRoot));
        labelPi.setText(Html.fromHtml(Helpers.pi));
        button0.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);
        buttonClear.setOnClickListener(this);
        buttonDivide.setOnClickListener(this);
        buttonMultiply.setOnClickListener(this);
        buttonSubtract.setOnClickListener(this);
        buttonAdd.setOnClickListener(this);
        buttonPercentage.setOnClickListener(this);
        buttonEqual.setOnClickListener(this);
        buttonDecimal.setOnClickListener(this);
        closeParenthesis.setOnClickListener(this);
        openParenthesis.setOnClickListener(this);
        buttonSingleDelete.setOnClickListener(this);
        buttonExp.setOnClickListener(this);
        buttonSquareRoot.setOnClickListener(this);
        buttonXSquare.setOnClickListener(this);
        buttonYPowerX.setOnClickListener(this);
        buttonSin.setOnClickListener(this);
        buttonCos.setOnClickListener(this);
        buttonTan.setOnClickListener(this);
        buttonLn.setOnClickListener(this);
        buttonLog.setOnClickListener(this);
        buttonRnd.setOnClickListener(this);
        buttonShift.setOnClickListener(this);
        buttonRad.setOnClickListener(this);
        buttonAbs.setOnClickListener(this);
        buttonMr.setOnClickListener(this);
        buttonMs.setOnClickListener(this);
        buttonMPlus.setOnClickListener(this);
        buttonAnswer.setOnClickListener(this);
    }

    private void obtainInputValues(String input) {
        switch (input) {
            case "0":
                currentDisplayedInput += "0";
                inputToBeParsed += "0";
                break;
            case "1":
                if (isInverse) {
                    currentDisplayedInput += "π";
                    inputToBeParsed += "pi";
                } else {
                    currentDisplayedInput += "1";
                    inputToBeParsed += "1";
                }
                toggleInverse();
                toggleShiftButton();
                break;
            case "2":
                if (isInverse) {
                    currentDisplayedInput += "e";
                    inputToBeParsed += "e";
                } else {
                    currentDisplayedInput += "2";
                    inputToBeParsed += "2";
                }
                toggleInverse();
                toggleShiftButton();
                break;
            case "3":
                if (isInverse) {
                    currentDisplayedInput += ",";
                    inputToBeParsed += ",";
                } else {
                    currentDisplayedInput += "3";
                    inputToBeParsed += "3";
                }
                toggleInverse();
                toggleShiftButton();
                break;
            case "4":
                if (isInverse) {
                    currentDisplayedInput += "!(";
                    inputToBeParsed += "!(";
                } else {
                    currentDisplayedInput += "4";
                    inputToBeParsed += "4";
                }
                toggleInverse();
                toggleShiftButton();
                break;
            case "5":
                if (isInverse) {
                    currentDisplayedInput += "comb(";
                    inputToBeParsed += "comb(";
                } else {
                    currentDisplayedInput += "5";
                    inputToBeParsed += "5";
                }
                toggleInverse();
                toggleShiftButton();
                break;
            case "6":
                if (isInverse) {
                    currentDisplayedInput += "permu(";
                    inputToBeParsed += "permu(";
                } else {
                    currentDisplayedInput += "6";
                    inputToBeParsed += "6";
                }
                toggleInverse();
                toggleShiftButton();
                break;
            case "7":
                currentDisplayedInput += "7";
                inputToBeParsed += "7";
                break;
            case "8":
                currentDisplayedInput += "8";
                inputToBeParsed += "8";
                break;
            case "9":
                currentDisplayedInput += "9";
                inputToBeParsed += "9";
                break;
            case ".":
                currentDisplayedInput += ".";
                inputToBeParsed += ".";
                break;
            case "+":
                currentDisplayedInput += "+";
                inputToBeParsed += "+";
                break;
            case "-":
                currentDisplayedInput += "-";
                inputToBeParsed += "-";
                break;
            case "÷":
                currentDisplayedInput += "÷";
                inputToBeParsed += "/";
                break;
            case "x":
                currentDisplayedInput += "*";
                inputToBeParsed += "*";
                break;
            case "(":
                currentDisplayedInput += "(";
                inputToBeParsed += "(";
                break;
            case ")":
                currentDisplayedInput += ")";
                inputToBeParsed += ")";
                break;
            case "%":
                if (isInverse) {
                    currentDisplayedInput += "1÷";
                    inputToBeParsed += "1÷";
                } else {
                    currentDisplayedInput += "%";
                    inputToBeParsed += "%";
                }
                toggleInverse();
                toggleShiftButton();
                break;
            case "ln":
                if (isInverse) {
                    currentDisplayedInput += "e^";
                    inputToBeParsed += "e^";
                } else {
                    currentDisplayedInput += "ln(";
                    inputToBeParsed += "ln(";
                }
                toggleInverse();
                toggleShiftButton();
                break;
            case "log":
                if (isInverse) {
                    currentDisplayedInput += "10^";
                    inputToBeParsed += "10^";
                } else {
                    currentDisplayedInput += "log(";
                    inputToBeParsed += "log(";
                }
                toggleInverse();
                toggleShiftButton();
                break;
            case "√":
                if (isInverse) {
                    currentDisplayedInput += "3√(";
                    inputToBeParsed += "crt(";
                } else {
                    currentDisplayedInput += "√(";
                    inputToBeParsed += "sqrt(";
                }
                toggleInverse();
                toggleShiftButton();
                break;
            case "Yx":
                currentDisplayedInput += "^";
                inputToBeParsed += "^";
                break;
            case "sin":
                if (isInverse) {
                    currentDisplayedInput += "asin(";
                    inputToBeParsed += "asin(";
                } else {
                    currentDisplayedInput += "sin(";
                    inputToBeParsed += "sin(";
                }
                toggleInverse();
                toggleShiftButton();
                break;
            case "cos":
                if (isInverse) {
                    currentDisplayedInput += "acos(";
                    inputToBeParsed += "acos(";
                } else {
                    currentDisplayedInput += "cos(";
                    inputToBeParsed += "cos(";
                }
                toggleInverse();
                toggleShiftButton();
                break;
            case "tan":
                if (isInverse) {
                    currentDisplayedInput += "atan(";
                    inputToBeParsed += "atan(";
                } else {
                    currentDisplayedInput += "tan(";
                    inputToBeParsed += "tan(";
                }
                toggleInverse();
                toggleShiftButton();
                break;
            case "exp":
                currentDisplayedInput += "e^";
                inputToBeParsed += "e^";
                break;
            case "x2":
                if (isInverse) {
                    currentDisplayedInput += "^3";
                    inputToBeParsed += "^3";
                } else {
                    currentDisplayedInput += "^2";
                    inputToBeParsed += "^2";
                }
                toggleInverse();
                toggleShiftButton();
                break;
            case "rnd":
                double ran = Math.random();
                currentDisplayedInput += String.valueOf(ran);
                inputToBeParsed += String.valueOf(ran);
                break;
            case "ABS":
                currentDisplayedInput += "abs(";
                inputToBeParsed += "abs(";
                break;
            case "MR":
                String mValue = getStoredPreferenceValue(MainActivity.this);
                String result = removeTrailingZero(mValue);
                if (!result.equals("0")) {
                    currentDisplayedInput += result;
                    inputToBeParsed += result;
                }
                break;
            case "MS":
                clearMemoryStorage(MainActivity.this);
                break;
            case "M+":
                if (isInverse) {
                    double inputValueMinus = isANumber(outputResult.getText().toString());
                    if (!Double.isNaN(inputValueMinus)) {
                        subtractMemoryStorage(MainActivity.this, inputValueMinus);
                    }
                } else {
                    double inputValue = isANumber(outputResult.getText().toString());
                    if (!Double.isNaN(inputValue)) {
                        addToMemoryStorage(MainActivity.this, inputValue);
                    }
                }
                toggleInverse();
                toggleShiftButton();
                break;
        }
        outputResult.setText(currentDisplayedInput);
    }

    @Override
    public void onClick(View view) {
        Button button = (Button) view;
        String data = button.getText().toString();
        if (data.equals("AC")) {
            outputResult.setText("");
            currentDisplayedInput = "";
            inputToBeParsed = "";
        } else if (data.equals("Del")) {
            String enteredInput = outputResult.getText().toString();
            if (enteredInput.length() > 0) {
                enteredInput = enteredInput.substring(0, enteredInput.length() - 1);
                currentDisplayedInput = enteredInput;
                inputToBeParsed = enteredInput;
                outputResult.setText(currentDisplayedInput);
            }
        } else if (data.equals("=")) {
            String enteredInput = outputResult.getText().toString();
            String resultObject = mCalculator.getResult(currentDisplayedInput, inputToBeParsed);
            lastResultObtain = resultObject;
            outputResult.setText(removeTrailingZero(resultObject));
        } else if (data.equals("Ans")) {
            String enteredInput = outputResult.getText().toString();
            enteredInput += lastResultObtain;
            outputResult.setText(enteredInput);
        } else if (data.equals("SHIFT")) {
            isInverse = !isInverse;
            toggleShiftButton();
        } else if (data.equals("RAD")) {
            buttonRad.setText("DEG");
            degreeRad.setText("RAD");
        } else if (data.equals("DEG")) {
            buttonRad.setText("RAD");
            degreeRad.setText("DEG");
        } else {
            obtainInputValues(data);
        }
    }

    private String removeTrailingZero(String formattingInput) {
        if (!formattingInput.contains(".")) {
            return formattingInput;
        }
        int dotPosition = formattingInput.indexOf(".");
        String newValue = formattingInput.substring(dotPosition, formattingInput.length());
        if (newValue.equals(".0")) {
            return formattingInput.substring(0, dotPosition);
        }
        return formattingInput;
    }

    private void toggleInverse() {
        if (isInverse) {
            isInverse = false;
        }
    }

    private void toggleShiftButton() {
        if (isInverse) {
            shiftDisplay.setText("SHIFT");
        } else {
            shiftDisplay.setText("");
        }
    }

    private double isANumber(String numberInput) {
        double result = Double.NaN;
        try {
            result = Double.parseDouble(numberInput);
        } catch (NumberFormatException nfe) {
        }
        return result;
    }

    private void addToMemoryStorage(Context context, double inputToStore) {
        float returnPrefValue = getPreference(context);
        float newValue = returnPrefValue + (float) inputToStore;
        setPreference(context, newValue);
    }

    private void subtractMemoryStorage(Context context, double inputToStore) {
        float returnPrefValue = getPreference(context);
        float newValue = returnPrefValue - (float) inputToStore;
        setPreference(context, newValue);
    }

    private void clearMemoryStorage(Context context) {
        setPreference(context, 0);
    }

    private String getStoredPreferenceValue(Context context) {
        float returnedValue = getPreference(context);
        return String.valueOf(returnedValue);
    }

    static public boolean setPreference(Context c, float value) {
        SharedPreferences settings = c.getSharedPreferences(PREFS_NAME, 0);
        settings = c.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putFloat("key", value);
        return editor.commit();
    }

    static public float getPreference(Context c) {
        SharedPreferences settings = c.getSharedPreferences(PREFS_NAME, 0);
        settings = c.getSharedPreferences(PREFS_NAME, 0);
        float value = settings.getFloat("key", 0);
        return value;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                optionConvert();
                break;
            case 1:
                invite();
                break;
            case 2:
                aboutUs();
                break;
            case 3:
                rateUs();
                break;
        }
    }

    private void setTheme() {
    }

    private void rateUs() {
        try {
            Intent rateIntent = rateIntentForUrl("market://details");
            startActivity(rateIntent);
        } catch (ActivityNotFoundException e) {
            Intent rateIntent = rateIntentForUrl("https://play.google.com/store/apps/details");
            startActivity(rateIntent);
        }
    }

    private Intent rateIntentForUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("%s?id=%s", url, getPackageName())));
        int flags = Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK;
        if (Build.VERSION.SDK_INT >= 21) {
            flags |= Intent.FLAG_ACTIVITY_NEW_DOCUMENT;
        } else {
            //noinspection deprecation
            flags |= Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET;
        }
        intent.addFlags(flags);
        return intent;
    }

    private void aboutUs() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.about_us, null);
        dialogBuilder.setView(dialogView);

        TextView mBtnOK = (TextView) dialogView.findViewById(R.id.btn_ok_abous);
        final AlertDialog alertShowListConvert = dialogBuilder.create();
        alertShowListConvert.show();
        mBtnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertShowListConvert.cancel();
            }
        });
    }

    private void invite() {
        Intent it = new Intent(Intent.ACTION_SEND);
        it.setType("text/plain");
        it.putExtra(Intent.EXTRA_SUBJECT, "My app name");
        String strShareMessage = "\nLet me recommend you this application\n\n";
        strShareMessage = strShareMessage + "https://play.google.com/store/apps/details?id=" + getPackageName();
        Uri screenshotUri = Uri.parse("android.resource://packagename/drawable/image_name");
        it.setType("image/png");
        it.putExtra(Intent.EXTRA_STREAM, screenshotUri);
        it.putExtra(Intent.EXTRA_TEXT, strShareMessage);
        startActivity(Intent.createChooser(it, "Share via"));
    }

    private void optionConvert() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.convert, null);
        dialogBuilder.setView(dialogView);

        mListConvert = new ArrayList<>();
        mListConvert.add(new Convert(R.drawable.ic_cv_chieudai, "Length"));
        mListConvert.add(new Convert(R.drawable.ic_cv_dientich, "Acreage"));
        mListConvert.add(new Convert(R.drawable.ic_cv_thetich, "Volume"));
        mListConvert.add(new Convert(R.drawable.ic_cv_nhietdo, "Temperature"));
        mListConvert.add(new Convert(R.drawable.ic_cv_thoigian, "Time"));
        mListConvert.add(new Convert(R.drawable.ic_cv_tocdo, "Speed"));
        mListConvert.add(new Convert(R.drawable.ic_cv_trongluong, "Weight"));

        RecyclerView mRcv = (RecyclerView) dialogView.findViewById(R.id.rcv_convert);
        mRcv.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        mRcv.setLayoutManager(layoutManager);
        AdapterConvert mAdapterConvert = new AdapterConvert(this, mListConvert);
        mRcv.setAdapter(mAdapterConvert);

        final AlertDialog alertShowListConvert = dialogBuilder.create();
        alertShowListConvert.show();

        mRcv.addOnItemTouchListener(new RecyclerItemClickListener(this, mRcv, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position) {
                    case 0:
                        convertChieuDai();
                        alertShowListConvert.cancel();
                        break;
                    case 1:
                        convertDienTich();
                        alertShowListConvert.cancel();
                        break;
                    case 2:
                        convertTheTich();
                        alertShowListConvert.cancel();
                        break;
                    case 3:
                        convertNhietDo();
                        alertShowListConvert.cancel();
                        break;
                    case 4:
                        convertThoiGian();
                        alertShowListConvert.cancel();
                        break;
                    case 5:
                        convertTocDo();
                        alertShowListConvert.cancel();
                        break;
                    case 6:
                        convertTrongLuong();
                        alertShowListConvert.cancel();
                        break;
                }
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
    }

    private void convertChieuDai() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.convert_chieudai, null);
        dialogBuilder.setView(dialogView);

        final Spinner mSpTopM = (Spinner) dialogView.findViewById(R.id.spinner_top_m);
        final Spinner mSpBottomM = (Spinner) dialogView.findViewById(R.id.spinner_bottom_m);
        final EditText mEdtTopM = (EditText) dialogView.findViewById(R.id.edt_top_m);
        final TextView mTvBottomM = (TextView) dialogView.findViewById(R.id.tv_bottom_m);
        Button mBtnConvertM = (Button) dialogView.findViewById(R.id.btn_convert_m);
        Button mBtnCancelM = (Button) dialogView.findViewById(R.id.btn_cancel_m);

        final AlertDialog alertShowConvertM = dialogBuilder.create();
        alertShowConvertM.show();

        mBtnConvertM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mStrSpTop = String.valueOf(mSpTopM.getSelectedItem());
                final String mStrSpBottom = String.valueOf(mSpBottomM.getSelectedItem());
                switch (mStrSpTop) {
                    case "cm":
                        switch (mStrSpBottom) {
                            case "cm":
                                mTvBottomM.setText(mEdtTopM.getText().toString());
                                break;
                            case "dm":
                                mTvBottomM.setText(Double.parseDouble(mEdtTopM.getText().toString()) / 10 + "");
                                break;
                            case "m":
                                mTvBottomM.setText(Double.parseDouble(mEdtTopM.getText().toString()) / 100 + "");
                                break;
                            case "km":
                                mTvBottomM.setText(Double.parseDouble(mEdtTopM.getText().toString()) / 1000 + "");
                                break;
                            case "mile":
                                mTvBottomM.setText(Double.parseDouble(mEdtTopM.getText().toString()) / 1.609344 / 1000 + "");
                                break;

                        }
                        break;
                    case "dm":
                        switch (mStrSpBottom) {
                            case "cm":
                                mTvBottomM.setText(Double.parseDouble(mEdtTopM.getText().toString()) * 10 + "");
                                break;
                            case "dm":
                                mTvBottomM.setText(mEdtTopM.getText().toString());
                                break;
                            case "m":
                                mTvBottomM.setText(Double.parseDouble(mEdtTopM.getText().toString()) / 10 + "");
                                break;
                            case "km":
                                mTvBottomM.setText(Double.parseDouble(mEdtTopM.getText().toString()) / 100 + "");
                                break;
                            case "mile":
                                mTvBottomM.setText(Double.parseDouble(mEdtTopM.getText().toString()) / 100 / 1.609344 + "");
                                break;

                        }
                        break;
                    case "m":
                        switch (mStrSpBottom) {
                            case "cm":
                                mTvBottomM.setText(Double.parseDouble(mEdtTopM.getText().toString()) * 100 + "");
                                break;
                            case "dm":
                                mTvBottomM.setText(Double.parseDouble(mEdtTopM.getText().toString()) * 10 + "");
                                break;
                            case "m":
                                mTvBottomM.setText(mEdtTopM.getText().toString());
                                break;
                            case "km":
                                mTvBottomM.setText(Double.parseDouble(mEdtTopM.getText().toString()) / 1000 + "");
                                break;
                            case "mile":
                                mTvBottomM.setText(Double.parseDouble(mEdtTopM.getText().toString()) / 1000 / 1.609344 + "");
                                break;

                        }
                        break;
                    case "km":
                        switch (mStrSpBottom) {
                            case "cm":
                                mTvBottomM.setText(Double.parseDouble(mEdtTopM.getText().toString()) * 100000 + "");
                                break;
                            case "dm":
                                mTvBottomM.setText(Double.parseDouble(mEdtTopM.getText().toString()) * 10000 + "");
                                break;
                            case "m":
                                mTvBottomM.setText(Double.parseDouble(mEdtTopM.getText().toString()) * 1000 + "");
                                break;
                            case "km":
                                mTvBottomM.setText(mEdtTopM.getText().toString());
                                break;
                            case "mile":
                                mTvBottomM.setText(Double.parseDouble(mEdtTopM.getText().toString()) / 1.609344 + "");
                                break;

                        }
                        break;
                    case "mile":
                        switch (mStrSpBottom) {
                            case "cm":
                                mTvBottomM.setText(Double.parseDouble(mEdtTopM.getText().toString()) * 100000 * 1.609344 + "");
                                break;
                            case "dm":
                                mTvBottomM.setText(Double.parseDouble(mEdtTopM.getText().toString()) * 10000 * 1.609344 + "");
                                break;
                            case "m":
                                mTvBottomM.setText(Double.parseDouble(mEdtTopM.getText().toString()) * 1000 * 1.609344 + "");
                                break;
                            case "km":
                                mTvBottomM.setText(Double.parseDouble(mEdtTopM.getText().toString()) * 1.609344 + "");
                                break;
                            case "mile":
                                mTvBottomM.setText(mEdtTopM.getText().toString());
                                break;

                        }
                        break;

                }
            }
        });

        mBtnCancelM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertShowConvertM.cancel();
            }
        });
    }

    private void convertDienTich() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.convert_dientich, null);
        dialogBuilder.setView(dialogView);

        final Spinner mSpTopDienTich = (Spinner) dialogView.findViewById(R.id.spinner_top_dientich);
        final Spinner mSpBottomDienTich = (Spinner) dialogView.findViewById(R.id.spinner_bottom_dientich);
        final EditText mEdtTopDienTich = (EditText) dialogView.findViewById(R.id.edt_top_dientich);
        final TextView mTvBottomDienTich = (TextView) dialogView.findViewById(R.id.tv_bottom_dientich);
        Button mBtnConvertDienTich = (Button) dialogView.findViewById(R.id.btn_convert_dientich);
        Button mBtnCancelDienTich = (Button) dialogView.findViewById(R.id.btn_cancel_dientich);

        final AlertDialog alertShowConvertDienTich = dialogBuilder.create();
        alertShowConvertDienTich.show();

        mBtnCancelDienTich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertShowConvertDienTich.cancel();
            }
        });

        mBtnConvertDienTich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mStrSpTop = String.valueOf(mSpTopDienTich.getSelectedItem());
                final String mStrSpBottom = String.valueOf(mSpBottomDienTich.getSelectedItem());
                switch (mStrSpTop) {
                    case "m²":
                        switch (mStrSpBottom) {
                            case "m²":
                                mTvBottomDienTich.setText(mEdtTopDienTich.getText().toString());
                                break;
                            case "km²":
                                mTvBottomDienTich.setText(Double.parseDouble(mEdtTopDienTich.getText().toString()) / 1000000 + "");
                                break;
                            case "ha":
                                mTvBottomDienTich.setText(Double.parseDouble(mEdtTopDienTich.getText().toString()) / 10000 + "");
                                break;
                        }
                        break;
                    case "km²":
                        switch (mStrSpBottom) {
                            case "m²":
                                mTvBottomDienTich.setText(Double.parseDouble(mEdtTopDienTich.getText().toString()) * 1000000 + "");
                                break;
                            case "km²":
                                mTvBottomDienTich.setText(mEdtTopDienTich.getText().toString());
                                break;
                            case "ha":
                                mTvBottomDienTich.setText(Double.parseDouble(mEdtTopDienTich.getText().toString()) * 100 + "");
                                break;
                        }
                        break;
                    case "ha":
                        switch (mStrSpBottom) {
                            case "m²":
                                mTvBottomDienTich.setText(Double.parseDouble(mEdtTopDienTich.getText().toString()) * 10000 + "");
                                break;
                            case "km²":
                                mTvBottomDienTich.setText(Double.parseDouble(mEdtTopDienTich.getText().toString()) / 100 + "");
                                break;
                            case "ha":
                                mTvBottomDienTich.setText(mEdtTopDienTich.getText().toString());
                                break;
                        }
                        break;
                }
            }
        });
    }

    private void convertTheTich() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.convert_thetich, null);
        dialogBuilder.setView(dialogView);

        final Spinner mSpTopTheTich = (Spinner) dialogView.findViewById(R.id.spinner_top_dientich);
        final Spinner mSpBottomTheTich = (Spinner) dialogView.findViewById(R.id.spinner_bottom_dientich);
        final EditText mEdtTopTheTich = (EditText) dialogView.findViewById(R.id.edt_top_dientich);
        final TextView mTvBottomTheTich = (TextView) dialogView.findViewById(R.id.tv_bottom_dientich);
        Button mBtnConvertTheTich = (Button) dialogView.findViewById(R.id.btn_convert_dientich);
        Button mBtnCancelTheTich = (Button) dialogView.findViewById(R.id.btn_cancel_dientich);

        final AlertDialog alertShowConvertTheTich = dialogBuilder.create();
        alertShowConvertTheTich.show();

        mBtnCancelTheTich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertShowConvertTheTich.cancel();
            }
        });

        mBtnConvertTheTich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mStrSpTop = String.valueOf(mSpTopTheTich.getSelectedItem());
                final String mStrSpBottom = String.valueOf(mSpBottomTheTich.getSelectedItem());
                switch (mStrSpTop) {
                    case "m³":
                        switch (mStrSpBottom) {
                            case "m³":
                                mTvBottomTheTich.setText(mEdtTopTheTich.getText().toString());
                                break;
                            case "lit":
                                mTvBottomTheTich.setText(Double.parseDouble(mEdtTopTheTich.getText().toString()) / 1000 + "");
                                break;
                            case "foot³":
                                mTvBottomTheTich.setText(Double.parseDouble(mEdtTopTheTich.getText().toString()) / 35.314 + "");
                                break;
                        }
                        break;
                    case "lit":
                        switch (mStrSpBottom) {
                            case "m³":
                                mTvBottomTheTich.setText(Double.parseDouble(mEdtTopTheTich.getText().toString()) * 1000 + "");
                                break;
                            case "lit":
                                mTvBottomTheTich.setText(mEdtTopTheTich.getText().toString());
                                break;
                            case "foot³":
                                mTvBottomTheTich.setText(Double.parseDouble(mEdtTopTheTich.getText().toString()) * 0.035314 + "");
                                break;
                        }
                        break;
                    case "foot³":
                        switch (mStrSpBottom) {
                            case "m³":
                                mTvBottomTheTich.setText(Double.parseDouble(mEdtTopTheTich.getText().toString()) * 0.0283 + "");
                                break;
                            case "lit":
                                mTvBottomTheTich.setText(Double.parseDouble(mEdtTopTheTich.getText().toString()) * 28.3 + "");
                                break;
                            case "foot³":
                                mTvBottomTheTich.setText(mEdtTopTheTich.getText().toString());
                                break;
                        }
                        break;
                }
            }
        });
    }

    private void convertNhietDo() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.convert_nhietdo, null);
        dialogBuilder.setView(dialogView);

        final Spinner mSpTopNhietDo = (Spinner) dialogView.findViewById(R.id.spinner_top_nhietdo);
        final Spinner mSpBottomNhietDo = (Spinner) dialogView.findViewById(R.id.spinner_bottom_nhietdo);
        final EditText mEdtTopNhietDo = (EditText) dialogView.findViewById(R.id.edt_top_nhietdo);
        final TextView mTvBottomNhietDo = (TextView) dialogView.findViewById(R.id.tv_bottom_nhietdo);
        Button mBtnConvertNhietDo = (Button) dialogView.findViewById(R.id.btn_convert_nhietdo);
        Button mBtnCancelNhietDo = (Button) dialogView.findViewById(R.id.btn_cancel_nhietdo);

        final AlertDialog alertShowConvertNhietDo = dialogBuilder.create();
        alertShowConvertNhietDo.show();

        mBtnCancelNhietDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertShowConvertNhietDo.cancel();
            }
        });

        mBtnConvertNhietDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mStrSpTop = String.valueOf(mSpTopNhietDo.getSelectedItem());
                final String mStrSpBottom = String.valueOf(mSpBottomNhietDo.getSelectedItem());
                switch (mStrSpTop) {
                    case "°C":
                        switch (mStrSpBottom) {
                            case "°C":
                                mTvBottomNhietDo.setText(mEdtTopNhietDo.getText().toString());
                                break;
                            case "°F":
                                mTvBottomNhietDo.setText(Double.parseDouble(mEdtTopNhietDo.getText().toString()) * 1.8 + 32 + "");
                                break;
                        }
                        break;
                    case "°F":
                        switch (mStrSpBottom) {
                            case "°C":
                                mTvBottomNhietDo.setText(Double.parseDouble(mEdtTopNhietDo.getText().toString()) * 0.56 - 17.78 + "");
                                break;
                            case "°F":
                                mTvBottomNhietDo.setText(mEdtTopNhietDo.getText().toString());
                                break;
                        }
                        break;
                }
            }
        });
    }

    private void convertThoiGian() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.convert_thoigian, null);
        dialogBuilder.setView(dialogView);

        final Spinner mSpTopTheTich = (Spinner) dialogView.findViewById(R.id.spinner_top_thoigian);
        final Spinner mSpBottomTheTich = (Spinner) dialogView.findViewById(R.id.spinner_bottom_thoigian);
        final EditText mEdtTopTheTich = (EditText) dialogView.findViewById(R.id.edt_top_thoigian);
        final TextView mTvBottomTheTich = (TextView) dialogView.findViewById(R.id.tv_bottom_thoigian);
        Button mBtnConvertTheTich = (Button) dialogView.findViewById(R.id.btn_convert_thoigian);
        Button mBtnCancelTheTich = (Button) dialogView.findViewById(R.id.btn_cancel_thoigian);

        final AlertDialog alertShowConvertTheTich = dialogBuilder.create();
        alertShowConvertTheTich.show();

        mBtnCancelTheTich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertShowConvertTheTich.cancel();
            }
        });

        mBtnConvertTheTich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mStrSpTop = String.valueOf(mSpTopTheTich.getSelectedItem());
                final String mStrSpBottom = String.valueOf(mSpBottomTheTich.getSelectedItem());
                switch (mStrSpTop) {
                    case "year":
                        switch (mStrSpBottom) {
                            case "year":
                                mTvBottomTheTich.setText(mEdtTopTheTich.getText().toString());
                                break;
                            case "day":
                                mTvBottomTheTich.setText(Double.parseDouble(mEdtTopTheTich.getText().toString()) * 365 + "");
                                break;
                            case "hour":
                                mTvBottomTheTich.setText(Double.parseDouble(mEdtTopTheTich.getText().toString()) * 365 * 24 + "");
                                break;
                            case "minute":
                                mTvBottomTheTich.setText(Double.parseDouble(mEdtTopTheTich.getText().toString()) * 365 * 24 * 60 + "");
                                break;
                            case "second":
                                mTvBottomTheTich.setText(Double.parseDouble(mEdtTopTheTich.getText().toString()) * 365 * 24 * 60 * 60 + "");
                                break;
                        }
                        break;
                    case "day":
                        switch (mStrSpBottom) {
                            case "year":
                                mTvBottomTheTich.setText(Double.parseDouble(mEdtTopTheTich.getText().toString()) / 365 + "");
                                break;
                            case "day":
                                mTvBottomTheTich.setText(mEdtTopTheTich.getText().toString());
                                break;
                            case "hour":
                                mTvBottomTheTich.setText(Double.parseDouble(mEdtTopTheTich.getText().toString()) * 24 + "");
                                break;
                            case "minute":
                                mTvBottomTheTich.setText(Double.parseDouble(mEdtTopTheTich.getText().toString()) * 24 * 60 + "");
                                break;
                            case "second":
                                mTvBottomTheTich.setText(Double.parseDouble(mEdtTopTheTich.getText().toString()) * 24 * 60 * 60 + "");
                                break;
                        }
                        break;
                    case "hour":
                        switch (mStrSpBottom) {
                            case "year":
                                mTvBottomTheTich.setText(Double.parseDouble(mEdtTopTheTich.getText().toString()) / 24 / 365 + "");
                                break;
                            case "day":
                                mTvBottomTheTich.setText(Double.parseDouble(mEdtTopTheTich.getText().toString()) / 24 + "");
                                break;
                            case "hour":
                                mTvBottomTheTich.setText(mEdtTopTheTich.getText().toString());
                                break;
                            case "minute":
                                mTvBottomTheTich.setText(Double.parseDouble(mEdtTopTheTich.getText().toString()) * 60 + "");
                                break;
                            case "second":
                                mTvBottomTheTich.setText(Double.parseDouble(mEdtTopTheTich.getText().toString()) * 60 * 60 + "");
                                break;
                        }
                        break;
                    case "minute":
                        switch (mStrSpBottom) {
                            case "year":
                                mTvBottomTheTich.setText(Double.parseDouble(mEdtTopTheTich.getText().toString()) / 24 / 365 / 60 + "");
                                break;
                            case "day":
                                mTvBottomTheTich.setText(Double.parseDouble(mEdtTopTheTich.getText().toString()) / 24 / 60 + "");
                                break;
                            case "hour":
                                mTvBottomTheTich.setText(Double.parseDouble(mEdtTopTheTich.getText().toString()) / 60 + "");
                                break;
                            case "minute":
                                mTvBottomTheTich.setText(mEdtTopTheTich.getText().toString());
                                break;
                            case "second":
                                mTvBottomTheTich.setText(Double.parseDouble(mEdtTopTheTich.getText().toString()) * 60 + "");
                                break;
                        }
                        break;
                    case "second":
                        switch (mStrSpBottom) {
                            case "year":
                                mTvBottomTheTich.setText(Double.parseDouble(mEdtTopTheTich.getText().toString()) / 60 / 60 / 24 / 365 + "");
                                break;
                            case "day":
                                mTvBottomTheTich.setText(Double.parseDouble(mEdtTopTheTich.getText().toString()) / 60 / 60 / 24 + "");
                                break;
                            case "hour":
                                mTvBottomTheTich.setText(Double.parseDouble(mEdtTopTheTich.getText().toString()) / 60 / 60 + "");
                                break;
                            case "minute":
                                mTvBottomTheTich.setText(Double.parseDouble(mEdtTopTheTich.getText().toString()) / 60 + "");
                                break;
                            case "second":
                                mTvBottomTheTich.setText(mEdtTopTheTich.getText().toString());
                                break;
                        }
                        break;
                }
            }
        });
    }

    private void convertTocDo() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.convert_tocdo, null);
        dialogBuilder.setView(dialogView);

        final Spinner mSpTopTheTich = (Spinner) dialogView.findViewById(R.id.spinner_top_tocdo);
        final Spinner mSpBottomTheTich = (Spinner) dialogView.findViewById(R.id.spinner_bottom_tocdo);
        final EditText mEdtTopTheTich = (EditText) dialogView.findViewById(R.id.edt_top_tocdo);
        final TextView mTvBottomTheTich = (TextView) dialogView.findViewById(R.id.tv_bottom_tocdo);
        Button mBtnConvertTheTich = (Button) dialogView.findViewById(R.id.btn_convert_tocdo);
        Button mBtnCancelTheTich = (Button) dialogView.findViewById(R.id.btn_cancel_tocdo);

        final AlertDialog alertShowConvertTheTich = dialogBuilder.create();
        alertShowConvertTheTich.show();

        mBtnCancelTheTich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertShowConvertTheTich.cancel();
            }
        });

        mBtnConvertTheTich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mStrSpTop = String.valueOf(mSpTopTheTich.getSelectedItem());
                final String mStrSpBottom = String.valueOf(mSpBottomTheTich.getSelectedItem());
                switch (mStrSpTop) {
                    case "m/s":
                        switch (mStrSpBottom) {
                            case "m/s":
                                mTvBottomTheTich.setText(mEdtTopTheTich.getText().toString());
                                break;
                            case "km/s":
                                mTvBottomTheTich.setText(Double.parseDouble(mEdtTopTheTich.getText().toString()) * 0.001 + "");
                                break;
                            case "km/h":
                                mTvBottomTheTich.setText(Double.parseDouble(mEdtTopTheTich.getText().toString()) * 3.6 + "");
                                break;
                            case "mile/h":
                                mTvBottomTheTich.setText(Double.parseDouble(mEdtTopTheTich.getText().toString()) * 2.2369 + "");
                                break;
                        }
                        break;
                    case "km/s":
                        switch (mStrSpBottom) {
                            case "m/s":
                                mTvBottomTheTich.setText(Double.parseDouble(mEdtTopTheTich.getText().toString()) * 1000 + "");
                                break;
                            case "km/s":
                                mTvBottomTheTich.setText(mEdtTopTheTich.getText().toString());
                                break;
                            case "km/h":
                                mTvBottomTheTich.setText(Double.parseDouble(mEdtTopTheTich.getText().toString()) * 3600 + "");
                                break;
                            case "mile/h":
                                mTvBottomTheTich.setText(Double.parseDouble(mEdtTopTheTich.getText().toString()) * 2236.9 + "");
                                break;
                        }
                        break;
                    case "km/h":
                        switch (mStrSpBottom) {
                            case "m/s":
                                mTvBottomTheTich.setText(mEdtTopTheTich.getText().toString());
                                break;
                            case "km/s":
                                mTvBottomTheTich.setText(Double.parseDouble(mEdtTopTheTich.getText().toString()) * 0.000278 + "");
                                break;
                            case "km/h":
                                mTvBottomTheTich.setText(mEdtTopTheTich.getText().toString());
                                break;
                            case "mile/h":
                                mTvBottomTheTich.setText(Double.parseDouble(mEdtTopTheTich.getText().toString()) * 0.62137 + "");
                                break;
                        }
                        break;
                    case "mile/h":
                        switch (mStrSpBottom) {
                            case "m/s":
                                mTvBottomTheTich.setText(Double.parseDouble(mEdtTopTheTich.getText().toString()) * 0.44704 + "");
                                break;
                            case "km/s":
                                mTvBottomTheTich.setText(Double.parseDouble(mEdtTopTheTich.getText().toString()) * 0.00044704 + "");
                                break;
                            case "km/h":
                                mTvBottomTheTich.setText(Double.parseDouble(mEdtTopTheTich.getText().toString()) * 1.61 + "");
                                break;
                            case "mile/h":
                                mTvBottomTheTich.setText(mEdtTopTheTich.getText().toString());
                                break;
                        }
                        break;
                }
            }
        });
    }

    private void convertTrongLuong() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.convert_trongluong, null);
        dialogBuilder.setView(dialogView);

        final Spinner mSpTopTheTich = (Spinner) dialogView.findViewById(R.id.spinner_top_trongluong);
        final Spinner mSpBottomTheTich = (Spinner) dialogView.findViewById(R.id.spinner_bottom_trongluong);
        final EditText mEdtTopTheTich = (EditText) dialogView.findViewById(R.id.edt_top_trongluong);
        final TextView mTvBottomTheTich = (TextView) dialogView.findViewById(R.id.tv_bottom_trongluong);
        Button mBtnConvertTheTich = (Button) dialogView.findViewById(R.id.btn_convert_trongluong);
        Button mBtnCancelTheTich = (Button) dialogView.findViewById(R.id.btn_cancel_trongluong);

        final AlertDialog alertShowConvertTheTich = dialogBuilder.create();
        alertShowConvertTheTich.show();

        mBtnCancelTheTich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertShowConvertTheTich.cancel();
            }
        });

        mBtnConvertTheTich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mStrSpTop = String.valueOf(mSpTopTheTich.getSelectedItem());
                final String mStrSpBottom = String.valueOf(mSpBottomTheTich.getSelectedItem());
                switch (mStrSpTop) {
                    case "ton":
                        switch (mStrSpBottom) {
                            case "ton":
                                mTvBottomTheTich.setText(mEdtTopTheTich.getText().toString());
                                break;
                            case "kg":
                                mTvBottomTheTich.setText(Double.parseDouble(mEdtTopTheTich.getText().toString()) * 1000 + "");
                                break;
                            case "g":
                                mTvBottomTheTich.setText(Double.parseDouble(mEdtTopTheTich.getText().toString()) * 1000 * 1000 + "");
                                break;
                            case "mg":
                                mTvBottomTheTich.setText(Double.parseDouble(mEdtTopTheTich.getText().toString()) * 1000 * 1000 * 1000 + "");
                                break;
                        }
                        break;
                    case "kg":
                        switch (mStrSpBottom) {
                            case "ton":
                                mTvBottomTheTich.setText(Double.parseDouble(mEdtTopTheTich.getText().toString()) / 1000 + "");
                                break;
                            case "kg":
                                mTvBottomTheTich.setText(mEdtTopTheTich.getText().toString());
                                break;
                            case "g":
                                mTvBottomTheTich.setText(Double.parseDouble(mEdtTopTheTich.getText().toString()) * 1000 + "");
                                break;
                            case "mg":
                                mTvBottomTheTich.setText(Double.parseDouble(mEdtTopTheTich.getText().toString()) * 1000000 + "");
                                break;
                        }
                        break;
                    case "g":
                        switch (mStrSpBottom) {
                            case "ton":
                                mTvBottomTheTich.setText(Double.parseDouble(mEdtTopTheTich.getText().toString()) / 1000000 + "");
                                break;
                            case "kg":
                                mTvBottomTheTich.setText(Double.parseDouble(mEdtTopTheTich.getText().toString()) / 1000 + "");
                                break;
                            case "g":
                                mTvBottomTheTich.setText(mEdtTopTheTich.getText().toString());
                                break;
                            case "mg":
                                mTvBottomTheTich.setText(Double.parseDouble(mEdtTopTheTich.getText().toString()) * 1000 + "");
                                break;
                        }
                        break;
                    case "mg":
                        switch (mStrSpBottom) {
                            case "ton":
                                mTvBottomTheTich.setText(Double.parseDouble(mEdtTopTheTich.getText().toString()) / 1000000000 + "");
                                break;
                            case "kg":
                                mTvBottomTheTich.setText(Double.parseDouble(mEdtTopTheTich.getText().toString()) / 1000000 + "");
                                break;
                            case "g":
                                mTvBottomTheTich.setText(Double.parseDouble(mEdtTopTheTich.getText().toString()) / 1000 + "");
                                break;
                            case "mg":
                                mTvBottomTheTich.setText(mEdtTopTheTich.getText().toString());
                                break;

                        }
                        break;
                }
            }
        });
    }
}
