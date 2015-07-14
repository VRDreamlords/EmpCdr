package de.vrd.android.games.empcdr.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import de.vrd.android.games.empcdr.R;
import de.vrd.android.games.empcdr.db.models.PlayerEntry;
import de.vrd.android.games.empcdr.util.Container;

/**
 * Created by Spellsinger007 on 27.03.2015.
 */
public class AdversaryListAdapter
	extends ArrayAdapter<PlayerEntry>
{
	public AdversaryListAdapter (List<PlayerEntry> entries)
	{
		super (Container.getInstance ().getContext (), R.layout.adversary_list_item, entries);
	}


	@Override
	public void add (PlayerEntry adversary)
	{
		super.add (adversary);
		Container.getInstance ().getDBHandler ().addPlayer (adversary);
		notifyDataSetChanged ();
	}


	@Override
	public void remove (PlayerEntry adversary)
	{
		super.remove (adversary);
		Container.getInstance ().getDBHandler ().deletePlayer (adversary);
		notifyDataSetChanged ();
	}


	@Override
	public View getView (int pos, View contentView, ViewGroup parent)
	{
		final PlayerEntry adversary = getItem (pos);

		ViewHolder viewHolder;

		contentView = LayoutInflater.from (getContext ()).inflate (R.layout.adversary_list_item, parent, false);

		viewHolder = new ViewHolder ();
		viewHolder.name = (TextView) contentView.findViewById (R.id.adversary_list_item_name);
		viewHolder.type = (TextView) contentView.findViewById (R.id.adversary_list_item_type);
		viewHolder.delete = (ImageButton) contentView.findViewById (R.id.adversary_list_item_button_delete);
		viewHolder.delete.setOnClickListener (new View.OnClickListener ()
		{
			@Override
			public void onClick (View view)
			{
				AdversaryListAdapter.this.remove (adversary);
			}
		});
		contentView.setTag (viewHolder);

		viewHolder.name.setText (adversary.getName ());
		viewHolder.type.setText (this.getType (adversary.getType ()));

		return contentView;
	}


	private String getType (int type)
	{
		switch (type)
		{
			case 0:
				return "human";

			case 1:
				return "defensive android";

			case 2:
				return "neutral android";

			case 3:
				return "offensive android";

			default:
				return "<indefinite>";
		}
	}


	private static class ViewHolder
	{
		TextView name;
		TextView type;
		ImageButton delete;
	}
}
