package com.zykj.loveattention.socket;

import com.zykj.loveattention.data.ResultBean;


/**
 * 网路回调
 * 
 * @author bin
 * 
 */

public interface SocketListener {
	public void response(ResultBean result);
}
