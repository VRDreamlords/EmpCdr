package de.vrd.android.games.empcdr.util;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.util.DisplayMetrics;

import de.vrd.android.games.empcdr.gamefield.Galaxy;
import de.vrd.android.games.empcdr.handlers.DatabaseHandler;

/**
 * Created by Spellsinger007 on 22.03.2015.
 */
public class Container
{
	private static Container instance = new Container ();

	private Application application;
	private Context context;
	private DatabaseHandler dbHandler;
	private DisplayMetrics displayMetrics;
	private Galaxy galaxy;
	private Typeface typeface;


	private Container (){}

	public static Container getInstance ()
	{
		return instance;
	}

	public Application getApplication ()
	{
		return this.application;
	}

	public void setApplication (Application application)
	{
		this.application = application;
	}

	public Context getContext ()
	{
		return this.context;
	}

	public void setContext (Context context)
	{
		this.context = context;
	}

	public DatabaseHandler getDBHandler ()
	{
		return this.dbHandler;
	}
	public void initDBHandler ()
	{
		this.dbHandler = new DatabaseHandler ();
	}

	public DisplayMetrics getDisplayMetrics()
	{
		return this.displayMetrics;
	}
	public void initDisplayMetrics()
	{
		this.displayMetrics= new DisplayMetrics ();
	}

	public Galaxy getGalaxy ()
	{
		return this.galaxy;
	}
	public void initGalaxy ()
	{
		this.galaxy = new Galaxy ();
	}

	/**
	 * @return the typeface
	 */
	public Typeface getTypeface ()
	{
		return this.typeface;
	}


	/**
	 * @param typeface
	 *            the typeface to set
	 */
	public void setTypeface (Typeface typeface)
	{
		this.typeface = typeface;
	}
}
