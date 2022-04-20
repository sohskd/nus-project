package com.nus.team3.scenarios;

import com.nus.team3.dto.Order;
import com.nus.team3.model.MasterPool;
import com.nus.team3.utils.Utils;
import org.junit.Before;


public class OrderMatchingTest {

    private MasterPool masterPool;

    @Before
    void setUp() {
        this.masterPool = new MasterPool();
        masterPool.setInit(true);
    }

    private Order constructOrder(String side, String stock, String quantity, String price, String user){
        return Utils.constructOrder(side + "#" + stock + "#" + quantity + "#" + price + "#" + user);
    }


    public void test_whenNoOrderGetMatched(){

    }

}
