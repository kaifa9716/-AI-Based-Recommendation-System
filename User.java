
public class User {
    private long userId;
    private String username;
    private String email;
    
    public User(long userId, String username, String email) {
        this.userId = userId;
        this.username = username;
        this.email = email;
    }
    
    public User(long userId, String username) {
        this.userId = userId;
        this.username = username;
        this.email = "";
    }
    
    // Getters
    public long getUserId() {
        return userId;
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getEmail() {
        return email;
    }
    
    // Setters
    public void setUserId(long userId) {
        this.userId = userId;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return userId == user.userId;
    }
    
    @Override
    public int hashCode() {
        return Long.hashCode(userId);
    }
}