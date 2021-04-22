/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Otavio
 */
public class JogoMemoria extends Application {
    
    private static Stage stage;
    private static Scene cenaPrincipal;
    
    @Override
    public void start(Stage primaryStage) throws IOException {        
        stage = primaryStage;
        primaryStage.setTitle("Jogo da Memória");
        
        Parent raiz;
        raiz = FXMLLoader.load(getClass().getResource("/view/TelaJogo.fxml"));
        cenaPrincipal = new Scene(raiz);
        
        primaryStage.setScene(cenaPrincipal);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
