package org.sangraama.login.controller;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.WsOutbound;
import org.sangraama.login.database.cassandra.dao.ClientEvent;
import org.sangraama.login.database.cassandra.dao.User;
import org.sangraama.login.database.cassandra.dao.UserImpl;
import org.sangraama.login.database.cassandra.dao.UserRepository;
import org.sangraama.login.database.cassandra.dao.UserRepositoryImpl;

import com.google.gson.Gson;

public class WebSocketConnection extends MessageInbound {
    // Local Debug or logs
    private static final String TAG = "Login-Server WebSocketConnection : ";

    private Gson gson;
    private UserRepository userRepository;

    public WebSocketConnection() {
        this.gson = new Gson();
        this.userRepository = new UserRepositoryImpl();
    }

    @Override
    protected void onOpen(WsOutbound outbound) {
        // log.info("Open Connection");
        System.out.println(TAG + " Open Connection");
    }

    @Override
    protected void onClose(int status) {

        System.out.println(TAG + " Close connection");
    }

    @Override
    protected void onBinaryMessage(ByteBuffer byteBuffer) throws IOException {
        // log.warn("binary messages are not supported");
        System.out.println("Binary");
        throw new UnsupportedOperationException("not supported binary messages");
    }

    @Override
    protected void onTextMessage(CharBuffer charBuffer) throws IOException {
        String loginDetail = charBuffer.toString();
        ClientEvent event = gson.fromJson(loginDetail, ClientEvent.class);
        switch (event.getType()) {
            case 1:
                System.out.println(TAG + "User Login request");
                User user;
                if (userRepository.isUserExists(event.getUsername(), event.getPassword())) {
                    user = userRepository.getUserByUserNameAndPassWord(event.getUsername(),
                            event.getPassword());
                    System.out.println(TAG + "User Already exist");
                } else {
                    user = createNewUser(event.getUsername(), event.getPassword());
                    System.out.println(TAG + "User does not exist, create new user");
                }
                sendUserToClient(user);
                break;
            default:
                break;

        }
    }

    private void sendUserToClient(User user) throws IOException {
        String convertedString = gson.toJson(user);
        getWsOutbound().writeTextMessage(CharBuffer.wrap(convertedString));
        System.out.println(TAG + "Sent user data to client : "+convertedString);

    }

    private User createNewUser(String userName, String password) {
        UserImpl userImpl = new UserImpl();
        userImpl.setType(1);
        userImpl.setUserId((int) (Math.random() * 100000));
        userImpl.setUserName(userName);
        userImpl.setPassword(password);
        userImpl.setAngle(0);
        userImpl.setScore(0);
        userImpl.setHealth(100);
        userImpl.setX(2800);
        userImpl.setY(500);
        userImpl.setShipType(1);
        userImpl.setBulletType(1);

        userRepository.create(userImpl);

        return userImpl;

    }
}
