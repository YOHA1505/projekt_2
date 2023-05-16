package Projekt_2;
import java.util.Scanner;

public class Projekt_2 
{
    static Scanner buss = new Scanner(System.in);
    static int[] platser = new int[21];
    static String[] platsernamn = new String[21];
    static String[] platserkön = new String[21];
    public static void main(String[] args)
    {
        while(true)
        {   
            try
            {
                //Meny 
                int meny = Menu();
                switch(meny)
                {
                    case 1:
                        Bokningsystem();
                        continue;
                    case 2: 
                        Ledigaplatser();
                        continue;
                    case 3:
                        System.out.println("Den totala vinsten är, "+Vinst(0)+"kr");
                        continue;
                    case 4:
                        Avbokning();
                        continue;
                    case 5:
                        hittabokning();
                        continue;
                    case 6:
                        listabokningar();
                        continue;
                    case 7:
                        break;
                }
            }
            catch(Exception ex)
            {
                continue;
            }
            break;
        }
    }

    static int Menu()
    {
        String startmeny = """
            Meny:
                [1] Boka plats
                [2] Antal lediga platser
                [3] Vinst
                [4] Avboka plats
                [5] Hitta plats
                [6] Lista Bokningar
                [7] Avsluta program
                """;
        System.out.println(startmeny);
        int meny = 0;
        try
        {
            meny = buss.nextInt();
            buss.nextLine();
        }
        catch(Exception ex)
        {
            buss.nextLine();
            felinmatning();
            
            return -1;//-1 betyder att koden failade och man måste börja om
        }

        return meny;
    }
    //Metod för att boka plats
    static int Bokningsystem()
    {
        System.out.println("Ange ditt namn: ");
        String namn = buss.nextLine();
        System.out.println("Ange ditt kön: ");
        String kön = buss.nextLine();

        int reverse = prsnrctrl();

        String platservisual = PlatsMapping();

        System.out.println(platservisual);
        int book = buss.nextInt();
                

        for(int i = book-1; i < platser.length; i++)
        {
            if(platser[i]==0)
            {
                platser[i] = reverse;
                platsernamn[i] = namn;
                platserkön[i] = kön;
                System.out.println("Plats "+book+" är bokad för "+namn+", "+kön+", "+reverse);
                break;
            }
            else
            {
                System.out.println("Platsen är redan bokad. Ange en ny plats som du vill boka: ");
                int nyplats = buss.nextInt();
                book = nyplats;
                platser[book-1] = reverse;
                continue;
            }
        }
        return platser[book-1];
    }
    //Metod för att beräkna lediga platser
    static void Ledigaplatser()
    {
        int lediga_platser = 0;
        for(int i = 0; i < platser.length; i++)
        {
            if(platser[i]==0)
            {
                lediga_platser++;
            }
        }
        System.out.println("Det finns "+lediga_platser+" lediga platser kvar.");
    }
    //Metod för att beräkna vinst
    static double Vinst(int index)
    {
        if(index==21)return 0;
        
        int prsnr = platser[index];

        if(prsnr == 0)return 0 + Vinst(index + 1);

        String årStr = Integer.toString(prsnr).substring(0, 4);
        int år = Integer.parseInt(årStr);

        double pris = 0;
        //Pris för vuxen
        if(år<=2005 && år>1963)pris=299.9;
        //Pris för barn
        else if(år>2005)pris=149.9;
        //Pris för pensionär
        else if(år<=1963)pris=199.9;
        return pris + Vinst(index + 1);
    }
    //Metod för att avboka plats
    static void Avbokning()
    {
        System.out.println("Vill du avboka med hjälp av personnummer eller namn?");
        String avbok = buss.nextLine();

        if(avbok.equals("namn")||avbok.equals("Namn"))
        {
            System.out.println("Vänligen ange det namn som finns på bokningen: ");
            String namn = buss.nextLine();
            
            for(int i = 0; i<platsernamn.length; i++)
            {
                if(platsernamn[i].equals(namn))
                {
                    System.out.println("Är du säker på att du vill avboka din plats?");
                    System.out.println("               [Ja]    [Nej]                ");
                    String val = buss.nextLine();
                    if(val.equals("Ja") || val.equals("ja"))
                    {
                        platsernamn[i] = "";
                        platser[i] = 0;
                        platserkön[i] = "";
                        System.out.println("Din plats har nu avbokats!");
                        break;
                    }
                    else if(val.equals("Nej") || val.equals("nej"))
                    {
                        break;
                    }
                }
                else if(platsernamn[20]=="")
                {
                    System.out.println("Det angivna personnummret är inte sparat vid en plats. Vänligen boka en plats först.");
                    break;
                }
                else
                {
                    continue;
                }
            }
        }
        else if(avbok.equals("Personnummer")||avbok.equals("personnummer"))
        {
            int reverse = prsnrctrl();
            int i = 0;
            for(i = 0; i<platser.length; i++)
            {
                if(platser[i]==reverse)
                {
                    System.out.println("Är du säker på att du vill avboka din plats?");
                    System.out.println("               [Ja]    [Nej]                ");
                    String val = buss.nextLine();
                    if(val.equals("Ja") || val.equals("ja"))
                    {
                        platser[i] = 0;
                        platsernamn[i] = "";
                        platserkön[i] = "";
                        System.out.println("Din plats har nu avbokats!");
                        break;
                    }
                    else if(val.equals("Nej") || val.equals("nej"))
                    {
                        break;
                    }
                }
                else if(platser[20]==0)
                {
                    System.out.println("Det angivna personnummret är inte sparat vid en plats. Vänligen boka en plats först.");
                    break;
                }
                else
                {
                    continue;
                }
            }   
        }
    }
    //Metod för att hitta bokning
    static void hittabokning()
    {
        System.out.println("Vill du hitta din bokning med hjälp av namn eller personnummer?");
        String hittaval = buss.nextLine();

        if(hittaval.equalsIgnoreCase("namn"))
        {
            System.out.println("Ange det namn som finns på bokningnen: ");
            String namn = buss.nextLine();
            for(int i = 0; i < platser.length; i++)
            {
                if(platsernamn[i].equals(namn))
                {
                    System.out.println("Du har bokat plats: "+(i+1));
                    break;
                }
                else if(i==21)
                {
                    System.out.println("Det angivna namnet är inte sparat vid en plats. Vänligen boka en plats först.");
                    break;
                }
                else
                {
                    continue;
                }
            }
        }
        else if(hittaval.equalsIgnoreCase("personnummer"))
        {
            int reverse = prsnrctrl();
            for(int i = 0; i<platser.length; i++)
            {
                if(platser[i]==reverse)
                {
                    System.out.println("Du har bokat plats: "+platser[i+1]);
                    break;
                }
                else if(platser[20]==0)
                {
                    System.out.println("Det angivna personnummret är inte sparat vid en plats. Vänligen boka en plats först.");
                    break;
                }
            }
        }
    }
    //Metod för att lista alla bokningar
    static void listabokningar()
    {
        for(int i = 0; i < platser.length; i++)
        {
            int år = 0;
            if(platser[i]>0)
            {
                String årStr = Integer.toString(platser[i]).substring(0, 4);
                år = Integer.parseInt(årStr);
            }

            if(år<=2005)
            {
                System.out.println("Över 18 år: "+platsernamn[i]+", "+platser[i]+", "+platserkön[i]);
                continue;
            }
            else if(år>2005)
            {
                System.out.println("Under 18 år: "+platsernamn[i]+", "+platser[i]+", "+platserkön[i]);
                continue;
            }
            else
            {
                continue;
            }
        }
    }
    //Metod för att kontrollera om personummret är riktigt eller inte
    static int prsnrctrl()
    {
        int dag;
        int år;
        int månad;
        String prsnr = buss.nextLine();

        while(true)
        {
            try
            {
                System.out.println("Ange personnummer (ÅÅÅÅMMDD): ");
                prsnr = buss.nextLine();

                //År
                String nyvar1 = prsnr.substring(0, 4);
                //Månad
                String nyvar2 = prsnr.substring(4, 6);
                //Dag
                String nyvar3 = prsnr.substring(6, 8);

                år = Integer.parseInt(nyvar1);
                månad = Integer.parseInt(nyvar2);
                dag = Integer.parseInt(nyvar3);
            }
            catch(Exception e)
            {
                felinmatning();
                continue;
            }
            if(år>2023)
            {
                felinmatning();
                continue;
            }
            else if(månad>12)
            {
                felinmatning();
                continue;
            }
            else if(dag>31)
            {
                felinmatning();
                continue;
            }
            else if(månad==4 && dag>30 || månad==6 && dag>30 || månad == 9 && dag>30 || månad == 11 && dag>30)
            {
                felinmatning();
                continue;
            }
            else if(månad == 2 && dag>29)
            {
                felinmatning();
                continue;
            }
            int reverse = Integer.parseInt(prsnr);
            return reverse;
        }
    }
    static String PlatsMapping()
    {
        String platservisual = "";
        for(int i = 0; i<platser.length; i++)
        {
            String suffix = "";
            String isle = "";
            String space = i<9 ? " " : "";
            if(i==3||i==7||i==11||i==15||i==20)
            {
                suffix="\n";
            }
            if(i==1||i==5||i==9||i==13)
            {
                isle = "    ";
            }
            if(platser[i]==0)
            {
                platservisual += "|" + (i + 1) + space +"|"+suffix+isle;
                continue;
            }

            platservisual += "|X |"+suffix+isle;
        }
        return platservisual;
    }
    static void felinmatning()
    {
        System.out.println("FELAKTIG INMATNING!!!");
        System.out.println("Var snäll och följ den korrekta strukturen.");
    }
}
