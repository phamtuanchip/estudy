package org.estudy.schedule;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.component.VToDo;
import net.fortuna.ical4j.model.property.Summary;

import org.exoplatform.services.jcr.util.IdGenerator;

public class ETask extends VToDo {
	public final static String PREF = "etask";
	public final static String NT_NAME = "exo:task";
	public final static String P_SUMMARY = "exo:summary";
	public final static String P_DATE_START = "exo:dtstart";
	public final static String P_PRIORITY = "exo:priority";
	public final static String P_DUE = "exo:due";
	public final static String P_STATUS = "exo:status";
	public final static String P_ORGANIZER = "exo:organizer";

	/*
    
          Component Name: VTODO
  
          Purpose: Provide a grouping of calendar properties that describe a
          to-do.
  
          Formal Definition: A "VTODO" calendar component is defined by the
          following notation:
  
            todoc      = "BEGIN" ":" "VTODO" CRLF
                         todoprop *alarmc
                         "END" ":" "VTODO" CRLF
  
            todoprop   = *(
  
                       ; the following are optional,
                       ; but MUST NOT occur more than once
  
                       class / completed / created / description / dtstamp /
                       dtstart / geo / last-mod / location / organizer /
                       percent / priority / recurid / seq / status /
                       summary / uid / url /
  
                       ; either 'due' or 'duration' may appear in
                       ; a 'todoprop', but 'due' and 'duration'
                       ; MUST NOT occur in the same 'todoprop'
  
                       due / duration /
  
                       ; the following are optional,
                       ; and MAY occur more than once
                       attach / attendee / categories / comment / contact /
                       exdate / exrule / rstatus / related / resources /
                       rdate / rrule / x-prop
  
                       )
 
    */
	private String id;
	private String summary;
	private Date dtStart;
	private long priority;
	private Date due;
	private String status;
	private String organizer;
	
	public ETask(){
		
	}
	public ETask(DateTime dateTime, DateTime dateTime2, String string) {
		super(dateTime, dateTime2, string);
		setId(PREF + IdGenerator.generate());
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	 
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public Date getDtStart() {
		return super.getStartDate().getDate();
	}
	public Calendar getCalStart(){
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(getDtStart());
		return cal;
	}
	public void setDtStart(Date dtStart) {
		this.dtStart = dtStart;
	}
	 
	public void setStatus(String status) {
		this.status = status;
	}
	public void setOrganizer(String organizer) {
		this.organizer = organizer;
	}
	public void setDue(Date due) {
		this.due = due;
	}
	public void setPriority(long priority) {
		this.priority = priority;
	}

}
