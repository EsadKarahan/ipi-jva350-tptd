package com.ipi.jva350.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;
import java.util.LinkedHashSet;

public class SalarieAideADomicileTest {

    @Test
    void aLegalementDroitADesCongesPayesNonInitialise() {
        // Given :
        SalarieAideADomicile aide = new SalarieAideADomicile();
        // When :
        boolean res = aide.aLegalementDroitADesCongesPayes();
        // Then :
        Assertions.assertEquals(false, res);
    }

    @Test
    void aLegalementDroitADesCongesPayesNouvelEmploye() {
        // Given :
        SalarieAideADomicile aide = new SalarieAideADomicile("Nouveau",
                LocalDate.now(), LocalDate.now(), 0, 0, 0,
                0, 0);
        // When :
        boolean res = aide.aLegalementDroitADesCongesPayes();
        // Then :
        Assertions.assertEquals(false, res);
    }

    @Test
    void aLegalementDroitADesCongesPayesNettementMoinsDe10() {
        // Given :
        //LocalDate dummyDate = LocalDate.now();
        SalarieAideADomicile aide = new SalarieAideADomicile("Jeanne",
                LocalDate.of(2021, 7, 1), LocalDate.now(),
                0, 0, 5,
                1, 0);
        // When :
        boolean res = aide.aLegalementDroitADesCongesPayes();
        // Then :
        Assertions.assertEquals(false, res);
    }

    @Test
    void aLegalementDroitADesCongesPayesNettementPlusDe10() {
        // Given :
        //LocalDate dummyDate = LocalDate.now();
        SalarieAideADomicile aide = new SalarieAideADomicile("Jeanne",
                LocalDate.of(2021, 7, 1), LocalDate.now(),
                0, 0, 15,
                1, 0);
        // When :
        boolean res = aide.aLegalementDroitADesCongesPayes();
        // Then :
        Assertions.assertEquals(true, res);
    }

    @Test
    void aLegalementDroitADesCongesPayesAuxLimites10() {
        // Given :
        //LocalDate dummyDate = LocalDate.now();
        SalarieAideADomicile aide = new SalarieAideADomicile("Jeanne",
                LocalDate.of(2021, 7, 1), LocalDate.now(),
                0, 0, 10,
                1, 0);
        // When :
        boolean res = aide.aLegalementDroitADesCongesPayes();
        // Then :
        Assertions.assertEquals(true, res);
    }

    @Test
    void aLegalementDroitADesCongesPayesAuxLimites9() {
        // Given :
        //LocalDate dummyDate = LocalDate.now();
        SalarieAideADomicile aide = new SalarieAideADomicile("Jeanne",
                LocalDate.of(2021, 7, 1), LocalDate.now(),
                0, 0, 9,
                1, 0);
        // When :
        boolean res = aide.aLegalementDroitADesCongesPayes();
        // Then :
        Assertions.assertEquals(false, res);
    }

    @Test
    void aLegalementDroitADesCongesTousLesCasAuxLimites() {
        // Given :
        //LocalDate dummyDate = LocalDate.now();
        SalarieAideADomicile aide = new SalarieAideADomicile("Jeanne",
                LocalDate.of(2021, 7, 1), LocalDate.now(),
                0, 0, 9,
                1, 0);
        // When :
        boolean res = aide.aLegalementDroitADesCongesPayes();
        // Then :
        Assertions.assertEquals(false, res);

        aide.setJoursTravaillesAnneeNMoins1(10);
        // When, Then :
        Assertions.assertEquals(true, aide.aLegalementDroitADesCongesPayes());
    }


    @Test
    void testCalculeJoursDeCongeDecomptesPourPlage() {
        // When :
        SalarieAideADomicile aide = new SalarieAideADomicile("Jeanne",
                LocalDate.of(2021, 7, 1), LocalDate.now(),
                0, 0, 9,
                1, 0);

        // When :
        LinkedHashSet<LocalDate> res = aide.calculeJoursDeCongeDecomptesPourPlage(
                LocalDate.of(2022, 7, 1), LocalDate.of(2022, 7, 2));

        // Then :
        LinkedHashSet<LocalDate> expected = new  LinkedHashSet<>();
        expected.add(LocalDate.of(2022, 7, 1));
        expected.add(LocalDate.parse("2022-07-02"));
        Assertions.assertEquals(expected, res);
    }


    @ParameterizedTest(name = "Entre {0} et {1}, nombre de JoursDeCongeDecomptes devrait être {2}")
    @CsvSource({
            "'2022-07-01', '2022-07-02', 2",
            "'2022-07-01', '2022-07-03', 2",
            "'2022-07-02', '2022-07-04', 1",
            "'2022-07-02', '2022-07-02', 0"
    })
    void testCalculeJoursDeCongeDecomptesPourPlageParametrized(String debut, String fin, double expectedNbJoursDeCongeDecomptes) {
        // Given :
        SalarieAideADomicile aide = new SalarieAideADomicile("Jeanne",
                LocalDate.of(2021, 7, 1), LocalDate.now(),
                0, 0, 10,
                1, 0);

        // When :
        LinkedHashSet<LocalDate> res = aide.calculeJoursDeCongeDecomptesPourPlage(
                LocalDate.parse(debut), LocalDate.parse(fin));

        // Then :
        Assertions.assertEquals(expectedNbJoursDeCongeDecomptes, res.size());
    }

}
