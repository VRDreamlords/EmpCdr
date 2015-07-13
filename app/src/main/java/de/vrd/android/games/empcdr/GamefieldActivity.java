package de.vrd.android.games.empcdr;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import de.vrd.android.games.empcdr.db.models.PlayerEntry;
import de.vrd.android.games.empcdr.db.models.GalaxyEntry;
import de.vrd.android.games.empcdr.gamefield.GalaxyLayout;
import de.vrd.android.games.empcdr.util.Container;

/**
 * Created by Spellsinger007 on 22.03.2015.
 */
public class GamefieldActivity
	extends Activity
	implements View.OnClickListener
{
	private int round = 1;
	private PlayerEntry player = Container.getInstance ().getDBHandler ().getPlayer (0);
	private GalaxyEntry galaxyEntry = Container.getInstance ().getDBHandler ().getGalaxy (0);

	private Button backButton;
	private Button endRoundButton;


	/**
	 *
	 * @param savedInstanceState
	 */
	@Override
	public void onCreate (Bundle savedInstanceState)
	{
		super.onCreate (savedInstanceState);

		setContentView (R.layout.activity_gamefileld);
		GalaxyLayout layout = (GalaxyLayout)findViewById (R.id.galaxy_view);

		backButton = getButton (R.id.game_back_button, true);
		endRoundButton= getButton (R.id.game_end_round_button, true);

		String ave = Container.getInstance ().getContext ().getString (R.string.game_welcome);
		Toast.makeText (Container.getInstance ().getContext (), ave + " " + player.getName (), Toast.LENGTH_SHORT).show ();

		Container.getInstance ().initGalaxy ();
		galaxyEntry.setFinished (false);
		for (int row = 0; row < galaxyEntry.getHeight (); row++)
		{
			for (int col = 0; col < galaxyEntry.getWidth (); col++)
			{
				layout.addView (Container.getInstance ().getGalaxy ().getGalaxyCellAt (row, col));
			}
		}
	}


	@Override
	public void onClick (View view)
	{
		switch (view.getId ())
		{
			case R.id.game_end_round_button:
				/* TODO : forall adversaries -> calc turn */
				Toast.makeText (Container.getInstance ().getContext (), "Finishing Round " + round++, Toast.LENGTH_SHORT).show ();

				break;

			case R.id.game_back_button:
				this.finish ();
				break;
		}
	}


	/**
	 * @return
	 */
	@Override
	public void finish ()
	{
		super.finish ();
		/*
		TODO : test auf 'won' oder 'lost' -> wenn false: save state
		 */
		if (galaxyEntry.isFinished ())
		{
			Container.getInstance ().getDBHandler ().resetGalaxy (galaxyEntry);
		}
		else
		{
			Container.getInstance ().getDBHandler ().updateGalaxy (galaxyEntry);
		}
	}


	/**
	 * initialize a single button_blue
	 *
	 * @param id      the id to the resource
	 * @param enabled whether this button_blue is enabled or not
	 *
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
}
