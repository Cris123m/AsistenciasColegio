package Modelo;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class LimitadorCaracteres extends PlainDocument
{
   /**
    * Método al que llama el editor cada vez que se intenta insertar caracteres.
    * Sólo debemos verificar arg1, que es la cadena que se quiere insertar en el JTextField
    */
   public void insertString(int arg0, String arg1, AttributeSet arg2) throws BadLocationException
   {
       if(arg0>9)
           return;
       for (int i=0;i<arg1.length();i++)
          // si no es digit, volvemos
          if (!Character.isDigit(arg1.charAt(i)))
             return;

       // Si todos son digit, insertamos el texto en el JTextField
       super.insertString(arg0, arg1, arg2);
   }
} 
