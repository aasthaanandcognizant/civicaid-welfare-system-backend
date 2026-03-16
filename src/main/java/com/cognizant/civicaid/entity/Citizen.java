package com.cognizant.civicaid.entity;

import com.cognizant.civicaid.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Citizen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CitizenID")
    private Long citizenId;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "DOB")
    private LocalDate dob;

    @Column(name = "Gender", nullable = false)
    private String gender;

    @Column(name = "Address")
    private String address;

    @Column(name = "ContactInfo", nullable = false)
    private String contactInfo;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false)
    private Status status;

    // Mappings
    //PK
    //orphanRemoval = true , If a child entity is removed from the parent’s collection,
    //and it no longer has any parent, then delete it from the database automatically.
    @OneToMany(mappedBy = "citizen", orphanRemoval = false)
    @Builder.Default
    private List<CitizenDocument> documents = new ArrayList<>();


    @OneToMany(mappedBy = "citizen", orphanRemoval = false)
    @Builder.Default
    private List<WelfareApplication> applications = new ArrayList<>();


    @OneToOne(mappedBy = "citizen", cascade = CascadeType.ALL)
    private AppUser user;

}