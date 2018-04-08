package com.myassignment.orderloader;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.myassignment.orderloader.OrderFileLayout.*;
import static com.myassignment.orderloader.ParserErrors.*;
import static com.myassignment.orderloader.StringUtils.isNotEmpty;

/**
 * Created by rshanmugam on 4/7/18.
 */
public class Order implements Serializable {

    String orderId;
    Date orderDate;
    String userId;

    double itemPrice1;
    double itemPrice2;
    double itemPrice3;
    double itemPrice4;

    String startPageUrl;
    String errorMsg;

    private static String OUT_DATE_FORMAT = "yyyy-MM-dd";
    private static String EMPTY = "";
    private static String HEADER = "Order_id\tOrder_date\tUser_id\tAvg_Item_price\tStart_page_url\tError_msg";


    public double getAvgItemPraise() {

        if(getItemTotal() == 0 || getNoOfItems() == 0) {
            return 0;
        }

        return getItemTotal() / getNoOfItems();
    }

    public double getItemTotal() {
        return itemPrice1 + itemPrice2 + itemPrice3 + itemPrice4;
    }

    public int getNoOfItems() {

        int totalItems = 0;

        totalItems = itemPrice1 > 0 ? totalItems + 1: totalItems;
        totalItems = itemPrice2 > 0 ? totalItems + 1: totalItems;
        totalItems = itemPrice3 > 0 ? totalItems + 1: totalItems;
        totalItems = itemPrice4 > 0 ? totalItems + 1: totalItems;

        return totalItems;
    }

    public String outputAsTsv() {

        SimpleDateFormat df = new SimpleDateFormat(OUT_DATE_FORMAT);

        return String.format("%s\t%s\t%s\t%.2f\t%s\t%s",
                orderId != null ? orderId : EMPTY,
                orderDate != null ? df.format(orderDate) : EMPTY,
                userId != null ? userId : EMPTY,
                getAvgItemPraise(),
                startPageUrl,
                errorMsg != null ? errorMsg : EMPTY);
    }


    public static Order createFromTsv(String orderRow) {

        String[] fields = orderRow.split(FILE_SEPARATOR, -1);

        Order order = new Order();
        List<String> errorMsgs = new ArrayList<>();

        if(fields.length < TOTAL_NO_OF_FIELDS) {
            errorMsgs.add(INVALID_ROW);
            order.errorMsg = StringUtils.join(errorMsgs, "|");
            return order;
        }

        //order id and date
        String orderIdDate = fields[ORDER_ID_DATE];
        if(isNotEmpty(orderIdDate)) {
            String idDate[] = orderIdDate.split(":");

            if(idDate.length > 0) {
                order.orderId = idDate[0];
            }

            if(idDate.length > 1 && isNotEmpty(idDate[1])) {

                SimpleDateFormat df = new SimpleDateFormat(ORDER_DATE_FROMAT);
                String dateStr = idDate[1];

                try {
                    order.orderDate = df.parse(dateStr);
                } catch (ParseException e) {
                   errorMsgs.add(INVALID_ORDER_DATE);
                }

            }
        }

        order.userId = fields[USER_ID];

        //item price, empty treated as zero
        order.itemPrice1 = isNotEmpty(fields[ITEM_PRICE_1]) ? Double.valueOf(fields[ITEM_PRICE_1]) : 0;
        order.itemPrice2 = isNotEmpty(fields[ITEM_PRICE_2]) ? Double.valueOf(fields[ITEM_PRICE_2]) : 0;
        order.itemPrice3 = isNotEmpty(fields[ITEM_PRICE_3]) ? Double.valueOf(fields[ITEM_PRICE_3]) : 0;
        order.itemPrice4 = isNotEmpty(fields[ITEM_PRICE_4]) ? Double.valueOf(fields[ITEM_PRICE_4]) : 0;

        //page url
        String pageUrl= fields[START_PAGE_URL];
        boolean validUrl = pageUrl.startsWith(VALID_PAGE_URL_PREFIX);
        order.startPageUrl = validUrl ? pageUrl : EMPTY;

        if(isNotEmpty(pageUrl) && !validUrl) {
            errorMsgs.add(INVALID_PAGE_URL);
        }

        order.errorMsg = StringUtils.join(errorMsgs, "|");

        return order;
    }

    public static String getHeader() {
        return HEADER;
    }

}

