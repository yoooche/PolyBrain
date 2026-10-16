package com.polybrain.util;

import java.io.BufferedReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.polybrain.util.Constants.*;

public class CommonUtil {
	public static <P> P json2Pojo(HttpServletRequest request, Class<P> classOfPojo) {
		try (BufferedReader br = request.getReader()) {
			System.out.println("有到json2Pojo");
			return GSON.fromJson(br, classOfPojo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static <P> P json2PojoForDatetime(HttpServletRequest request, Class<P> classOfPojo) {
		try (BufferedReader br = request.getReader()) {
			System.out.println("有到json2Pojo");
			return GSON_DATETIME.fromJson(br, classOfPojo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/** null 當成空字串，其餘去掉前後空白；給 req.getParameter() 用，省掉每次都要判斷 null */
	public static String trimToEmpty(String value) {
		return value == null ? "" : value.trim();
	}

	/**
	 * 不關閉 writer：關掉回應就會馬上送出，前端可能在 Filter commit 之前就收到並發下一個請求，讀到舊資料。
	 * 留給 Tomcat 在整個請求（含 Filter commit）結束後再送。
	 */
	public static <P> void writePojo2Json(HttpServletResponse response, P pojo) {
		response.setContentType(JSON_MIME_TYPE + "; charset=UTF-8");
		try {
			response.getWriter().print(GSON.toJson(pojo));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
