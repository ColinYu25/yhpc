package cn.safetys.sync.mq;

import java.io.Serializable;

import javax.jms.Destination;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;

import cn.safetys.constant.SystemConstant;

public class SyncClient implements Serializable{
	
	private static final long serialVersionUID = 8574317537345843472L;
	
	protected static final Log log = LogFactory.getLog(SyncClient.class);

	private JmsTemplate jmsTemplate;
	
	private Destination destination;
	
	private SystemConstant systemConstant;
	
	public void triggerRunGov(String runName){
		this.triggerRun(systemConstant.getGovernmentSyncName(), runName);
	}
	
	public void triggerRunEs(String runName){
		this.triggerRun(systemConstant.getEnterpriseSyncName(), runName);
	}
	
	public void triggerRun(String runName){
		this.triggerRun(systemConstant.getSyncName(), runName);
	}
	
	public void triggerRun(String syncName,String runName){
//		if(!systemConstant.isDataSeparation()){
//			return ;
//		}
//		try {
//			jmsTemplate.convertAndSend(destination, syncName+":run:"+runName);
//		} catch (JmsException e) {
//			e.printStackTrace();
//			log.debug("发送指令("+syncName+":run:"+runName+")失败！", e);
//		}
	}
	
	public void triggerJobGov(String jobName,Long id){
		this.triggerJob(systemConstant.getGovernmentSyncName(), jobName, id);
	}
	
	public void triggerJobEs(String jobName,Long id){
		this.triggerJob(systemConstant.getEnterpriseSyncName(), jobName, id);
	}
	
	public void triggerJob(String jobName,Long id){
		this.triggerJob(systemConstant.getSyncName(), jobName, id);
	}
	
	public void triggerJob(String syncName,String jobName,Long id){
//		if(!systemConstant.isDataSeparation()){
//			return ;
//		}
//		try {
//			jmsTemplate.convertAndSend(destination, syncName+":job:"+jobName+":"+id);
//		} catch (JmsException e) {
//			e.printStackTrace();
//			log.debug("发送指令("+syncName+":job:"+jobName+":"+id+")失败！", e);
//		}
	}
	
	public void triggerJob(String jobName,String id){
//		this.triggerJob(systemConstant.getSyncName(), jobName, id);
	}
	
	public void triggerJob(String syncName,String jobName,String id){
//		if(!systemConstant.isDataSeparation()){
//			return ;
//		}
//		try {
//			jmsTemplate.convertAndSend(destination, syncName+":job:"+jobName+":"+id);
//		} catch (JmsException e) {
//			e.printStackTrace();
//			log.debug("发送指令("+syncName+":job:"+jobName+":"+id+")失败！", e);
//		}
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public void setDestination(Destination destination) {
		this.destination = destination;
	}

	public void setSystemConstant(SystemConstant systemConstant) {
		this.systemConstant = systemConstant;
	}
	
}