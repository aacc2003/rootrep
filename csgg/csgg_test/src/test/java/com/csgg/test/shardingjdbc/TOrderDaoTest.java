package com.csgg.test.shardingjdbc;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.csgg.CsggBootMain;
import com.csgg.db.dao.TOrderDao;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= {CsggBootMain.class} )
public class TOrderDaoTest{

	@Autowired
	TOrderDao dao;
	
	@Test
	public void testInsertTOrder() {
		dao.insertTOrder(new BigDecimal("3.6"), 112233L, "1");
	}
}
