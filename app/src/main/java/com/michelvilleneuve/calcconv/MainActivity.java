package com.michelvilleneuve.calcconv;

import static android.text.Html.fromHtml;
import static com.michelvilleneuve.calcconv.HistoryStorage.PREF_KEY;
import static java.lang.Math.abs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.michelvilleneuve.calculator.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

public class MainActivity extends AppCompatActivity implements OnSharedPreferenceChangeListener,OnMenuItemClickListener, View.OnLongClickListener {

    private LinearLayout layoutMain, layoutIn, layoutFtIn;

    private static final String TAG = "MainActivity";

    private final String FIRST_NUMBER = "firstNumber";
    private final String SECOND_NUMBER = "secondNumber";
    private final String OPERATOR = "operator";
    private final String RESULT = "result";
    private final String WHOLE_NUMBER = "wholeNumber";


    private Context context;

    Vibrator vibrator;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.showOperation)
    EditText showOperation;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.displayResult)
    TextView displayResult;


//    @SuppressLint("NonConstantResourceId")
 //   @BindView(R.id.displayFtInResult)
 //   TextView displayFtInResult;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.displayMMResult)
    TextView displayMMResult;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.displayINResult)
    TextView displayINResult;

    double firstNumber = 0;
    double secondNumber = 0;
    Double result = null;
 //   Double result2;
    char mOperation;

    private Integer wholeNumber;
    private double checkResult;

    private ArrayList<History> historyList;
    private ListView listView;
    private HistoryAdapter adapter;
    private boolean convertToMilliliters = true;
    private boolean convertToCentimeters = true;
    private boolean convertToKilogrammes = true;
    private boolean convertTogrammes = true;
    private boolean convertToLiters = true;
    private boolean convertToMeters = true;
    private boolean convertToCelsius = true;
    private boolean convertToMillimeters = true;
    private boolean convertToMKilometers = true;
    private boolean convertToSquaremeters = true;
    private boolean convertToOnces = true;

    Integer in1 = null;
    Integer in2 = null;
    Double in3 = null;
    Double in5 = null;
    String in4 = null;
    String btnPercent = null;
    private SpannableStringBuilder spannableStringBuilder;

    private Button[] buttons;
    private String number;
    private String all;
    private double previousResult = 0.0;

    int consel = 0;

    @BindView(R.id.btnCV5)
    Button toggleButtonInMM;
    @BindView(R.id.btnCV1)
    Button toggleButtonlbsKg;
    @BindView(R.id.btnCV2)
    Button toggleButtonGaLt;
    @BindView(R.id.btnCV6)
    Button toggleButtonFtM;
    @BindView(R.id.btnCV8)
    Button toggleButtonFahCel;
    @BindView(R.id.btnCV4)
    Button toggleButtonCupml;
    @BindView(R.id.btnCV3)
    Button toggleButtonMilesKm;
    @BindView(R.id.btnCV7)
    Button toggleButtonSqfSqm;



    private static final String PREFS_NAME = "MyPrefs";
    private static final String BG_IMAGE_KEY = "bgImage";
    private static final String NO_BG_IMAGE = "bg1";

    private LinearLayout yourLinearLayout;
    private Button changeBackgroundButton;
    private Button changeMMtoCMButton;

    private String[] backgroundImages = {"bg1", "bg2", "bg3", "bg4", "bg5", "bg6", "bg7"};
    private int currentBackgroundIndex = 0;
    private boolean toggleToIncm = false;

    @SuppressLint({"ClickableViewAccessibility", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        yourLinearLayout = findViewById(R.id.linearLayoutOne);
        changeBackgroundButton = findViewById(R.id.btnEquals);
        changeMMtoCMButton = findViewById(R.id.btnCV5);

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String backgroundImage = prefs.getString(BG_IMAGE_KEY, NO_BG_IMAGE); // Default bg1 if not found

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.mv_logo_24);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        Button btnCV1 = findViewById(R.id.btnCV1);
 //       Button btnCV2 = findViewById(R.id.btnCV2);
 //       Button btnCV3 = findViewById(R.id.btnCV3);
        Button btnCV4 = findViewById(R.id.btnCV4);
        Button btnCV5 = findViewById(R.id.btnCV5);
//        Button btnCV6 = findViewById(R.id.btnCV6);
 //       Button btnCV7 = findViewById(R.id.btnCV7);
 //       Button btnCV8 = findViewById(R.id.btnCV8);
        Button btnClear = findViewById(R.id.btnClear);

        btnCV1.setOnLongClickListener(this);
 //       btnCV2.setOnLongClickListener(this);
 //       btnCV3.setOnLongClickListener(this);
        btnCV4.setOnLongClickListener(this);
        btnCV5.setOnLongClickListener(this);
 //       btnCV6.setOnLongClickListener(this);
 //       btnCV7.setOnLongClickListener(this);
 //       btnCV8.setOnLongClickListener(this);
        btnClear.setOnLongClickListener(this);


        buttons = new Button[]{
                findViewById(R.id.btn1),
                findViewById(R.id.btn2),
                findViewById(R.id.btn3),
                findViewById(R.id.btn4),
                findViewById(R.id.btn5),
                findViewById(R.id.btn6),
                findViewById(R.id.btn7),
                findViewById(R.id.btn8),
                findViewById(R.id.btn9),
                findViewById(R.id.btnZero),
        };



        for (final Button button : buttons) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    performHapticFeedback();
                }
            });
        }

        changeBackgroundButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // Toggle between background images
                currentBackgroundIndex = (currentBackgroundIndex + 1) % backgroundImages.length;
                String newBackground = backgroundImages[currentBackgroundIndex];

                // Change the background image
                changeBackgroundImage(newBackground);

                // Return true to consume the long click event
                return true;
            }
        });

        // Set the background image
        int resId = getResources().getIdentifier(backgroundImage, "drawable", getPackageName());
        yourLinearLayout.setBackgroundResource(resId);

        ButterKnife.bind(this);


        Button btn = findViewById(R.id.btnPercent);

        historyList = HistoryStorage.loadData(this);

        showOperation.setOnTouchListener(onTouchListener);

        if (savedInstanceState != null) {
            firstNumber = savedInstanceState.getDouble(FIRST_NUMBER);
            secondNumber = savedInstanceState.getDouble(SECOND_NUMBER);
            mOperation = savedInstanceState.getChar(OPERATOR);
            result = savedInstanceState.getDouble(RESULT);
            wholeNumber = savedInstanceState.getInt(WHOLE_NUMBER);

     //       displayColouredResult();

            if (Double.isNaN(result) || result == 0.0) {
                displayResult.setText("");
            }
        }

        // Register the listener
        HistoryStorage.registerPref(this, this);

        // Set the background image based on the current month
        setBackgroundBasedOnMonth();
     //   updateBackgroundImage();
     //   inmm();

        // Add click listener for btnCV5 to toggle between inmm and incm on short press
  //      btnCV5.setOnClickListener(new View.OnClickListener() {
   //         @Override
   //         public void onClick(View v) {
   //             if (toggleToIncm) {
   //                 incm();
  //              } else {
  //                  inmm();
  //              }
  //              toggleToIncm = !toggleToIncm;
  //          }
  //      });
    }


    /**
     * Sets different numbers
     *
     * @param number is the number that the user choose.
     */
    private void setAll2(String number) {
        if (showOperation.getText().toString().isEmpty()) {
            // If the operation field is empty, set the first number
            this.all = number;
        } else {
            // If there's already something in the operation field, assume it's the result of a previous calculation
            // Set the previousResult to this value
            previousResult = Double.parseDouble(showOperation.getText().toString());
            // Clear the operation field
            showOperation.setText("");
            // Set the new operation with the entered number
            this.all = number;
        }
        showOperation.setText(this.all);
    }

    private void setAll(String number) {
        this.all = showOperation.getText() + number;
        showOperation.setText(all);
    }
    @Override
    protected void onResume() {
        super.onResume();
        // Update the background image when the activity is resumed
        setBackgroundBasedOnMonth();
    }

    // Method to update the background image based on the current month
    private void setBackgroundBasedOnMonth() {
        // Determine the background image based on the current month
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
        String newBackground = getBackgroundImageForMonth(currentMonth);

        // Change the background image
        changeBackgroundImage(newBackground);
    }

    // Method to update the background image based on the stored preference
    private void updateBackgroundImage() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String backgroundImage = prefs.getString(BG_IMAGE_KEY, NO_BG_IMAGE); // Default bg1 if not found
        int resId = getResources().getIdentifier(backgroundImage, "drawable", getPackageName());
        yourLinearLayout.setBackgroundResource(resId);
    }

    // Method to change the background image and save the preference
    private void changeBackgroundImage(String imageName) {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(BG_IMAGE_KEY, imageName);
        editor.apply();

        updateBackgroundImage();
    }

    // Method to get the background image for the given month
    private String getBackgroundImageForMonth(int month) {
        switch (month) {
            case Calendar.JANUARY: //Max
                return "bg2";
            case Calendar.MARCH: //Sarah
                return "bg1";
            case Calendar.APRIL: //Ellie
                return "bg3";
            case Calendar.MAY: //Chloé
                return "bg1";
            case Calendar.SEPTEMBER: //Sophie
                return "bg1";
            case Calendar.NOVEMBER: //Gigi
                return "bg1";

            // Add cases for other months as needed
            default:
                return "bg1"; // Default to bg1 for unspecified months
        }
    }
    /**
     * Sets different operations
     *
     * @param operation is the operation that the user choose.
     */
    @SuppressLint("SetTextI18n")
    private void setOperation(char operation) {

        String text;
        Log.d(TAG, "operation: ");
        if (showOperation.getText().toString().isEmpty()) {
            showOperation.setText("");
        }else {
                // Check if the last character is an operator
                String currentText = showOperation.getText().toString();
                char lastChar = currentText.charAt(currentText.length() - 1);

                if (lastChar == '+' || lastChar == '-' || lastChar == 'x' || lastChar == '÷') {
                    Log.d(TAG, "second operation symbol: ");
                    // If the last character is an operator, perform the calculation
                    equals(null);  // Call the equals method to perform the calculation
     //               return; // Exit to avoid setting the operation
                }

            try {
                // Get the first Number and parse it into double
                firstNumber = Double.parseDouble(currentText);
                // Store specified operation into mOperation
                mOperation = operation;
                // Show mathematical operation on screen
                String newText = currentText + operation;
                showOperation.setText(newText);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Do another operation
     //   displayResult.setText("");
        if (!TextUtils.isEmpty(displayResult.getText().toString())
        ) {
            // Get teh result
            String result = displayResult.getText().toString();

            // Remove Equal sign
            Double textWithoutEqual = Double.parseDouble(result.replace("=", " "));
            // Store the result as the first number
            firstNumber = Double.parseDouble(String.valueOf(textWithoutEqual));

            // Clear the result screen
            displayResult.setText("");
            // Store another operation sign
            mOperation = operation;

            // Get the whole number of the result
            Integer wholeNumber = textWithoutEqual.intValue();
            // Check if result is whole number or not
            double checkResult = textWithoutEqual / wholeNumber;

            if (firstNumber == 0.0) {
                showOperation.setText(0 + String.valueOf(operation));
                // If result is not equal one
            } else if (checkResult != 1) {
                // Show the first number and the operation sign on screen
                showOperation.setText(firstNumber + String.valueOf(operation));
            } else {
                // Show the first number and the operation sign on screen
                showOperation.setText(wholeNumber + String.valueOf(operation));
            }
        }


    }


    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.btnClear)
    public void clear(View view) {
        performHapticFeedback();
  //      if (displayResult.getText().length() > 0 || showOperation.getText().length() > 0) {
            showOperation.setText("");
            displayResult.setText("");
            displayINResult.setText("");
            displayMMResult.setText("");
            consel = 0;
            result = null;
            all = null;
            number = null;
            convertToMillimeters = true;
            convertToKilogrammes = true;
            convertToMilliliters = true;
        toggleButtonInMM.setText("In◄ ►MM  \n☝ ► CM");
        toggleButtonlbsKg.setText("Lbs◄ ►Kg \n☝ ► g");
        toggleButtonCupml.setText("Cup◄ ►ml  \n☝ ►  Oz");

        //       }
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.btnAdd)
    public void add(View view) {
        performHapticFeedback();
        setOperation('+');
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.btnSub)
    public void subtract(View view) {
        performHapticFeedback();
        setOperation('-');
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.btnMul)
    public void multiply(View view) {
        performHapticFeedback();
        setOperation('x');
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.btnDiv)
    public void divide(View view) {
        performHapticFeedback();
        setOperation('÷');
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.btnPercent)
    public void percent(View view) {
        performHapticFeedback();
        setOperation('%');
    }


    /**
     * Undo the last number.
     */
    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.btnBS)
    public void backSpace(View view) {
        performHapticFeedback();
        String txt = showOperation.getText().toString();
        if (txt.length() > 0) {
            txt = txt.substring(0, txt.length() - 1);
            showOperation.setText(txt);
        }
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.btnZero)
    public void zero(View view) {
        performHapticFeedback();
        deletePreviousResult();
        setAll("0");
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.btn1)
    public void one(View view) {
        performHapticFeedback();
        deletePreviousResult();
        setAll("1");
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.btn2)
    public void two(View view) {
        performHapticFeedback();
        deletePreviousResult();
        setAll("2");
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.btn3)
    public void three(View view) {
        performHapticFeedback();
        deletePreviousResult();
        setAll("3");
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.btn4)
    public void four(View view) {
        performHapticFeedback();
        deletePreviousResult();
        setAll("4");
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.btn5)
    public void five(View view) {
        performHapticFeedback();
        deletePreviousResult();
        setAll("5");
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.btn6)
    public void six(View view) {
        performHapticFeedback();
        deletePreviousResult();
        setAll("6");
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.btn7)
    public void seven(View view) {
        performHapticFeedback();
        deletePreviousResult();
        setAll("7");
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.btn8)
    public void eight(View view) {
        performHapticFeedback();
        deletePreviousResult();
        setAll("8");
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.btn9)
    public void nine(View view) {
        performHapticFeedback();
        deletePreviousResult();
        setAll("9");
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.btnPoint)
    public void point(View view) {
        performHapticFeedback();
        deletePreviousResult();
        setAll(".");
    }

    private void performHapticFeedback() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            buttons[0].performHapticFeedback(HapticFeedbackConstants.CONFIRM);
        } else {
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            if (vibrator != null) {
                // Vibrate for 50 milliseconds
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
                }
            }
        }
    }

    private void performHapticFeedback2() {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        // Check if the device has a vibrator
        if (vibrator != null && vibrator.hasVibrator()) {
            // Vibrate for 20 milliseconds
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(5, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                // Deprecated in API 26 (Android 8.0 Oreo)
                vibrator.vibrate(10);
            }
        }
    }
    DecimalFormat df = new DecimalFormat("#.##");
    DecimalFormat df3 = new DecimalFormat("#.###");
    DecimalFormat df2 = new DecimalFormat("#.####");

    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    @OnClick(R.id.btnCV5)
    public void inmm() {

        if (all == null){
            // Handle the case when result is null, for example, show an error message
            displayMMResult.setText("Need to enter a");
            displayINResult.setText("number first");
            return;
        }
        if (result == null) {
            result = Double.parseDouble(all);
        }

        if (convertToMillimeters) {
            performHapticFeedback();
            // Convert millimeters to inches
            // Toggle between converting inches to millimeters and millimeters to inches
            String fullText = "mm " + (df.format(abs(result) * 25.4));
            String smallerText = "mm ";
            SpannableString spannableString = new SpannableString(fullText);
            int startIndex = fullText.indexOf(smallerText);
            int endIndex = startIndex + smallerText.length();
            spannableString.setSpan(new RelativeSizeSpan(0.5f), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            String fullText2 = "In " + (df.format(result));
            String smallerText2 = "In ";
            SpannableString spannableString2 = new SpannableString(fullText2);
            int startIndex2 = fullText2.indexOf(smallerText2);
            int endIndex2 = startIndex2 + smallerText2.length();
            spannableString2.setSpan(new RelativeSizeSpan(0.5f), startIndex2, endIndex2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            displayMMResult.setText(spannableString);
            displayINResult.setText(spannableString2);
            toggleButtonInMM.setText("MM◄ ►In \n☝ ► CM"); // Change button text to "MM-In"
        } else {
            // Convert inches to millimeters
            // Toggle between converting inches to millimeters and millimeters to inches
            performHapticFeedback();
            String fullText = "In " + (df.format(abs(result) / 25.4));
            String smallerText = "In ";
            SpannableString spannableString = new SpannableString(fullText);
            int startIndex = fullText.indexOf(smallerText);
            int endIndex = startIndex + smallerText.length();
            spannableString.setSpan(new RelativeSizeSpan(0.5f), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            String fullText2 = "mm " + (df.format(result));
            String smallerText2 = "mm ";
            SpannableString spannableString2 = new SpannableString(fullText2);
            int startIndex2 = fullText2.indexOf(smallerText2);
            int endIndex2 = startIndex2 + smallerText2.length();
            spannableString2.setSpan(new RelativeSizeSpan(0.5f), startIndex2, endIndex2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            displayMMResult.setText (spannableString);
            displayINResult.setText(spannableString2);
            toggleButtonInMM.setText("In◄ ►MM  \n☝ ► CM"); // Change button text to "In-MM"
        }

        // Toggle the conversion direction for the next click
        convertToMillimeters = !convertToMillimeters;
    }
    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    @OnLongClick(R.id.btnCV5)
    public boolean incm() {

        if (all == null){
            // Handle the case when result is null, for example, show an error message
            displayMMResult.setText("Need to enter a");
            displayINResult.setText("number first");
            return true;
        }
        if (result == null) {
            result = Double.parseDouble(all);
        }

        if (convertToCentimeters) {
            performHapticFeedback();
            // Convert millimeters to inches
            // Toggle between converting inches to millimeters and millimeters to inches
            String fullText = "cm " + (df.format(abs(result) * 2.54));
            String smallerText = "cm ";
            SpannableString spannableString = new SpannableString(fullText);
            int startIndex = fullText.indexOf(smallerText);
            int endIndex = startIndex + smallerText.length();
            spannableString.setSpan(new RelativeSizeSpan(0.5f), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            String fullText2 = "In " + (df.format(result));
            String smallerText2 = "In ";
            SpannableString spannableString2 = new SpannableString(fullText2);
            int startIndex2 = fullText2.indexOf(smallerText2);
            int endIndex2 = startIndex2 + smallerText2.length();
            spannableString2.setSpan(new RelativeSizeSpan(0.5f), startIndex2, endIndex2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            displayMMResult.setText(spannableString);
            displayINResult.setText(spannableString2);
            toggleButtonInMM.setText("CM◄ ►In \n☝"); // Change button text to "MM-In"
        } else {
            // Convert inches to millimeters
            // Toggle between converting inches to millimeters and millimeters to inches
            performHapticFeedback();
            String fullText = "In " + (df.format(abs(result) / 2.54));
            String smallerText = "In ";
            SpannableString spannableString = new SpannableString(fullText);
            int startIndex = fullText.indexOf(smallerText);
            int endIndex = startIndex + smallerText.length();
            spannableString.setSpan(new RelativeSizeSpan(0.5f), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            String fullText2 = "cm " + (df.format(result));
            String smallerText2 = "cm ";
            SpannableString spannableString2 = new SpannableString(fullText2);
            int startIndex2 = fullText2.indexOf(smallerText2);
            int endIndex2 = startIndex2 + smallerText2.length();
            spannableString2.setSpan(new RelativeSizeSpan(0.5f), startIndex2, endIndex2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            displayMMResult.setText (spannableString);
            displayINResult.setText(spannableString2);
            toggleButtonInMM.setText("In◄ ►CM \n☝"); // Change button text to "In-MM"
        }

        // Toggle the conversion direction for the next click
        convertToCentimeters = !convertToCentimeters;
        return true;
    }

    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    @OnClick(R.id.btnCV1)
    public void lbskg() {

        if (all == null){
            // Handle the case when result is null, for example, show an error message
            displayMMResult.setText("Need to enter a");
            displayINResult.setText("number first");
            return;
        }
        if (result == null) {
            result = Double.parseDouble(all);
        }

        // Toggle between converting inches to millimeters and millimeters to inches
        if (convertToKilogrammes) {
            // Convert lbs to kilo
            performHapticFeedback();
            String fullText = "Kg " + (df.format((abs(result)) / 2.20462));
            String smallerText = "Kg ";
            SpannableString spannableString = new SpannableString(fullText);
            int startIndex = fullText.indexOf(smallerText);
            int endIndex = startIndex + smallerText.length();
            spannableString.setSpan(new RelativeSizeSpan(0.5f), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            String fullText2 = "Lbs " + (df.format(result));
            String smallerText2 = "Lbs ";
            SpannableString spannableString2 = new SpannableString(fullText2);
            int startIndex2 = fullText2.indexOf(smallerText2);
            int endIndex2 = startIndex2 + smallerText2.length();
            spannableString2.setSpan(new RelativeSizeSpan(0.5f), startIndex2, endIndex2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            displayMMResult.setText((spannableString));
            displayINResult.setText(spannableString2);
            toggleButtonlbsKg.setText("Kg◄ ►Lbs \n☝ ► g"); // Change button text to "MM-In"
        } else {
            // Convert kilo to lbs
            performHapticFeedback();
            String fullText = "lbs " + (df.format((abs(result)) * 2.20462));
            String smallerText = "lbs ";
            SpannableString spannableString = new SpannableString(fullText);
            int startIndex = fullText.indexOf(smallerText);
            int endIndex = startIndex + smallerText.length();
            spannableString.setSpan(new RelativeSizeSpan(0.5f), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            String fullText2 = "Kg " + (df.format(result));
            String smallerText2 = "Kg ";
            SpannableString spannableString2 = new SpannableString(fullText2);
            int startIndex2 = fullText2.indexOf(smallerText2);
            int endIndex2 = startIndex2 + smallerText2.length();
            spannableString2.setSpan(new RelativeSizeSpan(0.5f), startIndex2, endIndex2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            displayMMResult.setText((spannableString));
            displayINResult.setText(spannableString2);
            toggleButtonlbsKg.setText("Lbs◄ ►Kg \n☝ ► g"); // Change button text to "In-MM"
        }

        // Toggle the conversion direction for the next click
        convertToKilogrammes = !convertToKilogrammes;
    }
    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    @OnLongClick(R.id.btnCV1)
    public boolean lbsg() {

        if (all == null){
            // Handle the case when result is null, for example, show an error message
            displayMMResult.setText("Need to enter a");
            displayINResult.setText("number first");
            return true;
        }
        if (result == null) {
            result = Double.parseDouble(all);
        }

        // Toggle between converting inches to millimeters and millimeters to inches
        if (convertTogrammes) {
            // Convert lbs to kilo
            performHapticFeedback();
            String fullText = "g " + (df.format((abs(result)) / 0.0022045));
            String smallerText = "g ";
            SpannableString spannableString = new SpannableString(fullText);
            int startIndex = fullText.indexOf(smallerText);
            int endIndex = startIndex + smallerText.length();
            spannableString.setSpan(new RelativeSizeSpan(0.5f), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            String fullText2 = "Lbs " + (df.format(result));
            String smallerText2 = "Lbs ";
            SpannableString spannableString2 = new SpannableString(fullText2);
            int startIndex2 = fullText2.indexOf(smallerText2);
            int endIndex2 = startIndex2 + smallerText2.length();
            spannableString2.setSpan(new RelativeSizeSpan(0.5f), startIndex2, endIndex2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            displayMMResult.setText((spannableString));
            displayINResult.setText(spannableString2);
            toggleButtonlbsKg.setText("g◄ ►Lbs \n☝"); // Change button text to "MM-In"
        } else {
            // Convert kilo to lbs
            performHapticFeedback();
            String fullText = "lbs " + (df.format((abs(result)) * 0.0022045));
            String smallerText = "lbs ";
            SpannableString spannableString = new SpannableString(fullText);
            int startIndex = fullText.indexOf(smallerText);
            int endIndex = startIndex + smallerText.length();
            spannableString.setSpan(new RelativeSizeSpan(0.5f), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            String fullText2 = "g " + (df.format(result));
            String smallerText2 = "g ";
            SpannableString spannableString2 = new SpannableString(fullText2);
            int startIndex2 = fullText2.indexOf(smallerText2);
            int endIndex2 = startIndex2 + smallerText2.length();
            spannableString2.setSpan(new RelativeSizeSpan(0.5f), startIndex2, endIndex2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            displayMMResult.setText((spannableString));
            displayINResult.setText(spannableString2);
            toggleButtonlbsKg.setText("Lbs◄ ►g \n☝"); // Change button text to "In-MM"
        }

        // Toggle the conversion direction for the next click
        convertTogrammes = !convertTogrammes;
        return true;
    }


    //******************************************************************************
    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    @OnClick(R.id.btnCV2)
    public void GaLt() {

        if (all == null){
            // Handle the case when result is null, for example, show an error message
            displayMMResult.setText("Need to enter a");
            displayINResult.setText("number first");
            return;
        }
        if (result == null) {
            result = Double.parseDouble(all);
        }
        // Toggle between converting inches to millimeters and millimeters to inches
        if (convertToLiters) {
            // Convert lbs to kilo
            performHapticFeedback();
            String fullText = "Lt " + (df.format((abs(result)) * 3.78541));
            String smallerText = "Lt ";
            SpannableString spannableString = new SpannableString(fullText);
            int startIndex = fullText.indexOf(smallerText);
            int endIndex = startIndex + smallerText.length();
            spannableString.setSpan(new RelativeSizeSpan(0.5f), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            String fullText2 = "Ga " + (df.format(result));
            String smallerText2 = "Ga ";
            SpannableString spannableString2 = new SpannableString(fullText2);
            int startIndex2 = fullText2.indexOf(smallerText2);
            int endIndex2 = startIndex2 + smallerText2.length();
            spannableString2.setSpan(new RelativeSizeSpan(0.5f), startIndex2, endIndex2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            displayMMResult.setText((spannableString));
            displayINResult.setText(spannableString2);
            toggleButtonGaLt.setText("Lt◄ ►Ga"); // Change button text to "MM-In"
        } else {
            performHapticFeedback();
            // Convert kilo to lbs
            String fullText = "Ga " + (df.format((abs(result)) / 3.78541));
            String smallerText = "Ga ";
            SpannableString spannableString = new SpannableString(fullText);
            int startIndex = fullText.indexOf(smallerText);
            int endIndex = startIndex + smallerText.length();
            spannableString.setSpan(new RelativeSizeSpan(0.5f), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            String fullText2 = "Lt " + (df.format(result));
            String smallerText2 = "Lt ";
            SpannableString spannableString2 = new SpannableString(fullText2);
            int startIndex2 = fullText2.indexOf(smallerText2);
            int endIndex2 = startIndex2 + smallerText2.length();
            spannableString2.setSpan(new RelativeSizeSpan(0.5f), startIndex2, endIndex2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            displayMMResult.setText((spannableString));
            displayINResult.setText(spannableString2);
            toggleButtonGaLt.setText("Ga◄ ►Lt"); // Change button text to "In-MM"
        }

        // Toggle the conversion direction for the next click
        convertToLiters = !convertToLiters;
    }

    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    @OnClick(R.id.btnCV6)
    public void FtM() {

        if (all == null){
            // Handle the case when result is null, for example, show an error message
            displayMMResult.setText("Need to enter a");
            displayINResult.setText("number first");
            return;
        }
        if (result == null) {
            result = Double.parseDouble(all);
        }
        // Toggle between converting inches to millimeters and millimeters to inches
        if (convertToMeters) {
            // Convert lbs to kilo
            performHapticFeedback();
            String fullText = "M " + (df.format((abs(result)) / 3.28084));
            String smallerText = "M ";
            SpannableString spannableString = new SpannableString(fullText);
            int startIndex = fullText.indexOf(smallerText);
            int endIndex = startIndex + smallerText.length();
            spannableString.setSpan(new RelativeSizeSpan(0.5f), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            String fullText2 = "Ft " + (df.format(result));
            String smallerText2 = "Ft ";
            SpannableString spannableString2 = new SpannableString(fullText2);
            int startIndex2 = fullText2.indexOf(smallerText2);
            int endIndex2 = startIndex2 + smallerText2.length();
            spannableString2.setSpan(new RelativeSizeSpan(0.5f), startIndex2, endIndex2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            displayMMResult.setText((spannableString));
            displayINResult.setText(spannableString2);
            toggleButtonFtM.setText("M◄ ►Ft"); // Change button text to "MM-In"
        } else {
            // Convert kilo to lbs
            performHapticFeedback();
            String fullText = "Ft " + (df.format((abs(result)) * 3.28084));
            String smallerText = "Ft ";
            SpannableString spannableString = new SpannableString(fullText);
            int startIndex = fullText.indexOf(smallerText);
            int endIndex = startIndex + smallerText.length();
            spannableString.setSpan(new RelativeSizeSpan(0.5f), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            String fullText2 = "M " + (df.format(result));
            String smallerText2 = "M ";
            SpannableString spannableString2 = new SpannableString(fullText2);
            int startIndex2 = fullText2.indexOf(smallerText2);
            int endIndex2 = startIndex2 + smallerText2.length();
            spannableString2.setSpan(new RelativeSizeSpan(0.5f), startIndex2, endIndex2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            displayMMResult.setText((spannableString));
            displayINResult.setText(spannableString2);
            toggleButtonFtM.setText("Ft◄ ►M"); // Change button text to "In-MM"
        }

        // Toggle the conversion direction for the next click
        convertToMeters = !convertToMeters;

    }

    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    @OnClick(R.id.btnCV8)
    public void FahCel() {

        if (all == null){
            // Handle the case when result is null, for example, show an error message
            displayMMResult.setText("Need to enter a");
            displayINResult.setText("number first");
            return;
        }
        if (result == null) {
            result = Double.parseDouble(all);
        }
        // Toggle between converting inches to millimeters and millimeters to inches
        if (convertToCelsius) {
            // Convert lbs to kilo
            performHapticFeedback();
            String fullText = "°C " + (df.format(((abs(result)) - 32)*0.5556));
            String smallerText = "°C ";
            SpannableString spannableString = new SpannableString(fullText);
            int startIndex = fullText.indexOf(smallerText);
            int endIndex = startIndex + smallerText.length();
            spannableString.setSpan(new RelativeSizeSpan(0.5f), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            String fullText2 = "°F " + (df.format(result));
            String smallerText2 = "°F ";
            SpannableString spannableString2 = new SpannableString(fullText2);
            int startIndex2 = fullText2.indexOf(smallerText2);
            int endIndex2 = startIndex2 + smallerText2.length();
            spannableString2.setSpan(new RelativeSizeSpan(0.5f), startIndex2, endIndex2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            displayMMResult.setText((spannableString));
            displayINResult.setText(spannableString2);
            toggleButtonFahCel.setText("°C◄ ►°F"); // Change button text to "MM-In"
        } else {
            // Convert kilo to lbs
            performHapticFeedback();
            String fullText = "°F " + (df.format(((abs(result)) * 1.8)+32));
            String smallerText = "°F ";
            SpannableString spannableString = new SpannableString(fullText);
            int startIndex = fullText.indexOf(smallerText);
            int endIndex = startIndex + smallerText.length();
            spannableString.setSpan(new RelativeSizeSpan(0.5f), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            String fullText2 = "°C " + (df.format(result));
            String smallerText2 = "°C ";
            SpannableString spannableString2 = new SpannableString(fullText2);
            int startIndex2 = fullText2.indexOf(smallerText2);
            int endIndex2 = startIndex2 + smallerText2.length();
            spannableString2.setSpan(new RelativeSizeSpan(0.5f), startIndex2, endIndex2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            displayMMResult.setText((spannableString));
            displayINResult.setText(spannableString2);
            toggleButtonFahCel.setText("°F◄ ►°C"); // Change button text to "In-MM"
        }

        // Toggle the conversion direction for the next click
        convertToCelsius = !convertToCelsius;

    }

    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    @OnClick(R.id.btnCV4)
    public void Cupml() {

        if (all == null){
            // Handle the case when result is null, for example, show an error message
            displayMMResult.setText("Need to enter a");
            displayINResult.setText("number first");
            return;
        }
        if (result == null) {
            result = Double.parseDouble(all);
        }
        // Toggle between converting inches to millimeters and millimeters to inches
        if (convertToMilliliters) {
            // Convert lbs to kilo
            performHapticFeedback();
            String fullText = "ml " + (df.format((abs(result)) * 236.588));
            String smallerText = "ml ";
            SpannableString spannableString = new SpannableString(fullText);
            int startIndex = fullText.indexOf(smallerText);
            int endIndex = startIndex + smallerText.length();
            spannableString.setSpan(new RelativeSizeSpan(0.5f), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            String fullText2 = "Cup " + (df.format(result));
            String smallerText2 = "Cup ";
            SpannableString spannableString2 = new SpannableString(fullText2);
            int startIndex2 = fullText2.indexOf(smallerText2);
            int endIndex2 = startIndex2 + smallerText2.length();
            spannableString2.setSpan(new RelativeSizeSpan(0.5f), startIndex2, endIndex2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            displayMMResult.setText((spannableString));
            displayINResult.setText(spannableString2);
            toggleButtonCupml.setText("ml◄ ►Cup \n☝ ► Oz"); // Change button text to "MM-In"
        } else {
            performHapticFeedback();
            // Convert kilo to lbs
            String fullText = "Cup " + (df.format((abs(result)) / 236.588));
            String smallerText = "Cup ";
            SpannableString spannableString = new SpannableString(fullText);
            int startIndex = fullText.indexOf(smallerText);
            int endIndex = startIndex + smallerText.length();
            spannableString.setSpan(new RelativeSizeSpan(0.5f), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            String fullText2 = "ml " + (df.format(result));
            String smallerText2 = "ml ";
            SpannableString spannableString2 = new SpannableString(fullText2);
            int startIndex2 = fullText2.indexOf(smallerText2);
            int endIndex2 = startIndex2 + smallerText2.length();
            spannableString2.setSpan(new RelativeSizeSpan(0.5f), startIndex2, endIndex2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            displayMMResult.setText((spannableString));
            displayINResult.setText(spannableString2);
            toggleButtonCupml.setText("Cup◄ ►ml  \n☝ ►  Oz"); // Change button text to "In-MM"
        }

        // Toggle the conversion direction for the next click
        convertToMilliliters = !convertToMilliliters;

    }
    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    @OnLongClick(R.id.btnCV4)
    public boolean Cupoz() {

        if (all == null){
            // Handle the case when result is null, for example, show an error message
            displayMMResult.setText("Need to enter a");
            displayINResult.setText("number first");
            return true;
        }
        if (result == null) {
            result = Double.parseDouble(all);
        }
        // Toggle between converting inches to millimeters and millimeters to inches
        if (convertToOnces) {
            // Convert lbs to kilo
            performHapticFeedback();
            String fullText = "oz " + (df.format((abs(result)) * 8));
            String smallerText = "oz ";
            SpannableString spannableString = new SpannableString(fullText);
            int startIndex = fullText.indexOf(smallerText);
            int endIndex = startIndex + smallerText.length();
            spannableString.setSpan(new RelativeSizeSpan(0.5f), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            String fullText2 = "Cup " + (df.format(result));
            String smallerText2 = "Cup ";
            SpannableString spannableString2 = new SpannableString(fullText2);
            int startIndex2 = fullText2.indexOf(smallerText2);
            int endIndex2 = startIndex2 + smallerText2.length();
            spannableString2.setSpan(new RelativeSizeSpan(0.5f), startIndex2, endIndex2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            displayMMResult.setText((spannableString));
            displayINResult.setText(spannableString2);
            toggleButtonCupml.setText("oz◄ ►Cup \n☝"); // Change button text to "MM-In"
        } else {
            performHapticFeedback();
            // Convert kilo to lbs
            String fullText = "Cup " + (df.format((abs(result)) / 8));
            String smallerText = "Cup ";
            SpannableString spannableString = new SpannableString(fullText);
            int startIndex = fullText.indexOf(smallerText);
            int endIndex = startIndex + smallerText.length();
            spannableString.setSpan(new RelativeSizeSpan(0.5f), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            String fullText2 = "oz " + (df.format(result));
            String smallerText2 = "oz ";
            SpannableString spannableString2 = new SpannableString(fullText2);
            int startIndex2 = fullText2.indexOf(smallerText2);
            int endIndex2 = startIndex2 + smallerText2.length();
            spannableString2.setSpan(new RelativeSizeSpan(0.5f), startIndex2, endIndex2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            displayMMResult.setText((spannableString));
            displayINResult.setText(spannableString2);
            toggleButtonCupml.setText("Cup◄ ►oz \n☝"); // Change button text to "In-MM"
        }

        // Toggle the conversion direction for the next click
        convertToOnces = !convertToOnces;
        return true;
    }
    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    @OnClick(R.id.btnCV3)
    public void MilesKm() {

        if (all == null){
            // Handle the case when result is null, for example, show an error message
            displayMMResult.setText("Need to enter a");
            displayINResult.setText("number first");
            return;
        }
        if (result == null) {
            result = Double.parseDouble(all);
        }
        // Toggle between converting inches to millimeters and millimeters to inches
        if (convertToMKilometers) {
            // Convert lbs to kilo
            performHapticFeedback();
            String fullText = "Km " + (df.format((abs(result)) * 1.609344));
            String smallerText = "Km ";
            SpannableString spannableString = new SpannableString(fullText);
            int startIndex = fullText.indexOf(smallerText);
            int endIndex = startIndex + smallerText.length();
            spannableString.setSpan(new RelativeSizeSpan(0.5f), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            String fullText2 = "Miles " + (df.format(result));
            String smallerText2 = "Miles ";
            SpannableString spannableString2 = new SpannableString(fullText2);
            int startIndex2 = fullText2.indexOf(smallerText2);
            int endIndex2 = startIndex2 + smallerText2.length();
            spannableString2.setSpan(new RelativeSizeSpan(0.5f), startIndex2, endIndex2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            displayMMResult.setText((spannableString));
            displayINResult.setText(spannableString2);
            toggleButtonMilesKm.setText("Km◄ ►Miles"); // Change button text to "MM-In"
        } else {
            performHapticFeedback();
            // Convert kilo to lbs
            String fullText = "Miles " + (df.format((abs(result)) / 1.609344));
            String smallerText = "Miles ";
            SpannableString spannableString = new SpannableString(fullText);
            int startIndex = fullText.indexOf(smallerText);
            int endIndex = startIndex + smallerText.length();
            spannableString.setSpan(new RelativeSizeSpan(0.5f), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            String fullText2 = "Km " + (df.format(result));
            String smallerText2 = "Km ";
            SpannableString spannableString2 = new SpannableString(fullText2);
            int startIndex2 = fullText2.indexOf(smallerText2);
            int endIndex2 = startIndex2 + smallerText2.length();
            spannableString2.setSpan(new RelativeSizeSpan(0.5f), startIndex2, endIndex2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            displayMMResult.setText((spannableString));
            displayINResult.setText(spannableString2);
            toggleButtonMilesKm.setText("Miles◄ ►Km"); // Change button text to "In-MM"
        }

        // Toggle the conversion direction for the next click
        convertToMKilometers = !convertToMKilometers;

    }

    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    @OnClick(R.id.btnCV7)
    public void SqfSqm() {

        if (all == null){
            // Handle the case when result is null, for example, show an error message
            displayMMResult.setText("Need to enter a");
            displayINResult.setText("number first");
            return;
        }
        if (result == null) {
            result = Double.parseDouble(all);
        }
        // Toggle between converting inches to millimeters and millimeters to inches
        if (convertToSquaremeters) {
            // Convert lbs to kilo
            performHapticFeedback();
            String fullText = "m² " + (df.format((abs(result)) / 10.764));
            String smallerText = "m² ";
            SpannableString spannableString = new SpannableString(fullText);
            int startIndex = fullText.indexOf(smallerText);
            int endIndex = startIndex + smallerText.length();
            spannableString.setSpan(new RelativeSizeSpan(0.5f), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            String fullText2 = "ft² " + (df.format(result));
            String smallerText2 = "ft² ";
            SpannableString spannableString2 = new SpannableString(fullText2);
            int startIndex2 = fullText2.indexOf(smallerText2);
            int endIndex2 = startIndex2 + smallerText2.length();
            spannableString2.setSpan(new RelativeSizeSpan(0.5f), startIndex2, endIndex2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            displayMMResult.setText((spannableString));
            displayINResult.setText(spannableString2);
            toggleButtonSqfSqm.setText("m²◄ ►ft²"); // Change button text to "MM-In"
        } else {
            performHapticFeedback();
            // Convert kilo to lbs
            String fullText = "ft² " + (df.format((abs(result)) * 10.764));
            String smallerText = "ft² ";
            SpannableString spannableString = new SpannableString(fullText);
            int startIndex = fullText.indexOf(smallerText);
            int endIndex = startIndex + smallerText.length();
            spannableString.setSpan(new RelativeSizeSpan(0.5f), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            String fullText2 = "m² " + (df.format(result));
            String smallerText2 = "m² ";
            SpannableString spannableString2 = new SpannableString(fullText2);
            int startIndex2 = fullText2.indexOf(smallerText2);
            int endIndex2 = startIndex2 + smallerText2.length();
            spannableString2.setSpan(new RelativeSizeSpan(0.5f), startIndex2, endIndex2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            displayMMResult.setText((spannableString));
            displayINResult.setText(spannableString2);
            toggleButtonSqfSqm.setText("ft²◄ ►m²"); // Change button text to "In-MM"
        }

        // Toggle the conversion direction for the next click
        convertToSquaremeters = !convertToSquaremeters;

    }

    @SuppressLint("NonConstantResourceId")
   @OnClick(R.id.btnNegative)
    public void setNegativeNumber(View view) {
        performHapticFeedback();
        if (showOperation.getText().toString().isEmpty()) {
            showOperation.setText("");
        } else {
            try {
                int num = Integer.parseInt(String.valueOf(showOperation.getText()));
                num = num * (-1);
                showOperation.setText(String.valueOf(num));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @SuppressLint({"SetTextI18n", "NonConstantResourceId"})
    @OnClick(R.id.btnEquals)
    public void equals(View view) {
        Log.d(TAG, "equal: ");
        performHapticFeedback();
        if (all == null){
            // Handle the case when result is null, for example, show an error message
            displayMMResult.setText("Need to enter a");
            displayINResult.setText("number first");
            return;
        }
        String currentText = showOperation.getText().toString();
        if (currentText.isEmpty()) {
            return; // No operation to calculate
        }
        try {
            // Split it
            String[] splittedText = getTheSecondNumber();

            if (splittedText.length > 1) {
                firstNumber = Double.parseDouble(splittedText[0]);
                Log.d(TAG, "array: " + Arrays.toString(splittedText));
                Log.d(TAG, "firstNumber: " + firstNumber + " secondNumber: " + secondNumber);
                Log.d(TAG, "operation: " + mOperation);

                // Take the Second number
                secondNumber = Double.parseDouble(splittedText[1]);

                switch (mOperation) {
                    case '+':
                        result = firstNumber + secondNumber;
                        break;
                    case '-':
                        result = firstNumber - secondNumber;
                        break;
                    case 'x':
                        result = firstNumber * secondNumber;
                        break;
                    case '÷':
                        if (secondNumber == 0) //when denominator becomes zero
                        {
                            Toast.makeText(this, "DIVISION NOT POSSIBLE", Toast.LENGTH_SHORT).show();
                            result = Double.POSITIVE_INFINITY;
                            displayResult.setText(String.valueOf(result));
                            break;
                        } else {
                            result = firstNumber / secondNumber;
                        }
                        break;
                    case '%':
                        result = firstNumber * (secondNumber / 100);
                        break;
                     }


                // Get the whole number of the result
                wholeNumber = result.intValue();
                // Check if result is whole number or not
                checkResult =  result / wholeNumber;

                String coloredDecimalNumber = " " + result + "</font>";
                String coloredWholeNumber = " " + wholeNumber + "</font>";

                // If result equals 0
                if (result == 0.0) {
                    coloredWholeNumber = " " + 0 + "</font>";
                    displayResult.setText(colorResult(coloredWholeNumber), TextView.BufferType.SPANNABLE);
                    historyList.add(new History(String.valueOf((int) firstNumber), String.valueOf(mOperation), String.valueOf((int) secondNumber), "0"));
                // If result equals infinity
                }else if(result == Double.POSITIVE_INFINITY){
                    coloredWholeNumber = "Infinity" + "</font>";
                    displayResult.setText(colorResult(coloredWholeNumber), TextView.BufferType.SPANNABLE);
                    historyList.add(new History(String.valueOf((int) firstNumber), String.valueOf(mOperation), String.valueOf((int) secondNumber), "Infinity"));
                // If result does not equal one
                }else if (checkResult != 1) {
                    // Set result as a decimal number
                    displayResult.setText(colorResult(coloredDecimalNumber), TextView.BufferType.SPANNABLE);
                    historyList.add(new History(String.valueOf(firstNumber), String.valueOf(mOperation) , String.valueOf(secondNumber) , String.valueOf(result)));
                // If result does not equal one and numbers are decimals
                } else if(firstNumber % 1 != 0 || secondNumber % 1 != 0){
                    // Set result as a whole number
                    displayResult.setText(colorResult(coloredDecimalNumber), TextView.BufferType.SPANNABLE);
                    historyList.add(new History(String.valueOf(firstNumber), String.valueOf(mOperation) , String.valueOf(secondNumber) , String.valueOf(result.intValue())));
                // If result equals one
                } else{
                    // Set result a whole number
                    displayResult.setText(colorResult(coloredWholeNumber), TextView.BufferType.SPANNABLE);
                    historyList.add(new History(String.valueOf((int) firstNumber), String.valueOf(mOperation) , String.valueOf((int)secondNumber) , String.valueOf(wholeNumber)));
                }

                HistoryStorage.saveData(this,historyList);

            }


        } catch (Exception e) {
            e.printStackTrace();


        }

        if (consel == 0){
            displayINResult.setText("");
            displayMMResult.setText("");
        }else if (consel == 1){
            inmm();
        }else if (consel == 2){
            lbskg();
        }else if (consel == 3){
            GaLt();
        }else if (consel == 4){
            FtM();
        }else if (consel == 5){
            FtM();
        }else if (consel == 6){
            FtM();
        }else if (consel == 7){
            FtM();
        }else if (consel == 8){
            FtM();
        }



        DecimalFormat df = new DecimalFormat("#.###");
        displayResult.setText(df.format (result));


  //   displayMMResult.setText(df.format(result * 0.0393701) + "\"");
    }

    private void decimalToFraction() {

        if (in3 == 0.0) {
            in4 = " ";
        } else if (in3 > 0.00 && in3 <= 0.04) {
            in4 = "1/32";
        } else if (in3 > 0.04 && in3 <= 0.07) {
            in4 = "1/16";
        } else if (in3 > 0.07 && in3 <= 0.10){
            in4 = "3/32";
        } else if (in3 > 0.10 && in3 <= 0.13){
            in4="1/8";
        } else if (in3 > 0.13 && in3 <= 0.16) {
            in4 = "5/32";
        } else if (in3 > 0.16 && in3 <= 0.19){
                in4="3/16";
        } else if (in3 > 0.19 && in3 <= 0.23){
                in4="7/32";
        } else if (in3 > 0.23 && in3 <= 0.26){
                in4="1/4";
        } else if (in3 > 0.26 && in3 <= 0.30){
                in4="9/32";
        } else if (in3 > 0.30 && in3 <= 0.32){
                in4="5/16";
        } else if (in3 > 0.32 && in3 <= 0.36){
                in4="11/32";
        } else if (in3 > 0.36 && in3 <= 0.38){
                in4="3/8";
        } else if (in3 > 0.38 && in3 <= 0.41){
                in4="13/32";
        } else if (in3 > 0.41 && in3 <= 0.44){
                in4="7/16";
        } else if (in3 > 0.44 && in3 <= 0.47){
                in4="15/32";
        } else if (in3 > 0.47 && in3 <= 0.51){
                in4="1/2";
        } else if (in3 > 0.51 && in3 <= 0.54){
                in4="17/32";
        } else if (in3 > 0.54 && in3 <= 0.57){
                in4="9/16";
        } else if (in3 > 0.57 && in3 <= 0.60){
                in4="19/32";
        } else if (in3 > 0.60 && in3 <= 0.63){
                in4="5/8";
        } else if (in3 > 0.63 && in3 <= 0.66){
                in4="21/32";
        } else if (in3 > 0.66 && in3 <= 0.69){
                in4="11/16";
        } else if (in3 > 0.69 && in3 <= 0.73){
                in4="23/32";
        } else if (in3 > 0.73 && in3 <= 0.76){
                in4="3/4";
        } else if (in3 > 0.76 && in3 <= 0.79){
                in4="25/32";
        } else if (in3 > 0.79 && in3 <= 0.82){
                in4="13/16";
        } else if (in3 > 0.82 && in3 <= 0.85){
                in4="27/32";
        } else if (in3 > 0.85 && in3 <= 0.89){
                in4="7/8";
        } else if (in3 > 0.89 && in3 <= 0.91){
                in4="29/32";
        } else if (in3 > 0.91 && in3 <= 0.94){
                in4="15/16";
        } else if (in3 > 0.94 && in3 <= 0.98){
                in4="31/32";
        } else if (in3 > 0.98){
            in2 = in2+1;
            in4=" ";
        }

    }



    private void displayColouredResult4() {

        // Get the whole number of the result
        wholeNumber = result.intValue();
        // Check if result is whole number or not
        checkResult = result / wholeNumber;

        String coloredDecimalNumber = getString(R.string.equals) + "" + result + "</font>";
        String coloredWholeNumber = getString(R.string.equals) + "" + wholeNumber + "</font>";


        if (result == 0.0) {
            coloredWholeNumber = getString(R.string.equals) + 0 + "</font>";
            displayResult.setText(colorResult(coloredWholeNumber), TextView.BufferType.SPANNABLE);
            // If result is not equal one
        } else if (checkResult != 1) {
            // Set the decimal number
            displayResult.setText(colorResult(coloredDecimalNumber), TextView.BufferType.SPANNABLE);
        } else{
            // Set the whole number
            displayResult.setText(colorResult(coloredWholeNumber), TextView.BufferType.SPANNABLE);
        }

        Log.d(TAG, "equals: " + coloredWholeNumber);
    }

    /**
     * get the second number
     */
    private String[] getTheSecondNumber() {
        // Get the Text
        String text = showOperation.getText().toString();
        if(text.startsWith("-")){
            text = showOperation.getText().toString().substring(1);
        }else {
            text = showOperation.getText().toString();
        }
        Log.d(TAG, text);

        if (mOperation == 'x') {
            // Split by operator x
            return text.split(String.valueOf(mOperation));
        } else {
            // Split by operator +, -, ÷
            return text.split(String.valueOf("\\" + mOperation));
        }
    }

    /**
     * Color the final result
     */
    @SuppressLint("ObsoleteSdkInt")
    private Spanned colorResult(String number) {
        if (Build.VERSION.SDK_INT >= 24) {
            return (fromHtml(number, 1)); // for 24 api and more
        } else {
            return fromHtml(number);// or for older api
        }
    }

    /**
     * Delete any previous results
     */
    private void deletePreviousResult() {
        if (!TextUtils.isEmpty(displayResult.getText().toString())) {
            showOperation.setText("");
            displayResult.setText("");
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private final View.OnTouchListener onTouchListener = (view, event) -> {
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_POINTER_DOWN) {
            view.setFocusable(false);
        } else {
            view.setFocusableInTouchMode(true);
        }
        return true;
    };

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putDouble(FIRST_NUMBER, firstNumber);
        outState.putDouble(SECOND_NUMBER, secondNumber);
        outState.putChar(OPERATOR, mOperation);
        if (result != null) {
            outState.putDouble(RESULT, result);
            outState.putInt(WHOLE_NUMBER, wholeNumber);
        }

        // call superclass->save any view hierarchy
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.action_history) {
            performHapticFeedback();
            showCustomAlertDialog();
        }
        else
            if(item.getItemId() == R.id.close_app) {
                performHapticFeedback();
                super.finishAndRemoveTask();
            }
     //       else
     //           if(item.getItemId() == R.id.action_clear) {
     //               performHapticFeedback();
     //               displayMMResult.setText("");
     //               displayINResult.setText("");
     //       }
 //               else
 //               if(item.getItemId() == R.id.action_show_overlay) {
 //                   startOverlayService();
 //                   return true;
 //       }

           return super.onOptionsItemSelected(item);
    }

    private void startOverlayService() {
        // Start the overlay service
        Intent intent = new Intent(this, OverlayService.class);
        startService(intent);
    }

    public void showCustomAlertDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);

        final TextView clearHistory = dialog.findViewById(R.id.btn_clear_history);
        clearHistory.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                HistoryStorage.clearData(getApplicationContext());
                adapter.clear();
                Toast.makeText(MainActivity.this, "Cleared", Toast.LENGTH_SHORT).show();
            }
        });

        final ImageView imgClose = dialog.findViewById(R.id.img_close);
        imgClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        ArrayList<History> reversedList = HistoryStorage.loadData(this);
        Collections.reverse(reversedList);
        adapter = new HistoryAdapter(this, reversedList);
        listView = dialog.findViewById(R.id.lst_history);
        listView.setAdapter(adapter);

        dialog.show();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        assert key != null;
        if (key.equals(PREF_KEY)) {
            adapter.clear();
            historyList = HistoryStorage.loadData(this);
            adapter.addAll(historyList);
            listView.setAdapter(adapter);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Unregister the listener
        HistoryStorage.unregisterPref(this, this);
    }

    public void Popup(View v) {
        PopupMenu popupMenu = new PopupMenu(this,v);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.show();
    }


    @SuppressLint("NonConstantResourceId")
    public boolean onMenuItemClick(MenuItem item) {
        Toast.makeText(this, "Selected Item: " +item.getTitle(), Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            case R.id.item116: setAll(" 1/16\"");
                return true;
            case R.id.item18: setAll(" 1/8\"");
                return true;
            case R.id.item316: setAll(" 3/16\"");
                return true;
            case R.id.item14: setAll(" 1/4\"");
                return true;
            case R.id.item516: setAll(" 5/16\"");
                return true;
            case R.id.item38: setAll(" 3/8\"");
                return true;
            case R.id.item716: setAll(" 7/16\"");
                return true;
            case R.id.item12: setAll(" 1/2\"");
                return true;
            case R.id.item916: setAll(" 9/16\"");
                return true;
            case R.id.item58: setAll(" 5/8\"");
                return true;
            case R.id.item1116: setAll(" 11/16\"");
                return true;
            case R.id.item34: setAll(" 3/4\"");
                return true;
            case R.id.item1316: setAll(" 13/16\"");
                return true;
            case R.id.item78: setAll(" 7/8\"");
                return true;
            case R.id.item1516: setAll(" 15/16\"");
                return true;

            default:
                return false;
        }
    }
    @Override
    public boolean onLongClick(View v) {
        // Perform the action you want when long press happens
        switch (v.getId()) {
            case R.id.btnCV1:
                lbskg();
                return true;
            case R.id.btnCV2:
                GaLt();
                return true;
            case R.id.btnCV3:
                MilesKm();
                return true;
            case R.id.btnCV4:
                Cupml();
                return true;
            case R.id.btnCV5:
                inmm();
                return true;
            case R.id.btnCV6:
                FtM();
                return true;
            case R.id.btnCV7:
                SqfSqm();
                return true;
            case R.id.btnCV8:
                FahCel();
                return true;
            case R.id.btnClear:
                displayMMResult.setText("");
                displayINResult.setText("");
                displayResult.setText("");
                showOperation.setText("");
                return true;
            default:
                return false;
        }
    }


}
