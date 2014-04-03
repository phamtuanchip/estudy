package org.estudy.learning.model;

import java.io.Serializable;

import org.exoplatform.services.jcr.util.IdGenerator;


public class ECategory implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7187747216395398874L;
	public final static String PREF = "ecat";
	public final static String NT_NAME = "exo:category";
	public final static String P_NAME = "exo:categoryname";
	private String id;
	private String name;

	public ECategory(){
		id = PREF + IdGenerator.generate();
	}
	public ECategory(String name) {
		id = PREF + IdGenerator.generate();
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

}
