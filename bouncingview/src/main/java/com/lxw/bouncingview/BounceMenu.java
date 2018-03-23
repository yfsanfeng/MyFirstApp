package com.lxw.bouncingview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;

/**
 * description... //TODO
 *
 * @author lsw
 * @version 1.0, 2017/4/16
 * @see //TODO
 * @since JDK 1.8
 */

public class BounceMenu {
    private BouncingView mBouncingView;
    private RecyclerView mRecyclerView;
    //在sample布局中的FrameLayout
    private ViewGroup mParentViewGroup;
    //要添加进来的View
    private View mBounceMenu;

    /**
     * @param context 上下文
     * @param view    该View为来自Activity或者Fragment中的View,通过此View找到帧布局
     * @param resId   要添加进来的Menu菜单的布局，
     */
    private BounceMenu(Context context, View view, int resId, final RecyclerView.Adapter adapter) {
        //找到FrameLayout
        mParentViewGroup = findParentViewGroup(context, view);
        //渲染布局
        mBounceMenu = LayoutInflater.from(context).inflate(resId, null, false);
        //查找BounceView与RecyclerView
        mBouncingView = (BouncingView) mBounceMenu.findViewById(R.id.bouncing_view);

        mRecyclerView = (RecyclerView) mBounceMenu.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mBouncingView.setListener(new BouncingView.onAnimatorEndListener() {
            @Override
            public void onFinish() {
                mRecyclerView.setAdapter(adapter);
                mRecyclerView.scheduleLayoutAnimation();
            }
        });
    }

    /**
     *  找到帧布局
     * @param context
     * @param view
     * @return
     */
    private ViewGroup findParentViewGroup(Context context, View view) {
        //通过DecorView的findViewById方法，查找到FrameLayout布局
        // Activity activity= (Activity) context;
        //ViewGroup viewGroup= (ViewGroup) activity.getWindow().getDecorView().findViewById(android.R.id.content);

        do {
            if (view instanceof FrameLayout) {
                if (view.getId() == android.R.id.content) {
                    return (ViewGroup) view;
                }
            }
            if (view.getParent() != null) {
                ViewParent viewParent = view.getParent();
                view = viewParent instanceof View ? (View) viewParent : null;
            }

        } while (view != null);

        return null;

    }

    public static BounceMenu makeBounceMenu(Context context, View view, int resId,RecyclerView.Adapter adapter) {
        return new BounceMenu(context, view, resId,adapter);
    }

    public void show() {
        if(mBounceMenu!=null){
            mParentViewGroup.removeView(mBounceMenu);
//            ViewGroup.LayoutParams lp=new ViewGroup.LayoutParams(
//                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            mParentViewGroup.addView(mBounceMenu);
            mBouncingView.show();
        }
    }

    public void dismiss(){
        if(mBouncingView!=null){
            mParentViewGroup.removeView(mBounceMenu);
        }
        mBounceMenu=null;
    }

}
