package com.example.poly_technic.university;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
 
public class Splash extends Activity{   
        private final int SPLASH_DISPLAY_LENGHT = 1500; //—”≥Ÿ1.5√Î     
            
        @Override   
        public void onCreate(Bundle savedInstanceState) {    
            super.onCreate(savedInstanceState);    
            setContentView(R.layout.splash);    
            new Handler().postDelayed(new Runnable(){    
          
             public void run() {    
            	 Intent mainIntent = new Intent(Splash.this,navigation.class);    
                 Splash.this.startActivity(mainIntent);    
                     Splash.this.finish();    
             }    
                    
            }, SPLASH_DISPLAY_LENGHT);    
        }    
        public boolean onKeyDown(int keyCode, KeyEvent event) {
            if(keyCode == KeyEvent.KEYCODE_BACK){
                return false;
            }
            return false;
        }
} 
