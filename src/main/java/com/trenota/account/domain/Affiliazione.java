package com.trenota.account.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Affiliazione.
 */
@Entity
@Table(name = "affiliazione")
public class Affiliazione implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cod_punto_vendita_ext")
    private String codPuntoVenditaExt;

    @Column(name = "data_affiliazione")
    private LocalDate dataAffiliazione;

    @ManyToOne
    @JsonIgnoreProperties("logicRelations")
    private LogicAccount logicAccount;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodPuntoVenditaExt() {
        return codPuntoVenditaExt;
    }

    public Affiliazione codPuntoVenditaExt(String codPuntoVenditaExt) {
        this.codPuntoVenditaExt = codPuntoVenditaExt;
        return this;
    }

    public void setCodPuntoVenditaExt(String codPuntoVenditaExt) {
        this.codPuntoVenditaExt = codPuntoVenditaExt;
    }

    public LocalDate getDataAffiliazione() {
        return dataAffiliazione;
    }

    public Affiliazione dataAffiliazione(LocalDate dataAffiliazione) {
        this.dataAffiliazione = dataAffiliazione;
        return this;
    }

    public void setDataAffiliazione(LocalDate dataAffiliazione) {
        this.dataAffiliazione = dataAffiliazione;
    }

    public LogicAccount getLogicAccount() {
        return logicAccount;
    }

    public Affiliazione logicAccount(LogicAccount logicAccount) {
        this.logicAccount = logicAccount;
        return this;
    }

    public void setLogicAccount(LogicAccount logicAccount) {
        this.logicAccount = logicAccount;
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
        Affiliazione affiliazione = (Affiliazione) o;
        if (affiliazione.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), affiliazione.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Affiliazione{" +
            "id=" + getId() +
            ", codPuntoVenditaExt='" + getCodPuntoVenditaExt() + "'" +
            ", dataAffiliazione='" + getDataAffiliazione() + "'" +
            "}";
    }
}
