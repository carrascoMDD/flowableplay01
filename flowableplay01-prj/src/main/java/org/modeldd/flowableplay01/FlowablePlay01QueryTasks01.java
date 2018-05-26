/*
 * org.modeldd.flowableplay01.FlowablePlay01QueryTasks01.java
 *
 * Created @author Antonio Carrasco Valero 201805252222
 *
 *
 ***************************************************************************

 Copyright 2018 Antonio Carrasco Valero
 Setup a Flowable ( https://github.com/flowable/flowable-engine) playground as standalone Java application.
 
Licensed under the EUPL, Version 1.1 only (the "Licence");
You may not use this work except in compliance with the
Licence.
You may obtain a copy of the Licence at:
https://joinup.ec.europa.eu/software/page/eupl/licence-eupl
Unless required by applicable law or agreed to in
writing, software distributed under the Licence is
distributed on an "AS IS" basis,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
express or implied.
See the Licence for the specific language governing
permissions and limitations under the Licence.
 {{License2}}

 {{Licensed1}}
 {{Licensed2}}

 ***************************************************************************
 *
 */
package org.modeldd.flowableplay01;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;

public class FlowablePlay01QueryTasks01 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// From FlowablePlay01LaunchProcessEngine.java
		ProcessEngineConfiguration cfg = new StandaloneProcessEngineConfiguration()
				.setJdbcUrl("jdbc:h2:mem:flowable;DB_CLOSE_DELAY=-1").setJdbcUsername("sa").setJdbcPassword("")
				.setJdbcDriver("org.h2.Driver")
				.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);

		ProcessEngine processEngine = cfg.buildProcessEngine();

		
		// From FlowablePlay01DeployBPMN20spec.java
		RepositoryService repositoryService = processEngine.getRepositoryService();
		Deployment deployment = repositoryService.createDeployment().addClasspathResource("flowableplay01.bpmn20.xml")
				.deploy();

		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
				.deploymentId(deployment.getId()).singleResult();
		System.out.println("org.modeldd.flowableplay01.FlowablePlay01QueryTasks01 Found process definition : "
				+ processDefinition.getName());

		
		// From FlowablePlay01RunProceessInstance01.java
		Scanner scanner = new Scanner(System.in);

		String employee = "";
		Integer nrOfHolidays = 0;
		String description = "";
		try {
			System.out.println("Who are you?");
			employee = scanner.nextLine();
	
			System.out.println("How many holidays do you want to request?");
			nrOfHolidays = Integer.valueOf(scanner.nextLine());
	
			System.out.println("Why do you need them?");
			description = scanner.nextLine();
		}
		finally {
			if( !( scanner == null)) {
				scanner.close();
				scanner=null;
			}
		}
		RuntimeService runtimeService = processEngine.getRuntimeService();

		System.out.println("org.modeldd.flowableplay01.FlowablePlay01RunProceessInstance01 Input process vars to instantiate process definition : "
				+ processDefinition.getName());
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("employee", employee);
		variables.put("nrOfHolidays", nrOfHolidays);
		variables.put("description", description);
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("flowableplay01", variables);
		
		
		// From FlowablePlay01QueryTasks01.java
		System.out.println("org.modeldd.flowableplay01.FlowablePlay01QueryTasks01 List tasks for managers in instance(s) of process definition : "
				+ processDefinition.getName());
		TaskService taskService = processEngine.getTaskService();
		List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("managers").list();
		System.out.println("You have " + tasks.size() + " tasks:");
		for (int i=0; i<tasks.size(); i++) {
		  System.out.println((i+1) + ") " + tasks.get(i).getName());
		}
		System.out.println("You have " + tasks.size() + " tasks (detailed):");
		for (int i=0; i<tasks.size(); i++) {
		  Task task = tasks.get(i);
		  Map<String, Object> processVariables = taskService.getVariables(task.getId());
		  System.out.println( (i+1) + ") " + task.getName() + ": " + processVariables.get("employee") + " wants " +
		      processVariables.get("nrOfHolidays") + " days of holidays.");
		}
		
	}

}
