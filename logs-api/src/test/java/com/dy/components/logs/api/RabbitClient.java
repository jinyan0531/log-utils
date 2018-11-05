package com.dy.components.logs.api;

import com.dy.components.logs.api.communication.AbstractChannel;
import com.dy.components.logs.api.communication.RegisterMeta;
import com.dy.components.logs.api.communication.rabbitmq.DefaultRabbitRegediter;
import com.dy.components.logs.api.log.collectlog.DefaultCollectLog;
import com.dy.components.logs.api.log.collectlog.LogId;
import com.dy.components.logs.api.log.dy.DyMvcPorfarmaceTransactionCollectLog;
import com.dy.components.logs.api.protocol.Content;
import com.dy.components.logs.api.protocol.Message;
import com.dy.components.logs.api.protocol.ProtocolEnum;
import com.dy.components.logs.utils.ProtostuffUtil;

public class RabbitClient extends DefaultRabbitRegediter {
    public static void main(String[] s){
        RabbitClient rabbitClient = new RabbitClient();
        //rabbitClient.doRegedit("192.168.20.51",30702,"rabbit","123456");

        rabbitClient.doRegedit("127.0.0.1",5672,"guest","guest");
        rabbitClient.connect();
        AbstractChannel channel = rabbitClient.getChannel();

        Message message = new Message();
        message.setType(ProtocolEnum.REGEDIT);

        RegisterMeta registerMeta = new RegisterMeta();

        RegisterMeta.Address address = new RegisterMeta.Address();
        address.setHost("127.0.0.1");
        address.setPort(1234);
        DefaultCollectLog log = new DefaultCollectLog();




        message.setRegeditMeta(registerMeta);

//
//        Content content = new Content();
//
//        DefaultCollectLog log = new DefaultCollectLog();
//
//        log.setCompleted(true);
//        log.setEnd(true);
//        log.setFirst(true);
//        LogId logid = new LogId();
//        logid.setHostName("ces");
//        logid.setId("32344");
//        logid.setIp("12.12.443.21");
//        logid.setSysId("tms");
//        log.setFirstLogId(logid);
//        log.setParentLogId(null);



//        content.setCollectLog(ProtostuffUtil.serializer(log));
//        content.setClassName(DefaultCollectLog.class.getName());
//        message.setContent(content);
//
//        Message message1 = new Message();
//        message1.setType(ProtocolEnum.REGEDIT);
//        RegisterMeta registerMeta1 = new RegisterMeta();
//        registerMeta1.setAddress(address);
//        registerMeta1.setServiceMeta(serviceMeta);
//
//        message1.setRegeditMeta(registerMeta1);


//
//
//
//
//        channel.send(message);






        LogId firstId = new LogId();
        firstId.setSysId("tms");
        firstId.setIp("127.0.0.1");
        firstId.setHostName("zh_1");
        firstId.setId("33");


        LogId parentLogId = new LogId();
        parentLogId.setSysId("tms");
        parentLogId.setIp("127.0.0.1");
        parentLogId.setHostName("zh_1");
        parentLogId.setId("44");


        LogId logId = new LogId();
        parentLogId.setSysId("tms");
        parentLogId.setIp("127.0.0.1");
        parentLogId.setHostName("zh_1");
        parentLogId.setId("55");


        DyMvcPorfarmaceTransactionCollectLog dyMvcPorfarmaceTransactionCollectLog = new DyMvcPorfarmaceTransactionCollectLog();

        dyMvcPorfarmaceTransactionCollectLog.setMessage("cesi1111");
        dyMvcPorfarmaceTransactionCollectLog.setMessageTempletId(233);
        dyMvcPorfarmaceTransactionCollectLog.setFirstLogId(firstId);
        dyMvcPorfarmaceTransactionCollectLog.setParentLogId(parentLogId);
        dyMvcPorfarmaceTransactionCollectLog.setLogId(logId);
        dyMvcPorfarmaceTransactionCollectLog.setUserId("2133");
        Message contentMessage = new Message();

        contentMessage.setType(ProtocolEnum.CONTENT);

        Content content = new Content();
        content.setLogType(dyMvcPorfarmaceTransactionCollectLog.getLogType());
        content.setIndexId(dyMvcPorfarmaceTransactionCollectLog.getSerialVersionUID());
        content.setCollectLog(ProtostuffUtil.serializer(dyMvcPorfarmaceTransactionCollectLog));
        content.setClassName(DyMvcPorfarmaceTransactionCollectLog.class.getName());

        contentMessage.setContent(content);




        RegisterMeta.ServiceMeta serviceMeta = new RegisterMeta.ServiceMeta("dy","crm","v1.0.0",dyMvcPorfarmaceTransactionCollectLog.buildXConBuilder(),dyMvcPorfarmaceTransactionCollectLog.getLogType(), String.valueOf(dyMvcPorfarmaceTransactionCollectLog.getSerialVersionUID()));

        registerMeta.setAddress(address);
        registerMeta.setServiceMeta(serviceMeta);


        Message regeditMessage = new Message();
        regeditMessage.setType(ProtocolEnum.REGEDIT);
        regeditMessage.setRegeditMeta(registerMeta);

        channel.send(contentMessage);

    }
}
