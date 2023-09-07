package model;

import jakarta.persistence.*;

import java.util.Date;

@Entity(name = "satellite")
@Table(name = "satellite")
public class SatelliteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "x", nullable = false)
    private Integer x;

    @Column(name = "y", nullable = false)
    private Integer y;

    @Column(name = "created", nullable = false)
    private Date created;

    public SatelliteEntity() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public Date getCreated() {
        return created;
    }
}
