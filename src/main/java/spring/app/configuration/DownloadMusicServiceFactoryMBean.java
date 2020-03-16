package spring.app.configuration;

import spring.app.service.abstraction.DownloadMusicService;

import java.util.List;

public interface DownloadMusicServiceFactoryMBean {

    String getOne();

    void setOne(String one);

    String getTwo();

    void setTwo(String two);

    String getThree();

    void setThree(String three);

    String getFour();

    void setFour(String four);

    public DownloadMusicService getService(String implName);

    public List<DownloadMusicService> getDownloadServices();
}
