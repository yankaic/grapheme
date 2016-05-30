/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphemes;

import entities.Controller;
import entities.GameControll;
import view.SwipeView;

/**
 *
 * @author Yan Kaic
 */
public class Main {

    public static final char BAR = '/';
    public static GameControll gameControll = new GameControll();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        gameControll.initGame();
    }//fim main

}//fim class
