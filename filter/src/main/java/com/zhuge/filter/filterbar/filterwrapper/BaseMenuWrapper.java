package com.zhuge.filter.filterbar.filterwrapper;

import android.content.Context;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import com.zhuge.filter.FilterBar;
import com.zhuge.filter.R;
import com.zhuge.filter.filterbar.menuitemview.BaseMenuItemView;
import com.zhuge.filter.filterbar.menuitemview.ListMenuItemView;
import com.zhuge.filter.filterbar.menuitemview.entity.MenuItem;
import com.zhuge.filter.filterbar.menuitemview.listener.OnMenuItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 封装filtermenu的父类
 */
public abstract class BaseMenuWrapper {
    private Context context;
    protected List<BaseMenuItemView> menuItems;
    private int lastPos = -1;//保存上一次的位置 防止重复绘制

    public BaseMenuWrapper(Context context) {
        this.context = context;
        menuItems = new ArrayList<>();
    }

    public void showMenu(int currentShowPos, FrameLayout frameLayout, FilterBar filterBar, boolean isNeedAnim) {
        if (filterBar.getMapFilter() != null && filterBar.getMapFilter().containsKey(currentShowPos) && isNeedAnim) {
            menuItems.get(currentShowPos).setCurrentFilterData(filterBar.getMapFilter().get(currentShowPos));
        } else if(isNeedAnim){
            menuItems.get(currentShowPos).setCurrentFilterData(null);
        }
        if(!isNeedAnim) {//直接覆盖上次显示的东西
            frameLayout.removeViewAt(frameLayout.getChildCount()-1);
            View menuView = menuItems.get(currentShowPos).getMenuItemView();
            frameLayout.addView(menuView);
            lastPos = currentShowPos;
        } else if (lastPos == currentShowPos) {
            frameLayout.getChildAt(0).setAnimation(AnimationUtils
                    .loadAnimation(context, R.anim.dd_menu_in));
        } else {
            frameLayout.removeAllViews();
            View menuView = menuItems.get(currentShowPos).getMenuItemView();
            menuView.setAnimation(AnimationUtils
                    .loadAnimation(context, R.anim.dd_menu_in));
            frameLayout.addView(menuView);
            lastPos = currentShowPos;
        }
    }

    /**
     * 设置选中菜单监听器  由Activity进行处理
     */

    public void setTabSelectListener(OnMenuItemClickListener tabSelectListener) {
        for (BaseMenuItemView menuItem : menuItems) {
            menuItem.setOnMenuItemClickListener(tabSelectListener);
        }
    }

    public void setData(int tab, List<MenuItem> dataList) {
        menuItems.get(tab).setOrRefreshListAdapter(dataList);
    }

    /**
     * 设置数据 tabIndex 区域一级index
     * tabTitle 区域一级title
     */

    public void setData(int tab, List<MenuItem> dataList, int tabIndex, String tabTitle) {
        menuItems.get(tab).setOrRefreshListAdapter(dataList, tabIndex, tabTitle);
    }

    /**
     * 设置多选
     * 只针对列表样式
     */

    public void setMultiPly(int tab) {
        menuItems.get(tab).setMultyChoice(true);
    }
}
