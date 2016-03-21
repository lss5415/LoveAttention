package com.zykj.loveattention.ui;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zykj.loveattention.R;
import com.zykj.loveattention.base.BaseActivity;
import com.zykj.loveattention.data.AppValue;
import com.zykj.loveattention.data.UserInfo;
import com.zykj.loveattention.utils.HttpUtils;
import com.zykj.loveattention.utils.ImageOptions;
import com.zykj.loveattention.utils.JsonUtils;
import com.zykj.loveattention.utils.Tools;
import com.zykj.loveattention.view.CircularImage;
import com.zykj.loveattention.view.RequestDailog;
import com.zykj.loveattention.view.UIDialog;

/**
 * @author lss 2015年8月14日	账户信息
 *
 */
public class B4_10_WoDeZiLiaoActivity extends BaseActivity {
	private ImageView im_b410_back_btn;//返回
	private LinearLayout ll_zhanghuxinxi;//账户信息
	private LinearLayout ll_shouhuodizhi;//收货地址
	private TextView tv_gexingqianming,tv_changzhudi,tv_youxiang,tv_weixin,tv_qq,tv_yonghuming;
	private TextView tv_myyaoqingma,tv_xingbie,tv_riqi,tv_shoujihao,tv_save;
	private EditText gexingqianming,changzhudi,youxiang,weixin,qq,yonghuming;
	private CircularImage ci_touxiang;//头像
	private RelativeLayout rl_yonghuming,rl_gexingqianming,rl_changzhudi,rl_youxiang,rl_weixin,rl_qq;
	
	/*头像上传*/
	private static final int PAIZHAO = 14;
	private static final int XIANGCE = 15;
	private static final int CAIJIAN = 16;
	private String timeString;
	private String filename;
	private String cutnameString,avatar;
	private UserInfo uinfo;
	private String json;
	private RequestQueue mRequestQueue;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_b4_10_wodeziliao);
		mRequestQueue = Volley.newRequestQueue(this);
		initView();
	}


	public void initView() {
		im_b410_back_btn = (ImageView) findViewById(R.id.im_b410_back_btn);
		ll_zhanghuxinxi = (LinearLayout) findViewById(R.id.ll_zhanghuxinxi);
		ll_shouhuodizhi = (LinearLayout) findViewById(R.id.ll_shouhuodizhi);
		tv_gexingqianming = (TextView) findViewById(R.id.tv_gexingqianming);
		tv_myyaoqingma = (TextView) findViewById(R.id.tv_myyaoqingma);
		tv_yonghuming = (TextView) findViewById(R.id.tv_yonghuming);
		tv_xingbie = (TextView) findViewById(R.id.tv_xingbie);
		tv_shoujihao = (TextView) findViewById(R.id.tv_shoujihao);
		tv_riqi = (TextView) findViewById(R.id.tv_riqi);
		tv_changzhudi = (TextView) findViewById(R.id.tv_changzhudi);
		tv_youxiang = (TextView) findViewById(R.id.tv_youxiang);
		tv_weixin = (TextView) findViewById(R.id.tv_weixin);
		tv_qq = (TextView) findViewById(R.id.tv_qq);
		tv_save = (TextView) findViewById(R.id.tv_save);

		rl_yonghuming = (RelativeLayout) findViewById(R.id.rl_yonghuming);
		rl_gexingqianming = (RelativeLayout) findViewById(R.id.rl_gexingqianming);
		rl_changzhudi = (RelativeLayout) findViewById(R.id.rl_changzhudi);
		rl_youxiang = (RelativeLayout) findViewById(R.id.rl_youxiang);
		rl_weixin = (RelativeLayout) findViewById(R.id.rl_weixin);
		rl_qq = (RelativeLayout) findViewById(R.id.rl_qq);
		
		ci_touxiang = (CircularImage) findViewById(R.id.ci_touxiang);
		uinfo = (UserInfo) getIntent().getSerializableExtra("uinfomodel");
		try {
			avatar = uinfo.getHeadportain();
			ImageLoader.getInstance().displayImage(AppValue.ImgUrl+avatar, ci_touxiang);
			tv_yonghuming.setText(uinfo.getName());
			tv_myyaoqingma.setText(uinfo.getInvitecode());
			tv_gexingqianming.setText(uinfo.getSign());
			tv_xingbie.setText(uinfo.getSex());
			tv_riqi.setText(uinfo.getBirthday());
			tv_changzhudi.setText(uinfo.getAddress());
			tv_shoujihao.setText(uinfo.getMobile());
			tv_youxiang.setText(uinfo.getMail());
			tv_weixin.setText(uinfo.getWeixin());
			tv_qq.setText(uinfo.getQq());
		} catch (Exception e) {
			// TODO: handle exception
		}
		setListener(im_b410_back_btn,ll_zhanghuxinxi,ll_shouhuodizhi, ci_touxiang,rl_yonghuming,rl_gexingqianming,rl_changzhudi,rl_youxiang,rl_weixin,rl_qq,tv_save);
	}

	@Override
	public void onClick(final View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.im_b410_back_btn:
			this.finish();
			break;
		case R.id.tv_save:
			SaveUserInfo();
			break;
		case R.id.ll_zhanghuxinxi:
			Intent itzhxx = new Intent();
			itzhxx.setClass(B4_10_WoDeZiLiaoActivity.this, B4_10_1_ZhangHuXinXiActivity.class);
			startActivity(itzhxx);
			break;
		case R.id.ll_shouhuodizhi:
			Intent itdenglu = new Intent();
			itdenglu.setClass(B4_10_WoDeZiLiaoActivity.this, B4_10_2_ShouHuoDiZhiActivity.class);
			startActivity(itdenglu);
			break;
		case R.id.rl_yonghuming:
			yonghuming = new EditText(v.getContext());
			new AlertDialog.Builder(v.getContext()).setTitle("用户名")
					.setIcon(android.R.drawable.ic_dialog_info)
					.setView(yonghuming)
//					.setView(new EditText(v.getContext()))
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
//							Toast.makeText(v.getContext(), yonghuming.getText().toString(), Toast.LENGTH_LONG).show();
							tv_yonghuming.setText(yonghuming.getText().toString());
						}
					})
					.setNegativeButton("取消", null).show();
			break;
		case R.id.rl_gexingqianming:
			gexingqianming = new EditText(v.getContext());
			new AlertDialog.Builder(v.getContext()).setTitle("个性签名")
					.setIcon(android.R.drawable.ic_dialog_info)
					.setView(gexingqianming)
//					.setView(new EditText(v.getContext()))
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
//							Toast.makeText(v.getContext(), tv_gexingqianming.getText().toString(), Toast.LENGTH_LONG).show();
							tv_gexingqianming.setText(gexingqianming.getText().toString());
						}
					})
					.setNegativeButton("取消", null).show();
			break;
		case R.id.rl_changzhudi:
			changzhudi = new EditText(v.getContext());
			new AlertDialog.Builder(v.getContext()).setTitle("常住地")
					.setIcon(android.R.drawable.ic_dialog_info)
					.setView(changzhudi)
//					.setView(new EditText(v.getContext()))
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
//							Toast.makeText(v.getContext(), tv_changzhudi.getText().toString(), Toast.LENGTH_LONG).show();
							tv_changzhudi.setText(changzhudi.getText().toString());
						}
					})
					.setNegativeButton("取消", null).show();
			break;
		case R.id.rl_youxiang:
			youxiang = new EditText(v.getContext());
			new AlertDialog.Builder(v.getContext()).setTitle("邮箱")
					.setIcon(android.R.drawable.ic_dialog_info)
					.setView(youxiang)
//					.setView(new EditText(v.getContext()))
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
//							Toast.makeText(v.getContext(), tv_youxiang.getText().toString(), Toast.LENGTH_LONG).show();
							tv_youxiang.setText(youxiang.getText().toString());
						}
					})
					.setNegativeButton("取消", null).show();
			break;
		case R.id.rl_weixin:
			weixin = new EditText(v.getContext());
			new AlertDialog.Builder(v.getContext()).setTitle("微信")
					.setIcon(android.R.drawable.ic_dialog_info)
					.setView(weixin)
//					.setView(new EditText(v.getContext()))
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
//							Toast.makeText(v.getContext(), tv_weixin.getText().toString(), Toast.LENGTH_LONG).show();
							tv_weixin.setText(weixin.getText().toString());
						}
					})
					.setNegativeButton("取消", null).show();
			break;
		case R.id.rl_qq:
			qq = new EditText(v.getContext());
			new AlertDialog.Builder(v.getContext()).setTitle("QQ")
					.setIcon(android.R.drawable.ic_dialog_info)
					.setView(qq)
//					.setView(new EditText(v.getContext()))
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
//							Toast.makeText(v.getContext(), tv_weixin.getText().toString(), Toast.LENGTH_LONG).show();
							tv_qq.setText(qq.getText().toString());
						}
					})
					.setNegativeButton("取消", null).show();
			break;
		case R.id.ci_touxiang:
			UIDialog.ForThreeBtn(this, new String[] { "相册", "拍照", "取消" }, this);
			break;
		case R.id.dialog_modif_1:// 相册
			UIDialog.closeDialog();
			/**
			 * 刚开始，我自己也不知道ACTION_PICK是干嘛的，后来直接看Intent源码，
			 * 可以发现里面很多东西,Intent是个很强大的东西，大家一定仔细阅读下
			 */
			Intent intent_toXIANGCE = new Intent(Intent.ACTION_PICK, null);
			/**
			 * 下面这句话，与其它方式写是一样的效果，如果： intent.setData(MediaStore.Images
			 * .Media.EXTERNAL_CONTENT_URI); intent.setType(""image/*");设置数据类型
			 * 如果朋友们要限制上传到服务器的图片类型时可以直接写如 ："image/jpeg 、 image/png等的类型"
			 * 这个地方小马有个疑问，希望高手解答下：就是这个数据URI与类型为什么要分两种形式来写呀？有什么区别？
			 */
			intent_toXIANGCE.setDataAndType(
					MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
			startActivityForResult(intent_toXIANGCE, XIANGCE);
			break;
		case R.id.dialog_modif_2:// 拍照
			UIDialog.closeDialog();
			/**
			 * 下面这句还是老样子，调用快速拍照功能，至于为什么叫快速拍照，大家可以参考如下官方
			 * 文档，you_sdk_path/docs/guide/topics/media/camera.html
			 * 我刚看的时候因为太长就认真看，其实是错的，这个里面有用的太多了，所以大家不要认为
			 * 官方文档太长了就不看了，其实是错的，这个地方小马也错了，必须改正
			 */
			Date date = new Date(System.currentTimeMillis());
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"'IMG'_yyyyMMddHHmmss");
			timeString = dateFormat.format(date);
			createSDCardDir();
			Intent intent_PAIZHAO = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			intent_PAIZHAO.putExtra(MediaStore.EXTRA_OUTPUT, Uri
					.fromFile(new File(Environment
							.getExternalStorageDirectory() + "/DCIM/Camera",
							timeString + ".jpg")));
			startActivityForResult(intent_PAIZHAO, PAIZHAO);
			break;
		case R.id.dialog_modif_3:// 取消
			UIDialog.closeDialog();
			break;
		default:
			break;

		}
	}
	


	// *****************************网络请求返回操作
	// end******************************************
	// *****************************onActivityResult操作
	// begin******************************************
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case XIANGCE:
			try {
				startPhotoZoom(data.getData());
			} catch (Exception e) {
				// TODO: handle exception
				Toast.makeText(this, "您没有选择任何照片", Toast.LENGTH_LONG).show();
			}
			break;
		// 如果是调用相机拍照时
		case PAIZHAO:
			// File temp = new File(Environment.getExternalStorageDirectory()
			// + "/xiaoma.jpg");
			// 给图片设置名字和路径
			File temp = new File(Environment.getExternalStorageDirectory()
					.getPath() + "/DCIM/Camera/" + timeString + ".jpg");
			startPhotoZoom(Uri.fromFile(temp));
			break;
		// 取得裁剪后的图片
		case CAIJIAN:
			/**
			 * 非空判断大家一定要验证，如果不验证的话， 在剪裁之后如果发现不满意，要重新裁剪，丢弃
			 * 当前功能时，会报NullException，小马只 在这个地方加下，大家可以根据不同情况在合适的 地方做判断处理类似情况
			 * 
			 */
			if (data != null) {
				setPicToView(data);
			}
			break;

		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	// *****************************onActivityResult操作
	// end******************************************
	// *****************************图像处理操作
	// begin******************************************
	/**
	 * 裁剪图片方法实现
	 * 
	 * @param uri
	 */
	public void startPhotoZoom(Uri uri) {
		/*
		 * 至于下面这个Intent的ACTION是怎么知道的，大家可以看下自己路径下的如下网页
		 * yourself_sdk_path/docs/reference/android/content/Intent.html
		 * 直接在里面Ctrl+F搜：CROP ，之前小马没仔细看过，其实安卓系统早已经有自带图片裁剪功能, 是直接调本地库的，小马不懂C C++
		 * 这个不做详细了解去了，有轮子就用轮子，不再研究轮子是怎么 制做的了...吼吼
		 */
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		// intent.putExtra("aspectX", 1);
		// intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, CAIJIAN);
	}

	public void createSDCardDir() {
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			// 创建一个文件夹对象，赋值为外部存储器的目录
			File sdcardDir = Environment.getExternalStorageDirectory();
			// 得到一个路径，内容是sdcard的文件夹路径和名字
			String path = sdcardDir.getPath() + "/DCIM/Camera";
			File path1 = new File(path);
			if (!path1.exists()) {
				// 若不存在，创建目录，可以在应用启动的时候创建
				path1.mkdirs();
			}
		}
	}

	/**
	 * 保存裁剪之后的图片数据
	 * 
	 * @param picdata
	 */
	private void setPicToView(Intent picdata) {
		Bundle extras = picdata.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			Drawable drawable = new BitmapDrawable(photo);
			/**
			 * 下面注释的方法是将裁剪之后的图片以Base64Coder的字符方式上 传到服务器，QQ头像上传采用的方法跟这个类似
			 */
			savaBitmap(photo);
			ci_touxiang.setImageBitmap(photo);// 设置头像
			// avatar_head_image.setBackgroundDrawable(drawable);
		}
	}

	// 将剪切后的图片保存到本地图片上！
	public void savaBitmap(Bitmap bitmap) {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"'IMG'_yyyyMMddHHmmss");
		cutnameString = dateFormat.format(date);
		filename = Environment.getExternalStorageDirectory().getPath() + "/"
				+ cutnameString + ".jpg";
		Tools.Log("filename=" + filename);
		File f = new File(filename);
		putSharedPreferenceValue("headImg_filename", filename);

		FileOutputStream fOut = null;
		try {
			f.createNewFile();
			fOut = new FileOutputStream(f);
		} catch (Exception e) {
			e.printStackTrace();
		}
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);// 把Bitmap对象解析成流

		try {
			fOut.flush();
			fOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		RequestDailog.showDialog(this, "正在上传头像，请稍后");
		HttpUtils.update(res_uploadavatar, getSharedPreferenceValue("id"), f);
	}

	// *****************************图像处理操作
	// end******************************************

	/**
	 * 上传头像之后的操作
	 */
	JsonHttpResponseHandler res_uploadavatar = new JsonHttpResponseHandler() {

		@Override
		public void onSuccess(int statusCode, Header[] headers,
				JSONObject response) {
			super.onSuccess(statusCode, headers, response);
			RequestDailog.closeDialog();
			Tools.Log("res_uploadavatar=" + response);
			String error = null;
			JSONObject datas = null;
			avatar = null;
			try {
				datas = response.getJSONObject("data");
				error = datas.getString("error");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (error == null)// 成功
			{
				try {
					avatar = datas.getString("imgurl");
					ImageLoader.getInstance().displayImage(AppValue.ImgUrl+avatar, ci_touxiang,ImageOptions.getOpstion());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else// 失败
			{
				Tools.Notic(B4_10_WoDeZiLiaoActivity.this, error + "", null);
			}
			// String url = LandousAppConst.avatar_url_head + avatar_head;
			// ImageLoader.getInstance().displayImage(url, avatar_head_image,
			// BeeFrameworkApp.options_circle);
			// }

		}

		@Override
		public void onFailure(int statusCode, Header[] headers,
				Throwable throwable, JSONObject errorResponse) {
			RequestDailog.closeDialog();
			Tools.Log("res_update=网络连接超时");
			super.onFailure(statusCode, headers, throwable, errorResponse);
		}
	};

	//保存用户信息
	public void SaveUserInfo(){

		RequestDailog.showDialog(this, "正在加载数据请稍后");
		Map<String, String> map = new HashMap<String, String>();
		map.put("memberid", getSharedPreferenceValue("id"));
		map.put("headportain", avatar);
		map.put("name", tv_yonghuming.getText().toString());
		map.put("sign", tv_gexingqianming.getText().toString());
		map.put("sex", tv_xingbie.getText().toString());
		map.put("birthday", tv_riqi.getText().toString());
		map.put("address", tv_changzhudi.getText().toString());
		map.put("receiveaddress", tv_youxiang.getText().toString());
		map.put("weixin", tv_weixin.getText().toString());
		map.put("qq", tv_qq.getText().toString());
		json = JsonUtils.toJson(map);
		JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET,
				HttpUtils.url_memberinfo(json), null,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						RequestDailog.closeDialog();
						JSONObject status;
						try {
							status = response.getJSONObject("status");
							String succeed = status.getString("succeed");
							if (succeed.equals("1")) // 成功
							{
								Toast.makeText(B4_10_WoDeZiLiaoActivity.this,"信息更新成功", Toast.LENGTH_LONG).show();
								B4_10_WoDeZiLiaoActivity.this.finish();
							} else {// 失败,提示失败信息
								String errdesc = status.getString("errdesc");
								Toast.makeText(B4_10_WoDeZiLiaoActivity.this,errdesc, Toast.LENGTH_LONG).show();
							}
						} catch (org.json.JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						RequestDailog.closeDialog();
						Tools.Log("ErrorResponse=" + error.getMessage());
						Toast.makeText(B4_10_WoDeZiLiaoActivity.this, "网络连接失败，请重试",
								Toast.LENGTH_LONG).show();
					}
				});
		mRequestQueue.add(jr);
	}
	
}