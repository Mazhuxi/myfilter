package com.zhuge.filter.filterbar.menuitemview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhuge.filter.R;
import com.zhuge.filter.filterbar.menuitemview.adapter.ListMenuAdapter;
import com.zhuge.filter.filterbar.menuitemview.entity.MenuItem;
import com.zhuge.filter.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 区域Item
 */

public class AreaMenuItemView extends BaseMenuItemView {
    private RecyclerView[] rvAreas;
    private LinearLayout llBottom;

    private static final int AREA_FIRST = 0;//第一级
    private static final int AREA_SECOND = 1;//第二级
    private static final int AREA_THIRD = 2;//第三级

    public AreaMenuItemView(Context context, int layoutId, int tabIndex) {
        super(context, layoutId, tabIndex);
    }

    @Override
    void initMenuView() {
        viewRoot.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Utils.dpTpPx(context, 320)));
        RecyclerView rvAreaOne = viewRoot.findViewById(R.id.rv_area_one);
        RecyclerView rvAreaTwo = viewRoot.findViewById(R.id.rv_area_two);
        RecyclerView rvAreaThree = viewRoot.findViewById(R.id.rv_area_three);
        llBottom = viewRoot.findViewById(R.id.ll_bottom);
        TextView tvClear = viewRoot.findViewById(R.id.tv_clear);
        TextView tvOk = viewRoot.findViewById(R.id.tv_ok);
        setClearAndOkListener(tvClear, tvOk);
        setRecyclerView(rvAreaOne, rvAreaTwo, rvAreaThree);
    }

    private void setRecyclerView(RecyclerView... rvAreas) {
        this.rvAreas = rvAreas;
        for (int i = 0; i < rvAreas.length; i++) {
            RecyclerView rvArea = rvAreas[i];
            rvArea.setLayoutManager(new LinearLayoutManager(context));
            setOrRefreshAreaAdapter(i, new ArrayList<MenuItem>());
        }
    }

    private void setOrRefreshAreaAdapter(int areaId, List<MenuItem> menuItems) {
        ListMenuAdapter areaMenuAdapter;
        if (menuItems.isEmpty()) {
            areaMenuAdapter = new ListMenuAdapter(itemLayout == 0 ? R.layout.item_single : itemLayout, menuItems);
            rvAreas[areaId].setAdapter(areaMenuAdapter);
            setAdapterItemClickListener(areaId, areaMenuAdapter);
        } else if (rvAreas[areaId].getAdapter() != null) {
            areaMenuAdapter = (ListMenuAdapter) rvAreas[areaId].getAdapter();
            if (areaMenuAdapter != null) {
                areaMenuAdapter.setNewData(menuItems);
            }
        }
    }

    /**
     * 设置item点击事件
     */

    private void setAdapterItemClickListener(final int areaId, final ListMenuAdapter areaMenuAdapter) {
        areaMenuAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                handleMenuSelect(areaMenuAdapter, position);
                if (areaId == AREA_FIRST) {//第一级
                    if (rvAreas[AREA_THIRD].isShown()) {
                        rvAreas[AREA_THIRD].setVisibility(View.GONE);
                        resetLastStatus(AREA_THIRD);
                    }
                    resetLastStatus(AREA_SECOND);
                    setOrRefreshAreaAdapter(AREA_SECOND, areaMenuAdapter.getData().get(position).getChildMenus());
                } else if (areaId == AREA_SECOND) {//第二级
                    if (position == 0) {
                        resetLastStatus(AREA_THIRD);
                        rvAreas[AREA_THIRD].setVisibility(View.GONE);
                        selectItem(areaMenuAdapter, position);
                    } else {
                        List<MenuItem> childMenus = areaMenuAdapter.getData().get(position).getChildMenus();
                        if(childMenus == null || childMenus.size() == 0) {
                            selectItem(areaMenuAdapter, position);
                        } else {
                            rvAreas[AREA_THIRD].setVisibility(View.VISIBLE);
                            resetLastStatus(AREA_THIRD);
                            if (areaMenuAdapter.getData().get(position).isChildMulty() && rvAreas[AREA_THIRD].getAdapter() != null) {
                                ((ListMenuAdapter) (rvAreas[AREA_THIRD].getAdapter())).setMultyChoice(true);
                            }
                            setOrRefreshAreaAdapter(AREA_THIRD, areaMenuAdapter.getData().get(position).getChildMenus());
                        }
                    }
                } else {//选中的事件回调 //第三级
                    if (position == 0) {
                        rvAreas[AREA_THIRD].setVisibility(View.GONE);
                    }
                    MenuItem parentItem = areaMenuAdapter.getData().get(position).getParentItem();
                    if (parentItem != null && !areaMenuAdapter.getData().get(position).getItemId().equals("-1") && parentItem.isChildMulty()) {
                        return;
                    }
                    selectItem(areaMenuAdapter, position);
                }
            }
        });
    }

    private void setClearAndOkListener(TextView tvClear, TextView tvOk) {
        tvClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //重置条件
                if(rvAreas[AREA_THIRD].isShown()) {
                    resetLastStatus(AREA_THIRD);
                    rvAreas[AREA_THIRD].setVisibility(View.GONE);
                }

                resetLastStatus(AREA_SECOND);
                resetLastStatus(AREA_FIRST);
                ListMenuAdapter firstAdapter = ((ListMenuAdapter)(rvAreas[AREA_FIRST].getAdapter()));
                if(firstAdapter != null) {
                    setOrRefreshAreaAdapter(AREA_SECOND, firstAdapter.getData().get(0).getChildMenus());
                }
            }
        });

        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onMenuItemClickListener == null) {
                    return;
                }
                //确定
                if(rvAreas[AREA_THIRD].isShown()) {//选中第三级列表了
                    ListMenuAdapter adapter = ((ListMenuAdapter)(rvAreas[AREA_THIRD].getAdapter()));
                    if(adapter == null) {
                        return;
                    }
                    List<MenuItem> data = adapter.getData();
                    List<MenuItem> list = new ArrayList<>();
                    for (MenuItem datum : data) {
                        if(datum.isChecked()) {
                            list.add(datum);
                        }
                    }
                    onMenuItemClickListener.onSelectMenuClick(list, tabIndex);
                } else {//选中二级列表
                    if(rvAreas[AREA_SECOND].getAdapter() != null) {
                        selectItem(((ListMenuAdapter)(rvAreas[AREA_SECOND].getAdapter())), 0);
                    }
                }
            }
        });
    }

    /**
     * 选中某个item
     */

    private void selectItem(ListMenuAdapter areaMenuAdapter, int position) {
        MenuItem menuItem = areaMenuAdapter.getData().get(position);
        List<MenuItem> list = new ArrayList<>();
        list.add(menuItem);
        onMenuItemClickListener.onSelectMenuClick(list, tabIndex);
    }

    /**
     * 还原上一次选择的操作 不限选中
     */

    private void resetLastStatus(int areaId) {
        ListMenuAdapter areaMenuAdapter = (ListMenuAdapter) rvAreas[areaId].getAdapter();
        if (areaMenuAdapter == null) {
            return;
        }
        if (areaMenuAdapter.getData().size() == 0) {
            return;
        }
        setItemSelect(areaMenuAdapter, 0);
    }

    /**
     * 处理item点击事件
     */

    private void handleMenuSelect(ListMenuAdapter areaMenuAdapter, int position) {
        MenuItem menuItem = areaMenuAdapter.getData().get(position);
        if (menuItem.getParentItem() != null && menuItem.getParentItem().isChildMulty()) {//多选
            if (!menuItem.isChecked() && menuItem.getItemId().equals("-1")) {//选中不限
                setItemSelect(areaMenuAdapter, position);
            } else if (!menuItem.isChecked()) {//选中其他
                areaMenuAdapter.getData().get(0).setChecked(false);
                areaMenuAdapter.getData().get(position).setChecked(true);
                areaMenuAdapter.notifyDataSetChanged();
            } else if (!menuItem.getItemId().equals("-1")) {//取消选中
                areaMenuAdapter.getData().get(position).setChecked(false);
                setCancelSelectItem(areaMenuAdapter);
            }
        } else {//单选
            if (!menuItem.isChecked()) {
                setItemSelect(areaMenuAdapter, position);
            }
        }
    }

    /**
     * 多选情况取消选中
     */

    private void setCancelSelectItem(ListMenuAdapter areaMenuAdapter) {
        boolean isNeedNolimit = true;
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
        for (int i = 0; i < areaMenuAdapter.getItemCount(); i++) {
            areaMenuAdapter.getData().get(i).setChecked(i == selectPos);
        }
        areaMenuAdapter.notifyDataSetChanged();
    }

    /**
     * 需要特别处理下第一级页面
     */

    @Override
    public void setOrRefreshListAdapter(List<MenuItem> menuChildItems, int index, String fistTitle) {
        ListMenuAdapter areaMenuAdapter = (ListMenuAdapter) rvAreas[0].getAdapter();
        if (areaMenuAdapter != null) {
            MenuItem fistMenu;
            if (areaMenuAdapter.getItemCount() == 0 || index == 0) {
                fistMenu = new MenuItem(String.valueOf(index), fistTitle, true);
            } else {
                fistMenu = new MenuItem(String.valueOf(index), fistTitle, false);
            }
            fistMenu.setChildMenus(menuChildItems);

            if (areaMenuAdapter.getItemCount() < index) {//当前没有一条数据 或数量少于index
                areaMenuAdapter.getData().add(areaMenuAdapter.getItemCount(), fistMenu);
            } else {
                areaMenuAdapter.getData().add(index, fistMenu);
            }
        }
        //默认显示第一个位置的数据要刷新列表 或者当前只有一组数据的时候
        if (index == 0 || (areaMenuAdapter != null && areaMenuAdapter.getItemCount() == 1)) {
            setOrRefreshAreaAdapter(AREA_SECOND, menuChildItems);
        }
    }

    /**
     * 设置数据回显
     */
    @Override
    public void setCurrentFilterData(List<MenuItem> menuItems) {
        if (menuItems == null || menuItems.size() == 0) {
            rvAreas[AREA_THIRD].setVisibility(View.GONE);
            ListMenuAdapter areaMenuAdapter = (ListMenuAdapter) rvAreas[AREA_FIRST].getAdapter();
            for (int i = 0; i < areaMenuAdapter.getData().size(); i++) {
                if (i == 0) {
                    areaMenuAdapter.getData().get(i).setChecked(true);
                    setOrRefreshAreaAdapter(AREA_SECOND, areaMenuAdapter.getData().get(i).getChildMenus());
                } else {
                    areaMenuAdapter.getData().get(i).setChecked(false);
                }
            }
            areaMenuAdapter.notifyDataSetChanged();
            ListMenuAdapter areaMenu2Adapter = (ListMenuAdapter) rvAreas[AREA_SECOND].getAdapter();
            if(areaMenu2Adapter == null || areaMenu2Adapter.getData().isEmpty()) {
                return;
            }
            for (int i = 0; i < areaMenu2Adapter.getData().size(); i++) {
                if (areaMenu2Adapter.getData().get(i).getItemId().equals("-1")) {
                    areaMenu2Adapter.getData().get(i).setChecked(true);
                } else {
                    areaMenu2Adapter.getData().get(i).setChecked(false);
                }
            }
            rvAreas[AREA_SECOND].smoothScrollToPosition(0);
            return;
        }
        String parentTitle = menuItems.get(0).getParentTitle();
        ListMenuAdapter areaMenuAdapter = (ListMenuAdapter) rvAreas[AREA_FIRST].getAdapter();
        if (areaMenuAdapter == null) {
            return;
        }
        if (TextUtils.isEmpty(parentTitle)) {
            return;
        }
        //是否展开第三个的view
        boolean isExpandThree = true;
        if (menuItems.get(0).getParentItem() == null) {
            //只有两级列表 -- 第二级列表选择了不限
            isExpandThree = false;
        } else {
            parentTitle = menuItems.get(0).getParentItem().getParentTitle();
        }
        //第一级列表选中
        for (int i = 0; i < areaMenuAdapter.getData().size(); i++) {
            if (areaMenuAdapter.getData().get(i).getItemTitle().equals(parentTitle)) {
                areaMenuAdapter.getData().get(i).setChecked(true);
                setOrRefreshAreaAdapter(AREA_SECOND, areaMenuAdapter.getData().get(i).getChildMenus());
            } else {
                areaMenuAdapter.getData().get(i).setChecked(false);
            }
        }
        areaMenuAdapter.notifyDataSetChanged();
        if (isExpandThree) {
            rvAreas[AREA_THIRD].setVisibility(View.VISIBLE);
            //第二级列表定位
            MenuItem parentItem = menuItems.get(0).getParentItem();
            ListMenuAdapter areaMenu2Adapter = (ListMenuAdapter) rvAreas[AREA_SECOND].getAdapter();
            int area2Pos = 0;
            if(areaMenu2Adapter == null) {
                return;
            }
            for (int i = 0; i < areaMenu2Adapter.getData().size(); i++) {
                if (areaMenu2Adapter.getData().get(i).getItemId().equals(parentItem.getItemId())) {
                    areaMenu2Adapter.getData().get(i).setChecked(true);
                    area2Pos = i;
                } else {
                    areaMenu2Adapter.getData().get(i).setChecked(false);
                }
            }
            rvAreas[AREA_SECOND].smoothScrollToPosition(area2Pos);
            setOrRefreshAreaAdapter(AREA_THIRD, parentItem.getChildMenus());
            //第三级列表定位
            ListMenuAdapter areaMenu3Adapter = (ListMenuAdapter) rvAreas[AREA_THIRD].getAdapter();
            if (areaMenu3Adapter == null) {
                return;
            }
            if (parentItem.isChildMulty()) {
                areaMenu3Adapter.setMultyChoice(true);
            }
            List<String> listChildId = new ArrayList<>();
            for (MenuItem menuItem : menuItems) {
                listChildId.add(menuItem.getItemId());
            }
            int area3Pos = -1;
            for (int i = 0; i < areaMenu3Adapter.getData().size(); i++) {
                if (listChildId.contains(areaMenu3Adapter.getData().get(i).getItemId())) {
                    if (area3Pos == -1) {
                        area3Pos = i;
                    }
                    areaMenu3Adapter.getData().get(i).setChecked(true);
                } else {
                    areaMenu3Adapter.getData().get(i).setChecked(false);
                }
            }
            rvAreas[AREA_THIRD].smoothScrollToPosition(area3Pos);
        } else {
            rvAreas[AREA_THIRD].setVisibility(View.GONE);
            ListMenuAdapter areaMenu2Adapter = (ListMenuAdapter) rvAreas[AREA_SECOND].getAdapter();
            int area2Pos = 0;
            List<String> listItems = new ArrayList<>();
            for (MenuItem menuItem : menuItems) {
                listItems.add(menuItem.getItemId());
            }
            if(areaMenu2Adapter == null || areaMenu2Adapter.getData().isEmpty()) {
                return;
            }
            for (int i = 0; i < areaMenu2Adapter.getData().size(); i++) {
                if (listItems.contains(areaMenu2Adapter.getData().get(i).getItemId())) {
                    areaMenu2Adapter.getData().get(i).setChecked(true);
                    if(area2Pos == 0) {
                        area2Pos = i;
                    }
                } else {
                    areaMenu2Adapter.getData().get(i).setChecked(false);
                }
            }
            rvAreas[AREA_SECOND].smoothScrollToPosition(area2Pos);
        }
    }

    @Override
    public void setMultyChoice(boolean isMultyChoice) {
        llBottom.setVisibility(View.VISIBLE);
    }
}
