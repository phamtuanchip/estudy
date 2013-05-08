package org.estudy.learning.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.exoplatform.services.jcr.util.IdGenerator;

public class ETesting {
	public final static String PREF = "etest";
	public final static String NT_NAME = "exo:testing";
	public final static String P_TIME = "exo:time";
	public final static String P_NOTE= "exo:note";
	public final static String P_POINT = "exo:point";
	public final static String P_RESULT = "exo:result";

	private String id;
	private long time;
	private String note;
	private long point;
	private long totalPoint;
	private Map<String, Collection<String>> quest = new HashMap<String, Collection<String>>() ;
	
	public ETesting(){
		setId(PREF + IdGenerator.generate());
	}
	
	public ETesting(long time){
		setId(PREF + IdGenerator.generate());
		setTime(time);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public long getPoint() {
		return point;
	}

	public void setPoint(long point) {
		this.point = point;
	}

	public long getTotalPoint() {
		return totalPoint;
	}

	public void setTotalPoint(long totalPoint) {
		this.totalPoint = totalPoint;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Collection<String> getQuestId() {
		return quest.keySet();
	}

	public void setQuest(Map<String, Collection<String>> quest) {
		this.quest = quest;
	}
	
	public Map<String, Collection<String>> getQuest() {
		return quest;
	}
	
	public Collection<String> getAnswered(String questId){
		return this.quest.get(questId);
	}



}
