package top.lingyuzhao.diskMirror.starter.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.lingyuzhao.diskMirror.conf.Config;
import top.lingyuzhao.diskMirror.core.Adapter;
import top.lingyuzhao.diskMirror.core.DiskMirror;
import top.lingyuzhao.diskMirror.core.module.HandleModule;
import top.lingyuzhao.diskMirror.starter.conf.properties.DiskMirrorProperties;
import top.lingyuzhao.diskMirror.starter.conf.properties.ImageCompressModuleConf;

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
public class DiskMirrorAutoConfiguration {

    public final static Logger LOGGER = Logger.getLogger(DiskMirrorAutoConfiguration.class.getName());

    // 注入属性类
    private final DiskMirrorProperties diskMirrorProperties;

    @Autowired
    public DiskMirrorAutoConfiguration(DiskMirrorProperties diskMirrorProperties) {
        this.diskMirrorProperties = diskMirrorProperties;
    }

    private static void loadHandleModule(Adapter adapter, ImageCompressModuleConf handleModule) {
        final HandleModule type = handleModule.getType();
        if (handleModule.isEnable()) {
            LOGGER.info("loadHandleModule ---> Enable  handleModule." + type.name());
            adapter.addHandleModule(type);
        } else {
            LOGGER.info("loadHandleModule -×-> Disable handleModule." + type.name());
        }
    }

    /**
     * @return 当前 starter 中根据配置文件自动生成的 diskMirror 适配器对象。
     * <p>
     * The diskMirror adapter object automatically generated in the current starter based on the configuration file.
     */
    @Bean("top.lingyuzhao.diskMirror.core.Adapter")
    @ConditionalOnMissingBean(name = "top.lingyuzhao.diskMirror.core.Adapter")
    public Adapter getAdapter() {
        if (!this.diskMirrorProperties.isEnabledFeature()) {
            LOGGER.info("DiskMirror is not enable.");
            throw new RuntimeException("DiskMirror is not enable.");
        }
        final DiskMirror diskMirrorAdapter = diskMirrorProperties.getAdapterType();
        final Adapter adapter = diskMirrorAdapter.getAdapter(diskMirrorProperties.getConfig());
        loadHandleModule(adapter, diskMirrorProperties.getImageCompressModule());
        LOGGER.info("diskMirror is ok!!!\n" + diskMirrorAdapter.getVersion());
        return adapter;
    }
}
