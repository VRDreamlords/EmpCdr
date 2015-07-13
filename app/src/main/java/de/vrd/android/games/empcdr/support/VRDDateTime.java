package de.vrd.android.games.empcdr.support;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Spellsinger007 on 12.07.2015.
 */
public class VRDDateTime
{
	public final static String DATE_TIME_DB = "yyyyMMddHHmmss";
	public static final SimpleDateFormat sdf = new SimpleDateFormat (DATE_TIME_DB, Locale.getDefault ());

	private Date date = null;
	private static VRDDateTime instance = new VRDDateTime ();


	private VRDDateTime ()
	{
		this.init ();
	}


	public static VRDDateTime getInstance ()
	{
		return instance;
	}


	public void init ()
	{
		date = Calendar.getInstance ().getTime ();
	}

	public static Date getActualDate ()
	{
		return Calendar.getInstance ().getTime ();
	}
}
