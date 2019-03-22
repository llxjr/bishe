package com.ssm.promotion.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import com.ssm.promotion.core.entity.Goods;
import com.ssm.promotion.core.entity.Paper;
import com.ssm.promotion.core.entity.Question;
import com.ssm.promotion.core.entity.QuestionType;
import com.ssm.promotion.core.entity.Role;
import com.ssm.promotion.core.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.CharacterData;

import com.ssm.promotion.core.dao.ChapterDao;
//import com.ssm.promotion.core.dao.PaperDao;
import com.ssm.promotion.core.dao.QuestionDao;
import com.ssm.promotion.core.dao.QuestionTypeDao;
import com.ssm.promotion.core.dao.UserDao;
//import com.ssm.promotion.core.dto.GoodsDTO;
import com.ssm.promotion.core.dto.QuestionDTO;
//import com.ssm.promotion.core.service.PaperService;
import com.ssm.promotion.core.service.QuestionService;
import com.ssm.promotion.core.service.RoleService;
import com.ssm.promotion.core.service.UserService;
import com.ssm.promotion.core.util.MD5Util;
import com.ssm.promotion.core.util.StringUtil;

/**
 * @author llz
 * @project_name perfect-ssm
 */
@Service("questionService")
public class QuestionServiceImpl implements QuestionService {

    @Resource
    private QuestionDao questionDao;
    @Resource
    private QuestionTypeDao questionTypeDao;
    @Resource
    private ChapterDao chapterDao;

    @Override
    public List<QuestionDTO> findQuestion(Map<String, Object> map) {
    	List<Question> qList = questionDao.findQuestion(map);
		List<QuestionDTO> qDtos = new ArrayList<QuestionDTO>();
		Question question = null;
		for (int i = 0, size = qList.size(); i < size; ++ i) {
			question = qList.get(i);
			QuestionDTO qDto = new QuestionDTO();
			qDto.setAnswerNum(question.getAnswerNum());
			qDto.setBankType(question.getBankType());
			qDto.setCorrect(question.getCorrect());
			qDto.setCorrectNum(question.getCorrectNum());
			qDto.setLevel(question.getLevel());
			qDto.setParentId(question.getParentId());
			qDto.setStem(question.getStem());
			qDto.setChapter(chapterDao.findById(question.getChapterId()));
			qDto.setQuestionType(questionTypeDao.findById(question.getQuestionType()));
			qDtos.add(qDto);
		}
		return qDtos;
    }

    @Override
    public int updateQuestion(Question question) {
  return questionDao.updateQuestion(question);
    }

    @Override
    public Long getTotalQuestion(Map<String, Object> map) {
        return questionDao.getTotalQuestion(map);
    }

    @Override
    public int addQuestion(Question question) {
    return questionDao.addQuestion(question);
    }

    @Override
    public int deleteQuestion(Integer id) {
       return questionDao.deleteQuestion(id);
    }

	@Override
	public Question findById(Integer id) {
		return questionDao.findById(id);
	}

	@Override
	public List<Question> findAllQuestion() {
		// TODO Auto-generated method stub
		return questionDao.findAllQuestion();
	}

	


}
