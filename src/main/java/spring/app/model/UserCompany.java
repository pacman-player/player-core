package spring.app.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

//@Entity
//@Table(name = "user_company")
public class UserCompany {

//    @Embeddable
//    public class UserCompanyKey implements Serializable {
//
//        @Column(name = "userId")
//        private Long userId;
//        @Column(name = "companyId")
//        private Long companyId;
//
//        public UserCompanyKey() {
//        }
//
//        public UserCompanyKey(long userId, long companyId) {
//            this.userId = userId;
//            this.companyId = companyId;
//        }
//
//        public long getUserId() {
//            return userId;
//        }
//
//        public void setUserId(long userId) {
//            this.userId = userId;
//        }
//
//        public long getCompanyId() {
//            return companyId;
//        }
//
//        public void setCompanyId(long companyId) {
//            this.companyId = companyId;
//        }
//
//        @Override
//        public boolean equals(Object o) {
//            if (this == o) return true;
//            if (o == null || getClass() != o.getClass()) return false;
//            UserCompanyKey that = (UserCompanyKey) o;
//
//            if (userId != that.userId) return false;
//            return companyId == that.companyId;
//        }
//
//        @Override
//        public int hashCode() {
//            int result = (int) (userId ^ (userId >>> 32));
//            result = 31 * result + (int) (companyId ^ (companyId >>> 32));
//            return result;
//        }
//    }
//
////    @EmbeddedId
//    private UserCompanyKey userCompanyId = new UserCompanyKey();
//
//    @ManyToMany(fetch = FetchType.EAGER, targetEntity = RegistrationStep.class)
//    @JoinTable(name = "UserCompanyKey_RegistrationStep",
//            joinColumns = {@JoinColumn(name = "user_id"),
//                           @JoinColumn(name = "company_id")},
//            inverseJoinColumns = {@JoinColumn(name = "registrationStep_id")})
//    private List<RegistrationStep> registrationSteps;
//
//    public UserCompany() {
//    }
//
//    public UserCompany(UserCompanyKey userCompanyId, List<RegistrationStep> regStepId) {
//        this.userCompanyId = userCompanyId;
//        this.registrationSteps = regStepId;
//    }
//
//    public UserCompany(Long userId, Long companyId, List<RegistrationStep> regStepId) {
//        this.userCompanyId = new UserCompanyKey(userId, companyId);
//        this.registrationSteps = regStepId;
//    }
//
//    @EmbeddedId
//    public UserCompanyKey getUserCompanyId() {
//        return userCompanyId;
//    }
//
//    public void setUserCompanyId(UserCompanyKey userCompanyId) {
//        this.userCompanyId = userCompanyId;
//    }
//
//    public List<RegistrationStep> getRegistrationSteps() {
//        return registrationSteps;
//    }
//
//    public void setRegistrationSteps(List<RegistrationStep> registrationSteps) {
//        this.registrationSteps = registrationSteps;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        UserCompany that = (UserCompany) o;
//        return Objects.equals(userCompanyId.userId, that.userCompanyId.userId) &&
//                Objects.equals(userCompanyId.companyId, that.userCompanyId.companyId) &&
//                Objects.equals(registrationSteps, that.registrationSteps);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(userCompanyId.userId, userCompanyId.companyId, registrationSteps);
//    }
//
//    @Override
//    public String toString() {
//        return "UserCompany{" +
//                "userCompanyId=" + userCompanyId +
//                ", registrationSteps=" + registrationSteps +
//                '}';
//    }
}
