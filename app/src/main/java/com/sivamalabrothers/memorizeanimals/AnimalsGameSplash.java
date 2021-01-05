
package com.sivamalabrothers.memorizeanimals;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

// import com.google.android.gms.ads.AdRequest;
      //  import com.google.android.gms.ads.AdSize;
    //    import com.google.android.gms.ads.AdView;


public class AnimalsGameSplash extends Activity implements Animation.AnimationListener{

    private TextView tv;
    private ImageView img;
    private Typeface font;
    private Animation mImgAnim;
    private Animation mTextAnim;
    Context context = this;
    String language = "English";
    SharePreferences sharePreferences;

    //private AdView adView;
    LinearLayout reklam_layout;

    private static final String REKLAM_ID = "ca-app-pub-3183404528711365/6839544594";
    private InterstitialAd interstitial;
    private static final String REKLAM_ID2 = "ca-app-pub-3183404528711365/6644665754";

    SharedPreferences preferences, ayarlar;
    MediaPlayer ses;
    Vibrator titresim;
    Boolean sesDurumu, titresimDurumu;
    RelativeLayout arkaplan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animals_game_splash);

        initViews();
        alphaAnimation(img);

        // splash efect kodu
         /*   Thread timerThread = new Thread(){
                public void run(){
                    try{
                        sleep(1200);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }finally{

                            Intent intent = new Intent(Splash.this, MainActivity.class);
                            startActivity(intent);

                    }
                }
            };
            timerThread.start();
        */
    }

    /*
    private void reklam_yukle(){

        adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(REKLAM_ID);

        reklam_layout.addView(adView);

        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        adView.loadAd(adRequest);

    }
    */

    public void initViews(){


        tv = findViewById(R.id.tv);
        img = findViewById(R.id.img);

        font = Typeface.createFromAsset(getAssets(),"fonts/Candy_Shop_Black.ttf");

        sharePreferences = new SharePreferences();
        language = sharePreferences.getString(context,"language");
        if(language.equals("") || language == null) {
            language = "English";
            sharePreferences.saveString(context, "language", language);
        }

        setGirisTextView();

        tv.setTypeface(font,Typeface.BOLD);
        arkaplan = findViewById(R.id.arkaplan);
        ayarlar = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        titresim = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        reklam_layout = findViewById(R.id.reklam_layout);
        //reklam_yukle();

    }

    private void setGirisTextView(){

        if(language.equals("English"))
            tv.setText("H A V E  F U N");
        else if(language.equals("Finnish"))
            tv.setText("P I D Ä H A U S K A A");
        else if(language.equals("German"))
            tv.setText("H A B E  S P A S S");
        else if(language.equals("Turkish"))
            tv.setText("İ Y İ  E Ğ L E N C E L E R");
        else if(language.equals("Spanish"))
            tv.setText("QUE TE DIVIERTAS");
        else if(language.equals("Italian"))
            tv.setText("DIVERTITI");

    }

    public void scaleAnimation(View view){
        mTextAnim = AnimationUtils.loadAnimation(this, R.anim.splash_anim_text);
        mTextAnim.setAnimationListener(this);
        tv.startAnimation(mTextAnim);
    }

    public void alphaAnimation(View view){
        mImgAnim = AnimationUtils.loadAnimation(this, R.anim.splash_anim_image);
        mImgAnim.setAnimationListener(this);
        img.startAnimation(mImgAnim);
    }


    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }

    @Override
    public void onAnimationStart(Animation animation) {
        if(animation.equals(mImgAnim))
            scaleAnimation(tv);
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if(animation.equals(mTextAnim)) {
            //reklam_yukle_gecis();
            if(Build.VERSION.SDK_INT>=21 ){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(AnimalsGameSplash.this);
                Intent intent = new Intent(AnimalsGameSplash.this, GirisSayfasi.class);
                startActivity(intent,options.toBundle());
            }else {
                Intent intent = new Intent(AnimalsGameSplash.this, GirisSayfasi.class);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    private void reklam_yukle_gecis(){
        interstitial = new InterstitialAd(this);
        interstitial.setAdUnitId(REKLAM_ID2);

        AdRequest adRequest = new AdRequest.Builder().build();

        interstitial.loadAd(adRequest);
        interstitial.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                if (interstitial.isLoaded()) {
                    interstitial.show();
                }
            }
        });

    }

}
