package com.vivifram.second.hitalk.ui;

import android.content.Context;
import android.os.Bundle;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.vivifram.second.hitalk.R;
import com.vivifram.second.hitalk.base.BaseActivity;
import com.vivifram.second.hitalk.base.LayoutInject;
import com.vivifram.second.hitalk.manager.chat.ClientManager;
import com.vivifram.second.hitalk.manager.chat.PushManager;
import com.vivifram.second.hitalk.ui.layout.SettingLayout;
import com.vivifram.second.hitalk.ui.view.BGATitlebar;
import com.zuowei.utils.bridge.EaterManager;
import com.zuowei.utils.bridge.params.LoginParam;
import com.zuowei.utils.bridge.params.LogoutParam;
import com.zuowei.utils.common.UIUtils;

/**
 * 项目名称：Hitalk
 * 类描述：
 * 创建人：zuowei
 * 创建时间：16-10-26 下午2:09
 * 修改人：zuowei
 * 修改时间：16-10-26 下午2:09
 * 修改备注：
 */
@LayoutInject(name = "SettingLayout")
public class SettingActivity extends BaseActivity<SettingLayout> {

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_setting_layout);
        mLayout.setTitleDelegate(new BGATitlebar.BGATitlebarDelegate(){
            @Override
            public void onClickLeftCtv() {
                super.onClickLeftCtv();
                finish();
            }
        });

        mLayout.setOnLogoutButtonClick(view->{
            ClientManager.getInstance().getClient().close(new AVIMClientCallback() {
                @Override
                public void done(AVIMClient avimClient, AVIMException e) {

                }
            });

            PushManager.getInstance().unsubscribeCurrentUserChannel();

            EaterManager.getInstance().
                    broadcast(new LoginParam.Builder(LoginParam.TYPE_LOGOUT).create());

            clearLoginData();
            finish();
            notifyAllActivityDestroy();
            UIUtils.startActivitySafety(mAppCtx,LoginActivity.class);
        });
    }

    private void clearLoginData() {
        EaterManager.getInstance().broadcast(new LogoutParam());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public static void start(Context context){
        start(context,SettingActivity.class);
    }
}
