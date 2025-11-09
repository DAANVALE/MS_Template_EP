package com.proyect.mstemplateep.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

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
    @Column(name = "kind", length = 50)
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

    @OneToMany(mappedBy = "terraceType", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // Evita loops infinitos en JSON
    private Set<Template> templates;

    @ManyToMany(targetEntity = Terrace.class,
            mappedBy = "terraceType",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private Set<Terrace> terrace;
}
