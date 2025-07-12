package com.fitness.user_service.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "t_user_role")
public class TUserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lg_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lg_user_id", nullable = false)
    private TUser lgUser;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lg_role_id", nullable = false)
    private TRole lgRole;

    @ColumnDefault("'ENABLED'")
    @Lob
    @Column(name = "em_status", nullable = false)
    private String emStatus;

    @Column(name = "dt_created", nullable = false)
    private Instant dtCreated;

    @Column(name = "dt_updated")
    private Instant dtUpdated;

}