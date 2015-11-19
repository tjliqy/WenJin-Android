package com.twt.service.wenjin.ui.search.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.twt.service.wenjin.R;
import com.twt.service.wenjin.bean.SearchDetailQuestion;
import com.twt.service.wenjin.support.LogHelper;
import com.twt.service.wenjin.ui.common.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Green on 15/11/17.
 */
public class SearchQuestionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String LOG_TAG = SearchQuestionFragment.class.getSimpleName();

    private static final int ITEM_VIEW_TYPE_ITEM = 0;
    private static final int ITEM_VIEW_TYPE_FOOTER = 1;

    private Context _context;
    private OnItemClickListener _onItemClicked;

    private List<SearchDetailQuestion> _DataSet = new ArrayList<>();

    private boolean _useFooter;

    public SearchQuestionAdapter(Context context, OnItemClickListener onItemClickListener){
        _context = context;
        _onItemClicked = onItemClickListener;
    }

    public static class ItemHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.tv_search_question_item_title)
        TextView mTvTile;

        @Bind(R.id.tv_search_question_item_answercount)
        TextView mTvAnswercount;

        @Bind(R.id.tv_search_question_item_followcount)
        TextView mTvFollowcount;

        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class FooterHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_footer_load_more)
        TextView tvLoadMore;
        @Bind(R.id.pb_footer_load_more)
        ProgressBar pbLoadMore;

        public FooterHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        RecyclerView.ViewHolder viewHolder;

        if(viewType == ITEM_VIEW_TYPE_ITEM){
            View view = inflater.inflate(R.layout.search_question_list_item,viewGroup,false);
            viewHolder = new ItemHolder(view);
        }else{
            View view = inflater.inflate(R.layout.recyclerview_footer_load_more,viewGroup,false);
            viewHolder = new FooterHolder(view);
        }
        LogHelper.v(LOG_TAG, "onCreateViewHolder");
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if(getItemViewType(position) == ITEM_VIEW_TYPE_ITEM){
            SearchDetailQuestion questionItem = _DataSet.get(position);
            ItemHolder itemHolder = (ItemHolder) holder;

            View.OnClickListener onClickListener = new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    _onItemClicked.onItemClicked(v, position);
                }
            };

            itemHolder.mTvTile.setOnClickListener(onClickListener);

            itemHolder.mTvTile.setText(questionItem.header.name);
            itemHolder.mTvAnswercount.setText(questionItem.answer_count);
            itemHolder.mTvFollowcount.setText(questionItem.focus_count);

            LogHelper.v(LOG_TAG, "onBindViewHolder");

        }
    }

    @Override
    public int getItemCount() {
        int count = _DataSet.size();
        return _useFooter? ++count:count;
    }

    @Override
    public int getItemViewType(int position) {
        LogHelper.v(LOG_TAG, "getItemViewType");
        return position < _DataSet.size() ? ITEM_VIEW_TYPE_ITEM:ITEM_VIEW_TYPE_FOOTER;
    }

    public SearchDetailQuestion getItem(int position){
        return _DataSet.get(position);
    }

    public void updateData(List<SearchDetailQuestion> items){
        _DataSet.clear();
        _DataSet.addAll(items);
        notifyDataSetChanged();

    }

    public void addData(List<SearchDetailQuestion> items){
        _DataSet.addAll(items);
        notifyDataSetChanged();
    }

    public void setUseFooter(boolean useFooter){
        _useFooter = useFooter;
        notifyDataSetChanged();
    }
}