package com.ssafy.dockchodogam.service.oauth2;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class PasswordMaker {

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public String make(){
        StringBuffer pw = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 8; i++) { // 인증코드 8자리
            int index = rnd.nextInt(3); // 0~2 까지 랜덤

            switch (index) {
                case 0:
                    pw.append((char) ((int) (rnd.nextInt(26)) + 97));
                    //  a~z  (ex. 1+97=98 => (char)98 = 'b')
                    break;
                case 1:
                    pw.append((char) ((int) (rnd.nextInt(26)) + 65));
                    //  A~Z
                    break;
                case 2:
                    pw.append((rnd.nextInt(10)));
                    // 0~9
                    break;
            }
        }

        return encoder.encode(pw.toString());
    }
}
