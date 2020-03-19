package spring.app.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "notification_template")
public class NotificationTemplate {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "template")
    private String template;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationTemplate that = (NotificationTemplate) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "NotificationTemplate{" +
                "id=" + id +
                ", template='" + template + '\'' +
                '}';
    }
}
