package com.pentavalue.tomato.ui.custom;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pentavalue.tomato.R;

/**
 * @author Mahmoud Turki
 */
public class DividerItemDecoration extends RecyclerView.ItemDecoration {
    private View spacer;
    private int resourceID;

    public DividerItemDecoration(int resourceID) {
        spacer = null; // layout holder so we only have to inflate it once
        this.resourceID = resourceID;
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);

        if (spacer == null && resourceID == 0) { // If layout is not already inflated
            // Inflate layout using recyclerview as the parent
            spacer = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_spacer, parent, false);
            fixLayoutSize(spacer, parent);
        } else if (spacer == null && resourceID != 0) {
            spacer = LayoutInflater.from(parent.getContext()).inflate(resourceID, parent, false);
            fixLayoutSize(spacer, parent);
        }

        // go through all the recycler views parents
        for (int i = 0; i < parent.getChildCount(); i++) {
            // get the current recycler view child
            View recyclerViewItem = parent.getChildAt(i);

            c.save(); // save our canvas

            // translate to just above the current view (with some padding 1dp top + bottom)
            c.translate(0, recyclerViewItem.getTop() - 2 - spacer.getHeight());

            // draw our space on the current canvas
            spacer.draw(c);

            c.restore(); // restore our canvas
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State
            state) {
        super.getItemOffsets(outRect, view, parent, state);

        // get the position in the list of the current view
        int position = parent.getChildAdapterPosition(view);
        // If the position does not exist, or is the first position or our space is null
        //  Do Nothing!
        if (position == RecyclerView.NO_POSITION || position == 0 || spacer == null) {
            return;
        }

        // Otherwise calculate the top offset for the view (so that it will be moved down)
        // And add it to the output rectangle
        outRect.top = 2 + spacer.getHeight(); // Don't forget our padding
    }

    protected void fixLayoutSize(View view, ViewGroup parent) {
        // Check if the view has a layout parameter and if it does not create one for it
        if (view.getLayoutParams() == null) {
            view.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        // Create a width and height spec using the parent as an example:
        // For width we make sure that the item matches exactly what it measures from the parent.
        //  IE if layout says to match_parent it will be exactly parent.getWidth()
        int widthSpec = View.MeasureSpec.makeMeasureSpec(parent.getWidth(), View.MeasureSpec.EXACTLY);
        // For the height we are going to create a spec that says it doesn't really care what is calculated,
        //  even if its larger than the screen
        int heightSpec = View.MeasureSpec.makeMeasureSpec(parent.getHeight(), View.MeasureSpec.UNSPECIFIED);

        // Get the child specs using the parent spec and the padding the parent has
        int childWidth = ViewGroup.getChildMeasureSpec(widthSpec,
                parent.getPaddingLeft() + parent.getPaddingRight(), view.getLayoutParams().width);
        int childHeight = ViewGroup.getChildMeasureSpec(heightSpec,
                parent.getPaddingTop() + parent.getPaddingBottom(), view.getLayoutParams().height);

        // Finally we measure the sizes with the actual view which does margin and padding changes to the sizes calculated
        view.measure(childWidth, childHeight);

        // And now we setup the layout for the view to ensure it has the correct sizes.
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
    }
}