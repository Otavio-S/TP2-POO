/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.GradeCartas;
import java.io.File;
import static java.lang.Math.random;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    int pontosComp, pontosUser, quantidadeVirada, paresEncontrados;
    
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
    @FXML
    private Label lblJogadorAtual;
    @FXML
    private Label lblPontosJogador;
    @FXML
    private Label lblPontosComputador;
    @FXML
    private Label lblJogador;
    @FXML
    private Label lblComp;
    @FXML
    private Label lblQuem;

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
        this.lblComp.setVisible(true);
        this.lblJogador.setVisible(true);
        this.lblQuem.setVisible(true);
        this.inicializarCampos();
    }
    
    private void inicializarCampos() {
        this.grade.carregarGrade();
        this.pontosComp = 0;
        this.quantidadeVirada = 0;
        this.lblPontosComputador.setText(String.valueOf(this.pontosComp));
        this.pontosUser = 0;
        this.lblPontosJogador.setText(String.valueOf(this.pontosUser));
        this.paresEncontrados = 0;
        this.lblJogadorAtual.setText("Usuário");
        
        if(this.btnIniciar.getText().equals("Iniciar")) this.btnIniciar.setText("Reiniciar");
        
        Carta[][] grad = this.grade.getGrade();
        
        File file = new File("src/images/back.jpg");
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
    
    private void finalizar() {
        String header, text;

        if(this.pontosComp > this.pontosUser) {
            header = "Computador Venceu!";
            text = "Computador "+this.pontosComp+" X "+this.pontosUser+" Usuário";
        } else if(this.pontosComp < this.pontosUser) {
            header = "Usuário Venceu!";
            text = "Usuário "+this.pontosUser+" X "+this.pontosComp+" Computador";
        } else {
            header = "Empate!";
            text = "Usuário "+this.pontosUser+" X "+this.pontosComp+" Computador";
        }
        
        Alert errorAlert = new Alert(Alert.AlertType.WARNING);
            errorAlert.setTitle("Jogo finalizado!");
            errorAlert.setHeaderText(header);
            errorAlert.setContentText(text);
            errorAlert.showAndWait();
        this.inicializarCampos();
    }
    
    private final Runnable t1 = () -> {
        try{
            jogadaComputador();
        } catch (Exception e){}     
    };
    
    private synchronized void jogadaComputador() {
        try {
            this.wait(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(TelaJogoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for(int i=0; i<4; i++) {
            for(int j=0; j<2; j++) {
                if(this.grade.getGrade()[i][j].isEncontrouPar()) continue;
                if(this.grade.getGrade()[i][j].isVirada()) {
                    for(int k=0; k<4; k++) {
                        for(int l=0; l<2; l++) {
                            if(i == k && j==l) continue;
                            if(!this.grade.getGrade()[k][l].isVirada()) continue;
                            if(this.grade.getGrade()[i][j].getId() == this.grade.getGrade()[k][l].getId()) {
                                this.marcarCarta(i, j);
                                this.paresEncontrados++;
                                this.pontosComp++;
                                Platform.runLater(
                                    () -> {
                                      lblPontosComputador.setText(String.valueOf(this.pontosComp));
                                    }
                                );
                                try {
                                    this.wait(2000);
                                } catch (InterruptedException ex) {
                                    Logger.getLogger(TelaJogoController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                    }
                }
            }
        }
        
        int count=0, aleX, aleY, primX=-1, primY=-1;
        while(count<2 && this.paresEncontrados<4) {
            aleX = (int) (random()*4);
            aleY = (int) (random()*2);
            if(primX == aleX && primY == aleY) continue;
            
            if(!this.grade.getGrade()[aleX][aleY].isEncontrouPar()) {
                if(primX==-1 && primY==-1) {
                    primX = aleX;
                    primY = aleY;
                }
                this.mostrarCarta(aleX, aleY);
                try {
                    this.wait(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(TelaJogoController.class.getName()).log(Level.SEVERE, null, ex);
                }
                count++;
            }

            if(count==2) {
                if(this.grade.getGrade()[primX][primY].getId() == this.grade.getGrade()[aleX][aleY].getId()) {
                    this.marcarCarta(aleX, aleY);
                    this.paresEncontrados++;
                    this.pontosComp++;
                    Platform.runLater(
                        () -> {
                          lblPontosComputador.setText(String.valueOf(this.pontosComp));
                        }
                    );
                    primX = -1;
                    primY = -1;
                    count = 0;
                    try {
                        this.wait(2000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(TelaJogoController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        
        if(this.paresEncontrados!=4) {
            try {
                this.wait(3000);
            } catch (InterruptedException ex) {
                Logger.getLogger(TelaJogoController.class.getName()).log(Level.SEVERE, null, ex);
            }

            this.virarCartas();
            Platform.runLater(
                () -> {
                  lblJogadorAtual.setText("Usuário");
                }
            );
            
        } else {
            Platform.runLater(
                () -> {
                  if(this.paresEncontrados==4) this.finalizar();
                }
            );
        }
        
    }
    
    private void mostrarCarta(int i, int j) {
        if(this.grade.getGrade()[0][0].getId() == this.grade.getGrade()[i][j].getId()) {
            this.im1.setImage(this.grade.getGrade()[0][0].getImagem());
            this.grade.getGrade()[0][0].alterarStatus(true);
            this.grade.getGrade()[0][0].virar();
            
        } else if(this.grade.getGrade()[1][0].getId() == this.grade.getGrade()[i][j].getId()) {
            this.im2.setImage(this.grade.getGrade()[1][0].getImagem());
            this.grade.getGrade()[1][0].alterarStatus(true);
            this.grade.getGrade()[1][0].virar();
            
        } else if(this.grade.getGrade()[2][0].getId() == this.grade.getGrade()[i][j].getId()) {
            this.im3.setImage(this.grade.getGrade()[2][0].getImagem());
            this.grade.getGrade()[2][0].alterarStatus(true);
            this.grade.getGrade()[2][0].virar();
            
        } else if(this.grade.getGrade()[3][0].getId() == this.grade.getGrade()[i][j].getId()) {
            this.im4.setImage(this.grade.getGrade()[3][0].getImagem());
            this.grade.getGrade()[3][0].alterarStatus(true);
            this.grade.getGrade()[3][0].virar();
            
        } else if(this.grade.getGrade()[0][1].getId() == this.grade.getGrade()[i][j].getId()) {
            this.im5.setImage(this.grade.getGrade()[0][1].getImagem());
            this.grade.getGrade()[0][1].alterarStatus(true);
            this.grade.getGrade()[0][1].virar();
            
        } else if(this.grade.getGrade()[1][1].getId() == this.grade.getGrade()[i][j].getId()) {
            this.im6.setImage(this.grade.getGrade()[1][1].getImagem());
            this.grade.getGrade()[1][1].alterarStatus(true);
            this.grade.getGrade()[1][1].virar();
            
        } else if(this.grade.getGrade()[2][1].getId() == this.grade.getGrade()[i][j].getId()) {
            this.im7.setImage(this.grade.getGrade()[2][1].getImagem());
            this.grade.getGrade()[2][1].alterarStatus(true);
            this.grade.getGrade()[2][1].virar();
            
        } else if(this.grade.getGrade()[3][1].getId() == this.grade.getGrade()[i][j].getId()) {
            this.im8.setImage(this.grade.getGrade()[3][1].getImagem());
            this.grade.getGrade()[3][1].alterarStatus(true);
            this.grade.getGrade()[3][1].virar();
        }
    }  
    
    private void marcarCarta(int i, int j) {
        if(this.grade.getGrade()[0][0].getId() == this.grade.getGrade()[i][j].getId()) {
            this.im1.setImage(this.grade.getGrade()[0][0].getImagem());
            this.grade.getGrade()[0][0].alterarStatus(true);
            this.grade.getGrade()[0][0].encontrada();
            
        }
        if(this.grade.getGrade()[1][0].getId() == this.grade.getGrade()[i][j].getId()) {
            this.im2.setImage(this.grade.getGrade()[1][0].getImagem());
            this.grade.getGrade()[1][0].alterarStatus(true);
            this.grade.getGrade()[1][0].encontrada();
            
        }
        if(this.grade.getGrade()[2][0].getId() == this.grade.getGrade()[i][j].getId()) {
            this.im3.setImage(this.grade.getGrade()[2][0].getImagem());
            this.grade.getGrade()[2][0].alterarStatus(true);
            this.grade.getGrade()[2][0].encontrada();
            
        }
        if(this.grade.getGrade()[3][0].getId() == this.grade.getGrade()[i][j].getId()) {
            this.im4.setImage(this.grade.getGrade()[3][0].getImagem());
            this.grade.getGrade()[3][0].alterarStatus(true);
            this.grade.getGrade()[3][0].encontrada();
            
        }
        if(this.grade.getGrade()[0][1].getId() == this.grade.getGrade()[i][j].getId()) {
            this.im5.setImage(this.grade.getGrade()[0][1].getImagem());
            this.grade.getGrade()[0][1].alterarStatus(true);
            this.grade.getGrade()[0][1].encontrada();
            
        }
        if(this.grade.getGrade()[1][1].getId() == this.grade.getGrade()[i][j].getId()) {
            this.im6.setImage(this.grade.getGrade()[1][1].getImagem());
            this.grade.getGrade()[1][1].alterarStatus(true);
            this.grade.getGrade()[1][1].encontrada();
            
        }
        if(this.grade.getGrade()[2][1].getId() == this.grade.getGrade()[i][j].getId()) {
            this.im7.setImage(this.grade.getGrade()[2][1].getImagem());
            this.grade.getGrade()[2][1].alterarStatus(true);
            this.grade.getGrade()[2][1].encontrada();
            
        }
        if(this.grade.getGrade()[3][1].getId() == this.grade.getGrade()[i][j].getId()) {
            this.im8.setImage(this.grade.getGrade()[3][1].getImagem());
            this.grade.getGrade()[3][1].alterarStatus(true);
            this.grade.getGrade()[3][1].encontrada();
        }
    }  
    
    private void virarCartas() {
        this.quantidadeVirada = 0;
        if(!this.grade.getGrade()[0][0].isEncontrouPar()) {
            this.im1.setImage(this.imagem);
            this.grade.getGrade()[0][0].alterarStatus(false);
        }
        if(!this.grade.getGrade()[1][0].isEncontrouPar()) {
            this.im2.setImage(this.imagem);
            this.grade.getGrade()[1][0].alterarStatus(false);
        }
        if(!this.grade.getGrade()[2][0].isEncontrouPar()) {
            this.im3.setImage(this.imagem);
            this.grade.getGrade()[2][0].alterarStatus(false);
        }
        if(!this.grade.getGrade()[3][0].isEncontrouPar()) {
            this.im4.setImage(this.imagem);
            this.grade.getGrade()[3][0].alterarStatus(false);
        }
        if(!this.grade.getGrade()[0][1].isEncontrouPar()) {
            this.im5.setImage(this.imagem);
            this.grade.getGrade()[0][1].alterarStatus(false);
        }
        if(!this.grade.getGrade()[1][1].isEncontrouPar()) {
            this.im6.setImage(this.imagem);
            this.grade.getGrade()[1][1].alterarStatus(false);
        }
        if(!this.grade.getGrade()[2][1].isEncontrouPar()) {
            this.im7.setImage(this.imagem);
            this.grade.getGrade()[2][1].alterarStatus(false);
        }
        if(!this.grade.getGrade()[3][1].isEncontrouPar()) {
            this.im8.setImage(this.imagem);
            this.grade.getGrade()[3][1].alterarStatus(false);
        }            
    }    
    
    @FXML
    private void im1Clicked(MouseEvent event) {
        if(this.grade.getGrade()[0][0].isEncontrouPar()) return;
        if(this.grade.getGrade()[0][0].getStatus())      return;
        if(this.lblJogadorAtual.getText().equals("Computador")) return;
        
        this.im1.setImage(this.grade.getGrade()[0][0].getImagem());
        this.grade.getGrade()[0][0].alterarStatus(true);
        this.grade.getGrade()[0][0].virar();
        this.quantidadeVirada++;
        
        for(int i=0; i<4; i++) {
            for(int j=0; j<2; j++) {
                if(i==0 && j==0) continue;
                if(this.grade.getGrade()[i][j].getStatus()) {
                    if(this.grade.getGrade()[i][j].getId() == this.grade.getGrade()[0][0].getId()) {
                        this.grade.getGrade()[i][j].encontrada();
                        this.grade.getGrade()[0][0].encontrada();
                        this.paresEncontrados++;
                        this.pontosUser++;
                        this.lblPontosJogador.setText(String.valueOf(this.pontosUser));
                        if(this.paresEncontrados==4) this.finalizar();
                        this.quantidadeVirada = 0;
                        return;
                    }
                }
            }
        }
        
    }

    @FXML
    private void im2Clicked(MouseEvent event) {
        if(this.grade.getGrade()[1][0].isEncontrouPar()) return;
        if(this.grade.getGrade()[1][0].getStatus())      return;
        if(this.lblJogadorAtual.getText().equals("Computador")) return;
        
        this.im2.setImage(this.grade.getGrade()[1][0].getImagem());
        this.grade.getGrade()[1][0].alterarStatus(true);
        this.grade.getGrade()[1][0].virar();
        this.quantidadeVirada++;
        
        for(int i=0; i<4; i++) {
            for(int j=0; j<2; j++) {
                if(i==1 && j==0) continue;
                if(this.grade.getGrade()[i][j].getStatus()) {
                    if(this.grade.getGrade()[i][j].getId() == this.grade.getGrade()[1][0].getId()) {
                        this.grade.getGrade()[i][j].encontrada();
                        this.grade.getGrade()[1][0].encontrada();
                        this.paresEncontrados++;
                        this.pontosUser++;
                        this.lblPontosJogador.setText(String.valueOf(this.pontosUser));
                        if(this.paresEncontrados==4) this.finalizar();
                        this.quantidadeVirada = 0;
                        return;
                    }
                }
            }
        }
        
    }

    @FXML
    private void im3Clicked(MouseEvent event) {
        if(this.grade.getGrade()[2][0].isEncontrouPar()) return;
        if(this.grade.getGrade()[2][0].getStatus())      return;
        if(this.lblJogadorAtual.getText().equals("Computador")) return;
        
        this.im3.setImage(this.grade.getGrade()[2][0].getImagem());
        this.grade.getGrade()[2][0].alterarStatus(true);
        this.grade.getGrade()[2][0].virar();
        this.quantidadeVirada++;
        
        for(int i=0; i<4; i++) {
            for(int j=0; j<2; j++) {
                if(i==2 && j==0) continue;
                if(this.grade.getGrade()[i][j].getStatus()) {
                    if(this.grade.getGrade()[i][j].getId() == this.grade.getGrade()[2][0].getId()) {
                        this.grade.getGrade()[i][j].encontrada();
                        this.grade.getGrade()[2][0].encontrada();
                        this.paresEncontrados++;
                        this.pontosUser++;
                        this.lblPontosJogador.setText(String.valueOf(this.pontosUser));
                        if(this.paresEncontrados==4) this.finalizar();
                        this.quantidadeVirada = 0;
                        return;
                    }
                }
            }
        }
        
    }

    @FXML
    private void im4Clicked(MouseEvent event) {
        if(this.grade.getGrade()[3][0].isEncontrouPar()) return;
        if(this.grade.getGrade()[3][0].getStatus())      return;
        if(this.lblJogadorAtual.getText().equals("Computador")) return;
        
        this.im4.setImage(this.grade.getGrade()[3][0].getImagem());
        this.grade.getGrade()[3][0].alterarStatus(true);
        this.grade.getGrade()[3][0].virar();
        this.quantidadeVirada++;
        
        for(int i=0; i<4; i++) {
            for(int j=0; j<2; j++) {
                if(i==3 && j==0) continue;
                if(this.grade.getGrade()[i][j].getStatus()) {
                    if(this.grade.getGrade()[i][j].getId() == this.grade.getGrade()[3][0].getId()) {
                        this.grade.getGrade()[i][j].encontrada();
                        this.grade.getGrade()[3][0].encontrada();
                        this.paresEncontrados++;
                        this.pontosUser++;
                        this.lblPontosJogador.setText(String.valueOf(this.pontosUser));
                        if(this.paresEncontrados==4) this.finalizar();
                        this.quantidadeVirada = 0;
                        return;
                    }
                }
            }
        }
        
    }

    @FXML
    private void im5Clicked(MouseEvent event) {
        if(this.grade.getGrade()[0][1].isEncontrouPar()) return;
        if(this.grade.getGrade()[0][1].getStatus())      return;
        if(this.lblJogadorAtual.getText().equals("Computador")) return;
        
        this.im5.setImage(this.grade.getGrade()[0][1].getImagem());
        this.grade.getGrade()[0][1].alterarStatus(true);
        this.grade.getGrade()[0][1].virar();
        this.quantidadeVirada++;
        
        for(int i=0; i<4; i++) {
            for(int j=0; j<2; j++) {
                if(i==0 && j==1) continue;
                if(this.grade.getGrade()[i][j].getStatus()) {
                    if(this.grade.getGrade()[i][j].getId() == this.grade.getGrade()[0][1].getId()) {
                        this.grade.getGrade()[i][j].encontrada();
                        this.grade.getGrade()[0][1].encontrada();
                        this.paresEncontrados++;
                        this.pontosUser++;
                        this.lblPontosJogador.setText(String.valueOf(this.pontosUser));
                        if(this.paresEncontrados==4) this.finalizar();
                        this.quantidadeVirada = 0;
                        return;
                    }
                }
            }
        }
        
    }

    @FXML
    private void im6Clicked(MouseEvent event) {
        if(this.grade.getGrade()[1][1].isEncontrouPar()) return;
        if(this.grade.getGrade()[1][1].getStatus())      return;
        if(this.lblJogadorAtual.getText().equals("Computador")) return;
        
        this.im6.setImage(this.grade.getGrade()[1][1].getImagem());
        this.grade.getGrade()[1][1].alterarStatus(true);
        this.grade.getGrade()[1][1].virar();
        this.quantidadeVirada++;
        
        for(int i=0; i<4; i++) {
            for(int j=0; j<2; j++) {
                if(i==1 && j==1) continue;
                if(this.grade.getGrade()[i][j].getStatus()) {
                    if(this.grade.getGrade()[i][j].getId() == this.grade.getGrade()[1][1].getId()) {
                        this.grade.getGrade()[i][j].encontrada();
                        this.grade.getGrade()[1][1].encontrada();
                        this.paresEncontrados++;
                        this.pontosUser++;
                        this.lblPontosJogador.setText(String.valueOf(this.pontosUser));
                        if(this.paresEncontrados==4) this.finalizar();
                        this.quantidadeVirada = 0;
                        return;
                    }
                }
            }
        }
        
    }

    @FXML
    private void im7Clicked(MouseEvent event) {
        if(this.grade.getGrade()[2][1].isEncontrouPar()) return;
        if(this.grade.getGrade()[2][1].getStatus())      return;
        if(this.lblJogadorAtual.getText().equals("Computador")) return;
        
        this.im7.setImage(this.grade.getGrade()[2][1].getImagem());
        this.grade.getGrade()[2][1].alterarStatus(true);
        this.grade.getGrade()[2][1].virar();
        this.quantidadeVirada++;
        
        for(int i=0; i<4; i++) {
            for(int j=0; j<2; j++) {
                if(i==2 && j==1) continue;
                if(this.grade.getGrade()[i][j].getStatus()) {
                    if(this.grade.getGrade()[i][j].getId() == this.grade.getGrade()[2][1].getId()) {
                        this.grade.getGrade()[i][j].encontrada();
                        this.grade.getGrade()[2][1].encontrada();
                        this.paresEncontrados++;
                        this.pontosUser++;
                        this.lblPontosJogador.setText(String.valueOf(this.pontosUser));
                        if(this.paresEncontrados==4) this.finalizar();
                        this.quantidadeVirada = 0;
                        return;
                    }
                }
            }
        }
        
    }

    @FXML
    private void im8Clicked(MouseEvent event) {
        if(this.grade.getGrade()[3][1].isEncontrouPar()) return;
        if(this.grade.getGrade()[3][1].getStatus())      return;
        if(this.lblJogadorAtual.getText().equals("Computador")) return;
        
        this.im8.setImage(this.grade.getGrade()[3][1].getImagem());
        this.grade.getGrade()[3][1].alterarStatus(true);
        this.grade.getGrade()[3][1].virar();
        this.quantidadeVirada++;
        
        for(int i=0; i<4; i++) {
            for(int j=0; j<2; j++) {
                if(i==3 && j==1) continue;
                if(this.grade.getGrade()[i][j].getStatus()) {
                    if(this.grade.getGrade()[i][j].getId() == this.grade.getGrade()[3][1].getId()) {
                        this.grade.getGrade()[i][j].encontrada();
                        this.grade.getGrade()[3][1].encontrada();
                        this.paresEncontrados++;
                        this.pontosUser++;
                        this.lblPontosJogador.setText(String.valueOf(this.pontosUser));
                        if(this.paresEncontrados==4) this.finalizar();
                        this.quantidadeVirada = 0;
                        return;
                    }
                }
            }
        }
    }
    
    private synchronized void release() {
        if(this.quantidadeVirada==2) lblJogadorAtual.setText("Computador");
        
        try {
            this.wait(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(TelaJogoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(this.quantidadeVirada==2) {
            this.virarCartas();
            new Thread(t1).start();
        }
    }

    @FXML
    private void im1Released(MouseEvent event) {
        if(this.quantidadeVirada==2) this.release();
    }

    @FXML
    private void im2Released(MouseEvent event) {
        if(this.quantidadeVirada==2) this.release();
    }

    @FXML
    private void im3Released(MouseEvent event) {
        if(this.quantidadeVirada==2) this.release();
    }

    @FXML
    private void im4Released(MouseEvent event) {
        if(this.quantidadeVirada==2) this.release();
    }

    @FXML
    private void im5Released(MouseEvent event) {
        if(this.quantidadeVirada==2) this.release();
    }

    @FXML
    private void im6Released(MouseEvent event) {
        if(this.quantidadeVirada==2) this.release();
    }

    @FXML
    private void im7Released(MouseEvent event) {
        if(this.quantidadeVirada==2) this.release();
    }

    @FXML
    private void im8Released(MouseEvent event) {
        if(this.quantidadeVirada==2) this.release();
    }
    
}
