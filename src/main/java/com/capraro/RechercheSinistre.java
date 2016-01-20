package com.capraro;

import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RechercheSinistre {

    public List<ResultatRechercheContrat> rechercheContrat(String critere, String numInt, String urlJdbc) throws ClassNotFoundException, SQLException {

        Connection con = null;
        CallableStatement callableStatementProc = null;
        PreparedStatement preparedStatementIdInt;
        String selectContratQuery = "{call EXPLOIT.PKG_RECHERCHE_WEB.SELECT_CONTRAT(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        String idIntQuery = "select ID_INT from APPORT where NUM_INT = ?";
        Integer idInt;

        try {
            con = DriverManager.getConnection(urlJdbc, "exploit", "exploit");

            preparedStatementIdInt = con.prepareStatement(idIntQuery);
            preparedStatementIdInt.setString(1, numInt);
            final ResultSet resultSet = preparedStatementIdInt.executeQuery();

            if (resultSet.next()) {
                idInt = resultSet.getInt(1);
            } else {
                throw new IllegalArgumentException("Aucun courtier correspondant pour le NUM_INT " + numInt);
            }

            callableStatementProc = con.prepareCall(selectContratQuery);

            callableStatementProc.setInt(CriteresRechercheContrat.ID_INT.getCode(), idInt); // Identifiant Intermédiaire
            callableStatementProc.setString(CriteresRechercheContrat.CHAINE_RECHERCHE.getCode(), critere); // Chaine de recherche
            callableStatementProc.setString(CriteresRechercheContrat.TYPE_RECHERCHE.getCode(), "CONSULT"); // Mouvement apporteur autorisé
            callableStatementProc.setInt(CriteresRechercheContrat.NB_RES_MAX.getCode(), 1000); // Nombre de résultats maximum

            callableStatementProc.registerOutParameter(CriteresRechercheContrat.ERRORS.getCode(), OracleTypes.VARCHAR); // Message d'erreur
            callableStatementProc.registerOutParameter(CriteresRechercheContrat.NB_ERRORS.getCode(), OracleTypes.NUMBER); // Nombre de résultats

            callableStatementProc.registerOutParameter(CriteresRechercheContrat.ID_CT.getCode(), OracleTypes.ARRAY, "V_ARRAY");
            callableStatementProc.registerOutParameter(CriteresRechercheContrat.NUM_CONT_CT.getCode(), OracleTypes.ARRAY, "V_ARRAY");
            callableStatementProc.registerOutParameter(CriteresRechercheContrat.DATE_EFFET.getCode(), OracleTypes.ARRAY, "V_ARRAY");
            callableStatementProc.registerOutParameter(CriteresRechercheContrat.POSITION.getCode(), OracleTypes.ARRAY, "V_ARRAY");
            callableStatementProc.registerOutParameter(CriteresRechercheContrat.NOM_ASSURE.getCode(), OracleTypes.ARRAY, "V_ARRAY");
            callableStatementProc.registerOutParameter(CriteresRechercheContrat.NOM_CLIENT.getCode(), OracleTypes.ARRAY, "V_ARRAY");
            callableStatementProc.registerOutParameter(CriteresRechercheContrat.ADRESSE_RISQUE.getCode(), OracleTypes.ARRAY, "V_ARRAY");
            callableStatementProc.registerOutParameter(CriteresRechercheContrat.DATE_SAISIE.getCode(), OracleTypes.ARRAY, "V_ARRAY");
            callableStatementProc.registerOutParameter(CriteresRechercheContrat.CD_SMV.getCode(), OracleTypes.ARRAY, "V_ARRAY");
            callableStatementProc.registerOutParameter(CriteresRechercheContrat.REFERENCE_PRODUIT.getCode(), OracleTypes.ARRAY, "V_ARRAY");
            callableStatementProc.registerOutParameter(CriteresRechercheContrat.ID_TI.getCode(), OracleTypes.ARRAY, "V_ARRAY");
            callableStatementProc.registerOutParameter(CriteresRechercheContrat.CD_FRC.getCode(), OracleTypes.ARRAY, "V_ARRAY");
            callableStatementProc.registerOutParameter(CriteresRechercheContrat.CD_MDR.getCode(), OracleTypes.ARRAY, "V_ARRAY");

            callableStatementProc.execute();

            List<ResultatRechercheContrat> resultatsRechercheContrat = new ArrayList<>();

            if (callableStatementProc.getInt(CriteresRechercheContrat.NB_ERRORS.getCode()) != 0) {
                String[] s7, s8, s9, s10, s11, s12, s13, s14, s15, s16, s17, s18, s19;
                s7 = (String[]) callableStatementProc.getArray(CriteresRechercheContrat.ID_CT.getCode()).getArray();
                s8 = (String[]) callableStatementProc.getArray(CriteresRechercheContrat.NUM_CONT_CT.getCode()).getArray();
                s9 = (String[]) callableStatementProc.getArray(CriteresRechercheContrat.DATE_EFFET.getCode()).getArray();
                s10 = (String[]) callableStatementProc.getArray(CriteresRechercheContrat.POSITION.getCode()).getArray();
                s11 = (String[]) callableStatementProc.getArray(CriteresRechercheContrat.NOM_ASSURE.getCode()).getArray();
                s12 = (String[]) callableStatementProc.getArray(CriteresRechercheContrat.NOM_CLIENT.getCode()).getArray();
                s13 = (String[]) callableStatementProc.getArray(CriteresRechercheContrat.ADRESSE_RISQUE.getCode()).getArray();
                s14 = (String[]) callableStatementProc.getArray(CriteresRechercheContrat.DATE_SAISIE.getCode()).getArray();
                s15 = (String[]) callableStatementProc.getArray(CriteresRechercheContrat.CD_SMV.getCode()).getArray();
                s16 = (String[]) callableStatementProc.getArray(CriteresRechercheContrat.REFERENCE_PRODUIT.getCode()).getArray();
                s17 = (String[]) callableStatementProc.getArray(CriteresRechercheContrat.ID_TI.getCode()).getArray();
                s18 = (String[]) callableStatementProc.getArray(CriteresRechercheContrat.CD_FRC.getCode()).getArray();
                s19 = (String[]) callableStatementProc.getArray(CriteresRechercheContrat.CD_MDR.getCode()).getArray();

                for (int i = 0; i < callableStatementProc.getInt(CriteresRechercheContrat.NB_ERRORS.getCode()); i++) {
                    ResultatRechercheContrat dto = new ResultatRechercheContrat();
                    dto.setIdCt(Integer.valueOf(s7[i]));
                    dto.setContrat(CommonFormat.boldMarkersOnly(s8[i]));
                    dto.setDateEffet(CommonFormat.boldMarkers(s9[i]));
                    dto.setPosition(CommonFormat.boldMarkers(s10[i]));
                    dto.setAssure(CommonFormat.boldMarkers(s11[i]));
                    dto.setClient(CommonFormat.boldMarkers(s12[i]));
                    dto.setRisque(CommonFormat.boldMarkers(s13[i]));
                    dto.setDateSaisie(CommonFormat.boldMarkers(s14[i]));
                    dto.setCdSmv(CommonFormat.boldMarkers(s15[i]));
                    dto.setRefProduit(CommonFormat.boldMarkers(s16[i]));
                    dto.setIdTi(Integer.valueOf(s17[i]));
                    dto.setCdFrc(CommonFormat.boldMarkers(s18[i]));
                    dto.setCdMdr(CommonFormat.boldMarkers(s19[i]));
                    resultatsRechercheContrat.add(dto);
                }
            }
            return resultatsRechercheContrat;

        } finally {
            try {
                if (callableStatementProc != null) {
                    callableStatementProc.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
    }

}

