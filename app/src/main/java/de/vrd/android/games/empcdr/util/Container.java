package de.vrd.android.games.empcdr.util;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.widget.Toast;

import java.util.List;

import de.vrd.android.games.empcdr.R;
import de.vrd.android.games.empcdr.db.Database;
import de.vrd.android.games.empcdr.db.models.GalaxyEntry;
import de.vrd.android.games.empcdr.db.models.PlayerEntry;
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

	private List<GalaxyEntry> galaxyEntries = null;
	private List<PlayerEntry> playerEntries = null;


	private Container ()
	{
	}


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


	public DisplayMetrics getDisplayMetrics ()
	{
		return this.displayMetrics;
	}


	public void initDisplayMetrics ()
	{
		this.displayMetrics = context.getResources ().getDisplayMetrics ();
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
	 * @param typeface the typeface to set
	 */
	public void setTypeface (Typeface typeface)
	{
		this.typeface = typeface;
	}


	public void initGalaxyEntries ()
	{
		if (this.dbHandler.getItemCount (Database.GALAXY_TABLE) == 0)
		{
		/* ToDo : init this galaxy entry at splash screen when no entry is available */
//			int size = 5 * Container.getInstance ().getDBHandler ().getGalaxy (0).getSize ();
//			size *= Container.getInstance ().getDBHandler ().getItemCount (Database.PLAYERS_TABLE);

			GalaxyEntry entry = new GalaxyEntry ();
			entry.setId (0);
			entry.setCellSize (displayMetrics.widthPixels/10);
			entry.setWidth (10);
			entry.setHeight (10);
			entry.setDensity (1);
			entry.setSize (1);
			entry.setFinished (true);
			entry.update ();
			this.dbHandler.initGalaxy (entry);
		}

		this.galaxyEntries = this.dbHandler.getGalaxies ();
	}


	public GalaxyEntry getGalaxyEntry (int id)
	{
		GalaxyEntry entry = null;
		try
		{
			entry = this.galaxyEntries.get (id);
		}
		catch (ArrayIndexOutOfBoundsException ex)
		{
			/* ToDo */
		}
		finally
		{
			return entry;
		}
	}


	public void initPlayerEntries ()
	{
		if (this.dbHandler.getItemCount (Database.PLAYERS_TABLE) == 0)
		{
			PlayerEntry entry = new PlayerEntry (
				0,
				this.context.getString (R.string.default_player),
				0); // human
			this.dbHandler.addPlayer (entry);
		}

		this.playerEntries = this.dbHandler.getPlayers ();
	}


	public PlayerEntry getPlayerEntry (int id)
	{
		PlayerEntry entry = null;
		try
		{
			entry = this.playerEntries.get (id);
		}
		catch (ArrayIndexOutOfBoundsException ex)
		{
			/* ToDo */
		}
		finally
		{
			return entry;
		}
	}
}
