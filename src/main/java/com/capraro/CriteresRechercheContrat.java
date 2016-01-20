package com.capraro;

public enum CriteresRechercheContrat {
    ID_INT(1),
    CHAINE_RECHERCHE(2),
    TYPE_RECHERCHE(3),
    NB_RES_MAX(4),
    ERRORS(5),
    NB_ERRORS(6),
    ID_CT(7),
    NUM_CONT_CT(8),
    DATE_EFFET(9),
    POSITION(10),
    NOM_ASSURE(11),
    NOM_CLIENT(12),
    ADRESSE_RISQUE(13),
    DATE_SAISIE(14),
    CD_SMV(15),
    REFERENCE_PRODUIT(16),
    ID_TI(17),
    CD_FRC(18),
    CD_MDR(19);

    private final int code;

    CriteresRechercheContrat(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }
}
