package de.vrd.android.games.empcdr.handlers;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import de.vrd.android.games.empcdr.R;
import de.vrd.android.games.empcdr.db.Database;
import de.vrd.android.games.empcdr.db.models.PlanetsEntry;
import de.vrd.android.games.empcdr.db.models.PlayersEntry;
import de.vrd.android.games.empcdr.db.models.GalaxyEntry;
import de.vrd.android.games.empcdr.db.tables.PlanetsTable;
import de.vrd.android.games.empcdr.db.tables.PlayersTable;
import de.vrd.android.games.empcdr.db.tables.GalaxyTable;
import de.vrd.android.games.empcdr.util.Container;

/**
 * Created by Spellsinger007 on 24.03.2015.
 */
public class DatabaseHandler
	extends SQLiteOpenHelper
{
	/**
	 *
	 */
	public DatabaseHandler ()
	{
		super (
			Container.getInstance ().getContext (),
			Database.DATABASE,
			null,
			Database.DATABASE_VERSION);

/*		super (
			container.getContext (),
			container.getContext ().getString (R.string.db_name),
			null,
			Integer.parseInt (container.getContext ().getString (R.string.db_version)));*/
/*		Log.i (TAG, "constructor");*/
	}


	/**
	 * @param db
	 */
	@Override
	public void onCreate (SQLiteDatabase db)
	{
//		Log.i (TAG, "onCreate");
		db.execSQL (PlayersTable.createTable ());
		db.execSQL (GalaxyTable.createTable ());
		db.execSQL (PlanetsTable.createTable ());
		db.insert (
			Database.PLAYERS_TABLE,
			null,
			PlayersTable.getValues (
				new PlayersEntry (
					0,
					Container.getInstance ().getContext ().getString (R.string.default_player),
					0) // 0=human
			)
		);
	}


	/**
	 * @param db
	 * @param oldVersion
	 * @param newVersion
	 */
	@Override
	public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion)
	{
		db.execSQL ("DROP TABLE IF EXISTS " + Database.PLANETS_TABLE);
		db.execSQL ("DROP TABLE IF EXISTS " + Database.GALAXY_TABLE);
		db.execSQL ("DROP TABLE IF EXISTS " + Database.PLAYERS_TABLE);

		this.onCreate (db);
//		Log.i (TAG, "onUpdate");
	}


	/**
	 * Get the count of entries of the given table
	 *
	 * @param table the specific table
	 * @return the number of entries
	 */
	public int getItemCount (String table)
	{
		SQLiteDatabase db = this.getReadableDatabase ();
		Cursor cursor = db.rawQuery ("SELECT * FROM " + table, null);
		int count = cursor.getCount ();
		cursor.close ();
		db.close ();

		return count;
	}


	public int getMaxID (String table, String attribute)
	{
		int maxID = -1;
		SQLiteDatabase db = this.getReadableDatabase ();
		Cursor cursor = db.rawQuery ("SELECT MAX (" + attribute + ") FROM " + table, null);

		if (cursor.moveToFirst ())
		{
			maxID = cursor.getInt (0);
		}

		cursor.close ();
		db.close ();

//		Log.i (this.getClass ().getSimpleName (), "maxID = " + maxID);
		return maxID;
	}


	public String simpleResult (String table, String attribute)
	{
		StringBuilder sb = new StringBuilder ();
		SQLiteDatabase db = this.getReadableDatabase ();
		Cursor cursor = db.rawQuery ("SELECT " + attribute + " FROM " + table, null);

		while (cursor.moveToNext ())
		{
			sb.append (cursor.getString (0));
			sb.append ("\n");
		}

		cursor.close ();
		db.close ();

		return new String (sb);
	}


	/**
	 * @param entry
	 */
	public void addPlanet (PlanetsEntry entry)
	{
		SQLiteDatabase db = this.getWritableDatabase ();
		db.insert (Database.PLANETS_TABLE, null, PlanetsTable.getValues (entry));
		db.close ();
	}


	/**
	 * @param id
	 *
	 * @return
	 */
	public PlanetsEntry getPlanet (int id)
	{
		PlanetsEntry entry = new PlanetsEntry ();
		SQLiteDatabase db = this.getReadableDatabase ();
		Cursor cursor = db.query (
			Database.PLANETS_TABLE,
			PlanetsTable.getColumnNames (),
			PlanetsTable.KEY_ID + " = ?",
			new String[]{String.valueOf (id)},
			null, null, null, null
		);

		if (cursor != null)
		{
			cursor.moveToFirst ();
		}

		entry.setId (cursor.getInt (0));
		entry.setPosX (cursor.getInt (1));
		entry.setPosY (cursor.getInt (2));
		entry.setBuildingSlots (cursor.getInt (3));
		entry.setPlayerIDRef (cursor.getInt (4));

		cursor.close ();
		db.close ();

		return entry;
	}


	/**
	 * @return
	 */
	public List<PlanetsEntry> getPlanets ()
	{
		List<PlanetsEntry> list = new ArrayList<PlanetsEntry> ();
		SQLiteDatabase db = this.getReadableDatabase ();
		Cursor cursor = db.rawQuery ("SELECT * FROM " + Database.PLANETS_TABLE, null);

		if (cursor.moveToFirst ())
		{
			do
			{
				PlanetsEntry entry = new PlanetsEntry ();
				entry.setId (cursor.getInt (0));
				entry.setPosX (cursor.getInt (1));
				entry.setPosY (cursor.getInt (2));
				entry.setBuildingSlots (cursor.getInt (3));
				entry.setPlayerIDRef (cursor.getInt (4));
				list.add (entry);
			}
			while (cursor.moveToNext ());
		}

		cursor.close ();
		db.close ();

		return list;
	}


	/**
	 * @param entry
	 *
	 * @return
	 */
	public int updatePlanet (PlanetsEntry entry)
	{
		SQLiteDatabase db = this.getWritableDatabase ();
		int returnValue = db.update (
			Database.PLANETS_TABLE,
			PlanetsTable.getValues (entry),
			PlayersTable.KEY_ID + " = ?",
			new String[]{String.valueOf (entry.getId ())}
		);

		db.close ();

		return returnValue;
	}


	/**
	 * @param entry
	 *
	 * @return
	 */
	public int deletePlanet (PlanetsEntry entry)
	{
		SQLiteDatabase db = this.getWritableDatabase ();
		int returnValue = db.delete (
			Database.PLANETS_TABLE,
			PlayersTable.KEY_ID + " = ?",
			new String[]{String.valueOf (entry.getId ())}
		);

		return returnValue;
	}


	/**
	 * @param entry
	 */
	public void addPlayer (PlayersEntry entry)
	{
		SQLiteDatabase db = this.getWritableDatabase ();
		db.insert (
			Database.PLAYERS_TABLE,
			null,
			PlayersTable.getValues (entry)
		);
		db.close ();
	}


	/**
	 * Getting one single player by ID
	 * @param id the player's ID
	 * @return the player info
	 */
	public PlayersEntry getPlayer (int id)
	{
		PlayersEntry entry = new PlayersEntry ();
		SQLiteDatabase db = this.getReadableDatabase ();
		Cursor cursor = db.query (
			Database.PLAYERS_TABLE,
			PlayersTable.getColumnNames (),
			PlayersTable.KEY_ID + " = ?",
			new String[]{String.valueOf (id)},
			null, null, null, null
		);

		if (cursor != null)
		{
			cursor.moveToFirst ();
		}

		entry.setId (cursor.getInt (0));
		entry.setName (cursor.getString (1));
		entry.setType (cursor.getInt (2));

		cursor.close ();
		db.close ();

		return entry;
	}


	/**
	 * @return
	 */
	public List<PlayersEntry> getAdversaries ()
	{
		List<PlayersEntry> list = new ArrayList<PlayersEntry> ();
		SQLiteDatabase db = this.getReadableDatabase ();
		Cursor cursor = db.rawQuery ("SELECT * FROM " + Database.PLAYERS_TABLE + " WHERE " + PlayersTable.KEY_ID + " > 0", null);

		if (cursor.moveToFirst ())
		{
			do
			{
				PlayersEntry entry = new PlayersEntry ();
				entry.setId (cursor.getInt (0));
				entry.setName (cursor.getString (1));
				entry.setType (cursor.getInt (2));
//				Log.i (this.getClass ().getSimpleName (), "entry = " + entry.getId () + "; " + entry.getName () + "; " + entry.getType ());
				list.add (entry);
			}
			while (cursor.moveToNext ());
		}

		cursor.close ();
		db.close ();

		return list;
	}


	/**
	 * @return
	 */
	public List<PlayersEntry> getPlayers ()
	{
		List<PlayersEntry> list = new ArrayList<PlayersEntry> ();
		SQLiteDatabase db = this.getReadableDatabase ();
		Cursor cursor = db.rawQuery ("SELECT * FROM " + Database.PLAYERS_TABLE, null);

		if (cursor.moveToFirst ())
		{
			do
			{
				PlayersEntry entry = new PlayersEntry ();
				entry.setId (cursor.getInt (0));
				entry.setName (cursor.getString (1));
				entry.setType (cursor.getInt (2));
				list.add (entry);
			}
			while (cursor.moveToNext ());
		}

		cursor.close ();
		db.close ();

		return list;
	}


	/**
	 * @param entry
	 *
	 * @return
	 */
	public int updatePlayer (PlayersEntry entry)
	{
		SQLiteDatabase db = this.getWritableDatabase ();
		int returnValue = db.update (
			Database.PLAYERS_TABLE,
			PlayersTable.getValues (entry),
			PlayersTable.KEY_ID + " = ?",
			new String[]{String.valueOf (entry.getId ())}
		);

		db.close ();

		return returnValue;
	}


	/**
	 * @param entry
	 *
	 * @return
	 */
	public int deletePlayer (PlayersEntry entry)
	{
		SQLiteDatabase db = this.getWritableDatabase ();
		int returnValue = db.delete (
			Database.PLAYERS_TABLE,
			PlayersTable.KEY_ID + " = ?",
			new String[]{String.valueOf (entry.getId ())}
		);

		return returnValue;
	}


	/**
	 * Initialize the galaxy (there should be only one - or saved games..?)
	 *
	 * @param entry the new playground
	 */
	public void initGalaxy (GalaxyEntry entry)
	{
		SQLiteDatabase db = this.getWritableDatabase ();
		db.insert (
			Database.GALAXY_TABLE,
			null,
			GalaxyTable.getValues (entry)
		);
		db.close ();
	}

	public GalaxyEntry getGalaxy (int id)
	{
		GalaxyEntry entry = new GalaxyEntry ();
		SQLiteDatabase db = this.getReadableDatabase ();
		Cursor cursor = db.query (
			Database.GALAXY_TABLE,
			GalaxyTable.getColumnNames (),
			GalaxyTable.KEY_ID + " = ?",
			new String[]{String.valueOf (id)},
			null, null, null, null
		);

		if (cursor != null)
		{
			cursor.moveToFirst ();
		}

		entry.setId (cursor.getInt (0));
		entry.setCellSize (cursor.getInt (1));
		entry.setWidth (cursor.getInt (2));
		entry.setHeight (cursor.getInt (3));

		cursor.close ();
		db.close ();

		return entry;
	}

	/**
	 * Delete the galaxy that matches the given data
	 *
	 * @param entry the new playground
	 */
	public int resetGalaxy (GalaxyEntry entry)
	{
		SQLiteDatabase db = this.getWritableDatabase ();
		int returnValue = db.delete (
			Database.GALAXY_TABLE,
			GalaxyTable.KEY_ID + " = ?",
			new String[]{String.valueOf (entry.getId ())}
		);

		return returnValue;
	}
}
