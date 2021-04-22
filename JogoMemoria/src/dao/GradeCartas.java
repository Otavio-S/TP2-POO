/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.IOException;
import static java.lang.Math.random;
import model.Carta;



/**
 *
 * @author Otavio
 */
public class GradeCartas {
    
    private Carta[][] grade;
    
    public GradeCartas() {
        this.grade = new Carta[4][2];
    }
    
    public void carregarGrade() {
        int aleX, aleY, quant=0;
        
        for(int i=0;i<4;i++) {
            grade[i][0] = null;
            grade[i][1] = null;
        }
        
        while(quant<8) {
            aleX = (int) (random()*4);
            aleY = (int) (random()*2);
            
            if(grade[aleX][aleY] == null) {
                try {
                    String path = "images/imagem"+(quant%4)+".jpg";
                    grade[aleX][aleY] = new Carta(path);
                } catch (IOException ex) {
                    System.out.println("Endereço inválido! "+ex);
                }
                quant++;
            }
            
        }
        
    }
    
    public Carta[][] getGrade() {
        return this.grade;
    }
    
}
