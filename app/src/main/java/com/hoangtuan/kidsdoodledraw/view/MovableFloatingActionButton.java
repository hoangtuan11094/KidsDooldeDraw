package com.hoangtuan.kidsdoodledraw.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hoangtuan.kidsdoodledraw.R;


@SuppressLint("AppCompatCustomView")
public class MovableFloatingActionButton extends FrameLayout implements View.OnTouchListener, View.OnClickListener {

    private final static float CLICK_DRAG_TOLERANCE = 10; // Often, there will be a slight, unintentional, drag when the user taps the FAB, so we need to account for this.

    private float downRawX, downRawY;
    private float dX, dY;


    public MovableFloatingActionButton(Context context) {
        super(context);
        init();
    }

    public MovableFloatingActionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MovableFloatingActionButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        int action = motionEvent.getAction();
        if (action == MotionEvent.ACTION_DOWN) {

            downRawX = motionEvent.getRawX();
            downRawY = motionEvent.getRawY();
            dX = getX() - downRawX;
            dY = getY() - downRawY;
        } else if (action == MotionEvent.ACTION_MOVE) {

            int viewWidth = getWidth();
            int viewHeight = getHeight();

            View viewParent = (View) getParent();
            int parentWidth = viewParent.getWidth();
            int parentHeight = viewParent.getHeight();

            float newX = motionEvent.getRawX() + dX;
            newX = Math.max(0, newX); // Don't allow the FAB past the left hand side of the parent
            newX = Math.min(parentWidth - viewWidth,
                    newX); // Don't allow the FAB past the right hand side of the parent

            float newY = motionEvent.getRawY() + dY;
            newY = Math.max(0, newY); // Don't allow the FAB past the top of the parent
            newY = Math.min(parentHeight - viewHeight,
                    newY); // Don't allow the FAB past the bottom of the parent
//TESY
            this.animate()
                    .x(newX)
                    .y(newY)
                    .setDuration(0)
                    .start();

            return true; // Consumed

        } else if (action == MotionEvent.ACTION_UP) {

            float upRawX = motionEvent.getRawX();
            float upRawY = motionEvent.getRawY();

            float upDX = upRawX - downRawX;
            float upDY = upRawY - downRawY;

            if (Math.abs(upDX) < CLICK_DRAG_TOLERANCE
                    && Math.abs(upDY) < CLICK_DRAG_TOLERANCE) { // A click
                //return performClick();
                return true;
            } else { // A drag
                return true; // Consumed
            }

        } else {
            return super.onTouchEvent(motionEvent);
        }
        return true; // Consumed

    }


    ImageView imgView,move;
    LinearLayout layoutExtent;
    View v1,v2,v3,v4;
    boolean isShow;
    private void init() {
        View view = inflate(getContext(), R.layout.dialog_options,null);
        addView(view);
        v1 = view.findViewById(R.id.v1);
        v2 = view.findViewById(R.id.v2);
        v3 = view.findViewById(R.id.v3);
        v4 = view.findViewById(R.id.v4);
        //
        v1.setOnClickListener(this);
        v2.setOnClickListener(this);
        v3.setOnClickListener(this);
        v4.setOnClickListener(this);
        imgView = view.findViewById(R.id.imgView);
        move = view.findViewById(R.id.move);
        layoutExtent = view.findViewById(R.id.layoutExtent);
        imgView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                isShow = !isShow;
                if (isShow){
                    layoutExtent.setVisibility(VISIBLE);
                }else {
                    layoutExtent.setVisibility(GONE);
                }
            }
        });
        move.setOnTouchListener(this);
    }

    public float getPosX() {
        return dX;
    }

    public float getPosY() {
        return dY;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.v1:
                onClickedChangeColorListener.changeColor(getResources().getColor(R.color.colorOP1));
                break;
            case R.id.v2:
                onClickedChangeColorListener.changeColor(getResources().getColor(R.color.colorOP2));
                break;
            case R.id.v3:
                onClickedChangeColorListener.changeColor(getResources().getColor(R.color.colorOP3));
                break;
            case R.id.v4:
                onClickedChangeColorListener.changeColor(getResources().getColor(R.color.colorOP4));
                break;


        }
    }

    public void setOnClickedChangeColorListener(OnClickedChangeColorListener onClickedChangeColorListener) {
        this.onClickedChangeColorListener = onClickedChangeColorListener;
    }

    OnClickedChangeColorListener onClickedChangeColorListener;
    public interface OnClickedChangeColorListener{
        void changeColor(int color);
    }

    public void changeType(int type){
        if (type == 0 || type == 1){
            imgView.setVisibility(VISIBLE);
        }else {
            imgView.setVisibility(GONE);
        }
    }
}
