package ch.epfl.sweng;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

/** An adapter for the list of stories. */
public final class StoriesAdapter extends ListAdapter<Story, StoriesAdapter.ViewHolder> {

    public StoriesAdapter() {
        super(new StoryItemCallback());
    }

    /** {@inheritDoc} */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        var inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(android.R.layout.simple_list_item_2, parent, false));
    }

    /** {@inheritDoc} */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(getItem(position).getTitle());
        holder.subtitle.setText(getItem(position).getUrl());
    }

    /** The {@link RecyclerView.ViewHolder} which holds the views for the {@link Story} items. */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final TextView subtitle;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(android.R.id.text1);
            subtitle = itemView.findViewById(android.R.id.text2);
        }
    }
}
