package com.nus.team3.dao;

import com.amazonaws.services.sqs.model.SendMessageResult;
import com.nus.team3.dto.Order;
import com.nus.team3.subscriber.Subscriber;
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

    private String rootMapperPath = "com.nus.team3.mapper.TransactionHistoryMapper";
    private String selectAllQuery = ".getAllTxnHist";
    private String saveTxnQuery = ".saveTxn";

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
                Float.parseFloat(messageBodyList[4]),
                Integer.parseInt(messageBodyList[5]));
        sqlSessionTemplate.insert(rootMapperPath + saveTxnQuery, order);
        return 1;
    }

}
