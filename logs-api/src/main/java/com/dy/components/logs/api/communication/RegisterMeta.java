package com.dy.components.logs.api.communication;

import com.dy.components.logs.api.log.LogerBuilder;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.json.JsonXContent;
import org.elasticsearch.common.xcontent.json.JsonXContentParser;

import java.io.Serializable;
import java.util.Objects;


/**
 * 注册信息
 * @author fufeijian
 */
public class RegisterMeta   implements Serializable {
    // 地址
    private Address address = null;
    // metadata
    private ServiceMeta serviceMeta = null;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public ServiceMeta getServiceMeta() {
        return serviceMeta;
    }

    public void setServiceMeta(ServiceMeta serviceMeta) {
        this.serviceMeta = serviceMeta;
    }

    @Override
    public String toString() {
        return "RegisterMeta{" +
                "address=" + address +
                ", serviceMeta=" + serviceMeta +
                '}';
    }

    /**
     * 不要轻易修改成员变量, 否则将影响hashCode和equals, Address需要经常放入List, Map等容器中.
     */
    public static class Address {
        // 地址
        private String host;
        // 端口
        private int port;

        public Address() {}

        public Address(String host, int port) {
            this.host = host;
            this.port = port;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Address address = (Address) o;

            return port == address.port && !(host != null ? !host.equals(address.host) : address.host != null);
        }

        @Override
        public int hashCode() {
            int result = host != null ? host.hashCode() : 0;
            result = 31 * result + port;
            return result;
        }

        @Override
        public String toString() {
            return "Address{" +
                    "host='" + host + '\'' +
                    ", port=" + port +
                    '}';
        }
    }

    /**
     * 不要轻易修改成员变量, 否则将影响hashCode和equals, ServiceMeta需要经常放入List, Map等容器中.
     */
    public static class ServiceMeta {
        // 组别
        private String group;
        // 服务名
        private String serviceProviderName;
        // 版本信息
        private String version;

        String xContentBuilder;


        String logType;

        String index;

        public ServiceMeta(String group, String serviceProviderName, String version,String xContentBuilder,String logType,String index) {
            this.group = group;
            this.serviceProviderName = serviceProviderName;
            this.version = version;
            this.xContentBuilder = xContentBuilder;


            this.logType = logType;
            this.index = index;
        }

        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }

        public String getServiceProviderName() {
            return serviceProviderName;
        }

        public void setServiceProviderName(String serviceProviderName) {
            this.serviceProviderName = serviceProviderName;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }
//


        public String getxContentBuilder() {
            return xContentBuilder;
        }

        public void setxContentBuilder(String xContentBuilder) {
            this.xContentBuilder = xContentBuilder;
        }

        public String getLogType() {
            return logType;
        }

        public void setLogType(String logType) {
            this.logType = logType;
        }

        public String getIndex() {
            return index;
        }

        public void setIndex(String index) {
            this.index = index;
        }

        @Override
        public String toString() {
            return "ServiceMeta{" +
                    "group='" + group + '\'' +
                    ", serviceProviderName='" + serviceProviderName + '\'' +
                    ", version='" + version + '\'' +
                    ", xContentBuilder=" + xContentBuilder +
                    ", logType='" + logType + '\'' +
                    ", index='" + index + '\'' +
                    '}';
        }
    }
}
