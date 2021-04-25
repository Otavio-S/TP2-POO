/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.GradeCartas;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    Image imagem;
    
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
        
        File file = new File("D:\\Documentos\\Graduação\\Programação Orientada a Objetos\\TP2\\TP2-POO\\JogoMemoria\\src\\images\\back.jpg");
        imagem = new Image(file.toURI().toString());
        
        this.im1.setImage(imagem);
        this.im2.setImage(imagem);
        this.im3.setImage(imagem);
        this.im4.setImage(imagem);
        this.im5.setImage(imagem);
        this.im6.setImage(imagem);
        this.im7.setImage(imagem);
        this.im8.setImage(imagem);
    }

    @FXML
    private void im1Clicked(MouseEvent event) {
        this.im1.setImage(this.grade.getGrade()[0][0].getImagem());
        this.grade.getGrade()[0][0].alterarStatus();
    }

    @FXML
    private void im2Clicked(MouseEvent event) {
        this.im2.setImage(this.grade.getGrade()[1][0].getImagem());
        this.grade.getGrade()[1][0].alterarStatus();
    }

    @FXML
    private void im3Clicked(MouseEvent event) {
        this.im3.setImage(this.grade.getGrade()[2][0].getImagem());
        this.grade.getGrade()[2][0].alterarStatus();
    }

    @FXML
    private void im4Clicked(MouseEvent event) {
        this.im4.setImage(this.grade.getGrade()[3][0].getImagem());
        this.grade.getGrade()[3][0].alterarStatus();
    }

    @FXML
    private void im5Clicked(MouseEvent event) {
        this.im5.setImage(this.grade.getGrade()[0][1].getImagem());
        this.grade.getGrade()[0][1].alterarStatus();
    }

    @FXML
    private void im6Clicked(MouseEvent event) {
        this.im6.setImage(this.grade.getGrade()[1][1].getImagem());
        this.grade.getGrade()[1][1].alterarStatus();
    }

    @FXML
    private void im7Clicked(MouseEvent event) {
        this.im7.setImage(this.grade.getGrade()[2][1].getImagem());
        this.grade.getGrade()[2][1].alterarStatus();
    }

    @FXML
    private void im8Clicked(MouseEvent event) {
        this.im8.setImage(this.grade.getGrade()[3][1].getImagem());
        this.grade.getGrade()[3][1].alterarStatus();
    }
    
}
