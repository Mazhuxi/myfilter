package com.zhuge.filter.filterbar.menuitemview.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhuge.filter.R;
import com.zhuge.filter.filterbar.menuitemview.entity.MenuItem;

import java.util.List;

/**
 * 列表类型适配器 区域 单价 户型 排序
 */
public class ListMenuAdapter extends BaseQuickAdapter<MenuItem, BaseViewHolder> {
    public ListMenuAdapter(int layoutResId, @Nullable List<MenuItem> data) {
        super(layoutResId, data);
    }

    private boolean isMultyChoice;//是否多选

    @Override
    protected void convert(BaseViewHolder helper, MenuItem item) {
        helper.setText(R.id.tv_item, item.getItemTitle());
        if (item.isChecked()) {
            helper.setTextColor(R.id.tv_item, Color.RED);
            if(isMultyChoice && helper.getAdapterPosition() != 0) {
                helper.setImageResource(R.id.iv_check, R.mipmap.fuxuan);
            }
        } else {
            helper.setTextColor(R.id.tv_item, Color.BLACK);
            if(isMultyChoice && helper.getAdapterPosition() != 0) {
                helper.setImageResource(R.id.iv_check, R.mipmap.fuxuan_default);
            }
        }

        if(isMultyChoice && helper.getAdapterPosition() != 0) {
            helper.setGone(R.id.iv_check, true);
        } else {
            helper.setGone(R.id.iv_check, false);
        }
    }

    public void setMultyChoice(boolean multyChoice) {
        isMultyChoice = multyChoice;
    }
}
