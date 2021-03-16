/**
 * Lily Ngo 
 * CSCI 3381 OO with Java
 * Project 2
 */
package project2;

import javax.swing.JFrame;

public class Proj2Main {

  public static void main(String[] args) {
    JFrame frame = new JFrame("Push Counter");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    Proj2Panel panel = new Proj2Panel();
    frame.getContentPane().add(panel);

    frame.pack();
    frame.setVisible(true);
  }

}
