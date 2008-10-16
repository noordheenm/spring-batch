package org.springframework.batch.core.partition;

import java.util.Set;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.StepExecution;

/**
 * Strategy interface for generating input contexts for a partitioned step
 * execution.
 * 
 * @author Dave Syer
 * 
 */
public interface StepExecutionSplitter {

	/**
	 * The name of the step configuration that will be executed remotely. Remote
	 * workers are going to execute a the same step for each execution context
	 * in the partition.
	 * @return the name of the step that will execute the business logic
	 */
	String getStepName();

	/**
	 * Partition the provided {@link StepExecution} into a set of parallel
	 * executable instances with the same parent {@link JobExecution}. The grid
	 * size will be treated as a hint for the size of the collection to be
	 * returned. It may or may not correspond to the physical size of an
	 * execution grid.<br/>
	 * <br/>
	 * 
	 * On a restart clients of the {@link StepExecutionSplitter} should expect
	 * it to reconstitute the state of the last failed execution and only return
	 * those executions that need to be restarted. Thus the grid size hint
	 * should be ignored on a restart.
	 * 
	 * @param stepExecution the {@link StepExecution} to be partitioned.
	 * @param gridSize a hint for the splitter if the size of the grid is known
	 * @return a set of {@link StepExecution} instances for remote processing
	 * 
	 * @throws JobExecutionException if the split cannot be made
	 */
	Set<StepExecution> split(StepExecution stepExecution, int gridSize) throws JobExecutionException;

}