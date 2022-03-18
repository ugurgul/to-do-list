package com.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements BottomSheetNewTask.FragmentListener {

    BottomSheetFragment bottomSheetFragment;
    BottomSheetNewTask bottomSheetNewTask;
    BottomSheetList bottomSheetList;
    int darktheme=AppCompatDelegate.MODE_NIGHT_YES;
    int defaultheme=AppCompatDelegate.MODE_NIGHT_NO;
    MaterialButtonToggleGroup materialButtonToggleGroup;
    SharedPreferences sharedPreferences;
    int checked,state=0;
    LinearLayout linearLayout;
    TextInputEditText txteditNew,txtedit_date,txtedit_time;

    @Override
    protected void onCreate(Bundle savedInstanceState ) {
        if(AppCompatDelegate.getDefaultNightMode()==darktheme){
            setTheme(R.style.dark_style);}
        else{setTheme(R.style.AppTheme);}
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomAppBar bar = findViewById(R.id.bar);
        FloatingActionButton fab = findViewById(R.id.fab);
        Toolbar toolbar = findViewById(R.id.toolbar);

        materialButtonToggleGroup=findViewById(R.id.theme_toggle_group);

        sharedPreferences=this.getPreferences(Context.MODE_PRIVATE);
        checked=sharedPreferences.getInt("cek",0);
        state=sharedPreferences.getInt("state",0);





        if (checked==1){
        AppCompatDelegate.setDefaultNightMode(darktheme);

            /*materialButtonToggleGroup.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
                group.check(R.id.theme_dark);
            });*/
        }
        else {
            AppCompatDelegate.setDefaultNightMode(defaultheme);
           /* materialButtonToggleGroup.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
                group.check(R.id.theme_light);
            });*/

        }


        setSupportActionBar(toolbar);

        fab.setOnClickListener(view -> {
            bottomSheetNewTask = new BottomSheetNewTask();
            bottomSheetNewTask.show(getSupportFragmentManager(), bottomSheetNewTask.getTag());
            keyboardShow();
        });


        bar.setNavigationOnClickListener(v -> {
            bottomSheetList = new BottomSheetList();
            bottomSheetList.show(getSupportFragmentManager(), bottomSheetList.getTag());

        });

        bar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.theme_switcher:
                    bottomSheetFragment = new BottomSheetFragment();
                    bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());

                    break;
                case R.id.app_bar_settings:
                    Toast.makeText(getApplicationContext(), "Setting Clicked", Toast.LENGTH_LONG).show();
                    break;
            }
            return false;
        });

        bottomSheetNewTask = BottomSheetNewTask.newInstance(this);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mtrl_theme_switcher_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.app_bar_search) {
           Toast.makeText(getApplicationContext(), "Search Clicked", Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void theme__dark(View view) {

        AppCompatDelegate.setDefaultNightMode(darktheme);
        checked=1;
        SharedCheckTheme();

    }

    public void theme__light(View view) {

        AppCompatDelegate.setDefaultNightMode(defaultheme);
        checked=2;
        SharedCheckTheme();


    }

    public void SharedCheckTheme(){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt("cek",checked);
        editor.apply();
        recreate();
    }

    public void keyboardShow(){
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        assert inputMethodManager != null;
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void getView(View view) {

        linearLayout = findViewById(R.id.ll_content);

        LinearLayout.LayoutParams layoutparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        linearLayout.setOrientation(LinearLayout.VERTICAL);
        layoutparams.topMargin=10;

        LinearLayout linearLayout1=new LinearLayout(this);
        linearLayout1.setOrientation(LinearLayout.HORIZONTAL);


        MaterialCardView mCardView=new MaterialCardView(this);
        mCardView.setLayoutParams(layoutparams);
        mCardView.setElevation(2);
        TypedValue value = new TypedValue();
        getApplicationContext().getTheme().resolveAttribute(R.attr.bottombarcolor, value, true);
        mCardView.setCardBackgroundColor(value.data);


        CheckBox checkBox=new CheckBox(this);
        LinearLayout.LayoutParams checkBoxParam = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        checkBoxParam.gravity = Gravity.CENTER;
        checkBox.setLayoutParams(checkBoxParam);

        LinearLayout verticalLinLayout=new LinearLayout(this);
        verticalLinLayout.setOrientation(LinearLayout.VERTICAL);
        layoutparams.gravity=Gravity.CENTER_HORIZONTAL;
        verticalLinLayout.setLayoutParams(layoutparams);



        TextView txt_baslik=new TextView(this);
        TextView txt_tarih=new TextView(this);

        txt_baslik.setLayoutParams(layoutparams);
        txt_tarih.setLayoutParams(layoutparams);

        txteditNew=view.findViewById(R.id.txteditNew);
        txtedit_date=view.findViewById(R.id.txtedit_date);
        txtedit_time=view.findViewById(R.id.txtedit_time);

        txt_baslik.setText(txteditNew.getText());
        txt_baslik.setTextSize(16);
        txt_baslik.setTextColor(R.attr.textcolor);
        txt_baslik.setSingleLine(true);
        Typeface typeface = getResources().getFont(R.font.regularopensans);
        txt_baslik.setTypeface(typeface);

        String date= Objects.requireNonNull(txtedit_date.getText()).toString();
        String time= Objects.requireNonNull(txtedit_time.getText()).toString();

        txt_tarih.setText(date + "  " + time);
        txt_tarih.setTextSize(16);
        txt_tarih.setTextColor(R.attr.textcolor);
        txt_tarih.setSingleLine(true);
        Typeface typeface1 = getResources().getFont(R.font.regularopensans);
        txt_tarih.setTypeface(typeface1);

        verticalLinLayout.removeAllViews();
        verticalLinLayout.addView(txt_baslik);
        verticalLinLayout.addView(txt_tarih);
        linearLayout1.addView(checkBox);
        linearLayout1.addView(verticalLinLayout);
        mCardView.addView(linearLayout1);
        linearLayout.addView(mCardView);

        state=1;
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt("state",state);
        editor.apply();


    }
}






