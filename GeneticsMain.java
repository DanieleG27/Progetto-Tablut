package tablut_gui;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import tablut_gui.ai.genetics.EvolutionAlgorithm;
import tablut_gui.ai.genetics.WhiteWeights;
import tablut_gui.dto.WeightsDTO;
import tablut_gui.dto.WeightsDTOList;
import tablut_gui.model.Player;
import tablut_gui.model.StateImplementation;
import tablut_gui.players.SimpleAIBlackPlayer;
import tablut_gui.players.WhiteAIPlayerV1;
import tablut_gui.players.WhiteGeneticAIPlayerV1;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GeneticsMain {

    private static Gson gson = new Gson();

    private static void startServer(){
        ProcessBuilder processBuilder = new ProcessBuilder("java", "-jar", ".\\exec\\Server.jar");
        try {
            Process process = processBuilder.start();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {}
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void startAdversarial(int timeoutSec, String serverIp){
        SimpleAIBlackPlayer adv = new SimpleAIBlackPlayer("blackPlayer", timeoutSec, serverIp, false, false, StateImplementation.BITBOARD);
        adv.start();
    }

    private static Map<WhiteWeights, Double> createNewFirstGeneration(EvolutionAlgorithm ea){
        List<double[]> population = ea.createGeneration(10, WhiteWeights.FIELDS_NUM);
        Map<WhiteWeights, Double> weights = new HashMap<>();

        for(var p : population){
            weights.put(new WhiteWeights(p), 0.0);
        }

        return weights;
    }

    private static Map<WhiteWeights, Double> loadGeneration(String fileName){

        try (FileReader reader = new FileReader(fileName)) {
            WeightsDTOList ws = gson.fromJson(reader, WeightsDTOList.class);
            Map<WhiteWeights, Double> map = new HashMap<>();
            for(var w : ws.getWeightsList()){
                map.put(w.getWeights(), w.getFitness());
            }
            return map;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    private static void saveGeneration(Map<WhiteWeights, Double> weights, String fileName){
        var ws = new WeightsDTOList();
        for(var k : weights.keySet()){
            ws.getWeightsList().add(new WeightsDTO(k, weights.get(k)));
        }

        try(FileWriter fw = new FileWriter(new File(fileName))){
            fw.write(gson.toJson(ws));
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }

    private static Map<WhiteWeights, Double> generateNextGeneration(Map<WhiteWeights, Double> weights, EvolutionAlgorithm ea){
        var orderedWeights = weights.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue()).toList();

        var population = ea.crossover(orderedWeights.get(weights.size()-1).getKey().toDoubleArray(),
                orderedWeights.get(weights.size()-2).getKey().toDoubleArray(), 10);

        Map<WhiteWeights, Double> w = new HashMap<>();
        for(var p : population){
            w.put(new WhiteWeights(p), 0.0);
        }

        return w;
    }

    private static Map<WhiteWeights, Double> generateNextGenerationWhitOutCrossover(Map<WhiteWeights, Double> weights, EvolutionAlgorithm ea){
        var orderedWeights = weights.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue()).toList();

        var population = ea.mutateGeneration(orderedWeights.get(weights.size()-1).getKey().toDoubleArray(),10);

        Map<WhiteWeights, Double> w = new HashMap<>();
        for(var p : population){
            w.put(new WhiteWeights(p), 0.0);
        }

        return w;
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

    public static void main(String[] args) {



        /// //////////////////////
        int timeoutSec = 60;
        String serverIp = "localhost";
        boolean enableGui = true;
        String baseFileName = "generation2005";

        int startGen = 1;
        int endGen = 4;

        EvolutionAlgorithm ea = new EvolutionAlgorithm(0.1);
        //Map<WhiteWeights, Double> weights = createNewFirstGeneration(ea);
        Map<WhiteWeights, Double> weights = new HashMap<>();


        //------------------------------------------------------------
        //campione trovato all'inizio
        weights.put(new WhiteWeights(new double[]{1.6255410713384937, 1.110030427965562, 1.8287500636036376, 1.5647192953464184, 1.0820990247717237, 0.8349729982015452, 1.4226601451425958, 1.2542132954054712, 0.9758777471703461, 1.1888976195729004, 1.0645035588495424, 0.9324536207363828, 0.9175150107632771, 0.5006023789509922, 0.9897119199140362, 0.7762579477998427, 0.4758883156202713, 1.3483882612747577, 2.02852380741212, 1.4301824039382875, 1.1339227560526548, 2.21467603291491, 0.9832115686159433, 0.5392765324082245, 1.2786954321769195}), 0.0);//966.0
        //vinto contro artificialfailure
        weights.put(new WhiteWeights(new double[]{1.7950687620121033, 1.0376404900099014, 1.9256325650489243, 1.4721877271131227, 1.1436903918115358, 0.9523166968598722, 1.2794696452953558, 1.1903000051483212, 1.0865583193310597, 1.2746776348159032, 1.114129569145967, 0.7932751563474988, 1.0028704739757577, 0.5703580578057185, 0.9732517865059058, 0.7068348877439958, 0.4685042831807599, 1.4013595684596858, 2.1004614064722973, 1.228067737601829, 1.1903248556369226, 2.005298932845229, 0.9538290529185156, 0.5393156265927029, 1.1484799512997723 }), 0.0);//962.0
        //vinto contro becchi
        weights.put(new WhiteWeights(new double[]{1.6289950569385938, 1.0753842997937504, 1.9296537791889878, 1.7021252355443501, 1.011875225859084, 0.8714929405436496, 1.499114058127688, 1.3706599448217565, 0.8981444910157979, 1.1748596501791915, 0.9984045630270045, 0.9521426244909844, 0.9378189888391616, 0.5102937203089274, 1.054654052893814, 0.7191728551485643, 0.43837608966993613, 1.2493404269307864, 1.9232714940019147, 1.3426689896103465, 1.0445948710645716, 2.351594819395576, 0.9234087646559974, 0.5001855641975883, 1.357130718507268}), 963.0);//963.0
        //vinto contro FunSociety
        weights.put(new WhiteWeights(new double[]{1.728975450907116, 1.15749336909461, 1.8669353578039434, 1.694286303264218, 0.9131750631470982, 0.9008345060774441, 1.5928042825168591, 1.3295397354411884, 0.8961229305305286, 1.1032724281317483, 1.1259752400089513, 0.9954821400346631, 0.9136787654555114, 0.5335342299817862, 0.9325877931639481, 0.8499277989515068, 0.4415965443680295, 1.3359672781820189, 1.7618446713955458, 1.3057576415255445, 1.0688029678933828, 2.3455716578107966, 0.8760181851090136, 0.5084650487948741, 1.360445784753827}), 0.0);//950.0
        //vinto contro playerWTF - secondo campione trovato che non vince contro becchi
        weights.put(new WhiteWeights(new double[]{1.6737519634335167, 1.0921124310152328, 2.0180737498227974, 1.5560319774240228, 1.1120964613043236, 0.8568657099867008, 1.5758901345074294, 1.4073607679193658, 0.928109782279687, 1.2697455995400688, 0.9860735950842149, 0.8397668430755906, 0.9080439122612586, 0.5922608454219455, 1.0521704398973233, 0.7640851511146581, 0.4529617894001198, 1.3390860017923356, 1.8214681109663722, 1.224042902867019, 1.1349866053175335, 1.8692351609594213, 0.8440137402956913, 0.4801256059651403, 1.2106341752228675}), 960.0);



        //------------------------------------------------------------


        weights = generateNextGeneration(weights, ea);
        /*
        Map<WhiteWeights, Double> weights = loadGeneration("gen1.json");
        weights = generateNextGeneration(weights, ea);
         */
        /// ///////////////////////////

        System.out.println("STARTED");

        for(int generation = startGen; generation<=endGen; generation++){
            System.out.println("STARTED GEN "+generation);

            for(var w : weights.keySet()){

                startServer();
                //startAdversarial(timeoutSec, serverIp);
                startAgainst(Player.BLACK, timeoutSec, serverIp,"Becchi.jar");

                System.out.println("WEIGHTS :  "+w);

                WhiteGeneticAIPlayerV1 white = new WhiteGeneticAIPlayerV1("whitePlayer", timeoutSec, serverIp,
                        enableGui, false, StateImplementation.BITBOARD, w);
                white.start();

                try {
                    white.join();
                    System.out.println(white.getController().getStats());
                    System.out.println(white.getController().getStats().fitness());
                    weights.put(w, white.getController().getStats().fitness());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println("ENDED GEN: "+generation);

            var orderedWeights = weights.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue()).toList();

            System.out.println(orderedWeights);

            System.out.println("Best 1: "+orderedWeights.get(orderedWeights.size()-1).getKey());
            System.out.println("Best 2: "+orderedWeights.get(orderedWeights.size()-2).getKey());

            saveGeneration(orderedWeights.stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)),
                    baseFileName+"generation"+generation+".json");

            /*
            try {
                FileWriter fw = new FileWriter(new File("gen"+generation+".txt"));
                fw.write(weights.entrySet()
                        .stream()
                        .sorted(Map.Entry.comparingByValue()).toList().toString());
                fw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            */
            weights = generateNextGeneration(weights, ea);
        }
    }
}
