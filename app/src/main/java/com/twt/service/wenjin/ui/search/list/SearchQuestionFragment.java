package com.twt.service.wenjin.ui.search.list;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.twt.service.wenjin.R;
import com.twt.service.wenjin.bean.SearchDetailQuestion;
import com.twt.service.wenjin.support.LogHelper;
import com.twt.service.wenjin.ui.common.OnItemClickListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Green on 15/11/16.
 */
public class SearchQuestionFragment extends Fragment implements OnItemClickListener {

    private static final String LOG_TAG = SearchQuestionFragment.class.getSimpleName();

    @Bind(R.id.search_recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.search_swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private SearchQuestionAdapter mSearchQuestionAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    ArrayList<SearchDetailQuestion> mQuestions = null;

    String keyword;

    public SearchQuestionFragment(){

    }

    public static SearchQuestionFragment getInstance(ArrayList<SearchDetailQuestion> questions){
        SearchQuestionFragment searchQuestionFragment = new SearchQuestionFragment();
        Bundle bundle = new Bundle();
        LogHelper.v(LOG_TAG,"mquestions new instance");
        if(questions != null) {
            bundle.putSerializable("questions", questions);
            LogHelper.v(LOG_TAG, "mquestions has bundle");
        }
        searchQuestionFragment.setArguments(bundle);
        return searchQuestionFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!getArguments().isEmpty() && mQuestions == null){
            mQuestions = (ArrayList<SearchDetailQuestion>) getArguments().getSerializable("questions");
            LogHelper.v(LOG_TAG,"has args");
        }{
            LogHelper.v(LOG_TAG,"empty args");
            mQuestions = new ArrayList<>();
            SearchDetailQuestion question = new SearchDetailQuestion();
            question.header.name = "测试";
            question.answer_count = "10";
            question.focus_count = "12";
            mQuestions.add(question);
            mQuestions.add(question);
            mQuestions.add(question);
            mQuestions.add(question);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search_list,container,false);
        ButterKnife.bind(this,rootView);

        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.color_primary));
        mSearchQuestionAdapter = new SearchQuestionAdapter(getActivity(),this);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mSearchQuestionAdapter);
        LogHelper.v(LOG_TAG, "onCreateView return rootview");
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onItemClicked(View view, int position) {

    }

    @Override
    public void onResume() {
        super.onResume();
        if(mQuestions != null){
            updateListData(mQuestions);
        }
    }

    public void showFooter() {
        mSearchQuestionAdapter.setUseFooter(true);
    }

    public void hideFooter() {
        mSearchQuestionAdapter.setUseFooter(false);
    }

    public void addListData(List<SearchDetailQuestion> items){
        mSearchQuestionAdapter.addData(items);
    }

    public void updateListData(List<SearchDetailQuestion> items){
        mSearchQuestionAdapter.updateData(items);
    }

    public void updateData(ArrayList<SearchDetailQuestion> items){
        mQuestions = items;
    }

    public void setKeyword(String keyword){
        this.keyword = keyword;
    }




}