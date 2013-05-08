package org.estudy.learning.model;

import java.util.Arrays;
import java.util.Collection;

import org.estudy.learning.Util;
import org.exoplatform.services.jcr.util.IdGenerator;

public class EQuestion {
	public final static String PREF = "equest";
	public final static String NT_NAME = "exo:question";
	public final static String P_TITLE = "exo:title";
	public final static String P_ANSWER = "exo:answer";
	public final static String P_CORRECT = "exo:correct";
	public final static String P_ANSWERED = "exo:answered";
	public final static String P_POINT = "exo:point";

	private String id;
	private String title;
	private Collection<String> answered;
	private long point;
	private Collection<String> answers;
	private Collection<String> correct;

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

	public Collection<String> getAnswers() {
		return answers;
	}

	public void setAnswers(Collection<String> answers) {
		this.answers = answers;
	}
	
	public void setAnswers(String answers) {
		this.answers = Arrays.asList(answers.split(Util.SEMI_COLON));
	}

	public long getPoint() {
		return point;
	}

	public void setPoint(long point) {
		this.point = point;
	}

	public Collection<String> getAnswered() {
		return answered;
	}
	public void setAnswered(String answered) {
		this.answered = Arrays.asList(answered.split(Util.SEMI_COLON));
	}
	public void setAnswered(Collection<String> answered) {
		this.answered = answered;
	}

	public Collection<String> getCorrect() {
		return correct;
	}

	public void setCorrect(Collection<String> correct) {
		this.correct = correct;
	}
	
	public void setCorrect(String correct) {
		this.correct = Arrays.asList(correct.split(Util.SEMI_COLON));
	}
}
