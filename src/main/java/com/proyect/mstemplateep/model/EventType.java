package com.proyect.mstemplateep.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "event_type")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EventType
{
    @Id
    @JsonProperty("id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @JsonProperty("kind")
    @Column(name = "kind", length = 20)
    private String kind;

    @JsonProperty("killed")
    @Column(name = "killed")
    private Byte killed = 0;

    public void setKilled(Byte killed) {
        this.killed = killed;
    }
}
