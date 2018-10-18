package com.dy.components.logs.api.log;

import com.dy.components.logs.api.log.collectlog.DefaultCollectLog;
import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;
import java.io.Serializable;

public abstract class AbstractLog implements Serializable, ILog{
    private static final long serialVersionUID = -1228223602458834533L;

    @Override
    public byte[] logSerializa(DefaultCollectLog log) {
        return null;
    }

    @Override
    public DefaultCollectLog assembleLogs(DefaultCollectLog parent, DefaultCollectLog child) {
        return null;
    }

    @Override
    public DefaultCollectLog readLogsFromBytes(byte[] bytes) {
        return null;
    }

    protected  static class DefaultBuilder{


        /**
         * 构造通用日志索引
         * @return
         */
        public static XContentBuilder defaultLogMapping( XContentBuilder builder,LogerBuilder appenduilder){

            try {
                builder.startObject();
                {

                    builder.startObject(appenduilder.getType());
                    {
                        builder.startObject("properties");
                        {

                            defaultProperties(builder,appenduilder);

                        }
                        builder.endObject();
                    }
                    builder.endObject();
                }
                builder.endObject();
                return builder;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return  null;

        }

        public static void defaultProperties(XContentBuilder builder,LogerBuilder appenduilder) throws IOException{



            /**
             * 添加构建
             */
            if(appenduilder!=null){
                appenduilder.builder();
            }

        }



    }

}
