package com.example.cpuboundapplication;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.DatatypeConverter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@RestController
@RequiredArgsConstructor
public class HashController {

    private final Environment env;

    @GetMapping("/hash/{input}")
    public String getDigest(@PathVariable("input") String input) throws NoSuchAlgorithmException {
        for (int i = 0; i < 100_000; i++) {
            input = getMD5Digest(input);
        }

        return input;
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    private String getMD5Digest(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(input.getBytes());
        byte[] digest = md.digest();

        return DatatypeConverter.printHexBinary(digest)
                .toUpperCase();
    }

    @GetMapping("/health_check")
    public String status() throws UnknownHostException {
//
        String ipAddress = InetAddress.getLocalHost().getHostAddress();
        return String.format(
                "This server is running on host : %s, port : %s, twosom property : name = %s, twosom message : message = %s",
                ipAddress,
                env.getProperty("local.server.port"),
                env.getProperty("twosom.property.name"),
                env.getProperty("twosom.property.message"));
    }
}
