package de.vrd.android.games.empcdr.support;

/**
 * Created by Spellsinger007 on 04.04.2015.
 */
public class VRDPoint3DInt
{
	public int x;
	public int y;
	public int z;

	public VRDPoint3DInt (){}

	public VRDPoint3DInt (int x, int y, int z)
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
				if (obj instanceof VRDPoint3DInt)
				{
					VRDPoint3DInt p = (VRDPoint3DInt) obj;
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
