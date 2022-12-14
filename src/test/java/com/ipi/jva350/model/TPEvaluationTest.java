//Pair programming Esad Karahan et Damien de Pindray d'Ambelle
package com.ipi.jva350.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static com.ipi.jva350.model.Entreprise.*;

public class TPEvaluationTest {

    @Test
    void getPremierJourAnneeDeCongesSup5Limit(){
        // Given
        LocalDate now = LocalDate.of(2022, 6, 30);

        //WHEN
        LocalDate res = getPremierJourAnneeDeConges(now);

        //THEN
        Assertions.assertEquals(LocalDate.of(2022, 06, 01), res);
    }

    @Test
    void getPremierJourAnneeDeCongesEstNull(){
        // Given
        LocalDate now = null;

        //WHEN
        LocalDate res = getPremierJourAnneeDeConges(now);

        //THEN
        Assertions.assertEquals(null, res);
    }

    @Test
    void getPremierJourAnneeDeCongesSup5(){
        // Given
        LocalDate now = LocalDate.of(2022, 6, 15);

        //WHEN
        LocalDate res = getPremierJourAnneeDeConges(now);

        //THEN
        Assertions.assertEquals(LocalDate.of(2022, 06, 01), res);
    }

    @Test
    void getPremierJourAnneeDeCongesInf5(){
        // Given
        LocalDate now = LocalDate.of(2022, 3, 15);

        //WHEN
        LocalDate res = getPremierJourAnneeDeConges(now);

        //THEN
        Assertions.assertEquals(LocalDate.of(2021, 06, 01), res);
    }

    @Test
    void getPremierJourAnneeDeCongesMountLess5LimitInf(){
        // Given
        LocalDate now = LocalDate.of(2022, 4, 30);

        //WHEN
        LocalDate res = getPremierJourAnneeDeConges(now);

        //THEN
        Assertions.assertEquals(LocalDate.of(2021, 06, 01), res);
    }

    @Test
    void estDansPlageFalseBefore(){
        // Given
        LocalDate now = LocalDate.of(2022, 3, 10);
        LocalDate dateDebut = LocalDate.of(2022, 3, 15);
        LocalDate dateFin = LocalDate.of(2022, 3, 17);

        //WHEN
        boolean res = estDansPlage(now, dateDebut, dateFin);

        //THEN
        Assertions.assertEquals(false, res);
    }

    @Test
    void estDansPlageFalseAfter(){
        // Given
        LocalDate now = LocalDate.of(2022, 3, 23);
        LocalDate dateDebut = LocalDate.of(2022, 3, 15);
        LocalDate dateFin = LocalDate.of(2022, 3, 17);

        //WHEN
        boolean res = estDansPlage(now, dateDebut, dateFin);

        //THEN
        Assertions.assertEquals(false, res);
    }

    @Test
    void estDansPlageTrue(){
        // Given
        LocalDate now = LocalDate.of(2022, 3, 16);
        LocalDate dateDebut = LocalDate.of(2022, 3, 15);
        LocalDate dateFin = LocalDate.of(2022, 3, 17);

        //WHEN
        boolean res = estDansPlage(now, dateDebut, dateFin);

        //THEN
        Assertions.assertEquals(true, res);
    }

    @Test
    void estDansPlageLimiteDebut(){
        // Given
        LocalDate now = LocalDate.of(2022, 3, 15);
        LocalDate dateDebut = LocalDate.of(2022, 3, 15);
        LocalDate dateFin = LocalDate.of(2022, 3, 17);

        //WHEN
        boolean res = estDansPlage(now, dateDebut, dateFin);

        //THEN
        Assertions.assertEquals(true, res);
    }

    @Test
    void estDansPlageLimiteFin(){
        // Given
        LocalDate now = LocalDate.of(2022, 3, 17);
        LocalDate dateDebut = LocalDate.of(2022, 3, 15);
        LocalDate dateFin = LocalDate.of(2022, 3, 17);

        //WHEN
        boolean res = estDansPlage(now, dateDebut, dateFin);

        //THEN
        Assertions.assertEquals(true, res);
    }

    // Test paramétré
    @ParameterizedTest(name = "{0} est ferie")
    @CsvSource({
            "'2022-12-25'",
            "'2022-07-14'",
            "'2022-01-01'"
    })
    void estJourFerieTrue(String debut){
        //Given
        LocalDate date = LocalDate.parse(debut);

        //When
        boolean res = estJourFerie(date);

        //Then
        Assertions.assertEquals(true, res);
    }

    // Test paramétré
    @ParameterizedTest(name = "{0} est pas ferie")
    @CsvSource({
            "'2022-12-24'",
            "'2022-12-26'",
            "'2022-07-13'",
            "'2022-07-15'",
            "'2022-12-31'",
            "'2022-01-02'",
            "'2022-04-21'"
    })
    void estJourFerieFalse(String debut){
        //Given
        LocalDate date = LocalDate.parse(debut);

        //When
        boolean res = estJourFerie(date);

        //Then
        Assertions.assertEquals(false, res);
    }
}
