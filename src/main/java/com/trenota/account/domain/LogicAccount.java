package com.trenota.account.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A LogicAccount.
 */
@Entity
@Table(name = "logic_account")
public class LogicAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "codice_fiscale", nullable = false)
    private String codiceFiscale;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "cellulare")
    private String cellulare;

    @Lob
    @Column(name = "foto")
    private byte[] foto;

    @Column(name = "foto_content_type")
    private String fotoContentType;

    @OneToOne
    @JoinColumn(unique = true)
    private User userJh;

    @OneToMany(mappedBy = "logicAccount")
    private Set<Affiliazione> logicRelations = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public LogicAccount codiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
        return this;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public String getEmail() {
        return email;
    }

    public LogicAccount email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public LogicAccount telefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCellulare() {
        return cellulare;
    }

    public LogicAccount cellulare(String cellulare) {
        this.cellulare = cellulare;
        return this;
    }

    public void setCellulare(String cellulare) {
        this.cellulare = cellulare;
    }

    public byte[] getFoto() {
        return foto;
    }

    public LogicAccount foto(byte[] foto) {
        this.foto = foto;
        return this;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getFotoContentType() {
        return fotoContentType;
    }

    public LogicAccount fotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
        return this;
    }

    public void setFotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
    }

    public User getUserJh() {
        return userJh;
    }

    public LogicAccount userJh(User user) {
        this.userJh = user;
        return this;
    }

    public void setUserJh(User user) {
        this.userJh = user;
    }

    public Set<Affiliazione> getLogicRelations() {
        return logicRelations;
    }

    public LogicAccount logicRelations(Set<Affiliazione> affiliaziones) {
        this.logicRelations = affiliaziones;
        return this;
    }

    public LogicAccount addLogicRelation(Affiliazione affiliazione) {
        this.logicRelations.add(affiliazione);
        affiliazione.setLogicAccount(this);
        return this;
    }

    public LogicAccount removeLogicRelation(Affiliazione affiliazione) {
        this.logicRelations.remove(affiliazione);
        affiliazione.setLogicAccount(null);
        return this;
    }

    public void setLogicRelations(Set<Affiliazione> affiliaziones) {
        this.logicRelations = affiliaziones;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LogicAccount logicAccount = (LogicAccount) o;
        if (logicAccount.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), logicAccount.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LogicAccount{" +
            "id=" + getId() +
            ", codiceFiscale='" + getCodiceFiscale() + "'" +
            ", email='" + getEmail() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", cellulare='" + getCellulare() + "'" +
            ", foto='" + getFoto() + "'" +
            ", fotoContentType='" + getFotoContentType() + "'" +
            "}";
    }
}
