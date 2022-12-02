package buffer;

import java.util.ArrayList;



public class Buffer extends ArrayList<Integer> {
     private int maxCapacity;
     public Buffer (int maxCapacity) {
         this.maxCapacity = maxCapacity;
     }


    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    private void addPrivate (int data) throws Exception {
         if(this.size()<maxCapacity) this.add(data);
         else throw new Exception("Cannot add element in a full buffer.");
     }

     private int removeLastPrivate () throws Exception {
         if(this.size()>0) return this.remove(this.size()-1);
         else throw new Exception("Cannot remove elements from an empty buffer.");
     }

     public void addInt(int i)  {
         try {
             this.addPrivate(i);
               /*int startingSize = this.size();
             if(startingSize==0){
                 this.add(data);
             }
             else {
                 this.add(this.get(startingSize - 1));
                 for (int j = 0; j < startingSize - 1; j++) {
                     this.set(startingSize - j - 1, this.get(startingSize - j - 2));
                 }
                 this.set(0, data);
             }*/
         }catch (Exception e){
             System.out.println(e.getMessage());
         }
     }

     public int removeLast () {
         try{
             return removeLastPrivate();
         }catch (Exception e){
             System.out.println(e.getMessage());
         }
         return -100;
     }

     public boolean checkFull () {
         if(this.size() == maxCapacity) return true;
         else return false;
     }

     public boolean checkEmpty () {
         if(this.size() == 0) return true;
         else return false;
     }

/*
    @Override
    public String toString() {
        String str = "";
        for(Integer i : this) str += i + " , " ;
        return str;
    }*/
}


