package tech.nan.demo.auth.test;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import tech.nan.demo.auth.util.AesUtils;


class TestControllerTest {

    @Test
    void test() {
        String password = "Nan12091209";
        BCryptPasswordEncoder encoder1 = new BCryptPasswordEncoder();
        System.out.println("encoder1  : " + encoder1.encode(password));
        System.out.println(encoder1.matches("Nan12091209", "$2a$10$12zlnYZy2QMv29OBEOyowuO9F6ogNWvbGoghfIsSzA8NyXet3F3M."));
        System.out.println("encoder1-2: " + encoder1.encode(password));
        System.out.println(encoder1.matches("Nan12091209", "$2a$10$QR4SC20vGvXdYghCqilrSui.tiYag4Hli/jNw.6gNLN2uOgFqvurG"));
        BCryptPasswordEncoder encoder2 = new BCryptPasswordEncoder();
        System.out.println("encoder2  : " + encoder2.encode(password));
        System.out.println(encoder1.matches("Nan12091209", "$2a$10$bXjaNmSIQAFsTFqZWTi13.p/d/iBYvzFCLsox9y4YaUEEfqFo4fuG"));
    }

    @Test
    void test2() {
        String str = "dg8IVEOSN9VDWGF0U/vQXLEeIswD9DyZRcW5BT1lUxMvXh7PrT9racGphmkftK5Ln+RXdmn5vrZ/hrAn9NPB+hnuDDwb6o+JZULeOUQUiR84Y6HVxd3IVbBzei449dsPMp50v3p6n8Pl8LqQ9H5ior30D3ApI9+LzG3TtZyPRkQMOwQLEiaZN5GNZw/kvTnxoZcsn53OHvIDHs6gFLXAWj5Win44w6fO19lTr2GFTbUE2FGWFr/JG7i8/3OHSxtcYEPe0jgoJb8d/WMZWoR2Dc9I7Z4TdBP9t0Cn0thqDJezjwm95IzfwyIjqcbHFyMzwKlHEKVqNbfiC3Ryq/1BPKW8AW3n264CiA0Xge+d/6aOsqJbsjHZ0iyODkAC9I82Gg1rnPTBlRtTUD0Pci35LQvpwLy7VzrHEXB5xEevBgPJ5LpA69SVQGxK4J6t0nau3zZo22jLE61x/UA2nkFhvTT/QYLm3q6TAknMdwU4RwtB5PuFKA+aCNb1Cx+rrh925ZQ5+U428Z8mk9dyOapjb5fQur++hVMQguD6qXB6NqgcUzvSWfB7REM1gnsT7lk8zvhy+qsQeRsb/6h9C1RdJ95Zc4hiR8HoRb2Sf8oyN5I8vHmzQOHRsFZhbnKroU/rTcwPIJ8UG5CuPRoW4Myr9ccEnFnPSRiRfsa68puztYSxxTxOhXjU3eHlmmnzHy2qEFAa66I/DNtVo6B5EEcTNKU4Ky7nLUgt6gqgTW7tMTTNDuifwf7V3RspiNpGcBU6OxnQMfPSUg47AvVLpeXym95gOD+rnM44Z6j5CFyE/BsbZKFYWwnZUmlTzJXQJEpRYaUs0cm2OpapdSwuFPg8e7O0nyuEZBnTC529QQ0akw4xLwShFbFfUefTBuk71Bi/w5yn86r4+elfySObLQFqOM4eLm0Vz4FImS1ZRJnjV0o80cYCrfHPbA7i3kne8Z1Sjxw+xU7wHGshGErNPXKhwKxjnaYfsYOumMbnKaxiOR4=";
        System.out.println(AesUtils.decrypt(str));

    }

}