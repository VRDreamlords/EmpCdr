package de.vrd.android.games.empcdr.db.models;

/**
 * Created by Spellsinger007 on 08.07.2015.
 */
public class GalaxyStarSystemsEntry
{
	private int galaxy_id;
	private int star_systems_id;

	public GalaxyStarSystemsEntry (){}


	public int getGalaxyID ()
	{
		return galaxy_id;
	}


	public void setGalaxyID (int galaxy_id)
	{
		this.galaxy_id = galaxy_id;
	}


	public int getStarSystemsID ()
	{
		return star_systems_id;
	}


	public void setStarSystemsID (int star_systems_id)
	{
		this.star_systems_id = star_systems_id;
	}
}
