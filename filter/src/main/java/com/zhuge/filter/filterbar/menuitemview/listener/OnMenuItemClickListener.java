package com.zhuge.filter.filterbar.menuitemview.listener;

import com.zhuge.filter.filterbar.menuitemview.entity.MenuItem;

import java.util.List;

/**
 * item点击回调
 */
public interface OnMenuItemClickListener {
    void onSelectMenuClick(List<MenuItem> seleMenus, int tabIndex);
}
