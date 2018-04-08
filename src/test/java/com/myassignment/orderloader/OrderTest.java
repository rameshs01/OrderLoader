package com.myassignment.orderloader;

import org.junit.Assert;
import org.junit.Test;
import scala.Int;

/**
 * Created by rshanmugam on 4/7/18.
 */
public class OrderTest {

    @Test
    public void testParser() {

        String ORDER_ID = "2324";
        String ORDER_DATE= "20180407";
        String USER_ID = "2222";
        Double ITEM_PRICE_1 = 10d;
        Double ITEM_PRICE_2 = 20d;
        Double ITEM_PRICE_3 = 30d;
        Double ITEM_PRICE_4 = 40d;
        String START_PAGE_URL = "http://www.test-cpc.com/search";

        String inputStr = String.format("%s:%s\t%s\t%s\t%s\t%s\t%s\t%s\t",
                ORDER_ID, ORDER_DATE , USER_ID,
                    ITEM_PRICE_1, ITEM_PRICE_2, ITEM_PRICE_3, ITEM_PRICE_4,
                    START_PAGE_URL);

        String outputStr = Order.createFromTsv(inputStr)
                    .outputAsTsv();

        String outputFields[] = outputStr.split("\t", -1);

        Assert.assertEquals("Invalid output row", 6, outputFields.length);
        Assert.assertEquals("Invalid Order Id", ORDER_ID, outputFields[0]);
        Assert.assertEquals("Invalid Order Date","2018-04-07", outputFields[1]);
        Assert.assertEquals("Invalid User Id",  USER_ID, outputFields[2]);
        Assert.assertEquals("Invalid Avg Item Price",  "25.00", outputFields[3]);
        Assert.assertEquals("Invalid Page Url",  START_PAGE_URL, outputFields[4]);
        Assert.assertEquals("Error msg should be empty",  "", outputFields[5]);

    }

    @Test
    public void testInvalidRow() {

        String inputRow = "test\t";
        String outputStr = Order.createFromTsv(inputRow)
                .outputAsTsv();

        String outputFields[] = outputStr.split("\t", -1);
        Assert.assertEquals( "Invalid Row", outputFields[5]);



    }

    @Test
    public void testInvalidOrderDate() {

        String ORDER_ID = "2324";
        String ORDER_DATE= "1234";
        String USER_ID = "2222";
        Double ITEM_PRICE_1 = 10d;
        Double ITEM_PRICE_2 = 20d;
        Double ITEM_PRICE_3 = 30d;
        Double ITEM_PRICE_4 = 40d;
        String START_PAGE_URL = "http://www.test-cpc.com/search";

        String inputStr = String.format("%s:%s\t%s\t%s\t%s\t%s\t%s\t%s\t",
                ORDER_ID, ORDER_DATE , USER_ID,
                ITEM_PRICE_1, ITEM_PRICE_2, ITEM_PRICE_3, ITEM_PRICE_4,
                START_PAGE_URL);

        String outputStr = Order.createFromTsv(inputStr)
                .outputAsTsv();

        String outputFields[] = outputStr.split("\t", -1);
        Assert.assertEquals( "Invalid Order Date", outputFields[5]);

    }

    @Test
    public void testInvalidPageUrl() {

        String ORDER_ID = "2324";
        String ORDER_DATE= "20180407";
        String USER_ID = "2222";
        Double ITEM_PRICE_1 = 10d;
        Double ITEM_PRICE_2 = 20d;
        Double ITEM_PRICE_3 = 30d;
        Double ITEM_PRICE_4 = 40d;
        String START_PAGE_URL = "http://www.yahoo.com";

        String inputStr = String.format("%s:%s\t%s\t%s\t%s\t%s\t%s\t%s\t",
                ORDER_ID, ORDER_DATE , USER_ID,
                ITEM_PRICE_1, ITEM_PRICE_2, ITEM_PRICE_3, ITEM_PRICE_4,
                START_PAGE_URL);

        String outputStr = Order.createFromTsv(inputStr)
                .outputAsTsv();

        String outputFields[] = outputStr.split("\t", -1);
        Assert.assertEquals( "", outputFields[4]);
        Assert.assertEquals("Invalid Page Url", outputFields[5]);


    }

    @Test
    public void testEmptyColumns() {

        String inputStr = String.format("%s:%s\t%s\t%s\t%s\t%s\t%s\t%s",
                "", "" , "", "", "", "", "", "");
        String outputStr = Order.createFromTsv(inputStr)
                .outputAsTsv();

        String outputFields[] = outputStr.split("\t", -1);

        Assert.assertEquals( "", outputFields[5]);

    }

}
