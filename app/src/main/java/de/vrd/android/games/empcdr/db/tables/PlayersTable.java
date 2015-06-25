package de.vrd.android.games.empcdr.db.tables;

import android.content.ContentValues;

import de.vrd.android.games.empcdr.db.Database;
import de.vrd.android.games.empcdr.db.models.PlayerEntry;

/**
 * Created by Spellsinger007 on 22.03.2015.
 */
public final class PlayersTable
{
	public static String KEY_ID = "id";
	public static String KEY_NAME = "name";
	public static String KEY_TYPE = "type";
	public static String KEY_MUSIC = "music";
	public static String KEY_SOUND = "sound";


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
		sb.append (KEY_TYPE + " INTEGER,");
		sb.append (KEY_MUSIC + " BOOL,");
		sb.append (KEY_SOUND + " BOOL");
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
			KEY_TYPE,
			KEY_MUSIC,
			KEY_SOUND
		};
	}


	/**
	 * @param entry
	 *
	 * @return
	 */
	public static ContentValues getValues (PlayerEntry entry)
	{
		ContentValues values = new ContentValues ();
		values.put (KEY_ID, entry.getId ());
		values.put (KEY_NAME, entry.getName ());
		values.put (KEY_TYPE, entry.getType ());
		values.put (KEY_MUSIC, entry.getMusic ());
		values.put (KEY_SOUND, entry.getSound ());

		return values;
	}
}
