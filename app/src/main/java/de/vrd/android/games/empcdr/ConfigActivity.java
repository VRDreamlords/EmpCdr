package de.vrd.android.games.empcdr;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

	private PlayerEntry player;

	private EditText input;
	private Button addAdversaryButton;
	private Button backButton;

	private AdversaryListAdapter adapter;
	private List<PlayerEntry> adveraries = null;
	private ListView adveraryListView;


	@Override
	protected void onCreate (Bundle savedInstanceState)
	{
		super.onCreate (savedInstanceState);

		setContentView (R.layout.activity_config);
		input = (EditText) findViewById (R.id.config_player_input);

		addAdversaryButton = getButton (R.id.config_add_adversary, true);
		backButton = getButton (R.id.config_back_button, true);

		adveraryListView = (ListView) findViewById (R.id.progress_adversary_list);
		adveraries = Container.getInstance ().getDBHandler ().getAdversaries ();
		adapter = new AdversaryListAdapter (adveraries);
		adveraryListView.setAdapter (adapter);

		Spinner galaxyDensitySpinner = (Spinner) findViewById (R.id.config_universe_density_spinner);
		ArrayAdapter<CharSequence> densityAdapter = ArrayAdapter.createFromResource (this, R.array.galaxydensity, android.R.layout.simple_spinner_item);
		densityAdapter.setDropDownViewResource (android.R.layout.simple_spinner_dropdown_item);
		galaxyDensitySpinner.setAdapter (densityAdapter);

		Spinner galaxySizeSpinner = (Spinner) findViewById (R.id.config_universe_size_spinner);
		ArrayAdapter<CharSequence> sizeAdapter = ArrayAdapter.createFromResource (this, R.array.galaxysize, android.R.layout.simple_spinner_item);
		sizeAdapter.setDropDownViewResource (android.R.layout.simple_spinner_dropdown_item);
		galaxySizeSpinner.setAdapter (sizeAdapter);
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

		player = Container.getInstance ().getDBHandler ().getPlayer (0);
		input.setText (player.getName ());
		input.clearFocus ();
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


	/**
	 *
	 */
	private void updateValues ()
	{
		player.setName (input.getText ().toString ());
		Container.getInstance ().getDBHandler ().updatePlayer (player);
	}


	/**
	 *
	 */
	private void runAddAdversaryActivity ()
	{
		startActivityForResult (new Intent (ConfigActivity.this, ConfigAddAdversaryActivity.class), 1);
	}
}
