package ac.liv.csc.comp201;

import ac.liv.csc.comp201.model.IMachine;
import ac.liv.csc.comp201.model.IMachineController;
import ac.liv.csc.comp201.simulate.Cup;
import ac.liv.csc.comp201.simulate.Hoppers;
import java.math.BigDecimal;
//
public class MachineController extends Thread implements IMachineController {
//This code is added by YifanWei
    private boolean running = true;
    private IMachine machine;
    private static final String version = "1.22";
    private final double upperTemprature = 74;//the upper temprature the machine could have
    private final double lowerTemprature = 70;//the lower temprature the machine could have
    private final double saleTemprature = 97;//the lower temprature when the maching need to prepare drink
    private final double saleTempratureU = 99;//the lower temprature when the maching need to prepare drink
    private final double CCsaleTemprature = 91;//the lower temprature when the maching need to prepare drink
    private final double CCsaleTempratureU = 94;//the lower temprature when the maching need to prepare drink
    private boolean onSelling = false;//on selling ensure the water tempurture high
    private float summoney = 0f;
    String UserInputTemp = "";
    boolean prepareCup=false;
    boolean making = false;
    boolean error1 = false;//used to check the wrong input
    boolean error2 = false;//used to check the wrong input
    boolean error3 = false;//powder enough
    boolean error4 = false;//enough money
    float mtemp = 0;
    double v = Cup.SMALL;
    boolean fc = false;//coffee hopper on/off
    boolean fm = false;//milk hopper on/off
    boolean fs = false;//sugar hopper on/off
    boolean fch = false;//chocolate hopper on/off
    private boolean addPowder = false;//add powder
    private boolean addWater = false;//add water
    private boolean finish = false;// finish and set all the variable to inicial valut to prepare for the next cup
    private double makeTemp = 95.9;//temprature make the drink
    private double ChocolateMakeTemp = 91;
    private double cupFullRate = 1;//the cup full rate
    private String UserInputTemp2 = "";//to store the user input
    private boolean startMake = false;
    private boolean setPowder = false;//to ensure set on all the hopper needed once
    private int step = 0;//to ensure add powder first then add water
    private boolean remainMoney=true;
    double temprature_tolerance=0.02;
    boolean cho=false;
    
    public void DealWith3DigitInput(){
        switch (UserInputTemp){
            case "101":
                UserInputTemp="4"+UserInputTemp;
                machine.getDisplay().setTextString("Current coin:" + summoney+" Input:"+UserInputTemp);
                break;
            case "102":
                UserInputTemp="4"+UserInputTemp;
                machine.getDisplay().setTextString("Current coin:" + summoney+" Input:"+UserInputTemp);
                break;
            case "201":
                UserInputTemp="4"+UserInputTemp;
                machine.getDisplay().setTextString("Current coin:" + summoney+" Input:"+UserInputTemp);
                break;
            case "202":
                UserInputTemp="4"+UserInputTemp;
                machine.getDisplay().setTextString("Current coin:" + summoney+" Input:"+UserInputTemp);
                break;
            case "300":
                UserInputTemp="4"+UserInputTemp;
                machine.getDisplay().setTextString("Current coin:" + summoney+" Input:"+UserInputTemp);
                break;
        }
    }
    public boolean CheckMachineMoney(float m){
        //float moneyRemain;
        //moneyRemain+=machine.getCoinHopperLevel(String coinCode);
        machine=this.machine;
        remainMoney=true;
        float moneyNeedChange=summoney-m;
        int a100=machine.getCoinHandler().getCoinHopperLevel("ef");
        int a50=machine.getCoinHandler().getCoinHopperLevel("bd");
        int a20=machine.getCoinHandler().getCoinHopperLevel("bc");
        int a10=machine.getCoinHandler().getCoinHopperLevel("ba");
        int a5=machine.getCoinHandler().getCoinHopperLevel("ac");
        int a1=machine.getCoinHandler().getCoinHopperLevel("ab");
        
        while (moneyNeedChange >= 1 && a100>=1) {
            moneyNeedChange -= 1;
            a100 -= 1;
            moneyNeedChange = eliminateExceptionValue(moneyNeedChange);
        }
        while (moneyNeedChange >= 0.5 && a50>=1) {
            moneyNeedChange -= 0.5;
            a50 -= 1;
            moneyNeedChange = eliminateExceptionValue(moneyNeedChange);
        }
        while (moneyNeedChange >= 0.2 && a20>=1) {
            moneyNeedChange -= 0.2;
            a20 -= 1;
            moneyNeedChange = eliminateExceptionValue(moneyNeedChange);
        }
        while (moneyNeedChange >= 0.1 && a10>=1) {
            moneyNeedChange -= 0.1;
            a10 -= 1;
            moneyNeedChange = eliminateExceptionValue(moneyNeedChange);
        }
        while (moneyNeedChange >= 0.05 && a5>=1) {
            moneyNeedChange -= 0.05;
            a5 -= 1;
            moneyNeedChange = eliminateExceptionValue(moneyNeedChange);
        }
        while (moneyNeedChange >= 0.01 && a1>=1) {
            moneyNeedChange -= 0.01;
            a1 -= 1;
            moneyNeedChange = eliminateExceptionValue(moneyNeedChange);
        }
        if(moneyNeedChange==0){
            return true;
        }
        machine.getDisplay().setTextString("Sorry, not enough money in the machine");
        return false;
    }
    public void HeatersControl() {
        //This code is added by YifanWei
        System.out.println("HeatersControl");
        if (!onSelling) {
            System.out.println("HeatersControl normal");
            if (machine.getWaterHeater().getTemperatureDegreesC() <= lowerTemprature) {
                machine.getWaterHeater().setHeaterOn();
            }
            if (machine.getWaterHeater().getTemperatureDegreesC() >= upperTemprature) {
                machine.getWaterHeater().setHeaterOff();//turn off if the temprature is high enough
            }
            if (machine.getWaterHeater().getTemperatureDegreesC() >= 100) {
                machine.getDisplay().setTextString("Heated Over Control System Down!");//out put error message
                Change();//return the input money if any
                machine.shutMachineDown();//shut down if temprature reach 100
            }
        }
        if (onSelling) {
            System.out.println("HeatersControl sell");
            if (cho) {
                if (onSelling && machine.getWaterHeater().getTemperatureDegreesC() < CCsaleTemprature) {
                    System.out.println("HeatersControl sell on");
                    machine.getWaterHeater().setHeaterOn();//heat the water to ensure prepare as soon as possible
                    //System.currentTimeMillis();
                }
                if (machine.getWaterHeater().getTemperatureDegreesC() >= CCsaleTempratureU) {
                    System.out.println("HeatersControl offl");
                    machine.getWaterHeater().setHeaterOff();//turn off if the temprature is high enough
                }
                if (machine.getWaterHeater().getTemperatureDegreesC() >= 100) {
                    machine.getDisplay().setTextString("Heated Over Control System Down!");//out put error message
                    Change();//return the input money if any
                    machine.shutMachineDown();//shut down if temprature reach 100
                }
            } else {
                if (onSelling && (machine.getWaterHeater().getTemperatureDegreesC() < saleTemprature)) {
                    System.out.println("HeatersControl sell on");
                    machine.getWaterHeater().setHeaterOn();//heat the water to ensure prepare as soon as possible
                    //System.currentTimeMillis();
                }
                if (machine.getWaterHeater().getTemperatureDegreesC() >= saleTempratureU) {
                    System.out.println("HeatersControl offl");
                    machine.getWaterHeater().setHeaterOff();//turn off if the temprature is high enough
                }
                if (machine.getWaterHeater().getTemperatureDegreesC() >= 100) {
                    machine.getDisplay().setTextString("Heated Over Control System Down!");//out put error message
                    Change();//return the input money if any
                    machine.shutMachineDown();//shut down if temprature reach 100
                }
            }
        }
    }
    public boolean CheckMakeable(Cup cup) {
        boolean Powder = false;
        boolean Input1 = false;
        boolean Input2 = false;
        mtemp = 0;
        if(!remainMoney){
            machine.getDisplay().setTextString("Not enough money in this machine");
            Change();
            return false;
        }
        switch (UserInputTemp.substring(0, 1)) {
            case "4":
                //small
                //check small cup in story id enough or not
//                    if(machine.getCup()){
//                        break;
//                    }
                Input1 = true;
                break;
            case "5":
                //medium
                mtemp += 0.2f;
                v = Cup.MEDIUM;//set v to medium cup size
                Input1 = true;
                break;
            case "6":
                //large
                v = Cup.LARGE;//set v to large cup size
                mtemp += 0.25f;
                Input1 = true;
                break;
        }
        switch (UserInputTemp.substring(1, 4)) {
            case "101":
                //Black coffee powder amount checking
                mtemp += 1.2f;
                Input2 = true;
                if (machine.getHoppers().getHopperLevelsGrams(0) >= 2 / Cup.SMALL * v) {
                    Powder = true;
                }
                break;
            case "102":
                //Black coffee with suger powder amount checking
                mtemp += 1.3f;
                Input2 = true;
                if (machine.getHoppers().getHopperLevelsGrams(0) >= 2 / Cup.SMALL * v || machine.getHoppers().getHopperLevelsGrams(2) >= 5 / Cup.SMALL * v) {
                    Powder = true;
                }
                break;
            case "201":
                //White coffee powder amount checking
                mtemp += 1.2f;
                Input2 = true;
                if (machine.getHoppers().getHopperLevelsGrams(0) >= 2 / Cup.SMALL * v || machine.getHoppers().getHopperLevelsGrams(1) >= 3 / Cup.SMALL * v) {
                    Powder = true;
                }
                break;
            case "202":
                //White coffee with suger powder amount checking
                mtemp += 1.3f;
                Input2 = true;
                if (machine.getHoppers().getHopperLevelsGrams(0) >= 2 / Cup.SMALL * v || machine.getHoppers().getHopperLevelsGrams(2) >= 5 / Cup.SMALL * v || machine.getHoppers().getHopperLevelsGrams(1) >= 3 / cup.SMALL * v) {
                    Powder = true;
                }
                break;
            case "300":
                //Hot chocolate powder amount checking
                mtemp += 1.1f;
                Input2 = true;
                if (machine.getHoppers().getHopperLevelsGrams(3) >= 28 / Cup.SMALL * v) {
                    Powder = true;
                }
                break;
        }
        if (Input1 == false || Input2 == false) {
            machine.getDisplay().setTextString("Error input, money remain:" + summoney);
            return false;
        }
        if (summoney <= mtemp) {
            machine.getDisplay().setTextString("Not enough money:" + summoney);
            return false;
        }else if(summoney >= mtemp){
            if(!CheckMachineMoney(mtemp)){
                //System.out.println("HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH");
                return false;
            }
        }
        if (Powder == false) {
            machine.getDisplay().setTextString("Sold out,money remain:" + summoney);
            return false;
        }
        if (Input1 && Input2 && Powder) {
            machine.getDisplay().setTextString("Start making,money remain:" + summoney);
            onSelling = true;
            prepareCup = true;
            return true;
        }
        return false;
        //This code is added by YifanWei
    }
    public void prepareCup(Cup cup) {
        //prepare cup
        //This code is added by YifanWei
        switch (UserInputTemp2.substring(0, 1)) {
            case "4":
                //prepare small cup to machine
                machine.vendCup(Cup.SMALL_CUP);
                break;
            case "5":
                //medium
                machine.vendCup(Cup.MEDIUM_CUP);
                summoney -= 0.2f;//spend money
                machine.setBalance(machine.getBalance()-20);
                summoney = eliminateExceptionValue(summoney);
                break;
            case "6":
                //large
                machine.vendCup(Cup.LARGE_CUP);
                summoney -= 0.25f;//spend money
                machine.setBalance(machine.getBalance()-25);
                summoney = eliminateExceptionValue(summoney);
                break;
        }
        //prepare hopper
        switch (UserInputTemp2.substring(1, 4)) {
            case "101"://Make Black coffee
                summoney -= 1.2f;//spend money
                machine.setBalance(machine.getBalance()-120);
                summoney = eliminateExceptionValue(summoney);
                //=====================================================================
                //set hopper
                //machine.getHoppers().setHopperOn(Hoppers.COFFEE);
                break;
            case "102"://Make Black coffee with suger
                //=====================================================================
                //set hopper
                //machine.getHoppers().setHopperOn(Hoppers.COFFEE);
                //machine.getHoppers().setHopperOn(Hoppers.SUGAR);
                //=====================================================================
                summoney -= 1.3f;
                machine.setBalance(machine.getBalance()-130);
                summoney = eliminateExceptionValue(summoney);

                break;
            case "201"://Make White coffee
                //=====================================================================
                //set hopper
                //machine.getHoppers().setHopperOn(Hoppers.COFFEE);
                //machine.getHoppers().setHopperOn(Hoppers.MILK);
                //=====================================================================
                summoney -= 1.2f;
                machine.setBalance(machine.getBalance()-120);
                summoney = eliminateExceptionValue(summoney);

                break;
            case "202"://Make white coffee with suger
                //This code is added by YifanWei
                //=====================================================================
                //set hopper
                //machine.getHoppers().setHopperOn(Hoppers.COFFEE);
                //machine.getHoppers().setHopperOn(Hoppers.SUGAR);
                //machine.getHoppers().setHopperOn(Hoppers.MILK);
                //=====================================================================
                summoney -= 1.3f;
                machine.setBalance(machine.getBalance()-130);
                summoney = eliminateExceptionValue(summoney);

                break;
            case "300"://Make hot chocolate
                //=====================================================================
                //set hopper
                //machine.getHoppers().setHopperOn(Hoppers.CHOCOLATE);
                //=====================================================================
                summoney -= 1.1f;
                machine.setBalance(machine.getBalance()-110);
                summoney = eliminateExceptionValue(summoney);
                cho=true;
                break;
        }
        //machine.getDisplay().setTextString("Current coin:" + summoney);
        prepareCup = false;
        addPowder=true;
        addWater = true;
        setPowder=true;
        step=1;
    }
    public void setPowderOn(Cup cup){
        switch (UserInputTemp2.substring(1, 4)) {
            case "101"://Make Black coffee
                //=====================================================================
                //set hopper
                machine.getHoppers().setHopperOn(Hoppers.COFFEE);
                fc = true;
                //System.out.println("Start to add coffee");
                break;
            case "102"://Make Black coffee with suger
                //=====================================================================
                //set hopper
                machine.getHoppers().setHopperOn(Hoppers.COFFEE);
                machine.getHoppers().setHopperOn(Hoppers.SUGAR);
                fc = true;
                fs = true;
                break;
            case "201"://Make White coffee
                //=====================================================================
                //set hopper
                machine.getHoppers().setHopperOn(Hoppers.COFFEE);
                machine.getHoppers().setHopperOn(Hoppers.MILK);
                fc = true;
                fm = true;
                //=====================================================================
                break;
            case "202"://Make white coffee with suger
                //=====================================================================
                //set hopper
                machine.getHoppers().setHopperOn(Hoppers.COFFEE);
                machine.getHoppers().setHopperOn(Hoppers.SUGAR);
                machine.getHoppers().setHopperOn(Hoppers.MILK);
                fc = true;
                fm = true;
                fs = true;
                //=====================================================================
                break;
            case "300"://Make hot chocolate
                //This code is added by YifanWei
                //=====================================================================
                //set hopper
                machine.getHoppers().setHopperOn(Hoppers.CHOCOLATE);
                fch = true;
                //=====================================================================
                break;
        }
        setPowder=false;
        //System.out.println("setPowder=false");
    }
    public void AddPowder(Cup cup) {
        System.out.println("===================================Test powder========================================");
        //double coffee=2 / Cup.SMALL * v;
        //System.out.println(coffee);
        //double cc=cup.getCoffeeGrams();
        //System.out.println(cc);
        //This code is added by YifanWei
        if (cup.getCoffeeGrams() >= 2 / Cup.SMALL * v) {
            System.out.println("Stop Coffee");
            machine.getHoppers().setHopperOff(Hoppers.COFFEE);//set coffee hopper off if coffee is enough
            fc = false;
        }
        if (cup.getMilkGrams() >= 3 / Cup.SMALL * v) {
            machine.getHoppers().setHopperOff(Hoppers.MILK);//set milk hopper off if coffee is enough
            fm = false;
        }
        if (cup.getSugarGrams() >= 5 / Cup.SMALL * v) {
            machine.getHoppers().setHopperOff(Hoppers.SUGAR);//set sugar hopper off if coffee is enough
            fs = false;
        }
        if (cup.getChocolateGrams() >= 28 / Cup.SMALL * v) {
            machine.getHoppers().setHopperOff(Hoppers.CHOCOLATE);//set chocolate hopper off if coffee is enough
            fch = false;
        }
        //swich off hopper if needed  
        if (!fc && !fm && !fs && !fch) {
            addPowder = false;
            step=2;
        }
        if (addPowder) {
            machine.getHoppers().updateHoppers(cup);//add power
        }
    }
    public void TapControl(Cup cup) {
        //addWater = false;
        //This code is added by YifanWei
        //System.out.println("============88888888================");
        if (!onSelling) {
            return;
        }
        //double t=v * cupFullRate;
        //System.out.println(t);
        //System.out.println("===================================");
        
        if (cup.getWaterLevelLitres() >= v * cupFullRate) {
            addWater = false;
        }
        if (!addWater) {
            machine.getWaterHeater().setColdWaterTap(false);
            machine.getWaterHeater().setHotWaterTap(false);
        }
        if (addWater) {
//            boolean a = true;//flag
//            boolean b = true;//flag
            //cup.addWater(v * 0.2, saleTemprature);
            //cup.addWater(v * 0.8, 80);
            //addWater=false;
            //This code is by YifanWei
            //Hot water melt powder================================================
            if (cup.getWaterLevelLitres() <= v * 0.2) {
                machine.getWaterHeater().setHotWaterTap(true);
            }
            //Fill the cup=========================================================
            if (cup.getWaterLevelLitres() < v && cup.getWaterLevelLitres() >= v * 0.2) {
                if (cup.getTemperatureInC() >= 80 * (1 + temprature_tolerance)) {
                    machine.getWaterHeater().setColdWaterTap(true);
                    machine.getWaterHeater().setHotWaterTap(false);
                }
                if (cup.getTemperatureInC() <= 80 * (1 - temprature_tolerance)) {
                    machine.getWaterHeater().setColdWaterTap(false);
                    machine.getWaterHeater().setHotWaterTap(true);
                }
                if (cup.getWaterLevelLitres() == 0.9*v) {
                    machine.getWaterHeater().setColdWaterTap(false);
                    machine.getWaterHeater().setHotWaterTap(false);
                    addWater=false;
                }
            }
            machine.getWaterHeater().updateHeater(cup);
        }
        
    }
    public void Make(Cup cup) {
        //System.out.println("Make，PrepareCup"+prepareCup+"Degree enough:"+(machine.getWaterHeater().getTemperatureDegreesC()>= makeTemp));
        //making (prepare cup) 
        //If check accessed
        if (prepareCup) {
            //System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            prepareCup(cup);
        }
        //if (cup != null && machine.getWaterHeater().getTemperatureDegreesC() >= makeTemp)
        if(cup!=null&&startMake==true){
            System.out.println("prepare Add power and Tap control");
            if (setPowder) {
                setPowderOn(cup);//making Add power
                //System.out.println("Add powder");
                //This code is by YifanWei
            }
            if (step == 1) {
                AddPowder(cup);
            }
            if (step == 2) {
                //System.out.println("Tap control");
                TapControl(cup);//tap control
            }
//            if (addPowder) {
//                AddPowder(cup);//making Add power
//                //System.out.println("Add powder");
//            }
            
            
        }
//        if (!addPowder) {
//            machine.getDisplay().setTextString("Stop all powder" );
//            machine.getHoppers().setHopperOff(0);machine.getHoppers().setHopperOff(1);machine.getHoppers().setHopperOff(2);machine.getHoppers().setHopperOff(3);
//        }
//This code is by YifanWei
        if (!addPowder && !addWater) {
            machine.getDisplay().setTextString("Purchase Succeed, money remain:" + summoney);
            making = false;
            finish = true;
            UserInputTemp = "";
            onSelling = false;
            startMake=false;
            finish();
            step=0;
            cho=false;
        }
    }
    public void startController(IMachine machine) {
        this.machine = machine;				// Machine that is being controlled
        machine.getKeyPad().setCaption(4, "4");
        //machine.getKeyPad().setCaption(4, "4 Small");
        machine.getKeyPad().setCaption(5, "5 Medium");
        machine.getKeyPad().setCaption(6, "6 Large");
        //machine.getKeyPad().setCaption(8, "8 Take out Cup");
        machine.getKeyPad().setCaption(9, "Change");
//==========================================================================
//              This code is by YifanWei
//		machine.getKeyPad().setCaption(0,"Cup");
//		machine.getKeyPad().setCaption(1,"Water heater on");
//		machine.getKeyPad().setCaption(2,"Water heater off");		
//		machine.getKeyPad().setCaption(3,"Hot Water On");
//		machine.getKeyPad().setCaption(4,"Hot Water Off");		
//		machine.getKeyPad().setCaption(5,"Dispense coffee");
//		machine.getKeyPad().setCaption(6,"Dispense milk");
//		machine.getKeyPad().setCaption(7,"Cold water on");
//		machine.getKeyPad().setCaption(8,"Cold water off");
//==========================================================================
        super.start();
    } 
    public MachineController() {
    }
    public void CheckLockCoinH(){
        if(machine.getHoppers().getHopperLevelsGrams(0) < 2 &&
                machine.getHoppers().getHopperLevelsGrams(2) < 5 &&
                machine.getHoppers().getHopperLevelsGrams(1) < 3 &&
                machine.getHoppers().getHopperLevelsGrams(3) < 28 )
            {machine.getCoinHandler().lockCoinHandler();}
        if(machine.getHoppers().getHopperLevelsGrams(0) >= 2 ||
                machine.getHoppers().getHopperLevelsGrams(2) >= 5 ||
                machine.getHoppers().getHopperLevelsGrams(1) >= 3 ||
                machine.getHoppers().getHopperLevelsGrams(3) >= 28 )
            {machine.getCoinHandler().unlockCoinHandler();}
    }
    private synchronized void runStateMachine(){
        
        CheckLockCoinH();//lock if all the hopper is 0
        
        Cup cup = machine.getCup();
        //CheckMachineMoney();//check money remain in the machine
        //machine.vendCup(Cup.SMALL_CUP);
        //cup.getWaterLevelLitres();
        //This code is by YifanWei
        if (cup != null) {
            System.out.println("Water level is " + cup.getWaterLevelLitres() + " coffee is " + cup.getCoffeeGrams() + " g"+ " sugar is " + cup.getSugarGrams() + " g"+ " milk is " + cup.getMilkGrams() + " g"+ " chocolate is " +  cup.getChocolateGrams() + " grams" +
                    " Cup Temprature is"+cup.getTemperatureInC()+
                    "Heater temprature is "+machine.getWaterHeater().getTemperatureDegreesC());
        }
        System.out.println("onSelling:"+onSelling+" addPowder"+addPowder+" addWater"+addWater+" V:"+v+" making:"+making);
        int keyCode = machine.getKeyPad().getNextKeyCode();
        String coinCode = machine.getCoinHandler().getCoinKeyCode();
        if (coinCode != null) {
            switch (coinCode) {
                case "ab":
                    summoney += 0.01f;
                    machine.setBalance(machine.getBalance()+1);
                    break;
                case "ac":
                    summoney += 0.05f;
                    machine.setBalance(machine.getBalance()+5);
                    break;
                case "ba":
                    summoney += 0.1f;
                    machine.setBalance(machine.getBalance()+10);
                    break;
                case "bc":
                    summoney += 0.2f;
                    machine.setBalance(machine.getBalance()+20);
                    break;
                case "bd":
                    summoney += 0.5f;
                    machine.setBalance(machine.getBalance()+50);
                    break;
                case "ef":
                    summoney += 1;
                    machine.setBalance(machine.getBalance()+100);
                    break;
            }//Check money and counyt the 
            //This code is by YifanWei
            BigDecimal bd = new BigDecimal(summoney);
            summoney = bd.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
            machine.getDisplay().setTextString("Current coin:" + summoney);//This is used to floor the exception value
            //machine.getDisplay().setTextString("Current coin:"+machine.getCoinHandler().getCoinHopperLevel("ab"));
        }
        switch (keyCode) {
            case 0:
                UserInputTemp += "0";
                finish=false;
                machine.getDisplay().setTextString("Current coin:" + summoney+" Input:"+UserInputTemp);
                break;
            case 1:
                UserInputTemp += "1";
                finish = false;
                machine.getDisplay().setTextString("Current coin:" + summoney+" Input:"+UserInputTemp);
                break;
            case 2:
                UserInputTemp += "2";
                finish = false;
                machine.getDisplay().setTextString("Current coin:" + summoney+" Input:"+UserInputTemp);
                break;
                //This code is by YifanWei
            case 3:
                UserInputTemp += "3";
                finish = false;
                machine.getDisplay().setTextString("Current coin:" + summoney+" Input:"+UserInputTemp);
                break;
            case 4:
                UserInputTemp += "4";
                finish = false;
                machine.getDisplay().setTextString("Current coin:" + summoney+" Input:"+UserInputTemp);
                break;
            case 5:
                UserInputTemp += "5";
                finish = false;
                machine.getDisplay().setTextString("Current coin:" + summoney+" Input:"+UserInputTemp);
                break;
            case 6:
                UserInputTemp += "6";
                finish = false;
                machine.getDisplay().setTextString("Current coin:" + summoney+" Input:"+UserInputTemp);
                break;
            case 7:
                UserInputTemp += "7";
                finish = false;
                machine.getDisplay().setTextString("Current coin:" + summoney+" Input:"+UserInputTemp);
                break;
            case 8:
                UserInputTemp += "8";
                //machine.doVendCup();
                //This code is by YifanWei
                finish = false;
                machine.getDisplay().setTextString("Current coin:" + summoney+" Input:"+UserInputTemp);
                break;
            case 9:
                Change();
                machine.getDisplay().setTextString("Current coin:" + summoney);//This is used to floor the exception value
                //machine.getDisplay().setTextString("Current coin:"+machine.getCoinHandler().getCoinHopperLevel("ab"));
                UserInputTemp = "";
                onSelling=false;
                finish = false;
                break;
        }
        //Check 101 and so on
        DealWith3DigitInput();
        //===================================================================================
        //start making reach 95.5
        if (machine.getWaterHeater().getTemperatureDegreesC() >= makeTemp||(cho &&machine.getWaterHeater().getTemperatureDegreesC() >= ChocolateMakeTemp)) {
            startMake = true;
        }
//         if (machine.getWaterHeater().getTemperatureDegreesC() >= makeTemp) {
//            startMake = true;
//        }
        //===================================================================================
        //CheckMakeable
        if (UserInputTemp.length() >= 4 && making == false) {
            CheckMakeable(cup);
            UserInputTemp2 = UserInputTemp;
            UserInputTemp = "";
        }
        //===================================================================================
        //Heater control
        //This code is by YifanWei
        HeatersControl();
        //===================================================================================
        //make coffee
        if (onSelling==true ) {
            Make(cup);
        }
    }
    public float eliminateExceptionValue(float a) {
        BigDecimal bd = new BigDecimal(a);
        a = bd.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        return a;
        //This code is by YifanWei
    }
    public void Change() {
        //boolean a=machine.getCoinHandler().coinAvailable("ab");
        int coin1 = 0;
        int coin5 = 0;
        int coin10 = 0;
        int coin20 = 0;
        int coin50 = 0;
        int coin100 = 0;
        float a = summoney;
        while (a >= 1 && machine.getCoinHandler().coinAvailable("ef")) {
            a -= 1;
            a = eliminateExceptionValue(a);
            coin100++;
            machine.getCoinHandler().dispenseCoin("ef");
        }
        while (a >= 0.5 && machine.getCoinHandler().coinAvailable("bd")) {
            a -= 0.5;
            coin50++;
            a = eliminateExceptionValue(a);
            machine.getCoinHandler().dispenseCoin("bd");
            //This code is by YifanWei
        }
        while (a >= 0.2 && machine.getCoinHandler().coinAvailable("bc")) {
            a -= 0.2;
            coin20++;
            a = eliminateExceptionValue(a);
            machine.getCoinHandler().dispenseCoin("bc");
        }
        while (a >= 0.1 && machine.getCoinHandler().coinAvailable("ba")) {
            a -= 0.1;
            coin10++;
            a = eliminateExceptionValue(a);
            machine.getCoinHandler().dispenseCoin("ba");
        }
        while (a >= 0.05 && machine.getCoinHandler().coinAvailable("ac")) {
            a -= 0.05;
            coin5++;
            a = eliminateExceptionValue(a);
            machine.getCoinHandler().dispenseCoin("ac");
        }
        while (a >= 0.01 && machine.getCoinHandler().coinAvailable("ab")) {
            a -= 0.01;
            coin1++;
            a = eliminateExceptionValue(a);
            machine.getCoinHandler().dispenseCoin("ab");
        }
        //=================================================================================
        //display the money on the plate
        //=================================================================================
        summoney = 0;
        machine.setBalance(0);

    }
    public void finish(){
        //This code is by YifanWei
        onSelling = false;//on selling ensure the water tempurture high
        UserInputTemp = "";
        prepareCup = false;
        making = false;
        error1 = false;//used to check the wrong input
        error2 = false;//used to check the wrong input
        error3 = false;//powder enough
        error4 = false;//enough money
        mtemp = 0;
        v = Cup.SMALL;
        fc = false;
        fm = false;
        fs = false;
        fch = false;
        addPowder = false;
        addWater = false;
        finish = false;
        cho=false;
    }
    public void run() {
        // Controlling thread for coffee machine
        int counter = 1;
        while (running) {
            //machine.getDisplay().setTextString("Running drink machine controller "+counter);
            counter++;
            try {
                Thread.sleep(10);		// Set this delay time to lower rate if you want to increase the rate
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            runStateMachine();
            //This code is by YifanWei
        }
    }
    public void updateController() {
        //runStateMachine();
    }
    public void stopController() {
        running = false;
    }
}
