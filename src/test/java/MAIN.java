import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import top.lingyuzhao.diskMirror.starter.conf.DiskMirrorAutoConfiguration;
import top.lingyuzhao.diskMirror.starter.conf.properties.DiskMirrorProperties;

/**
 * @author zhao
 */
@SpringBootTest(classes = {DiskMirrorAutoConfiguration.class, DiskMirrorProperties.class})
public class MAIN {
    public static void main(String[] args) {
        final ConfigurableApplicationContext run = SpringApplication.run(MAIN.class);
        System.out.println(run.getBean(DiskMirrorProperties.class).getProtocolPrefix());
    }
}
