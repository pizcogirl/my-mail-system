
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
    // Almacena el ultimo email recibido
    private MailItem lastEmail;

    /**
     * Constructor del cliente de email, introduce el nombre
     * del servidor y el nombre del usuario
     */
    public MailClient(String user, MailServer server)
    {
        // Inicializa las variables mediante parametros
        this.user = user;
        this.server = server;
        lastEmail = null;
    }

    /**
     * Devuelve el siguiente email en el servidor
     */
    public MailItem getNextMailItem()
    {
        // Devuelve el siguiente email en el servidor
        lastEmail = server.getNextMailItem(user);
        return lastEmail;
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
            lastEmail = server.getNextMailItem(user);
            lastEmail.printEmail();
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
     * Muestra por pantalla cuantos emails sin leer tiene el usuario
     */
    public void howManyMailItems()
    {
        int numEmail = server.howManyMailItems(user);
        System.out.println("Tiene " + numEmail + " mensajes nuevos");
    }

    /**
     * Devuelve el siguiente email en el servidor y lo contesta automaticamente
     * con un mensaje indicando que estamos de vacaciones
     */
    public void getNextMailItemAndAutorespond()
    {
        // Devolvemos el siguiente email, almacenamos los strings a modificar en strings 
        // temporales, y con los nuevos datos enviamos un mensaje automatico
        MailItem tempMail = server.getNextMailItem(user);
        // Comprueba si hay mensaje al que responder
        if (tempMail != null)
        {
            String tempSubject = ("RE: " + tempMail.getSubject());
            String tempMessage = (tempMail.getMessage() + "\nEstoy de vacaciones");
            sendMailItem (tempMail.getFrom(), tempSubject, tempMessage);
        }
    }
    
    /**
     * Muestra por pantalla el ultimo email devuelto desde el servidor.
     * Si no hay ningun mensaje, avisa de ello.
     */
    public void printLastMailItem()
    {
        // Si hay algun email guardado, lo imprime por pantalla
        // sino avisa de ello
        if (lastEmail != null)
        {
            lastEmail.printEmail();
        }
        else
        {
            System.out.println ("No hay mensajes almacenados");
        }
    }
}
