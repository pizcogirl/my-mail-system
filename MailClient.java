
/**
 * Representa un cliente de corrreo electronico
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MailClient
{
    // Servidor asociado a este cliente de email
    private MailServer server;
    // Usuario del cliente de email
    private String user;

    /**
     * Constructor del cliente de email, introduce el nombre
     * del servidor y el nombre del usuario
     */
    public MailClient(String user, MailServer server)
    {
        // Inicializa las variables mediante parametros
        this.user = user;
        this.server = server;
    }

    /**
     * Devuelve el siguiente email en el servidor
     */
    public MailItem getNextMailItem()
    {
        // Devuelve el siguiente email en el servidor
        return server.getNextMailItem(user);
    }

    /**
     * Imprime por pantalla el siguiente email en el servidor
     */
    public void printNextMailItem()
    {
        // Imprime por pantalla el siguiente email en el servidor,
        // si no hay ninguno imprime un mensaje avisandolo
        if (server.howManyMailItems(user) > 0)
        {
            MailItem email = server.getNextMailItem(user);
            email.printEmail();
        }
        else
        {
            System.out.println("No hay mensajes nuevos");
        }
    }

    /**
     * Envia un mensaje a otro usuario de email. Introduce el destinatario,
     * el asunto y el cuerpo del mensaje
     */
    public void sendMailItem (String toMail, String newSubject, String text)
    {
        // Crea el email con los parametros introducidos y el user como emisor
        MailItem email = new MailItem(user, toMail, newSubject, text);
        // Envia el mensaje al servidor
        server.post(email);
    }
    
    /**
     * Muestra por pantalla cuantos emails sin leer tiene el cliente
     */
    public void howManyMailItems()
    {
        int numEmail = server.howManyMailItems(user);
        System.out.println("Tiene " + numEmail + " mensajes nuevos");
    }
}
