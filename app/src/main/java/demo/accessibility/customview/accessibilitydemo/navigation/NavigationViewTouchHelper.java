package demo.accessibility.customview.accessibilitydemo.navigation;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.widget.ExploreByTouchHelper;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;

import java.util.List;

/**
 * Created by vijay on 3/26/17.
 */

public class NavigationViewTouchHelper extends ExploreByTouchHelper {

    private final int[] NAV_VIEW = new int[2];
    private int viewFromXY;
    private View forView;


    /**
     * Factory method to create a new {@link ExploreByTouchHelper}.
     *
     * @param forView View whose logical children are exposed by this helper.
     */
    public NavigationViewTouchHelper(View forView) {
        super(forView);
        this.forView = forView;
        for (int i = 0; i < NAV_VIEW.length; i++) {
            NAV_VIEW[i] = i;
        }
    }

    @Override
    protected int getVirtualViewAt(float x, float y) {
        return getViewFromXY(x, y);
    }

    @Override
    protected void getVisibleVirtualViews(List<Integer> virtualViewIds) {
        for (int i = 0; i < NAV_VIEW.length; i++) {
            virtualViewIds.add(NAV_VIEW[i]);
        }
    }

    @Override
    protected void onPopulateEventForVirtualView(int virtualViewId, AccessibilityEvent event) {
        event.setContentDescription(getContentDescription(virtualViewId));
    }

    private CharSequence getContentDescription(int virtualViewId) {
        return "Page " + virtualViewId;
    }

    @Override
    protected void onPopulateNodeForVirtualView(int virtualViewId, AccessibilityNodeInfoCompat node) {
        node.setContentDescription(getContentDescription(virtualViewId));
        Rect rect = getRectForVirtualId(virtualViewId);
        node.setBoundsInParent(rect);
        node.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK);
    }

    private Rect getRectForVirtualId(int virtualViewId) {
        float viewWidth = forView.getMeasuredWidth() / NAV_VIEW.length;
        Rect rect = new Rect();
        rect.top = 0;
        rect.bottom = forView.getMeasuredHeight();
        rect.left = (int) viewWidth * virtualViewId;
        rect.right = rect.left + (int) viewWidth;
        return rect;
    }

    @Override
    protected boolean onPerformActionForVirtualView(int virtualViewId, int action, Bundle arguments) {
        switch (action) {
            case AccessibilityNodeInfoCompat.ACTION_CLICK:
                sendEventForVirtualView(virtualViewId, AccessibilityEvent.TYPE_VIEW_CLICKED);
                return true;
        }
        return false;
    }

    private int getViewFromXY(float x, float y) {
        float viewWidth = forView.getMeasuredWidth() / NAV_VIEW.length;
        return NAV_VIEW[(int) (x / viewWidth)];
    }
}
