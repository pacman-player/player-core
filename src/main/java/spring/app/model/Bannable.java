package spring.app.model;

import java.util.Set;

public interface Bannable {

    void setBanned(boolean banned);

    boolean isBannedBy(Company company);
}
