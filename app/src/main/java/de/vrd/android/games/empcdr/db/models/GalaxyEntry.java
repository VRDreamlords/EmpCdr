package de.vrd.android.games.empcdr.db.models;

/**
 * Created by Spellsinger007 on 22.03.2015.
 */
public class GalaxyEntry
{
	private int id;
	private int cellSize;
	private int width, height;


	public GalaxyEntry () {}


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
	public int getCellSize ()
	{
		return this.cellSize;
	}


	/**
	 *
	 * @param cellSize
	 */
	public void setCellSize (int cellSize)
	{
		this.cellSize = cellSize;
	}


	/**
	 *
	 * @return
	 */
	public int getWidth ()
	{
		return this.width;
	}


	/**
	 *
	 * @param width
	 */
	public void setWidth (int width)
	{
		this.width = width;
	}


	/**
	 *
	 * @return
	 */
	public int getHeight()
	{
		return this.height;
	}


	/**
	 *
	 * @param height
	 */
	public void setHeight (int height)
	{
		this.height = height;
	}
}
