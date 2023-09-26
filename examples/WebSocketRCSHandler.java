/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yum.e3.client.development.apps.finance.it.ccot.websockets;


import com.google.gwt.core.client.JavaScriptObject;
/**
 *
 * @author BRG1103
 */
public class WebSocketRCSHandler {

    private JavaScriptObject webSocket;

    public WebSocketRCSHandler(String url) {
        initializeWebSocket(url);
    }

    private native void initializeWebSocket(String url) /*-{
        var _this = this;
        this.@yum.e3.client.development.apps.finance.it.ccot.websockets.WebSocketRCSHandler::webSocket = new WebSocket(url);
        
        this.@yum.e3.client.development.apps.finance.it.ccot.websockets.WebSocketRCSHandler::webSocket.onopen = function(event) {
            console.log("Conexión abierta");
            _this.@yum.e3.client.development.apps.finance.it.ccot.websockets.WebSocketRCSHandler::send(Ljava/lang/String;)("/name gwt");
        };
        
        this.@yum.e3.client.development.apps.finance.it.ccot.websockets.WebSocketRCSHandler::webSocket.onmessage = function(event) {
            var message = event.data;
            console.log("Mensaje recibido: " + message);
            _this.@yum.e3.client.development.apps.finance.it.ccot.websockets.WebSocketRCSHandler::onMessage(Ljava/lang/String;)(message);
        };
        
        this.@yum.e3.client.development.apps.finance.it.ccot.websockets.WebSocketRCSHandler::webSocket.onclose = function(event) {
            console.log("Conexión cerrada");
            // Manejar la conexión cerrada
        };
        
        this.@yum.e3.client.development.apps.finance.it.ccot.websockets.WebSocketRCSHandler::webSocket.onerror = function(event) {
            console.log("Error en la conexión");
            // Manejar errores
        };
    }-*/;

    public native void send(String message) /*-{
        this.@yum.e3.client.development.apps.finance.it.ccot.websockets.WebSocketRCSHandler::webSocket.send(message);
    }-*/;

    public void onMessage(String message) {
        // Manejar el mensaje recibido
    }
}