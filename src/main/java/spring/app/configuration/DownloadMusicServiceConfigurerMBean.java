package spring.app.configuration;

/**
 * Интерфейс, необходимый для реализации JMX API, для подключения к приложению в Runtime
 * и изменения параметров, в частности, очередности музыкальных сервисов.
 * Должен находиться в одном пакете с реализующим его классом {@link DownloadMusicServiceConfigurer}
 */
public interface DownloadMusicServiceConfigurerMBean {

    String getOne();

    void setOne(String one);

    String getTwo();

    void setTwo(String two);

    String getThree();

    void setThree(String three);

    String getFour();

    void setFour(String four);

    String getFive();

    void setFive(String five);
}
