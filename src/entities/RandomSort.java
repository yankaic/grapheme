/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.nio.file.Files;
import java.util.Comparator;
import java.util.Random;



/**
 *
 * @author shanks
 */
public class RandomSort implements Comparator<Files> {

    private Random r;
    
    @Override
    public int compare(Files o1, Files o2) {
        return r.nextInt(3);
    }
    
    private int randomCompare(){
        return 0;
    }
    
}
