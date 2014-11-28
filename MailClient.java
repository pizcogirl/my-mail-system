
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
    // Contador de email enviados
    private int sendMailCount;
    // Contador de email recibidos
    private int receiveMailCount;
    // Contador de email de spam recibidos
    private int receiveSpamCount;
    // Almacena la direccion de correo de quien nos ha enviado el correo más largo
    private String longestFrom;
    // Almacena la longitud del mensaje más largo recibido
    private int longestMessage;

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
        sendMailCount = 0;
        receiveMailCount = 0;
        receiveSpamCount = 0;
        longestFrom = "";
        longestMessage = 0;
    }

    /**
     * Devuelve el siguiente email en el servidor
     */
    public MailItem getNextMailItem()
    {
        // Devuelve el siguiente email en el servidor
        lastEmail = server.getNextMailItem(user);
        String tempString = lastEmail.getMessage() + " " + lastEmail.getSubject();
        checkFrom();
        if (lastEmail != null)
        {
            receiveMailCount = receiveMailCount + 1;
        }
        if (tempString.contains("viagra"))
        {
            if (tempString.contains("proyecto"))
            {
                return lastEmail;
            }
            else
            {
                lastEmail = null;
                receiveSpamCount = receiveSpamCount + 1;
            }
        }
        if (tempString.contains("oferta"))
        {
            if (tempString.contains("proyecto"))
            {
                return lastEmail;
            }
            else
            {
                lastEmail = null;
                receiveSpamCount = receiveSpamCount + 1;
            }

        }

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
            receiveMailCount = receiveMailCount + 1;
            lastEmail = server.getNextMailItem(user);
            checkFrom();
            String tempString = lastEmail.getMessage() + " " + lastEmail.getSubject();
            if (tempString.contains("viagra"))
            {
                if (tempString.contains("proyecto"))
                {
                    lastEmail.printEmail();
                }
                else
                {
                    System.out.println("El mensaje recibido es spam");
                    receiveSpamCount = receiveSpamCount + 1;
                    lastEmail = null;
                }
            }
            if (tempString.contains("oferta"))
            {
                if (tempString.contains("proyecto"))
                {
                    lastEmail.printEmail();
                }
                else
                {
                    System.out.println("El mensaje recibido es spam");
                    receiveSpamCount = receiveSpamCount + 1;
                    lastEmail = null;
                }

            }
            else
            {
                lastEmail.printEmail();
            }
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
        sendMailCount = sendMailCount + 1;
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
    
    /**
     * Muestra estadisticas referentes al uso del correo electronico
     * numero de emails enviados, recibidos, porcentaje de recibidos
     * que son spam y direccion de correo que nos ha enviado el mensaje
     * más largo.
     */
    public void printStatistics()
    {
        // Imprime por pantalla todas las estadisticas
        System.out.println ("Ha recibido " + receiveMailCount + " mensajes");
        System.out.println ("De los cuales " + ((receiveSpamCount/receiveMailCount)*100) + "% eran spam");
        System.out.println ("Ha enviado " + sendMailCount + " mensajes");
        System.out.println ("La direccion de la persona que nos envio el mensaje más largo es " + longestFrom);
    }
    
    /**
     * Metodo para guardar la direccion de correo del mensaje más largo recibido
     */
    private void checkFrom()
    {
        String tempString = lastEmail.getMessage();
        if (tempString.length() > longestMessage)
        {
            longestFrom = lastEmail.getFrom();
            longestMessage = tempString.length();
        }
    }
}
