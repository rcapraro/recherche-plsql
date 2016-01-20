package com.capraro;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.HtmlRenderer;

import javax.servlet.annotation.WebServlet;
import java.util.List;

@Theme("valo")
public class RechercheUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        final RechercheContrat rechercheContrat = new RechercheContrat();
        final RechercheSinistre rechercheSinistre = new RechercheSinistre();

        final VerticalLayout verticalLayout = new VerticalLayout();

        final Label resultLabel = new Label();
        resultLabel.setVisible(false);

        final ListSelect databaseSelect = new ListSelect("Sélection de la BDD:");
        for (final Databases database : Databases.values()) {
            databaseSelect.addItem(database.getUrl());
            databaseSelect.setItemCaption(database.getUrl(), database.getAlias());
        }
        databaseSelect.setRows(1);
        databaseSelect.setImmediate(true);
        databaseSelect.setRequired(true);
        databaseSelect.setNullSelectionAllowed(false);
        databaseSelect.select(Databases.REPLICAT.getUrl());
        databaseSelect.setIcon(FontAwesome.DATABASE);

        final HorizontalLayout horizontalLayoutSearch = new HorizontalLayout();
        horizontalLayoutSearch.setMargin(false);
        horizontalLayoutSearch.setSpacing(true);

        final TextField critereTextfield = new TextField();
        critereTextfield.setCaption("Critère de Recherche:");
        critereTextfield.setWidth("500px");
        critereTextfield.setIcon(FontAwesome.SEARCH);

        final TextField numIntTextField = new TextField();
        numIntTextField.setCaption("Courtier (NUM_INT):");
        numIntTextField.setValue("27501");
        numIntTextField.setWidth("100");
        numIntTextField.setRequired(true);
        numIntTextField.setIcon(FontAwesome.USER);

        horizontalLayoutSearch.addComponents(critereTextfield, numIntTextField);

        final HorizontalLayout horizontalLayoutButtons = new HorizontalLayout();
        horizontalLayoutButtons.setMargin(false);
        horizontalLayoutButtons.setSpacing(true);

        Grid gridContrat = new Grid();
        gridContrat.setVisible(false);
        gridContrat.setContainerDataSource(new BeanItemContainer<>(ResultatRechercheContrat.class));
        gridContrat.setColumnOrder("contrat", "refProduit", "assure", "client", "risque", "idCt", "idTi");
        gridContrat.setColumnReorderingAllowed(true);
        gridContrat.setHeightMode(HeightMode.ROW);
        gridContrat.setHeightByRows(14);
        gridContrat.getColumns().stream().forEach(c -> c.setRenderer(new HtmlRenderer()));

        Grid gridSinistre = new Grid();
        gridSinistre.setVisible(false);
        gridSinistre.setContainerDataSource(new BeanItemContainer<>(ResultatRechercheSinistre.class));
        gridSinistre.setColumnOrder("contrat", "assure", "risque", "tiersAdverse", "idSi");
        gridSinistre.setColumnReorderingAllowed(true);
        gridSinistre.setHeightMode(HeightMode.ROW);
        gridSinistre.setHeightByRows(14);
        gridSinistre.getColumns().stream().forEach(c -> c.setRenderer(new HtmlRenderer()));

        Button buttonSearchContrat = new Button("Recherche Contrat");
        buttonSearchContrat.addClickListener(evt -> {
            try {
                List<ResultatRechercheContrat> resultatsRechercheContrats = rechercheContrat.rechercheContrat(critereTextfield.getValue(),
                                                                                                              numIntTextField.getValue(),
                                                                                                              String.valueOf(databaseSelect.getValue()));
                gridContrat.setContainerDataSource(new BeanItemContainer<>(ResultatRechercheContrat.class, resultatsRechercheContrats));
                gridSinistre.setVisible(false);
                gridContrat.setVisible(true);
                resultLabel.setVisible(true);
                resultLabel.setCaption("<b>Nombre de résultats retournés:</b> " + String.valueOf(resultatsRechercheContrats.size()));
                resultLabel.setValue("(Maximum autorisé: 1000)");
                resultLabel.setCaptionAsHtml(true);
                resultLabel.setIcon(FontAwesome.COGS);
            } catch (Exception e) {
                handleError(resultLabel, e);
            }
        });

        Button buttonSearchSinistre = new Button("Recherche Sinistre");
        buttonSearchSinistre.addClickListener(evt -> {
            try {
                List<ResultatRechercheSinistre> resultatRechercheSinistres = rechercheSinistre.rechercheSinistre(critereTextfield.getValue(),
                                                                                                              numIntTextField.getValue(),
                                                                                                              String.valueOf(databaseSelect.getValue()));
                gridSinistre.setContainerDataSource(new BeanItemContainer<>(ResultatRechercheSinistre.class, resultatRechercheSinistres));
                gridContrat.setVisible(false);
                gridSinistre.setVisible(true);
                resultLabel.setVisible(true);
                resultLabel.setCaption("<b>Nombre de résultats retournés:</b> " + String.valueOf(resultatRechercheSinistres.size()));
                resultLabel.setValue("(Maximum autorisé: 1000)");
                resultLabel.setCaptionAsHtml(true);
                resultLabel.setIcon(FontAwesome.COGS);
            } catch (Exception e) {
                handleError(resultLabel, e);
            }
        });

        horizontalLayoutButtons.addComponents(buttonSearchContrat, buttonSearchSinistre);

        verticalLayout.addComponents(databaseSelect, horizontalLayoutSearch, horizontalLayoutButtons, gridContrat, gridSinistre, resultLabel);
        verticalLayout.setMargin(true);
        verticalLayout.setSpacing(true);
        verticalLayout.setExpandRatio(gridContrat, 1);
        gridContrat.setSizeFull();
        verticalLayout.setExpandRatio(gridSinistre, 1);
        gridSinistre.setSizeFull();
        setContent(verticalLayout);
    }

    private void handleError(final Label resultLabel, final Exception e) {
        e.printStackTrace();
        resultLabel.setVisible(true);
        resultLabel.setValue(e.getMessage());
        resultLabel.setCaption("<b>La recherche a échoué pour la raison suivante:</b><br/>");
        resultLabel.setCaptionAsHtml(true);
        resultLabel.setIcon(FontAwesome.WARNING);
    }

    @WebServlet(urlPatterns = "/*", name = "RechercheUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = RechercheUI.class, productionMode = true)
    public static class MyUIServlet extends VaadinServlet {
    }
}
