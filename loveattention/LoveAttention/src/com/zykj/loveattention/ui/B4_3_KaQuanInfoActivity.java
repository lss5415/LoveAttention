package com.zykj.loveattention.ui;

import java.util.Hashtable;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.zxing.activity.CaptureActivity;
import com.zxing.encoding.EncodingHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zxing.encoding.EncodingHandler;
import com.zykj.loveattention.R;
import com.zykj.loveattention.base.BaseActivity;
import com.zykj.loveattention.data.AppValue;
import com.zykj.loveattention.utils.Share;
import com.zykj.loveattention.utils.Tools;
import com.zykj.loveattention.view.CircularImage;
import com.zykj.loveattention.view.RGBLuminanceSource;

/**
 * @author lss 2015年8月12日	卡券详情
 *
 */
public class B4_3_KaQuanInfoActivity extends BaseActivity {
	private ImageView im_b43s_back_btn;//返回
	private ImageView iv_share;//分享
//	private LinearLayout ll_wodeqianbao;//我的钱包
//	private LinearLayout ll_wodekaquan;//我的卡券
	
	private TextView tv_diyongquan;//商家名
	private TextView tv_kaquanname;//卡券名
	private TextView tv_effectivetime;//有效时间
	
	private RelativeLayout rl_youhuidetail0;//优惠详情栏
	private RelativeLayout rl_youhuidetail;//优惠详情栏
	private RelativeLayout rl_shiyongxuzhi;//使用须知
	private RelativeLayout rl_shiyongxuzhi1,rl_background;//使用须知
	private TextView tv_youhuidetail;//优惠详情
	private TextView tv_shiyongxuzhi;//使用须知
	
	private ImageView iv_call,im_kaquanstate,im_erweima;//拨打电话
	
	private String ShareContent ;
	private String ShareTitle;
	private String ShareUrl ;
	
	private String couponname,name,effecttime,couponintroduct,state,overdue;//卡券名，商家名，有效时间,使用须知，state--卡券使用状态(0未使用,1已使用,3已过期,4未支付)
	private String imgpath,couponcolor,telephone,couponid;
	private CircularImage im_touxiang;
	private int isShow = 0;
	private int isShow_s = 0;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_b43_kaquaninfo);
		initView();
		initShare();
		getIntentData();
		ErWeiMa();//生成二维码
		setData();
	}


	private void setData() {
		// TODO Auto-generated method stub
		tv_diyongquan.setText(name);
		tv_kaquanname.setText(couponname);
		tv_effectivetime.setText("有效期："+effecttime);
		ImageLoader.getInstance().displayImage(AppValue.ImgUrl+imgpath, im_touxiang);
		rl_background.setBackgroundColor(Color.parseColor(couponcolor));
	}


	private void getIntentData() {
		Intent intent = getIntent();
		Bundle bundle = intent.getBundleExtra("data");
		couponname = bundle.getString("couponname");
		name = bundle.getString("name");
		effecttime = bundle.getString("effecttime");
		imgpath = bundle.getString("imgpath");
		couponcolor = bundle.getString("couponcolor");
		telephone = bundle.getString("telephone");
		couponid = bundle.getString("couponid");
		couponintroduct = bundle.getString("couponintroduct");
		
		overdue = bundle.getString("overdue");
		if (overdue.equals("0")) {//overdue--是否过期(0未过期，1过期)
			state = bundle.getString("state");
			if (state.equals("0")) {//state--卡券使用状态(0未使用,1已使用,3已过期,4未支付)
			}else if (state.equals("1")) {
				im_kaquanstate.setImageResource(R.drawable.yishiyong);
			}else if (state.equals("3")) {
				im_kaquanstate.setImageResource(R.drawable.guoqi);
			}
		}else {
			im_kaquanstate.setImageResource(R.drawable.guoqi);
		}
//		Log.e("data1", "couponname="+couponname+"name="+name+"effecttime="+effecttime);
		
	}


	public void initView() {
		im_b43s_back_btn = (ImageView) findViewById(R.id.im_b43s_back_btn);
		iv_share = (ImageView) findViewById(R.id.iv_share);
		tv_diyongquan = (TextView) findViewById(R.id.tv_diyongquan);
		tv_kaquanname = (TextView) findViewById(R.id.tv_kaquanname);
		tv_effectivetime = (TextView) findViewById(R.id.tv_effectivetime);
		tv_youhuidetail = (TextView) findViewById(R.id.tv_youhuidetail);
		tv_shiyongxuzhi = (TextView) findViewById(R.id.tv_shiyongxuzhi);
		rl_youhuidetail0 = (RelativeLayout) findViewById(R.id.rl_youhuidetail0);
		rl_youhuidetail = (RelativeLayout) findViewById(R.id.rl_youhuidetail);
		rl_shiyongxuzhi = (RelativeLayout) findViewById(R.id.rl_shiyongxuzhi);
		rl_shiyongxuzhi1 = (RelativeLayout) findViewById(R.id.rl_shiyongxuzhi1);
		rl_background = (RelativeLayout) findViewById(R.id.rl_background);
		iv_call = (ImageView) findViewById(R.id.iv_call);
		im_kaquanstate = (ImageView) findViewById(R.id.im_kaquanstate);
		im_touxiang = (CircularImage) findViewById(R.id.im_touxiang);
		im_erweima = (ImageView) findViewById(R.id.im_erweima);
//		ll_wodeqianbao = (LinearLayout) findViewById(R.id.ll_wodeqianbao);
//		ll_wodekaquan = (LinearLayout) findViewById(R.id.ll_wodekaquan);

		setListener(im_b43s_back_btn,iv_share,rl_youhuidetail0,rl_shiyongxuzhi,iv_call);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.im_b43s_back_btn:
			this.finish();			
		break;
		
		case R.id.rl_youhuidetail0:
			Log.e("isShow", isShow+"");
			if (isShow == 1){
				rl_youhuidetail.setVisibility(View.GONE);
				isShow = 0;
			}else {
				rl_youhuidetail.setVisibility(View.VISIBLE);
				tv_youhuidetail.setText(couponname);
				isShow = 1;
			}
			break;
		case R.id.rl_shiyongxuzhi:
			Log.e("isShow", isShow+"");
			if (isShow_s == 1){
				rl_shiyongxuzhi1.setVisibility(View.GONE);
				isShow_s = 0;
			}else {
				rl_shiyongxuzhi1.setVisibility(View.VISIBLE);
				tv_shiyongxuzhi.setText(couponintroduct);
				isShow_s = 1;
			}
			break;
		case R.id.iv_call:
			// 传入服务， parse（）解析号码
			Tools.Notic(this, "是否要打电话给："+telephone, new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					 Intent intent = new Intent(Intent.ACTION_CALL, Uri
			                    .parse("tel:" + telephone));
			            // 通知activtity处理传入的call服务
			        startActivity(intent);	
				}
			});
           
			break;
		case R.id.iv_share:
			Share.invit(this, ShareContent, ShareTitle, ShareUrl);		
			break;
		default:
			break;

		}
	}
	private void initShare() {
		ShareContent = "我在爱关注领取了优惠券，送给你了，快来领吧";
		ShareTitle = "优惠券";
		ShareUrl = "http://www.pgyer.com/ov1S";
	}
	
	public void ErWeiMa(){
		//获取界面输入的内容
		String content = couponid;
		//判断内容是否为空
		if (null == content || "".equals(content)) {
			Toast.makeText(B4_3_KaQuanInfoActivity.this, "请输入要写入二维码的内容...",
					Toast.LENGTH_SHORT).show();
			return;
		}
		
		try {
			//生成二维码图片，第一个参数是二维码的内容，第二个参数是正方形图片的边长，单位是像素
			Bitmap qrcodeBitmap = EncodingHandler.createQRCode(content, 400);
			im_erweima.setImageBitmap(qrcodeBitmap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	//解析二维码图片,返回结果封装在Result对象中
	private com.google.zxing.Result  parseQRcodeBitmap(String bitmapPath){
		//解析转换类型UTF-8
		Hashtable<DecodeHintType, String> hints = new Hashtable<DecodeHintType, String>();
		hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
		//获取到待解析的图片
		BitmapFactory.Options options = new BitmapFactory.Options(); 
		//如果我们把inJustDecodeBounds设为true，那么BitmapFactory.decodeFile(String path, Options opt)
		//并不会真的返回一个Bitmap给你，它仅仅会把它的宽，高取回来给你
		options.inJustDecodeBounds = true;
		//此时的bitmap是null，这段代码之后，options.outWidth 和 options.outHeight就是我们想要的宽和高了
		Bitmap bitmap = BitmapFactory.decodeFile(bitmapPath,options);
		//我们现在想取出来的图片的边长（二维码图片是正方形的）设置为400像素
		/**
			options.outHeight = 400;
			options.outWidth = 400;
			options.inJustDecodeBounds = false;
			bitmap = BitmapFactory.decodeFile(bitmapPath, options);
		*/
		//以上这种做法，虽然把bitmap限定到了我们要的大小，但是并没有节约内存，如果要节约内存，我们还需要使用inSimpleSize这个属性
		options.inSampleSize = options.outHeight / 400;
		if(options.inSampleSize <= 0){
			options.inSampleSize = 1; //防止其值小于或等于0
		}
		/**
		 * 辅助节约内存设置
		 * 
		 * options.inPreferredConfig = Bitmap.Config.ARGB_4444;    // 默认是Bitmap.Config.ARGB_8888
		 * options.inPurgeable = true; 
		 * options.inInputShareable = true; 
		 */
		options.inJustDecodeBounds = false;
		bitmap = BitmapFactory.decodeFile(bitmapPath, options); 
		//新建一个RGBLuminanceSource对象，将bitmap图片传给此对象
		RGBLuminanceSource rgbLuminanceSource = new RGBLuminanceSource(bitmap);
		//将图片转换成二进制图片
		BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(rgbLuminanceSource));
		//初始化解析对象
		QRCodeReader reader = new QRCodeReader();
		//开始解析
		Result result = null;
		try {
			result = reader.decode(binaryBitmap, hints);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return result;
	}
	
}