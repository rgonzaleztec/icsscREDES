package org.example.TriviaRedesBot;

import java.util.ArrayList;

import io.github.nixtabyte.telegram.jtelebot.client.RequestHandler;
import io.github.nixtabyte.telegram.jtelebot.exception.JsonParsingException;
import io.github.nixtabyte.telegram.jtelebot.exception.TelegramServerException;
import io.github.nixtabyte.telegram.jtelebot.request.TelegramRequest;
import io.github.nixtabyte.telegram.jtelebot.request.factory.TelegramRequestFactory;
import io.github.nixtabyte.telegram.jtelebot.response.json.Message;
import io.github.nixtabyte.telegram.jtelebot.server.impl.AbstractCommand;

public class MessageCommand extends AbstractCommand {
	private static int questionsIndex = 0;
	private static int answersIndex = 0;
	private ArrayList<String> questions; 
	private ArrayList<String> answers;
	private static float corrects = 0;
	
	public MessageCommand(Message message, RequestHandler requestHandler) {
		super(message, requestHandler);
		
		questions = new ArrayList<>();
		questions.add("�Cuantas capas tiene el modelo OSI?\n/1: 7\n/2: 4\n/3: 8\n/4: 5");
		questions.add("�Qu� tipo de red debe usar un usuario para el acceso a casa con el fin" +
				"de hacer compras en l�nea?\n/1: Una red de �rea local\n/2: Internet\n/3: Una extranet" +
				"\n/4: Una intranet");
		questions.add("�Qu� dispositivo realiza la funci�n de determinar el camino que deben " +
				"tomar los mensajes a trav�s de interconexiones de redes?\n/1: Un servidor web\n" +
				"/2: Un m�dem DSL\n/3: Un router\n/4: Un cortafuegos");
		questions.add("�Qu� comando se puede utilizar en un PC con Windows para ver la " +
				"configuraci�n IP de ese equipo?\n/1: Ping\n" + "/2: Show interfaces\n/3: Interface " +
				"brief show ip\n/4: Ipconfig");
		questions.add("�Qu� protocolo se encarga de controlar el tama�o y la velocidad de los " +
				"mensajes HTTP intercambiados entre el servidor y el cliente?\n/1: TCP\n/2: ARP\n/" +
				"3: HTTP\n/4: DHCP");
		questions.add("�Qu� est�ndar IEEE permite una interfaz de red inal�mbrica para conectarse" + 
				"a un punto de acceso inal�mbrico que est� hecho por un fabricante diferente?" +
				"\n/1: 802.11\n/2: 802.1\n/3: 802.3\n/4: 802.2");
		questions.add("�En qu� capa del modelo OSI se encapsula una direcci�n l�gica?" +
				"\n/1: Capa de red\n/2: Capa de transporte\n/3: Capa f�sica\n/4: Capa de enlace de datos");
		
		answers = new ArrayList<>();
		answers.add("/1");
		answers.add("/2");
		answers.add("/3");
		answers.add("/4");
		answers.add("/1");
		answers.add("/1");
		answers.add("/4");
	}

	@Override
	public void execute() {

		try {
			System.out.println("-----------------------------------------------------------");
			String incommingMessage = message.getText();
			String showingMessage = "";
			
			if (incommingMessage.compareTo("/start") == 0) {
				showingMessage = "Est�s a punto de iniciar con la grandiosa Trivia sobre Redes de Computadoras"
						+ "\n/Iniciemos";
			} else if (incommingMessage.compareTo("/Iniciemos") == 0) {
				showingMessage = questions.get(questionsIndex);
				questionsIndex++;
			} else if (questionsIndex == questions.size()) {
				if (incommingMessage.compareTo(answers.get(answersIndex)) == 0) {
					corrects++;
				}
				
				questionsIndex = 0;
				answersIndex = 0;
				showingMessage = "Has terminado la grandiosa Trivia sobre Redes de Computadoras con una nota de "
						+ (corrects * 100 / questions.size());
				corrects = 0;
			} else {

				if (incommingMessage.compareTo(answers.get(answersIndex)) == 0) {
					corrects++;
				}
				System.out.println("Incomming: " + incommingMessage + "\nAnswer: " + answers.get(answersIndex));
				
				showingMessage = questions.get(questionsIndex);
				questionsIndex++;
				answersIndex++;
			}

            TelegramRequest telegramRequest = TelegramRequestFactory.
            		createSendMessageRequest(
            				message.getChat().getId(), 
            				showingMessage, 
            				true, 
            				message.getId(), 
            				null);
            
            requestHandler.sendRequest(telegramRequest);
        } catch (TelegramServerException | JsonParsingException e) {
            e.printStackTrace();
        }
	}

}
