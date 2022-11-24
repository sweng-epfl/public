package ch.epfl.sweng;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

/**
 * An {@link DiffUtil.ItemCallback} which may be used to compute the difference between two {@link
 * Story}.
 */
public final class StoryItemCallback extends DiffUtil.ItemCallback<Story> {

    /** {@inheritDoc} */
    @Override
    public boolean areItemsTheSame(@NonNull Story oldItem, @NonNull Story newItem) {
        return oldItem.getId() == newItem.getId();
    }

    /** {@inheritDoc} */
    @Override
    public boolean areContentsTheSame(@NonNull Story oldItem, @NonNull Story newItem) {
        return oldItem.equals(newItem);
    }
}
