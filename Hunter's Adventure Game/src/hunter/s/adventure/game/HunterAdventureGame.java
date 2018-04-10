package hunter.s.adventure.game;
import java.util.ArrayList;
public class HunterAdventureGame {
    public static void main(String args[]){
      Tools_pack UI = new Tools_pack();
        ArrayList<Player> slots = new ArrayList(); 
        Player player = new Player();
        State state = new State();
        NPC Jonathan = new NPC(1);
        NPC Yharnum  = new NPC(2);
        NPC Gabriel  = new NPC(3);
        Home homeE;
        boolean playing = true;
        boolean SlotIsNull=true;
        boolean selectedSlot=false;
        String input,temp;
 
        UI.startGame();
        input=UI.enter.nextLine();
        temp=input;    
        //playing 
        while(playing){
            //choose slot 
            //pass
            while(SlotIsNull&&playing){
                selectedSlot=false;
                UI.mainMenu(slots.size());
                input=UI.enter.nextLine();
            
                switch(input){
                    case"1":
                        System.out.println("------Create New Game------");
                        boolean check;
                        do{
                           
                            System.out.print("Create your name first\nName :");
                            input=UI.enter.nextLine();
                            check=UI.checkName(input,slots);
                            if(check){
                                System.out.println("Name \""+input+"\" already exist");
                            }
                        }while(check);
                        player=new Player(input);
                        slots.add(player);
                    case"2":
                        if(slots.size()>0){
                            input=UI.selectSlot(slots);
                            if(UI.StringToNum(input)){
                                player=slots.get(UI.getNum()-1);
                                SlotIsNull=false;
                                selectedSlot=true;
                                System.out.println(player.getName());
                            }
                            else
                                SlotIsNull=true;
                                
                        }else{
                            System.out.println("Please press 1 for New game.");
                            break;
                    }
                    break;
                    case"0":playing=false;
                    break;    
                }
            }
            
            boolean stateSelected=false;
            boolean stateIsNull=true;
            int stateAt=0;
            
            //Seting home && selectMap
            while(selectedSlot&&stateIsNull){
                //home
                homeE=new Home(Jonathan,Yharnum,Gabriel);
                input=UI.homeTown();
                switch(input){
                    case"1"://weapon
                        homeE.weaponShop();
                        break;
                    case"2"://Potions and grenade
                        homeE.utilityShop();
                        break;
                    case"3"://upstat
                        homeE.upStatus();
                        break;
                    case"4"://select state
                        //clear
                        input=UI.chooseState(player.getStates());
                        if(UI.StringToNum(input)){
                            stateAt=UI.getNum();
                            stateSelected=true;
                            stateIsNull=false;
                        }
                        break;
                    case"b"://main menu
                        //clear
                        SlotIsNull=true;
                        selectedSlot=false;
                        break;
                }
            }
            
            boolean confirmState=false;
            
            //confirmslot
            while(stateSelected){
                input = UI.sureState(stateAt);
                switch(input){
                    case"1":
                        //state
                        //not clearly
                        state = new State(stateAt);
                        confirmState=true;
                        stateSelected=false;
                        break;
                    case"b":
                        SlotIsNull=true;
                        //home
                        
                }
            }
            
            boolean completeState=false;
            boolean won=false;
            
            //startslot
            while(confirmState){
                //waveing
                //neeeddddd it  nowwwwwwwww
            }
            
            if(completeState){
                state.result(won);
                player.setGold(state.getG());
                player.setExp(state.getXp());
            }
            state.resetState();
            //set new Coin and exp of player
        }
        UI.exit(slots);
    }
    
}