package spring.app.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "user_company")
public class UserCompany {

    @Embeddable
    public class UserCompanyKey implements Serializable {

        @Column(name = "userId")
        private Long userId;
        @Column(name = "companyId")
        private Long companyId;

        public UserCompanyKey() {
        }

        public UserCompanyKey(long userId, long companyId) {
            this.userId = userId;
            this.companyId = companyId;
        }

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public long getCompanyId() {
            return companyId;
        }

        public void setCompanyId(long companyId) {
            this.companyId = companyId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            UserCompanyKey that = (UserCompanyKey) o;

            if (userId != that.userId) return false;
            return companyId == that.companyId;
        }

        @Override
        public int hashCode() {
            int result = (int) (userId ^ (userId >>> 32));
            result = 31 * result + (int) (companyId ^ (companyId >>> 32));
            return result;
        }
    }

    @EmbeddedId
    private UserCompanyKey userCompanyId;

    private Long regStepId;

    public UserCompany() {
    }

    public UserCompany(UserCompanyKey userCompanyId, Long regStepId) {
        this.userCompanyId = userCompanyId;
        this.regStepId = regStepId;
    }

    public UserCompany(Long userId, Long companyId, Long regStepId) {
        this.userCompanyId = new UserCompanyKey(userId, companyId);
        this.regStepId = regStepId;
    }

    public UserCompanyKey getUserCompanyId() {
        return userCompanyId;
    }

    public void setUserCompanyId(UserCompanyKey userCompanyId) {
        this.userCompanyId = userCompanyId;
    }

    public Long getRegStepId() {
        return regStepId;
    }

    public void setRegStepId(Long regStepId) {
        this.regStepId = regStepId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserCompany that = (UserCompany) o;
        return Objects.equals(userCompanyId.userId, that.userCompanyId.userId) &&
                Objects.equals(userCompanyId.companyId, that.userCompanyId.companyId) &&
                Objects.equals(regStepId, that.regStepId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userCompanyId.userId, userCompanyId.companyId, regStepId);
    }

    @Override
    public String toString() {
        return "UserCompany{" +
                "userCompanyId=" + userCompanyId +
                ", registrationSteps=" + regStepId +
                '}';
    }
}
