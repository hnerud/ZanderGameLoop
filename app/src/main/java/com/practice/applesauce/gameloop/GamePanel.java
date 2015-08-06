package com.practice.applesauce.gameloop;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by heidinerud on 8/6/15.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{

    public static final int WIDTH = 856;
    public static final int HEIGHT = 480;
    private MainThread thread;
    private Background bg;

    public GamePanel(Context context) {
        super(context);


        //add callback to SurHOl
        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);

        //make gamePanel focusable to rue
        setFocusable(true);
    }

    @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }
    @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);


                thread.join();


            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            retry = false;

        }
    }
    @Override
        public void surfaceCreated(SurfaceHolder holder) {

      bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.hillbg1));
        bg.setVector(-5);

      //safe start
        thread.setRunning(true);
        thread.start();
    }
    @Override
        public boolean onTouchEvent(MotionEvent event){
        return super.onTouchEvent(event);

    }
    public void update() {
    bg.update();
    }
    @Override
    public void draw(Canvas canvas) {

        final float scaleFactorX = getWidth()/(float)WIDTH;
        final float scaleFactorY = getHeight()/(float)HEIGHT;

        if (canvas!=null) {
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX,scaleFactorY);
            bg.draw(canvas);
            canvas.restoreToCount(savedState);
        }
    }


}