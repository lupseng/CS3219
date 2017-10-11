package PipeAndFilter;

import java.util.LinkedList;
import java.util.Properties;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Matric 1: A0135807A
 * Name   1: Tang Di Feng
 *
 * Matric 2:
 * Name   2:
 *
 * This file implements a pipe that transfer messages using JMS.
 */

public class JmsPipe implements IPipe, MessageListener {

    public final String JMS_FACTORY;
    public final String QUEUE;

    private QueueConnectionFactory qconFactory;
    private QueueConnection qcon;
    private QueueSession qsession;
    private QueueSender qsender;
    private QueueReceiver qreceiver;
    private Queue queue;
    private LinkedList<String> messages;
    private TextMessage msg;

    public JmsPipe(String simpleConnectionFactory, String simpleQueue) {
        JMS_FACTORY = simpleConnectionFactory;
        QUEUE = simpleQueue;
        start();
    }

    public void start() {
        try {
            InitialContext ic = getInitialContext();
            init(ic, QUEUE);
            messages = new LinkedList<String>();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void init(Context ctx, String queueName)
            throws NamingException, JMSException {
        qconFactory = (QueueConnectionFactory) ctx.lookup(JMS_FACTORY);
        qcon = qconFactory.createQueueConnection();
        qsession = qcon.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
        queue = (Queue) ctx.lookup(queueName);
    }

    private static InitialContext getInitialContext()
            throws NamingException {
        Properties props = new Properties();
        props.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
        props.put(Context.PROVIDER_URL, "jnp://localhost:1099");
        props.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
        return new InitialContext(props);
    }

    @Override
    public void write(Order s) {
        try {
            if(qsender == null) {
                qsender = qsession.createSender(queue);
                msg = qsession.createTextMessage();
                qcon.start();
            }
            msg.setText(s.toString());
            qsender.send(msg);
        } catch(Exception ex){
            ex.printStackTrace();
        }
        // TODO Auto-generated method stub

    }

    @Override
    public Order read() {
        // TODO Auto-generated method stub
        try {
            if(qreceiver == null) {
                qreceiver = qsession.createReceiver(queue);
                qreceiver.setMessageListener(this);
                msg = qsession.createTextMessage();
                qcon.start();
//                synchronized (this) {
//                    while (true) {
//                        try {
//                            wait();
//                        } catch (InterruptedException ie) {
//                        }
//                    }
//                }
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        if(!messages.isEmpty()) {
            return Order.fromString(messages.poll());
        }
        //System.out.println("Message Received: " + msgText);
        return null;
    }

    @Override
    public void close() {
        // TODO Auto-generated method stub
        try {
            qreceiver.close();
            qsender.close();
            qsession.close();
            qcon.close();
            } catch(Exception ex) {
                ex.printStackTrace();
            }
    }

    @Override
    public void onMessage(Message msg) {
        // TODO Auto-generated method stub
        String msgText;
        try {
            if (msg instanceof TextMessage) {
                msgText = ((TextMessage) msg).getText();
            } else {
                msgText = msg.toString();
            }
            if(msgText != null) {
                messages.add(msgText);
            }
            System.out.println("Message Received: " + msgText);
        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }

}