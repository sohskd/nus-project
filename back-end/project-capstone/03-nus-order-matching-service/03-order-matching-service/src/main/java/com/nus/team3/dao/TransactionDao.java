package com.nus.team3.dao;

import com.nus.team3.constants.TradeEnum;
import com.nus.team3.dto.Order;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionDao {

    private static final Logger logger = LoggerFactory.getLogger(TransactionDao.class);

    public static String rootMapperPath = "com.nus.team3.mapper.TransactionHistoryMapper";
    public static String selectAllQuery = ".getAllTxnHist";
    public static String saveTxnQuery = ".saveTxn";

    @Autowired
    @Qualifier("mysqlSqlSessionTemplate")
    private SqlSessionTemplate sqlSessionTemplate;

    @GetMapping("/getAllTxnHist")
    public List<Order> getAllTransactionHistories(){
        return sqlSessionTemplate.selectList(rootMapperPath + selectAllQuery);
    }

    @PostMapping("/saveTxn")
    public int saveTransaction(@RequestBody String messageBody) {
        String[] messageBodyList = messageBody.split("#");
        Order order = new Order(messageBodyList[0],
                                messageBodyList[1],
                                messageBodyList[2],
                                messageBodyList[3],
                                System.currentTimeMillis(),
                Float.parseFloat(String.format("%.2f", Double.valueOf(messageBodyList[5]))),
                Integer.parseInt(messageBodyList[4]));
        if (messageBodyList.length > 6 && messageBodyList[6].equalsIgnoreCase(TradeEnum.STATUS.MATCHED.name())){
            order.setMatchStatus(messageBodyList[6]);
        }
        if (messageBodyList.length > 7 ){
            order.setTransactionIdAfterMatch(messageBodyList[7]);
        }
        sqlSessionTemplate.insert(rootMapperPath + saveTxnQuery, order);
        return 1;
    }

}
