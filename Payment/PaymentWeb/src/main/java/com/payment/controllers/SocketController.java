package com.payment.controllers;

import javax.websocket.OnError;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class SocketController {

  private static final Logger log = LoggerFactory.getLogger(SocketController.class);

  @Autowired
  private SimpMessagingTemplate messagingTemplate;

  @MessageMapping(value = { "/openSocket" })
  public void initSocket() {
    log.info("Socket coneection initialized.......");
  }

  @OnError
  public void socketError(Throwable error) {
    log.error("Error message for socket: {}", error);
  }

}
