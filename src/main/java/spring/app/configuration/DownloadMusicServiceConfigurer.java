package spring.app.configuration;

import org.springframework.stereotype.Component;

/**
 * Класс, реализующий интерфейс {@link DownloadMusicServiceConfigurerMBean}
 * необходимый для подключения к приложению в Runtime через <b>jconsole</b> и
 * изменения параметров очередности музыкальных сервисов.
 * Должен находиться с интерфейсом MBean в одном пакете.
 * <p>
 * {@link DownloadMusicServiceFactory}
 */
@Component
public class DownloadMusicServiceConfigurer implements DownloadMusicServiceConfigurerMBean {
    private DownloadMusicServiceFactory factory;

    public DownloadMusicServiceConfigurer(DownloadMusicServiceFactory factory) {
        this.factory = factory;
    }

    public String getOne() {
        return factory.getOne();
    }

    public void setOne(String one) {
        this.factory.setOne(one);
    }

    public String getTwo() {
        return factory.getTwo();
    }

    public void setTwo(String two) {
        this.factory.setTwo(two);
    }

    public String getThree() {
        return factory.getThree();
    }

    public void setThree(String three) {
        this.factory.setThree(three);
    }

    public String getFour() {
        return factory.getFour();
    }

    public void setFour(String four) {
        this.factory.setFour(four);
    }

    public String getFive() {
        return factory.getFive();
    }

    public void setFive(String five) {
        this.factory.setFive(five);
    }
}