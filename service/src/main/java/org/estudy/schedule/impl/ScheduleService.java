package org.estudy.schedule.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Value;

import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.TimeZone;
import net.fortuna.ical4j.model.TimeZoneRegistryFactory;
import net.fortuna.ical4j.model.TimeZoneRegistryImpl;
import net.fortuna.ical4j.model.component.VTimeZone;
import net.fortuna.ical4j.model.component.VToDo;
import net.fortuna.ical4j.model.property.TzId;

import org.estudy.learning.Util;
import org.estudy.learning.model.EQuestion;
import org.estudy.learning.storage.impl.JcrDataStorage;
import org.estudy.schedule.EventSchedule;
import org.estudy.schedule.ETask;
import org.estudy.schedule.TaskSchedule;
import org.exoplatform.container.ExoContainer;
import org.exoplatform.container.ExoContainerContext;
import org.exoplatform.services.jcr.RepositoryService;
import org.exoplatform.services.jcr.ext.app.SessionProviderService;
import org.exoplatform.services.jcr.ext.common.SessionProvider;
import org.exoplatform.services.jcr.ext.hierarchy.NodeHierarchyCreator;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;

public class ScheduleService implements TaskSchedule, EventSchedule{
	private NodeHierarchyCreator nodeHierarchyCreator_;
	private RepositoryService repoService_;
	private SessionProviderService sessionProviderService_;
	private static final Log       log                 = ExoLogger.getLogger("ScheduleService.class");
	public ScheduleService(NodeHierarchyCreator nodeHierarchyCreator,
			RepositoryService repoService) {
		nodeHierarchyCreator_ = nodeHierarchyCreator;
		repoService_ = repoService;
		ExoContainer container = ExoContainerContext.getCurrentContainer();
		sessionProviderService_ = (SessionProviderService) container.getComponentInstanceOfType(SessionProviderService.class);

	}
	public Node getEStorageHome() throws RepositoryException, Exception {
		SessionProvider sProvider = createSessionProvider();
		Node publicApp = sProvider.getSession(repoService_.getCurrentRepository().getConfiguration().getDefaultWorkspaceName(), repoService_.getCurrentRepository()).getRootNode(); 
		try {
			return publicApp.getNode(Util.E_STUDY_APP);
		} catch (PathNotFoundException e) {
			Node eApp = publicApp.addNode(Util.E_STUDY_APP, Util.NT_UNSTRUCTURED);
			publicApp.getSession().save();
			return eApp;
		}
	}

	public Node getEScheduleHome() throws RepositoryException, Exception{
		Node eHome = getEStorageHome();
		try {
			return  eHome.getNode(Util.E_STUDY_SCH);
		} catch (PathNotFoundException e) {
			eHome.addNode(Util.E_STUDY_SCH, Util.NT_UNSTRUCTURED);
			eHome.getSession().save();
			return eHome.getNode(Util.E_STUDY_SCH);
		}  
	}

	public Node getETaskHome() throws RepositoryException, Exception{
		Node eHome = getEScheduleHome();
		try {
			return  eHome.getNode(Util.E_STUDY_TSK);
		} catch (PathNotFoundException e) {
			eHome.addNode(Util.E_STUDY_TSK, Util.NT_UNSTRUCTURED);
			eHome.getSession().save();
			return eHome.getNode(Util.E_STUDY_TSK);
		}  
	}
	public Node getEEventHome() throws RepositoryException, Exception{
		Node eHome = getEScheduleHome();
		try {
			return  eHome.getNode(Util.E_STUDY_EVT);
		} catch (PathNotFoundException e) {
			eHome.addNode(Util.E_STUDY_EVT, Util.NT_UNSTRUCTURED);
			eHome.getSession().save();
			return eHome.getNode(Util.E_STUDY_EVT);
		}  
	}
	private Node setTaskProp(ETask e, Node ques){
		try {
			ques.setProperty(ETask.P_SUMMARY, e.getSummary().getValue());
			ques.setProperty(ETask.P_DATE_START, e.getCalStart());
			//ques.setProperty(ETask.P_PRIORITY, e.getPriority().getLevel());
			//ques.setProperty(ETask.P_DUE, e.getDue().getValue());
			//ques.setProperty(ETask.P_ORGANIZER, e.getOrganizer().getValue());
			//ques.setProperty(ETask.P_STATUS, e.getStatus().getValue());
		} catch (Exception e2) {
			e2.printStackTrace();
			log.info(e2.getMessage());
			return null ;
		}
		return ques;
	}
	private ETask getTaskProp(Node ques){
		ETask e = new ETask();
		try {
			e.setId(ques.getName());
			if(ques.hasProperty(ETask.P_SUMMARY)) {
				e.setSummary(ques.getProperty(ETask.P_SUMMARY).getString());
			}
			if(ques.hasProperty(ETask.P_STATUS)) 
			{
				e.setStatus(ques.getProperty(ETask.P_STATUS).getString());
			}

			if(ques.hasProperty(ETask.P_DATE_START)) 
			{
				e.setDtStart(ques.getProperty(ETask.P_DATE_START).getDate().getTime());
			}

			if(ques.hasProperty(ETask.P_ORGANIZER)) 
			{
				e.setOrganizer(ques.getProperty(ETask.P_ORGANIZER).getString());
			}
			if(ques.hasProperty(ETask.P_DUE)) {
				SimpleDateFormat sd = new SimpleDateFormat();
				e.setDue(sd.parse(ques.getProperty(ETask.P_ORGANIZER).getString()));
			}
			if(ques.hasProperty(ETask.P_PRIORITY)) {
				e.setPriority(ques.getProperty(ETask.P_PRIORITY).getLong());
			}
		} catch (Exception e2) {
			e2.printStackTrace();
			log.info(e2.getMessage());
			return null ;
		}
		return e;
	}
	private SessionProvider createSessionProvider() {
		SessionProvider provider = sessionProviderService_.getSessionProvider(null);
		if (provider == null) {
			//log.info("No user session provider was available, trying to use a system session provider");
			provider = sessionProviderService_.getSystemSessionProvider(null);
		}
		return SessionProvider.createSystemProvider();
	}

	public ETask saveTask(ETask t) throws RepositoryException, Exception{
		Node p = getETaskHome();
		Node tn = setTaskProp(t, p.addNode(t.getId(), ETask.NT_NAME));
		if(tn.isNew()) p.save();
		else tn.save();
		return t ;
	}

}
