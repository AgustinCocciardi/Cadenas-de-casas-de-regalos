package regalos;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class Cadena {

    private int cantidadSucursales;
    private int[][] matrizAdyacencia;
    private ArrayList<Integer> noPintados = new ArrayList<Integer>();
    private ArrayList<Integer> pintados = new ArrayList<Integer>();
    private ArrayList<Integer> solucion = new ArrayList<Integer>();
    
    public Cadena(Scanner entrada){
        int valor = 0;
        this.cantidadSucursales = entrada.nextInt();
        this.matrizAdyacencia = new int[this.cantidadSucursales][this.cantidadSucursales];
        for(int[] rows : matrizAdyacencia)
            Arrays.fill(rows, Integer.MAX_VALUE);
        
        for(int i=0; i<this.cantidadSucursales; i++){
            while((valor = entrada.nextInt()) != -1){
                if(i != valor){
                    this.matrizAdyacencia[i][valor-1] = 1;
                    this.matrizAdyacencia[valor-1][i] = 1;
                }
            }
        }
    }
    
    private void setearLista(){
        for(int i=0; i<this.cantidadSucursales; i++)
            this.noPintados.add(i);
        Random rnd = new Random();
        Collections.shuffle(noPintados,rnd);
        this.pintados.clear();
    }
    
    private void pintar(){
        ArrayList<Integer> pintadosActuales; 
        Integer actual, auxiliar;
        boolean puedoPintar;
        int recorrido;
        while(this.noPintados.isEmpty() == false){
            Integer iterador = this.noPintados.remove(0);
            pintadosActuales = new ArrayList<Integer>();
            pintadosActuales.add(iterador);
            for(int i=0; i<this.noPintados.size(); i++){
                    if(this.noPintados.contains((Integer) i)){
                        puedoPintar = true;
                        actual = this.noPintados.get(i);
                        recorrido = 0;
                        while(recorrido < pintadosActuales.size() && puedoPintar == true){
                            auxiliar = pintadosActuales.get(recorrido);
                            if(this.matrizAdyacencia[auxiliar][actual] != Integer.MAX_VALUE)
                                puedoPintar = false;
                            recorrido++;
                        }
                        if(puedoPintar == true){
                            pintadosActuales.add(actual);
                            this.noPintados.remove(actual);
                        }
                    }
                }
            if(pintadosActuales.size() > this.solucion.size())
                this.solucion = pintadosActuales;
        }
    }
    
    public void resolver(PrintWriter salida){
        for(int i=0; i<10000 ; i++){
            this.setearLista();
            this.pintar();
        }
        salida.println(this.solucion.size());
        for(int i=0; i<this.solucion.size(); i++){
            salida.print((this.solucion.get(i)+1) + " ");
        }
    }
    
    public static void main(String[] args) throws IOException {
       Scanner entrada = new Scanner(new FileReader("regalos.in"));
       Cadena cadena = new Cadena(entrada);
       entrada.close();
       PrintWriter salida = new PrintWriter(new FileWriter("regalos.out"));
       cadena.resolver(salida);
       salida.close();
    }

}
