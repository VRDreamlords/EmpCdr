package de.vrd.android.games.empcdr.support;

/**
 * Created by Spellsinger007 on 09.04.2015.
 */
public abstract class VRDMath
{
	public static float pDist (VRDPoint2DInt p1, VRDPoint2DInt p2, double p)
	{
		return VRDMath.pDist (new VRDPoint2DFloat (p1.x, p1.y), new VRDPoint2DFloat (p2.x, p2.y), p);
	}

	public static float pDist (VRDPoint2DFloat p1, VRDPoint2DFloat p2, double p)
	{
		// should not occur
		if (p < 1)
		{
			return -1.0f;
		} // if

		if (p1.equals (p2))
		{
			return 0.0f;
		}

		float dx = p1.x - p2.x;
		float dy = p1.y - p2.y;

		if (p == 1)
		{
			return Math.abs (dx) + Math.abs (dy);
		} // else
		else
		{
			return (float) Math.pow (Math.pow (dx, p) + Math.pow (dy, p), 1.0 / p);
		} // else
	}
}
