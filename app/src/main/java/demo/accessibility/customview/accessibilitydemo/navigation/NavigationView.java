package demo.accessibility.customview.accessibilitydemo.navigation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class NavigationView extends View {

    @NonNull private final Point[] points = new Point[2];
    @NonNull Paint mPaint;
    @Nullable OnNavigationViewClickListener listener;
    @NonNull NavigationViewTouchHelper navigationViewTouchHelper;

    public NavigationView(Context context) {
        super(context);
    }

    public NavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLACK);
        setFocusable(true);
        setClickable(true);
        setImportantForAccessibility(IMPORTANT_FOR_ACCESSIBILITY_YES);
        navigationViewTouchHelper = new NavigationViewTouchHelper(this);
        ViewCompat.setAccessibilityDelegate(this, navigationViewTouchHelper);
    }

    public void setNavigationViewClickListener(OnNavigationViewClickListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        setUpCenters();
    }

    private void setUpCenters() {
        float left = getLeft();
        float top = getTop();
        Point point1 = new Point(getMeasuredWidth() / 4, getMeasuredHeight() / 2);
        points[0] = point1;
        Point point2 = new Point(getMeasuredWidth() * 3 / 4, getMeasuredHeight() / 2);
        points[1] = point2;
    }

    @Override
    protected boolean dispatchHoverEvent(MotionEvent event) {
        if (navigationViewTouchHelper.dispatchHoverEvent(event)) {
            return true;
        }
        return super.dispatchHoverEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < points.length; i++) {
            canvas.drawCircle(points[i].x, points[i].y, getMeasuredHeight() / 3, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (event.getX() < getMeasuredWidth() / 2) {
                listener.navigationClicked(0);
            } else {
                listener.navigationClicked(1);
            }
        }
        return true;
    }

    public interface OnNavigationViewClickListener {
        void navigationClicked(int option);
    }

    private class Point {
        float x;
        float y;

        public Point(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }
}
