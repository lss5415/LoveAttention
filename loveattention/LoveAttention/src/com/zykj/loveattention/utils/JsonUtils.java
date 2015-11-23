package com.zykj.loveattention.utils;

import java.util.Map;

import com.google.gson.Gson;

public class JsonUtils {
	
	public static String toJson(Map<String, String> map)
	{
		Gson gson = new Gson();
		String json = gson.toJson(map);
		return json;
	}
	
	

}
