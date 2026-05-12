package com.workorderhub.core.caseuse.supervisor;

public interface SupervisorMainInput {

	void retrieveWorkFront();

	void retrieveClosedWork(RequestClosedWork request);
}
