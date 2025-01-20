package com.StripeProject.payments_processing_service_start.controller;

import com.StripeProject.payments_processing_service_start.constants.EndPoints;
import com.StripeProject.payments_processing_service_start.pojo.stripe.StripeEvent;
import com.StripeProject.payments_processing_service_start.service.interfaces.StripeService;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.net.Webhook;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping(EndPoints.STRIPE)
public class StripeNotificationController {

    @Autowired
    private Gson gson;

    @Autowired
    private StripeService stripeService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Value("${stripe.notification.signing-secret}")
    private String endpointSecret;

    @PostMapping(EndPoints.NOTIFICATION)
    public ResponseEntity<String> processNotification() {
        System.out.println("StripeNotificationController.processNotification || httpServletRequest: " + httpServletRequest);
        String reqAsString;
        try {
            reqAsString = StreamUtils.copyToString(httpServletRequest.getInputStream(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        System.out.println("json = " + reqAsString);
        String sigHeader = httpServletRequest.getHeader("Stripe-Signature");
        System.out.println("sigHeader = " + sigHeader);

        try {
            Webhook.constructEvent(reqAsString, sigHeader, endpointSecret);
            System.out.println("HMACSHA256 SIGNATURE VERIFIED");
        } catch (JsonSyntaxException e) {
            // Invalid payload
            System.out.println("JsonSyntaxException e = " + e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (SignatureVerificationException e) {
            // Invalid signature
            System.out.println(" SignatureVerificationException e = " + e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        System.out.println("Signature VALID. Continue further processing of event");

        try{
            StripeEvent event = gson.fromJson(reqAsString, StripeEvent.class);
            System.out.println("event = " + event);
            stripeService.processStripeEvent(event);
            return ResponseEntity.ok().build();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }

    }
}
