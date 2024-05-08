package org.ergea.springapp.ch4.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Where;

import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "employee_training")
@Where(clause = "deleted_date is null")
public class EmployeeTraining extends AbstractDate implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column
    private Date tanggal;

    //relasi antar tabel ditandai adanya many to one
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_training")
    private Training training;

    @ManyToOne(cascade = CascadeType.ALL) // FK dari tabel
    @JoinColumn(name = "id_employee")
    private Employee employee;
}
