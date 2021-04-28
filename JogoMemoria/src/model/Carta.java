/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.File;
import java.io.IOException;
import javafx.scene.image.Image;

/**
 *
 * @author Otavio
 */
public class Carta {
    int id;
    private Image imagem;
    private boolean statusAtual;
    private boolean virada;
    private boolean encontrouPar;
    
    public Carta(String path, int id) throws IOException {
        File file = new File(path);
        this.id = id;
        this.imagem = new Image(file.toURI().toString());
        this.statusAtual = false;
        this.virada = false;
        this.encontrouPar = false;
    }
    
    public void alterarStatus(boolean situacao) {
        this.statusAtual = situacao;
    }
    
    public void virar() {
        this.virada = true;
    }
    
    public void encontrada() {
        this.encontrouPar = true;
    }

    public Image getImagem() {
        return imagem;
    }
    
    public int getId() {
        return id;
    }

    public boolean getStatus() {
        return statusAtual;
    }
    
    public boolean isVirada() {
        return virada;
    }

    public boolean isEncontrouPar() {
        return encontrouPar;
    }
    
    
}
