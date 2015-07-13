package de.vrd.android.games.empcdr.handlers;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import de.vrd.android.games.empcdr.db.Database;
import de.vrd.android.games.empcdr.db.models.GalaxyEntry;
import de.vrd.android.games.empcdr.db.models.PlayerEntry;
import de.vrd.android.games.empcdr.db.models.StarSystemEntry;
import de.vrd.android.games.empcdr.db.tables.GalaxyStarSystemsLookup;
import de.vrd.android.games.empcdr.db.tables.GalaxyTable;
import de.vrd.android.games.empcdr.db.tables.PlayersTable;
import de.vrd.android.games.empcdr.db.tables.StarSystemsTable;
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
		db.execSQL (StarSystemsTable.createTable ());
		db.execSQL (GalaxyStarSystemsLookup.createTable ());

/*		db.insert (
			Database.PLAYERS_TABLE,
			null,
			PlayersTable.getValues (
				new PlayerEntry (
					0,
					Container.getInstance ().getContext ().getString (R.string.default_player),
					0) // 0=human
			)
		);*/
	}


	/**
	 * @param db
	 * @param oldVersion
	 * @param newVersion
	 */
	@Override
	public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion)
	{
		db.execSQL ("DROP TABLE IF EXISTS " + Database.GALAXY_STAR_SYSTEMS_LOOKUP);
		db.execSQL ("DROP TABLE IF EXISTS " + Database.STAR_SYSTEMS_TABLE);
		db.execSQL ("DROP TABLE IF EXISTS " + Database.GALAXY_TABLE);
		db.execSQL ("DROP TABLE IF EXISTS " + Database.PLAYERS_TABLE);

		this.onCreate (db);
//		Log.i (TAG, "onUpdate");
	}


	/**
	 * Get the count of entries of the given table
	 *
	 * @param table the specific table
	 *
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
	public void addStarSystem (StarSystemEntry entry)
	{
		SQLiteDatabase db = this.getWritableDatabase ();
		db.insert (Database.STAR_SYSTEMS_TABLE, null, StarSystemsTable.getValues (entry));
		db.close ();
	}


	/**
	 * @param id
	 *
	 * @return
	 */
	public StarSystemEntry getStarSystem (int id)
	{
		StarSystemEntry entry = new StarSystemEntry ();
		SQLiteDatabase db = this.getReadableDatabase ();
		Cursor cursor = db.query (
			Database.STAR_SYSTEMS_TABLE,
			StarSystemsTable.getColumnNames (),
			StarSystemsTable.KEY_ID + " = ?",
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
	public List<StarSystemEntry> getStarSystems ()
	{
		List<StarSystemEntry> list = new ArrayList<StarSystemEntry> ();
		SQLiteDatabase db = this.getReadableDatabase ();
		Cursor cursor = db.rawQuery ("SELECT * FROM " + Database.STAR_SYSTEMS_TABLE, null);

		if (cursor.moveToFirst ())
		{
			do
			{
				StarSystemEntry entry = new StarSystemEntry ();
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
	public int updateStarSystem (StarSystemEntry entry)
	{
		SQLiteDatabase db = this.getWritableDatabase ();
		int returnValue = db.update (
			Database.STAR_SYSTEMS_TABLE,
			StarSystemsTable.getValues (entry),
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
	public int deleteStarSystem (StarSystemEntry entry)
	{
		SQLiteDatabase db = this.getWritableDatabase ();
		int returnValue = db.delete (
			Database.STAR_SYSTEMS_TABLE,
			PlayersTable.KEY_ID + " = ?",
			new String[]{String.valueOf (entry.getId ())}
		);

		return returnValue;
	}


	/**
	 * cleaning Star Systems table
	 */
	public void resetStarSystems ()
	{
		SQLiteDatabase db = this.getWritableDatabase ();
		db.execSQL ("Delete * from " + Database.STAR_SYSTEMS_TABLE);
		db.close ();
	}


	/**
	 * @param entry
	 */
	public void addPlayer (PlayerEntry entry)
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
	 *
	 * @param id the player's ID
	 *
	 * @return the player info
	 */
	public PlayerEntry getPlayer (int id)
	{
		PlayerEntry entry = new PlayerEntry ();
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
		entry.setMusic (cursor.getInt (3) != 0);
		entry.setSound (cursor.getInt (4) != 0);
		entry.setModified (cursor.getString (5));

		cursor.close ();
		db.close ();

		return entry;
	}


	/**
	 * @return
	 */
	public List<PlayerEntry> getAdversaries ()
	{
		List<PlayerEntry> list = new ArrayList<PlayerEntry> ();
		SQLiteDatabase db = this.getReadableDatabase ();
		Cursor cursor = db.rawQuery ("SELECT * FROM " + Database.PLAYERS_TABLE + " WHERE " + PlayersTable.KEY_ID + " > 0", null);

		if (cursor.moveToFirst ())
		{
			do
			{
				PlayerEntry entry = new PlayerEntry ();
				entry.setId (cursor.getInt (0));
				entry.setName (cursor.getString (1));
				entry.setType (cursor.getInt (2));
				entry.setModified (cursor.getString (5));
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
	public List<PlayerEntry> getPlayers ()
	{
		List<PlayerEntry> list = new ArrayList<PlayerEntry> ();
		SQLiteDatabase db = this.getReadableDatabase ();
		Cursor cursor = db.rawQuery ("SELECT * FROM " + Database.PLAYERS_TABLE, null);

		if (cursor.moveToFirst ())
		{
			do
			{
				PlayerEntry entry = new PlayerEntry ();
				entry.setId (cursor.getInt (0));
				entry.setName (cursor.getString (1));
				entry.setType (cursor.getInt (2));
				entry.setModified (cursor.getString (5));
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
	public int updatePlayer (PlayerEntry entry)
	{
		SQLiteDatabase db = this.getWritableDatabase ();
		entry.update ();
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
	public int deletePlayer (PlayerEntry entry)
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


	public int updateGalaxy (GalaxyEntry entry)
	{
		SQLiteDatabase db = this.getWritableDatabase ();
		entry.update ();
		int returnValue = db.update (
			Database.GALAXY_TABLE,
			GalaxyTable.getValues (entry),
			GalaxyTable.KEY_ID + " = ?",
			new String[]{String.valueOf (entry.getId ())}
		);

		db.close ();

		return returnValue;
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
		entry.setDensity (cursor.getInt (4));
		entry.setSize (cursor.getInt (5));
		entry.setFinished (cursor.getInt (6) != 0);
		entry.setModified (cursor.getString (7));

		cursor.close ();
		db.close ();

		return entry;
	}




	/**
	 * @return
	 */
	public List<GalaxyEntry> getGalaxies ()
	{
		List<GalaxyEntry> list = new ArrayList<GalaxyEntry> ();
		SQLiteDatabase db = this.getReadableDatabase ();
		Cursor cursor = db.rawQuery ("SELECT * FROM " + Database.GALAXY_TABLE, null);

		if (cursor.moveToFirst ())
		{
			do
			{
				GalaxyEntry entry = new GalaxyEntry ();
				entry.setCellSize (cursor.getInt (1));
				entry.setWidth (cursor.getInt (2));
				entry.setHeight (cursor.getInt (3));
				entry.setDensity (cursor.getInt (4));
				entry.setSize (cursor.getInt (5));
				entry.setFinished (cursor.getInt (6) != 0);
				entry.setModified (cursor.getString (7));
				list.add (entry);
			}
			while (cursor.moveToNext ());
		}

		cursor.close ();
		db.close ();

		return list;
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
