package de.vrd.android.games.empcdr.db.models;

/**
 * Created by Spellsinger007 on 22.03.2015.
 */
public class PlayersEntry
{
	private int id;
	private String name;
	private int type; // 0:human ; 1:defensive comp ; 2:neutral comp ; 3:aggressive comp


	/**
	 * Empty Constructor
	 */
	public PlayersEntry () {}


	/**
	 * Constructor
	 * @param id
	 * @param name
	 */
	public PlayersEntry (int id, String name, int type)
	{
		this.setId (id);
		this.setName (name);
		this.setType (type);
	}


	/**
	 * Getting the ID of an entry
	 * @return the desired ID
	 */
	public int getId ()
	{
		return id;
	}


	/**
	 * Setting the ID of an entry
	 * @param id the new ID
	 */
	public void setId (int id)
	{
		this.id = id;
	}


	/**
	 * Getting the name of an entry
	 * @return the desired name
	 */
	public String getName ()
	{
		return this.name;
	}


	/**
	 * Setting a new name
	 * @param name the new name
	 */
	public void setName (String name)
	{
		this.name = name;
	}


	/**
	 * Getting the type of an entry
	 * @return the desired name
	 */
	public int getType ()
	{
		return this.type;
	}


	/**
	 * Setting the type of the player (device/human)
	 * @param type the type
	 */
	public void setType (int type)
	{
		this.type = type;
	}
}
