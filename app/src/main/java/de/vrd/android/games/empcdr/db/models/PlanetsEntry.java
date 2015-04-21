package de.vrd.android.games.empcdr.db.models;

/**
 * Created by Spellsinger007 on 22.03.2015.
 */
public class PlanetsEntry
{
	int id;
	int pos_x;
	int pos_y;
	int building_slots;
	int player_id_ref;

	/**
	 * Empty Constructor
	 */
	public PlanetsEntry () {}


	/**
	 *
	 * @return
	 */
	public int getId ()
	{
		return id;
	}


	/**
	 *
	 * @param id
	 */
	public void setId (int id)
	{
		this.id = id;
	}


	/**
	 *
	 * @return
	 */
	public int getPosX ()
	{
		return pos_x;
	}


	/**
	 *
	 * @param pos_x
	 */
	public void setPosX (int pos_x)
	{
		this.pos_x = pos_x;
	}


	/**
	 *
	 * @return
	 */
	public int getPosY ()
	{
		return pos_y;
	}


	/**
	 *
	 * @param pos_y
	 */
	public void setPosY (int pos_y)
	{
		this.pos_y = pos_y;
	}


	/**
	 *
	 * @return
	 */
	public int getBuildingSlots ()
	{
		return building_slots;
	}


	/**
	 *
	 * @param building_slots
	 */
	public void setBuildingSlots (int building_slots)
	{
		this.building_slots = building_slots;
	}


	/**
	 *
	 * @return
	 */
	public int getPlayerIDRef ()
	{
		return player_id_ref;
	}


	/**
	 *
	 * @param player_id_ref
	 */
	public void setPlayerIDRef (int player_id_ref)
	{
		this.player_id_ref = player_id_ref;
	}
}
