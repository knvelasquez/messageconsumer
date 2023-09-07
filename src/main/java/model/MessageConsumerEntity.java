package model;

import jakarta.persistence.*;

import java.util.Date;

@Entity(name = "message")
@Table(name = "message")
public class MessageConsumerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "created", nullable = false)
    private Date created;

    @Column(name = "id_satellite", nullable = false)
    private Integer idSatellite;

  /*  @ManyToOne
    @JoinColumn(name = "id_satellite", nullable = false)
    private SatelliteEntity satellite;*/

    public MessageConsumerEntity() {
    }

    public MessageConsumerEntity(String content, Integer idSatellite) {
        this.content = content;
        this.idSatellite = idSatellite;
        this.created = new Date();
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Date getCreated() {
        return created;
    }
}
