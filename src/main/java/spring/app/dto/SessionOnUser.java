package spring.app.dto;

/**
 * Created by User on 14.03.2018.
 */
public class SessionOnUser {
	private String sessionId;
	private UserDto userDto;

	public SessionOnUser(String sessionId, UserDto userDto) {
		this.sessionId = sessionId;
		this.userDto = userDto;
	}

	public SessionOnUser() {
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public UserDto getUserDto() {
		return userDto;
	}

	public void setUserDto(UserDto userDto) {
		this.userDto = userDto;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		SessionOnUser that = (SessionOnUser) o;

		if (sessionId != null ? !sessionId.equals(that.sessionId) : that.sessionId != null) return false;
		return userDto != null ? userDto.equals(that.userDto) : that.userDto == null;
	}

	@Override
	public int hashCode() {
		int result = sessionId != null ? sessionId.hashCode() : 0;
		result = 31 * result + (userDto != null ? userDto.hashCode() : 0);
		return result;
	}
}
