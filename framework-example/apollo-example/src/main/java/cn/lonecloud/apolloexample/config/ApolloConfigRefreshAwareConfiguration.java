package cn.lonecloud.apolloexample.config;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.core.ConfigConsts;
import com.ctrip.framework.apollo.core.utils.StringUtils;
import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBean;
import org.springframework.cloud.context.properties.ConfigurationPropertiesBeans;
import org.springframework.cloud.context.properties.ConfigurationPropertiesRebinder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lonecloud
 * @version v1.0
 * @Package cn.lonecloud.apolloexample.config
 * @Description: TODO
 * @date 2021/11/258:02 下午
 */
@Configuration
@ConditionalOnClass(ApolloConfigChangeListener.class)
@ConditionalOnProperty(name = "apollo.bootstrap.enabled", matchIfMissing = true)
public class ApolloConfigRefreshAwareConfiguration implements BeanPostProcessor, ApplicationContextAware {


    private static final Logger logger = LoggerFactory.getLogger(ApolloConfigRefreshAwareConfiguration.class);

    @Value("${apollo.bootstrap.namespace:application}")
    private String namespace;

    private Map<String, ConfigurationPropertiesBean> cacheBeanMap = new ConcurrentHashMap<>();

    @Autowired
    private ConfigurationPropertiesRebinder configurationPropertiesRebinder;

    @PostConstruct
    public void initConfig() {
        Set<String> namespaceSet = Sets.newHashSet();
        namespaceSet.add(ConfigConsts.NAMESPACE_APPLICATION);
        if (!StringUtils.isBlank(namespace)) {
            String[] namespaceArr = namespace.split(",");
            logger.info("get namespace data have :{}", namespace);
            namespaceSet.addAll(Sets.newHashSet(namespaceArr));

        }
        for (String namespace : namespaceSet) {
            Config config = ConfigService.getConfig(namespace);
            config.addChangeListener(configChangeEvent -> {
                Set<String> changedKeys = configChangeEvent.changedKeys();
                for (String changedKey : changedKeys) {
                    ConfigChange change = configChangeEvent.getChange(changedKey);
                    logger.warn("config change namespace:[{}] key:{} value[{}]->[{}],changeType:{}",
                            change.getNamespace(),
                            changedKey,
                            change.getOldValue(),
                            change.getNewValue(),
                            change.getChangeType());
                    //
                    cacheBeanMap.forEach((beanName, beanDto) -> {
                        ConfigurationProperties configurationProperties = beanDto.getAnnotation();
                        if (changedKey.startsWith(configurationProperties.prefix())) {
                            configurationPropertiesRebinder.rebind(beanName);
                            logger.warn("completed to refresh @ConfigurationProperties beans ");
                        }
                    });
                }

            });
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, ConfigurationPropertiesBean> allPropertiesBean = ConfigurationPropertiesBean.getAll(applicationContext);
        cacheBeanMap.putAll(allPropertiesBean);

    }
}
