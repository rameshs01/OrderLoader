# OrderLoader

Note : this is a test process / not for any production use.

This distributed process accepts input data as CSV file with specified format and converting it into the defined output format. Parser implemented using Apache Spark and it can be deployed to any Hadoop cluster. Also, it can be launched as java process.

Implementation details:-
  The code is written using Java 8 and Apache Spark used to process it distributed using Map Reduce. RDD API only used.
  Maven build tool that creates an uber-jar and also creates final distribution tar that can be deployed.
  Tested with Hortonworks(HDP) / Azure cloud cluster.
  The Order parser does validation for bad data, it validates only when data is present. Not having value for a field is   accepted.
  
 
Build from source, "mvn clean package". it will create distrubuted tar at /target/OrderLoader-x.x.x-dist.tar.gz 
    
Deployment details

1) Extract, tar -xvf OrderLoader-x.x.x-dist.tar.gz -C /YourDeployLocation/OrdlerLoader

2) cd /YourDeployLocation/OrdlerLoader

3) To run it as a java process
   ./run.sh  ${input file path} ${output dir path}
   
   2 arguments, input and output file path should be local file system path. it does not accept HDFS or AWS dfs path.
   
4) To run it Hadoop cluster
   ./runCluster.sh  ${input file path} ${output dir path} 
   
   input and output file path should be local your DFS path whic your cluster connected to.
   
Note: the process creates output directory an given output path. so the output dir should not have created before otherwise process will fail. it is intentional not to delete output path from a process.


To read the code, start from the "com.myassignment.orderloader.OrderParser" which is the main class.
