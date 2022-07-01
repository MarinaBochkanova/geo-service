package ru.netology.geo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class GeoServiceImplTest {

    @ParameterizedTest
    @MethodSource("methodSource")
    void testLocationByIp(String ip, Country expected) {
        GeoServiceImpl geoService = new GeoServiceImpl();
        Country actual = geoService.byIp(ip).getCountry();
        Assertions.assertEquals(expected, actual);
    }

    static Stream<Arguments> methodSource() {
        return Stream.of(
                Arguments.of("172.110.17.15", Country.RUSSIA),
                Arguments.of("172.123.11.09", Country.RUSSIA),
                Arguments.of("96.21.183.111", Country.USA),
                Arguments.of("96.11.154.135", Country.USA)
        );
    }

}