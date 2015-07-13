package de.vrd.android.games.empcdr.db.tables;

import android.content.ContentValues;

import de.vrd.android.games.empcdr.db.Database;
import de.vrd.android.games.empcdr.db.models.GalaxyEntry;
import de.vrd.android.games.empcdr.db.models.GalaxyStarSystemsEntry;

/**
 * Created by Spellsinger007 on 08.07.2015.
 */
public class GalaxyStarSystemsLookup
{
	public static String GALAXY_KEY_ID = "galaxy_id";
	public static String STAR_SYSTEM_KEY_ID = "star_system_id";


	private GalaxyStarSystemsLookup ()
	{
	}


	/**
	 * @return
	 */
	public static String createTable ()
	{
		StringBuilder sb = new StringBuilder ();

		sb.append ("CREATE TABLE IF NOT EXISTS " + Database.GALAXY_STAR_SYSTEMS_LOOKUP + "(");
		sb.append (GALAXY_KEY_ID + " INTEGER NOT NULL REFERENCES " + Database.GALAXY_TABLE + "(" + GalaxyTable.KEY_ID + ") DEFERRABLE INITIALLY DEFERRED,");
		sb.append (STAR_SYSTEM_KEY_ID + " INTEGER NUT NULL REFERENCES " + Database.STAR_SYSTEMS_TABLE + "(" + StarSystemsTable.KEY_ID + ") DEFERRABLE INITIALLY DEFERRED,");
		sb.append ("PRIMARY KEY (" + GALAXY_KEY_ID + ", " + STAR_SYSTEM_KEY_ID + ")");
		sb.append (");");

		return new String (sb);
	}


	public static String[] getColumnNames ()
	{
		return new String[]{
			GALAXY_KEY_ID,
			STAR_SYSTEM_KEY_ID
		};
	}


	public static ContentValues getValues (GalaxyStarSystemsEntry entry)
	{
		ContentValues values = new ContentValues ();
		values.put (GALAXY_KEY_ID, entry.getGalaxyID ());
		values.put (STAR_SYSTEM_KEY_ID, entry.getStarSystemsID ());

		return values;
	}
}
