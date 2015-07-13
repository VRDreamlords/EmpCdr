package de.vrd.android.games.empcdr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.List;

import de.vrd.android.games.empcdr.adapters.AdversaryListAdapter;
import de.vrd.android.games.empcdr.db.models.PlayerEntry;
import de.vrd.android.games.empcdr.util.Container;

/**
 * Created by Spellsinger007 on 22.03.2015.
 */
public class ConfigActivity
	extends Activity
	implements View.OnClickListener
{
	private final String TAG = this.getClass ().getSimpleName ();
//	private final Resources resources = getResources ();
//	private final String[] galaxydensity = resources.getStringArray (R.array.galaxydensity);
//	private final String[] galaxysize = resources.getStringArray (R.array.galaxysize);

	private EditText input;
	private Button addAdversaryButton;
	private Button backButton;
	private CheckBox music;
	private CheckBox sound;
	private Spinner galaxyDensitySpinner;
	private Spinner galaxySizeSpinner;

	private AdversaryListAdapter adapter;
	private List<PlayerEntry> adveraries = null;
	private ListView adveraryListView;


	@Override
	protected void onCreate (Bundle savedInstanceState)
	{
		super.onCreate (savedInstanceState);

		setContentView (R.layout.activity_config);
		input = (EditText) findViewById (R.id.config_player_input);

		music = getCheckBox (R.id.config_music_check, true);
		sound = getCheckBox (R.id.config_sound_check, true);

		addAdversaryButton = getButton (R.id.config_add_adversary, true);
		backButton = getButton (R.id.config_back_button, true);

		adveraryListView = (ListView) findViewById (R.id.progress_adversary_list);
		adveraries = Container.getInstance ().getDBHandler ().getAdversaries ();
		adapter = new AdversaryListAdapter (adveraries);
		adveraryListView.setAdapter (adapter);

		galaxyDensitySpinner = getSpinner (R.id.config_galaxy_density_spinner, R.array.galaxydensity, true);
		galaxySizeSpinner = getSpinner (R.id.config_galaxy_size_spinner, R.array.galaxysize, true);
	}


	@Override
	protected void onPause ()
	{
		super.onPause ();

		updateValues ();
	}


	@Override
	protected void onResume ()
	{
		super.onResume ();

		input.setText (Container.getInstance ().getPlayerEntry (0).getName ());
		input.clearFocus ();

		music.setChecked (Container.getInstance ().getPlayerEntry (0).hasMusic ());
		sound.setChecked (Container.getInstance ().getPlayerEntry (0).hasSound ());

		galaxyDensitySpinner.setSelection (Container.getInstance ().getGalaxyEntry (0).getDensity ());
		galaxySizeSpinner.setSelection (Container.getInstance ().getGalaxyEntry (0).getSize ());
	}


	protected void onActivityResult (int requestCode, int resultCode, Intent data)
	{
		switch (requestCode)
		{
			case 1:
				if (resultCode == RESULT_OK)
				{
					Bundle result = data.getExtras ();

					adapter.add (new PlayerEntry (
						result.getInt ("id"),
						result.getString ("name"),
						result.getInt ("type")));
					adapter.notifyDataSetChanged ();
				}
				break;
		}
	}

	@Override
	public void onClick (View view)
	{
		switch (view.getId ())
		{
			case R.id.config_add_adversary:
				this.runAddAdversaryActivity ();
				break;

			case R.id.config_back_button:
				this.finish ();
				break;

			case R.id.config_music_check:
				Container.getInstance ().getPlayerEntry (0).setMusic (music.isChecked ());
				break;

			case R.id.config_sound_check:
				Container.getInstance ().getPlayerEntry (0).setSound (sound.isChecked ());
				break;
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


	private CheckBox getCheckBox (int id, boolean enabled)
	{
		CheckBox checkbox = (CheckBox) findViewById (id);
		checkbox.setTypeface (Container.getInstance ().getTypeface ());
		checkbox.setOnClickListener (this);
		checkbox.setEnabled (enabled);
		return checkbox;
	}


	private Spinner getSpinner (int id, int array_id, boolean enabled)
	{
		Spinner spinner = (Spinner) findViewById (id);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource (this, array_id, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource (android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter (adapter);
		spinner.setEnabled (enabled);
		return spinner;
	}


	/**
	 * Update the Player & Galaxy values when exiting the config menu
	 */
	private void updateValues ()
	{
		Container.getInstance ().getPlayerEntry (0).setName (input.getText ().toString ());
		Container.getInstance ().getDBHandler ().updatePlayer (Container.getInstance ().getPlayerEntry (0));

		Container.getInstance ().getGalaxyEntry (0).setDensity (getDensity (galaxyDensitySpinner.getSelectedItem ().toString ()));
		Container.getInstance ().getGalaxyEntry (0).setSize (getSize (galaxySizeSpinner.getSelectedItem ().toString ()));
		Container.getInstance ().getDBHandler ().updateGalaxy (Container.getInstance ().getGalaxyEntry (0));
	}


	/**
	 * Getting the numeric density value corresponding the spinner entry.
	 * @param value the string value from the spinner
	 * @return the numeric value
	 */
	private int getDensity (String value)
	{
		if (value.equals ("sparse"))
		{
			return 0;
		}
		else if (value.equals ("intermediate"))
		{
			return 1;
		}
		else
		{
			return 2;
		}
	}


	/**
	 * Getting the numeric size value corresponding the spinner entry.
	 * @param value the string value from the spinner
	 * @return the numeric value
	 */
	private int getSize (String value)
	{
		if (value.equals ("small"))
		{
			return 0;
		}
		else if (value.equals ("medium"))
		{
			return 1;
		}
		else
		{
			return 2;
		}
	}

	/**
	 *
	 */
	private void runAddAdversaryActivity ()
	{
		startActivityForResult (new Intent (ConfigActivity.this, ConfigAddAdversaryActivity.class), 1);
	}
}
