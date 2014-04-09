package org.estudy.schedule;

import javax.jcr.RepositoryException;

public interface TaskSchedule {
	public ETask saveTask(ETask t) throws RepositoryException, Exception;
}
