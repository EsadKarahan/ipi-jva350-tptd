package com.ipi.jva350.service;

import antlr.Lookahead;
import com.ipi.jva350.exception.SalarieException;
import com.ipi.jva350.model.Entreprise;
import com.ipi.jva350.model.SalarieAideADomicile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class SalarieAideADomicileServiceIntegrationTest {

    @Autowired
    private SalarieAideADomicileService salarieAideADomicileService;

    @Test
    void testClotureMois() throws SalarieException {
        // Given (aurait aussi pu être mis en méthode @BeforeEach pour toutes les autres méthodes de test)
        SalarieAideADomicile aide = new SalarieAideADomicile("Jeanne",
                LocalDate.of(2021, 7, 1), LocalDate.now(),
                0, 0, 9,
                1, 0);

        // When
        salarieAideADomicileService.clotureMois(aide, 20);
        // Then
        Assertions.assertEquals(20, aide.getJoursTravaillesAnneeN());
    }

    @ParameterizedTest(name = "La date {0} est un jour férié : {1}")
    @CsvSource({
            "'2012-04-09', true",
            "'2012-06-08', false",
            "'2020-08-15', true",
            "'2013-04-08', false",
            "'2012-01-01', true"
    })

    public final void estJourFerieTest(String date, boolean expectedJourFerierBool) {
        Entreprise entreprise = new Entreprise();

        LocalDate d = LocalDate.parse(date);

        boolean jourferier= Entreprise.estJourFerie(d);
        assertEquals(expectedJourFerierBool, jourferier);
    }

    @Test
    public final void calculeLimiteEntrepriseCongesPermisTest() {
        Entreprise entreprise = new Entreprise();
        SalarieAideADomicile aide = new SalarieAideADomicile("Jeanne",
                LocalDate.of(2021, 7, 1), LocalDate.now(),
                0, 0, 9,
                1, 0);

        double congesPayesAcquisAnneeNMoins1 = 2.1;
        LocalDate premierJourDeConge = LocalDate.of(2022 , 12, 1);
        LocalDate dernierJourDeConge = LocalDate.of(2022 , 12, 15);

        assertEquals(SalarieAideADomicileService.calculeLimiteEntrepriseCongesPermis(aide.getMoisEnCours(), congesPayesAcquisAnneeNMoins1, aide.getMoisDebutContrat(), premierJourDeConge, dernierJourDeConge), 2);
    }
}