package de.vrd.android.games.empcdr.db.tables;

import android.content.ContentValues;

import de.vrd.android.games.empcdr.db.Database;
import de.vrd.android.games.empcdr.db.models.PlanetsEntry;

/**
 * Created by Spellsinger007 on 22.03.2015.
 */
public final class PlanetsTable
{
	public static String KEY_ID = "id";
	public static String KEY_POS_X = "pos_x";
	public static String KEY_POS_Y = "pos_y";
	public static String KEY_BUILDING_SLOTS = "building_slots";
	public static String KEY_PLAYER_ID_REF = "player_id_ref";


	private PlanetsTable () {}


	/**
	 * creating the planets table
	 *
	 * @return the create table statement
	 */
	public static String createTable ()
	{
		StringBuilder sb = new StringBuilder ();

		sb.append ("CREATE TABLE IF NOT EXISTS " + Database.PLANETS_TABLE + "(");
		sb.append (PlanetsTable.KEY_ID + " INTEGER PRIMARY KEY,");
		sb.append (PlanetsTable.KEY_POS_X + " INTEGER,");
		sb.append (PlanetsTable.KEY_POS_Y + " INTEGER,");
		sb.append (PlanetsTable.KEY_BUILDING_SLOTS + " INTEGER,");
		sb.append (PlanetsTable.KEY_PLAYER_ID_REF + " INTEGER,");
		sb.append ("FOREIGN KEY (" + PlanetsTable.KEY_PLAYER_ID_REF + ") REFERENCES " + Database.PLAYERS_TABLE + " (" + PlayersTable.KEY_ID + ")");
		sb.append (");");

		return new String (sb);
	}

	public static String[] getColumnNames ()
	{
		return new String[]{
			KEY_ID,
			KEY_POS_X,
			KEY_POS_Y,
			KEY_BUILDING_SLOTS,
			KEY_PLAYER_ID_REF
		};
	}

		/**
		 *
		 * @param entry
		 * @return
		 */
	public static ContentValues getValues (PlanetsEntry entry)
	{
		ContentValues values = new ContentValues ();
		values.put (PlanetsTable.KEY_ID, entry.getId ());
		values.put (PlanetsTable.KEY_POS_X, entry.getPosX ());
		values.put (PlanetsTable.KEY_POS_Y, entry.getPosY ());
		values.put (PlanetsTable.KEY_BUILDING_SLOTS, entry.getBuildingSlots ());
		values.put (PlanetsTable.KEY_PLAYER_ID_REF, entry.getPlayerIDRef ());

		return values;
	}
}
