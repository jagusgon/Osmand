package net.osmand.plus.mapmarkers.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import net.osmand.plus.GPXDatabase.GpxDataItem;
import net.osmand.plus.R;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class TracksGroupsAdapter extends GroupsAdapter {

	private List<GpxDataItem> gpxFiles;

	public TracksGroupsAdapter(Context context, List<GpxDataItem> gpxFiles) {
		super(context);
		this.gpxFiles = gpxFiles;
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		if (holder instanceof MapMarkersGroupHeaderViewHolder) {
			MapMarkersGroupHeaderViewHolder markersGroupHeaderViewHolder = (MapMarkersGroupHeaderViewHolder) holder;
			markersGroupHeaderViewHolder.title.setText(app.getText(R.string.shared_string_tracks));
			markersGroupHeaderViewHolder.description.setText(app.getText(R.string.add_track_to_markers_descr));
		} else if (holder instanceof MapMarkersGroupViewHolder) {
			GpxDataItem gpx = getItem(position);
			MapMarkersGroupViewHolder markersGroupViewHolder = (MapMarkersGroupViewHolder) holder;
			markersGroupViewHolder.icon.setImageDrawable(iconsCache.getThemedIcon(R.drawable.ic_action_polygom_dark));
			markersGroupViewHolder.name.setText(gpx.getFile().getName().replace(".gpx", "").replace("/", " ").replace("_", " "));
			markersGroupViewHolder.numberCount.setText(String.valueOf(gpx.getAnalysis().wptPoints));
			String description = getDescription(gpx);
			markersGroupViewHolder.description.setVisibility(description == null ? View.GONE : View.VISIBLE);
			markersGroupViewHolder.description.setText(description);
		}
	}

	@Override
	public int getItemCount() {
		return gpxFiles.size() + 1;
	}

	@Nullable
	private String getDescription(GpxDataItem item) {
		Set<String> categories = item.getAnalysis().wptCategoryNames;
		if (categories != null && !categories.isEmpty() && !(categories.size() == 1 && categories.contains(""))) {
			StringBuilder sb = new StringBuilder();
			Iterator<String> it = categories.iterator();
			while (it.hasNext()) {
				String category = it.next();
				if (!category.equals("")) {
					sb.append(category);
					if (it.hasNext()) {
						sb.append(", ");
					}
				}
			}
			return sb.toString();
		}
		return null;
	}

	private GpxDataItem getItem(int position) {
		return gpxFiles.get(position - 1);
	}
}
