package top.lingyuzhao.diskMirror.starter.conf.properties;

import top.lingyuzhao.diskMirror.core.module.HandleModule;
import top.lingyuzhao.utils.PaletteGenerator;
import top.lingyuzhao.utils.PalettePng;

/**
 * 图片压缩模块配置
 *
 * @author zhao
 */
public class ImageCompressModuleConf extends HandleModuleConf {

    private final HandleModule module = HandleModule.ImageCompressModule;

    private PalettePng palettePng = PalettePng.RGB_8;

    @Override
    public HandleModule getType() {
        module.setImageCompress(this.palettePng);
        return module;
    }

    public void setPalettePng(String palettePngName) {
        final PaletteGenerator paletteGenerator = this.palettePng.getPaletteGenerator();
        this.palettePng = PalettePng.valueOf(palettePngName);
        this.palettePng.setPaletteGenerator(paletteGenerator);
    }

    public void setPaletteGenerator(String paletteGenerator) {
        this.palettePng.setPaletteGenerator(PaletteGenerator.valueOf(paletteGenerator));
    }

    public void setTransparent(boolean transparent) {
        this.palettePng.getPaletteGenerator().setTransparent(transparent);
    }
}
