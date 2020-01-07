package com.zhuge.filter.filterbar.menuitemview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhuge.filter.R;
import com.zhuge.filter.filterbar.menuitemview.adapter.ListMenuAdapter;
import com.zhuge.filter.filterbar.menuitemview.entity.MenuItem;
import com.zhuge.filter.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 长列表Item 单价 户型 总价 排序
 */
public class ListMenuItemView extends BaseMenuItemView {
    private boolean isShowBottom;//是否显示底部控件 最大值-最小值-确定
    private String maxHint;//最大值
    private String minHint;//最小值
    private String unit;//单位
    private boolean isHideUnit;//是否隐藏单位
    private boolean isClear;//是否显示清空条件按钮
    private RecyclerView rvItem;
    private EditText etMin;
    private EditText etMax;
    private TextView tvOk;
    private TextView tvClear;
    private RelativeLayout rlBottomLayout;

    private boolean isMultyChoice;//是否多选

    public ListMenuItemView(Context context, int layoutId, int tabIndex) {
        super(context, layoutId, tabIndex);
    }

    @Override
    public void initMenuView() {
        viewRoot.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Utils.dpTpPx(context, 320)));
    }

    private void setRecyclerAndBottom() {
        rvItem = viewRoot.findViewById(R.id.rv_item);
        rlBottomLayout = viewRoot.findViewById(R.id.rl_bottom_layout);
        rvItem.setLayoutManager(new LinearLayoutManager(context));

        if (isShowBottom) {
            rlBottomLayout.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(minHint)) {//底部布局包含最大最小值
                etMin = viewRoot.findViewById(R.id.et_min);
                etMax = viewRoot.findViewById(R.id.et_max);
                tvOk = viewRoot.findViewById(R.id.tv_ok);
                TextView tvUnit = viewRoot.findViewById(R.id.tvUnit);
                etMin.setHint(minHint);
                etMax.setHint(maxHint);
                tvUnit.setText(unit);
                if (isHideUnit) {
                    tvUnit.setVisibility(View.GONE);
                }
                tvOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String strMin = etMin.getText().toString();
                        String strMax = etMax.getText().toString();
                        if (!TextUtils.isEmpty(strMin) && !TextUtils.isEmpty(strMax) && rvItem.getAdapter() != null) {
                            handleMenuSelect((ListMenuAdapter) rvItem.getAdapter(), 0);
                            if (onMenuItemClickListener != null) {
                                List<MenuItem> list = new ArrayList<>();
                                list.add(new MenuItem("自定义", strMin + "-" + strMax + unit, false));
                                onMenuItemClickListener.onSelectMenuClick(list, tabIndex);
                            }
                        } else {
                            if (isMultyChoice) {
                                setMultyChoice();
                            } else {
                                Toast.makeText(context, "输入有误", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            } else if (isMultyChoice) {
                setMultiplyOkListener();
            }
        } else {
            if (rlBottomLayout != null) {
                rlBottomLayout.setVisibility(View.GONE);
            }
        }
        setOrRefreshListAdapter(new ArrayList<MenuItem>());
    }

    /**
     * 设置多选的确定监听
     */

    private void setMultiplyOkListener() {
        tvOk = viewRoot.findViewById(R.id.tv_ok);
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //将所有的选中元素回调回去
                setMultyChoice();
            }
        });

        tvClear = viewRoot.findViewById(R.id.tv_clear);
        tvClear.setVisibility(isClear ? View.VISIBLE : View.GONE);
        tvClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //清空操作
                if (rvItem.getAdapter() != null) {
                    ListMenuAdapter listMenuAdapter = (ListMenuAdapter) rvItem.getAdapter();
                    for (MenuItem datum : listMenuAdapter.getData()) {
                        if (datum.getItemId().equals("-1")) {
                            datum.setChecked(true);
                        } else {
                            datum.setChecked(false);
                        }
                    }
                    listMenuAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    /**
     * 设置多选选中的元素
     */

    private void setMultyChoice() {
        if (rvItem.getAdapter() != null) {
            ListMenuAdapter listMenuAdapter = (ListMenuAdapter) rvItem.getAdapter();
            List<MenuItem> list = new ArrayList<>();
            for (MenuItem datum : listMenuAdapter.getData()) {
                if (datum.isChecked()) {
                    list.add(datum);
                }
            }
            if (onMenuItemClickListener != null) {
                onMenuItemClickListener.onSelectMenuClick(list, tabIndex);
            }
        }
    }


    /**
     * 设置或更新Adapter
     */

    @Override
    public void setOrRefreshListAdapter(List<MenuItem> menuItems) {
        ListMenuAdapter listMenuAdapter;
        if (menuItems.isEmpty()) {
            listMenuAdapter = new ListMenuAdapter(itemLayout == 0 ? R.layout.item_single : itemLayout, menuItems);
            rvItem.setAdapter(listMenuAdapter);
            setAdapterItemClickListener(listMenuAdapter);
        } else if (rvItem.getAdapter() != null) {
            listMenuAdapter = (ListMenuAdapter) rvItem.getAdapter();
            if (listMenuAdapter != null) {
                listMenuAdapter.setMultyChoice(isMultyChoice);
                listMenuAdapter.setNewData(menuItems);
            }
        }
    }

    /**
     * 设置item点击事件
     */

    private void setAdapterItemClickListener(final ListMenuAdapter listMenuAdapter) {
        listMenuAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (etMin != null) {
                    etMin.setText("");
                    etMax.setText("");
                }
                handleMenuSelect(listMenuAdapter, position);
                if (!isMultyChoice) {
                    MenuItem menuItem = listMenuAdapter.getData().get(position);
                    List<MenuItem> list = new ArrayList<>();
                    list.add(menuItem);
                    if (onMenuItemClickListener != null) {
                        onMenuItemClickListener.onSelectMenuClick(list, tabIndex);
                    }
                }
            }
        });
    }

    /**
     * 处理item点击事件
     */

    private void handleMenuSelect(ListMenuAdapter areaMenuAdapter, int position) {
        MenuItem menuItem = areaMenuAdapter.getData().get(position);
        if (!menuItem.isChecked()) {
            setItemSelect(areaMenuAdapter, position);
        } else if (isMultyChoice) {
            setItemUnSelect(areaMenuAdapter, position);
        }
    }

    /**
     * 设置该item未选中
     */

    private void setItemUnSelect(ListMenuAdapter areaMenuAdapter, int position) {
        areaMenuAdapter.getData().get(position).setChecked(false);
        boolean isNeedNolimit = true;//是否需要选中不限
        for (MenuItem datum : areaMenuAdapter.getData()) {
            if (datum.isChecked()) {
                isNeedNolimit = false;
                break;
            }
        }

        if (isNeedNolimit) {
            areaMenuAdapter.getData().get(0).setChecked(true);
        }
        areaMenuAdapter.notifyDataSetChanged();
    }

    /**
     * 设置某item选中其他不选中
     */

    private void setItemSelect(ListMenuAdapter areaMenuAdapter, int selectPos) {
        if (!isMultyChoice || selectPos == 0) {
            for (int i = 0; i < areaMenuAdapter.getItemCount(); i++) {
                areaMenuAdapter.getData().get(i).setChecked(i == selectPos);
            }
        } else {
            areaMenuAdapter.getData().get(0).setChecked(false);
            areaMenuAdapter.getData().get(selectPos).setChecked(true);
        }
        areaMenuAdapter.notifyDataSetChanged();
    }


    /**
     * 设置数据回显
     */
    @Override
    public void setCurrentFilterData(List<MenuItem> menuItems) {
        ListMenuAdapter menuAdapter = (ListMenuAdapter) rvItem.getAdapter();
        if (menuAdapter == null) {
            return;
        }

        List<MenuItem> data = menuAdapter.getData();

        if (menuItems == null || menuItems.size() == 0) {
            for (MenuItem datum : data) {
                if ("-1".equals(datum.getItemId())) {
                    datum.setChecked(true);
                } else {
                    datum.setChecked(false);
                }
            }
            menuAdapter.notifyDataSetChanged();
            return;
        }

        List<String> listItems = new ArrayList<>();

        for (MenuItem menuItem : menuItems) {
            listItems.add(menuItem.getItemId());
        }

        int pos = 0;

        for (int i=0;i<data.size();i++) {
            MenuItem datum = data.get(i);
            if (listItems.contains(datum.getItemId())) {
                if(pos == 0) {
                    pos = i;
                }
                datum.setChecked(true);
            } else {
                datum.setChecked(false);
            }
        }
        rvItem.smoothScrollToPosition(pos);
        menuAdapter.notifyDataSetChanged();
    }

    public ListMenuItemView setShowBottom(boolean showBottom) {
        isShowBottom = showBottom;
        return this;
    }

    public ListMenuItemView setMaxHint(String maxHint) {
        this.maxHint = maxHint;
        return this;
    }

    public ListMenuItemView setMinHint(String minHint) {
        this.minHint = minHint;
        return this;
    }

    public ListMenuItemView setUnit(String unit) {
        this.unit = unit;
        return this;
    }

    public ListMenuItemView setHideUnit(boolean isHideUnit) {
        this.isHideUnit = isHideUnit;
        return this;
    }

    public ListMenuItemView setClear(boolean isClear) {
        this.isClear = isClear;
        return this;
    }

    public ListMenuItemView get() {
        setRecyclerAndBottom();
        return this;
    }

    @Override
    public void setMultyChoice(boolean isMultyChoice) {
        this.isMultyChoice = isMultyChoice;
        if (tvOk == null) {
            rlBottomLayout.setVisibility(View.VISIBLE);
            setMultiplyOkListener();
        }
    }
}
