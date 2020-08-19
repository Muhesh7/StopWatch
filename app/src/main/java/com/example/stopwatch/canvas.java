package com.example.stopwatch;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;
import java.util.List;

public class canvas extends View {

    public canvas(Context context) {
        super(context);

        mContext=context;
        init();
    }

    public canvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        init();
    }

    public canvas(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext=context;
        init();
    }

    private int[] numbers = {1,2,3,4,5,6,7,8,9,10,11,12};
    private Rect rect = new Rect();
    int fontSize=0;
        Paint filler;
        Path mPath;
        Paint brush;
        Paint mPaint;
    Paint mPaint2;
        Context mContext;
        android.graphics.Canvas mCanvas;
        Bitmap bitmap;
        List<Point> mPoints =new ArrayList<>();
        ArrayList<Bitmap> mBitmaps=new ArrayList<>();
        private int mLastPointIndex = 0;
        private int mTouchTolerance;
        private boolean isPathStarted = false;
        int height,width;
        int radius=0;

        public void init(){
            fontSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10,
                    getResources().getDisplayMetrics());
            mPath= new Path();
            brush=new Paint(Paint.ANTI_ALIAS_FLAG);
            brush.setAntiAlias(true);
            brush.setDither(true);
            brush.setColor(getResources().getColor(android.R.color.holo_blue_dark));
            brush.setStyle(Paint.Style.STROKE);
            brush.setStrokeJoin(Paint.Join.ROUND);
            brush.setStrokeCap(Paint.Cap.ROUND);
            brush.setStrokeWidth(6f);
            filler=new Paint(Paint.ANTI_ALIAS_FLAG);
            filler.setAntiAlias(true);
            filler.setDither(true);
            filler.setColor(getResources().getColor(android.R.color.secondary_text_light));
            filler.setStyle(Paint.Style.FILL);
            filler.setStrokeCap(Paint.Cap.ROUND);
            filler.setStrokeJoin(Paint.Join.ROUND);
            mPaint2=new Paint(Paint.ANTI_ALIAS_FLAG);
            mPaint2.setAntiAlias(true);
            mPaint2.setDither(true);
            mPaint2.setColor(getResources().getColor(android.R.color.holo_red_light));
            mPaint2.setStyle(Paint.Style.STROKE);
            mPaint2.setStrokeWidth(6f);
            mPaint=new Paint(Paint.LINEAR_TEXT_FLAG);
            mPaint.setStrokeWidth(12f);
            mPaint.setTextSize(30);
            mPaint.setColor(getResources().getColor(android.R.color.white));

            height=getResources().getDisplayMetrics().heightPixels;
            width=getResources().getDisplayMetrics().widthPixels;
        }
        Bitmap b;
        int innerRadius;
        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            b = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            mCanvas = new Canvas(b);
            bh=b.getHeight();
            bw=b.getWidth();
            radius=bw/2-50;
            innerRadius=bw/4-100;
            bitmap=BitmapFactory.decodeResource(getResources(),R.drawable.line);
            Bitmap.createBitmap(18,bw/2, Bitmap.Config.ARGB_8888);

        }
        Boolean draw=false;
        int bh;
        int bw;
        @Override
        protected void onDraw(Canvas canvas) {
            int h=getResources().getDisplayMetrics().heightPixels;
            int w=getResources().getDisplayMetrics().widthPixels;

            canvas.drawBitmap(b,0,0,mPaint);
             canvas.drawCircle(bw/2,bh/2,bw/2-25,brush);
            //canvas.drawCircle(bw/2,bh/2,bw/2-25-6,filler);

            canvas.drawCircle(bw/2,bh/4,bw/4-50-mPaint.getTextSize(),brush);
             int x1= (int) (bw/2-mPaint.getTextSize()/2+50);
             int y1=(int) mPaint.getTextSize()+50;
            drawSeconds(canvas);
            drawMinutes(canvas);
                canvas.drawCircle(bw / 2, bh / 2, 10, mPaint);
            canvas.drawCircle(bw / 2, bh / 4, 8, mPaint);

            canvas.drawText("Made  By  Muhesh",bw/2-4*mPaint.getTextSize(),
                    bh/2+10*mPaint.getTextSize(),mPaint);
            if(clock.equals("start"))
            {
                drawtick1(canvas,c);
                if(c==60)
                {count++;
                    c=0;
                    c2++;
                    Toast.makeText(mContext.getApplicationContext(),"Minute passed",Toast.LENGTH_SHORT).show();
                }
                if(count==0)
                {drawtick2(canvas,mModels2.size()-1);}
                else {
                    drawtick2(canvas,c2);
                }
                c++;
                postInvalidateDelayed(1000);

            }
            else if(clock.equals("reset")){
                drawtick1(canvas,mModels.size()-1);
                drawtick2(canvas,mModels2.size()-1);
                c=0;
                c2=-1;
                count=0;
            }
            else {
                drawtick1(canvas,c);
                if(c2==-1)
                {drawtick2(canvas,mModels2.size()-1);}
                else
                {drawtick2(canvas,c2);}
            }
            super.onDraw(canvas);
        }
        String clock="reset";
        int c=0;
        int c2=-1;
        int count=0;
    private void drawSeconds(Canvas canvas) {
     mPaint.setTextSize(fontSize);

        for (int i=1;i<=60;++i) {
            String tmp = String.valueOf(i);
            model m=new model();
            double angle = Math.PI / 30 * (i - 15);
            int x = (int) (bw / 2-mPaint.getTextSize()/2 + Math.cos(angle) * radius);
            int y = (int) (bh / 2+mPaint.getTextSize()/2 + Math.sin(angle) * radius);
            if(i==60)
            {m.setX((int) (x+mPaint.getTextSize()/2) );
                m.setY((int) (y+mPaint.getTextSize()/2) );}
            else if(i>14&&i<58){
                m.setX((int) (x+mPaint.getTextSize()/2));
                m.setY((int) (y));
            }
            else {
                m.setX((int) (x));
                m.setY((int) (y));
            }
           mModels.add(m);
           canvas.drawText(tmp, x, y, mPaint);
        }
    }
    private void drawMinutes(Canvas canvas) {

        for (int i=1;i<=15;++i) {
            String tmp = String.valueOf(i);
            double angle = Math.PI*2 / 15 * (i - 3.8);
            model m=new model();
            int x = (int) (bw / 2-mPaint.getTextSize()/2 + Math.cos(angle) * innerRadius);
            int y = (int) (bh / 4+mPaint.getTextSize()/2 + Math.sin(angle) * innerRadius);
            if(i==15)
            {m.setX((int) (x+mPaint.getTextSize()/2) );
            m.setY((int) (y+mPaint.getTextSize()/2) );}
            else {
                m.setX((int) (x));
                m.setY((int) (y));
            }
            mModels2.add(m);
            canvas.drawText(tmp, x, y, mPaint);
        }
    }
public void drawtick1(Canvas canvas,int a)
{ ox=bw/2;
    oy=bh/2;
    nx=mModels.get(a).getX();
    ny=mModels.get(a).getY();
    canvas.drawLine(ox,oy,nx,ny,mPaint2);
}
    public void drawtick2(Canvas canvas,int a)
    { ox2=bw/2;
        oy2=bh/4;
        nx2=mModels2.get(a).getX();
        ny2=mModels2.get(a).getY();
        canvas.drawLine(ox2,oy2,nx2,ny2,mPaint2);
    }
float ox,oy,nx,ny;
    float ox2,oy2,nx2,ny2;
  public void spin(int c)
  { Toast.makeText(mContext.getApplicationContext(),"Start",Toast.LENGTH_SHORT).show();
    clock="start";
    invalidate();
    }

  public void stop()
  {  Toast.makeText(mContext.getApplicationContext(),"Stop",Toast.LENGTH_SHORT).show();
      clock="stop";
  invalidate();

  }
  public void reset()
  {Toast.makeText(mContext.getApplicationContext(),"Reset",Toast.LENGTH_SHORT).show();
      clock="reset";
      invalidate();
  }
ArrayList<model> mModels=new ArrayList<>();
    ArrayList<model> mModels2=new ArrayList<>();
}
