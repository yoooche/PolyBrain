package com.polybrain.util;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Constants {
	public static final Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
	public static final Gson GSON_DATETIME = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:MM:ss").create();
	public static final String JSON_MIME_TYPE = "application/json";

	public static final String SESSION_MEM_NO = "memNo";
	public static final String LOGIN_PAGE = "/view/member/login.html";
}