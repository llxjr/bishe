package com.ssm.promotion.core.dto;

import java.io.Serializable;

import com.ssm.promotion.core.entity.Chapter;
import com.ssm.promotion.core.entity.QuestionType;

public class QuestionDTO implements Serializable{
	private Chapter chapter;
	private int parentId;
	private int level;
	private QuestionType questionType;
	private String stem;
	private int answerNum;
	private int correctNum;
	private int value;
	private int correct;
	private int bankType;
	
	public Chapter getChapter() {
		return chapter;
	}
	public void setChapter(Chapter chapter) {
		this.chapter = chapter;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public QuestionType getQuestionType() {
		return questionType;
	}
	public void setQuestionType(QuestionType questionType) {
		this.questionType = questionType;
	}
	public String getStem() {
		return stem;
	}
	public void setStem(String stem) {
		this.stem = stem;
	}
	public int getAnswerNum() {
		return answerNum;
	}
	public void setAnswerNum(int answerNum) {
		this.answerNum = answerNum;
	}
	public int getCorrectNum() {
		return correctNum;
	}
	public void setCorrectNum(int correctNum) {
		this.correctNum = correctNum;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public int getCorrect() {
		return correct;
	}
	public void setCorrect(int correct) {
		this.correct = correct;
	}
	public int getBankType() {
		return bankType;
	}
	public void setBankType(int bankType) {
		this.bankType = bankType;
	}
	
}
