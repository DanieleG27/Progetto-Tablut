package tablut_gui;

import tablut_gui.ai.genetics.WhiteWeights;
import tablut_gui.model.Player;
import tablut_gui.model.StateImplementation;
import tablut_gui.players.*;

import java.io.IOException;
import java.util.Random;

public class Main {

	private static void startServer(){
		ProcessBuilder processBuilder = new ProcessBuilder("java", "-jar", ".\\exec\\Server.jar");
		try {
			Process process = processBuilder.start();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static void startAlgise(Player player, int timeout, String ip){
		ProcessBuilder processBuilder;

		if(player==Player.WHITE)
			processBuilder = new ProcessBuilder("java", "-jar", ".\\exec\\AlgiseWhiteClient.jar", ""+timeout, ip);
		else
			processBuilder = new ProcessBuilder("java", "-jar", ".\\exec\\AlgiseBlackClient.jar", ""+timeout, ip);

		try {
			Process process = processBuilder.start();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static void startBecchi(Player player, int timeout, String ip){
		ProcessBuilder processBuilder;

		if(player==Player.WHITE)
			processBuilder = new ProcessBuilder("java", "-jar", ".\\exec\\Becchi.jar", "WHITE", ""+timeout, ip);
		else
			processBuilder = new ProcessBuilder("java", "-jar", ".\\exec\\Becchi.jar", "BLACK", ""+timeout, ip);

		try {
			Process process = processBuilder.start();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}


	private static void startAgainst(Player player, int timeout, String ip, String jarName){
		ProcessBuilder processBuilder;

		if(player==Player.WHITE)
			processBuilder = new ProcessBuilder("java", "-jar", ".\\exec\\"+jarName, "WHITE", ""+timeout, ip);
		else
			processBuilder = new ProcessBuilder("java", "-jar", ".\\exec\\"+jarName, "BLACK", ""+timeout, ip);

		try {
			Process process = processBuilder.start();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static void startPiedino(Player player, int timeout, String ip){
		ProcessBuilder processBuilder;

		if(player==Player.WHITE)
			processBuilder = new ProcessBuilder("java", "-jar", ".\\exec\\Piedino.jar", "WHITE", ""+timeout, ip);
		else
			processBuilder = new ProcessBuilder("java", "-jar", ".\\exec\\Piedino.jar", "BLACK", ""+timeout, ip);

		try {
			Process process = processBuilder.start();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {

		int timeoutSec = 60;
		String serverIp = "localhost";

		startServer();


		//vittoria con Piedino e Algise
		/*
		WhiteWeights weights = new WhiteWeights(new double[]{
				1.312620150992534, 0.9466057762992949, 1.8584651370725078, 1.558999151777429, 1.1043334828566984, 1.061570148844939, 1.3998508513473502, 1.2224030986158119, 1.0554627616785803, 1.3397181028983927, 1.1903114834806368, 1.020807624309391, 0.9542034018623763, 0.5097884637209162, 1.1015690328688559, 0.9482466718653882, 0.5667251887455155, 1.6767446259276022, 1.4374227781875941, 1.4316894103717044, 1.182392766220474, 2.0471775213024874, 0.8266821194394454, 0.5864835556075233, 1.0223751271370511
		});
		*/

		/* CAMP1 - INIZIALE
		WhiteWeights weights = new WhiteWeights(new double[]{
				1.6255410713384937, 1.110030427965562, 1.8287500636036376, 1.5647192953464184, 1.0820990247717237, 0.8349729982015452, 1.4226601451425958, 1.2542132954054712, 0.9758777471703461, 1.1888976195729004, 1.0645035588495424, 0.9324536207363828, 0.9175150107632771, 0.5006023789509922, 0.9897119199140362, 0.7762579477998427, 0.4758883156202713, 1.3483882612747577, 2.02852380741212, 1.4301824039382875, 1.1339227560526548, 2.21467603291491, 0.9832115686159433, 0.5392765324082245, 1.2786954321769195
		});
		*/

		/*
		//Media di quelli che hanno vinto contro i 4 contro cui CAMP1 ha vinto
		WhiteWeights weights = new WhiteWeights(new double[]{
				1.6904668601259643, 1.0949326039570116, 1.9134191038938581, 1.5978757073380265, 1.0525876337787532, 0.8832961703330425, 1.4731876537183856, 1.3102143497472207, 0.9561622544654839, 1.2022905868471625, 1.057017705623936, 0.902224476937824, 0.9361854302597933, 0.541009646893474, 1.0004755988742056, 0.7632557289513135, 0.45506540604742374, 1.3344287073275169, 1.9275130988496495, 1.3061437355082058, 1.1145268115934132, 2.157275120385186, 0.9164962627194322, 0.513073875191706, 1.2714776121921308
		});
		*/

		/*
		//NEWCAMP2 - vinto contro AInabilityClient, UniOne, Piedino, WTF, FunSociety, artificialFailure (perde con becchi, beluga ambiguo)
		WhiteWeights weights = new WhiteWeights(new double[]{
			1.6737519634335167, 1.0921124310152328, 2.0180737498227974, 1.5560319774240228, 1.1120964613043236, 0.8568657099867008, 1.5758901345074294, 1.4073607679193658, 0.928109782279687, 1.2697455995400688, 0.9860735950842149, 0.8397668430755906, 0.9080439122612586, 0.5922608454219455, 1.0521704398973233, 0.7640851511146581, 0.4529617894001198, 1.3390860017923356, 1.8214681109663722, 1.224042902867019, 1.1349866053175335, 1.8692351609594213, 0.8440137402956913, 0.4801256059651403, 1.2106341752228675
		});
		*/

		// NEWCAMP2 TRAINATO e mixato con Becchi - vinto contro becchi, artificialFailure, nostroNero, Piedino,
		// perde contro AInabilityClient, playerWTF, FunSociety
		WhiteWeights weights = new WhiteWeights(new double[]{
				1.6362256828728168, 1.1842382110861425, 2.187108426163769, 1.4471039300543727, 1.0858552803529011, 0.882910976605825, 1.6615214392448312, 1.456567216455382, 0.9051117954189921, 1.2586528278205782, 1.0649703809693163, 0.8740551873642163, 0.8993140860834131, 0.5052417926545657, 1.0489140373504713, 0.7028914125791602, 0.4687612373346254, 1.350140835293078, 2.049112124546597, 1.17766804790182, 1.058136364443092, 2.000804566620761, 0.7883299455100082, 0.4521699069419196, 1.3727313334899232
		});



		WhiteGeneticAIPlayerV1 white = new WhiteGeneticAIPlayerV1("whitePlayer", timeoutSec, serverIp, true, true, StateImplementation.BITBOARD, weights);

		//RandomPlayer white = new RandomPlayer(Player.WHITE, "whitePlayer", 2, serverIp, true, false);
		//RandomPlayer black = new RandomPlayer(Player.BLACK, "blackPlayer", 5, serverIp, false, false);

		//WhiteAIPlayerV1 white = new WhiteAIPlayerV1("whitePlayer", timeoutSec, serverIp, true, true, StateImplementation.BITBOARD);
		//HumanPlayer white = new HumanPlayer("human", 60, serverIp, false, Player.WHITE, StateImplementation.STANDARD);

		//startAlgise(Player.BLACK, timeoutSec, serverIp);
		//startPiedino(Player.BLACK, timeoutSec, serverIp);
		startAgainst(Player.BLACK, timeoutSec, serverIp, "UniOne.jar");

		//SimpleAIBlackPlayer black = new SimpleAIBlackPlayer("blackPlayer", timeoutSec, serverIp, true, true, StateImplementation.BITBOARD);
		//WhiteAIPlayerV1 white = new WhiteAIPlayerV1("whitePlayer", timeoutSec, serverIp, true, true, StateImplementation.BITBOARD);

		//SimpleAIBlackPlayer black = new SimpleAIBlackPlayer("blackPlayer", timeoutSec, serverIp, false, true, StateImplementation.BITBOARD);

		white.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {}

		//black.start();



    }

}
