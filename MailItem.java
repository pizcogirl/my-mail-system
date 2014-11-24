
/**
 * Write a description of class MailItem here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MailItem
{
    // Cadena de caracteres que contiene el emisor
    private String from;
    // Cada de caracteres que contiene el mensaje
    private String message;
    // Cadena de caracteres que contiene el destinatario
    private String to;

    /**
     * Escribe la direccion a la que envia el email y
     * el texto a enviar.
     */
    public MailItem(String fromMail, String toMail, String text)
    {
        // Inicializa las variables con los parametros introducidos
        from = fromMail;
        to = toMail;
        message = text;
    }

    /**
     * Imprime el mensaje por pantalla
     */
    public void printNextEmail()
    {
        // Imprime por pantalla el siguiente email
        System.out.println("De: " + from);
        System.out.println("Para: " + to);
        System.out.println(message);
    }
    
    /**
     * Devuelve el emisor del mensaje
     */
    public String getFrom()
    {
        return from;
    }
    
    /**
     * Devuelve el cuerpo del mensaje
     */
    public String getMessage()
    {
        return message;
    }
    
    /**
     * Devuelve el destinatario del mensaje
     */
    public String getTo()
    {
        return to;
    }
}
