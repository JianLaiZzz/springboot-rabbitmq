package com.sxw.springbootproducer;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.sxw.entity.Order;
import com.sxw.springbootproducer.service.OrderService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootProducerApplicationTests
{
	@Autowired
	private OrderService orderService;

	@Test
	public void testSend() throws Exception
	{
		for (int i = 2; i < 1000; i++)
		{
			Order order = new Order();
			order.setId(i);
			order.setName("测试订单" + i);
			order.setMessageId(System.currentTimeMillis() + "$" + UUID.randomUUID().toString());
			orderService.createOrder(order);
		}
	}

}
