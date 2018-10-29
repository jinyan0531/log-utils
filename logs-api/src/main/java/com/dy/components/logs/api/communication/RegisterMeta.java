package com.dy.components.logs.api.communication;

import com.dy.components.logs.api.log.LogerBuilder;
import org.elasticsearch.common.xcontent.XContentBuilder;

import java.util.Objects;


/**
 * 注册信息
 * @author fufeijian
 */
public class RegisterMeta {
    // 地址
    private Address address = new Address();
    // metadata
    private ServiceMeta serviceMeta = new ServiceMeta();
    // 权重 hashCode() 与 equals() 不把weight计算在内
    private volatile int weight;
    private volatile int connCount;





    public void setAddress(Address address) {
        this.address = address;
    }

    public void setServiceMeta(ServiceMeta serviceMeta) {
        this.serviceMeta = serviceMeta;
    }


    public String getHost() {
        return address.getHost();
    }

    public void setHost(String host) {
        address.setHost(host);
    }

    public int getPort() {
        return address.getPort();
    }

    public void setPort(int port) {
        address.setPort(port);
    }

    public String getGroup() {
        return serviceMeta.getGroup();
    }

    public void setGroup(String group) {
        serviceMeta.setGroup(group);
    }

    public String getServiceProviderName() {
        return serviceMeta.getServiceProviderName();
    }

    public void setServiceProviderName(String serviceProviderName) {
        serviceMeta.setServiceProviderName(serviceProviderName);
    }

    public String getVersion() {
        return serviceMeta.getVersion();
    }

    public void setVersion(String version) {
        serviceMeta.setVersion(version);
    }

    public Address getAddress() {
        return address;
    }

    public ServiceMeta getServiceMeta() {
        return serviceMeta;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getConnCount() {
        return connCount;
    }

    public void setConnCount(int connCount) {
        this.connCount = connCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RegisterMeta that = (RegisterMeta) o;

        return !(address != null ? !address.equals(that.address) : that.address != null)
                && !(serviceMeta != null ? !serviceMeta.equals(that.serviceMeta) : that.serviceMeta != null);
    }

    @Override
    public int hashCode() {
        int result = address != null ? address.hashCode() : 0;
        result = 31 * result + (serviceMeta != null ? serviceMeta.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RegisterMeta{" +
                "address=" + address +
                ", serviceMeta=" + serviceMeta +
                ", weight=" + weight +
                ", connCount=" + connCount +
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

        XContentBuilder xContentBuilder;


        String type;

        String index;



        public ServiceMeta() {}

        public ServiceMeta(String group, String serviceProviderName, String version,XContentBuilder xContentBuilder,String type,String index) {
            this.group = group;
            this.serviceProviderName = serviceProviderName;
            this.version = version;
            this.xContentBuilder = xContentBuilder;
            this.type = type;
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ServiceMeta that = (ServiceMeta) o;

            return !(group != null ? !group.equals(that.group) : that.group != null)
                    && !(serviceProviderName != null ? !serviceProviderName.equals(that.serviceProviderName) : that.serviceProviderName != null)
                    && !(version != null ? !version.equals(that.version) : that.version != null)
                    &&!(xContentBuilder!=null?!xContentBuilder.equals(that.xContentBuilder):that.xContentBuilder!=null)
                    &&!(type!=null?!type.equals(that.type):that.type!=null)
                    &&!(index!=null?!index.equals(that.index):that.type!=null);
        }

        @Override
        public int hashCode() {
            int result = group != null ? group.hashCode() : 0;
            result = 31 * result + (serviceProviderName != null ? serviceProviderName.hashCode() : 0);
            result = 31 * result + (version != null ? version.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "ServiceMeta{" +
                    "group='" + group + '\'' +
                    ", serviceProviderName='" + serviceProviderName + '\'' +
                    ", version='" + version + '\'' +
                    '}';
        }

        public XContentBuilder getxContentBuilder() {
            return xContentBuilder;
        }

        public void setxContentBuilder(XContentBuilder xContentBuilder) {
            this.xContentBuilder = xContentBuilder;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getIndex() {
            return index;
        }

        public void setIndex(String index) {
            this.index = index;
        }
    }
}
