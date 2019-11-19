package com.alex.playandroid.module.knowledge.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alex.playandroid.R;
import com.alex.playandroid.base.BaseMvpFragment;
import com.alex.playandroid.module.knowledge.activity.KnowledgeActivity;
import com.alex.playandroid.module.knowledge.adapter.KnowledgeAdapter;
import com.alex.playandroid.module.knowledge.contract.KnowledgeContract;
import com.alex.playandroid.module.knowledge.presenter.KnowledgePresenter;
import com.alex.playandroid.module.main.model.ChapterBean;

import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class KnowledgeFragment extends BaseMvpFragment<KnowledgePresenter> implements KnowledgeContract.KnowledgeView {


    @BindView(R.id.rv_knowledge)
    RecyclerView rvKnowledge;

    private KnowledgeAdapter knowledgeAdapter;


    public static KnowledgeFragment newInstance() {
        KnowledgeFragment fragment = new KnowledgeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int bindLayout() {
        return R.layout.fragment_knowledge;
    }

    @Override
    protected KnowledgePresenter createPresenter() {
        return new KnowledgePresenter();
    }

    @Override
    protected void initView() {
        rvKnowledge.setLayoutManager(new LinearLayoutManager(getActivity()));
        knowledgeAdapter = new KnowledgeAdapter(R.layout.rv_item_knowledge);
        rvKnowledge.setAdapter(knowledgeAdapter);
    }

    @Override
    protected void initData() {
        presenter.requestKnowledgeList();
    }

    @Override
    protected void lazyLoadData() {
        super.lazyLoadData();

    }

    @Override
    protected void initListener() {
        knowledgeAdapter.setOnFlexboxLayoutItemClickListener(new KnowledgeAdapter.OnFlexboxLayoutItemClickListener() {
            @Override
            public void onClick(ChapterBean chapterBean, int position) {
                Intent intent = new Intent(getActivity(),KnowledgeActivity.class);
                intent.putExtra("chapter",chapterBean);
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });
    }


    @Override
    public void requestKnowledgeListSuccess(List<ChapterBean> data) {
        knowledgeAdapter.setNewData(data);
    }

    @Override
    public void requestKnowledgeListFailed(String msg) {

    }
}
