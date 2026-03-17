package com.cognizant.civicaid.entity;

import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AuditLogID")
    private Long auditId;

    //Relation - (FK) we can fetch from appuser so used appuser datatype
    //fetch - it controls how jjpa loads,
    //lazy - loads data when we call like user.userId, eager - loads everytime with parent class
    //fetch data like user.
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "UserID", nullable = false) //here name is user but not need to be same as parent
    private User user;

    private String action;
    private String resource;
    private LocalDateTime timestamp;


}
