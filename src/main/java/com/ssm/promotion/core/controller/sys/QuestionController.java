package com.ssm.promotion.core.controller.sys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.promotion.core.common.Constants;
import com.ssm.promotion.core.common.Result;
import com.ssm.promotion.core.common.ResultGenerator;
import com.ssm.promotion.core.controller.base.BasicController;
import com.ssm.promotion.core.dto.QuestionDTO;
import com.ssm.promotion.core.entity.Category;
import com.ssm.promotion.core.entity.PageBean;
import com.ssm.promotion.core.entity.Question;
import com.ssm.promotion.core.entity.User;
import com.ssm.promotion.core.redis.RedisUtil;
import com.ssm.promotion.core.service.QuestionService;
import com.ssm.promotion.core.service.UserService;
import com.ssm.promotion.core.util.MD5Util;
import com.ssm.promotion.core.util.ResponseUtil;
import com.ssm.promotion.core.util.StringUtil;

/**
 * @author llz
 */
@Controller
@RequestMapping("/qf/question")
public class QuestionController extends BasicController {

	@Resource
	private QuestionService questionService;
	@Autowired
	
	private static final Logger log = Logger.getLogger(QuestionController.class);// 日志文件
	

	/**
	 * 跳转tab
	 * 
	 * @return
	 */
	@RequestMapping("/list")
	public String index() {
		return "question/question";
	}

	/**
	 * @param page
	 * @param rows
	 * @param s_user
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/datagrid", method = RequestMethod.POST)
	public String list(
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "rows", required = false) String rows,
			Question q_question, HttpServletResponse response) throws Exception {
		PageBean pageBean = new PageBean(Integer.parseInt(page),
				Integer.parseInt(rows));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("stem", StringUtil.formatLike(q_question.getStem()));
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getPageSize());
		List<QuestionDTO> questionList = questionService.findQuestion(map);
		Long total =questionService.getTotalQuestion(map);
		JSONObject result = new JSONObject();
		JSONArray jsonArray = JSONArray.fromObject(questionList);
		result.put("rows", jsonArray);
		result.put("total", total);
		log.info("request: question/list , map: " + map.toString());
		ResponseUtil.write(response, result);
		return null;
	}
	

	/**
	 * 添加或修改管理员
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "", method = RequestMethod.POST)
	@ResponseBody
	public Result save(@RequestBody Question question) throws Exception {
		int resultTotal = 0;
		resultTotal = questionService.addQuestion(question);
		if (resultTotal > 0) {
			return ResultGenerator.genSuccessResult();
		} else {
			return ResultGenerator.genFailResult("FAIL");
		}
	}

	/**
	 * 修改
	 * 
	 * @param paper
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "", method = RequestMethod.PUT)
	@ResponseBody
	public Result update(@RequestBody Question question) throws Exception {
		
		int resultTotal = questionService.updateQuestion(question);
		System.out.println(question.getId());
		log.info("request: question/update , question: " + question.toString());
		if (resultTotal > 0) {
			return ResultGenerator.genSuccessResult();
		} else {
			return ResultGenerator.genFailResult("FAIL");
		}
	}

	/**
	 * 删除管理员
	 * 
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/{ids}", method = RequestMethod.DELETE)
	@ResponseBody
	public Result delete(@PathVariable(value = "ids") String ids)
			throws Exception {
		if (ids.length() > 20) {
			return ResultGenerator.genFailResult("ERROR");
		}
		String[] idsStr = ids.split(",");
		for (int i = 0; i < idsStr.length; i++) {
			questionService.deleteQuestion(Integer.valueOf(idsStr[i]));
		}
		log.info("request: question/delete , ids: " + ids);
		return ResultGenerator.genSuccessResult();
	}
	/**
	 * 
	 * 查询全部
	 */
	
	@RequestMapping(value = "/findAllQuestion", method = RequestMethod.GET)
	@ResponseBody
	public List<Question> findAllQuestion(){
		
		List<Question> result = null;
		try {
			result = questionService.findAllQuestion();
		} catch (Exception e) {
			log.error("获取所有权限信息失败：" + e.getMessage());
		}
		return result;
	}

}
