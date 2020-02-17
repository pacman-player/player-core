package spring.app.model;

public abstract class Bannable {

    public abstract void setBanned(boolean banned);

    public abstract boolean isBannedBy(Company company);
}
