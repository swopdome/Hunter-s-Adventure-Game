package hunter.s.adventure.game;
import java.util.ArrayList;
public class HunterAdventureGame {
    public static void main(String args[]){
        inteface_game UI = new inteface_game();                
        ArrayList<Player> slots = new ArrayList();
        Player player = null;
        State state = null;
        Home homeTown=null;
        boolean playing = true;
        boolean SlotIsNull=true;
        boolean selectedSlot=false;
        String input,temp;
        
        UI.startGame();
        
        input=UI.enter.nextLine();
        temp=input;    
        while(playing){
            while(SlotIsNull&&playing){
                selectedSlot=false;
                UI.mainMenu(slots.size());
                input=UI.enter.nextLine();
                switch(input){
                    case"1":
                        boolean check;
                        //Create new Game :name , stat ,weapon
                        String name=null;
                        System.out.print(">Create New Slot\nEnter Name:");
                        do{
                            input=UI.resistNaming();
                            check=UI.checkName(input,slots);
                            if(check){
                                System.out.println("[System]name \""+input+"\" already exist");
                            }else{
                                name=input;
                            }
                        }while(check);
                        boolean TypeWeaponUnable=true;
                        int weaponType=0;
                        UI.chooseWeapon();
                        do{
                            System.out.print("choose:");
                            input=UI.enter.nextLine();
                            switch(input){
                                case"1":case"2":case"3":
                                    if(UI.StringToNum(input)){
                                        weaponType=UI.getNum();
                                        TypeWeaponUnable=false;
                                    }
                            }
                            
                        }while(TypeWeaponUnable);
                        player=new Player(name,weaponType);
                        slots.add(player);
                    case"2":
                        if(slots.size()>0){
                            input=UI.selectSlot(slots);
                            if(input.equals("r")){
                                input=UI.removeSlot(slots);
                                if(UI.StringToNum(input)){
                                    int removeAt=UI.getNum()-1;
                                    boolean confirmDeleteSlot=false;
                                    System.out.print("[System]enter name for confirm :");
                                    input=UI.enter.nextLine();
                                    if(input.equals(slots.get(removeAt).getNAME())){
                                        confirmDeleteSlot=true;
                                    }else
                                        System.out.println("[System]delete Fail!");
                                    if(confirmDeleteSlot){
                                        System.out.println("[System]deleting slot at "+(removeAt+1)
                                                +" name:"+slots.get(removeAt).getNAME());
                                        slots.remove(removeAt);
                                        System.out.println("[System]slot deleted.");
                                    }
                                }
                                System.out.println("[System]returning to Main Menu");
                            }else if(UI.StringToNum(input)){
                                player=slots.get(UI.getNum()-1);
                                homeTown=new Home(player);
                                SlotIsNull=false;
                                selectedSlot=true;
                            }  
                        }else{
                            System.out.println("[System]please press 1 for New game.");
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
            while(selectedSlot&&stateIsNull&&homeTown!=null&&player!=null){
                input=homeTown.Lobby();
                switch(input){
                    case"0":homeTown.Training_place();
                        break;
                    case"1":homeTown.Refill();
                        break;
                    case"2":homeTown.Upgrade();
                        break;
                    case"3":homeTown.ChangeWeapon();
                        break;
                    case"4":input=UI.chooseState(player.getStates());
                        if(UI.StringToNum(input)){
                            stateAt=UI.getNum();
                            stateSelected=true;
                            stateIsNull=false;
                        }
                        break;
                    case"b":
                        SlotIsNull=true;
                        selectedSlot=false;
                        break;
                }
            }
            boolean confirmState=false;
            while(stateSelected){
                input = UI.sureState(stateAt);
                switch(input){
                    case"1":
                        System.out.println(stateAt);
                        state = new State(stateAt-1,player);
                        confirmState=true;
                        stateSelected=false;
                        break;
                    case"b":
                        stateSelected=false;
                        System.out.println("Returning to Home");
                }
            }
            boolean alive=true;
            while(confirmState&&state!=null&&player!=null){
                int hp=player.getHP();
                for(int waveAt=0;waveAt<state.getAmountWave()&&alive&&state.getBattle();waveAt++){
                    alive=state.wave(waveAt,hp);
                    hp=state.getHp();
                }
                state.Result(alive);
                confirmState=false;
            }
        }
        UI.exit(slots);
    }
}
