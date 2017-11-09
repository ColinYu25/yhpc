package cn.safetys.mq.service;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

import cn.safetys.center.db.mq.cmd.CmdHelp;

public class Server {

	public static void main(String... args) throws Exception {

		// ConnectionFactory cf = new
		// ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_BROKER_URL);

		ConnectionFactory cf = new ActiveMQConnectionFactory("system",
				"manager", "failover://tcp://60.190.2.249:61616");

		Connection c = cf.createConnection();

		Session s = c.createSession(true, Session.AUTO_ACKNOWLEDGE);

		// Destination destination = s.createQueue("test");
		Destination destination = s.createTopic("centerTopic");
		MessageProducer producer = s.createProducer(destination);
		// for (int i = 0; i < 3; i++) {

		// MapMessage message = s.createMapMessage();
		TextMessage message = s.createTextMessage();

		message.setStringProperty("clientId",
				"40ca5680fceb4160a74220900fddasdf");

		message.setStringProperty("subscriberName", "nbcx_center");

		message.setText(CmdHelp._SYS_BZHZP_CMD);

		System.out.println(message.toString());

		Thread.sleep(1000);
		// 通过消息生产者发出消息
		producer.send(message);
		// }
		s.commit();
		s.close();
		c.close();
	}

}
