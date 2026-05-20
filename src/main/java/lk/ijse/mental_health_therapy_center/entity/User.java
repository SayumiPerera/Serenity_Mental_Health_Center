package lk.ijse.mental_health_therapy_center.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user")
public class User {

    @Id
    @Column(name = "username", nullable = false, unique = true, length = 30)
    private String username;

    @Column(name = "password", nullable = false, length = 255)
    private String password;           // BCrypt hash stored here — NEVER plain text

    @Column(name = "role", nullable = false, length = 20)
    private String role;               // "ADMIN" or "RECEPTIONIST"


    // ─── Getters & Setters ────────────────────────────────────────────────────

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
}