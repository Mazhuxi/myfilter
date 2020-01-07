package com.zhuge.filter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhuge.filter.filterbar.filterwrapper.BaseMenuWrapper;
import com.zhuge.filter.filterbar.menuitemview.entity.MenuItem;
import com.zhuge.filter.utils.Utils;

import java.util.HashMap;
import java.util.List;

/**
 * 筛选栏
 */
public class FilterBar extends LinearLayout {
    private String[] tabTexts;
    private FrameLayout flFilter;//需要在布局中设置 传给filterBar
    private LinearLayout tabLayout;
    private BaseMenuWrapper menuWrapper;
    private Context context;
    //tab选中颜色
    private int textSelectedColor = 0xff890c85;
    //tab未选中颜色
    private int textUnselectedColor = 0xff111111;
    //tab字体大小
    private int menuTextSize = 14;
    //tab选中图标
    private int menuSelectedIcon = R.mipmap.bg_cms_shaixuan_red;
    //tab未选中图标
    private int menuUnselectedIcon = R.mipmap.bg_cms_shaixuan;
    private int menuLastIcon = 0;
    private boolean tabIconBottom;//tabIcon底对齐

    private int currentClickPos = -1;//当前点击的tab
    @SuppressLint("UseSparseArrays")
    private HashMap<Integer, List<MenuItem>> mapFilter = new HashMap<>();//保存一份数据用于回显
    public FilterBar(Context context) {
        this(context, null);
    }

    public FilterBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FilterBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        setOrientation(VERTICAL);
        setAttributeSet(attrs);
        setDefaultParams();
    }

    private void setAttributeSet(AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FileBar);
        textSelectedColor = a
                .getColor(R.styleable.FileBar_tabselectColor, textSelectedColor);
        textUnselectedColor = a
                .getColor(R.styleable.FileBar_tabUnselectColor, textUnselectedColor);
        menuTextSize = a
                .getDimensionPixelSize(R.styleable.FileBar_tabTextSize, menuTextSize);
        menuSelectedIcon = a
                .getResourceId(R.styleable.FileBar_tabSelectedIcon, menuSelectedIcon);
        menuUnselectedIcon = a
                .getResourceId(R.styleable.FileBar_tabUnselectedIcon, menuUnselectedIcon);
        tabIconBottom = a
                .getBoolean(R.styleable.FileBar_tabInconBottom, tabIconBottom);
        menuLastIcon = a
                .getResourceId(R.styleable.FileBar_tabLastIcon, menuLastIcon);
        a.recycle();
    }

    /**
     * 设置默认的参数
     */

    private void setDefaultParams() {

    }

    /**
     * 设置tab文字
     */

    public FilterBar setTabTexts(String[] tabTexts) {
        this.tabTexts = tabTexts;
        return this;
    }

    public FilterBar setFilterFrame(FrameLayout flFilter) {
        this.flFilter = flFilter;
        return this;
    }

    public FilterBar setMenuWrapper(BaseMenuWrapper menuWrapper) {
        this.menuWrapper = menuWrapper;
        return this;
    }

    /**
     * 设置tab布局
     */
    public void setTabLayout() {
        tabLayout = new LinearLayout(context);
        tabLayout.setOrientation(HORIZONTAL);
        for (int i = 0; i < tabTexts.length; i++) {
            RelativeLayout rlTab = new RelativeLayout(context);
            TextView tvTab = new TextView(context);
            tvTab.setMaxLines(1);
            tvTab.setEllipsize(TextUtils.TruncateAt.END);
            tvTab.setText(tabTexts[i]);
            tvTab.setTextSize(TypedValue.COMPLEX_UNIT_PX, menuTextSize);
            tvTab.setPadding(0, Utils.dpTpPx(context, 6), 0, Utils.dpTpPx(context, 6));
            rlTab.setGravity(Gravity.CENTER);
            rlTab.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1.0f));
            setTabIcon(tvTab, false);
            rlTab.addView(tvTab);
            tabLayout.addView(rlTab);
        }

        if (menuLastIcon != 0) {
            addLastIcon();
        }

        addView(tabLayout);
        setTabClicker();
        setFilterClicker();
    }

    private void addLastIcon() {
        final RelativeLayout rlTab = new RelativeLayout(getContext());
        final ImageView imageView = new ImageView(getContext());
        imageView.setPadding(Utils.dpTpPx(context, 5), 0, Utils.dpTpPx(context, 15), 0);
        imageView.setImageResource(menuLastIcon);
        RelativeLayout.LayoutParams imgParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        imgParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        rlTab.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
        rlTab.addView(imageView, imgParams);
        tabLayout.addView(rlTab);
    }

    private void setFilterClicker() {
        flFilter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                closeOrShowMenu(true, false);
                currentClickPos = -1;
            }
        });
    }

    private void setTabClicker() {
        for (int i = 0; i < tabLayout.getChildCount(); i++) {
            final int finalI = i;
            tabLayout.getChildAt(i).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentClickPos == finalI) {
                        closeMenu();
                    } else if (currentClickPos == -1) {
                        currentClickPos = finalI;
                        closeOrShowMenu(false, true);
                    } else {//之前已经弹出 只是更改view
                        setTabText(true);
                        currentClickPos = finalI;
                        closeOrShowMenu(false, false);
                    }
                }
            });
        }
    }

    /**
     * 设置tab的状态
     */

    private void setTabText(boolean isClose) {
        View childAt = ((RelativeLayout) tabLayout.getChildAt(currentClickPos)).getChildAt(0);
        if (childAt instanceof TextView) {
            TextView tvTab = (TextView) childAt;
            setTabIcon(tvTab, !isClose);
        }
    }

    private void setTabText(int tab) {
        View childAt = ((RelativeLayout) tabLayout.getChildAt(tab)).getChildAt(0);
        if (childAt instanceof TextView) {
            TextView tvTab = (TextView) childAt;
            List<MenuItem> list = mapFilter.get(tab);
            if (list == null || list.size() == 0) {
                tvTab.setText(tabTexts[tab]);
                tvTab.setTextColor(textUnselectedColor);
                return;
            }
            if(list.size() == 1 && !list.get(0).getItemId().equals("-1")) {
                tvTab.setText(list.get(0).getItemTitle());
                tvTab.setTextColor(textSelectedColor);
            } else if(list.size() > 1) {
                int allNum = 0;
                for (MenuItem menuItem : list) {
                    if(menuItem.getItemId().equals("-1")) {
                        continue;
                    }
                    allNum++;
                }
                tvTab.setText(tabTexts[tab] + "(" + allNum + ")");
                tvTab.setTextColor(textSelectedColor);
            } else {
                tvTab.setText(tabTexts[tab]);
                tvTab.setTextColor(textUnselectedColor);
            }
        }
    }

    /**
     * 关闭菜单
     */

    public void closeMenu() {
        closeOrShowMenu(true, false);
        currentClickPos = -1;
    }

    /**
     * 关闭或显示menu
     */

    public void closeOrShowMenu(final boolean isClose, boolean isNeedAlpha) {
        setTabText(isClose);
        if (!isClose) {
            menuWrapper.showMenu(currentClickPos, flFilter, this, isNeedAlpha);
        }
        flFilter.setVisibility(isClose ? View.GONE : View.VISIBLE);
    }

    private void setTabIcon(TextView tvTab, boolean isSelect) {
        if (tabIconBottom) {
            Drawable drawable = getResources()
                    .getDrawable(isSelect ? menuSelectedIcon : menuUnselectedIcon);
            drawable.setBounds(0, Utils.dpTpPx(context, 6), Utils.dpTpPx(context, 5), Utils.dpTpPx(context, 11));
            tvTab.setCompoundDrawables(null, null, drawable, null);
        } else {
            tvTab.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources()
                    .getDrawable(isSelect ? menuSelectedIcon : menuUnselectedIcon), null);
        }
    }

    public HashMap<Integer, List<MenuItem>> getMapFilter() {
        return mapFilter;
    }

    public void setMapFilter(HashMap<Integer, List<MenuItem>> mapFilter, int tab) {
        this.mapFilter = mapFilter;
        setTabText(tab);
    }

}
