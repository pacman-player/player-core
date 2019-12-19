package spring.app.model;



import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "usr")
public class GoogleUsers {
        @Id
        private String id;
        private String name;
        private String email;
        private String locale;
        private LocalDateTime lastVisit;

        public GoogleUsers(String id, String name, String email, String locale, LocalDateTime lastVisit) {
                this.id = id;
                this.name = name;
                this.email = email;
                this.locale = locale;
                this.lastVisit = lastVisit;
        }

        public Set<Role> getRoles() {
                return roles;
        }

        public void setRoles(Set<Role> roles) {
                this.roles = roles;
        }

        @ManyToMany(fetch = FetchType.EAGER, targetEntity = Role.class)
        @JoinTable(name = "google_permissions",
                joinColumns = {@JoinColumn(name = "google_user_id")},
                inverseJoinColumns = {@JoinColumn(name = "role_id")})
        private Set<Role> roles;


        public GoogleUsers() {
        }

        public String getId() {
                return id;
        }

        public void setId(String id) {
                this.id = id;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public String getLocale() {
                return locale;
        }

        public void setLocale(String locale) {
                this.locale = locale;
        }

        public LocalDateTime getLastVisit() {
                return lastVisit;
        }

        public void setLastVisit(LocalDateTime lastVisit) {
                this.lastVisit = lastVisit;
        }
}

