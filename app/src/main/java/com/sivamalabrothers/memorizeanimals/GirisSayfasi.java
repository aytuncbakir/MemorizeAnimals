package com.sivamalabrothers.memorizeanimals;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;


public class GirisSayfasi extends AppCompatActivity {

    Button veryeasy, easy, normal, hard;
    Button otherApp, rateApp, settingApp, langApp;
    Button english, finnish , turkish, italian, spanish, greek, german, russian, chinese;
    Intent intent;
    Context context = this;
    String easyValue ;
    String harderValue;
    String normalValue ;
    String veryeasyValue;
    TextView giris_baslik,selectText;
    String language = "English";
    SharePreferences sharePreferences;
    Typeface font ;
    private static final int CUSTOM_DIALOG_ID = 1;

    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_giris);

        font = Typeface.createFromAsset(getAssets(),"fonts/Candy_Shop_Black.ttf");

        sharePreferences = new SharePreferences();

        giris_baslik = findViewById(R.id.giris_baslik);
        giris_baslik.setTypeface(font,Typeface.BOLD);

        otherApp = findViewById(R.id.otherApp);
        otherApp.setTypeface(font,Typeface.BOLD);
        rateApp = findViewById(R.id.rateApp);
        rateApp.setTypeface(font,Typeface.BOLD);
        settingApp = findViewById(R.id.settingApp);
        settingApp.setTypeface(font,Typeface.BOLD);
        langApp = findViewById(R.id.langApp);
        langApp.setTypeface(font,Typeface.BOLD);

        veryeasy = findViewById(R.id.veryeasy);
        veryeasy.setTypeface(font,Typeface.BOLD);

        easy = findViewById(R.id.easy);
        easy.setTypeface(font,Typeface.BOLD);
        hard = findViewById(R.id.hard);
        hard.setTypeface(font,Typeface.BOLD);
        normal = findViewById(R.id.normal);
        normal.setTypeface(font,Typeface.BOLD);

        language = sharePreferences.getString(context,"language");
        if(language.equals("") || language == null) {
            language = "English";
            sharePreferences.saveString(context, "language", language);
        }
        setLanguage();

        langApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(CUSTOM_DIALOG_ID);

                           }
        });

        veryeasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(context, VeryEasyGame.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("veryeasy", veryeasyValue);
                startActivity(intent);
                finish();
            }
        });

        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(context, EasyGame.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("easy", easyValue);
                intent.putExtra("language", language);
                startActivity(intent);
                finish();
            }
        });

        normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(context, NormalGame.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("normal", normalValue);
                startActivity(intent);
                finish();
            }
        });

        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(context, HardGame.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("hard", harderValue);
                startActivity(intent);
                finish();
            }
        });

        animasyonUygula();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id){
            case CUSTOM_DIALOG_ID:
                final Dialog language_dialog = new Dialog(context);
                language_dialog.getWindow().setBackgroundDrawableResource(R.drawable.custom_play_button);
                language_dialog.setTitle("Select Language");
                language_dialog.setContentView(R.layout.language_selection);

                english = language_dialog.findViewById(R.id.english);
                english.setTypeface(font,Typeface.BOLD);
                finnish = language_dialog.findViewById(R.id.finnish);
                finnish.setTypeface(font,Typeface.BOLD);
                turkish = language_dialog.findViewById(R.id.turkish);
                turkish.setTypeface(font,Typeface.BOLD);
                spanish = language_dialog.findViewById(R.id.spanish);
                spanish.setTypeface(font,Typeface.BOLD);
                italian = language_dialog.findViewById(R.id.italian);
                italian.setTypeface(font,Typeface.BOLD);
                german = language_dialog.findViewById(R.id.german);
                german.setTypeface(font,Typeface.BOLD);

                selectText = language_dialog.findViewById(R.id.selectText);
                selectText.setTypeface(font,Typeface.BOLD);
                setLanguageButtons();

                english.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        language = "English";
                        sharePreferences.saveString(context,"language",language);
                        language_dialog.dismiss();
                        setLanguageButtons();
                        setLanguage();
                    }
                });

                finnish.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        language = "Finnish";
                        sharePreferences.saveString(context,"language",language);
                        language_dialog.dismiss();
                        setLanguageButtons();
                        setLanguage();
                    }
                });

                turkish.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        language = "Turkish";
                        sharePreferences.saveString(context,"language",language);
                        language_dialog.dismiss();
                        setLanguageButtons();
                        setLanguage();
                    }
                });

                german.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        language = "German";
                        sharePreferences.saveString(context,"language",language);
                        language_dialog.dismiss();
                        setLanguageButtons();
                        setLanguage();
                    }
                });

                spanish.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        language = "Spanish";
                        sharePreferences.saveString(context,"language",language);
                        language_dialog.dismiss();
                        setLanguageButtons();
                        setLanguage();
                    }
                });

                italian.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        language = "Italian";
                        sharePreferences.saveString(context,"language",language);
                        language_dialog.dismiss();
                        setLanguageButtons();
                        setLanguage();
                    }
                });
              return  language_dialog;
        }
        return super.onCreateDialog(id);
    }

    private void setLanguageButtons(){
        language = sharePreferences.getString(context,"language");
        if(language.equals("Finnish")) {
            selectText.setText("Valitse Kieli");
            english.setText("ENGLANTI");
            finnish.setText("SUOMI");
            turkish.setText("TURKKI");
            spanish.setText("ESPANJA");
            italian.setText("ITALIAN K.");
            german.setText("SAKSA K.");

        }else if(language.equals("English")){
            selectText.setText("Select Language");
            finnish.setText("FINNISH");
            english.setText("ENGLISH");
            turkish.setText("TURKISH");
            spanish.setText("SPANISH");
            italian.setText("ITALIAN");
            german.setText("GERMAN");

        }else if(language.equals("German")){
            selectText.setText("Sprache auswählen");
            finnish.setText("FINNISCH");
            english.setText("ENGLISCH");
            turkish.setText("TÜRKISCH");
            spanish.setText("SPANISCH");
            italian.setText("ITALIENISCH");
            german.setText("DEUTSCHE");

        }
        else if(language.equals("Turkish")){
            selectText.setText("Dil Seçiniz");
            finnish.setText("FINCE");
            english.setText("INGILIZCE");
            turkish.setText("TÜRKÇE");
            spanish.setText("ISPANYOLCA");
            italian.setText("ITALYANCA");
            german.setText("ALMANCA");

        }else if(language.equals("Spanish")){
            selectText.setText("Select Language");
            finnish.setText("FINNISH");
            english.setText("ENGLISH");
            turkish.setText("TURKISH");
            spanish.setText("SPANISH");
            italian.setText("ITALIAN");
            german.setText("GERMAN");

        }else if(language.equals("Italian")){
            selectText.setText("Select Language");
            finnish.setText("FINNISH");
            english.setText("ENGLISH");
            turkish.setText("TURKISH");
            spanish.setText("SPANISH");
            italian.setText("ITALIAN");
            german.setText("GERMAN");
        }
    }

    private void setLanguage(){
        language = sharePreferences.getString(context,"language");
        if(language.equals("Finnish")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                giris_baslik.setText(Html.fromHtml(getResources().getString(R.string.game_title_finnish), Html.FROM_HTML_MODE_COMPACT));
            } else
                giris_baslik.setText(Html.fromHtml(getResources().getString(R.string.game_title_finnish)));

            veryeasy.setText("NIIN HELPPOA");
            easy.setText("HELPPOA");
            normal.setText("NORMAALI");
            hard.setText("VAIKEA");

            otherApp.setText("L");
            rateApp.setText("N");
            settingApp.setText("A");
            langApp.setText("K");

        }else if(language.equals("English")){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                giris_baslik.setText(Html.fromHtml(getResources().getString(R.string.game_title_english), Html.FROM_HTML_MODE_COMPACT));
            } else
                giris_baslik.setText(Html.fromHtml(getResources().getString(R.string.game_title_english)));

            veryeasy.setText("VERY EASY");
            easy.setText("EASY");
            normal.setText("NORMAL");
            hard.setText("HARD");

            otherApp.setText("A");
            rateApp.setText("R");
            settingApp.setText("S");
            langApp.setText("L");
        }else if(language.equals("German")){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                giris_baslik.setText(Html.fromHtml(getResources().getString(R.string.game_title_german), Html.FROM_HTML_MODE_COMPACT));
            } else
                giris_baslik.setText(Html.fromHtml(getResources().getString(R.string.game_title_german)));

            veryeasy.setText("SEHR EINFACH");
            easy.setText("EINFACH");
            normal.setText("Normal");
            hard.setText("SCHWER");

            otherApp.setText("M");
            rateApp.setText("B");
            settingApp.setText("R");
            langApp.setText("S");
        }else if(language.equals("Turkish")){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                giris_baslik.setText(Html.fromHtml(getResources().getString(R.string.game_title_turkish), Html.FROM_HTML_MODE_COMPACT));
            } else
                giris_baslik.setText(Html.fromHtml(getResources().getString(R.string.game_title_turkish)));

            veryeasy.setText("BASIT");
            easy.setText("KOLAY");
            normal.setText("NORMAL");
            hard.setText("ZOR");

            otherApp.setText("E");
            rateApp.setText("P");
            settingApp.setText("A");
            langApp.setText("D");
        }else if(language.equals("Spanish")){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                giris_baslik.setText(Html.fromHtml(getResources().getString(R.string.game_title_spanish), Html.FROM_HTML_MODE_COMPACT));
            } else
                giris_baslik.setText(Html.fromHtml(getResources().getString(R.string.game_title_spanish)));

            veryeasy.setText("MUY FÁCIL");
            easy.setText("FÁCIL");
            normal.setText("NORMAL");
            hard.setText("DIFÍCIL");

            otherApp.setText("A");
            rateApp.setText("T");
            settingApp.setText("A");
            langApp.setText("I");
        }else if(language.equals("Italian")){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                giris_baslik.setText(Html.fromHtml(getResources().getString(R.string.game_title_italian), Html.FROM_HTML_MODE_COMPACT));
            } else
                giris_baslik.setText(Html.fromHtml(getResources().getString(R.string.game_title_italian)));

            veryeasy.setText("MOLTO FACILE");
            easy.setText("FACILE");
            normal.setText("NORMALE");
            hard.setText("DIFFICILE");

            otherApp.setText("S");
            rateApp.setText("V");
            settingApp.setText("I");
            langApp.setText("L");
        }
    }

    private void animasyonUygula(){
        if(Build.VERSION.SDK_INT >=21){
            Slide enterTransition = new Slide();
            enterTransition.setDuration(300);
            enterTransition.setSlideEdge(Gravity.BOTTOM);
            getWindow().setEnterTransition(enterTransition);
        }
    }

    // geri butonuna basıldığında çalışır
    @Override
    public boolean onSupportNavigateUp(){
        if(Build.VERSION.SDK_INT >= 21) {
            finishAfterTransition();
        }
        else{
            finish();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if(Build.VERSION.SDK_INT >= 21) {
            finishAfterTransition();
            finishAffinity();
            GirisSayfasi.this.finishAffinity();
            System.exit(0);

        }else{
            GirisSayfasi.this.finish();
            finish();
            System.exit(0);
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }
}
