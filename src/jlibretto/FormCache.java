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
    String nameBinaryInput;
    String creditsBinaryInput;
    String markBinaryInput;
    String dateBinaryInput;
    
    public FormCache(String n,String c,String m,String d) {
        nameBinaryInput = n;
        creditsBinaryInput = c;
        markBinaryInput = m;
        dateBinaryInput = d;
    }
}
