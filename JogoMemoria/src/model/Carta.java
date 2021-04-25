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
    private Image imagem;
    private boolean status;
    private boolean encontrouPar;
    
    public Carta(String path) throws IOException {
        File file = new File(path);
        this.imagem = new Image(file.toURI().toString());
        this.status = false;
        this.encontrouPar = false;
    }
    
    public void alterarStatus() {
        if(this.status == true) this.status = false;
        else                    this.status = true;
    }
    
    public void encontrada() {
        this.encontrouPar = true;
    }

    public Image getImagem() {
        return imagem;
    }

    public boolean getStatus() {
        return status;
    }

    public boolean isEncontrouPar() {
        return encontrouPar;
    }
    
    
}
