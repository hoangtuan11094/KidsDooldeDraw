package com.hoangtuan.kidsdoodledraw.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.content.ContentValues.TAG;

public class DoodleCanvas extends View {
    private Canvas mCanvas;
    private MyPath mPath;
    private Path mPathConner;
    private Path mPathRect;



    private MyPath mPathChecked = new MyPath(); //Path when checked

    private Context context;
    private Paint mPaint; //Paint drawing


    private Paint _paintSimple;
    private Paint _paintBlur;

    private List<RectF> listRectF; //List circle

    private float mX, mY;
    private ArrayList<MyPath> paths = new ArrayList<MyPath>();
    private ArrayList<MyPath> undonePaths = new ArrayList<MyPath>();


    private int paintBG1 = 0xFF000000;


    public DoodleCanvas(Context c) {
        super(c);
        init(c);

    }

    public DoodleCanvas(Context c, AttributeSet attrs) {
        super(c, attrs);
        init(c);
    }

    private void init(Context c) {
        context = c;

        mPath = new MyPath();
        mPathConner = new Path();
        mPathRect = new Path();

        _paintSimple = new Paint();
        _paintSimple.setAntiAlias(true);
        _paintSimple.setDither(true);
        _paintSimple.setColor(Color.argb(248, 255, 255, 255));
        _paintSimple.setStrokeWidth(5f);
        _paintSimple.setStyle(Paint.Style.STROKE);
        _paintSimple.setStrokeJoin(Paint.Join.ROUND);
        _paintSimple.setStrokeCap(Paint.Cap.ROUND);

        _paintBlur = new Paint();
        _paintBlur.set(_paintSimple);
        _paintBlur.setColor(Color.argb(150, 74, 138, 255));
        _paintBlur.setStrokeWidth(10f);
        _paintBlur.setMaskFilter(new BlurMaskFilter(20, BlurMaskFilter.Blur.NORMAL));


    }




    // override onSizeChanged
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        super.onSizeChanged(w, h, oldw, oldh);
        Log.d(TAG, "onSizeChanged: ");

        mCanvas = new Canvas(Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888));

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        try {
            if (context == null) return;
            @SuppressLint("DrawAllocation") DisplayMetrics displayMetrics = new DisplayMetrics();
            ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    int strocker = 0;



    // override onDraw
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        canvas.drawColor(paintBG1);

        //canvas.drawBitmap(mBitmap, 0, 0, null);
        int w = getWidth();
        int h = getHeight();


        // draw the mPath with the mPaint on the canvas when onDraw
        for (MyPath p : paths) {
            canvas.drawPath(p, _paintBlur);
            canvas.drawPath(p, _paintSimple);

        }


        //canvas.drawBitmap(mBitmap,new Matrix(),null);
//        strocker = (w * 6) / 640;


        Log.d(TAG, "onDraw: " + strocker);
        canvas.drawPath(mPath, _paintBlur);
        canvas.drawPath(mPath, _paintSimple);


        canvas.restore();
    }



    //override the onTouchEvent
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:


                startTouch(x, y);
                invalidate();

                //

                break;

            case MotionEvent.ACTION_MOVE:
                moveTouch(x, y);
                invalidate();
                break;

            case MotionEvent.ACTION_UP:
                upTouch();
                invalidate();
                break;
        }

        return true;


    }

    // when ACTION_MOVE move touch according to the x,y values
    private void startTouch(float x, float y) {
        undonePaths.clear();

        mPath.moveTo(x, y);

        mX = x;
        mY = y;
    }

    private void moveTouch(float x, float y) {


        float dx = Math.abs(x - mX);

        float dy = Math.abs(y - mY);

        mPath.quadTo(mX, mY, (x), (y));
        mX = x;
        mY = y;
        // commit the path to our offscreen
        mCanvas.drawPath(mPath, _paintBlur);
        mCanvas.drawPath(mPath, _paintSimple);


    }

    private void upTouch() {
        paths.add(mPath);
        mPath = new MyPath();
        _paintBlur.setColor(Color.argb(238,new Random().nextInt(254),new Random().nextInt(254),new Random().nextInt(254)));
    }





    public void onClickUndo() {
        Log.d(TAG, "onClickUndo: " + paths.size());
        if (paths.size() > 0) {
            undonePaths.add(paths.remove(paths.size() - 1));
            invalidate();
        } else {
            //Util.Imageview_undo_redum_Status=false;
        }
        //toast the user
    }

    public void onClickRedo() {
        Log.d(TAG, "onClickRedo: " + undonePaths.size());
        if (undonePaths.size() > 0) {
            paths.add(undonePaths.remove(undonePaths.size() - 1));
            invalidate();
        } else {
            // Util.Imageview_undo_redum_Status=false;
        }
        //toast the user
    }



    private void remove(int index) {
        paths.remove(index);
        invalidate();
    }

}
