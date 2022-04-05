package com.nus.team3.dto;

import com.nus.team3.constants.TradeEnum;

class Order implements Comparable<Order>{
    private String buyOrSell;
    private String transactionId;
    private String stockName;
    private String user;
    private long timestamp;
    private int price;
    private int quantity;

    public Order(String buyOrSell, String transactionId, String stockName, String user, long timestamp, int price, int quantity) {
        this.buyOrSell = buyOrSell;
        this.transactionId = transactionId;
        this.stockName = stockName;
        this.user = user;
        this.timestamp = timestamp;
        this.price = price;
        this.quantity = quantity;
    }

    @Override
    public int compareTo(Order o){
        if (this.getBuyOrSell().equals(TradeEnum.SIDE.BUY.name())){
            return o.getPrice() - this.getPrice() != 0 ? o.getPrice() - this.getPrice() : (int)(this.getTimestamp() - o.getTimestamp());
        }else{
            return this.getPrice() - o.getPrice() != 0 ? this.getPrice() - o.getPrice() : (int)(this.getTimestamp() - o.getTimestamp());
        }
    }

    public String getBuyOrSell() {
        return buyOrSell;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getStockName() {
        return stockName;
    }

    public String getUser() {
        return user;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}
