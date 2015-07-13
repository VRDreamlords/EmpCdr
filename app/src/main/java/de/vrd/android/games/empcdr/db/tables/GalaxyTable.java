package de.vrd.android.games.empcdr.db.tables;

import android.content.ContentValues;

import de.vrd.android.games.empcdr.db.Database;
import de.vrd.android.games.empcdr.db.models.GalaxyEntry;

/**
 * Created by Spellsinger007 on 22.03.2015.
 */
public final class GalaxyTable
{
	public static String KEY_ID = "id";
	public static String KEY_CELL_SIZE = "cell_size";
	public static String KEY_WIDTH = "width";
	public static String KEY_HEIGHT = "height";
	public static String KEY_DENSITY = "density";
	public static String KEY_SIZE = "size";
	public static String KEY_FINISHED = "finished";
	public static String KEY_MODIFIED = "modified";


	private GalaxyTable ()
	{
	}


	/**
	 * @return
	 */
	public static String createTable ()
	{
		StringBuilder sb = new StringBuilder ();

		sb.append ("CREATE TABLE IF NOT EXISTS " + Database.GALAXY_TABLE + "(");
		sb.append (KEY_ID + " INTEGER PRIMARY KEY,");
		sb.append (KEY_CELL_SIZE + " INTEGER,");
		sb.append (KEY_WIDTH + " INTEGER,");
		sb.append (KEY_HEIGHT + " INTEGER,");
		sb.append (KEY_DENSITY + " INTEGER,");
		sb.append (KEY_SIZE + " INTEGER,");
		sb.append (KEY_FINISHED + " BOOL,");
		sb.append (KEY_MODIFIED + " TEXT");
		sb.append (");");

		return new String (sb);
	}


	public static String[] getColumnNames ()
	{
		return new String[]{
			KEY_ID,
			KEY_CELL_SIZE,
			KEY_WIDTH,
			KEY_HEIGHT,
			KEY_DENSITY,
			KEY_SIZE,
			KEY_FINISHED,
			KEY_MODIFIED
		};
	}


	public static ContentValues getValues (GalaxyEntry entry)
	{
		ContentValues values = new ContentValues ();
		values.put (KEY_ID, entry.getId ());
		values.put (KEY_CELL_SIZE, entry.getCellSize ());
		values.put (KEY_WIDTH, entry.getWidth ());
		values.put (KEY_HEIGHT, entry.getHeight ());
		values.put (KEY_DENSITY, entry.getDensity ());
		values.put (KEY_SIZE, entry.getSize ());
		values.put (KEY_FINISHED, entry.isFinished ());
		values.put (KEY_MODIFIED, entry.getModified ());

		return values;
	}
}