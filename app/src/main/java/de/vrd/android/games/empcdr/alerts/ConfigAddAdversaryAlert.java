package de.vrd.android.games.empcdr.alerts;

import android.app.AlertDialog;
import android.content.Context;

/**
 * Created by Spellsinger007 on 18.04.2015.
 */
public class ConfigAddAdversaryAlert
	extends AlertDialog
{
	/* TODO: implement ConfigAddAdversaryActivity as Alert */
	public ConfigAddAdversaryAlert (Context context)
	{
		super (context);
	}


	public ConfigAddAdversaryAlert (Context context, int theme)
	{
		super (context, theme);
	}


	public ConfigAddAdversaryAlert (Context context, boolean cancelable, OnCancelListener cancelListener)
	{
		super (context, cancelable, cancelListener);
	}
}
