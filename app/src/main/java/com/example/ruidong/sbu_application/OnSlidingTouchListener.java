package com.example.ruidong.sbu_application;

/**
 * Created by Ruidong on 5/27/2015.
 */
import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class OnSlidingTouchListener implements OnTouchListener {

    private final GestureDetector gestureDetector;

    public OnSlidingTouchListener(Context context){
        gestureDetector = new GestureDetector(context, new GestureListener());
    }


    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    private final class GestureListener extends SimpleOnGestureListener {

        private final String TAG = GestureListener.class.getSimpleName();

        private static final int SLIDE_THRESHOLD = 100;

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            try {
                float deltaY = e2.getY() - e1.getY();
                float deltaX = e2.getX() - e1.getX();

                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    if (Math.abs(deltaX) > SLIDE_THRESHOLD) {
                        if (deltaX > 0) {
                            // the user made a sliding right gesture
                            return onSlideRight();
                        } else {
                            // the user made a sliding left gesture
                            return onSlideLeft();
                        }
                    }
                } else {
                    if (Math.abs(deltaY) > SLIDE_THRESHOLD) {
                        if (deltaY > 0) {
                            // the user made a sliding down gesture
                            return onSlideDown();
                        } else {
                            // the user made a sliding up gesture
                            return onSlideUp();
                        }
                    }
                }
            } catch (Exception exception) {
                Log.e(TAG, exception.getMessage());
            }

            return false;
        }
    }

    public boolean onSlideRight() {
        return false;
    }

    public boolean onSlideLeft() {
        return false;
    }

    public boolean onSlideUp() {
        return false;
    }

    public boolean onSlideDown() {
        return false;
    }
}