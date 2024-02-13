package top.lingyuzhao.diskMirror.starter.conf.properties;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.boot.context.properties.ConfigurationProperties;
import top.lingyuzhao.diskMirror.conf.Config;
import top.lingyuzhao.diskMirror.core.DiskMirror;
import top.lingyuzhao.diskMirror.starter.conf.DiskMirrorAutoConfiguration;

@ConfigurationProperties(
        prefix = "disk-mirror"
)
public class DiskMirrorProperties {

    // 修改为字符串类型，并添加一个setter方法用于从配置文件中注入适配器名称
    private String adapterType = DiskMirror.LocalFSAdapter.name();

    private String rootDir, fsDefaultFs, resKey, protocolPrefix, secureKey;
    private JSONObject params, spaceMaxSize;
    private long userDiskMirrorSpaceQuota;

    public Config getConfig() {
        final Config config = new Config();
        config.put(Config.ROOT_DIR, rootDir);
        config.put(Config.FS_DEFAULT_FS, fsDefaultFs);
        config.put(Config.RES_KEY, resKey);
        config.put(Config.PROTOCOL_PREFIX, protocolPrefix);
        config.put(Config.PARAMS, params);
        this.spaceMaxSize.forEach(
                (k, v) -> config.setSpaceMaxSize(k, (Long) v)
        );
        config.put(Config.PARAMS, params);
        config.put(Config.USER_DISK_MIRROR_SPACE_QUOTA, userDiskMirrorSpaceQuota);
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
     */
    public void setAdapterType(String adapterType) {
        this.adapterType = adapterType;
    }

    public String getRootDir() {
        return rootDir;
    }

    public void setRootDir(String rootDir) {
        this.rootDir = rootDir;
    }

    public String getFsDefaultFs() {
        return fsDefaultFs;
    }

    public void setFsDefaultFs(String fsDefaultFs) {
        this.fsDefaultFs = fsDefaultFs;
    }

    public String getResKey() {
        return resKey;
    }

    public void setResKey(String resKey) {
        this.resKey = resKey;
    }

    public String getProtocolPrefix() {
        return protocolPrefix;
    }

    public void setProtocolPrefix(String protocolPrefix) {
        this.protocolPrefix = protocolPrefix;
    }

    public String getSecureKey() {
        return secureKey;
    }

    public void setSecureKey(String secureKey) {
        this.secureKey = secureKey;
    }

    public JSONObject getParams() {
        return params;
    }

    public void setParams(JSONObject params) {
        this.params = params;
    }

    public JSONObject getSpaceMaxSize() {
        return spaceMaxSize;
    }

    public void setSpaceMaxSize(JSONObject spaceMaxSize) {
        this.spaceMaxSize = spaceMaxSize;
    }

    public long getUserDiskMirrorSpaceQuota() {
        return userDiskMirrorSpaceQuota;
    }

    public void setUserDiskMirrorSpaceQuota(long userDiskMirrorSpaceQuota) {
        this.userDiskMirrorSpaceQuota = userDiskMirrorSpaceQuota;
    }
}