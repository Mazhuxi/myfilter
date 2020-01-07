package com.zhuge.filter.filterbar.menuitemview.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhuge.filter.R;
import com.zhuge.filter.filterbar.menuitemview.entity.MenuItem;

import java.util.List;

/**
 * 更多子项目
 */
public class MoreChildMenuAdapter extends BaseQuickAdapter<MenuItem, BaseViewHolder> {
    public MoreChildMenuAdapter(int layoutResId, @Nullable List<MenuItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MenuItem item) {
        TextView tvChildTitle = helper.getView(R.id.tv_child_title);
        tvChildTitle.setSelected(item.isChecked());
        helper.setText(R.id.tv_child_title, item.getItemTitle());
    }

}
