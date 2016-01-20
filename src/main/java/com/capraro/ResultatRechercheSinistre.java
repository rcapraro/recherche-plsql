package com.capraro;

import java.io.Serializable;

public class ResultatRechercheSinistre implements Serializable, Cloneable{

    private Integer idCt;
    private String contrat;
    private String dateEffet;
    private String position;
    private String assure;
    private String client;
    private String risque;
    private String dateSaisie;
    private String cdSmv;
    private String refProduit;
    private Integer idTi;
    private String cdFrc;
    private String cdMdr;

    public Integer getIdCt() {
        return idCt;
    }

    public void setIdCt(final Integer idCt) {
        this.idCt = idCt;
    }

    public String getContrat() {
        return contrat;
    }

    public void setContrat(final String contrat) {
        this.contrat = contrat;
    }

    public String getDateEffet() {
        return dateEffet;
    }

    public void setDateEffet(final String dateEffet) {
        this.dateEffet = dateEffet;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(final String position) {
        this.position = position;
    }

    public String getAssure() {
        return assure;
    }

    public void setAssure(final String assure) {
        this.assure = assure;
    }

    public String getClient() {
        return client;
    }

    public void setClient(final String client) {
        this.client = client;
    }

    public String getRisque() {
        return risque;
    }

    public void setRisque(final String risque) {
        this.risque = risque;
    }

    public String getDateSaisie() {
        return dateSaisie;
    }

    public void setDateSaisie(final String dateSaisie) {
        this.dateSaisie = dateSaisie;
    }

    public String getCdSmv() {
        return cdSmv;
    }

    public void setCdSmv(final String cdSmv) {
        this.cdSmv = cdSmv;
    }

    public String getRefProduit() {
        return refProduit;
    }

    public void setRefProduit(final String refProduit) {
        this.refProduit = refProduit;
    }

    public Integer getIdTi() {
        return idTi;
    }

    public void setIdTi(final Integer idTi) {
        this.idTi = idTi;
    }

    public String getCdFrc() {
        return cdFrc;
    }

    public void setCdFrc(final String cdFrc) {
        this.cdFrc = cdFrc;
    }

    public String getCdMdr() {
        return cdMdr;
    }

    public void setCdMdr(final String cdMdr) {
        this.cdMdr = cdMdr;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ResultatRechercheContrat{");
        sb.append("idCt=").append(idCt);
        sb.append(", contrat='").append(contrat).append('\'');
        sb.append(", dateEffet='").append(dateEffet).append('\'');
        sb.append(", position='").append(position).append('\'');
        sb.append(", assure='").append(assure).append('\'');
        sb.append(", client='").append(client).append('\'');
        sb.append(", risque='").append(risque).append('\'');
        sb.append(", dateSaisie='").append(dateSaisie).append('\'');
        sb.append(", cdSmv='").append(cdSmv).append('\'');
        sb.append(", refProduit='").append(refProduit).append('\'');
        sb.append(", idTi=").append(idTi);
        sb.append(", cdFrc='").append(cdFrc).append('\'');
        sb.append(", cdMdr='").append(cdMdr).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
