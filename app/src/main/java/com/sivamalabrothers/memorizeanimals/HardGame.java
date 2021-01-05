package com.sivamalabrothers.memorizeanimals;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class HardGame extends AppCompatActivity implements TextToSpeech.OnInitListener,
        View.OnClickListener,Animation.AnimationListener{

    TextToSpeech textToSpeech;
    ImageView image1, image2, image3, image4, image5,image6, image7, image8, image9,image10;;
    ImageView image11, image12,image13, image14, image15, image16,image17, image18, image19,image20;

    ImageView imageBir, imageIki, imageUc,imageDort, imageBes;
    ImageView  imageAlti, imageYedi, imageSekiz,imageDokuz,imageOn;
    LinearLayout skorLevelTablo;
    //RotateAnimation rotate;
    ObjectAnimator rotate;
    ArrayList<ImageView> images;
    ArrayList<ImageView> imagesKilitleAc;
    int oyunBittiMi = 0;
    TextView tebrikText, rekorText ,skorText;
    Button levelAtla;
    LinearLayout tahta;
    Context context = this;
    ArrayList<Integer> yerlesimBelirle;
    String s= "  ";
    ArrayList<Integer> tiklananImageIdler;
    ArrayList<Animals> animals ;
    ArrayList<Animals> animalsKonumla ;
    String text = "";
    static int hardLevel = 0;
    private ProgressBar progressBar;
    private TextView textView;
    private Handler handler = new Handler();
    private int progressStatus = 100;
    TextView skor,levelGoster;
    static int hardScore = 0;
    AlphaAnimation fadeIn, fadeOut;
    static int hardDurum = 0;
    boolean suspended = false;
    SharePreferences sharePreferences;
    String sonSkor = "hardSkor";
    String maksSkor = "hardMaxSkor";
    String sonLevel = "hardLevel";
    MediaPlayer ses;
    int maxSkor = 100000; // maksimum skor tutulacak

    private Animation animation1;
    private Animation animation2;
    private boolean isBackOfCardShowing = true;
    private ImageView image = null;

    private static int backColour = 0;
    private int backColourCode = 0;
    private int  backColourCodeSecond  = 0;
    private String language = "";
    private String levelLanguage = "";

    RecyclerView recyclerView;
    private static final int CUSTOM_DIALOG_ID_HARD_BACK = 0;
    private static final int CUSTOM_DIALOG_ID_HARD_NEXT = 1;

    Typeface font;

    private AdView adView;
    LinearLayout reklam_layout,reklam_layout1,reklam_layout2;

    private static final String REKLAM_ID = "ca-app-pub-3183404528711365/1691243042";
    private static final String REKLAM_ID1 = "ca-app-pub-3183404528711365/2561950515";
    private static final String REKLAM_ID2 = "ca-app-pub-3183404528711365/7799138784";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_hard);
        init();
        //reklam_yukle();
        //reklam_yukle1();
        //reklam_yukle2();
        yerlesimKur();
        animalsKonumlaArrayList();
        animasyonUygula();
    }

    private void reklam_yukle(){

        adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(REKLAM_ID);
        reklam_layout.addView(adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        adView.loadAd(adRequest);

    }

    private void reklam_yukle1(){

        adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(REKLAM_ID1);
        reklam_layout1.addView(adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        adView.loadAd(adRequest);

    }

    private void reklam_yukle2(){

        adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(REKLAM_ID2);
        reklam_layout2.addView(adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        adView.loadAd(adRequest);

    }

    public void sesCikar(int sesId){
        ses = MediaPlayer.create(getApplicationContext(),sesId);
        ses.start();
    }

    private void animasyonUygula(){
        if(Build.VERSION.SDK_INT >=21){
            Slide enterTransition = new Slide();
            enterTransition.setDuration(300);
            enterTransition.setSlideEdge(Gravity.RIGHT);
            getWindow().setEnterTransition(enterTransition);
        }
    }

    // geri butonuna basıldığında çalışır
    @Override
    public boolean onSupportNavigateUp(){
        Intent intent = new Intent(context, GirisSayfasi.class);
        if(Build.VERSION.SDK_INT >= 21) {

            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finishAfterTransition();
        }
        else {
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
        return true;
    }

    // geri butonuna basıldığında çalışır
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(context, GirisSayfasi.class);
        if(Build.VERSION.SDK_INT >= 21) {

            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finishAfterTransition();
        }
        else {
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        finish();
        super.onPause();
    }

  /*  @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {//Back key pressed
            //Things to Do
            goHomePage(1);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
*/
    public void goHomePage(int i){
        Intent intent = new Intent(context, GirisSayfasi.class);
        if(i == 0){
            // gameover activiy kodlanacak.
            startActivity(intent);
        }else if(i == 1){
            startActivity(intent);
        }
    }

    private void setLanguage(){
        if(language.equals("English")){
            levelLanguage = "Level: ";
            skorText.setText("Score:");
        }else if(language.equals("Finnish")){
            levelLanguage = "Taso: ";
            skorText.setText("Pisteet:");
        }else if(language.equals("German")){
            levelLanguage = "Niveau: ";
            skorText.setText("Ergebnis:");
        }else if(language.equals("Turkish")){
            levelLanguage = "Seviye: ";
            skorText.setText("Skor:");
        }else if(language.equals("Spanish")){
            levelLanguage = "Nivel: ";
            skorText.setText("Puntuación:");
        }else if(language.equals("Italian")){
            levelLanguage = "Livello: ";
            skorText.setText("Punto:");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void init(){
        sharePreferences = new SharePreferences();
        language = sharePreferences.getString(context,"language");

        reklam_layout = findViewById(R.id.reklam_layout);
        reklam_layout1 = findViewById(R.id.reklam_layout1);
        reklam_layout2 = findViewById(R.id.reklam_layout2);

        textToSpeech = new TextToSpeech(context,this);
        image1=findViewById(R.id.image1);
        image2=findViewById(R.id.image2);
        image3=findViewById(R.id.image3);
        image4=findViewById(R.id.image4);
        image5=findViewById(R.id.image5);
        image6=findViewById(R.id.image6);
        image7=findViewById(R.id.image7);
        image8=findViewById(R.id.image8);
        image9=findViewById(R.id.image9);
        image10=findViewById(R.id.image10);
        image11=findViewById(R.id.image11);
        image12=findViewById(R.id.image12);
        image13=findViewById(R.id.image13);
        image14=findViewById(R.id.image14);
        image15=findViewById(R.id.image15);
        image16=findViewById(R.id.image16);
        image17=findViewById(R.id.image17);
        image18=findViewById(R.id.image18);
        image19=findViewById(R.id.image19);
        image20=findViewById(R.id.image20);

        image1.setOnClickListener(this);
        image2.setOnClickListener(this);
        image3.setOnClickListener(this);
        image4.setOnClickListener(this);
        image5.setOnClickListener(this);
        image6.setOnClickListener(this);
        image7.setOnClickListener(this);
        image8.setOnClickListener(this);
        image9.setOnClickListener(this);
        image10.setOnClickListener(this);
        image11.setOnClickListener(this);
        image12.setOnClickListener(this);
        image13.setOnClickListener(this);
        image14.setOnClickListener(this);
        image15.setOnClickListener(this);
        image16.setOnClickListener(this);
        image17.setOnClickListener(this);
        image18.setOnClickListener(this);
        image19.setOnClickListener(this);
        image20.setOnClickListener(this);

        font = Typeface.createFromAsset(getAssets(),"fonts/Candy_Shop_Black.ttf");

        skor = findViewById(R.id.skor);
        levelGoster = findViewById(R.id.level);

        skorText = findViewById(R.id.skorText);
        skorText.setTypeface(font, Typeface.BOLD);

        skor.setTypeface(font,Typeface.BOLD);
        levelGoster.setTypeface(font,Typeface.BOLD);

        animation1 = AnimationUtils.loadAnimation(this, R.anim.to_middle);
        animation1.setAnimationListener(this);
        animation2 = AnimationUtils.loadAnimation(this, R.anim.from_middle);
        animation2.setAnimationListener(this);

        setLanguage();
        loadAnimals();

        hardLevel = sharePreferences.getInt(context,sonLevel) ;
        //Toast.makeText(context," İnit Level "+hardLevel,Toast.LENGTH_LONG).show();

        maxSkor = sharePreferences.getInt(context,maksSkor);
        hardScore = sharePreferences.getInt(context,sonSkor);

        if(hardLevel == animals.size()){
            hardLevel = 0;
            hardScore = 0;
            sharePreferences.saveInt(context,maksSkor,maxSkor);
            sharePreferences.saveInt(context,sonSkor,0);
            sharePreferences.saveInt(context,sonLevel,hardLevel);
        }

        skorLevelTablo = findViewById(R.id.skorLevelTablo);

        skor.setText(String.valueOf(sharePreferences.getInt(context, sonSkor)));
        String formattedLevel = levelLanguage+ String.valueOf(sharePreferences.getInt(context, sonLevel) / 10 + 1);
        levelGoster.setText(formattedLevel);

        // Bileşenleri eşleştiriyoruz.
        progressBar =  findViewById(R.id.progressBar1);

        textView = findViewById(R.id.textView2);
        textView.setTypeface(font,Typeface.BOLD);

        progressBar.setMin(0);
        progressBar.setMax(100);
        progressBar.setIndeterminate(false);
        initProgressBar();

        tahta = findViewById(R.id.tahta);
        images = new  ArrayList<>();

        imagesKilitleAc = new  ArrayList<>();
        imagesKilitleAc.add(image1);
        imagesKilitleAc.add(image2);
        imagesKilitleAc.add(image3);
        imagesKilitleAc.add(image4);
        imagesKilitleAc.add(image5);
        imagesKilitleAc.add(image6);
        imagesKilitleAc.add(image7);
        imagesKilitleAc.add(image8);
        imagesKilitleAc.add(image9);
        imagesKilitleAc.add(image10);
        imagesKilitleAc.add(image11);
        imagesKilitleAc.add(image12);
        imagesKilitleAc.add(image13);
        imagesKilitleAc.add(image14);
        imagesKilitleAc.add(image15);
        imagesKilitleAc.add(image16);
        imagesKilitleAc.add(image17);
        imagesKilitleAc.add(image18);
        imagesKilitleAc.add(image19);
        imagesKilitleAc.add(image20);

        setViewsColors();

        tiklananImageIdler = new ArrayList<>();
        yerlesimBelirle = new ArrayList<>();
        animalsKonumla = new ArrayList<>();

        setKronometre().start();
    }

    private void setMaxRekor(int max) {
        sharePreferences.saveInt(context,maksSkor,max);
    }

    private void setSkor(){
        fadeIn = new AlphaAnimation(0.0f, 1.0f);
        fadeOut = new AlphaAnimation(1.0f, 0.0f);

        fadeIn.setDuration(5);
        fadeIn.setFillAfter(true);
        fadeOut.setDuration(5);
        fadeOut.setFillAfter(true);

        fadeIn.setAnimationListener(this);
        fadeOut.setAnimationListener(this);
        skor.startAnimation(fadeIn);
        skor.startAnimation(fadeOut);

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void setViewsColors(){
        if( backColour % 9 == 0 ){
            for(int i = 0; i<20; i++)
                (imagesKilitleAc.get(i)).setBackground(getResources().getDrawable(R.mipmap.pink));
            skorLevelTablo.setBackground(getResources().getDrawable(R.drawable.pink));
            backColourCode = R.mipmap.pink;
            backColourCodeSecond = R.drawable.pink;

        }else if(backColour % 9 == 1 ){

            for(int i = 0; i<20; i++)
                (imagesKilitleAc.get(i)).setBackground(getResources().getDrawable(R.mipmap.seagreen));
            skorLevelTablo.setBackground(getResources().getDrawable(R.drawable.seagreen));
            backColourCode = R.mipmap.seagreen;
            backColourCodeSecond = R.drawable.seagreen;

        }else if(backColour % 9 == 2 ){
            for(int i = 0; i<20; i++)
                (imagesKilitleAc.get(i)).setBackground(getResources().getDrawable(R.mipmap.orange));
            skorLevelTablo.setBackground(getResources().getDrawable(R.drawable.orange));
            backColourCode = R.mipmap.orange;
            backColourCodeSecond = R.drawable.orange;

        }else if(backColour % 9 == 3 ){
            for(int i = 0; i<20; i++)
                (imagesKilitleAc.get(i)).setBackground(getResources().getDrawable(R.mipmap.purple));
            skorLevelTablo.setBackground(getResources().getDrawable(R.drawable.purple));
            backColourCode = R.mipmap.purple;
            backColourCodeSecond = R.drawable.purple;

        }else if(backColour % 9 == 4 ){
            for(int i = 0; i<20; i++)
                (imagesKilitleAc.get(i)).setBackground(getResources().getDrawable(R.mipmap.green));
            skorLevelTablo.setBackground(getResources().getDrawable(R.drawable.green));
            backColourCode = R.mipmap.green;
            backColourCodeSecond = R.drawable.green;

        }else if(backColour % 9 == 5 ){
            for(int i = 0; i<20; i++)
                (imagesKilitleAc.get(i)).setBackground(getResources().getDrawable(R.mipmap.yellow));
            skorLevelTablo.setBackground(getResources().getDrawable(R.drawable.yellow));
            backColourCode = R.mipmap.yellow;
            backColourCodeSecond = R.drawable.yellow;

        }else if(backColour % 9 == 6 ){
            for(int i = 0; i<20; i++)
                (imagesKilitleAc.get(i)).setBackground(getResources().getDrawable(R.mipmap.coffee));
            skorLevelTablo.setBackground(getResources().getDrawable(R.drawable.coffee));
            backColourCode = R.mipmap.coffee;
            backColourCodeSecond = R.drawable.coffee;

        }else if(backColour % 9 == 7 ){
            for(int i = 0; i<20; i++)
                (imagesKilitleAc.get(i)).setBackground(getResources().getDrawable(R.mipmap.red));
            skorLevelTablo.setBackground(getResources().getDrawable(R.drawable.red));
            backColourCode = R.mipmap.red;
            backColourCodeSecond = R.drawable.red;

        }else if(backColour % 9 == 8 ){
            for(int i = 0; i<20; i++)
                (imagesKilitleAc.get(i)).setBackground(getResources().getDrawable(R.mipmap.gri));
            skorLevelTablo.setBackground(getResources().getDrawable(R.drawable.gri));
            backColourCode = R.mipmap.gri;
            backColourCodeSecond = R.drawable.gri;
        }
        backColour++;
        if(backColour == 91)
            backColour = 0;
    }

    @Override
    public void onAnimationEnd(Animation animType) {

        if(animType.equals(fadeIn) || animType.equals(fadeOut)) {
            int puan = progressBar.getMax() - progressBar.getProgress();
            //Toast.makeText(context, puan + "", Toast.LENGTH_LONG).show();
            hardDurum++;

            hardScore += 1;
            skor.setText(String.valueOf(hardScore));
            if (hardDurum < puan) {
                skor.startAnimation(fadeIn);
                skor.startAnimation(fadeOut);
            } else {
                hardDurum = 0;
                skor.clearAnimation();

            }
            if (maxSkor >= splitString(skor.getText().toString())) {
                setMaxRekor(splitString(skor.getText().toString()));
            }
        }else if(animType.equals(animation1)){
            //image.setBackground(getResources().getDrawable(animalsKonumla.get(animalKonumla(image)).getImage()));
            image.setBackgroundResource(animalsKonumla.get(animalKonumla(image)).getImage());
            image.clearAnimation();
            tiklananImageIdler.add(animalsKonumla.get(animalKonumla(image)).getImage());
            customToast(animalsKonumla.get(animalKonumla(image)).getName(),animalsKonumla.get(animalKonumla(image)).getImage());
            //Toast.makeText(context," "+animalsKonumla.get(animalKonumla(image)).getName(),Toast.LENGTH_LONG ).show();
            text = animalsKonumla.get(getNameIndex(image)).getName();
            convertTextToSpeech();
            images.add(image);

            if(images.size() == 2) {
                new Handler().postDelayed(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void run() {
                        eslendiMi();

                    }
                }, 500); // Millisecond 1000 = 1 sec

            }else{

                kilitAcEslenmedi();
            }
        }else if(animType.equals(animation2)) {

            sesCikar(R.raw.fail);
            ImageView v1 = images.get(0);
            ImageView v2 =  images.get(1);

            v1.clearAnimation();
            v2.clearAnimation();

            removeKilitleAc();
            kilitAcEslenmedi();
            images.clear();
            tiklananImageIdler.clear();
        }
    }


    @Override
    public void onAnimationRepeat(Animation animType) {
    }

    @Override
    public void onAnimationStart(Animation animType) {
        sesCikar(R.raw.turn);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void eslenmedi(){
        ImageView v1 = images.get(0);
        ImageView v2 =  images.get(1);
        setAnimationType(v1,v2);

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private  void setAnimationType(ImageView v1, ImageView v2){
        v1.setBackground(getResources().getDrawable(backColourCode));
        v1.clearAnimation();
        v1.setAnimation(animation2);
        v1.startAnimation(animation2);

        v2.setBackground(getResources().getDrawable(backColourCode));
        v2.clearAnimation();
        v2.setAnimation(animation2);
        v2.startAnimation(animation2);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        int id = v.getId();
        kilitle();
        image = null;
        switch (id) {
            case R.id.image1:
                image1.setClickable(false);
                image = image1;
                setAnimationType(image);
                break;
            case R.id.image2:
                image2.setClickable(false);
                image = image2;
                setAnimationType(image);
                break;
            case R.id.image3:
                image3.setClickable(false);
                image = image3;
                setAnimationType(image);
                break;
            case R.id.image4:
                image4.setClickable(false);
                image = image4;
                setAnimationType(image);
                break;
            case R.id.image5:
                image5.setClickable(false);
                image = image5;
                setAnimationType(image);
                break;
            case R.id.image6:
                image6.setClickable(false);
                image = image6;
                setAnimationType(image);
                break;
            case R.id.image7:
                image7.setClickable(false);
                image = image7;
                setAnimationType(image);
                break;
            case R.id.image8:
                image8.setClickable(false);
                image = image8;
                setAnimationType(image);
                break;
            case R.id.image9:
                image9.setClickable(false);
                image = image9;
                setAnimationType(image);
                break;
            case R.id.image10:
                image10.setClickable(false);
                image = image10;
                setAnimationType(image);
                break;
            case R.id.image11:
                image11.setClickable(false);
                image = image11;
                setAnimationType(image);
                break;
            case R.id.image12:
                image12.setClickable(false);
                image = image12;
                setAnimationType(image);
                break;
            case R.id.image13:
                image13.setClickable(false);
                image = image13;
                setAnimationType(image);
                break;
            case R.id.image14:
                image14.setClickable(false);
                image = image14;
                setAnimationType(image);
                break;
            case R.id.image15:
                image15.setClickable(false);
                image = image15;
                setAnimationType(image);
                break;
            case R.id.image16:
                image16.setClickable(false);
                image = image16;
                setAnimationType(image);
                break;
            case R.id.image17:
                image17.setClickable(false);
                image = image17;
                setAnimationType(image);
                break;
            case R.id.image18:
                image18.setClickable(false);
                image = image18;
                setAnimationType(image);
                break;
            case R.id.image19:
                image19.setClickable(false);
                image = image19;
                setAnimationType(image);
                break;
            case R.id.image20:
                image20.setClickable(false);
                image = image20;
                setAnimationType(image);
                break;
        }

    }

    private void setAnimationType(ImageView image){
        image.clearAnimation();
        image.setAnimation(animation1);
        image.startAnimation(animation1);
    }

    private void initProgressBar() { //Başlangıç değerlerini set ediyoruz.
        progressStatus=100;
        progressBar.setProgress(progressStatus);
        textView.setText(String.valueOf(100));

    }

    private Thread setKronometre(){
        return new Thread(new Runnable() {

            public void run() {

                while (progressStatus >= 0) {

                    while (suspended) { //Eğer kronometre durdurulduysa bekle
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    if(progressBar.getProgress() == 0){
                        try {
                            Thread.sleep(3000);
                            goHomePage(0);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    progressStatus -= 1;
                    // yeni değeri ekranda göster ve progressBar'a set et.
                    handler.post(new Runnable() {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        public void run() {
                            progressBar.setProgress(progressStatus);
                            textView.setText(String.valueOf(progressStatus));
                        }
                    });
                    try {
                        // Sleep for 1 second.
                        // Just to display the progress slowly
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void animalsKonumlaArrayList(){
        for(int i = 0; i<yerlesimBelirle.size(); i++)
            s+="  "+yerlesimBelirle.get(i);

        //Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
        if(hardLevel >= animals.size())
            hardLevel = 0;

        for(int i = 0; i<yerlesimBelirle.size(); i++){
            if(yerlesimBelirle.get(i) == 1)
                animalsKonumla.add(animals.get(hardLevel));
            else if(yerlesimBelirle.get(i) == 2)
                animalsKonumla.add(animals.get(hardLevel+1));
            else if(yerlesimBelirle.get(i) == 3)
                animalsKonumla.add(animals.get(hardLevel+2));
            else if(yerlesimBelirle.get(i) == 4)
                animalsKonumla.add(animals.get(hardLevel+3));
            else if(yerlesimBelirle.get(i) == 5)
                animalsKonumla.add(animals.get(hardLevel+4));
            else if(yerlesimBelirle.get(i) == 6)
                animalsKonumla.add(animals.get(hardLevel+5));
            else if(yerlesimBelirle.get(i) == 7)
                animalsKonumla.add(animals.get(hardLevel+6));
            else if(yerlesimBelirle.get(i) == 8)
                animalsKonumla.add(animals.get(hardLevel+7));
            else if(yerlesimBelirle.get(i) == 9)
                animalsKonumla.add(animals.get(hardLevel+8));
            else if(yerlesimBelirle.get(i) == 10)
                animalsKonumla.add(animals.get(hardLevel+9));
        }
    }

    int animalKonumla (ImageView image){

        int id = image.getId();
        switch (id){
            case R.id.image1:
                return 0;
            case R.id.image2:
                return 1;
            case R.id.image3:
                return 2;
            case R.id.image4:
                return 3;
            case R.id.image5:
                return 4;
            case R.id.image6:
                return 5;
            case R.id.image7:
                return 6;
            case R.id.image8:
                return 7;
            case R.id.image9:
                return 8;
            case R.id.image10:
                return 9;
            case R.id.image11:
                return 10;
            case R.id.image12:
                return 11;
            case R.id.image13:
                return 12;
            case R.id.image14:
                return 13;
            case R.id.image15:
                return 14;
            case R.id.image16:
                return 15;
            case R.id.image17:
                return 16;
            case R.id.image18:
                return 17;
            case R.id.image19:
                return 18;
            case R.id.image20:
                return 19;
        }
        return -1;
    }

    private void loadAnimals(){

        String [] animalArray = new String[120];
        if(language.equals("English")){
            animalArray   =  getResources().getStringArray(R.array.animals_english);
        }else  if(language.equals("Finnish")){
            animalArray   =  getResources().getStringArray(R.array.animals_finnish);
        }else  if(language.equals("German")){
            animalArray   =  getResources().getStringArray(R.array.animals_german);
        }else  if(language.equals("Turkish")){
            animalArray   =  getResources().getStringArray(R.array.animals_turkish);
        }else  if(language.equals("Spanish")){
            animalArray   =  getResources().getStringArray(R.array.animals_spanish);
        }else  if(language.equals("Italian")){
            animalArray   =  getResources().getStringArray(R.array.animals_italian);
        }else  if(language.equals("Russian")){
            animalArray   =  getResources().getStringArray(R.array.animals_russian);
        }else  if(language.equals("Chinese")){
            animalArray   =  getResources().getStringArray(R.array.animals_chinese);
        }else  if(language.equals("Greek")){
            animalArray   =  getResources().getStringArray(R.array.animals_greek);
        }

        Animals animal ;
        animals = new ArrayList<>();
        for(int i = 0; i< animalArray.length; i++){
            animal = new Animals(animalArray[i]);
            animal.setImage(imageId(i));
            animals.add(animal);
        }
    }

    int imageId (int animalName){
        switch (animalName){
            case 0:
                return R.mipmap.ant;
            case 1:
                return R.mipmap.antelope;
            case 2:
                return R.mipmap.anaconda;
            case 3:
                return R.mipmap.anteater;
            case 4:
                return R.mipmap.angus;
            case 5:
                return R.mipmap.bee;
            case 6:
                return R.mipmap.butterfly;
            case 7:
                return R.mipmap.bison;
            case 8:
                return R.mipmap.bird;
            case 9:
                return R.mipmap.bat;
            case 10:
                return R.mipmap.bear;
            case 11:
                return R.mipmap.beaver;
            case 12:
                return R.mipmap.bull;
            case 13:
                return R.mipmap.canary;
            case 14:
                return R.mipmap.cheetah;
            case 15:
                return R.mipmap.chick;
            case 16:
                return R.mipmap.chameleon;
            case 17:
                return R.mipmap.crow;
            case 18:
                return R.mipmap.cow;
            case 19:
                return R.mipmap.coyote;
            case 20:
                return R.mipmap.cat;
            case 21:
                return R.mipmap.chimpanzee;
            case 22:
                return R.mipmap.chicken;
            case 23:
                return R.mipmap.crab;
            case 24:
                return R.mipmap.crocodile;
            case 25:
                return R.mipmap.deer;
            case 26:
                return R.mipmap.donkey;
            case 27:
                return R.mipmap.dog;
            case 28:
                return R.mipmap.dolphin;
            case 29:
                return R.mipmap.dove;
            case 30:
                return R.mipmap.duck;
            case 31:
                return R.mipmap.elephant;
            case 32:
                return R.mipmap.eagle;
            case 33:
                return R.mipmap.fish;
            case 34:
                return R.mipmap.frog;
            case 35:
                return R.mipmap.falcon;
            case 36:
                return R.mipmap.fly;
            case 37:
                return R.mipmap.fox;
            case 38:
                return R.mipmap.grasshopper;
            case 39:
                return R.mipmap.goat;
            case 40:
                return R.mipmap.goose;
            case 41:
                return R.mipmap.giraffe;
            case 42:
                return R.mipmap.gorilla;
            case 43:
                return R.mipmap.hamster;
            case 44:
                return R.mipmap.hawk;
            case 45:
                return R.mipmap.horse;
            case 46:
                return R.mipmap.hedgehog;
            case 47:
                return R.mipmap.hippopotamus;
            case 48:
                return R.mipmap.insect;
            case 49:
                return R.mipmap.impala;
            case 50:
                return R.mipmap.iguana;
            case 51:
                return R.mipmap.jackal;
            case 52:
                return R.mipmap.jaguar;
            case 53:
                return R.mipmap.jellyfish;
            case 54:
                return R.mipmap.kangaroo;
            case 55:
                return R.mipmap.koala;
            case 56:
                return R.mipmap.ladybug;
            case 57:
                return R.mipmap.lamb;
            case 58:
                return R.mipmap.leopard;
            case 59:
                return R.mipmap.lion;
            case 60:
                return R.mipmap.lizard;
            case 61:
                return R.mipmap.lobster;
            case 62:
                return R.mipmap.millipede;
            case 63:
                return R.mipmap.mouse;
            case 64:
                return R.mipmap.mosquito;
            case 65:
                return R.mipmap.monkey;
            case 66:
                return R.mipmap.mole;
            case 67:
                return R.mipmap.mule;
            case 68:
                return R.mipmap.octopus;
            case 69:
                return R.mipmap.ostrich;
            case 70:
                return R.mipmap.panda;
            case 71:
                return R.mipmap.panther;
            case 72:
                return R.mipmap.parrot;
            case 73:
                return R.mipmap.peacock;
            case 74:
                return R.mipmap.pelican;
            case 75:
                return R.mipmap.penguin;
            case 76:
                return R.mipmap.puma;
            case 77:
                return R.mipmap.pig;
            case 78:
                return R.mipmap.piranha;
            case 79:
                return R.mipmap.python;
            case 80:
                return R.mipmap.pecker;
            case 81:
                return R.mipmap.rabbit;
            case 82:
                return R.mipmap.raccoon;
            case 83:
                return R.mipmap.rooster;
            case 84:
                return R.mipmap.roach;
            case 85:
                return R.mipmap.rat;
            case 86:
                return R.mipmap.rhinoceros;
            case 87:
                return R.mipmap.sable;
            case 88:
                return R.mipmap.snail;
            case 89:
                return R.mipmap.seagull;
            case 90:
                return R.mipmap.sheep;
            case 91:
                return R.mipmap.shark;
            case 92:
                return R.mipmap.seahorse;
            case 93:
                return R.mipmap.spider;
            case 94:
                return R.mipmap.sparrow;
            case 95:
                return R.mipmap.snake;
            case 96:
                return R.mipmap.starfish;
            case 97:
                return R.mipmap.stork;
            case 98:
                return R.mipmap.skunk;
            case 99:
                return R.mipmap.swordfish;
            case 100:
                return R.mipmap.scorpion;
            case 101:
                return R.mipmap.squid;
            case 102:
                return R.mipmap.tarantula;
            case 103:
                return R.mipmap.tiger;
            case 104:
                return R.mipmap.turkey;
            case 105:
                return R.mipmap.turtle;
            case 106:
                return R.mipmap.vulture;
            case 107:
                return R.mipmap.viper;
            case 108:
                return R.mipmap.webworm;
            case 109:
                return R.mipmap.whale;
            case 110:
                return R.mipmap.wolf;
            case 111:
                return R.mipmap.worm;
            case 112:
                return R.mipmap.zebra;
            case 113:
                return R.mipmap.ant;
            case 114:
                return R.mipmap.antelope;
            case 115:
                return R.mipmap.anaconda;
            case 116:
                return R.mipmap.anteater;
            case 117:
                return R.mipmap.angus;
            case 118:
                return R.mipmap.bee;
            case 119:
                return R.mipmap.butterfly;

        }
        return 0;
    }

    void yerlesimKur(){
        Random r = new Random();
        ArrayList<Integer> konumlar = new ArrayList<>();
        int konum = 0;
        for(int i = 0; i<20 ; i++) {
            konum = r.nextInt(40);
            konumlar.add(konum);
        }

        for(int x = 0; x<40;x++){
            yerlesimBelirle.add(0);
        }

        yerlesimBelirle.add(konumlar.get(0),1);
        yerlesimBelirle.add(konumlar.get(1),1);
        yerlesimBelirle.add(konumlar.get(2),2);
        yerlesimBelirle.add(konumlar.get(3),2);
        yerlesimBelirle.add(konumlar.get(4),3);
        yerlesimBelirle.add(konumlar.get(5),3);
        yerlesimBelirle.add(konumlar.get(6),4);
        yerlesimBelirle.add(konumlar.get(7),4);
        yerlesimBelirle.add(konumlar.get(8),5);
        yerlesimBelirle.add(konumlar.get(9),5);
        yerlesimBelirle.add(konumlar.get(10),6);
        yerlesimBelirle.add(konumlar.get(11),6);
        yerlesimBelirle.add(konumlar.get(12),7);
        yerlesimBelirle.add(konumlar.get(13),7);
        yerlesimBelirle.add(konumlar.get(14),8);
        yerlesimBelirle.add(konumlar.get(15),8);
        yerlesimBelirle.add(konumlar.get(16),9);
        yerlesimBelirle.add(konumlar.get(17),9);
        yerlesimBelirle.add(konumlar.get(18),10);
        yerlesimBelirle.add(konumlar.get(19),10);

        for(int x = 0; x<yerlesimBelirle.size();x++){
            if(yerlesimBelirle.get(x) == 0){
                yerlesimBelirle.remove(x);
                x--;
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void eslendiMi(){

        final ImageView v1 =  images.get(0);
        final ImageView v2 =  images.get(1);

        //Toast.makeText(context,tiklananImageIdler.get(0)+" "+tiklananImageIdler.get(1),Toast.LENGTH_LONG ).show();
        int id1 = tiklananImageIdler.get(0);
        int id2 = tiklananImageIdler.get(1);
        if(id1 == id2){
            oyunBittiMi = oyunBittiMi+1;
            tiklananImageIdler.clear();

            //Toast.makeText(this,"Eşleşti",Toast.LENGTH_SHORT).show();
            v1.setVisibility(View.INVISIBLE);
            v2.setVisibility(View.INVISIBLE);
            sesCikar(R.raw.diseppear);
            removeKilitleAc();
            kilitAcEslendi();
            images.clear();
        }else {
            eslenmedi();
        }

        if(oyunBittiMi == 10){

            image1.setVisibility(View.GONE);
            image2.setVisibility(View.GONE);
            image3.setVisibility(View.GONE);
            image4.setVisibility(View.GONE);
            image5.setVisibility(View.GONE);
            image6.setVisibility(View.GONE);
            image7.setVisibility(View.GONE);
            image8.setVisibility(View.GONE);
            image9.setVisibility(View.GONE);
            image10.setVisibility(View.GONE);
            image11.setVisibility(View.GONE);
            image12.setVisibility(View.GONE);
            image13.setVisibility(View.GONE);
            image14.setVisibility(View.GONE);
            image15.setVisibility(View.GONE);
            image16.setVisibility(View.GONE);
            image17.setVisibility(View.GONE);
            image18.setVisibility(View.GONE);
            image19.setVisibility(View.GONE);
            image20.setVisibility(View.GONE);

            reklam_layout.setVisibility(View.GONE);
            reklam_layout1.setVisibility(View.GONE);
            reklam_layout2.setVisibility(View.GONE);

            // stop progres bar
            suspended = true;
            setSkor();
            imagesKilitleAc.clear();

            LinearLayout.LayoutParams params= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(10,10,10,10);
            params.gravity = Gravity.CENTER;

            /*
            rekorText = new TextView(context);
            rekorText.setText(String.format("Record: %s", String.valueOf(sharePreferences.getInt(context, maksSkor))));
            rekorText.setTextSize(40);
            LinearLayout.LayoutParams params= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(10,10,10,10);
            params.gravity = Gravity.CENTER;
            rekorText.setLayoutParams(params);
            rekorText.setTextColor(getResources().getColor(R.color.colorAccent));
            rekorText.setBackgroundColor(getResources().getColor(R.color.colorWhite));

            rekorText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    maksimumRekoruAyarla();
                    rekorText.setText(String.format("Record: %s", String.valueOf(sharePreferences.getInt(context, maksSkor))));
                }
            });

            */

            LinearLayout.LayoutParams  params2= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params2.setMargins(10,10,10,10);
            params2.gravity = Gravity.CENTER;

            LayoutInflater inflater=getLayoutInflater();

            View customToastroot =inflater.inflate(R.layout.level_passing_page, null);

            LinearLayout play = new LinearLayout(context);
            play.setLayoutParams(params2);
            play.setBackground(getResources().getDrawable(backColourCodeSecond));
            customToastroot.setLayoutParams(params2);
            TextView toastTextview = customToastroot.findViewById(R.id.tebrikText);
            if(language.equals("English"))
                toastTextview.setText("Excellent");
            else if(language.equals("Finnish"))
                toastTextview.setText("Erinomainen");
            else if(language.equals("German"))
                toastTextview.setText("Ausgezeichnet");
            else if(language.equals("Turkish"))
                toastTextview.setText("Mükemmel");
            else if(language.equals("Spanish"))
                toastTextview.setText("Excelente");
            else if(language.equals("Italian"))
                toastTextview.setText("Eccellente");

            toastTextview.setTextSize(18);
            toastTextview.setTypeface(font, Typeface.BOLD);


            ImageButton play_next = customToastroot.findViewById(R.id.play_next_Level);
            ImageButton look_back = customToastroot.findViewById(R.id.look_back);
            ImageButton look_forward = customToastroot.findViewById(R.id.look_forward);
            play.addView(customToastroot);

            /*
            tebrikText = new TextView(context);
            tebrikText.setText(R.string.tebrik);

            tebrikText.setGravity(Gravity.TOP);

            //tebrikText.setGravity(Gravity.TOP);

            tebrikText = new TextView(context);
            tebrikText.setText(R.string.tebrik);
            //tebrikText.setGravity(Gravity.TOP);
            tebrikText.setTextSize(40);
            params.setMargins(10,10,10,10);
            params.gravity = Gravity.CENTER;
            tebrikText.setLayoutParams(params);
            tebrikText.setTextColor(getResources().getColor(R.color.colorAccent));
            tebrikText.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            tebrikText.setTypeface(font, Typeface.BOLD);

            levelAtla = new Button(context);
            LinearLayout.LayoutParams  params1= new LinearLayout.LayoutParams(300,300);
            params1.setMargins(10,10,10,10);
            params1.gravity = Gravity.CENTER;
            levelAtla.setLayoutParams(params1);
            levelAtla.setTextSize(30);

            levelAtla.setText("Play Next Level");

            levelAtla.setTypeface(font, Typeface.BOLD);

            levelAtla.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            levelAtla.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

            LinearLayout.LayoutParams  params2= new LinearLayout.LayoutParams(300,300
            );
            params2.setMargins(0,0,0,0);
            params2.gravity = Gravity.CENTER;
            levelAtla.setLayoutParams(params2);
            levelAtla.setTextSize(30);

            levelAtla.setText("Play Next Level");

            levelAtla.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            levelAtla.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

            LinearLayout.LayoutParams  params3= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params3.setMargins(0,0,0,0);

            LinearLayout satir1 = new LinearLayout(this);
            satir1.setOrientation(LinearLayout.HORIZONTAL);
            satir1.setGravity(Gravity.CENTER);


            LinearLayout satir2 = new LinearLayout(this);
            satir2.setOrientation(LinearLayout.HORIZONTAL);
            satir2.setGravity(Gravity.CENTER);


            LinearLayout satir3 = new LinearLayout(this);
            satir3.setOrientation(LinearLayout.HORIZONTAL);
            satir3.setGravity(Gravity.CENTER);


            LinearLayout satir4 = new LinearLayout(this);
            satir4.setOrientation(LinearLayout.HORIZONTAL);
            satir4.setGravity(Gravity.CENTER);

            imageBir = new ImageView(context);
            imageBir.setLayoutParams(params1);
            imageBir.setBackgroundResource(animals.get(hardLevel).getImage());
            TextView textBir = new TextView(context);
            //textBir.setLayoutParams(params2);
            textBir.setTextSize(12);
            textBir.setTextColor(getResources().getColor(R.color.colorAccent));
            textBir.setText(animals.get(hardLevel).getName());

            imageIki = new ImageView(context);
            imageIki.setLayoutParams(params1);
            imageIki.setBackgroundResource(animals.get(hardLevel+1).getImage());
            TextView textIki = new TextView(context);
            // textIki.setLayoutParams(params2);
            textIki.setTextSize(12);
            textIki.setTextColor(getResources().getColor(R.color.colorAccent));
            textIki.setText(animals.get(hardLevel+1).getName());

            imageUc = new ImageView(context);
            imageUc.setLayoutParams(params1);
            imageUc.setBackgroundResource(animals.get(hardLevel+2).getImage());
            TextView textUc = new TextView(context);
            //textUc.setLayoutParams(params2);
            textUc.setTextSize(12);
            textUc.setTextColor(getResources().getColor(R.color.colorAccent));
            textUc.setText(animals.get(hardLevel+2).getName());

            imageDort = new ImageView(context);
            imageDort.setLayoutParams(params1);
            imageDort.setBackgroundResource(animals.get(hardLevel+3).getImage());
            TextView textDort = new TextView(context);
            // textDort.setLayoutParams(params2);
            textDort.setTextSize(12);
            textDort.setTextColor(getResources().getColor(R.color.colorAccent));
            textDort.setText(animals.get(hardLevel+3).getName());

            imageBes = new ImageView(context);
            imageBes.setLayoutParams(params1);
            imageBes.setBackgroundResource(animals.get(hardLevel+4).getImage());
            TextView textBes = new TextView(context);
            //textBes.setLayoutParams(params2);
            textBes.setTextSize(12);
            textBes.setTextColor(getResources().getColor(R.color.colorAccent));
            textBes.setText(animals.get(hardLevel+4).getName());

            imageAlti = new ImageView(context);
            imageAlti.setLayoutParams(params1);
            imageAlti.setBackgroundResource(animals.get(hardLevel+5).getImage());
            TextView textAlti = new TextView(context);
            //textAlti.setLayoutParams(params2);
            textAlti.setTextSize(12);
            textAlti.setTextColor(getResources().getColor(R.color.colorAccent));
            textAlti.setText(animals.get(hardLevel+5).getName());

            imageYedi = new ImageView(context);
            imageYedi.setLayoutParams(params1);
            imageYedi.setBackgroundResource(animals.get(hardLevel+6).getImage());
            TextView textYedi = new TextView(context);
            //textAlti.setLayoutParams(params2);
            textYedi.setTextSize(12);
            textYedi.setTextColor(getResources().getColor(R.color.colorAccent));
            textYedi.setText(animals.get(hardLevel+6).getName());

            imageSekiz = new ImageView(context);
            imageSekiz.setLayoutParams(params1);
            imageSekiz.setBackgroundResource(animals.get(hardLevel+7).getImage());
            TextView textSekiz = new TextView(context);
            //textAlti.setLayoutParams(params2);
            textSekiz.setTextSize(12);
            textSekiz.setTextColor(getResources().getColor(R.color.colorAccent));
            textSekiz.setText(animals.get(hardLevel+7).getName());

            imageDokuz = new ImageView(context);
            imageDokuz.setLayoutParams(params1);
            imageDokuz.setBackgroundResource(animals.get(hardLevel+8).getImage());
            TextView textDokuz = new TextView(context);
            //textAlti.setLayoutParams(params2);
            textDokuz.setTextSize(12);
            textDokuz.setTextColor(getResources().getColor(R.color.colorAccent));
            textDokuz.setText(animals.get(hardLevel+8).getName());

            imageOn = new ImageView(context);
            imageOn.setLayoutParams(params1);
            imageOn.setBackgroundResource(animals.get(hardLevel+9).getImage());
            TextView textOn = new TextView(context);
            //textAlti.setLayoutParams(params2);
            textOn.setTextSize(12);
            textOn.setTextColor(getResources().getColor(R.color.colorAccent));
            textOn.setText(animals.get(hardLevel+9).getName());


            satir1.addView(imageBir);
            satir1.addView(textBir);
            satir1.addView(imageIki);
            satir1.addView(textIki);

            satir2.addView(imageUc);
            satir2.addView(textUc);
            satir2.addView(imageDort);
            satir2.addView(textDort);
            satir2.addView(imageBes);
            satir2.addView(textBes);

            satir3.addView(imageAlti);
            satir3.addView(textAlti);
            satir3.addView(imageYedi);
            satir3.addView(textYedi);
            satir3.addView(imageSekiz);
            satir3.addView(textSekiz);

            satir4.addView(imageDokuz);
            satir4.addView(textDokuz);
            satir4.addView(imageOn);
            satir4.addView(textOn);

            //tahta.addView(rekorText);
            tahta.addView(tebrikText);
            tahta.addView(satir1);
            tahta.addView(satir2);
            tahta.addView(satir3);
            tahta.addView(satir4);
 */

            LinearLayout.LayoutParams  params3= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params3.gravity = Gravity.CENTER_HORIZONTAL;
            params3.topMargin = 500;

            ImageButton record = new ImageButton(context);
            record.setLayoutParams(params3);
            record.setBackgroundResource(R.drawable.record);

            tahta.addView(play);
            tahta.addView(record,params3);

            final String recordString = String.format("Record: %s", String.valueOf(sharePreferences.getInt(context, maksSkor)));
            record.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    customToast( recordString,  R.mipmap.record);
                }
            });

            play_next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hardLevel = hardLevel + 10;

                    if(maxSkor >= splitString(skor.getText().toString())){
                        setMaxRekor(splitString(skor.getText().toString()));
                    }
                    maksimumRekoruAyarla();

                    sharePreferences.saveInt(context,sonSkor,hardScore);
                    sharePreferences.saveInt(context,sonLevel,hardLevel);

                    tekrarBaslat();
                }
            });
        }

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id){
            case CUSTOM_DIALOG_ID_HARD_BACK:

                final Dialog look_dialog = new Dialog(context);
                look_dialog.getWindow().setBackgroundDrawableResource(R.drawable.custom_play_button);
                look_dialog.setContentView(R.layout.animals_look_list);

                recyclerView = look_dialog.findViewById(R.id.recyclerview_giris);

                AnimalCustomAdapter girisMenuCustomAdapter =
                        new AnimalCustomAdapter(this,AnimalMenuItem.getAnimalMenuItems(hardLevel,0,
                                animals,3));
                recyclerView.setAdapter(girisMenuCustomAdapter);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManager);

        /* GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(gridLayoutManager);  */

                return  look_dialog;

            case CUSTOM_DIALOG_ID_HARD_NEXT:

                final Dialog look_dialog_next = new Dialog(context);
                look_dialog_next.getWindow().setBackgroundDrawableResource(R.drawable.custom_play_button);
                look_dialog_next.setContentView(R.layout.animals_look_list);

                recyclerView = look_dialog_next.findViewById(R.id.recyclerview_giris);

                AnimalCustomAdapter girisMenuCustomAdapter1 =
                        new AnimalCustomAdapter(this,AnimalMenuItem.getAnimalMenuItems(hardLevel,1,
                                animals,3));
                recyclerView.setAdapter(girisMenuCustomAdapter1);

                LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
                linearLayoutManager1.setOrientation(linearLayoutManager1.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManager1);

        /* GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(gridLayoutManager);  */
                return  look_dialog_next;
        }
        return super.onCreateDialog(id);
    }

    public  void tiklananMenuItem(int position) {}

    private void tekrarBaslat(){

        Intent intent = new Intent(context, HardGame.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish(); // Call once you redirect to another activity
    }

    private void maksimumRekoruAyarla() {

        int tmp = sharePreferences.getInt(context,maksSkor);
        int currentMax = 1000 - tmp;

        if(currentMax > tmp)
            sharePreferences.saveInt(context,maksSkor,currentMax);
        else
            sharePreferences.saveInt(context,maksSkor,tmp);
    }

    private int splitString(String string){
        return Integer.valueOf( string.trim());
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onInit(int status) {
        if(status == textToSpeech.SUCCESS){
            int sonuc = 0;
            if(language.equals("English")){
                sonuc =  textToSpeech.setLanguage(Locale.ENGLISH);
            }else  if(language.equals("Finnish")){
                sonuc =  textToSpeech.setLanguage(Locale.getDefault());
            }else  if(language.equals("German")){
                sonuc =  textToSpeech.setLanguage(Locale.GERMAN);
            }else  if(language.equals("Turkish")){
                sonuc =  textToSpeech.setLanguage(Locale.getDefault());
            }else  if(language.equals("Spanish")){
                sonuc =  textToSpeech.setLanguage(Locale.getDefault());
            }else  if(language.equals("Italian")){
                sonuc =  textToSpeech.setLanguage(Locale.ITALIAN);
            }

            if(sonuc == textToSpeech.LANG_MISSING_DATA || sonuc == textToSpeech.LANG_NOT_SUPPORTED){
                Toast.makeText(context,"Not supported language",Toast.LENGTH_SHORT).show();
            }else{
                //convertTextToSpeech();
            }
        }else{
            Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show();
        }
    }
    private void convertTextToSpeech(){

        if(text == null || text.equals("")){
            Toast.makeText(context,"Boş geçmeyiniz",Toast.LENGTH_SHORT).show();
            return;
        }

        textToSpeech.speak(text,textToSpeech.QUEUE_FLUSH,null);

    }

    private int  getNameIndex (ImageView image){
        int id = image.getId();
        switch (id){
            case R.id.image1:
                return 0;
            case R.id.image2:
                return 1;
            case R.id.image3:
                return 2;
            case R.id.image4:
                return 3;
            case R.id.image5:
                return 4;
            case R.id.image6:
                return 5;
            case R.id.image7:
                return 6;
            case R.id.image8:
                return 7;
            case R.id.image9:
                return 8;
            case R.id.image10:
                return 9;
            case R.id.image11:
                return 10;
            case R.id.image12:
                return 11;
            case R.id.image13:
                return 12;
            case R.id.image14:
                return 13;
            case R.id.image15:
                return 14;
            case R.id.image16:
                return 15;
            case R.id.image17:
                return 16;
            case R.id.image18:
                return 17;
            case R.id.image19:
                return 18;
            case R.id.image20:
                return 19;
        }
        return -1;
    }

    public void customToast(String toastText, int image) {

        LinearLayout.LayoutParams  params2= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params2.setMargins(10,10,10,10);
        params2.gravity = Gravity.CENTER;

        LayoutInflater inflater=getLayoutInflater();

        View customToastroot =inflater.inflate(R.layout.mycustom_toast, null);

        LinearLayout back = customToastroot.findViewById(R.id.custom_toast_backround);
        back.setBackgroundResource(backColourCodeSecond);

        Toast customtoast = new Toast(context);

        TextView toastTextview = customToastroot.findViewById(R.id.toastTextview);
        ImageView toastImageview = customToastroot.findViewById(R.id.toastImageView);
        customToastroot.setLayoutParams(params2);
        customtoast.setView(customToastroot);
        customtoast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM,0, 0);
        customtoast.setDuration(Toast.LENGTH_SHORT);
        toastTextview.setText(toastText);
        toastTextview.setTypeface(font, Typeface.BOLD);
        toastImageview.setBackgroundResource(image);
        customtoast.show();
    }

    private void removeKilitleAc(){
        for(int j = 0; j< images.size(); j++) {
            for(int i = 0; i< imagesKilitleAc.size(); i++){
                if (imagesKilitleAc.get(i).getId() == images.get(j).getId()){
                    imagesKilitleAc.remove(i);
                    break;
                }
            }
        }
    }

    private void kilitle(){
        for(int i = 0; i< imagesKilitleAc.size(); i++)
            imagesKilitleAc.get(i).setClickable(false);
    }

    private void kilitAcEslendi(){
        if(images.size() == 2) {

            for (int i = 0; i < imagesKilitleAc.size(); i++) {
                imagesKilitleAc.get(i).setClickable(true);
            }
        }else   if(images.size() == 1) {
            for (int j = 0; j < imagesKilitleAc.size(); j++) {
                for (int i = 0; i < imagesKilitleAc.size(); i++) {
                    if(images.get(0).getId() == imagesKilitleAc.get(i).getId()) {
                        continue;
                    }else
                        imagesKilitleAc.get(i).setClickable(true);
                }
            }
        }
    }

    private void kilitAcEslenmedi() {
        if(images.size() == 2) {

            for (int i = 0; i < images.size(); i++)
                imagesKilitleAc.add(images.get(i));

            for (int i = 0; i < imagesKilitleAc.size(); i++) {
                imagesKilitleAc.get(i).setClickable(true);
            }
        }else   if(images.size() == 1) {
            for (int j = 0; j < imagesKilitleAc.size(); j++) {
                for (int i = 0; i < imagesKilitleAc.size(); i++) {
                    if(images.get(0).getId() == imagesKilitleAc.get(i).getId())
                        continue;
                    else
                        imagesKilitleAc.get(i).setClickable(true);
                }
            }
        }
    }
}
