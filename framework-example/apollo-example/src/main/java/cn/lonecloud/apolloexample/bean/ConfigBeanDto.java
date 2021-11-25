package cn.lonecloud.apolloexample.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author lonecloud
 * @version v1.0
 * @Package cn.lonecloud.apolloexample.bean
 * @Description: TODO
 * @date 2021/11/259:09 下午
 */
public class ConfigBeanDto {

    private ConfigurationProperties configurationProperties;

    private Object bean;

    public ConfigBeanDto(ConfigurationProperties configurationProperties, Object bean) {
        this.configurationProperties = configurationProperties;
        this.bean = bean;
    }

    public ConfigurationProperties getConfigurationProperties() {
        return configurationProperties;
    }

    public void setConfigurationProperties(ConfigurationProperties configurationProperties) {
        this.configurationProperties = configurationProperties;
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }
}
