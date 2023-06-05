import java.util.Scanner;

//建立顧客類別
class Customer extends Thread{
    Eat eat;
    String name;

//建構式
    public Customer(Eat eat, String name){
        this.eat=eat;
        this.name=name;
    }
//執行多執行緒
    public void run(){
        //執行到所有水餃都吃完
        while(eat.status==false){
            //亂數選擇水餃
            int selectDumpling=(int)(Math.ceil(Math.random()*3));
            //亂數選擇幾顆水餃
            int noDumpling=(int)(Math.ceil(Math.random()*50));
            //傳執行緒名字, 選擇的水餃, 幾顆水餃給Eat類別的eatCount方法
            eat.eatCount(name, selectDumpling, noDumpling);
            try {
                //暫停三秒
                sleep(3000);
            } catch (InterruptedException e) {
            }
        }
    }
}

//主要吃水餃的類別
class Eat{
    //設定水餃初始值
    int pork=5000;
    int beef=3000;
    int veg=1000;
    int noCustomers;
    Customer[] customer;
    public boolean status=false; 

    //建構式 傳入由主程式使用者輸入的顧客數目
    Eat(int a){
        noCustomers=a;
    }
    //開始扣水餃, 記得設定執行緒同步
    public synchronized void eatCount(String b, int x, int y){
        String name=b;
        int eatDumpling=x;
        int noeatDumpling=y;
        //判斷水餃類別
        if (eatDumpling==1){
            //判斷水餃是不是吃完了 吃完的話不能再點 趕走顧客
            if(pork==0){
                System.out.println("顧客:"+name+"來了");
                System.out.println("豬肉水餃已經賣完不能再點");
            }else{
                //如果點的水餃數目大於剩下的就只扣到０為止
                if(noeatDumpling>pork){
                    System.out.println("顧客:"+name+"來了");
                    System.out.println("賣出豬肉水餃:"+pork);
                    pork-=pork;
                    System.out.println("豬肉水餃還剩:"+pork);  
                }else{
                    //扣水餃
                    pork-=noeatDumpling;
                    System.out.println("顧客:"+name+"來了");
                    System.out.println("賣出豬肉水餃:"+noeatDumpling);
                    System.out.println("豬肉水餃還剩:"+pork);  
                }
            }
        }else if(eatDumpling==2){
            if(beef==0){
                System.out.println("顧客:"+name+"來了");
                System.out.println("牛肉水餃已經賣完不能再點");
            }else{
                if(noeatDumpling>beef){
                    System.out.println("顧客:"+name+"來了");
                    System.out.println("賣出牛肉水餃:"+beef);
                    beef-=beef;
                    System.out.println("牛肉水餃還剩:"+beef);  
                }else{
                    beef-=noeatDumpling;
                    System.out.println("顧客:"+name+"來了");
                    System.out.println("賣出牛肉水餃:"+noeatDumpling);
                    System.out.println("牛肉水餃還剩:"+beef);   
                }              
            }
        }else if(eatDumpling==3){
            if(veg==0){
                System.out.println("顧客:"+name+"來了");
                System.out.println("蔬菜水餃已經賣完不能再點");
            }else{
                if(noeatDumpling>veg){
                    System.out.println("顧客:"+name+"來了");
                    System.out.println("賣出蔬菜水餃:"+veg);
                    veg-=veg;
                    System.out.println("蔬菜水餃還剩:"+veg);  
                }else{
                    veg-=noeatDumpling;
                    System.out.println("顧客:"+name+"來了");
                    System.out.println("賣出蔬菜水餃:"+noeatDumpling);
                    System.out.println("蔬菜水餃還剩:"+veg);
                }
            }
        }

        //若所有水餃都賣完了就設定狀態為true
        if(pork==0 && beef==0 && veg==0){
            status=true;
        }
    }

    //開始吃的方法 建立顧客執行緒
    public void startEat(){
        //用陣列方式建立 因為顧客數不確定 使用由主程式使用者輸入的顧客數目
        customer=new Customer[noCustomers];
        for(int i=0;i<noCustomers;i++){
            int j=i+1;
            customer[i]=new Customer(this,"顧客:"+j);
        }
        for(int k=0;k<noCustomers;k++){
            //啟動執行緒
            customer[k].start();
        }
    }
}

//主程式
public class Dumpling {
    public static void main(String[] args) {
        //取得顧客數
        System.out.println("請輸入顧客數目");
        Scanner scanner=new Scanner(System.in);
        int customers=scanner.nextInt();
        //建立吃水餃物件 傳入顧客數
        Eat eat=new Eat(customers);
        //執行吃水餃類別中開始吃的方法
        eat.startEat();
    }
}
