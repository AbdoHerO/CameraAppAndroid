package com.example.abdohero.camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.Toast;
/**
 * Created by abdohero on 11/7/17.
 */

public class Touch extends MainActivity implements OnGestureListener {
    //OnGestureListener:permet d'ecouter les évenement du touch
    GestureDetector gestureDetector;
    int c=0;
    //gestureDetector:detecteur de mouvement
    ImageView imageswiper;
    int [] images=new int[3];
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.touch_main);
        imageswiper=(ImageView)findViewById(R.id.imageswiper) ;
        super.images.get(i).getResources()
        images[0]=R.drawable.images1;
        images[1]=R.drawable.images2;
        images[2]=R.drawable.images3;
        //ecouter les evement du touch sur  l'activité GestionGesturesGli
        gestureDetector = new GestureDetector(Touch.this, Touch.this);
        if(super.images.get(0)!=null) {
            imageswiper.setImageBitmap();
        }
    }

    //onFling:retourne les position de la première touche et la deuxième
    @Override
    public boolean onFling(MotionEvent m1, MotionEvent m2, float X, float Y) {

        if (m1.getY() - m2.getY() > 50) {

            Toast.makeText(Touch.this, " up", Toast.LENGTH_LONG).show();

            return true;
        }

        if (m2.getY() - m1.getY() > 50) {
            //Toast:permet d'afficher une alerete
            //Toast.LENGTH_LONG:determine la durée :LENGTH_LONG , LENGTH_SHORT
            Toast.makeText(Touch.this, " Swipe Down ", Toast.LENGTH_LONG).show();

            return true;
        }

        if (m1.getX() - m2.getX() > 50) {

            Toast.makeText(Touch.this, " Swipe Left ", Toast.LENGTH_LONG).show();
            c--;
            if(c<0)
            {
                c=2;
            }
            imageswiper.setImageResource(images[c]);
            return true;
        }

        if (m2.getX() - m1.getX() > 50) {

            Toast.makeText(Touch.this, " Swipe Right ", Toast.LENGTH_LONG).show();
            c++;
            if(c>2)
            {
                c=0;
            }
            imageswiper.setImageResource(images[c]);
            return true;
        } else {

            return true;
        }
    }

    // s'éxcute lorsque on touche l'écran pendant une longue durée
    @Override
    public void onLongPress(MotionEvent arg0) {

        // TODO Auto-generated method stub
        Toast.makeText(Touch.this,"Long Presss",Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2, float arg3) {

        // TODO Auto-generated method stub

        return false;
    }

    @Override
    public void onShowPress(MotionEvent arg0) {

        // TODO Auto-generated method stub

    }

    @Override
    public boolean onSingleTapUp(MotionEvent arg0) {

        // TODO Auto-generated method stub

        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        // TODO Auto-generated method stub

        return gestureDetector.onTouchEvent(motionEvent);
    }

    // cette fonction s'exute lorsque on click sur l'écran
    @Override
    public boolean onDown(MotionEvent arg0) {

        // TODO Auto-generated method stub
        Toast.makeText(Touch.this,"on down function",Toast.LENGTH_SHORT).show();

        return false;
    }
}

