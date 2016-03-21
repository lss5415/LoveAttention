package com.zykj.loveattention.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.zykj.loveattention.R;
import com.zykj.loveattention.base.BaseActivity;
import com.zykj.loveattention.utils.Tools;
import com.zykj.loveattention.view.RequestDailog;
import com.zykj.loveattention.view.UIDialog;

/**
 * @author lss 2015年8月8日 更多
 * 
 */
public class B5_GengDuoActivity extends BaseActivity {
	
	private RelativeLayout rl_xinshouzhinan;//新手指南
	private RelativeLayout rl_shequgonggao;//社区公告
	private RelativeLayout rl_qinglineicun;//清理内存
	private RelativeLayout rl_guanyuwomen;//关于我们
	private RelativeLayout rl_jianchagengxin;//检查更新
	private RelativeLayout rl_changyongwentizhinan;//常用问题指南
	private RelativeLayout rl_yijianfankui;//意见反馈
	private RelativeLayout rl_kefudianhua;//客服电话
	private RelativeLayout rl_woshishangjia;//我是商家
	
	
	
	private RelativeLayout rl_logout;//推出当前账号
	
	private int isCleaned=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_b5_gengduo);
		initView();
		
	}
	private Handler handler = new Handler()
	{
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case 1:
				RequestDailog.closeDialog();
				break;

			default:
				Tools.Log(msg+"");
				break;
			}
			super.handleMessage(msg);
		}
		
	};
    int what = 1;
	Thread thread = new Thread(new Runnable() {

        public void run() {
            while (true) {
                try {
                	Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(what);
            }

        }
    });
	public void initView() {
		rl_xinshouzhinan = (RelativeLayout) findViewById(R.id.rl_xinshouzhinan);
		rl_shequgonggao = (RelativeLayout) findViewById(R.id.rl_shequgonggao);
		rl_qinglineicun = (RelativeLayout) findViewById(R.id.rl_qinglineicun);
		rl_guanyuwomen = (RelativeLayout) findViewById(R.id.rl_guanyuwomen);
		rl_jianchagengxin = (RelativeLayout) findViewById(R.id.rl_jianchagengxin);
		rl_changyongwentizhinan = (RelativeLayout) findViewById(R.id.rl_changyongwentizhinan);
		rl_yijianfankui = (RelativeLayout) findViewById(R.id.rl_yijianfankui);
		rl_kefudianhua = (RelativeLayout) findViewById(R.id.rl_kefudianhua);
		rl_woshishangjia = (RelativeLayout) findViewById(R.id.rl_woshishangjia);
		rl_logout = (RelativeLayout) findViewById(R.id.rl_logout);
		setListener(rl_shequgonggao,rl_logout,
				rl_xinshouzhinan,rl_qinglineicun,rl_guanyuwomen,rl_jianchagengxin,
				rl_changyongwentizhinan,rl_yijianfankui,rl_kefudianhua,rl_woshishangjia);
	}
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.rl_xinshouzhinan:
			//新手指南
			Intent intent = new Intent();
			intent.setClass(this, B5_1_XinShouZhiNanActivity.class);
			startActivity(intent);
			break;
		case R.id.rl_shequgonggao:
			Intent itsqgg = new Intent();
			itsqgg.setClass(B5_GengDuoActivity.this, B5_2_SheQuGongGaoActivity.class);
			startActivity(itsqgg);
			break;
		case R.id.rl_qinglineicun://清理内存
			if (isCleaned == 0) {
				isCleaned = 1;
				RequestDailog.showDialog(this, "正在清理内存,请稍后");
				thread.start();
			}else {
				Tools.Notic(this, "您已经清理过内存，手机状况良好", null);
			}
			break;
		case R.id.rl_guanyuwomen://关于我们
			Intent itgywm = new Intent();
			itgywm.setClass(B5_GengDuoActivity.this, B5_4_AboutUsActivity.class);
			startActivity(itgywm);
			break;
		case R.id.rl_jianchagengxin://检查更新
			Tools.Notic(this, "目前已经是最新版本", null);
			break;
		case R.id.rl_changyongwentizhinan://常用问题指南
			Intent itcjwt = new Intent();
			itcjwt.setClass(B5_GengDuoActivity.this, B5_6_ChangJianWenTiActivity.class);
			startActivity(itcjwt);
			break;
		case R.id.rl_yijianfankui://意见反馈
//			Tools.Notic(this, "意见反馈：" +
//					"1，2，3，4", null);
			Intent it4feedback = new Intent(this,B5_Feedback.class);
			startActivity(it4feedback);
			break;
		case R.id.rl_kefudianhua://客服电话
			// 传入服务， parse（）解析号码
						Tools.Notic(this, "是否要打电话给："+"4000877567", new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								 Intent intent = new Intent(Intent.ACTION_CALL, Uri
						                    .parse("tel:" + "4000877567"));
						            // 通知activtity处理传入的call服务
						        startActivity(intent);	
							}
						});
			break;
		case R.id.rl_woshishangjia://我是商家
			Intent itwssj = new Intent();
			itwssj.setClass(B5_GengDuoActivity.this, B5_9_WoShiShangJiaActivity.class);
			startActivity(itwssj);
			break;
		case R.id.rl_logout:
			if (isLoged()) {
				Tools.Notic(B5_GengDuoActivity.this, "是否退出当前账号", new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						putSharedPreferenceValue("isLoged", "0");
						Intent itwode = new Intent(B5_GengDuoActivity.this,B0_MainActivity.class);
						startActivity(itwode);
					}
				});
			}else {
				Toast.makeText(this, "您未登录", Toast.LENGTH_LONG).show();
			}
			break;
		default:
			break;

		}
	}
	public  boolean isLoged() {
		String isLoged = null;
		if (getSharedPreferenceValue("isLoged")!=null) {
			isLoged = getSharedPreferenceValue("isLoged");
			if (isLoged.equals("1")) {
				return true;
			}else {
				return false;
			}
		}else {
			putSharedPreferenceValue("isLoged", "0");
			return false;
		}
	}

}