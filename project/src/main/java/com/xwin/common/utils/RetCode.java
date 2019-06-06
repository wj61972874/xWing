package com.xwin.common.utils;

/**
 * 客户端接口返回码
 * 
 */
public class RetCode {

	/** 成功 */
	public static final int SUCCESS = 200;

	/** 请求参数不合法 */
	public static final int PARAMS_INVALID = 100;

	/** 验证失败 */
	public static final int FAIL = 101;

	/** 未知错误 */
	public static final int UNKOWN_WRONG = 1;
	
	/** 无效用户会话 */
	public static final int INVALID_SESSION = 2;
}