package com.zhuge.filter.filterbar.menuitemview.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhuge.filter.R;
import com.zhuge.filter.filterbar.menuitemview.entity.MenuItem;
import com.zhuge.filter.filterbar.menuitemview.entity.MoreMenuItem;

import java.util.List;

/**
 * 更多类型适配器
 */
public class MoreMenuAdapter extends BaseQuickAdapter<MenuItem, BaseViewHolder> {
    private int layoutResId;

    public MoreMenuAdapter(int layoutResId, @Nullable List<MenuItem> data) {
        super(layoutResId, data);
        this.layoutResId = layoutResId;
    }

    @Override
    protected void convert(BaseViewHolder helper, MenuItem item) {
        helper.setText(R.id.tv_more_title, item.getItemTitle());
        RecyclerView rvItem = helper.getView(R.id.rv_item);

        setItemRecycler(rvItem, item.getChildMenus(), item.isChildMulty(), helper);
        setMoreViewData(helper, item);
    }

    /**
     * 设置展示第二级的数据---租房经济公司/公寓
     */

    private void setMoreViewData(BaseViewHolder helper, final MenuItem item) {
        LinearLayout llMore = helper.getView(R.id.ll_more);
        final RecyclerView rvChildItem = helper.getView(R.id.rv_child_item);
        final TextView tvOpen = helper.getView(R.id.tv_open);
        RecyclerView rvMore = helper.getView(R.id.rv_more);
        if (item.getMoreMenuItem() == null) {
            llMore.setVisibility(View.GONE);
            return;
        }

        boolean isShowMoreView = false;
        final MoreMenuItem moreMenuItem = item.getMoreMenuItem();
        if (moreMenuItem.getSecondMenus() != null) {
            isShowMoreView = true;
            rvChildItem.setVisibility(View.VISIBLE);
            final List<MenuItem> childMenus = moreMenuItem.getSecondMenus();
            if (childMenus.size() > 12) {//超过12条要展示展开收起
                if(moreMenuItem.isExpend()) {
                    tvOpen.setText("收起");
                } else {
                    setExpandText(tvOpen, item.getItemTitle());
                }
                final String strExpand = tvOpen.getText().toString();
                tvOpen.setVisibility(View.VISIBLE);

                if (TextUtils.isEmpty(strExpand)) {
                    setExpandText(tvOpen, item.getItemTitle());
                    tvOpen.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            moreMenuItem.setExpend(!moreMenuItem.isExpend());
                            if ("收起".equals(strExpand)) {
                                setExpandText(tvOpen, item.getItemTitle());
                                setChildRecycler(rvChildItem, childMenus.subList(0, 12), moreMenuItem.isSecondChildMulty());
                            } else {
                                tvOpen.setText("收起");
                                setChildRecycler(rvChildItem, childMenus, moreMenuItem.isSecondChildMulty());
                            }
                        }
                    });
                    setChildRecycler(rvChildItem, item.getChildMenus().subList(0, 12), moreMenuItem.isSecondChildMulty());
                } else {
                    if ("收起".equals(strExpand)) {
                        setChildRecycler(rvChildItem, childMenus.subList(0, 12), moreMenuItem.isSecondChildMulty());
                    } else {
                        setChildRecycler(rvChildItem, childMenus, moreMenuItem.isSecondChildMulty());
                    }
                }
            } else {
                setChildRecycler(rvChildItem, childMenus, moreMenuItem.isSecondChildMulty());
                tvOpen.setVisibility(View.GONE);
            }
        } else {
            rvChildItem.setVisibility(View.GONE);
        }

        if (moreMenuItem.getMoreMenus() != null) {
            isShowMoreView = true;
            rvMore.setVisibility(View.VISIBLE);
            setMoreRecycler(rvMore, moreMenuItem.getMoreMenus());
        } else {
            rvMore.setVisibility(View.GONE);
        }

        if (isShowMoreView) {
            llMore.setVisibility(View.VISIBLE);
        } else {
            llMore.setVisibility(View.GONE);
        }
    }

    /**
     * 设置展开收起的文案
     */

    private void setExpandText(TextView tvOpen, String title) {
        String sb = "展开查看全部" +
                title;
        tvOpen.setText(sb);
    }

    /**
     * 设置第一级的数据
     */

    private void setItemRecycler(RecyclerView rvItem, List<MenuItem> childMenus, boolean isChildMulty, BaseViewHolder helper) {
        if (rvItem.getAdapter() == null) {
            rvItem.setLayoutManager(new GridLayoutManager(mContext, 4));
            MoreChildMenuAdapter moreChildMenuAdapter = new MoreChildMenuAdapter(R.layout.item_more_child, childMenus);
            setMoreChildItemListener(moreChildMenuAdapter, isChildMulty, helper.getAdapterPosition());
            rvItem.setAdapter(moreChildMenuAdapter);
        } else {
            MoreChildMenuAdapter moreChildMenuAdapter = (MoreChildMenuAdapter) rvItem.getAdapter();
            moreChildMenuAdapter.setNewData(childMenus);
        }
    }

    /**
     * 设置子view点击事件
     * mposition 为第一级子列表对应的position  第二级子列表 为-1 不需要处理
     */

    private void setMoreChildItemListener(final MoreChildMenuAdapter moreChildMenuAdapter, final boolean isChildMulty, final int mposition) {
        moreChildMenuAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MenuItem childItem = moreChildMenuAdapter.getData().get(position);
                if (!childItem.isChecked() && (childItem.getItemId().equals("-1") || !isChildMulty)) {
                    for (int i = 0; i < moreChildMenuAdapter.getData().size(); i++) {
                        moreChildMenuAdapter.getData().get(i).setChecked(position == i);
                    }
                    if(mposition != -1) {
                        if (childItem.getMoreMenuItem() != null) {
                            MoreMenuItem moreMenuItem = childItem.getMoreMenuItem();
                            List<MenuItem> secondMenus = moreMenuItem.getSecondMenus();
                            if(secondMenus != null && !secondMenus.isEmpty()) {
                                for (MenuItem menuItem : secondMenus) {
                                    if(menuItem.getItemId().equals("-1")) {
                                        menuItem.setChecked(true);
                                    } else {
                                        menuItem.setChecked(false);
                                    }
                                }
                            }

                            List<MenuItem> moreMenus = moreMenuItem.getMoreMenus();
                            if(moreMenus != null && !moreMenus.isEmpty()) {
                                for (MenuItem moreMenu : moreMenus) {
                                    List<MenuItem> childMenus = moreMenu.getChildMenus();
                                    if(childMenus != null && !childMenus.isEmpty()) {
                                        for (MenuItem childMenu : childMenus) {
                                            if(childMenu.getItemId().equals("-1")) {
                                                childMenu.setChecked(true);
                                            } else {
                                                childMenu.setChecked(false);
                                            }
                                        }
                                    }
                                }
                            }

                            getData().get(mposition).setMoreMenuItem(moreMenuItem);
                        } else {
                            getData().get(mposition).setMoreMenuItem(null);
                        }
                    }
                    notifyDataSetChanged();
                } else if (childItem.isChecked() && !childItem.getItemId().equals("-1")) {
                    childItem.setChecked(false);
                    boolean isNeedUnLimit = true;//是否需要选中不限
                    for (MenuItem datum : moreChildMenuAdapter.getData()) {
                        if (datum.isChecked()) {
                            isNeedUnLimit = false;
                            break;
                        }
                    }

                    if (moreChildMenuAdapter.getData().get(0).getItemId().equals("-1") && isNeedUnLimit) {
                        moreChildMenuAdapter.getData().get(0).setChecked(true);
                    }
                    if(mposition != -1) {
                        getData().get(mposition).setMoreMenuItem(null);
                    }
                    notifyDataSetChanged();
                } else if (!childItem.isChecked() && isChildMulty) {
                    if (moreChildMenuAdapter.getData().get(0).getItemId().equals("-1")) {
                        moreChildMenuAdapter.getData().get(0).setChecked(false);
                    }
                    moreChildMenuAdapter.getData().get(position).setChecked(true);
                    if(mposition != -1) {
                        if (childItem.getMoreMenuItem() != null) {
                            getData().get(mposition).setMoreMenuItem(childItem.getMoreMenuItem());
                        } else {
                            getData().get(mposition).setMoreMenuItem(null);
                        }
                    }
                    notifyDataSetChanged();
                }
                moreChildMenuAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 设置第二级的数据
     */

    private void setChildRecycler(RecyclerView rvItem, List<MenuItem> childMenus, boolean isChildMulty) {
        if (rvItem.getAdapter() == null) {
            rvItem.setLayoutManager(new GridLayoutManager(mContext, 4));
            MoreChildMenuAdapter moreChildMenuAdapter = new MoreChildMenuAdapter(R.layout.item_more_child, childMenus);
            setMoreChildItemListener(moreChildMenuAdapter, isChildMulty, -1);
            rvItem.setAdapter(moreChildMenuAdapter);
        } else {
            MoreChildMenuAdapter moreChildMenuAdapter = (MoreChildMenuAdapter) rvItem.getAdapter();
            moreChildMenuAdapter.setNewData(childMenus);
        }
    }

    /**
     * 设置第二级的更多的数据 样式和主样式一致 有大标题
     */

    private void setMoreRecycler(RecyclerView rvItem, List<MenuItem> moreMenus) {
        if (rvItem.getAdapter() == null) {
            rvItem.setLayoutManager(new LinearLayoutManager(mContext));
            rvItem.setAdapter(new MoreMenuAdapter(layoutResId, moreMenus));
        } else {
            MoreMenuAdapter moreChildMenuAdapter = (MoreMenuAdapter) rvItem.getAdapter();
            moreChildMenuAdapter.setNewData(moreMenus);
        }
    }
}
