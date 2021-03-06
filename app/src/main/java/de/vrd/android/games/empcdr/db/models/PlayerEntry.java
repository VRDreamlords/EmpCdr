package de.vrd.android.games.empcdr.db.models;

import android.widget.Toast;

import java.util.Date;

import de.vrd.android.games.empcdr.support.VRDDateTime;
import de.vrd.android.games.empcdr.util.Container;

/**
 * Created by Spellsinger007 on 22.03.2015.
 */
public class PlayerEntry
{
	private int id;
	private String name;
	private int type; // 0:human ; 1:defensive comp ; 2:neutral comp ; 3:aggressive comp
	private boolean music = false;
	private boolean sound = false;
	private Date modified = null;


	/**
	 * Empty Constructor
	 */
	public PlayerEntry ()
	{
		this.update ();
	}


	/**
	 * Constructor
	 *
	 * @param id
	 * @param name
	 */
	public PlayerEntry (int id, String name, int type)
	{
		this.setId (id);
		this.setName (name);
		this.setType (type);
		this.setMusic (true);
		this.setSound (true);
		this.update ();
	}

	public void update ()
	{
		this.setModified (VRDDateTime.getActualDate ());
	}


	/**
	 * Getting the ID of an entry
	 *
	 * @return the desired ID
	 */
	public int getId ()
	{
		return id;
	}


	/**
	 * Setting the ID of an entry
	 *
	 * @param id the new ID
	 */
	public void setId (int id)
	{
		this.id = id;
	}


	/**
	 * Getting the name of an entry
	 *
	 * @return the desired name
	 */
	public String getName ()
	{
		return this.name;
	}


	/**
	 * Setting a new name
	 *
	 * @param name the new name
	 */
	public void setName (String name)
	{
		this.name = name;
	}


	/**
	 * Getting the type of an entry
	 *
	 * @return the desired name
	 */
	public int getType ()
	{
		return this.type;
	}


	/**
	 * Setting the type of the player (device/human)
	 *
	 * @param type the type
	 */
	public void setType (int type)
	{
		this.type = type;
	}


	/**
	 * Getting the desired value of the background music
	 *
	 * @return true if the player wants background music, false otherwise
	 */
	public boolean hasMusic ()
	{
		return this.music;
	}


	/**
	 * Set, if the player wants background music or not
	 *
	 * @param music if background music is desired or not
	 */
	public void setMusic (boolean music)
	{
		this.music = music;
	}


	/**
	 * Getting the desired value of the sound effects
	 *
	 * @return true if the player wants sound effects, false otherwise
	 */
	public boolean hasSound ()
	{
		return this.sound;
	}


	/**
	 * Set, if the player wants sound effects or not
	 *
	 * @param sound if sound effects are desired or not
	 */
	public void setSound (boolean sound)
	{
		this.sound = sound;
	}


	public String getModified ()
	{
		return VRDDateTime.sdf.format (this.modified);
	}


	public void setModified (Date date)
	{
		this.modified = new Date (date.getTime ());
	}


	public void setModified (String datetime)
	{
		try
		{
			this.modified = VRDDateTime.sdf.parse (datetime);
		}
		catch (Exception ex)
		{
			ex.printStackTrace ();
		}
	}
}
