package de.vrd.android.games.empcdr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import de.vrd.android.games.empcdr.db.Database;
import de.vrd.android.games.empcdr.db.models.GalaxyEntry;
import de.vrd.android.games.empcdr.util.Container;


/**
 * Created by Spellsinger007 on 22.03.2015.
 */
public class MainActivity
	extends Activity
	implements View.OnClickListener
{
	private Button startButton;
	private Button configButton;
	private Button exitButton;

	private GalaxyEntry entry = null;

	/**
	 *
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate (Bundle savedInstanceState)
	{
		super.onCreate (savedInstanceState);
		setContentView (R.layout.activity_main);

		getWindowManager ().getDefaultDisplay ().getMetrics (Container.getInstance ().getDisplayMetrics ());

		configButton = getButton (R.id.start_button_config, true);
		exitButton = getButton (R.id.start_button_exit, true);
		startButton = getButton (R.id.start_button_start, true);
	}


	@Override
	protected void onDestroy ()
	{
		super.onDestroy ();
		if (entry != null)
		{
			Container.getInstance ().getDBHandler ().resetGalaxy (entry);
		}
	}


	/**
	 * override the onClick method
	 * @param view the view that triggered the click method
	 */
	@Override
	public void onClick (View view)
	{
		switch (view.getId ())
		{
			case R.id.start_button_config:
				this.runConfig ();
				break;

			case R.id.start_button_start:
				this.runGame ();
				break;

			case R.id.start_button_exit:
				this.exiting ();
				break;
		}
	}


	/**
	 * initialize a single button_blue
	 * @param id the id to the resource
	 * @param enabled whether this button_blue is enabled or not
	 * @return the initialized button_blue
	 */
	private Button getButton (int id, boolean enabled)
	{
		Button button = (Button) findViewById (id);
		button.setTypeface (Container.getInstance ().getTypeface ());
		button.setOnClickListener (this);
		button.setEnabled (enabled);
		return button;
	}


	/**
	 * running the config activity
	 */
	private void runConfig ()
	{
		startActivity (new Intent (MainActivity.this, ConfigActivity.class));
	}


	/**
	 * running the game activity
	 */
	private void runGame ()
	{
		int size = 15 * Container.getInstance ().getDBHandler ().getItemCount (Database.PLAYERS_TABLE);

		entry = new GalaxyEntry ();
		entry.setId (0);
		entry.setCellSize (Container.getInstance ().getDisplayMetrics ().widthPixels/10);
		entry.setWidth (size);
		entry.setHeight (size);
		Container.getInstance ().getDBHandler ().initGalaxy (entry);

		startActivity (new Intent (MainActivity.this, GamefieldActivity.class));
	}


	/**
	 * finishing the app
	 */
	private void exiting ()
	{
		super.finish ();
	}
}
