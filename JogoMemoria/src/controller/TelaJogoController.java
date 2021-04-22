/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.GradeCartas;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.Carta;

/**
 * FXML Controller class
 *
 * @author Otavio
 */
public class TelaJogoController implements Initializable {
    
    private GradeCartas grade;

    @FXML
    private Button btnIniciar;
    @FXML
    private ImageView im1;
    @FXML
    private ImageView im2;
    @FXML
    private ImageView im3;
    @FXML
    private ImageView im4;
    @FXML
    private ImageView im5;
    @FXML
    private ImageView im6;
    @FXML
    private ImageView im7;
    @FXML
    private ImageView im8;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.grade = new GradeCartas();
    }    

    @FXML
    private void btnIniciarClick(ActionEvent event) {
        this.grade.carregarGrade();
        
        Carta[][] grad = this.grade.getGrade();
        System.out.println("Aqui");
        
        
        // Testes
        Image image = null;
        try {
            image = new Image(new FileInputStream("D:\\Documentos\\Graduação\\Programação Orientada a Objetos\\TP2\\JogoMemoria\\src\\images\\imagem1.jpg"));
        } catch (FileNotFoundException ex) {
            System.out.println("ERRO!");
        }
        System.out.println(image.toString());
        //
        
        this.im1.setImage(grad[0][0].getImagem());
        this.im2.setImage(grad[0][1].getImagem());
        this.im3.setImage(grad[1][0].getImagem());
        this.im4.setImage(grad[1][1].getImagem());
        this.im5.setImage(grad[2][0].getImagem());
        this.im6.setImage(grad[2][1].getImagem());
        this.im7.setImage(grad[3][0].getImagem());
        this.im8.setImage(grad[3][1].getImagem());        
        
    }

    @FXML
    private void im1Clicked(MouseEvent event) {
    }

    @FXML
    private void im2Clicked(MouseEvent event) {
    }

    @FXML
    private void im3Clicked(MouseEvent event) {
    }

    @FXML
    private void im4Clicked(MouseEvent event) {
    }

    @FXML
    private void im5Clicked(MouseEvent event) {
    }

    @FXML
    private void im6Clicked(MouseEvent event) {
    }

    @FXML
    private void im7Clicked(MouseEvent event) {
    }

    @FXML
    private void im8Clicked(MouseEvent event) {
    }
    
}
