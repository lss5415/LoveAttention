package com.zykj.loveattention.utils;

import java.io.File;
import java.io.FileNotFoundException;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

/**
 *AsyncHttp 异步联网第三方库
 * @author 
 */

public class HttpUtils {
//	public static final String base_url = "http://115.28.208.196:8080/aigz/";
	public static final String base_url = "http://115.28.67.86:8080/aigz/";
	private static AsyncHttpClient client = new AsyncHttpClient(); // 实例话对象
	static {
		client.setTimeout(5000); // 设置链接超时，如果不设置，默认为15s
		client.setMaxRetriesAndTimeout(3, 5000);
		client.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
		// client.setEnableRedirects(true);
	}
	public static void initClient(Context c) {
		PersistentCookieStore myCookieStore = new PersistentCookieStore(c);
		client.setCookieStore(myCookieStore);
	}

	public static AsyncHttpClient getClient() {

		return client;
	}
	
	/**
	 * 1注册
	 * 
	 * @param res
	 * @param mobile(手机号,必填),username(用户名,必填),password(密码,必填),invitecode(邀请码,选填)
	 */
	public static String url_regist(String json) {
		String url = null;
		url = base_url + "data/register?json="+json;
		return url;
	}
	
	/**
	 * 2.登录
	 * 
	 * @param res
	 * @param username（用户名），password（密码）
	 */
	public static String url_login(String json) {
		String url = null;
		url = base_url + "data/login?json="+json;
		return url;
	}
	
	/**
	 * 2-a.上传，更新头像
	 * 
	 * @param res
	 * @param memberid（用户名），imgsrc （图片）
	 */
	public static void update(AsyncHttpResponseHandler res, String memberid,File imgsrc ) {
		RequestParams params = new RequestParams();
		try {
			params.put("memberid", memberid);
			params.put("imgsrc",imgsrc);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // Upload a File
		String url = base_url + "data/uploadHeadportain";
		client.post(url, params, res);
	}
	
	/**
	 * 3.城市列表（热门）
	 * 
	 * @param res
	 * @param 
	 */
	public static void getCityList(AsyncHttpResponseHandler res) {
		String url = base_url + "data/city";
		client.get(url, res);
	}
	
	/**
	 * 4.我的DNA
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_myDNA() {
		String url = null;
		url = base_url + "data/myDNA";
		return url;
	}
	
	/**
	 * 5.商户类型分类
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_leixingfenlei() {
		String url = null;
		url = base_url + "data/categoryinfo";
		return url;
	}
	
	/**
	 * 6.商户列表 根据类型查询
	 * 
	 * @param res
	 * @param districtid： 地区id categoryid：分类id distance：距离 perperson:人均 orderType： 排序 1.人气 2.口碑 3.离我最近 4.人均最高 5.人均最低 
			  searchType： 筛选 1.卡卷 2.免预约 3.节假日可用
	 */
	public static String url_merchantinfo(String json) {
		String url = null;
		url = base_url + "data/merchantinfo?json="+json;
		return url;
	}
	
	/**
	 * 7.我的订单
	 * 
	 * @param res
	 * @param type: 订单类型 0：到店 1：外送 -1 全部 
					state: 订单类型为0：订单状态 0：未付款 1 未消费 2：待评价 -1全部
					订单类型为1：订单状态 0：未付款 1：已付款 2 已收货 -1全部 
	 */
	public static String url_order(String json) {
		String url = null;
		url = base_url + "data/order?json="+json;
		return url;
	}
	
	/**
	 * 8.订单生成
	 * 
	 * @param res
	 * @param json={"memberid":"1","merchantid":"2","goodid":"2","type":"0","state":"1","price":"50"}
	 */
	public static String url_orderAdd(String json) {
		String url = null;
		url = base_url + "data/orderAdd?json="+json;
		return url;
	}
	
	/**
	 * 9.我的钱包
	 * 
	 * @param res
	 * @param json={"memberid":"5"}
	 */
	public static String url_wallet(String json) {
		String url = null;
		url = base_url + "data/wallet?json="+json;
		return url;
	}
	
	/**
	 * 10.我的关注
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_attention(String json) {
		String url = null;
		url = base_url + "data/attention?json="+json;
		return url;
	}
	
	/**
	 * 11.我的关注增加
	 * 
	 * @param res
	 * @param objid: 被关注用户的id objtype 关注类型 ： 1商品 2 商户 3活动 响应成功
	 */
	public static String url_attentionAdd(String json) {
		String url = null;
		url = base_url + "data/attentionAdd?json="+json;
		return url;
	}
	
	/**
	 * 12.我的关注删除
	 * 
	 * @param res
	 * @param json={"attentionid":"1"}
	 */
	public static String url_attentionDelete(String json) {
		String url = null;
		url = base_url + "data/attentionDelete?json="+json;
		return url;
	}
	
	/**
	 * 13.我的收藏
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_collect(String json) {
		String url = null;
		url = base_url + "data/collect?json="+json;
		return url;
	}
	
	/**
	 * 14.我的收藏增加
	 * 
	 * @param res
	 * @param objtype: 收藏类别 1商品 2 商户 3活动  objid： 被收藏者id
	 */
	public static String url_collectAdd(String json) {
		String url = null;
		url = base_url + "data/collectAdd?json="+json;
		return url;
	}
	
	/**
	 * 15.当面立即付款
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_payOrder(String json) {
		String url = null;
		url = base_url + "data/payOrder?json="+json;
		return url;
	}
	
	/**
	 * 15.我的收藏删除
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_collectDelete(String json) {
		String url = null;
		url = base_url + "data/collectDelete?json="+json;
		return url;
	}
	
	/**
	 * 16。我的预约
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_reseration(String json) {
		String url = null;
		url = base_url + "data/reseration?json="+json;
		return url;
	}
	
	/**
	 * 17.我的预约增加
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_reserationAdd(String json) {
		String url = null;
		url = base_url + "data/reserationAdd?json="+json;
		return url;
	}
	
	/**
	 * 18.我的预约删除
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_reserationDel(String json) {
		String url = null;
		url = base_url + "data/reserationDel?json="+json;
		return url;
	}
	

	/**
	 * 19.我的邀请
	 * 
	 * @param res
	 * @param json  {"memberid":"1"}
	 */
	public static String url_myinvite(String json) {
		String url = null;
		url = base_url + "data/invite?json="+json;
		return url;
	}
	
	/**
	 * 20.用户信息编辑
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_memberinfo(String json) {
		String url = null;
		url = base_url + "data/memberinfo?json="+json;
		return url;
	}
	
	/**
	 * 21.用户地址信息
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_addressinfo(String json) {
		String url = null;
		url = base_url + "data/addressinfo?json="+json;
		return url;
	}
	
	/**
	 * 获取省市县接口
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_getcitylist(String json) {
		String url = null;
		url = base_url + "data/citylist?json="+json;
		return url;
	}
	
	/**
	 * 22.用户地址信息增加
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_addressinfoAdd(String json) {
		String url = null;
		url = base_url + "data/addressinfoAdd?json="+json;
		return url;
	}
	
	/**
	 * 23.用户地址信息更新
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_addressinfoUpdate(String json) {
		String url = null;
		url = base_url + "data/addressinfoUpdate?json="+json;
		return url;
	}
	
	/**
	 * 24.用户地址信息删除
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_addressinfoDel(String json) {
		String url = null;
		url = base_url + "data/addressinfoDel?json="+json;
		return url;
	}
	
	/**
	 * 25.用户账户信息
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_accountinfo(String json) {
		String url = null;
		url = base_url + "data/accountinfo?json="+json;
		return url;
	}
	
	/**
	 * 26.用户账户信息增加
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_accountinfoAdd(String json) {
		String url = null;
		url = base_url + "data/accountinfoAdd?json="+json;
		return url;
	}
	
	/**
	 * 27.用户账户信息更新
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_accountinfoUpdate(String json) {
		String url = null;
		url = base_url + "data/accountinfoUpdate?json="+json;
		return url;
	}
	
	/**
	 * 28.用户信息密码修改
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_memberloginpwdUpdate(String json) {
		String url = null;
		url = base_url + "data/memberloginpwdUpdate?json="+json;
		return url;
	}
	
	/**
	 * 29.我的卡券
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_couponinfo(String json) {
		String url = null;
		url = base_url + "data/couponinfo?json="+json;
		return url;
	}
	
	/**
	 * 30.发现中活动
	 * 
	 * @param res
	 */
	public static String url_faxian(String json) {
		String url = null;
		url = base_url + "data/activity?json="+json;
		return url;
	}
	
	/**
	 * 常见问题
	 * @param json
	 * @return
	 */
	public static String url_question(String json) {
		String url = null;
		url = base_url + "data/question?json="+json;
		return url;
	}
	
	/**
	 * 31.更多-公告
	 * 
	 * @param res
	 */
	public static String url_gonggaolist(String json) {
		String url = null;
		url = base_url + "data/announce?json=" +json;
		return url;
	}
	
	/**
	 * 32.更多-升级
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_softupdate(String json) {
		String url = null;
		url = base_url + "data/softupdate?json="+json;
		return url;
	}
	
	/**
	 * 33.更多-升级
	 * 
	 * @param res
	 * @param 
	 */
	/*public static String url_(String json) {
		String url = null;
		url = base_url + "data/register?json="+json;
		return url;
	}*/
	
	/**
	 * 34.更多-意见反馈
	 * 
	 * @param res
	 * @param json  {"memberid":"1","content":"建议测试"}
	 */
	public static String url_feedback(String json) {
		String url = null;
		url = base_url + "data/feedback?json="+json;
		return url;
	}
	
	/**
	 * 35.首页-广告 自定义广告和轮播广告
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_shouyeguanggao() {
		String url = null;
		url = base_url + "data/advertList";
		return url;
	}
	public static void qwe(AsyncHttpResponseHandler res) {
		String url = base_url + "data/advertList";
		client.get(url, res);
	}
	
	/**
	 * 36.首页-轮播广告详情页
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_rotationDetail(String json) {
		String url = null;
		url = base_url + "data/rotationDetail?json="+json;
		return url;
	}
	
	/**
	 * 
	 * 
	 * @param res
	 * @param 
	 */
	/*public static String url_(String json) {
		String url = null;
		url = base_url + "data/register?json="+json;
		return url;
	}*/
	
	/**
	 * 38.首页-广告信息详情（定制、团购、今日新单、金币新城）
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_addetailinfo(String json) {
		String url = null;
		url = base_url + "data/addetailinfo?json="+json;
		return url;
	}
	
	/**
	 * 39.首页-热门商户信息
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_hotmerchantinfo(String json) {
		String url = null;
		url = base_url + "data/hotmerchantinfo?json="+json;
		return url;
	}
	
	/**
	 * 40.首页-猜你喜欢信息
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_guessinfo(String json) {
		String url = null;
		url = base_url + "data/guessinfo?json="+json;
		return url;
	}
	
	/**
	 * 41.商品详情（评论）
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_gooddetail(String json) {
		String url = null;
		url = base_url + "data/gooddetail?json="+json;
		return url;
	}
	
	/**
	 * 42.商品评论分页查询
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_goodmoreComment(String json) {
		String url = null;
		url = base_url + "data/goodmoreComment?json="+json;
		return url;
	}
	/**
	 * 42.商家评论分页查询
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_merchantmoreComment(String json) {
		String url = null;
		url = base_url + "data/merchantmoreComment?json="+json;
		return url;
	}
	
	
	
	/**
	 * 搜索结果
	 * @return
	 */
	public static String url_shouyesousuo() {
		String url = null;
		url = base_url + "data/searchRecord";
		return url;
	}
	
	/**
	 * 全部分类
	 * @return
	 */
	public static String url_allcategory() {
		String url = null;
		url = base_url + "data/categoryinfo";
		Log.d("----", "url =  " + url);
		return url;
	}
	/**
	 * 全部分类1
	 * @return
	 */
	public static String url_allcategorynew() {
		String url = null;
		url = base_url + "data/categoryinfoNew";
		Log.d("----", "url =  " + url);
		return url;
	}
	/**
	 * 43.商家详情优惠活动
	 * 
	 * @param res
	 * @param 
	 */
	public static String getMerChantDetailInfo(String json) {
		String url = base_url + "data/merchantdetailinfo?json="+json;
		return url;
	}
	
	/**
	 * 44.商家详情中商家卡券
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_merchantcouponinfo(String json) {
		String url = null;
		url = base_url + "data/merchantcouponinfo?json="+json;
		return url;
	}
	
	/**
	 * 45.附近商家
	 * 
	 * @param res
	 */
	public static String url_fujin(String json) {
		String url = null;
		url = base_url + "data/nearinfo?json="+json;
		return url;
	}
	
	/**
	 * 46.广告定制 答题
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_subjectinfo(String json) {
		String url = null;
		url = base_url + "data/subjectinfo?json="+json;
		return url;
	}
	
	/**
	 * 46.广告定制 答案提交
	 * 
	 * @param res
	 * @param 
	 */
	public static String subjectresult(String json) {
		String url = null;
		url = base_url + "data/subjectresult?json="+json;
		return url;
	}
	
	/**
	 * 47.公告删除
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_announceDel(String json) {
		String url = null;
		url = base_url + "data/announceDel?json="+json;
		return url;
	}
	
	/**
	 * 48.公告批量删除
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_announceBatchDel(String json) {
		String url = null;
		url = base_url + "data/announceBatchDel?json="+json;
		return url;
	}
	/**
	 * 关于我们
	 * @param json
	 * @return
	 */
	public static String url_about() {
		String url = null;
		url = base_url + "data/about";
		return url;
	}
	/**
	 * 49.我的订单详情 
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_orderdetail(String json) {
		String url = null;
		url = base_url + "data/orderdetail?json="+json;
		return url;
	}
	
	/**
	 * 50.今日新单
	 * 
	 * @param res
	 * @param 
	 */
	public static void url_todaymenu(AsyncHttpResponseHandler handler) {
		String url = base_url + "data/todaymenu";
		client.get(url, handler);
	}
	
	/**
	 * 51.我的邀请确定
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_minviteconfirm(String json) {
		String url = null;
		url = base_url + "mdata/minviteconfirm?json="+json;
		return url;
	}
	/**
	 * 52-b.获取用户资料
	 * 
	 * @param res
	 * @param memberid（用户id）
	 */
	public static String url_memberdetail(String json) {
		String url = null;
		url = base_url + "data/memberdetail?json="+json;
		return url;
	}
	
	/**
	 * 53.查询预约商戶信息 根据用户查询预约的商户
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_getMerchantByMember(String json) {
		String url = null;
		url = base_url + "mdata/getMerchantByMember?json="+json;
		return url;
	}
	
	/**
	 * 54.商户的预约确定 等待确定的预约信息
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_mreserationWaitcofirm(String json) {
		String url = null;
		url = base_url + "mdata/mreserationWaitcofirm?json="+json;
		return url;
	}
	
	/**
	 * 55.商户的预约成功 已经确定的预约信息
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_mreserationSuccess(String json) {
		String url = null;
		url = base_url + "mdata/mreserationSuccess?json="+json;
		return url;
	}
	
	/**
	 * 56.商户预约删除
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_mreserationDel(String json) {
		String url = null;
		url = base_url + "mdata/mreserationDel?json="+json;
		return url;
	}
	
	/**
	 * 57.我在本店的卡卷
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_myCouponOfMerchant(String json) {
		String url = null;
		url = base_url + "mdata/myCouponOfMerchant?json="+json;
		return url;
	}
	
	/**
	 * 58.商家关注增加
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_merchantAttentionAdd(String json) {
		String url = null;
		url = base_url + "mdata/merchantAttentionAdd?json="+json;
		return url;
	}
	
	/**
	 * 59.商家关注取消
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_merchantAttentionDel(String json) {
		String url = null;
		url = base_url + "mdata/merchantAttentionDel?json="+json;
		return url;
	}
	
	/**
	 * 60.商家详情的用户评论列表
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_merchantDetailComment(String json) {
		String url = null;
		url = base_url + "mdata/merchantDetailComment?json="+json;
		return url;
	}
	
	/**
	 * 61.商品图文列表
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_goodsResource(String json) {
		String url = null;
		url = base_url + "mdata/goodsResource?json="+json;
		return url;
	}
	
	/**
	 * 62.评价商品
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_goodsCommentAdd(String json) {
		String url = null;
		url = base_url + "mdata/goodsCommentAdd?json="+json;
		return url;
	}
	
	/**
	 * 63.我的关注批量取消
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_attentionBatchDel(String json) {
		String url = null;
		url = base_url + "data/attentionBatchDel?json="+json;
		return url;
	}
	
	/**
	 * 64.我的预约批量取消
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_reserationBatchDel(String json) {
		String url = null;
		url = base_url + "data/reserationBatchDel?json="+json;
		return url;
	}
	
	/**
	 * 65.我的收藏批量删除
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_collectBatchDel(String json) {
		String url = null;
		url = base_url + "data/collectBatchDel?json="+json;
		return url;
	}
	
	/**
	 * 66.发现 优惠活动（卡券）详情
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_coupondetail(String json) {
		String url = null;
		url = base_url + "data/coupondetail?json="+json;
		return url;
	}
	
	/**
	 * 67.发现 确认领取优惠活动（卡券）
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_confirmcard(String json) {
		String url = null;
		url = base_url + "data/confirmcard?json="+json;
		return url;
	}
	
	/**
	 * 68
	 * 
	 * @param res
	 * @param 
	 */
	/*public static String url_(String json) {
		String url = null;
		url = base_url + "data/register?json="+json;
		return url;
	}*/
	
	/**
	 * 69.赠送卡券
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_sendcoupon(String json) {
		String url = null;
		url = base_url + "data/sendcoupon?json="+json;
		return url;
	}
	
	/**
	 * 70.广告定制列表
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_adverttailor(String json) {
		String url = null;
		url = base_url + "data/adverttailor?json="+json;
		return url;
	}
	
	/**
	 * 71.广告定制详情
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_advertdetail(String json) {
		String url = null;
		url = base_url + "data/advertdetail?json="+json;
		return url;
	}
	
	public static String url_sousuojieguo(String json) {
		String url = null;
		url = base_url + "data/searchList?json="+json;
		Log.d("----", "url = " + url);
		return url;
	}
	
	/**
	 * 71.提交答案
	 * 
	 * @param res
	 * @param 
	 */
	public static String advertanswer(String json) {
		String url = null;
		url = base_url + "data/data/advertanswer?json="+json;
		return url;
	}
		
	/**
	 * 83.我的 确认订单 确认订单
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_confirmgoods(String json) {
		String url = null;
		url = base_url + "data/confirmgoods?json="+json;
		return url;
	}
	
	/**
	 * 84.我的 我的订单 我的订单在线支付已收货
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_myorder(String json) {
		String url = null;
		url = base_url + "data/myorder?json="+json;
		return url;
	}
	
	/**
	 * 85.忘记密码
	 * 
	 * @param res
	 * @param json={"mobile":"15006598533","loginpwd":"333333"}
	 */
	public static String url_resetpsw(String json) {
		String url = null;
		url = base_url + "data/memberforgetpassword?json="+json;
		return url;
	}
	
	/**
	 * 86.支付-接入ping++
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_payping(String json) {
		String url = null;
		url = base_url + "data/pay?json="+json;
		return url;
	}
	
	/**
	 * 87.查询我的积分和金币余额
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_myIntegralAndCoin(String json) {
		String url = null;
		url = base_url + "data/myIntegralAndCoin?json="+json;
		return url;
	}
	
	/**
	 * 88.我的消息
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_mymessage(String json) {
		String url = null;
		url = base_url + "data/mymessage?json="+json;
		return url;
	}
	
	/**
	 * 89.推送消息
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_pushmessage(String json) {
		String url = null;
		url = base_url + "data/pushmessage?json="+json;
		return url;
	}
	
	/**
	 * 90.系统消息
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_systemessage(String json) {
		String url = null;
		url = base_url + "data/systemessage?json="+json;
		return url;
	}
	
	/**
	 * 92.我的 我的消息（批量删除）
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_messagebatchDel(String json) {
		String url = null;
		url = base_url + "data/messagebatchDel?json="+json;
		return url;
	}
	
	/**
	 * 93.我的 我的消息（标记为已读）
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_messageMarkRead(String json) {
		String url = null;
		url = base_url + "data/messageMarkRead?json="+json;
		return url;
	}
	
	/**
	 * 94.金币商城一级菜单
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_mallParentMenu() {
		String url = null;
		url = base_url + "data/mallParentMenu";
		return url;
	}
	
	/**
	 * 95.金币商城二级菜单
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_mallChildMenu(String json) {
		String url = null;
		url = base_url + "data/mallChildMenu?json="+json;
		return url;
	}
	
	/**
	 * 96.金币商城卡券列表
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_mallGoods(String json) {
		String url = null;
		url = base_url + "data/mallGoods?json="+json;
		return url;
	}
	
	/**
	 * 97.商户积分充值
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_merchantIntegralAdd(String json) {
		String url = null;
		url = base_url + "mdata/merchantIntegralAdd?json="+json;
		return url;
	}
	
	/**
	 * 98.商户积分变化记录
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_integralMerchantRecord(String json) {
		String url = null;
		url = base_url + "mdata/integralMerchantRecord?json="+json;
		return url;
	}
	
	/**
	 * 99.商户版 我的积分
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_mIntegral(String json) {
		String url = null;
		url = base_url + "mdata/mIntegral?json="+json;
		return url;
	}
	
	/**
	 * 100.积分提现
	 * 
	 * @param res
	 * @param 
	 */
	public static String drawIntegral(String json) {
		String url = null;
		url = base_url + "data/drawIntegral?json="+json;
		return url;
	}
	
	/**
	 * 101.积分兑换金币
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_exchangeIntegral(String json) {
		String url = null;
		url = base_url + "data/exchangeIntegral?json="+json;
		return url;
	}
	
	/**
	 * 102.我的购物车
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_myShoppingcart(String json) {
		String url = null;
		url = base_url + "data/myShoppingcart?json="+json;
		return url;
	}
	
	/**
	 * 103.添加到购物车
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_shoppingCartAdd(String json) {
		String url = null;
		url = base_url + "data/shoppingCartAdd?json="+json;
		return url;
	}
	
	/**
	 * 104.购物车 输入产品数量
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_shoppingCartChange(String json) {
		String url = null;
		url = base_url + "data/shoppingCartChange?json="+json;
		return url;
	}
	
	/**
	 * 105.购物车 购物车 点击加减
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_shoppingCartAddOrSub(String json) {
		String url = null;
		url = base_url + "data/shoppingCartAddOrSub?json="+json;
		return url;
	}
	
	/**
	 * 105.购物车 购物车 点击删除
	 * 
	 * @param res
	 * @param 
	 */
	public static String shoppingCartDel(String json) {
		String url = null;
		url = base_url + "data/shoppingCartDel?json="+json;
		return url;
	}
	
	/**
	 * 106.订单确认收货 
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_confirmReceive(String json) {
		String url = null;
		url = base_url + "data/confirmReceive?json="+json;
		return url;
	}
	
	/**
	 * 107.我的金币
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_myCoin(String json) {
		String url = null;
		url = base_url + "data/myCoin?json="+json;
		return url;
	}
	
	/**
	 * 109.我的提现账户信息
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_myDrawAccount(String json) {
		String url = null;
		url = base_url + "data/myDrawAccount?json="+json;
		return url;
	}
	
	/**
	 * 110.更新提现账户 
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_updateAccountInfo(String json) {
		String url = null;
		url = base_url + "data/updateAccountInfo?json="+json;
		return url;
	}
	
	/**
	 * 111.商家 我的关注 
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_mattention(String json) {
		String url = null;
		url = base_url + "mdata/mattention?json="+json;
		return url;
	}
	
	/**
	 * 111.商家 我的邀请 
	 * 
	 * @param res
	 * @param 
	 */
	public static String minvite(String json) {
		String url = null;
		url = base_url + "mdata/minvite?json="+json;
		return url;
	}
	
	/**
	 * 112.商家 添加邀请 
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_minviteAdd(String json) {
		String url = null;
		url = base_url + "mdata/mdata/minviteAdd?json="+json;
		return url;
	}
	
	/**
	 * 113.我的邀请人 根据用户id查询 该用户的邀请者 
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_myinviter(String json) {
		String url = null;
		url = base_url + "mdata/data/myinviter?json="+json;
		return url;
	}
	
	/**
	 * 114.商户类型 8个图标 1级菜单
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_() {
		String url = null;
		url = base_url + "data/merchantParentMenu?";
		return url;
	}
	
	/**
	 * 115.商户类型 8个图标 1级菜单 2级菜单
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_merchantChildMenu(String json) {
		String url = null;
		url = base_url + "data/merchantChildMenu?json="+json;
		return url;
	}
	
	/**
	 * 116.区县列表
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_district(String json) {
		String url = null;
		url = base_url + "data/district?json="+json;
		return url;
	}
	
	/**
	 * 117.我的积分
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_myIntegral(String json) {
		String url = null;
		url = base_url + "data/myIntegral?json="+json;
		return url;
	}
	
	/**
	 * 118.商户预约确定
	 * 
	 * @param res
	 * @param 
	 */
/*	public static String url_district(String json) {
		String url = null;
		url = base_url + "data/district?json="+json;
		return url;
	}*/
	
	/**
	 * 119.我的预约填写 到店或者外送 
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_fillReseration(String json) {
		String url = null;
		url = base_url + "data/fillReseration?json="+json;
		return url;
	}
	
	/**
	 * 120.翻菜单 
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_goodsMenu(String json) {
		String url = null;
		url = base_url + "data/goodsMenu?json="+json;
		return url;
	}
	
	/**
	 *  121.商家图文列表
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_merchantResource(String json) {
		String url = null;
		url = base_url + "mdata/merchantResource?json="+json;
		return url;
	}
	
	/**
	 * 122.保存dna
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_saveDna(String json) {
		String url = null;
		url = base_url + "data/saveDna?json="+json;
		return url;
	}
	
	/**
	 * 123.商家产品列表
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_goodsList(String json) {
		String url = null;
		url = base_url + "data/goodsList?json="+json;
		return url;
	}
	
	/**
	 * 124.商家优惠活动
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_merchantCouponList(String json) {
		String url = null;
		url = base_url + "data/merchantCouponList?json="+json;
		return url;
	}
	
	/**
	 * 125.购物车批量生成订单
	 * 
	 * @param res
	 * @param 
	 */
	public static String url_ShoppingCartToorder(String json) {
		String url = null;
		url = base_url + "data/shoppingcarttoorder?json="+json;
		return url;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static final String base_url1 = "http://115.28.21.137/mobile/";
	/**
	 *  城市定位
	 * @param goods_id 商品编号
	 * @param
	 */
	public static void getCityName(AsyncHttpResponseHandler res,String lng,String lat) {
		String url = base_url1 + "index.php?act=area&op=location"+"&lng="+lng+"&lat="+lat;
		client.get(url, res);
	}
}
