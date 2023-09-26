package com.mycompany.appwebtest01;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.CloseReason;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/chat")
public class WebSocketServer extends Endpoint {

    private static ObjectMapper objectMapper = new ObjectMapper();
    private static Map<String, Sesion> userSessions = new HashMap<>();

    @Override
    public void onOpen(Session session, EndpointConfig config) {

        session.addMessageHandler(new MessageHandler.Whole<String>() {
            @Override
            public void onMessage(String jsonMessage) {
                System.out.println("Message : " + jsonMessage);
                SendMessage sendMessage = new SendMessage();
                String from;
                String to;
                String message;
                if (jsonMessage.startsWith("/name ")) {
                    String newName = jsonMessage.substring(6); // Elimina "/name " del mensaje.
                    userSessions.put(session.getId(), new Sesion(newName, session));
                    from = "serverWS";
                    to = session.getId();
                    message = "Bienvenido, " + userSessions.get(session.getId()).getUserName() + "!";
                    sendMessage = new SendMessage(from, to, message);
                } else {
                    try {
                        JsonNode jsonNode = objectMapper.readTree(jsonMessage);
                        
                        from = jsonNode.get("from").asText();
                        to = jsonNode.get("to").asText();
                        message = jsonNode.get("message").asText();

                        sendMessage = new SendMessage(from, to, message);

                    } catch (JsonProcessingException ex) {
                        Logger.getLogger(WebSocketServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                for (Sesion sesions : userSessions.values()) {
                    try {
                        ObjectNode nodeRoot = objectMapper.createObjectNode();
                        nodeRoot.put("idsession", sesions.getSession().getId());
                        nodeRoot.put("username", sesions.getUserName());

                        ObjectNode nodeSendMessage = objectMapper.createObjectNode();
                        nodeSendMessage.put("from", sendMessage.getFromData());
                        nodeSendMessage.put("to", sendMessage.getToData());
                        nodeSendMessage.put("message", sendMessage.getMessageData());

                        nodeRoot.set("sendmessage", nodeSendMessage);

                        ArrayNode nodeLiveUsers = objectMapper.createArrayNode();
                        for (Map.Entry<String, Sesion> entry : userSessions.entrySet()) {
                            ObjectNode user = objectMapper.createObjectNode();
                            user.put("idsession", entry.getKey());
                            user.put("username", entry.getValue().getUserName());
                            nodeLiveUsers.add(user);
                        }

                        nodeRoot.set("liveusers", nodeLiveUsers);
                        String jsonString = nodeRoot.toString();
                        
                        sesions.getSession().getBasicRemote().sendText(jsonString);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        // Asigna un ID único al usuario y almacénalo en el mapa de sesiones.
        // Envía un mensaje de bienvenida al usuario recién conectado.        
    }

    @Override
    public void onClose(Session session, CloseReason closeReason) {
        // Cuando un usuario se desconecta, elimina su sesión del mapa de sesiones.
        ObjectNode nodeRoot = objectMapper.createObjectNode();
        nodeRoot.put("idsession", session.getId());
        nodeRoot.put("username", userSessions.get(session.getId()).getUserName());

        ObjectNode nodeSendMessage = objectMapper.createObjectNode();
        nodeSendMessage.put("from", "");
        nodeSendMessage.put("to", "");
        nodeSendMessage.put("message", "");

        nodeRoot.set("sendmessage", nodeSendMessage);

        userSessions.remove(session.getId());

        ArrayNode nodeLiveUsers = objectMapper.createArrayNode();
        for (Map.Entry<String, Sesion> entry : userSessions.entrySet()) {
            ObjectNode user = objectMapper.createObjectNode();
            user.put("idsession", entry.getKey());
            user.put("username", entry.getValue().getUserName());
            nodeLiveUsers.add(user);
        }

        nodeRoot.set("liveusers", nodeLiveUsers);
        String jsonString = nodeRoot.toString();

        for (Sesion sesion : userSessions.values()) {
            try {
                sesion.getSession().getBasicRemote().sendText(jsonString);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
