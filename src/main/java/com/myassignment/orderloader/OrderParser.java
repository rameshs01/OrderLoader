package com.myassignment.orderloader;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rshanmugam on 4/6/18.
 */
public class OrderParser {

    private String inputDataPath;
    private String outputDirPath;

    private JavaSparkContext sc;

    public OrderParser() {
    }

    public void init(String inputDataPath, String outputDirPath) {

        this.inputDataPath = inputDataPath;
        this.outputDirPath = outputDirPath;

        initSpark();

    }

    private void initSpark() {

        SparkConf conf = new SparkConf();

        if(StringUtils.isEmpty(System.getProperty("spark.master"))) {
            String local = String.format("local[%d]", new Object[]{Integer.valueOf(1)});
            System.err.println("Couldn\'t find spark.master, using " + local);
            conf.setAppName("Order Parser");
            conf.setMaster(local);
        }

        sc = new JavaSparkContext(conf);

    }

    public void parseData() {

        JavaRDD<String> inputRdd = sc.textFile(inputDataPath);
        JavaRDD<String> orderRdd = transformRawRdd(inputRdd);

        addHeader(orderRdd)
                .coalesce(1)
                .saveAsTextFile(outputDirPath);
    }

    public JavaRDD<String> transformRawRdd(JavaRDD<String> orderRawRdd) {

        return orderRawRdd
                .filter(r -> !r.contains("order_id"))   //drop header
                .map(r -> Order.createFromTsv(r))  //parse row
                .map(r -> r.outputAsTsv());

    }

    private JavaRDD<String>  addHeader(JavaRDD<String> orderRdd) {

        //add new header to existing Rdd
        List<String> headerRow = new ArrayList<>(1);
        headerRow.add(Order.getHeader());
        JavaRDD<String> headerRDD = sc.parallelize(headerRow);

        return headerRDD
                .union(orderRdd);
    }

    //for test, it is public
    public JavaSparkContext getSc() {
        return sc;
    }

    public void setSc(JavaSparkContext sc) {
        this.sc = sc;
    }


    public static void main(String[] args) {

        //validate input args
        if(args.length < 2) {
            throw new RuntimeException("Missing input arguments, " +
                    "two arguments required which are input and output directory paths");
        }

        String inputPath = args[0];
        String outputPath = args[1];

        OrderParser parser = new OrderParser();
        parser.init(inputPath, outputPath);

        parser.parseData();
    }



}
