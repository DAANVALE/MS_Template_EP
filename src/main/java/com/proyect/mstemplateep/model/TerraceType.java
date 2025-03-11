package com.proyect.mstemplateep.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "terrace_type")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TerraceType
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

    public Integer getId() {
        return id;
    }

    public void setKilled(Byte killed) {
        this.killed = killed;
    }
}
