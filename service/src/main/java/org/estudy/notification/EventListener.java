package org.estudy.notification;

import org.exoplatform.container.PortalContainer;
import org.exoplatform.container.component.BaseComponentPlugin;
import org.exoplatform.ws.frameworks.cometd.ContinuationService;

public class EventListener extends BaseComponentPlugin implements EventLifeCycle {

	@Override
	public void preSave(Object t) {
		System.out.println("pre save :" + t.getClass().getName());
		ContinuationService continuation = (ContinuationService) PortalContainer.getInstance().getComponentInstanceOfType(ContinuationService.class);
		continuation.sendMessage("users", "/estudy/notification/messages", t.hashCode());
	}

	@Override
	public void postSave(Object t) {
		System.out.println("Post save :" + t.getClass().getName());
		
	}

	@Override
	public void preDelete(Object t) {
		System.out.println("pre delete :" + t.getClass().getName());
		
	}

	@Override
	public void postDelete(Object t) {
		System.out.println("post delete :" + t.getClass().getName());
		
	}

}
