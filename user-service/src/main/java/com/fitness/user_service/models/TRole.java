package com.fitness.user_service.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "t_role")
public class TRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "str_key", nullable = false, length = 50)
    private String strKey;

    @ColumnDefault("1")
    @Column(name = "bl_active", nullable = false)
    private Boolean blActive = false;

    @Column(name = "dt_created", nullable = false)
    private Instant dtCreated;

    @Column(name = "dt_updated")
    private Instant dtUpdated;

    @OneToMany(mappedBy = "lgRole")
    private List<TUserRole> tUserRoles;

}