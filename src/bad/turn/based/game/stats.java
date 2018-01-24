/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bad.turn.based.game;
import java.lang.Math;
/**
 *
 * @author jason
 */
public class stats {
//    private string :
    public String role;  // shows role
    public boolean Taunt = false;
    public int strength; // attack stat
    public int defense; // defense stat
    public int defaultDefense; // remembers default defense
    public int maxHealth; // maintains max health
    public int maxMana; //maintains max mana
    public int health; // health amount 
    public int accuracy; // +- damage
    public int evasion; // chance to avoid attack
    public int defaultEvasion; // resets
    public int mana;    // amount fo times spells can be used
    public int INT;     // magic damage
    
    public void displayStats(){
        System.out.println ("-----------------------------------------");
        System.out.println ("Role : " + role);
        System.out.println ("HP: " + health);
        System.out.println ("MP: " + mana);
        System.out.println ("STR: " + strength);
        System.out.println ("INT: " + INT);
        System.out.println ("DEF: " + defense);
        System.out.println ("ACC: " + accuracy);
        System.out.println ("EVA: " + evasion);
        System.out.println ("");
    }
    public void hit(int attackerDamage){
        int prevHealth = this.health;
        int damage = (attackerDamage - this.defense);
        if (damage <= 0){
            damage = 1;
        }
        int avoid = (int) (Math.random() * 9 + 1);                    
        
        if (avoid <= this.evasion){
            System.out.println ("The target dodged the attack");
        }
        else{
            this.health = this.health - damage;
            System.out.println (prevHealth + " ---> " + this.health);
        }
    }
    
    public void magicHit(int attackerDamage){
        int prevHealth = this.health;
        this.health = this.health - attackerDamage;
        System.out.println (prevHealth + " ---> " + this.health);
    }
    
    public int magicAttack(){
        int damage = this.INT;
        this.mana -= 1;
        return damage;
    }
    
    public void heal(int healAmount){ //healamount = users int
        this.health += healAmount; // heals targets hp
        if (this.health > this.maxHealth){
            this.health = maxHealth; //sets to max hp is overflowed past
        }
    }
    
    public void evasionBuff(){
        this.evasion += 5;
        System.out.println ("The unit " + this.role + " evasion increased by 50%");
        this.mana -= 1; 
    }
    
    public void manaRegen (){
//        if (this.role == "Healer" || this.role == "Mage"){
//            this.mana += 2;
//        }
//        else{
//            this.mana += 1;
//        }
        this.mana +=2;
        if (this.mana > this.maxMana){
            this.mana = maxMana; //sets to max hp is overflowed past
        }
    }
    
    public void Evade () {
        this.evasion += 2;
    }
    
    public void resetEvasion (){
        this.evasion = this.defaultEvasion;
    }
    
    public int attack (){
        //int acc = (int ) (Math.random * (2 * attackerAcc) + 1);
        int damage = this.strength + (int) (Math.random() * this.accuracy * 2) - this.accuracy;// + this.accuracy;
        return damage;
    }
    
    public void createStats(int classes, boolean enemy){
        switch (classes){
            case 1: 
//                (role == 1){ // tank
                role = "Warrior";
                strength = 15;
                defense = 4;
                health = 120;                
                maxHealth = health;
                accuracy = 4;
                evasion = 1;
                defaultEvasion = evasion; 
                mana = 3;
                maxMana = mana;
                if (enemy == true) {
                    strength = 9;
                    defense = 4;
                    health = 90;                
                    maxHealth = health;
                    accuracy = 2;
                }
                break;
        
//        else if (role == 2){ //archer
            case 2:
                role = "Archer";
                strength = 20;
                defense = 1;
                health = 70;
                maxHealth = health;
                accuracy = 6;
                evasion = 3;
                defaultEvasion = evasion; 
                mana = 4;
                maxMana = mana;                
                if (enemy == true) {
                    strength = 12;
                    defense = 0;
                    health = 75;
                    maxHealth = health;
                    accuracy = 5;
                    evasion = 2;
                    defaultEvasion = evasion;
                }
                break;
        
        //else if (role == 3){ // healer
            case 3:
                role = "Healer";
                strength = 10;
                defense = 3;
                health = 80;
                maxHealth = health;
                accuracy = 5;
                evasion = 1;
                defaultEvasion = evasion;
                mana = 8;
                maxMana = mana;
                INT = 25;                
                if (enemy == true) {
                    strength = 9; //enemy healer becomes a rogue instead since they cant heal
                    defense = 2;
                    health = 60;                  
                    maxHealth = health;
                    accuracy = 10;
                    evasion = 5;
                    defaultEvasion = evasion;                    
                    mana = 5;
                    maxMana = mana;
                }
                break;
        
        //else if (role == 4){ // mage
            case 4:
                role = "Mage";
                strength = 8;
                health = 55;
                maxHealth = health;
                accuracy = 1;
                evasion = 1;
                defaultEvasion = evasion;
                mana = 8;
                maxMana = mana;
                INT = 25;
                if (enemy == true) {
                    strength = 8; // always uses magic attack
                    defense = 1;
                    health = 45;
                    maxHealth = health;
                    accuracy = 1;
                    mana = 8;
                    maxMana = mana;
                    INT = 18; 
                }
                break;
            case 5:
                role = "Boss";
                if (enemy == true) {
                    strength = 37; 
                    defense = 5;
                    health = 300;
                    maxHealth = health;
                    evasion = 0;
                    accuracy = 5;
                    mana = 8;
                    maxMana = mana;
                    INT = 18; 
                }
                break;
            default:
                System.out.println("rip");
                
        }
    }
}
