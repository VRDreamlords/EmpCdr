package de.vrd.android.games.empcdr.gamefield;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import de.vrd.android.games.empcdr.support.VRDPoint2DInt;
import de.vrd.android.games.empcdr.util.Container;

/**
 * Created by Spellsinger007 on 25.03.2015.
 */
public class GalaxyCell
	extends View
	implements View.OnTouchListener
{
	private final String TAG = this.getClass ().getSimpleName ();

	private boolean empty = true;
	private boolean selected = false;

	private int cellSize = 205;

	private VRDPoint2DInt position = new VRDPoint2DInt (); /* x=row; y=col */
	private String rowIndex = new String ("000");
	private String colIndex = new String ("000");

	private int planetRef;

	private final Paint paint = new Paint ();


	/**
	 * constructor
	 * @param context
	 */
	public GalaxyCell (Context context)
	{
		this (context, null);
	}


	/**
	 * constructor
	 * @param context
	 * @param attrs
	 */
	public GalaxyCell (Context context, AttributeSet attrs)
	{
		super (context, attrs);
//		Log.i (TAG, "constructor");
		this.init ();
	}


	/**
	 * constructor
	 * @param context
	 * @param attrs
	 * @param defStyleAttr
	 */
	public GalaxyCell (Context context, AttributeSet attrs, int defStyleAttr)
	{
		super (context, attrs, defStyleAttr);
		this.init ();
	}

	/**
	 *
	 */
	private void init ()
	{
		this.planetRef = -1;
		this.empty = true;
		this.setOnTouchListener (this);
	}


	/**
	 *
	 * @param canvas
	 */
	@Override
	public void draw (Canvas canvas)
	{
		super.draw (canvas);
//		Log.i (TAG, "draw()");
		float size = (this.cellSize) - 1;

		paint.setColor (Color.DKGRAY);
		// line from (0,0) to (0,h)
		canvas.drawLine (0, 0, 0, size, paint);
		// line from (0,h) to (w,h)
		canvas.drawLine (0, size, size, size, paint);
		// line from (w,h) to (w,0)
		canvas.drawLine (size, size, size, 0, paint);
		// line from (w,0) to (0,0)
		canvas.drawLine (size, 0, 0, 0, paint);

		// the coordinates
		paint.setColor (Color.WHITE);
		canvas.drawText (this.rowIndex + " " + this.colIndex, 2, 12, paint);

		if (this.planetRef >= 0)
		{
			if (Container.getInstance ().getGalaxy ().getPlanet (this.position).getPlayerIDRef () == 0) // is part of empire
			{
				paint.setColor (Color.GREEN);
			}
			else
			{
				paint.setColor (Color.LTGRAY);
			}

			// (pos.x, pos.y, diameter, color)
			canvas.drawCircle (size/2, size/2, size/4, paint);
		}
	}


	/**
	 *
	 * @return
	 */
	public  int getCol ()
	{
		return this.position.y;
	}


	/**
	 *
	 * @param col
	 */
	public void setCol (int col)
	{
		this.position.y = col;
	}


	/**
	 *
	 * @return
	 */
	public  int getRow ()
	{
		return this.position.x;
	}


	/**
	 *
	 * @param row
	 */
	public void setRow (int row)
	{
		this.position.x = row;
	}

	/**
	 *
	 * @param v
	 * @param event
	 * @return
	 */
	@Override
	public boolean onTouch (View v, MotionEvent event)
	{
		v.performClick ();
		return false;
	}


	/**
	 *
	 * @param event
	 * @return
	 */
	@Override
	public boolean onTouchEvent (MotionEvent event)
	{
		this.performClick ();

		switch (event.getAction ())
		{
			case MotionEvent.ACTION_DOWN:
				if (this.planetRef >= 0)
				{
					this.selected = !this.selected;
					this.setBackgroundColor (Color.BLUE);
				/* TODO : open Planet Definition Activity (or Alert?) */
					Toast.makeText (Container.getInstance ().getContext (), "Planet selected", Toast.LENGTH_SHORT).show ();
					return true;
				} // if
				return false;

			case MotionEvent.ACTION_UP:
				if (selected)
				{
					this.selected = !this.selected;
					this.setBackgroundColor (Color.TRANSPARENT);
				} // if
				return false;

			default:
				return super.onTouchEvent (event);
		} // switch()
	} // onTouch()


	/**
	 *
	 * @return
	 */
	@Override
	public boolean performClick ()
	{
		return super.performClick ();
	} // performClick()


	public boolean isEmpty ()
	{
		return this.empty;
	} // isEmpty()


	public void setEmpty (boolean value)
	{
		this.empty = value;
	} // setEmpty()


	public void setCellSize (int cellSize)
	{
		if (cellSize > 0)
		{
			this.cellSize = cellSize;
		}
	}


	/**
	 * This position will be set in GalaxyLayout.java - it is the position within the grid.
	 * 
	 * @param row
	 * @param col
	 */
	public void setIndex (int row, int col)
	{
		StringBuilder sb;

		this.setRow (row);
		this.setCol (col);

		if (row > 0)
		{
			sb = new StringBuilder ();
			if (row < 100)
			{
				sb.append ("0");
			}
			if (row < 10)
			{
				sb.append ("0");
			}
			sb.append (row);

			rowIndex = new String (sb);
		}

		if (col > 0)
		{
			sb = new StringBuilder ();
			if (col < 100)
			{
				sb.append ("0");
			}
			if (col < 10)
			{
				sb.append ("0");
			}
			sb.append (col);

			colIndex = new String (sb);
//			Log.i (TAG, "index("+rowIndex +", "+colIndex +")");
		}
	}

	public void setPlanetRef (int id)
	{
		this.planetRef = id;
	}


/*	public void addStarSystem (Planet planet)
	{
		if (planet != null)
		{
			this.planet = planet;
		}
	}*/
}
