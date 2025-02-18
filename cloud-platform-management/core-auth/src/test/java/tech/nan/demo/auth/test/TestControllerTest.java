package tech.nan.demo.auth.test;

import okhttp3.*;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import tech.nan.demo.auth.util.AesUtils;

import java.io.IOException;
import java.util.List;


class TestControllerTest {

    @Test
    void test() {
        String password = "Color123";
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
        String str = "dg8IVEOSN9VDWGF0U/vQXLEeIswD9DyZRcW5BT1lUxPQg92fplx9Mf7PG22AHH6+cDsr93TabNCY6GDTT1E2kVwP7lVCt4ePcfe8DDZiuk67exEGZS9X1XbuCqcbVy5X49XMSLmMCT9vDog2wB66ts58NBnnrF6pVfOzA08B/zp1+wmMWKzeXBQfJbnRGgOV/25WcnZsUPtpUPXae8sGydRzX3qcCZbfrmvEjIP7cZlAudOhiES3cocoX590dE5DguRpOyNdYrdwXXWaw6He4mcRB/CUc2n2rEksdJkD6IU9DYXVbwgnK0I1T4HNzVXmwfr8g28/MfEUkd4d9NNEutGQZznHcyGBYdEDAS9B1rmFr/ngLdExOxZrL1IPndmI3oOpUad1Piuo8Ao9/VZzAQHtFLyhJ/QQ2QCIMrcI5mVs27ZQxpKtfu++q8okaZmXQRTIo4C56lOIBFCnZiI0cEtkghSqWeGpML7jHiEsuVVEleQ7ipHxtbuxgUtYCWlLFx6uroYA+6QEG0Gg1DzCq746cAPnXTHT+S69jp7DE0du5lxfI7gvaDqyFu4p0nx5ZG4REXJoCB0Em+JAS1rAYGBD3tI4KCW/Hf1jGVqEdg2q2/3CxmvxmQDC/C0ETIL32m3lNPgJJ9dApR0Z2sLJkaJcB4NVlU8n/F5UL3JhMABz5VJ48i38uGig6bQoOQi6nLegOXNoGA2gzItqGlesZhZkUVTkVMUyO3yq6uYrS/vLTdAnr8Y211V3Uvd1r7/PRD+Z4CbO20RUSZFKXNiSV3xVMbGTqk0lMpgJHYZbtP3CT6jYNDK9nTTLpXvPnXgOpdd1jRBu5Ei31MMKgAu1bfJUf2YnahCgwr1EBm8jjBNQO6PlX+cnKUFnpdbbfcAlPH0CP8T2WCPgcn79Q9dc/VcfaPb59Xb5gxnudO91ClBf65sYsTbFL0F+oh2LvWZT70EK/nDgNWcmknyu2dA+dbx+culxfgrt/ezb9xiqNgwhG6csZxrD+AKaJJv//1XemhxvSx+jayYaBJws7SzQNFENz5ALaBF93WSZoDc0jd0PGKTbB2sDUC92CuGFlEH3vj+GIWTU2DyDVBWgyp9/eccpg3HxqyyhKpgtwaIqq1f/QnkWn7IJ1GMq7uTfiwER8qfG/l5at/Lx9PyFZKaAytHcFHr3o8r41ec/TuRgHzCvKXmKiqCQgP/PqyEVXoFkV92fFZzrs6tXecV5Sd7upA==";
        System.out.println(AesUtils.decrypt(str));

    }

    @Test
    void testOkHttp() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Response response = chain.proceed(chain.request());

                        List<String> cookies = response.headers("Set-Cookie");
                        for (String cookie : cookies) {
                            System.out.println("Received cookie: " + cookie);
                        }

                        return response;
                    }
                })
                .build();

        FormBody body = new FormBody.Builder()
                .add("log", "superroot")
                .add("pwd", "Color123")
                .add("lwa", "1")
                .add("login-with-ajax", "login")
                .build();

        Request request = new Request.Builder()
                .url("http://192.168.1.12/wp-login.php")
                .post(body)
                .build();

        // 执行请求
        try (Response response = client.newCall(request).execute()) {
            // 输出响应体
            if (response.isSuccessful()) {
                System.out.println("Response: " + response.body().string());
            } else {
                System.out.println("Request failed: " + response.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}