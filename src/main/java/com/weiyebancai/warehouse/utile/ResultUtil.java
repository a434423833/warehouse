package com.weiyebancai.warehouse.utile;

import com.weiyebancai.warehouse.pojo.Result;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:返回对象工具类
 * @author CaoHao
 * @2017年11月8日 上午10:21:03
 */

@Configuration
public class ResultUtil {

	public static Result success(Object object) {
		Result result = new Result();
		result.setCode(0);
		result.setMsg("成功");
		result.setData(object);
		return result;
	}

	public static Result success() {
		return success(null);
	}

	public static Result error(Integer code, String msg, Object object) {
		Result result = new Result();
		result.setCode(code);
		result.setMsg(msg);
		result.setData(object);
		return result;
	}

}
