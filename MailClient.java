
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
    private int receivedMailCount;
    // Contador de email de spam recibidos
    private int receivedSpamCount;
    // Almacena la direccion de correo de quien nos ha enviado el correo más largo
    private String longestFrom;
    // Almacena la longitud del mensaje más largo recibido
    private int longestMessage;
    // Almacena el ultimo email de spam recibido
    private MailItem lastSpam;
    // Boolean para recoger si el email ha sido marcado como spam o no
    private boolean mailIsSpam;

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
        receivedMailCount = 0;
        receivedSpamCount = 0;
        longestFrom = "";
        longestMessage = 0;
        lastSpam = null;
        mailIsSpam = false;
    }

    /**
     * Devuelve el siguiente email en el servidor
     */
    public MailItem getNextMailItem()
    {
        // Devuelve el siguiente email en el servidor
        lastEmail = server.getNextMailItem(user);
        if (lastEmail != null)
        {
            // Comprueba si es el mensaje más largo recibido
            checkFrom();
            // Suma uno al contador de mensajes recibidos
            receivedMailCount = receivedMailCount + 1;
            // Comprueba si el mensaje es spam
            checkSpam();

            if (mailIsSpam)
            {
                // Si el mensaje es spam nos devolvera null, suma uno a la estadistica
                // de email de spam recibidos y lo guarda como spam
                lastSpam = lastEmail;
                lastEmail = null;
                receivedSpamCount = receivedSpamCount + 1;
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
            receivedMailCount = receivedMailCount + 1;
            lastEmail = server.getNextMailItem(user);
            // Comprueba si es el mensaje más largo recibido
            checkFrom();
            // Comprueba si el mensaje es spam
            checkSpam();
            if (mailIsSpam)
            {
                // Si el mensaje es spam nos avisa de ello, suma uno a la estadistica
                // de email de spam recibidos, lo guarda como spam y lo borra de lastEmail
                System.out.println("El mensaje recibido es spam");
                receivedSpamCount = receivedSpamCount + 1;
                lastSpam = lastEmail;
                lastEmail = null;
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
        // Cuenta la longitud del mensaje
        int originalLength = text.length();
        // Crea el email con los parametros introducidos y el user como emisor
        MailItem email = new MailItem(user, toMail, newSubject, text);
        // Envia el mensaje al servidor y suma uno a la estadistica de email enviados
        sendMailCount = sendMailCount + 1;
        server.post(email, originalLength);
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
        System.out.println ("Ha recibido " + receivedMailCount + " mensajes");
        System.out.println ("De los cuales " + ((receivedSpamCount/receivedMailCount)*100) + "% eran spam");
        System.out.println ("Ha enviado " + sendMailCount + " mensajes");
        System.out.println ("La direccion de la persona que nos envio el mensaje más largo es " + longestFrom);
    }

    /**
     * Metodo para guardar la direccion de correo del mensaje más largo recibido
     */
    private void checkFrom()
    {
        // Guarda el mensaje del ultimo email recibido
        String tempString = lastEmail.getMessage();
        // Comprueba la longitud del mensaje con la del mensaje más
        // largo recibido hasta ahora
        if (tempString.length() > longestMessage)
        {
            // Si es más largo el nuevo mensaje, guarda de quien es
            // y su longitud en caracteres
            longestFrom = lastEmail.getFrom();
            longestMessage = tempString.length();
        }
    }

    /**
     * Muestra por pantalla el ultimo email de spam devuelto desde 
     * el servidor. Si no hay ningun mensaje, avisa de ello.
     */
    public void printLastSpamItem()
    {
        // Si hay algun email de spam guardado, lo imprime por pantalla
        // sino avisa de ello
        if (lastSpam != null)
        {
            lastSpam.printEmail();
        }
        else
        {
            System.out.println ("No hay mensajes de spam almacenados");
        }
    }

    /**
     * Envia al servidor un mensaje con errores por problemas de transmisión
     */ 

    public void sendMailItemWithTransmissionError(String toMail, String newSubject, String text)
    {
        // Cuenta la longitud del mensaje
        int originalLength = text.length();
        // Simula errores de transmisión
        text = text.replace("a", "#&");
        text = text.replace("e", "$#");
        // Crea el email con los parametros introducidos y el user como emisor
        MailItem email = new MailItem(user, toMail, newSubject, text);
        // Envia el mensaje al servidor y suma a la cuenta de emails enviados
        sendMailCount = sendMailCount + 1;
        server.post(email, originalLength);
    }

    /**
     * Metodo para comprobar si un email es o no spam
     */
    private void checkSpam()
    {
        // Guarda el texto del mensaje para comprobar si hay palabras de spam
        String tempString = lastEmail.getMessage() + " " + lastEmail.getSubject();
        // Comprueba si es spam y guarda el resultado en un booleano
        if (tempString.contains("viagra"))
        {
            if (tempString.contains("proyecto"))
            {
                mailIsSpam = false;
            }
            else
            {
                mailIsSpam = true;
            }
        }
        if (tempString.contains("oferta"))
        {
            if (tempString.contains("proyecto"))
            {
                mailIsSpam = false;
            }
            else
            {
                mailIsSpam = true;
            }

        }
    }
}
