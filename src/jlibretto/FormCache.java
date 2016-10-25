/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlibretto;

import java.io.Serializable;

/**
 *
 * @author feder
 */
public class FormCache implements Serializable {
    String contenutoInputNome;
    String contenutoInputCrediti;
    String contenutoInputValutazione;
    String contenutoInputData;
    
    public FormCache(String n,String c,String m,String d) {
        contenutoInputNome = n;
        contenutoInputCrediti = c;
        contenutoInputValutazione = m;
        contenutoInputData = d;
    }
}
