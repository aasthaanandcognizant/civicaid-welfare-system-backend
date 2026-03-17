package com.cognizant.civicaid.entity;

import com.cognizant.civicaid.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(name = "uc_user_email", columnNames = "Email"),
                @UniqueConstraint(name = "uc_user_phone", columnNames = "Phone")
        }

)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder

public class User {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "UserID")
        private Long userId;

        @Column(name = "Name")
        private String name;

        @Enumerated(EnumType.STRING)
        @Column(name = "Role")
        private Role role;

        @Column(name = "Email", nullable = false)
        private String email;

        @Column(name = "Phone")
        private String phone;

        @Column(name = "Status")
        private String status;

        @Column(name = "Password", nullable = false)
        private String password;

        @Column(name = "CreatedAt")
        private OffsetDateTime createdAt;

        @Column(name = "UpdatedAt")
        private OffsetDateTime updatedAt;

        // mappings below;
        //PK
        @OneToMany(mappedBy = "user", cascade = CascadeType.ALL) // this should match with the fiels name in child i.e like user in auditLog
        @Builder.Default  // - like it will not keep value null, it'll put default when not made any value
        private List<AuditLog> auditLogs = new ArrayList<>();

        @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
        @Builder.Default
        private List<Notification> notifications = new ArrayList<>();

        @OneToMany(mappedBy = "officer", cascade = CascadeType.ALL)
        @Builder.Default
        private List<Audit> audits = new ArrayList<>();

        //FK  --- need verification from team!!!!!!!!!!
        //to get citizen easily
        @OneToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "CitizenID", nullable = false, unique = true)
        private Citizen citizen;

    }

