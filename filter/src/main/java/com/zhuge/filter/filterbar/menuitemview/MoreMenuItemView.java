package com.zhuge.filter.filterbar.menuitemview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhuge.filter.R;
import com.zhuge.filter.filterbar.menuitemview.adapter.MoreMenuAdapter;
import com.zhuge.filter.filterbar.menuitemview.entity.MenuItem;
import com.zhuge.filter.filterbar.menuitemview.entity.MoreMenuItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 更多Item
 */
public class MoreMenuItemView extends BaseMenuItemView {
    private RecyclerView rvMore;

    public MoreMenuItemView(Context context, int layoutId, int tabIndex) {
        super(context, layoutId, tabIndex);
    }

    @Override
    public void initMenuView() {
        viewRoot.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        rvMore = viewRoot.findViewById(R.id.rv_more);
        setOrRefreshListAdapter(new ArrayList<MenuItem>());
        setSureOrClearListener();
    }

    /**
     * 设置清空条件和确定的点击事件
     */

    private void setSureOrClearListener() {
        TextView tvClear = viewRoot.findViewById(R.id.tv_clear);
        TextView tvOk = viewRoot.findViewById(R.id.tv_ok);
        tvClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetItem();
            }
        });
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectItems();
            }
        });
    }

    /**
     * 设置或刷新adapter
     */

    @Override
    public void setOrRefreshListAdapter(List<MenuItem> menuItems) {
        MoreMenuAdapter moreMenuAdapter;
        if (menuItems.isEmpty()) {
            moreMenuAdapter = new MoreMenuAdapter(itemLayout == 0 ? R.layout.item_more : itemLayout, menuItems);
            rvMore.setLayoutManager(new LinearLayoutManager(context));
            rvMore.setAdapter(moreMenuAdapter);
        } else if (rvMore.getAdapter() != null) {
            moreMenuAdapter = (MoreMenuAdapter) rvMore.getAdapter();
            if (moreMenuAdapter != null) {
                moreMenuAdapter.setNewData(menuItems);
            }
        }
    }

    /**
     * 重置条件
     */

    private void resetItem() {
        MoreMenuAdapter moreMenuAdapter = (MoreMenuAdapter) rvMore.getAdapter();
        if (moreMenuAdapter == null) {
            return;
        }
        List<MenuItem> list = moreMenuAdapter.getData();
        for (MenuItem menuItem : list) {
            List<MenuItem> childMenus = menuItem.getChildMenus();//第一级子menu
            for (int i = 0; i < childMenus.size(); i++) {
                MenuItem childItem = childMenus.get(i);
                childItem.setChecked(childItem.getItemId().equals("-1"));
                MoreMenuItem moreMenuItem = childItem.getMoreMenuItem();
                if (moreMenuItem != null) {
                    List<MenuItem> moreMenus = moreMenuItem.getMoreMenus();
                    for (int i1 = 0; i1 < moreMenus.size(); i1++) {
                        MenuItem item = moreMenus.get(i1);
                        List<MenuItem> childMenus1 = item.getChildMenus();
                        for (int i2 = 0; i2 < childMenus1.size(); i2++) {
                            MenuItem item1 = childMenus1.get(i2);
                            item1.setChecked(item1.getItemId().equals("-1"));
                        }
                    }

                    List<MenuItem> secondMenus = moreMenuItem.getSecondMenus();
                    for (int i1 = 0; i1 < secondMenus.size(); i1++) {
                        MenuItem item = secondMenus.get(i1);
                        item.setChecked(item.getItemId().equals("-1"));
                    }
                }
            }
            menuItem.setMoreMenuItem(null);
        }
        moreMenuAdapter.notifyDataSetChanged();
    }


    /**
     * 确定条件
     */

    private void selectItems() {
        MoreMenuAdapter moreMenuAdapter = (MoreMenuAdapter) rvMore.getAdapter();
        if (moreMenuAdapter == null) {
            return;
        }
        List<MenuItem> list = moreMenuAdapter.getData();
        List<MenuItem> resultList = new ArrayList<>();
        for (MenuItem menuItem : list) {
            List<MenuItem> childMenus = menuItem.getChildMenus();//第一级子menu
            for (MenuItem childMenu : childMenus) {
                if (childMenu.isChecked()) {
                    resultList.add(childMenu);
                }
            }

            if (menuItem.getMoreMenuItem() != null) {//更多的菜单---租房专用
                MoreMenuItem moreMenuItem = menuItem.getMoreMenuItem();
                List<MenuItem> secondMenus = moreMenuItem.getSecondMenus();//子菜单中的筛选项
                for (MenuItem secondMenu : secondMenus) {
                    if (secondMenu.isChecked()) {
                        resultList.add(secondMenu);
                    }
                }

                List<MenuItem> moreMenus = moreMenuItem.getMoreMenus();//子菜单中延伸的筛选项

                for (MenuItem moreMenu : moreMenus) {
                    List<MenuItem> childMenus1 = moreMenu.getChildMenus();
                    for (MenuItem item : childMenus1) {
                        if (item.isChecked()) {
                            resultList.add(item);
                        }
                    }
                }
            }
        }

        if (onMenuItemClickListener != null) {
            onMenuItemClickListener.onSelectMenuClick(resultList, tabIndex);
        }
    }

    /**
     * 设置数据回显
     */
    @Override
    public void setCurrentFilterData(List<MenuItem> menuItems) {
        MoreMenuAdapter moreMenuAdapter = (MoreMenuAdapter) rvMore.getAdapter();
        if (moreMenuAdapter == null) {
            return;
        }
        List<MenuItem> data = moreMenuAdapter.getData();

        if (menuItems == null || menuItems.size() == 0) {
            resetItem();
            return;
        }

        Map<String, String> mapNomalItem = new HashMap<>();//key为pid String为对应的一级子itemId 用逗号拼接
        Map<String, String> mapSecondItem = new HashMap<>();//key为pid String为对应的二级子itemId 用逗号拼接
        Map<String, Map<String, String>> mapMoreItem = new HashMap<>();//key为pid String为对应的更多子item(pid--itemid) 用逗号拼接

        for (MenuItem menuItem : menuItems) {
            if (menuItem.getParentItem() != null) {//有父亲节点
                MenuItem parentItem = menuItem.getParentItem();
                if (parentItem.getParentItem() != null) {//--说明是更多选项中的子选项
                    MenuItem item = parentItem.getParentItem();
                    String pid = item.getParentId();
                    if (mapMoreItem.containsKey(pid)) {
                        Map<String, String> stringMap = mapMoreItem.get(pid);
                        String itemId = parentItem.getItemId();
                        if (!TextUtils.isEmpty(itemId) || stringMap == null) {
                            continue;
                        }
                        if (stringMap.containsKey(itemId)) {
                            String id = stringMap.get(itemId);
                            stringMap.put(itemId, id + "," + menuItem.getItemId());
                        } else {
                            stringMap.put(itemId, menuItem.getItemId());
                        }
                        mapMoreItem.put(pid, stringMap);
                    } else {
                        Map<String, String> stringMap = new HashMap<>();
                        stringMap.put(parentItem.getItemId(), menuItem.getItemId());
                        mapMoreItem.put(pid, stringMap);
                    }
                } else {//--说明是第二级子item(可展开)的选项
                    String parentId = parentItem.getParentId();
                    if (mapSecondItem.containsKey(parentId)) {
                        String id = mapSecondItem.get(parentId);
                        mapSecondItem.put(parentId, id + "," + menuItem.getItemId());
                    } else {
                        mapSecondItem.put(parentId, menuItem.getItemId());
                    }
                }
            } else {//---说明是第一级子item
                String parentId = menuItem.getParentId();
                if (mapNomalItem.containsKey(parentId)) {
                    String id = mapNomalItem.get(parentId);
                    mapNomalItem.put(parentId, id + "," + menuItem.getItemId());
                } else {
                    mapNomalItem.put(parentId, menuItem.getItemId());
                }
            }
        }

        for (int i = 0; i < data.size(); i++) {
            MenuItem menuItem = data.get(i);
            List<MenuItem> childMenus = menuItem.getChildMenus();
            String pid = menuItem.getItemId();

            if (mapNomalItem.containsKey(pid)) {
                MoreMenuItem moreMenuItem = null;
                if (childMenus != null && !childMenus.isEmpty()) {
                    String itemIds = mapNomalItem.get(pid);
                    for (MenuItem childMenu : childMenus) {
                        if (!TextUtils.isEmpty(itemIds) && itemIds != null && itemIds.contains(childMenu.getItemId())) {
                            childMenu.setChecked(true);
                            if(childMenu.getMoreMenuItem() != null) {
                                moreMenuItem = childMenu.getMoreMenuItem();
                            }
                        } else {
                            childMenu.setChecked(false);
                        }
                    }
                }

                if(moreMenuItem != null) {
                    menuItem.setMoreMenuItem(moreMenuItem);
                    List<MenuItem> secondMenus = moreMenuItem.getSecondMenus();
                    List<MenuItem> moreMenus = moreMenuItem.getMoreMenus();
                    if (secondMenus != null && !secondMenus.isEmpty()) {
                        String secondIds = mapSecondItem.get(pid);
                        boolean isExpend = false;
                        for (int j = 0; j < secondMenus.size(); j++) {
                            MenuItem item = secondMenus.get(j);
                            if (!TextUtils.isEmpty(secondIds) && secondIds != null && secondIds.contains(item.getItemId())) {
                                if (j > 12) {
                                    isExpend = true;
                                }
                                item.setChecked(true);
                            } else {
                                item.setChecked(false);
                            }
                        }
                        moreMenuItem.setExpend(isExpend);
                    }

                    if (moreMenus != null && !moreMenus.isEmpty()) {
                        Map<String, String> moreMap = mapMoreItem.get(pid);
                        if (moreMap != null && moreMap.size() > 0) {
                            for (MenuItem moreMenu : moreMenus) {
                                String morePid = moreMenu.getItemId();
                                if (moreMap.containsKey(morePid)) {
                                    String moreChildIds = moreMap.get(morePid);
                                    List<MenuItem> moreChild = moreMenu.getChildMenus();
                                    for (MenuItem item : moreChild) {
                                        if(!TextUtils.isEmpty(moreChildIds) && moreChildIds != null && moreChildIds.contains(item.getItemId())) {
                                            item.setChecked(true);
                                        } else {
                                            item.setChecked(false);
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    menuItem.setMoreMenuItem(null);
                }
            }
        }

        moreMenuAdapter.notifyDataSetChanged();
        rvMore.smoothScrollToPosition(0);
    }
}
