package com.cliff777.chat.obj;

import com.cliff777.chat.ChatMain;
import com.cliff777.chat.listener.KeyEventHandler;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cliff on 12/21/2015.
 */
public class ChatWindow extends Application
{
    private static TextArea chatHistory;
    private static TextField messageBox;

    private static String username;

    final static int WIDTH = 800;
    final static int HEIGHT = 600;

    //list of messages to add to the chat history
    //usually added very quickly, list is to prevent
    //possibility of a miss
    private static List<String> messagesToAdd = new ArrayList<String>();

    public ChatWindow(){}

    public static void construct(String user)
    {
        username = user;
        //cannot call launch without first finishing the constructor in this class
        launch();
    }


    //build the window
    @Override
    public void start(Stage stage) throws Exception
    {
        chatHistory = new TextArea();
        messageBox = new TextField();

        chatHistory.setEditable(false);

        BorderPane pane = new BorderPane();

        pane.setCenter(chatHistory);
        pane.setBottom(messageBox);

        Scene scene = new Scene(pane, 800, 600);

        stage.setTitle("'" + username + "' on cChat");
        stage.setScene(scene);
        stage.show();

        messageBox.requestFocus();
        messageBox.setOnAction(new KeyEventHandler());

        //configure shutdown
        stage.setOnCloseRequest(new EventHandler<WindowEvent>()
        {
            public void handle(WindowEvent arg0) {
                //shutdown connection
                ChatMain.getConnection().terminateConnection();
                System.exit(0);
            }
        });
    }

    public static String getMessageBoxContent()
    {
        try
        {
            return messageBox.getText();
        }
        catch(Exception e)
        {
            return "";
        }
    }

    public static void clearMessageBox()
    {
        messageBox.clear();
    }

    public static void addMessageToWindow(String msg)
    {
        //add to temporary list
        messagesToAdd.add(msg);

        Platform.runLater(new Runnable()
        {
            public void run()
            {
                chatHistory.appendText(messagesToAdd.get(0) + '\n');
                messagesToAdd.remove(0);

                //keep scroll at the bottom
                chatHistory.setScrollTop(Double.MAX_VALUE);
            }
        });
    }
}