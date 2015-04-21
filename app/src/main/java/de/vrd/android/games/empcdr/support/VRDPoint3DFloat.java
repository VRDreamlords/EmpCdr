package de.vrd.android.games.empcdr.support;

/**
 * Created by Spellsinger007 on 04.04.2015.
 */
public class VRDPoint3DFloat
{
	public float x;
	public float y;
	public float z;

	public VRDPoint3DFloat (){}

	public VRDPoint3DFloat (float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
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
				if (obj instanceof VRDPoint3DFloat)
				{
					VRDPoint3DFloat p = (VRDPoint3DFloat) obj;
					return ((p.x == this.x) && (p.y == this.y) && (p.z == this.z));
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
