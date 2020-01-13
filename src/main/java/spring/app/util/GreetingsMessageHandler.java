//package spring.app.util;
//
//import groovy.util.logging.Slf4j;
//import org.springframework.web.socket.CloseStatus;
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketSession;
//import org.springframework.web.socket.handler.TextWebSocketHandler;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.concurrent.CopyOnWriteArrayList;
//
//@Slf4j
//public class GreetingsMessageHandler extends TextWebSocketHandler {
//
//    private List<WebSocketSession> webSocketSessions = new CopyOnWriteArrayList<>();
//
//    @Override
//    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//        webSocketSessions.add(session);
//    }
//
//    @Override
//    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        for (WebSocketSession webSocketSession : webSocketSessions) {
//            sendMessageToClient(message, webSocketSession);
//        }
//    }
//
//    @Override
//    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//        webSocketSessions.remove(session);
//    }
//
//    private void sendMessageToClient(TextMessage message, WebSocketSession webSocketSession) {
//        try {
//            webSocketSession.sendNotification(new TextMessage(message.getPayload()));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//}
