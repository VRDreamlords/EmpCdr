package de.vrd.android.games.empcdr.support;

/**
 * Created by Spellsinger007 on 04.04.2015.
 */
public class VRDPoint2DInt
{
	public int x;
	public int y;

	public VRDPoint2DInt (){}

	public VRDPoint2DInt (int x, int y)
	{
		this.x = x;
		this.y = y;
	}


	@Override
	public boolean equals (Object obj)
	{
		if (obj != null)
		{
			if (this == obj)
			{
				return true;
			}
			else
			{
				if (obj instanceof VRDPoint2DInt)
				{
					VRDPoint2DInt p = (VRDPoint2DInt) obj;
					return ((p.x == this.x) && (p.y == this.y));
				}
				else
				{
					return false;
				}
			}
		}

		return super.equals (obj);
	}
}
