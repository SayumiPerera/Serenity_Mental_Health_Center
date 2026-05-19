package lk.ijse.mental_health_therapy_center.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "therapy_programs")
public class TherapyProgram {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "program_id")
    private int programId;

    @Column(name = "program_code", nullable = false, unique = true, length = 20)
    private String programCode;

    @Column(name = "program_name", nullable = false, length = 100)
    private String programName;

    @Column(name = "duration", nullable = false, length = 50)
    private String duration;

    @Column(name = "fee", nullable = false)
    private double fee;

    @Column(name = "description", length = 500)
    private String description;

    // One TherapyProgram → Many TherapySessions
    @OneToMany(mappedBy = "therapyProgram", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TherapySession> therapySessions = new ArrayList<>();

    public TherapyProgram() {}
    public TherapyProgram(String programCode, String programName, String duration, double fee, String description) {
        this.programCode = programCode;
        this.programName = programName;
        this.duration = duration;
        this.fee = fee;
        this.description = description;
    }

    public int getProgramId(){
        return programId;
    }
    public String getProgramCode(){
        return programCode;
    }
    public void setProgramCode(String c){
        this.programCode = c;
    }
    public String getProgramName(){
        return programName;
    }
    public void setProgramName(String n){
        this.programName = n;
    }
    public String getDuration(){
        return duration;
    }
    public void setDuration(String d){
        this.duration = d;
    }
    public double getFee(){
        return fee;
    }
    public void setFee(double f){
        this.fee = f;
    }
    public String getDescription(){
        return description;
    }
    public void setDescription(String d){
        this.description = d;
    }
    public List<TherapySession> getTherapySessions(){
        return therapySessions;
    }
    public void setTherapySessions(List<TherapySession> s) {
        this.therapySessions = s;
    }

    @Override
    public String toString() {
        return "TherapyProgram{code='" + programCode + "', name='" + programName + "', fee=" + fee + "}";
    }
}
