package com.nus.team3.utils;

import com.nus.team3.constants.TradeEnum;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderConstructionTest {

    private String message;
    private String side = "buy";
    private String stock = "MSFT";
    private String quantity = "1";
    private String price = "101.2";
    private String user = "777";

    @BeforeEach
    void setUpMessage(){
        setMessage(this.side ,this.stock ,this.quantity, this.price , this.user);
    }

    @AfterEach
    void restoreGoldenMessage(){
        this.side = "buy";
        this.stock = "MSFT";
        this.quantity = "1";
        this.price = "101.2";
        this.user = "777";
    }

    private void setMessage(String side, String stock, String quantity, String price, String user){
        this.message = side + "#" + stock + "#" + quantity + "#" + price + "#" + user;
    }

    @Test
    void test_constructOrderWithExpectedParams() {
        assertEquals(this.side, Utils.constructOrder(message).getBuyOrSell());
        assertEquals(this.stock, Utils.constructOrder(message).getStockTicker());
        assertEquals(Integer.parseInt(this.quantity),
                Utils.constructOrder(message).getQuantity());
        assertEquals(Float.parseFloat(String.format("%.2f", Double.valueOf(this.price))),
                Utils.constructOrder(message).getPrice());
        assertEquals(Integer.parseInt(this.user),
                Utils.constructOrder(message).getUser());
        assertEquals(TradeEnum.STATUS.UNMATCHED.name(), Utils.constructOrder(message).getMatchStatus());
        assertEquals("", Utils.constructOrder(message).getTransactionIdAfterMatch());
        assertNotNull(Utils.constructOrder(message).getTimestamp());
        assertNotNull(Utils.constructOrder(message).getTransactionId());
    }

    @Test
    void test_orderWithInvalidSide() {
        this.side = "Unknown";
        setMessage(this.side ,this.stock ,this.quantity, this.price , this.user);
        assertNull(Utils.constructOrder(message));
    }

    @Test
    void test_orderWithInvalidQuantity() {
        String[] invalidQtyString = {"wdkjfb72k3", "-1", "100.0"};
        for (String invalidStr: invalidQtyString){
            this.quantity = invalidStr;
            setMessage(this.side ,this.stock ,this.quantity, this.price , this.user);
            assertNull(Utils.constructOrder(message));
        }
    }

    @Test
    void test_orderWithInvalidPrice() {
        String[] invalidPriceString = {"wdkjfb72k3", "-1.01"};
        for (String invalidStr: invalidPriceString){
            this.price = invalidStr;
            setMessage(this.side ,this.stock ,this.quantity, this.price , this.user);
            assertNull(Utils.constructOrder(message));
        }
    }

    @Test
    void test_orderWithInvalidUsername() {
        String[] invalidUserString = {"vincent"};
        for (String invalidStr: invalidUserString){
            this.user = invalidStr;
            setMessage(this.side ,this.stock ,this.quantity, this.price , this.user);
            assertNull(Utils.constructOrder(message));
        }
    }

}