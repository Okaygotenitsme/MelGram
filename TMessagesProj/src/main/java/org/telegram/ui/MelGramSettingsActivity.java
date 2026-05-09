package org.telegram.ui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.telegram.messenger.MelGramConfig;
import org.telegram.messenger.R;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Cells.HeaderCell;
import org.telegram.ui.Cells.TextCheckCell;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.RecyclerListView;

public class MelGramSettingsActivity extends BaseFragment {

    private static final int ROW_HEADER_PRIVACY = 0;
    private static final int ROW_GHOST_MODE = 1;
    private static final int ROW_HIDE_READ_RECEIPTS = 2;
    private static final int ROW_DISABLE_TYPING = 3;
    private static final int ROWS_COUNT = 4;

    private RecyclerListView listView;
    private ListAdapter adapter;

    @Override
    public View createView(Context context) {
        actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        actionBar.setTitle("MelGram");
        actionBar.setActionBarMenuOnItemClick(new ActionBar.ActionBarMenuOnItemClick() {
            @Override
            public void onItemClick(int id) {
                if (id == -1) finishFragment();
            }
        });

        fragmentView = new FrameLayout(context);
        fragmentView.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        FrameLayout frameLayout = (FrameLayout) fragmentView;

        listView = new RecyclerListView(context);
        listView.setLayoutManager(new LinearLayoutManager(context));
        listView.setAdapter(adapter = new ListAdapter(context));
        listView.setOnItemClickListener((view, position) -> {
            if (position == ROW_GHOST_MODE) {
                MelGramConfig.setGhostMode(!MelGramConfig.showGhostMode);
                ((TextCheckCell) view).setChecked(MelGramConfig.showGhostMode);
            } else if (position == ROW_HIDE_READ_RECEIPTS) {
                MelGramConfig.setHideReadReceipts(!MelGramConfig.hideReadReceipts);
                ((TextCheckCell) view).setChecked(MelGramConfig.hideReadReceipts);
            } else if (position == ROW_DISABLE_TYPING) {
                MelGramConfig.setDisableTypingStatus(!MelGramConfig.disableTypingStatus);
                ((TextCheckCell) view).setChecked(MelGramConfig.disableTypingStatus);
            }
        });

        frameLayout.addView(listView, LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        return fragmentView;
    }

    private class ListAdapter extends RecyclerListView.SelectionAdapter {

        private final Context context;

        ListAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == ROW_HEADER_PRIVACY) return 0;
            return 1;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            if (viewType == 0) {
                view = new HeaderCell(context);
                view.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
            } else {
                view = new TextCheckCell(context);
                view.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
            }
            return new RecyclerListView.Holder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (position == ROW_HEADER_PRIVACY) {
                ((HeaderCell) holder.itemView).setText("Приватность");
            } else if (position == ROW_GHOST_MODE) {
                ((TextCheckCell) holder.itemView).setTextAndCheck("Ghost Mode", MelGramConfig.showGhostMode, true);
            } else if (position == ROW_HIDE_READ_RECEIPTS) {
                ((TextCheckCell) holder.itemView).setTextAndCheck("Скрыть прочтение", MelGramConfig.hideReadReceipts, true);
            } else if (position == ROW_DISABLE_TYPING) {
                ((TextCheckCell) holder.itemView).setTextAndCheck("Скрыть печатаю...", MelGramConfig.disableTypingStatus, false);
            }
        }

        @Override
        public int getItemCount() {
            return ROWS_COUNT;
        }

        @Override
        public boolean isEnabled(RecyclerView.ViewHolder holder) {
            int pos = holder.getAdapterPosition();
            return pos != ROW_HEADER_PRIVACY;
        }
    }
}
