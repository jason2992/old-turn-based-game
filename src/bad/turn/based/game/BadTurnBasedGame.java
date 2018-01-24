/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bad.turn.based.game;

import java.util.Scanner;
import java.lang.Math;
/**
 *
 * @author jason
 */
public class BadTurnBasedGame {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println(2.4 / (int) 1.2);
        stats[] unit = new stats[4];
        for (int i = 0; i < 4; i++){
            unit[i] = new stats();
            unit[i].createStats(i, true);
//            unit[i].displayStats();
        }
        
        stats[] enemy = new stats[6];
        for (int i = 0; i < 6; i++){
            enemy[i] = new stats();
        }
        
        Scanner s = new Scanner(System.in);
        Scanner chooseSwap1 = new Scanner(System.in);
        Scanner chooseSwap2 = new Scanner(System.in);
        
        int swap1;
        int swap2;
        boolean swapped = false;

        stats tempSwap = new stats();
        
        int classChoice; // choice for making class
        int battleAction;
        int damage; // damage unit deals
        int countPlayerDead;
        int target; // used to choose target for offense/ defense
        
        System.out.println ("Welcome to my simple turn based game");
        System.out.println ("");
        System.out.print ("press 1 to view game information, or press enter to start : ");
        
        int startInfo = s.nextInt();
        if (startInfo == 1){
            
        }
        
        while(true){
            System.out.println ("Welcome to my simple turn based game");
            System.out.println ("");
            System.out.println ("Phase 1: Select your class");
            System.out.println ("enter 1 for a warrior, 2 for a archer, 3 for a healer, 4 for a mage");

            System.out.println ("please enter the class for your first unit");
            classChoice = s.nextInt();
            unit[0].createStats(classChoice, false);
            unit[0].displayStats();
            
            System.out.println ("please enter the class for your second unit");            
            classChoice = s.nextInt();
            unit[1].createStats(classChoice, false);
            unit[1].displayStats();

            System.out.println ("please enter the class for your third unit");            
            classChoice = s.nextInt();
            unit[2].createStats(classChoice, false);
            unit[2].displayStats();

            System.out.println ("please enter the class for your fourth and final unit");            
            classChoice = s.nextInt();
            unit[3].createStats(classChoice, false);
            unit[3].displayStats();
            
            enemy[0].createStats(1, true);
            enemy[1].createStats(2, true);
            enemy[2].createStats(2, true);
            enemy[3].createStats(4, true);
            enemy[4].createStats(3, true);
            enemy[5].createStats(3, true);
            
            //---------------------------------------------------------
            //     G  A  M  E   B  E  G  I  N  S   H  E  R  E
            //---------------------------------------------------------
            
            while (enemy[0].health > 0 || enemy[1].health > 0 || enemy[2].health > 0 || enemy[3].health > 0 || enemy[4].health > 0 || enemy[5].health > 0){
                //turn order begins again
                countPlayerDead = 0;
                for (int i = 0; i < 4; i++){
                    if (unit[i].health <= 0){
                       countPlayerDead += 1;                                              
                    }
                }
                for (int i = 0; i < 4; i++){ // reset skills/stats
                    unit[i].resetEvasion();
                    unit[i].Taunt = false;
                }
                for (int i = 0; i < 4; i++){ // reset skills/stats
                    if (unit[i].defense > unit[i].defaultDefense){
                        unit[i].defense -= 1;
                    }
                }
                
                enemy = organiseEnemy(enemy);

                swapped = false;
                swap1 = 0;
                swap2 = 0;
                
                System.out.println ("----------------------------------------");
                System.out.println ("    E    N    E    M    I    E    S   ");
                displayEnemy(enemy[0], enemy[1], enemy[2], enemy[3], enemy[4], enemy[5]);
                
                System.out.println ("----------------------------------------");
                System.out.println ("    P    L    A    Y    E    R    S   ");
                displayPlayers(unit[0], unit[1], unit[2], unit[3]);
                

                System.out.println ("-------------Player Phase---------------");
                System.out.println ("enter 1 : attack, 2 : action, 3 : evade (+20%), 4 : rest, 5 : swap "); // player phase
                //unit 1 turn
                if (unit[0].health >0){
                    System.out.print ("choose your action for your first unit: ");
                    battleAction = s.nextInt();
                    switch (battleAction){
                        case 1: 
                            System.out.print ("enter the enemy you are targeting: ");
                            damage = unit[0].attack(); //calculates damage taken
                            target = chooseTarget();
                            enemy[target - 1].hit(damage); //deals damage to target
                            break;
                        case 2: 
                            if (unit[0].role == "Warrior" && unit[0].mana > 0){ // higher chance of being targeted, slight def increase
                                //taunt, gets murdered by everyone on that turn                                        
                                unit[0].Taunt = true;
                                unit[0].defense = 9;
                                unit[0].mana -= 1;
                            }
                            else if (unit[0].role == "Archer" && unit[0].mana > 0){ //increased evasion
                                unit[0].evasionBuff();            
                            }
                            else if (unit[0].role == "Healer" && unit[0].mana > 0){ //heals chosen target                                
                                System.out.print ("enter the ally you are healing: ");
                                target = chooseTarget();
                                unit[target - 1].heal(unit[0].INT);                   
                                unit[0].mana -= 1;                      
                            }
                            else if (unit[0].role == "Mage" && unit[0].mana > 0){ //deals magic damage
                                System.out.print ("enter the enemy you are targeting: ");
                                target = chooseTarget();
                                damage = unit[0].magicAttack();
                                enemy[target -1].magicHit(damage);
                            }
                            else {
                                System.out.println ("your unit has no mana");
                            }
                            break;
                        case 3: 
                            unit[0].Evade(); //adds 20% evasion
                            break;
                        case 4: 
                            unit[0].heal(5); //heals 5 hp
                            unit[0].manaRegen(); //regens 2 health
                            break;
                        case 5:
                            if (!swapped){
                                System.out.print ("choose the two units you would like to swap: ");
                                swap1 = chooseSwap1.nextInt();
                                swap2 = chooseSwap2.nextInt();                            
                                swapped = true;
                            }
                            else{
                                System.out.print ("you may only swap once per turn");                                                               
                            }
                    }
                }
                else {
                    System.out.println ("Unit 1 - " + unit[0].role + " is dead and cannot move");
                }
                //unit 2 turn
                if (unit[1].health >0){
                    System.out.print ("choose your action for your second unit: ");
                    battleAction = s.nextInt();
                    switch (battleAction){
                        case 1: 
                            System.out.print ("enter the enemy you are targeting: ");
                            damage = unit[1].attack(); //calculates damage taken
                            target = chooseTarget();
                            enemy[target-1].hit(damage); //deals damage to target
                            break;
                        case 2: 
                            if (unit[1].role == "Warrior" && unit[1].mana > 0){ // higher chance of being targeted, slight def increase
                                //taunt, gets murdered by everyone on that turn
                                unit[1].Taunt = true;
                                unit[1].defense = 9;
                                unit[1].mana -= 1;
                            }
                            else if (unit[1].role == "Archer" && unit[1].mana > 0){ //increased evasion
                                unit[1].evasionBuff();            
                            }
                            else if (unit[1].role == "Healer" && unit[1].mana > 0){ //heals chosen target                                
                                System.out.print ("enter the ally you are healing: ");
                                target = chooseTarget();
                                unit[target - 1].heal(unit[1].INT);                   
                                unit[1].mana -= 1;        
                            }
                            else if (unit[1].role == "Mage" && unit[1].mana > 0){ //deals magic damage
                                System.out.print ("enter the enemy you are targeting: ");
                                target = chooseTarget();
                                damage = unit[1].magicAttack();
                                enemy[target -1].magicHit(damage);
                            }
                            break;
                        case 3: 
                            unit[1].Evade(); //adds 20% evasion
                            break;
                        case 4: 
                            unit[1].heal(5); //heals 5 hp
                            unit[1].manaRegen(); //regens 2 health
                            break;
                        case 5:
                            if (!swapped){
                                System.out.print ("choose the two units you would like to swap: ");
                                swap1 = chooseSwap1.nextInt();
                                swap2 = chooseSwap2.nextInt();                            
                                swapped = true;
                            }
                            else{
                                System.out.print ("you may only swap once per turn");                                                               
                            }
                    }
                }
                else {
                    System.out.println ("Unit 2 - " + unit[1].role + " is dead and cannot move");
                }
                //unit 3 turn
                if (unit[2].health >0){
                    System.out.print ("choose your action for your third unit: ");
                    battleAction = s.nextInt();
                    switch (battleAction){
                        case 1: 
                            System.out.print ("enter the enemy you are targeting: ");
                            damage = unit[2].attack(); //calculates damage taken
                            target = chooseTarget();
                            enemy[target-1].hit(damage); //deals damage to target
                            break;
                        case 2:                             
                            if (unit[2].role == "Warrior" && unit[2].mana > 0){ // higher chance of being targeted, slight def increase
                                //taunt, gets murdered by everyone on that turn
                                unit[2].Taunt = true;
                                unit[2].defense = 9;
                                unit[2].mana -= 1;
                            }
                            else if (unit[2].role == "Archer" && unit[2].mana > 0){ //increased evasion
                                unit[2].evasionBuff();            
                            }
                            else if (unit[2].role == "Healer" && unit[2].mana > 0){ //heals chosen target                                
                                System.out.print ("enter the ally you are healing: ");
                                target = chooseTarget();
                                unit[target - 1].heal(unit[2].INT);                                       
                                unit[2].mana -= 1;      
                            }
                            else if (unit[2].role == "Mage" && unit[2].mana > 0){ //deals magic damage
                                System.out.print ("enter the enemy you are targeting: ");
                                target = chooseTarget();
                                damage = unit[2].magicAttack();
                                enemy[target -1].magicHit(damage);
                            }
                            break;
                        case 3: 
                            unit[2].Evade(); //adds 20% evasion
                            break;
                        case 4: 
                            unit[2].heal(5); //heals 5 hp
                            unit[2].manaRegen(); //regens 2 health
                            break;
                        case 5:
                            if (!swapped){
                                System.out.print ("choose the two units you would like to swap: ");
                                swap1 = chooseSwap1.nextInt();
                                swap2 = chooseSwap2.nextInt();                            
                                swapped = true;
                            }
                            else{
                                System.out.print ("you may only swap once per turn");                                                               
                            }
                    }
                }
                else {
                    System.out.println ("Unit 3 - " + unit[2].role + " is dead and cannot move");
                }
                //unit 4 turn
                if (unit[3].health >0){
                    System.out.print ("choose your action for your fourth unit: ");
                    battleAction = s.nextInt();
                    switch (battleAction){
                        case 1: 
                            System.out.print ("enter the enemy you are targeting: ");
                            damage = unit[3].attack(); //calculates damage taken
                            target = chooseTarget();
                            enemy[target-1].hit(damage); //deals damage to target
                            break;
                        case 2:                             
                            if (unit[3].role == "Warrior" && unit[3].mana > 0){ // higher chance of being targeted, slight def increase
                                //taunt, gets murdered by everyone on that turn
                                unit[3].Taunt = true;
                                unit[3].defense = 9;
                                unit[3].mana -= 1;
                            }
                            else if (unit[3].role == "Archer" && unit[3].mana > 0){ //increased evasion
                                unit[3].evasionBuff();            
                            }
                            else if (unit[3].role == "Healer" && unit[3].mana > 0){ //heals chosen target                                
                                System.out.print ("enter the ally you are healing: ");
                                target = chooseTarget();
                                unit[target - 1].heal(unit[3].INT);                                                           
                                unit[3].mana -= 1;      
                            }
                            else if (unit[3].role == "Mage" && unit[3].mana > 0){ //deals magic damage
                                System.out.print ("enter the enemy you are targeting: ");
                                target = chooseTarget();
                                damage = unit[3].magicAttack();
                                enemy[target -1].magicHit(damage);
                            }
                            break;
                        case 3: 
                            unit[3].Evade(); //adds 20% evasion
                            break;
                        case 4: 
                            unit[3].heal(5); //heals 5 hp  try 10 % if 5 is too low
                            unit[3].manaRegen(); //regens 2 mp
                            break;
                        case 5:
                            if (!swapped){
                                System.out.print ("choose the two units you would like to swap: ");
                                swap1 = chooseSwap1.nextInt();
                                swap2 = chooseSwap2.nextInt();                            
                                swapped = true;
                            }
                            else{
                                System.out.print ("you may only swap once per turn");                                                               
                            }
                    }
                }
                else {
                    System.out.println ("Unit 4 - " + unit[3].role + " is dead and cannot move");
                }
                
                if (swapped){
                    tempSwap = unit[swap1 - 1];
                    unit[swap1 - 1] = unit[swap2 - 1];
                    unit[swap2 - 1] = tempSwap;
                    
                } 
                //end of player turn
                
                
                System.out.println ("----------------------------------------");
                System.out.println ("    E    N    E    M    I    E    S   ");
                displayEnemy(enemy[0], enemy[1], enemy[2], enemy[3], enemy[4], enemy[5]);
                
                System.out.println ("----------------------------------------");
                System.out.println ("    P    L    A    Y    E    R    S   ");
                displayPlayers(unit[0], unit[1], unit[2], unit[3]);
                

                System.out.println ("-------------Enemy Phase---------------");
                //begin enemy turn
                for (int i = 0; i < 6; i++){
                    if (enemy[i].health > 0){
                        target = enemyChooseTarget(unit,countPlayerDead);
                        //target = (int) (Math.random() * 3);                    
                        
                        //enemy unique roles
                        //mage always uses magic
                        damage = enemy[i].attack(); //calculates damage taken
                        if (enemy[i].role == "Mage" && enemy[i].mana != 0){ 
                            damage = enemy[i].magicAttack(); //calculates damage taken                            
                            System.out.println("mage");
                            unit[target].magicHit(damage);
                        }
                        else if (enemy[i].role == "Mage" && enemy[i].mana <= 0){
                            unit[target].hit(damage);
                        }
                        
                        //archer can hit twice
                        if (enemy[i].role == "Archer"){
                            if (1 >= (int)(Math.random() * 2 + 1)){ //33%
                                System.out.println("archer");
                                unit[target].hit(damage);           
                                damage = enemy[i].attack(); //calculates damage taken                 
                            }
                        }
                        
                        //rogue can ignore taunt and target in reverse
                        if (enemy[i].role == "Healer"){
                            target = rogueChooseTarget(unit,countPlayerDead);
                            System.out.println("rogue");  
                        }
                        
                        //warriors can hit full row
                        if (enemy[i].role == "Warrior"){                            
                            if (1 >= (int)(Math.random() * 2 + 1)){ //33%
                                System.out.println("warrior");
                                for (int k = 0; k < 4; k++){
                                    unit[k].hit(damage - k);
                                }
                            }
                            else {
                            unit[target].hit(damage);                                
                            }
                        }
                        if (enemy[i].role != "Mage" && enemy[i].role != "Warrior"){
                            unit[target].hit(damage);
                        }
                        
                    }
                }
                    
                
            }
        }
    }





//---------------------------------------------------------

//functions used in main
    
//---------------------------------------------------------
    public static void displayEnemy(stats enemy1,stats enemy2,stats enemy3,stats enemy4,stats enemy5,stats enemy6){
        if (enemy1.health > 0){
            System.out.print (" Enemy 1    ");
        }
        if (enemy2.health > 0){
            System.out.print (" Enemy 2    ");
        }
        if (enemy3.health > 0){
            System.out.print (" Enemy 3    ");
        }
        if (enemy4.health > 0){
            System.out.print (" Enemy 4    ");
        }
        if (enemy5.health > 0){
            System.out.print (" Enemy 5    ");
        }
        if (enemy6.health > 0){
            System.out.print (" Enemy 6    ");
        }
        
        System.out.println ("");
        
        if (enemy1.health > 0){
            System.out.print ("  " + enemy1.health + "/" + enemy1.maxHealth + "HP   ");
        }
        if (enemy2.health > 0){
            System.out.print ("  " + enemy2.health + "/" + enemy2.maxHealth + "HP   ");
        }
        if (enemy3.health > 0){
            System.out.print ("  " + enemy3.health + "/" + enemy3.maxHealth + "HP   ");
        }
        if (enemy4.health > 0){
            System.out.print ("  " + enemy4.health + "/" + enemy4.maxHealth + "HP   ");
        }
        if (enemy5.health > 0){
            System.out.print ("  " + enemy5.health + "/" + enemy5.maxHealth + "HP   ");
        }
        if (enemy6.health > 0){
            System.out.print ("  " + enemy6.health + "/" + enemy6.maxHealth + "HP   ");
        }
        System.out.println("");
   
        if (enemy1.health > 0){
            System.out.print ("  " + enemy1.mana + "/" + enemy1.maxMana + "MP     ");
        }
        if (enemy2.health > 0){
            System.out.print ("  " + enemy2.mana + "/" + enemy2.maxMana + "MP     ");
        }
        if (enemy3.health > 0){
            System.out.print ("  " + enemy3.mana + "/" + enemy3.maxMana + "MP     ");
        }
        if (enemy4.health > 0){
            System.out.print ("  " + enemy4.mana + "/" + enemy4.maxMana + "MP     ");
        }
        if (enemy5.health > 0){
            System.out.print ("  " + enemy5.mana + "/" + enemy5.maxMana + "MP     ");
        }
        if (enemy6.health > 0){
            System.out.print ("  " + enemy6.mana + "/" + enemy6.maxMana + "MP     ");
        }
        System.out.println("");
    }
    
    public static void displayPlayers(stats unit1,stats unit2,stats unit3,stats unit4){

        System.out.print (" Unit 1     ");
        System.out.print (" Unit 2     ");
        System.out.print (" Unit 3     ");
        System.out.print (" Unit 4     ");  
            
        System.out.println ("");
        
        displayHealth(unit1);
        displayHealth(unit2);
        displayHealth(unit3);
        displayHealth(unit4);

        System.out.println ("");
            
        System.out.print ("  " + unit1.mana + "/" + unit1.maxMana + "MP     ");
        System.out.print ("  " + unit2.mana + "/" + unit2.maxMana + "MP     ");
        System.out.print ("  " + unit3.mana + "/" + unit3.maxMana + "MP     ");
        System.out.print ("  " + unit4.mana + "/" + unit4.maxMana + "MP     ");
            
        System.out.println ("");
        
    }
    public static void displayHealth(stats unit){
        if (unit.role == "Warrior"){
            System.out.print ("  " + unit.health + "/" + unit.maxHealth + "HP ");
        }
        else{
            System.out.print ("  " + unit.health + "/" + unit.maxHealth + "HP   ");
        }
    }
    
    public static int chooseTarget(){   
        Scanner s = new Scanner(System.in);
        int target = s.nextInt();
        return target;
    }
    
    public static int enemyChooseTarget(stats[] unit, int dead){
        //40 30 20 10
        //45 30 15 10
        //50 35 15
        //70 30
        //100
        int countedDead = 0;
        for (int i = 0; i < 4; i++){
            if (unit[i].Taunt == true){
                System.out.println ("tauntnotunt?");
                return i;
            }
        }
        if (dead == 0){
            int test = (int)(Math.random() * 19 + 1);
//            if (9 >= (int)(Math.random() * 19 + 1)){
          //  System.out.println(test);
            if (9 >= test){
                return 0;        
            }
            if (6 >= (int)(Math.random() * 10 + 1) ){
                return 1;
            }
             if (3 >= (int)(Math.random() * 4 + 1) ){
                return 2;
            }
            return 3;            
        }
        
        if (dead == 1){
            
            if (unit[countedDead].health == 0){
                countedDead += 1;
            }
            
            if (1 >= (int)(Math.random() * 1 + 1)){ //50%
                return countedDead;
            }
            
            countedDead += 1; // moves to next target
            if (unit[countedDead].health == 0){
                countedDead += 1;
            }
            
            if (7 >= (int)(Math.random() * 9 + 1) ){ //35% but written 70% for first missed
                return countedDead;
            }
            
            countedDead += 1; // moves to next target
            if (unit[countedDead].health == 0){
                countedDead += 1;
            }
            
            return countedDead;            // if first 2 not hit, this gets hit
        }

        if (dead == 2){
            
            if (unit[countedDead].health == 0){
                countedDead += 1;
            }
            
            if (7 >= (int)(Math.random() * 9 + 1)){ // 70% for first unit
                return countedDead;        
            }
            
            countedDead += 1;
            if (unit[countedDead].health == 0){
                countedDead += 1;
            }
            
            return countedDead;            
        }
        
        if (dead == 3){
            for (int i = 0; i < 4; i++){ //only 1 target, finds the last target and targets him/her
                if (unit[i].health > 0){
                    return i;
                }
            }
        }
        return 0;

        
    }
    
    public static int rogueChooseTarget(stats[] unit, int dead){
        //40 30 20 10
        //45 30 15 10
        //50 35 15
        //70 30
        //100
        int countedDead = 3;
        if (dead == 0){
            int test = (int)(Math.random() * 19 + 1);
//            if (9 >= (int)(Math.random() * 19 + 1)){
          //  System.out.println(test);
            if (9 >= test){
                return 3;        
            }
            if (6 >= (int)(Math.random() * 10 + 1) ){
                return 2;
            }
             if (3 >= (int)(Math.random() * 4 + 1) ){
                return 1;
            }
            return 0;            
        }
        
        if (dead == 1){
            
            if (unit[countedDead].health == 0){
                countedDead -= 1;
            }
            
            if (1 >= (int)(Math.random() * 1 + 1)){ //50%
                return countedDead;
            }
            
            countedDead -= 1; // moves to next target
            if (unit[countedDead].health == 0){
                countedDead -= 1;
            }
            
            if (7 >= (int)(Math.random() * 9 + 1) ){ //35% but written 70% for first missed
                return countedDead;
            }
            
            countedDead -= 1; // moves to next target
            if (unit[countedDead].health == 0){
                countedDead -= 1;
            }
            
            return countedDead;            // if first 2 not hit, this gets hit
        }

        if (dead == 2){
            
            if (unit[countedDead].health == 0){
                countedDead -= 1;
            }
            
            if (7 >= (int)(Math.random() * 9 + 1)){ // 70% for first unit
                return countedDead;        
            }
            
            countedDead -= 1;
            if (unit[countedDead].health == 0){
                countedDead -= 1;
            }
            
            return countedDead;            
        }
        
        if (dead == 3){
            for (int i = 0; i < 4; i++){ //only 1 target, finds the last target and targets him/her
                if (unit[i].health > 0){
                    return i;
                }
            }
        }
        return 0;   
    } // end 
    
    public static stats[] organiseEnemy(stats[] enemy){
        stats tempSwap = new stats();
        for(int k = 0; k < 5; k++){ 
            for (int i = 0; i < 5; i++){
                if (enemy[i].health <= 0){
                    tempSwap = enemy[i];
                    enemy[i] = enemy[i + 1];
                    enemy[i + 1] = tempSwap;
                }
            }
        }
        return enemy;
    }
}
