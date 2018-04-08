package com.myassignment.orderloader;

/**
 * Created by rshanmugam on 4/7/18.
 */
public interface OrderFileLayout {

    String FILE_SEPARATOR = "\t";
    int TOTAL_NO_OF_FIELDS = 7;
    String ORDER_DATE_FROMAT = "yyyyMMdd";
    String VALID_PAGE_URL_PREFIX = "http://www.test-cpc.com";


    int ORDER_ID_DATE = 0;
    int USER_ID = 1;
    int ITEM_PRICE_1 = 2;
    int ITEM_PRICE_2 = 3;
    int ITEM_PRICE_3 = 4;
    int ITEM_PRICE_4 = 5;
    int START_PAGE_URL = 6;


}
