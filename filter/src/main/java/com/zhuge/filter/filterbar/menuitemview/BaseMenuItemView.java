package com.zhuge.filter.filterbar.menuitemview;

import android.content.Context;
import android.view.View;

import com.zhuge.filter.filterbar.menuitemview.entity.MenuItem;
import com.zhuge.filter.filterbar.menuitemview.listener.OnMenuItemClickListener;

import java.util.List;

/**
 * menuItemView的基类
 */
public abstract class BaseMenuItemView {
    Context context;
    View viewRoot;
    int tabIndex;
    int itemLayout;//自定义adapter的布局文件

    OnMenuItemClickListener onMenuItemClickListener;

    BaseMenuItemView(Context context, int layoutId, int tabIndex) {
        this.context = context;
        this.tabIndex = tabIndex;
        viewRoot = View.inflate(context, layoutId, null);
        initMenuView();
    }

    abstract void initMenuView();

    /**
     * 设置当前的menuView
     */
    public View getMenuItemView() {
        return viewRoot;
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        this.onMenuItemClickListener = onMenuItemClickListener;
    }

    public abstract void setCurrentFilterData(List<MenuItem> menuItems);

    public void setItemLayout(int itemLayout) {
        this.itemLayout = itemLayout;
    }

    public void setOrRefreshListAdapter(List<MenuItem> dataList) {

    }

    /**
     * tabIndex -- 区域 第一级的index
     * tabTitle -- 区域 第一级的title
     */

    public void setOrRefreshListAdapter(List<MenuItem> dataList, int tabIndex, String tabTitle) {

    }

    public void setMultyChoice(boolean isMultyChoice) {

    }
}
