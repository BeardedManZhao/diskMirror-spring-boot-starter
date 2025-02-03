package top.lingyuzhao.diskMirror.starter.conf.properties;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.boot.context.properties.ConfigurationProperties;
import top.lingyuzhao.diskMirror.conf.Config;
import top.lingyuzhao.diskMirror.core.DiskMirror;
import top.lingyuzhao.diskMirror.core.module.ModuleManager;
import top.lingyuzhao.diskMirror.core.module.SkCheckModule;
import top.lingyuzhao.diskMirror.core.module.Verification;
import top.lingyuzhao.diskMirror.starter.conf.DiskMirrorAutoConfiguration;
import top.lingyuzhao.utils.StrUtils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 配置文件属性类，此属性类中的所有配置可以直接在 application.yaml 文件中进行配置，其格式为 disk-mirror.*
 * <p>
 * The configuration file property class allows all configurations in this property class to be directly configured in the application.yaml file, in the format of disk mirror*
 */
@ConfigurationProperties(
        prefix = "disk-mirror"
)
public class DiskMirrorProperties {

    // 修改为字符串类型，并添加一个setter方法用于从配置文件中注入适配器名称
    private String adapterType = DiskMirror.LocalFSAdapter.name();

    private boolean enabledFeature = false;

    private String rootDir, fsDefaultFs, resKey, okValue, protocolPrefix, secureKey = "", useSpaceConfigMode = "HashMapper", redisHostPortDb = "127.0.0.1:6379:0", redisPassword = "-", charSet = "UTF-8";
    private JSONObject params = new JSONObject(), spaceMaxSize = new JSONObject();
    private long userDiskMirrorSpaceQuota = 128 << 10 << 10;

    private boolean isNotOverWrite = true;

    private ImageCompressModuleConf imageCompressModule = new ImageCompressModuleConf();

    /**
     * 添加一个验证器
     *
     * @param moduleName 校验模块的类名字
     * @param loadMode   验证模块的加载模式
     */
    private static void addVerification(String moduleName, String loadMode) {
        Verification verification = null;
        // 加载出模块对象
        if (moduleName.equals("SkCheckModule")) {
            verification = new SkCheckModule(moduleName, "密钥校验服务");
        } else {
            DiskMirrorAutoConfiguration.LOGGER.warning("未定义的模块：" + moduleName);
        }
        if (verification != null) {
            // 开始进行模块加载
            switch (loadMode) {
                case "read":
                    ModuleManager.registerModuleRead(verification);
                    break;
                case "writer":
                    ModuleManager.registerModuleWriter(verification);
                    break;
                default:
                    DiskMirrorAutoConfiguration.LOGGER.warning("未定义的模块：" + moduleName);
                    return;
            }
        }
        DiskMirrorAutoConfiguration.LOGGER.info("已加载 " + loadMode + " 模块：" + moduleName);
    }

    public Config getConfig() {
        final HashMap<String, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put(Config.USE_SPACE_CONFIG_MODE, useSpaceConfigMode);
        objectObjectHashMap.put(Config.REDIS_HOST_PORT_DB, redisHostPortDb);
        objectObjectHashMap.put(Config.REDIS_PASSWORD, redisPassword);
        final Config config = new Config(objectObjectHashMap);
        if (rootDir != null) {
            config.put(Config.ROOT_DIR, rootDir);
        }
        if (fsDefaultFs != null) {
            config.put(Config.FS_DEFAULT_FS, fsDefaultFs);
        }
        if (resKey != null) {
            config.put(Config.RES_KEY, resKey);
        }
        if (okValue != null) {
            config.put(Config.OK_VALUE, okValue);
        }
        if (charSet != null) {
            config.put(Config.CHAR_SET, charSet);
        }
        if (protocolPrefix != null) {
            config.put(Config.PROTOCOL_PREFIX, protocolPrefix);
        }
        config.put(Config.PARAMS, params);
        this.spaceMaxSize.forEach(
                (k, v) -> config.setSpaceMaxSize(k, (Long) v)
        );
        config.put(Config.PARAMS, params);
        config.put(Config.USER_DISK_MIRROR_SPACE_QUOTA, userDiskMirrorSpaceQuota);
        config.put(Config.IS_NOT_OVER_WRITE, isNotOverWrite);
        config.put(Config.CHAR_SET, charSet);
        config.setSecureKey(secureKey);
        return config;
    }

    // 新增一个方法来获取枚举类型的DiskMirrorAdapter
    public DiskMirror getAdapterType() {
        DiskMirrorAutoConfiguration.LOGGER.info("getAdapterType run = adapterType:" + adapterType);
        return DiskMirror.valueOf(adapterType);
    }

    /**
     * @param adapterType 要使用的盘镜适配器类型 在这里默认数值是本地盘镜适配器，具体的适配器 您可以查阅 top.lingyuzhao.diskMirror.core.DiskMirror 类
     *                    <p>
     *                    The default value for the type of disk mirror adapter to be used here is the local disk mirror adapter. You can refer to top-lingyuzhao. diskMirror. core for specific adapters DiskMirror class
     * @see top.lingyuzhao.diskMirror.core.DiskMirror
     */
    public void setAdapterType(String adapterType) {
        DiskMirrorAutoConfiguration.LOGGER.info("setAdapterType run = adapterType:" + adapterType);
        this.adapterType = adapterType;
    }

    public String getRootDir() {
        return rootDir;
    }

    public void setRootDir(String rootDir) {
        DiskMirrorAutoConfiguration.LOGGER.info("setRootDir run = rootDir:" + rootDir);
        this.rootDir = rootDir;
    }

    public String getFsDefaultFs() {
        return fsDefaultFs;
    }

    public void setFsDefaultFs(String fsDefaultFs) {
        DiskMirrorAutoConfiguration.LOGGER.info("setFsDefaultFs run = fsDefaultFs:" + fsDefaultFs);
        this.fsDefaultFs = fsDefaultFs;
    }

    public String getResKey() {
        return resKey;
    }

    public void setResKey(String resKey) {
        DiskMirrorAutoConfiguration.LOGGER.info("setResKey run = resKey:" + resKey);
        this.resKey = resKey;
    }

    public String getProtocolPrefix() {
        return protocolPrefix;
    }

    public void setProtocolPrefix(String protocolPrefix) {
        DiskMirrorAutoConfiguration.LOGGER.info("setProtocolPrefix run = protocolPrefix:" + protocolPrefix);
        this.protocolPrefix = protocolPrefix;
    }

    public String getSecureKey() {
        return secureKey;
    }

    public void setSecureKey(String secureKey) {
        DiskMirrorAutoConfiguration.LOGGER.info("setSecureKey run = secureKey:" + secureKey);
        this.secureKey = secureKey;
    }

    public JSONObject getParams() {
        return params;
    }

    public void setParams(JSONObject params) {
        DiskMirrorAutoConfiguration.LOGGER.info("setParams run = params:" + params);
        this.params = params;
    }

    public JSONObject getSpaceMaxSize() {
        return spaceMaxSize;
    }

    public void setSpaceMaxSize(JSONObject spaceMaxSize) {
        DiskMirrorAutoConfiguration.LOGGER.info("setSpaceMaxSize run = spaceMaxSize:" + spaceMaxSize);
        this.spaceMaxSize = spaceMaxSize;
    }

    public long getUserDiskMirrorSpaceQuota() {
        return userDiskMirrorSpaceQuota;
    }

    public void setUserDiskMirrorSpaceQuota(long userDiskMirrorSpaceQuota) {
        DiskMirrorAutoConfiguration.LOGGER.info("setUserDiskMirrorSpaceQuota run = userDiskMirrorSpaceQuota:" + userDiskMirrorSpaceQuota);
        this.userDiskMirrorSpaceQuota = userDiskMirrorSpaceQuota;
    }

    public ImageCompressModuleConf getImageCompressModule() {
        return imageCompressModule;
    }

    public void setImageCompressModule(ImageCompressModuleConf imageCompressModule) {
        DiskMirrorAutoConfiguration.LOGGER.info("setImageCompressModule run = imageCompressModule:" + imageCompressModule.getType());
        this.imageCompressModule = imageCompressModule;
    }

    public boolean isEnabledFeature() {
        return enabledFeature;
    }

    public void setEnabledFeature(boolean enableFeature) {
        DiskMirrorAutoConfiguration.LOGGER.info("setEnableFeature run = enableFeature:" + enableFeature);
        this.enabledFeature = enableFeature;
    }

    public String getUseSpaceConfigMode() {
        return useSpaceConfigMode;
    }

    public void setUseSpaceConfigMode(String useSpaceConfigMode) {
        DiskMirrorAutoConfiguration.LOGGER.info("setUseSpaceConfigMode run = useSpaceConfigMode:" + useSpaceConfigMode);
        this.useSpaceConfigMode = useSpaceConfigMode;
    }

    public String getRedisHostPortDb() {
        return redisHostPortDb;
    }

    public void setRedisHostPortDb(String redisHostPortDb) {
        this.redisHostPortDb = redisHostPortDb;
    }

    public String getRedisPassword() {
        return redisPassword;
    }

    public void setRedisPassword(String redisPassword) {
        this.redisPassword = redisPassword;
    }

    public boolean isNotOverWrite() {
        return isNotOverWrite;
    }

    public void setNotOverWrite(boolean notOverWrite) {
        DiskMirrorAutoConfiguration.LOGGER.info("setNotOverWrite run = notOverWrite:" + notOverWrite);
        isNotOverWrite = notOverWrite;
    }

    public String getCharSet() {
        return charSet;
    }

    public void setCharSet(String charSet) {
        DiskMirrorAutoConfiguration.LOGGER.info("setCharSet run = charSet:" + charSet);
        this.charSet = charSet;
    }

    public String getOkValue() {
        return okValue;
    }

    public void setOkValue(String okValue) {
        DiskMirrorAutoConfiguration.LOGGER.info("setOkValue run = okValue:" + okValue);
        this.okValue = okValue;
    }

    public void setVerifications(ArrayList<String> verifications) {
        for (String verification : verifications) {
            final String[] strings = StrUtils.splitBy(verification, '$', 2);
            addVerification(strings[0], strings[1]);
        }
    }
}