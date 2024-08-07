package com.gtn.ejbjboss.ejb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;


@MessageDriven(activationConfig = {
		@ActivationConfigProperty(
				propertyName = "destinationType",
				propertyValue = "javax.jms.Queue"
				),
		@ActivationConfigProperty(
				propertyName = "destination",
				propertyValue = "FooQueue"
				)
})
public class StudentRegistrationListener implements MessageListener {
	@Override
	public void onMessage(Message arg0) {
		// TODO Auto-generated method stub
		TextMessage message = (TextMessage) arg0;
		try {
			String payload = message.getText();
			System.out.println(payload);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
