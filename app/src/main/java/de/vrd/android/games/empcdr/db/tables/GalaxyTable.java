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
		sb.append (KEY_HEIGHT + " INTEGER");
		sb.append (");");

		return new String (sb);
	}

	public static String[] getColumnNames ()
	{
		return new String[]{
			KEY_ID,
			KEY_CELL_SIZE,
			KEY_WIDTH,
			KEY_HEIGHT
		};
	}

	public static ContentValues getValues (GalaxyEntry entry)
	{
		ContentValues values = new ContentValues ();
		values.put (KEY_ID, entry.getId ());
		values.put (KEY_CELL_SIZE, entry.getCellSize ());
		values.put (KEY_WIDTH, entry.getWidth ());
		values.put (KEY_HEIGHT, entry.getHeight ());

		return values;
	}
}