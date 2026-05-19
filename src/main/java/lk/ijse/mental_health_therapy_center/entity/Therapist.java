package lk.ijse.mental_health_therapy_center.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "therapists")
public class Therapist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "therapist_id")
    private int therapistId;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "phone", nullable = false, length = 15)
    private String phone;

    @Column(name = "specialization", length = 100)
    private String specialization;

    @Column(name = "available", nullable = false)
    private boolean available = true;

    // One Therapist → Many TherapySessions
    @OneToMany(mappedBy = "therapist", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TherapySession> therapySessions = new ArrayList<>();

    public Therapist() {}
    public Therapist(String firstName, String lastName, String email, String phone, String specialization) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.specialization = specialization;
    }

    public int getTherapistId(){
        return therapistId;
    }
    public String getFirstName(){
        return firstName;
    }
    public void setFirstName(String n){
        this.firstName = n;
    }
    public String getLastName(){
        return lastName;
    }
    public void setLastName(String n){
        this.lastName = n;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String e){
        this.email = e;
    }
    public String getPhone(){
        return phone;
    }
    public void setPhone(String p){
        this.phone = p;
    }
    public String getSpecialization(){
        return specialization;
    }
    public void setSpecialization(String s){
        this.specialization = s;
    }
    public boolean isAvailable(){
        return available;
    }
    public void setAvailable(boolean a){
        this.available = a;
    }
    public List<TherapySession> getTherapySessions(){
        return therapySessions;
    }
    public void setTherapySessions(List<TherapySession> s) {
        this.therapySessions = s;
    }
    public String getFullName(){
        return firstName + " " + lastName;
    }
}