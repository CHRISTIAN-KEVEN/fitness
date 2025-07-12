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
@Table(name = "t_user")
public class TUser {
    @Id
    @Column(name = "lg_user_id", nullable = false, length = 150)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String lgUserId;

    @Column(name = "str_fname", nullable = false, length = 150)
    private String strFname;

    @Column(name = "str_lname", length = 150)
    private String strLname;

    @Column(name = "str_phone_number", nullable = false, length = 30)
    private String strPhoneNumber;

    @Column(name = "str_password", length = 150)
    private String strPassword;

    @Column(name = "str_id_card_number", length = 150)
    private String strIdCardNumber;

    @Column(name = "str_email", length = 200)
    private String strEmail;

    @Column(name = "str_roles", length = 200)
    private String strRoles;

    @ColumnDefault("'ENABLED'")
    @Lob
    @Column(name = "em_status", nullable = false)
    private String emStatus;

    @Lob
    @Column(name = "em_sex", nullable = false)
    private String emSex;

    @Column(name = "dt_created", nullable = false)
    private Instant dtCreated;

    @Column(name = "dt_updated")
    private Instant dtUpdated;

    @OneToMany(mappedBy = "lgUser")
    private List<TUserRole> tUserRoles;

}