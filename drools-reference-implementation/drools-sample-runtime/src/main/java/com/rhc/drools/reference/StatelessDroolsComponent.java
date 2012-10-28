package com.rhc.drools.reference;

import java.util.List;
import java.util.Map;

import org.drools.command.Command;
import org.drools.event.rule.AfterActivationFiredEvent;
import org.drools.runtime.ExecutionResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A component that identifies all concerns for a stateless Drools interaction in a Java Application wraps them all in a
 * request/response API.
 * 
 */
public class StatelessDroolsComponent<Request, Response> {

	private static Logger logger = LoggerFactory.getLogger( StatelessDroolsComponent.class );

	// Drools Concern #1
	private KnowledgeBaseBuilder kBaseBuilder;
	// Drools Concern #2
	private CommandListBuilder<Request> commandListBuilder;
	// Drools Concern #3
	private StatelessDroolsRuntime droolsRuntime;
	// Drools Concern #4
	private ExecutionResultsTransformer<Response> resultsTransformer;

	public StatelessDroolsComponent( KnowledgeBaseBuilder kBaseBuilder, CommandListBuilder<Request> commandListBuilder,
			StatelessDroolsRuntime droolsRuntime, ExecutionResultsTransformer<Response> resultsTransformer ) {

		this.kBaseBuilder = kBaseBuilder;
		this.commandListBuilder = commandListBuilder;
		this.droolsRuntime = droolsRuntime;
		this.resultsTransformer = resultsTransformer;
	}

	public StatelessDroolsComponent() {
	}

	@SuppressWarnings("rawtypes")
	public Response executeAllRules( Request request ) {

		List<Command> commandList = commandListBuilder.buildBusinessLogicCommandList( request );

		// append the queries to the end of the list so they are executed after the business logic
		commandList.addAll( resultsTransformer.getQueryCommands() );

		long startTime = System.currentTimeMillis();
		logger.debug( "Executing Drools Application..." );
		ExecutionResults results = droolsRuntime.executeCommandList( kBaseBuilder.getKnowledgeBase(), commandList );
		logger.debug( "Executing Drools Application took " + ( System.currentTimeMillis() - startTime ) + " ms" );

		Response response = resultsTransformer.transform( results );

		return response;
	}

	/**
	 * Convenience method for testing to return activations from previous the previous execution.
	 * 
	 * @return Map<Rule name, List<Activations of that rule fired>>
	 */
	public Map<String, List<AfterActivationFiredEvent>> getPreviouslyFiredActivations() {
		return droolsRuntime.getFiredActivations();
	}

	public void setKnowledgeBaseBuilder( KnowledgeBaseBuilder kBaseBuilder ) {
		this.kBaseBuilder = kBaseBuilder;
	}

	public void setCommandListBuilder( CommandListBuilder<Request> commandListBuilder ) {
		this.commandListBuilder = commandListBuilder;
	}

	public void setDroolsRuntime( StatelessDroolsRuntime droolsRuntime ) {
		this.droolsRuntime = droolsRuntime;
	}

	public void setResultsTransformer( ExecutionResultsTransformer<Response> resultsTransformer ) {
		this.resultsTransformer = resultsTransformer;
	}

}