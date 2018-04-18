package com.example.markrogalski.homeaway;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.support.v7.widget.AppCompatImageView;


public class CircularImageView extends AppCompatImageView {

    private float padding = 0.0f;
    private BitmapShader bitmapShader;
    private Paint bitmapPaint;
    private boolean clipToCircle = true;
    final private RectF oval = new RectF();

    public CircularImageView(Context context) {
        super(context);
        padding = getPaddingTop(); // padding is the same for all sides
    }

    public CircularImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        padding = getPaddingTop(); // padding is the same for all sides
    }

    public CircularImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        padding = getPaddingTop(); // padding is the same for all sides
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        clipToCircle = true;
        clearScaledBitmap();
        super.setImageBitmap(bm);
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        clipToCircle = true;
        clearScaledBitmap();
        super.setImageDrawable(drawable);
    }

    @Override
    public void setImageResource(int resId) {
        clipToCircle = false;
        clearScaledBitmap();
        super.setImageResource(resId);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!clipToCircle) {
            super.onDraw(canvas);
            return;
        }

        if (bitmapShader == null) {
            BitmapDrawable mDrawable = (BitmapDrawable) getDrawable();
            if (mDrawable == null) return;
            Bitmap bitmap = mDrawable.getBitmap();
            if (bitmap == null) return;
            float size = getWidth() - padding;
            Matrix matrix = new Matrix();
            int min = Math.min(bitmap.getWidth(), bitmap.getHeight());  //use to make sure we maintain aspect ratio
            matrix.setScale(size/min, size/min);
            bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            bitmapShader.setLocalMatrix(matrix);
        }

        if (bitmapPaint == null) {
            bitmapPaint = new Paint();
            bitmapPaint.setAntiAlias(true);
        }
        bitmapPaint.setShader(bitmapShader);

        int borderWidth = 0;
        oval.set(padding+borderWidth, padding+borderWidth, getWidth()-padding-borderWidth, getWidth()-padding-borderWidth);

        canvas.drawOval(oval, bitmapPaint);
    }

    private void clearScaledBitmap() {
        bitmapShader = null;
    }
}