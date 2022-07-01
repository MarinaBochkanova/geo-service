package ru.netology.i18n;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.netology.entity.Country;

import static org.junit.jupiter.api.Assertions.*;

class LocalizationServiceImplTest {

   
    @Test
    void testLocale() {
        LocalizationServiceImpl localizationService = new LocalizationServiceImpl();
        Country country = Country.RUSSIA;
        String expected = "Добро пожаловать";
        String actual = localizationService.locale(country);
        Assertions.assertEquals(expected, actual);
    }
}