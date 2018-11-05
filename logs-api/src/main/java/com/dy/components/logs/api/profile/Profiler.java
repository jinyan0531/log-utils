package com.dy.components.logs.api.profile;


import com.dy.components.logs.api.communication.AbstractChannel;
import com.dy.components.logs.api.communication.rabbitmq.DefaultRabbitRegediter;
import com.dy.components.logs.api.log.collectlog.DefaultCollectLog;
import com.dy.components.logs.api.log.collectlog.DefaultTransactionCollectLog;
import com.dy.components.logs.api.log.collectlog.LogId;
import com.dy.components.logs.api.log.dy.DyMvcPorfarmaceTransactionCollectLog;
import com.dy.components.logs.api.log.dy.DyPorfarmaceTransactionCollectLog;
import com.dy.components.logs.api.log.dy.DySqlPorfarmaceTransactionCollectLog;
import com.dy.components.logs.api.protocol.Content;
import com.dy.components.logs.api.protocol.Message;
import com.dy.components.logs.api.protocol.ProtocolEnum;
import com.dy.components.logs.utils.ProtostuffUtil;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

/**
 * 用来测试并统计线程执行时间的工具。
 * 
 */
public final class Profiler  extends DefaultRabbitRegediter {

    final static Logger logger = LoggerFactory.getLogger(Profiler.class);
	/**构建实体的存储缓存体*/
    private static final ThreadLocal<DyMvcPorfarmaceTransactionCollectLog> mvcEntryStack = new ThreadLocal<DyMvcPorfarmaceTransactionCollectLog>();
    //private static final ThreadLocal<DySqlPorfarmaceTransactionCollectLog> sqlEntryStack = new ThreadLocal<DySqlPorfarmaceTransactionCollectLog>();
    //private static final ThreadLocal<DyPorfarmaceTransactionCollectLog> pEntryStack = new ThreadLocal<DyPorfarmaceTransactionCollectLog>();

     static String hostName;

     static  String ip;

    /**
     * 队列信息
     */
     static String rabbitMQIp;
     static String rabbitMQPort;
     static String rabbbitMQuser;
     static String rabbitMQpassword;
    static   RabbitMQClient rabbitMQClient = new RabbitMQClient();


    private void init(){
        hostName = getHostName();
        ip = getIp();
        rabbitMQClient.doRegedit(rabbitMQIp,  Integer.parseInt(rabbitMQPort),rabbbitMQuser,rabbitMQpassword);
        rabbitMQClient.connect();
    }
    static String sysId;
    static final String mvctemp = "userid:#userId# url:#url#";



    static final String sqltemp = "userid:#userId# sql:#sql#  params:#param#  cattime:#casttime#";
    static final String version = "v1.0.0";
    static final String indexVersion = "v1.0.0";
    /**开始计时，创建一个Entry的实体对象*/
    public static void start(String url,String uri,String params) {
        if(url==null)
            url="";

        DyMvcPorfarmaceTransactionCollectLog dyMvcPorfarmaceTransactionCollectLog = new DyMvcPorfarmaceTransactionCollectLog();

        String userId = UserOperator.getUserId();
        if(userId==null){
            userId="0000";
        }
        LogId logId = getNewLogId();
        dyMvcPorfarmaceTransactionCollectLog.setLogId(logId);
        dyMvcPorfarmaceTransactionCollectLog.setParentLogId(null);
        dyMvcPorfarmaceTransactionCollectLog.setFirstLogId(logId);
        dyMvcPorfarmaceTransactionCollectLog.setMessage(mvctemp.replaceAll("#userId#",userId).replaceAll("#url#",url));
        dyMvcPorfarmaceTransactionCollectLog.setUrl(url);
        dyMvcPorfarmaceTransactionCollectLog.setUri(uri);
        dyMvcPorfarmaceTransactionCollectLog.setParames(params);
        dyMvcPorfarmaceTransactionCollectLog.setUserId(userId);
        dyMvcPorfarmaceTransactionCollectLog.setCompleted(false);

        dyMvcPorfarmaceTransactionCollectLog.setParames(null);
        dyMvcPorfarmaceTransactionCollectLog.setStartTime(System.currentTimeMillis());
        dyMvcPorfarmaceTransactionCollectLog.setVersion(version);
        dyMvcPorfarmaceTransactionCollectLog.setIndexVersion(indexVersion);

        logger.info(dyMvcPorfarmaceTransactionCollectLog.toString());

        mvcEntryStack.set(dyMvcPorfarmaceTransactionCollectLog);
    }

    /**threadLocal缓存清理，由于现在大多是线程池的设置，所以要做一个清理*/
    public static void reset() {
        mvcEntryStack.set(null);
    }

    /**由于Entry自身是树状结构，所以如果是进入非Root的节点，那就需要enter来搞*/
    public static void enterSql(String sql,String params,Long casttime) {
        DySqlPorfarmaceTransactionCollectLog dySqlPorfarmaceTransactionCollectLog = new DySqlPorfarmaceTransactionCollectLog();
        DyMvcPorfarmaceTransactionCollectLog dyMvcPorfarmaceTransactionCollectLog =mvcEntryStack.get();
                dySqlPorfarmaceTransactionCollectLog.setSql(sql);
        dySqlPorfarmaceTransactionCollectLog.setParames(params);
        String userId = null;
        if(dyMvcPorfarmaceTransactionCollectLog!=null) {
            if (dyMvcPorfarmaceTransactionCollectLog.getUserId() != null) {
                userId = dyMvcPorfarmaceTransactionCollectLog.getUserId();
            }
        }else{
                userId = UserOperator.getUserId();
        }
        dySqlPorfarmaceTransactionCollectLog.setUserId(userId);
        dySqlPorfarmaceTransactionCollectLog.setCompleted(true);
        dySqlPorfarmaceTransactionCollectLog.setDurationTime(casttime);
        dySqlPorfarmaceTransactionCollectLog.setEnd(true);
        long time = System.currentTimeMillis();
        dySqlPorfarmaceTransactionCollectLog.setEndTime(time);
        dySqlPorfarmaceTransactionCollectLog.setFirst(false);
        LogId logId = getNewLogId();
        dySqlPorfarmaceTransactionCollectLog.setLogId(logId);
        if(userId==null){
            userId="0000";
        }
        dySqlPorfarmaceTransactionCollectLog.setVersion(version);
        dySqlPorfarmaceTransactionCollectLog.setIndexVersion(indexVersion);
        dySqlPorfarmaceTransactionCollectLog.setMessage(sqltemp.replaceAll("#userId#",userId).replaceAll("#sql#",sql).replaceAll("#param#",params).replaceAll("#casttime#",String.valueOf(casttime)));

        dySqlPorfarmaceTransactionCollectLog.setStartTime(time);
        dySqlPorfarmaceTransactionCollectLog.setVersion(version);

        if(dyMvcPorfarmaceTransactionCollectLog!=null) {
            dySqlPorfarmaceTransactionCollectLog.setFirstLogId(dyMvcPorfarmaceTransactionCollectLog.getLogId());
            dySqlPorfarmaceTransactionCollectLog.setParentLogId(dyMvcPorfarmaceTransactionCollectLog.getLogId());
            dyMvcPorfarmaceTransactionCollectLog.addChildCollectLog(dySqlPorfarmaceTransactionCollectLog);
        }else {
            dySqlPorfarmaceTransactionCollectLog.setFirst(true);
            dySqlPorfarmaceTransactionCollectLog.setFirstLogId(logId);
            dySqlPorfarmaceTransactionCollectLog.setParentLogId(logId);
        }
        logger.info(dySqlPorfarmaceTransactionCollectLog.toString());

        AbstractChannel channel = rabbitMQClient.getChannel();


        channel.send(buildSqlMessage(dySqlPorfarmaceTransactionCollectLog));
    }


    static Message buildSqlMessage(DySqlPorfarmaceTransactionCollectLog dySqlPorfarmaceTransactionCollectLog){
        Message message = new Message();
        message.setType(ProtocolEnum.CONTENT);
        Content content = new Content();
        content.setLogType(dySqlPorfarmaceTransactionCollectLog.getLogType());
        content.setClassName(DySqlPorfarmaceTransactionCollectLog.class.getName());
        content.setIndexVersion(dySqlPorfarmaceTransactionCollectLog.getIndexVersion());
        content.setCollectLog(ProtostuffUtil.serializer(dySqlPorfarmaceTransactionCollectLog));
        message.setContent(content);
        return message;
    }

    static Message buildMvcMessage(DyMvcPorfarmaceTransactionCollectLog dyMvcPorfarmaceTransactionCollectLog){
        Message message = new Message();
        message.setType(ProtocolEnum.CONTENT);
        Content content = new Content();
        content.setIndexVersion(dyMvcPorfarmaceTransactionCollectLog.getIndexVersion());
        content.setLogType(dyMvcPorfarmaceTransactionCollectLog.getLogType());
        content.setClassName(DyMvcPorfarmaceTransactionCollectLog.class.getName());
        content.setCollectLog(ProtostuffUtil.serializer(dyMvcPorfarmaceTransactionCollectLog));
        message.setContent(content);
        return message;
    }


    static LogId getNewLogId(){
        LogId logId = new LogId();
        logId.setId(UUID.randomUUID().toString());
        logId.setHostName(hostName);
        logId.setSysId(sysId);
        return logId;
    }

    public static void enterLog(String userId,String message) {

    }

    public static void enterException(String userId,Throwable e){

    }

    /**
     * 结束后推送mvc数据
     * @param casttime
     */
    public static void release(Long casttime) {
        DyMvcPorfarmaceTransactionCollectLog dyMvcPorfarmaceTransactionCollectLog = mvcEntryStack.get();


        dyMvcPorfarmaceTransactionCollectLog.setEndTime(System.currentTimeMillis());
        dyMvcPorfarmaceTransactionCollectLog.setEnd(true);
        dyMvcPorfarmaceTransactionCollectLog.setDurationTime(casttime);
        AbstractChannel channel = rabbitMQClient.getChannel();
        channel.send(buildMvcMessage(dyMvcPorfarmaceTransactionCollectLog));
    }

    public String getRabbitMQIp() {
        return rabbitMQIp;
    }

    public void setRabbitMQIp(String rabbitMQIp) {
        this.rabbitMQIp = rabbitMQIp;
    }

    public String getRabbitMQPort() {
        return rabbitMQPort;
    }

    public void setRabbitMQPort(String rabbitMQPort) {
        this.rabbitMQPort = rabbitMQPort;
    }

    public String getRabbbitMQuser() {
        return rabbbitMQuser;
    }

    public void setRabbbitMQuser(String rabbbitMQuser) {
        this.rabbbitMQuser = rabbbitMQuser;
    }

    public String getRabbitMQpassword() {
        return rabbitMQpassword;
    }

    public void setRabbitMQpassword(String rabbitMQpassword) {
        this.rabbitMQpassword = rabbitMQpassword;
    }

    public String getSysId() {
        return sysId;
    }


    public void setSysId(String sysId) {
        this.sysId = sysId;
    }



    public static String getHostNameForLiunx() {
        try {
            return (InetAddress.getLocalHost()).getHostName();
        } catch (UnknownHostException uhe) {
            String host = uhe.getMessage(); // host = "hostname: hostname"
            if (host != null) {
                int colon = host.indexOf(':');
                if (colon > 0) {
                    return host.substring(0, colon);
                }
            }
            return "UnknownHost";
        }
    }


    public static String getHostName() {
        if (System.getenv("COMPUTERNAME") != null) {
            return System.getenv("COMPUTERNAME");
        } else {
            return getHostNameForLiunx();
        }
    }

    public static String getIp(){
           String ip = null;
            try {
                ip =  InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                e.printStackTrace();
                ip = "UnknownIp";
            }
            return ip;
        }
    static class RabbitMQClient extends DefaultRabbitRegediter {



    }

}
