package com.myassignment.orderloader;

import com.myassignment.orderloader.OrderParser;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rshanmugam on 4/6/18.
 */
public class OrderParserTest {

    /**public void testParser() {

        String inputPath = "/Users/rshanmugam/Documents/12dm/workfolder/orderloader/scripting_challenge_input_file.txt";
        String outputPath = "/Users/rshanmugam/Documents/12dm/workfolder/orderloader/scripting_challenge_output_file.txt";

        OrderParser parser = new OrderParser();
        parser.init(inputPath, outputPath);
        parser.parseData();
    }**/

    @Test
    public void testParser() {

        List<String> inputRows = new ArrayList<String>();

        String row1 = "54374\t2015-05-01\t123\t15.00\thttp://www.test-cpc.com/favorites\t\n";
        inputRows.add(row1);

        String row2 = "54356\t2015-05-01\t124\t43.10\thttp://www.test-cpc.com/favorites\t\n";
        inputRows.add(row2);

        String row3 = "54234\t2015-05-01\t123\t37.07\thttp://www.test-cpc.com/favorites\t\n";
        inputRows.add(row3);

        OrderParser parser = new OrderParser();
        parser.init(null, null);

        JavaSparkContext sc = parser.getSc();

        JavaRDD<String> convertedRdd = parser.transformRawRdd(sc.parallelize(inputRows));

        Assert.assertEquals(inputRows.size(), convertedRdd.count());


    }




}
