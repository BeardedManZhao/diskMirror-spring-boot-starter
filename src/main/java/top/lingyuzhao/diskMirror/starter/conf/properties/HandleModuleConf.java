package top.lingyuzhao.diskMirror.starter.conf.properties;

import top.lingyuzhao.diskMirror.core.module.HandleModule;

/**
 * 处理模块配置，当接收到请求之后，处理模块会根据配置信息进行相应的处理，这对于模块的配置进行灵活控制。
 *
 * @author zhao
 */
public abstract class HandleModuleConf {

    private boolean enable = false;

    /**
     * @return 处理模块类型对应的模块对象
     */
    public abstract HandleModule getType();

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
