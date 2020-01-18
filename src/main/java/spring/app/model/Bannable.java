package spring.app.model;

import java.util.Set;

public abstract class Bannable {

    public abstract void setBanned(boolean banned);

    public abstract boolean isBannedBy(Company company);
}
