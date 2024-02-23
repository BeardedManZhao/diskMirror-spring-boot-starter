package top.lingyuzhao.diskMirror.starter.conf;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.lingyuzhao.diskMirror.conf.Config;
import top.lingyuzhao.diskMirror.core.Adapter;
import top.lingyuzhao.diskMirror.core.DiskMirror;
import top.lingyuzhao.diskMirror.starter.conf.properties.DiskMirrorProperties;

import java.util.logging.Logger;

/**
 * diskMirror 自动配置类 SpringBoot 将可以自动的通过此类获取到 diskMirror 的适配器对象。
 * <p>
 * The automatic configuration class SpringBoot will automatically obtain the adapter object for diskMirror through this class.
 *
 * @author zhao
 */
@Configuration(
        proxyBeanMethods = false
)
@ConditionalOnClass({
        Adapter.class, Config.class
})
@EnableConfigurationProperties(DiskMirrorProperties.class)
@ConditionalOnProperty(prefix = "disk-mirror", name = "enable-feature", havingValue = "true")
public class DiskMirrorAutoConfiguration {

    public final static Logger LOGGER = Logger.getLogger(DiskMirrorAutoConfiguration.class.getName());

    // 注入属性类
    private final DiskMirrorProperties diskMirrorProperties;

    public DiskMirrorAutoConfiguration(DiskMirrorProperties diskMirrorProperties) {
        this.diskMirrorProperties = diskMirrorProperties;
    }

    /**
     * @return 当前 starter 中根据配置文件自动生成的 diskMirror 适配器对象。
     * <p>
     * The diskMirror adapter object automatically generated in the current starter based on the configuration file.
     */
    @Bean("top.lingyuzhao.diskMirror.core.Adapter")
    // 当容器没有这个 Bean 的时候才创建这个 Bean
    @ConditionalOnMissingBean(Adapter.class)
    public Adapter getAdapter() {
        final DiskMirror diskMirrorAdapter = diskMirrorProperties.getAdapterType();
        LOGGER.info("diskMirror is ok!!!\n" + diskMirrorAdapter.getVersion());
        return diskMirrorAdapter.getAdapter(diskMirrorProperties.getConfig());
    }
}
