package de.vrd.android.games.empcdr;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;

import de.vrd.android.games.empcdr.util.Container;


/**
 * Created by Spellsinger007 on 22.03.2015.
 */
public class SplashScreenActivity
	extends Activity
{
	private final long showSplash = 1000; // ms

	private static Container container = Container.getInstance ();

	@Override
	protected void onCreate (Bundle savedInstanceState)
	{
		super.onCreate (savedInstanceState);
		setContentView (R.layout.activity_splash);

		container.setApplication (getApplication ());
		container.setContext (getApplicationContext ());
		container.setTypeface (Typeface.createFromAsset (getAssets (), "fonts/Roboto-Light.ttf"));
		container.initDBHandler ();
		container.initDisplayMetrics ();

		new Handler ().postDelayed (new Runnable ()
		{
			@Override
			public void run ()
			{
				startActivity (new Intent (SplashScreenActivity.this, MainActivity.class));

				finish ();
			} // run()
		}, showSplash);
	}
}
