package org.dasher.speed.base.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "administrators")
public class Administrator extends Person {
    // Hereda todo de Person
}
