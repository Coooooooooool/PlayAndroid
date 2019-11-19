package com.alex.playandroid.module.mine.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.alex.playandroid.R;
import com.alex.playandroid.base.BaseMvpFragment;
import com.alex.playandroid.module.main.activity.WebActivity;
import com.alex.playandroid.module.mine.adapter.CollectionLinkAdapter;
import com.alex.playandroid.module.mine.contract.CollectionLinkContract;
import com.alex.playandroid.module.mine.model.CollectionLinkBean;
import com.alex.playandroid.module.mine.presenter.CollectionLinkPresenter;
import com.alex.playandroid.utils.CopyUtil;
import com.alex.playandroid.utils.IntentUtil;
import com.alex.playandroid.utils.RvAnimUtils;
import com.alex.playandroid.utils.SettingUtils;
import com.coder.zzq.smartshow.toast.SmartToast;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CollectionLinkFragment extends BaseMvpFragment<CollectionLinkPresenter> implements CollectionLinkContract.CollectionLinkView {


    @BindView(R.id.rv_link)
    RecyclerView rvLink;
    @BindView(R.id.layout_refresh)
    SmartRefreshLayout layoutRefresh;

    private CollectionLinkAdapter adapter;
    private int uncollectItemPosition;


    public static CollectionLinkFragment newInstance() {
        CollectionLinkFragment fragment = new CollectionLinkFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    protected int bindLayout() {
        return R.layout.fragment_collection_link;
    }

    @Override
    protected CollectionLinkPresenter createPresenter() {
        return new CollectionLinkPresenter();
    }

    @Override
    protected void initView() {
        rvLink.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CollectionLinkAdapter();
        RvAnimUtils.setAnim(adapter,SettingUtils.getInstance().getRvAnim());
        rvLink.setAdapter(adapter);
        View emptyView = LayoutInflater.from(getContext()).inflate(R.layout.layout_recyclerview_empty_view,null);
        adapter.setEmptyView(emptyView);

    }

    @Override
    protected void initData() {
        presenter.requestCollectionLinkList();
    }

    @Override
    protected void initListener() {
        adapter.setOnItemChildClickListener((baseQuickAdapter, view, position) -> {
            adapter.closeAll(null);
            CollectionLinkBean item = adapter.getItem(position);
            if(item == null)
                return;
            switch (view.getId()){
                case R.id.rl_top:
                    Intent intent = new Intent(getContext(),WebActivity.class);
                    intent.putExtra("title",item.getName());
                    intent.putExtra("url",item.getLink());
                    startActivity(intent);
                    break;
                case R.id.tv_copy:
                    CopyUtil.copy(getContext(),item.getLink());
                    SmartToast.show("已复制到粘贴板");
                    break;
                case R.id.tv_open:
                    if (TextUtils.isEmpty(item.getLink())) {
                        SmartToast.info("链接为空");
                        break;
                    }
                    if (getContext() != null) {
                        IntentUtil.openBrowser(getContext(), item.getLink());
                    }
                    break;
                case R.id.tv_edit:

                    break;
                case R.id.tv_delete:
                    uncollectItemPosition = position;
                    presenter.uncollect(item.getId());
                    break;
            }
        });
    }

    @Override
    public void requestCollectionLinkListSuccess(List<CollectionLinkBean> data) {
        adapter.setNewData(data);
    }

    @Override
    public void requestCollectionLinkListFailed(String msg) {
        SmartToast.fail(msg);
    }

    @Override
    public void uncollectSuccess(String data) {
        adapter.remove(uncollectItemPosition);
    }

    @Override
    public void uncollectFailed(String msg) {
        SmartToast.fail(msg);
    }

}
