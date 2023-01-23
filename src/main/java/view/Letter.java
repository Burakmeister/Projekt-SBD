package view;

/**
 *
 * @author BURAK
 */
public enum Letter {
   OHH('\u0105'),
   CI('\u0107'),
   EHH('\u0119'),
   ELL('\u0142'),
   NI('\u0144'),
   UU('\u00F3'),
   SII('\u015B'),
   ZY('\u017C'),
   ZIET('\u017A');
   
   private final char letter;
   
   private Letter(char letter){
       this.letter = letter;
   }
   
   public char getLetter(){
       return this.letter;
   }
}
