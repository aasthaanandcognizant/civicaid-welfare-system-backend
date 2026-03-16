package com.cognizant.civicaid.entity;

import com.cognizant.civicaid.enums.Category; // Application, Disbursement, Compliance
import com.cognizant.civicaid.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NotificationID")
    private Long notificationId;

    //FK
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "UserID", nullable = false)
    private AppUser user;

    @Column(name = "EntityID")
    private Long entityId;

    @Enumerated(EnumType.STRING)
    @Column(name = "Category", nullable = false)
    private Category category;

    @Column(name = "Message", columnDefinition = "text")
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false)
    private Status status;

    @Column(name = "CreatedDate")
    private LocalDate createdDate;
}