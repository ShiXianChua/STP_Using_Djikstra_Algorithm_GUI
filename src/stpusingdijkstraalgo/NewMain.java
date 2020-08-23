/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stpusingdijkstraalgo;

/**
 *
 * @author progu
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        // TODO code application logic here
        for (int i = 0; i < 1000; i++) {
            System.out.print(i + "\r");
            Thread.sleep(100);
        }
    }
    
}
