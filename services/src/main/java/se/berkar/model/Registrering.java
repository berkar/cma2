package se.berkar.model;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "registration_list")
@SequenceGenerator(name = "SQ_CMA", sequenceName = "sq_cma", allocationSize = 1)
public class Registrering extends Item {
}

