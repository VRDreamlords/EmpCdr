package de.vrd.android.games.empcdr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import de.vrd.android.games.empcdr.db.Database;
import de.vrd.android.games.empcdr.db.tables.PlayersTable;
import de.vrd.android.games.empcdr.util.Container;

/**
 * Created by Spellsinger007 on 25.03.2015.
 */
public class ConfigAddAdversaryActivity
	extends Activity
	implements View.OnClickListener
{
	private int id;
	private Button addButton;
	private Button abortButton;
	private EditText name;
	private EditText type;

	@Override
	protected void onCreate (Bundle savedInstanceState)
	{
		super.onCreate (savedInstanceState);
		setContentView (R.layout.activity_add_adversary);

		addButton = getButton (R.id.config_adversary_add, true);
		abortButton = getButton (R.id.config_adversary_abort, true);

		id = 1+Container.getInstance ().getDBHandler ().getMaxID (Database.PLAYERS_TABLE, PlayersTable.KEY_ID);
		name = (EditText) findViewById (R.id.adversary_name_input);
		name.setText ("Adversary " + id);
		name.selectAll ();
		name.requestFocus ();
		type = (EditText) findViewById (R.id.adversary_type_input);
		type.setText ("Device");
	}


	@Override
	public void onClick (View view)
	{
		switch (view.getId ())
		{
			case R.id.config_adversary_add:
				this.finishWithResult ();
				break;

			case R.id.config_adversary_abort:
				this.finish ();
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
	 *
	 */
	private void finishWithResult ()
	{
//		Log.i (this.getClass ().getSimpleName (), "finishing");

		Intent intent = new Intent ();
		Bundle data = new Bundle ();

		data.putInt ("id", id);
		data.putString ("name", name.getText ().toString ());
		data.putInt ("type", getType (type.getText ().toString ()));

		intent.putExtras (data);
		setResult (RESULT_OK, intent);
		finish ();
	}


	private int getType (String type)
	{
		if (type.equalsIgnoreCase ("human"))
		{
			return 0;
		}
		else
		{
			return -1;
		}
	}
}
