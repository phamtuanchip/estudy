package org.estudy.learning.model;

import java.util.Arrays;
import java.util.Collection;

import org.estudy.learning.Util;
import org.exoplatform.services.jcr.util.IdGenerator;

public class ESession {
	public final static String PREF = "eset";
	public final static String NT_NAME = "exo:session";
	public final static String P_TITLE = "exo:title";
	public final static String P_CAT = "exo:catId";
	public final static String P_QUEST = "exo:questId";
	public final static String P_DEC = "exo:description";
	public final static String P_RFLINK = "exo:rflink";
	public final static String P_VLINK = "exo:vlink";

	private String id;
	private String title;
	private String cat;
	private Collection<String> quest;
	private String dec;
	private String rflink;
	private String vlink;

	public ESession(){
		setId(PREF + IdGenerator.generate());
	}
	
	public ESession(String title){
		setId(PREF + IdGenerator.generate());
		setTitle(title);
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDec() {
		return dec;
	}
	public void setDec(String dec) {
		this.dec = dec;
	}
	public String getRflink() {
		return rflink;
	}
	public void setRflink(String rflink) {
		this.rflink = rflink;
	}
	public String getVlink() {
		return vlink;
	}
	public void setVlink(String vlink) {
		this.vlink = vlink;
	}
	public String getCat() {
		return cat;
	}
	public void setCat(String cat) {
		this.cat = cat;
	}
	public Collection<String> getQuest() {
		return quest;
	}
	public void setQuest(String quest) {
		this.quest = Arrays.asList(quest.split(Util.SEMI_COLON));
	}
	
	public void setQuest(Collection<String> quest) {
		this.quest = quest;
	}

}
