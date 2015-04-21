package de.vrd.android.games.empcdr.gamefield;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import de.vrd.android.games.empcdr.db.models.GalaxyEntry;
import de.vrd.android.games.empcdr.support.VRDPoint2DFloat;
import de.vrd.android.games.empcdr.support.VRDPoint2DInt;
import de.vrd.android.games.empcdr.support.VRDRectInt;
import de.vrd.android.games.empcdr.util.Container;

/**
 * Created by Spellsinger007 on 22.03.2015.
 */
public class GalaxyLayout
	extends ViewGroup
{
	private GalaxyEntry galaxy = Container.getInstance ().getDBHandler ().getGalaxy (0);
	private int cellSize = galaxy.getCellSize ();

	private VRDPoint2DFloat oldPos = new VRDPoint2DFloat ();


	/**
	 * Constructor
	 *
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public GalaxyLayout (Context context, AttributeSet attrs, int defStyle)
	{
		super (context, attrs, defStyle);
		this.init (context);
	} // constructor


	/**
	 * Constructor
	 *
	 * @param context
	 * @param attrs
	 */
	public GalaxyLayout (Context context, AttributeSet attrs)
	{
		super (context, attrs);
		this.init (context);
	} // constructor


	/**
	 * Constructor
	 *
	 * @param context
	 */
	public GalaxyLayout (Context context)
	{
		this (context, null);
	} // constructor


	/**
	 * Initializations here
	 *
	 * @param context
	 */
	private void init (Context context)
	{
	} // initView()


	/**
	 * @return
	 */
	public boolean finish ()
	{
		boolean done = true;
		/* TODO */
		return done;
	}


	/**
	 * Inherited method
	 *
	 * @param changed
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 */
	@Override
	protected void onLayout (boolean changed, int left, int top, int right, int bottom)
	{
		final int childs = this.getChildCount ();

		VRDRectInt childRect = new VRDRectInt ();
		VRDRectInt currentRect = new VRDRectInt ();

		childRect.left = this.getPaddingLeft ();
		childRect.top = this.getPaddingTop ();
		childRect.right = this.getMeasuredWidth () - this.getPaddingRight ();
		childRect.bottom = this.getMeasuredHeight () - this.getPaddingBottom ();
		childRect.width = childRect.right - childRect.left;
		childRect.height = childRect.bottom - childRect.top;

		currentRect.top = childRect.top;
		currentRect.left = childRect.left;

		/* for each child */
		for (int i = 0; i < childs; i++)
		{
			final GalaxyCell child = (GalaxyCell)this.getChildAt (i);

			if (child.getVisibility () != this.GONE)
			{
				child.measure (
					MeasureSpec.makeMeasureSpec (childRect.width, MeasureSpec.AT_MOST),
					MeasureSpec.makeMeasureSpec (childRect.height, MeasureSpec.AT_MOST));

				// final LayoutParams layoutParams = child.getLayoutParams ();

				currentRect.left = child.getCol () * this.cellSize;
				currentRect.top = child.getRow () * this.cellSize;
				currentRect.right = currentRect.left + this.cellSize;
				currentRect.bottom = currentRect.top + this.cellSize;

				child.layout (currentRect.left, currentRect.top, currentRect.right, currentRect.bottom);
			}
		}
	}


	/**
	 *
	 * @param widthMeasureSpec
	 * @param heightMeasureSpec
	 */
	@Override
	protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec)
	{
		super.onMeasure (widthMeasureSpec, heightMeasureSpec);
//		Log.i (this.getClass ().getSimpleName (), "onMeasure()");

		final int childs = this.getChildCount ();
		final int maxWidth = this.cellSize * galaxy.getWidth ();
		final int maxHeight =  this.cellSize * galaxy.getHeight ();

		int childState = 0;

		for (int i = 0; i < childs; i++)
		{
			final View child = this.getChildAt (i);

			if (child.getVisibility () != this.GONE)
			{
				childState = combineMeasuredStates (childState, child.getMeasuredState ());
			}
		}

		setMeasuredDimension (
			resolveSizeAndState (maxWidth, widthMeasureSpec, childState),
			resolveSizeAndState (maxHeight, heightMeasureSpec, childState << MEASURED_HEIGHT_STATE_SHIFT));
	}


	/**
	 * @param event
	 *
	 * @return
	 */
	@Override
	public boolean onTouchEvent (MotionEvent event)
	{
		this.performClick ();

		Rect rect = new Rect ();
		this.getDrawingRect (rect);

		VRDPoint2DFloat delta = new VRDPoint2DFloat ();
		VRDPoint2DInt temp = new VRDPoint2DInt ();

		switch (event.getAction ())
		{
			case MotionEvent.ACTION_DOWN:
				oldPos.x = event.getX ();
				oldPos.y = event.getY ();
				break;

			case MotionEvent.ACTION_UP:
				break;

			case MotionEvent.ACTION_MOVE:
				delta.x = oldPos.x - event.getX ();
				delta.y = oldPos.y - event.getY ();

				if ((rect.left + delta.x) < 0)
				{
					temp.x = 0;
				}
				else
				{
					temp.x = (int)(rect.left + delta.x);
				}

				if ((rect.top + delta.y) < 0)
				{
					temp.y = 0;
				}
				else
				{
					temp.y = (int)(rect.top + delta.y);
				}

				this.scrollTo (temp.x, temp.y);

				oldPos.x = event.getX ();
				oldPos.y = event.getY ();
				break;

			default:
				break;
		}

		return true;
	}


	/**
	 * @return
	 */
	@Override
	public boolean performClick ()
	{
		return super.performClick ();
	}
}
