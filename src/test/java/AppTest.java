import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;
import ru.netology.sender.MessageSenderImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class AppTest {

    @ParameterizedTest
    @ValueSource(strings = {"172.123.10.10", "172.123.11.09", "172.120.12.15", "172.110.17.16"})
    void test_send_Message_languageRussian(String ip) {
        GeoService geoService = Mockito.mock(GeoServiceImpl.class);
        Mockito.when(geoService.byIp(Mockito.<String>any()))
                .thenReturn(new Location("Moscow", Country.RUSSIA, "Lenina", 15));

        LocalizationService localizationService = Mockito.mock(LocalizationServiceImpl.class);
        Mockito.when(localizationService.locale(Country.RUSSIA))
                .thenReturn("Добро пожаловать");

        MessageSenderImpl messageSender = new MessageSenderImpl(geoService, localizationService);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);
        String message = messageSender.send(headers);
        String expected = "Добро пожаловать";
        Assertions.assertEquals(expected, message);
    }

    @ParameterizedTest
    @ValueSource(strings = {"96.21.183.111", "96.44.105.149", "96.14.123.197", "96.10.185.163"})
    void test_send_Message_languageEnglish(String ip) {
        GeoService geoService = Mockito.mock(GeoServiceImpl.class);
        Mockito.when(geoService.byIp(Mockito.<String>any()))
                .thenReturn(new Location("New York", Country.USA, " 10th Avenue", 320));

        LocalizationService localizationService = Mockito.mock(LocalizationServiceImpl.class);
        Mockito.when(localizationService.locale(Country.USA))
                .thenReturn("Welcome");

        MessageSenderImpl messageSender = new MessageSenderImpl(geoService, localizationService);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);
        String message = messageSender.send(headers);
        String expected = "Welcome";
        Assertions.assertEquals(expected, message);
    }

    @ParameterizedTest
    @MethodSource("methodSource")
    void test_Location_byIp(String ip, Country expected) {
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


    @Test
    void test_locale() {
        LocalizationServiceImpl localizationService = new LocalizationServiceImpl();
        Country country = Country.RUSSIA;
        String expected = "Добро пожаловать";
        String actual = localizationService.locale(country);
        Assertions.assertEquals(expected, actual);
    }


}
