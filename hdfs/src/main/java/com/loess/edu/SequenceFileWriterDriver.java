package com.loess.edu;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 * The entry point for the Sequence Writer App example,
 * which setup the Hadoop job with MapReduce Classes
 *
 * @author Raman
 *
 */
public class SequenceFileWriterDriver extends Configured implements Tool
{
    /**
     * Main function which calls the run method and passes the args using ToolRunner
     * @param args Two arguments input and output file paths
     * @throws Exception
     */
    public static void main(String[] args) throws Exception{
        String[] arr = new String[2];
        arr[0]="G:\\BIGDATA\\HBase\\资料\\数据集\\WATER_BILL.TSV";
        arr[1]="G:\\BIGDATA\\HBase\\资料\\数据集\\1.TSV";
        int exitCode = ToolRunner.run(new SequenceFileWriterDriver(), arr);
        System.exit(exitCode);
    }

    /**
     * Run method which schedules the Hadoop Job
     * @param args Arguments passed in main function
     */
    public int run(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.printf("Usage: %s needs two arguments   files\n",
                    getClass().getSimpleName());
            return -1;
        }

        //Initialize the Hadoop job and set the jar as well as the name of the Job
        Job job = Job.getInstance(getConf(), "sequenceFileWriter");
        job.setJarByClass(SequenceFileWriterDriver.class);
        job.setJobName("sequenceFileWriter");

        //Add input and output file paths to job based on the arguments passed
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        job.setInputFormatClass(KeyValueTextInputFormat.class);
        job.setOutputFormatClass(SequenceFileOutputFormat.class);

        //Set the MapClass and ReduceClass in the job
        job.setMapperClass(SequenceFileWriterMapper.class);

        //Setting the number of reducer tasks to 0 as we do not
        //have any reduce tasks in this example. We are only concentrating on the Mapper
        job.setNumReduceTasks(0);


        //Wait for the job to complete and print if the job was successful or not
        int returnValue = job.waitForCompletion(true) ? 0:1;

        if(job.isSuccessful()) {
            System.out.println("Job was successful");
        } else if(!job.isSuccessful()) {
            System.out.println("Job was not successful");
        }

        return returnValue;
    }
}
