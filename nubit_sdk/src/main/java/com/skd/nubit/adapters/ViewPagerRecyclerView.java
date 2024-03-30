package com.skd.nubit.adapters;


import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

public class ViewPagerRecyclerView extends RecyclerView {

    public ViewPagerRecyclerView(@NonNull Context context) {
        super(context);
        init();
    }

    public ViewPagerRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ViewPagerRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setLayoutManager(new ViewPagerLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    public boolean fling(int velocityX, int velocityY) {
        ViewPagerLayoutManager layoutManager = (ViewPagerLayoutManager) getLayoutManager();
        if (layoutManager == null) return super.fling(velocityX, velocityY);

        int targetPosition = layoutManager.findTargetPosition(velocityX);
        if (targetPosition == RecyclerView.NO_POSITION) return super.fling(velocityX, velocityY);

        smoothScrollToPosition(targetPosition);
        return true;
    }

    private static class ViewPagerLayoutManager extends LinearLayoutManager {
        ViewPagerLayoutManager(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);
        }

        @Override
        public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
            LinearSmoothScroller smoothScroller = new LinearSmoothScroller(recyclerView.getContext()) {
                @Override
                protected int getHorizontalSnapPreference() {
                    return SNAP_TO_START;
                }
            };
            smoothScroller.setTargetPosition(position);
            startSmoothScroll(smoothScroller);
        }

        int findTargetPosition(int velocityX) {
            if (velocityX > 0) {
                return findFirstVisibleItemPosition() + 1;
            } else if (velocityX < 0) {
                return findFirstVisibleItemPosition();
            }
            return RecyclerView.NO_POSITION;
        }
    }
}

