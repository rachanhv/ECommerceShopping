package vinformax.vinmart.product;

import java.text.NumberFormat;

/**
 * Created by basker.t on 12/31/2015.
 */
public class CurrencyFormat {
   public static String putComma(String currency){
       try{

         return   NumberFormat.getNumberInstance().format(Double.parseDouble(currency));
       }catch (Exception e){
           return currency;
       }
   }
    public static String removeComma(String currency){
        return  currency.replace(",","");
    }
}
