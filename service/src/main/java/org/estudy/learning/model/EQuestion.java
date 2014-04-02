package org.estudy.learning.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.estudy.learning.Util;
import org.exoplatform.services.jcr.util.IdGenerator;

public class EQuestion implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 788575679364439614L;
	public final static String PREF = "equest";
	public final static String NT_NAME = "exo:question";
	public final static String P_TITLE = "exo:title";
	public final static String P_ANSWER = "exo:answer";
	public final static String P_CORRECT = "exo:correct";
	public final static String P_ANSWERED = "exo:answered";
	public final static String P_POINT = "exo:point";

	private String id;
	private String title;
	private ArrayList<String> answered;
	private long point;
	private ArrayList<String> answers;
	private ArrayList<String> correct;

	public EQuestion(){
		setId(PREF + IdGenerator.generate());
	}
	
	public EQuestion(String title){
		setId(PREF + IdGenerator.generate());
		setTitle(title);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ArrayList<String> getAnswers() {
		return answers;
	}

	public void setAnswers(ArrayList<String> answers) {
		this.answers = answers;
	}
	
	public void setAnswers(String answers) {
		this.answers = new ArrayList<String>(Arrays.asList(answers.split(Util.SEMI_COLON)));
	}

	public long getPoint() {
		return point;
	}

	public void setPoint(long point) {
		this.point = point;
	}

	public ArrayList<String> getAnswered() {
		return answered;
	}
	public void setAnswered(String answered) {
		this.answered = new ArrayList<String>(Arrays.asList(answered.split(Util.SEMI_COLON)));
	}
	public void setAnswered(ArrayList<String> answered) {
		this.answered = answered;
	}

	public ArrayList<String> getCorrect() {
		return correct;
	}

	public void setCorrect(ArrayList<String> correct) {
		this.correct = correct;
	}
	
	public void setCorrect(String correct) {
		this.correct = new ArrayList<String>(Arrays.asList(correct.split(Util.SEMI_COLON)));
	}
}
