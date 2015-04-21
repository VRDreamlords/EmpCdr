package de.vrd.android.games.empcdr.db.tables;

import android.content.ContentValues;

import de.vrd.android.games.empcdr.db.Database;
import de.vrd.android.games.empcdr.db.models.PlayersEntry;

/**
 * Created by Spellsinger007 on 22.03.2015.
 */
public final class PlayersTable
{
	public static String KEY_ID = "id";
	public static String KEY_NAME = "name";
	public static String KEY_TYPE = "type";


	private PlayersTable () {}


	/**
	 * @return
	 */
	public static String createTable ()
	{
		StringBuilder sb = new StringBuilder ();

		sb.append ("CREATE TABLE IF NOT EXISTS " + Database.PLAYERS_TABLE + "(");
		sb.append (KEY_ID + " INTEGER PRIMARY KEY,");
		sb.append (KEY_NAME + " TEXT,");
		sb.append (KEY_TYPE + " INTEGER");
		sb.append (");");

		return new String (sb);
	}


	/**
	 *
	 * @return
	 */
	public static String[] getColumnNames ()
	{
		return new String[]{
			KEY_ID,
			KEY_NAME,
			KEY_TYPE
		};
	}


	/**
	 * @param entry
	 *
	 * @return
	 */
	public static ContentValues getValues (PlayersEntry entry)
	{
		ContentValues values = new ContentValues ();
		values.put (KEY_ID, entry.getId ());
		values.put (KEY_NAME, entry.getName ());
		values.put (KEY_TYPE, entry.getType ());

		return values;
	}
}
