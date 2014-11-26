
/**
 * Esta clase representa un mensaje de email
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
     * Escribe la direccion a la que envia el email, quien
     * lo envia y el texto a enviar.
     */
    public MailItem(String from, String to, String message)
    {
        // Inicializa las variables con los parametros introducidos
        this.from = from;
        this.to = to;
        this.message = message;
    }

    /**
     * Imprime el mensaje por pantalla
     */
    public void printEmail()
    {
        // Imprime por pantalla el email
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
