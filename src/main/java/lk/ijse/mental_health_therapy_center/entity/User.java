package lk.ijse.mental_health_therapy_center.entity;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    public enum Role {
        ADMIN,
        RECEPTIONIST
    }

    public User() {}
    public User(String username, String password, Role role, String fullName, String email) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.fullName = fullName;
        this.email = email;
    }

    public int getUserId(){
        return userId;
    }
    public String getUsername(){
        return username;
    }
    public void setUsername(String u) {
        this.username = u;
    }
    public String getPassword(){
        return password;
    }
    public void setPassword(String p) {
        this.password = p;
    }
    public Role getRole(){
        return role;
    }
    public void setRole(Role r){
        this.role = r;
    }
    public String getFullName(){
        return fullName;
    }
    public void setFullName(String n) {
        this.fullName = n;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String e){
        this.email = e;
    }

    @Override
    public String toString() {
        return "User{id=" + userId + ", username='" + username + "', role=" + role + "}";
    }
}
