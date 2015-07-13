package de.vrd.android.games.empcdr.db.models;

import java.util.Date;

import de.vrd.android.games.empcdr.support.VRDDateTime;

/**
 * Created by Spellsinger007 on 22.03.2015.
 */
public class GalaxyEntry
{
	private int id;
	private int cellSize;
	private int width, height;
	private int density, size;
	private boolean finished;
	private Date modified = null;


	public GalaxyEntry ()
	{
		this.update ();
	}

	public void update ()
	{
		this.setModified (VRDDateTime.getActualDate ());
	}


	/**
	 * @return
	 */
	public int getId ()
	{
		return id;
	}


	/**
	 * @param id
	 */
	public void setId (int id)
	{
		this.id = id;
	}


	/**
	 * @return
	 */
	public int getCellSize ()
	{
		return this.cellSize;
	}


	/**
	 * @param cellSize
	 */
	public void setCellSize (int cellSize)
	{
		this.cellSize = cellSize;
	}


	/**
	 * @return
	 */
	public int getWidth ()
	{
		return this.width;
	}


	/**
	 * @param width
	 */
	public void setWidth (int width)
	{
		this.width = width;
	}


	/**
	 * @return
	 */
	public int getHeight ()
	{
		return this.height;
	}


	/**
	 * @param height
	 */
	public void setHeight (int height)
	{
		this.height = height;
	}


	/**
	 * @return
	 */
	public int getDensity ()
	{
		return this.density;
	}


	/**
	 * @param density
	 */
	public void setDensity (int density)
	{
		this.density = density;
	}


	/**
	 * @return
	 */
	public int getSize ()
	{
		return this.size;
	}


	/**
	 * @param size
	 */
	public void setSize (int size)
	{
		this.size = size;
	}


	/**
	 * @return
	 */
	public boolean isFinished ()
	{
		return this.finished;
	}


	/**
	 * @param finished
	 */
	public void setFinished (boolean finished)
	{
		this.finished = finished;
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
