package de.vrd.android.games.empcdr.support;

/**
 * Created by Spellsinger007 on 04.04.2015.
 */
public class VRDPoint2DFloat
{
	public float x;
	public float y;

	public VRDPoint2DFloat (){}

	public VRDPoint2DFloat (float x, float y)
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
				if (obj instanceof VRDPoint2DFloat)
				{
					VRDPoint2DFloat p = (VRDPoint2DFloat) obj;
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
