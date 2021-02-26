package edu.eci.arsw.highlandersim;

import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Immortal extends Thread {

    private ImmortalUpdateReportCallback updateCallback=null;
    
    private int health;
    
    private int defaultDamageValue;

    private final List<Immortal> immortalsPopulation;

    private final String name;

    private final Random r = new Random(System.currentTimeMillis());

    private boolean detener = false;
    
    private boolean stop = false;

    public Immortal(String name, List<Immortal> immortalsPopulation, int health, int defaultDamageValue, ImmortalUpdateReportCallback ucb) {
        super(name);
        this.updateCallback=ucb;
        this.name = name;
        this.immortalsPopulation = immortalsPopulation;
        this.health = health;
        this.defaultDamageValue=defaultDamageValue;
    }

    public void run() {
        
        while(!stop && health>0) {
            synchronized (this) {
                while (detener) {                      
                    try {
                        wait();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();    
                    }
                }
         }   

            Immortal im;
            synchronized (immortalsPopulation) {
                        int myIndex = immortalsPopulation.indexOf(this);

                        int nextFighterIndex = r.nextInt(immortalsPopulation.size());

                        //avoid self-fight
                        if (nextFighterIndex == myIndex) {
                            nextFighterIndex = ((nextFighterIndex + 1) % immortalsPopulation.size());
                        }

                        im = immortalsPopulation.get(nextFighterIndex);

                        this.fight(im);
            }

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }           
        }
        
        if(health==0) {
            immortalsPopulation.remove(this);
        }
        
    }

    public void fight(Immortal i2) {
        String pReport ="";
        synchronized (i2) {
            if (i2.getHealth()>0) {
                if(!(immortalsPopulation.size()==2 && i2.getHealth()-defaultDamageValue==0)) {
                    i2.changeHealth(i2.getHealth() - defaultDamageValue);
                    this.health += defaultDamageValue;
                    pReport="Fight: " + this + " vs " + i2+"\n";
                }
            } else {
                pReport=this + " says:" + i2 + " is already dead!\n";      
            }          
        }
        if(!pReport.equals(""))
        {
            synchronized (updateCallback) {
                updateCallback.processReport(pReport);
            }
        }
    }

    public void changeHealth(int v) {
        health = v;
    }

    public int getHealth() {
        return health;
    }

    synchronized void detener(){
        detener=true;
    }
    synchronized void renaudar(){
        detener=false;
        notify();
    }
    public void stopHilo() {
        stop = true;
    }
    
    @Override
    public String toString() {

        return name + "[" + health + "]";
    }

}
