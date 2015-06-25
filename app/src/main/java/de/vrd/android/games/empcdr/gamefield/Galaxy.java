package de.vrd.android.games.empcdr.gamefield;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import de.vrd.android.games.empcdr.db.Database;
import de.vrd.android.games.empcdr.db.models.GalaxyEntry;
import de.vrd.android.games.empcdr.db.models.StarSystemEntry;
import de.vrd.android.games.empcdr.support.VRDMath;
import de.vrd.android.games.empcdr.support.VRDPoint2DInt;
import de.vrd.android.games.empcdr.util.Container;

/**
 * Created by Spellsinger007 on 25.03.2015.
 */
public class Galaxy
{
	private final int MAX_TRY = 20;
	private final int BELOW_MIN = -1;
	private final int IN_RANGE = 0;
	private final int BEYOND_MAX = 1;

	private int row, col;
	private int playerCount = Container.getInstance ().getDBHandler ().getItemCount (Database.PLAYERS_TABLE);

	private float maxDist = 0.0f;
	private float minDist = 0.0f;

	private static int tryCounter;

	private GalaxyEntry entry = null;
	private GalaxyCell[][] galaxy = null;

	private final List<StarSystemEntry> planets = new LinkedList<StarSystemEntry> ();


	/**
	 *
	 */
	public Galaxy ()
	{
//		Log.i (this.getClass ().getSimpleName (), "constructor");
		this.init ();
	}

	/**
	 *
	 */
	private void init ()
	{
//		Log.i (this.getClass ().getSimpleName (), "init");
		entry = Container.getInstance ().getDBHandler ().getGalaxy (0);

		this.galaxy = new GalaxyCell[entry.getHeight ()][entry.getWidth ()];

		for (row = entry.getHeight (); --row >= 0; )
		{
			for (col = entry.getWidth (); --col >= 0; )
			{
				this.galaxy[row][col] = new GalaxyCell (Container.getInstance ().getContext ());
				this.galaxy[row][col].setCellSize (entry.getCellSize ());
				this.galaxy[row][col].setIndex (row, col);
			}
		}

		this.setMinMaxDist (3.0f, 5.0f); /* ToDo : Take Global Values */
		this.populateUniverse ();
		this.initHomeworlds ();
	}


	public void addPlanet (int x, int y)
	{
		StarSystemEntry p = new StarSystemEntry ();
		p.setPosX (x);
		p.setPosY (y);
		p.setPlayerIDRef (-1);

		this.planets.add (p);
		this.getGalaxyCellAt (x, y).setPlanetRef (this.planets.size () - 1);
		tryCounter = 0;
	}


	/**
	 *
	 * @param r
	 * @param c
	 * @return
	 */
	public GalaxyCell getGalaxyCellAt (int r, int c)
	{
		return this.galaxy[r][c];
	}


	public StarSystemEntry getPlanet (VRDPoint2DInt pos)
	{
		for (int i = planets.size (); --i >= 0; )
		{
			if (pos.equals (new VRDPoint2DInt (planets.get (i).getPosX (), planets.get (i).getPosY ())))
			{
				return planets.get (i);
			}
		}

		return null;
	}


	public void initHomeworlds()
	{
		/* ToDo : Randomized Player IDs */
		for (int p = playerCount; --p >= 0; )
		{
			planets.get (p).setPlayerIDRef (p);
		}
	}

	private void populateUniverse ()
	{
		Random rand = new Random ();
		tryCounter = 0;

		while (tryCounter < MAX_TRY)
		{
			checkPosition (
				rand.nextInt (entry.getWidth ()),
				rand.nextInt (entry.getHeight ()));

			tryCounter++;
		}
	}


	private void checkPosition (int x, int y)
	{
		if (galaxy[x][y].isEmpty () && checkDistance (new VRDPoint2DInt (x, y)))
		{
			addPlanet (x, y);
		}
	}

	private void setMinMaxDist (float min, float max)
	{
		if (min > 0)
		{
			if (max > min)
			{
				this.minDist = min;
				this.maxDist = max;
			}
			else
			{
				this.minDist = max;
				this.maxDist = min;
			}
		}
		else
		{
			/* ToDo : DEFINE MIN-/MAX-DIST */
			this.minDist = 3.0f;
			this.maxDist = 7.0f;
		}
	}



	private boolean checkDistance (VRDPoint2DInt point)
	{
		int i, j;
		float d;

		int[] distanceList = new int[planets.size ()];

		/*
		 * initiate array
		 */
		for (i = distanceList.length; --i >= 0;)
		{
			distanceList[i] = BELOW_MIN;
		}

		/*
		 * check distances
		 */
		for (i = 0; i < distanceList.length; i++)
		{
			d = VRDMath.pDist (new VRDPoint2DInt (planets.get (i).getPosX (),planets.get (i).getPosY ()), point, 1);
			// d = Maths.euclideanDistance (planets.get (0).getPosition (), p);

			if (d < minDist)
			{
				return false;
//				distanceList[i] = BELOW_MIN;
			} // if
			else if (d > maxDist)
			{
				distanceList[i] = BEYOND_MAX;
			} // if
			else
			{
				distanceList[i] = IN_RANGE;
			}
		}

		/*
		 * return false if the new planet would be too close
		 */
/*		for (i = distanceList.length; --i >= 0;)
		{
			if (distanceList[i] == BELOW_MIN)
			{
				return false;
			}
		}
*/
		/*
		 * return true if the new planet would be in range to at least one other
		 * planet
		 */
		for (i = distanceList.length; --i >= 0;)
		{
			if (distanceList[i] == IN_RANGE)
			{
				return true;
			}
			else if (distanceList[i] == BEYOND_MAX)
			{
				for (j = distanceList.length; --j >= 0;)
				{
					if (distanceList[j] == IN_RANGE)
					{
						return true;
					}
				}
			}
		}

		return true;
	}
}
