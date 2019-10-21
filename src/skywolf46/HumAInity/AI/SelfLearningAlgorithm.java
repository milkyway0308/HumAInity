package skywolf46.HumAInity.AI;

import org.deeplearning4j.datasets.iterator.INDArrayDataSetIterator;
import org.deeplearning4j.earlystopping.EarlyStoppingConfiguration;
import org.deeplearning4j.earlystopping.EarlyStoppingResult;
import org.deeplearning4j.earlystopping.saver.LocalFileModelSaver;
import org.deeplearning4j.earlystopping.scorecalc.DataSetLossCalculator;
import org.deeplearning4j.earlystopping.termination.MaxEpochsTerminationCondition;
import org.deeplearning4j.earlystopping.termination.MaxTimeIterationTerminationCondition;
import org.deeplearning4j.earlystopping.trainer.EarlyStoppingTrainer;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.BackpropType;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.learning.config.Nesterovs;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import org.nd4j.linalg.primitives.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class SelfLearningAlgorithm {

    public static void init(){
        BlockingQueue<DataSet> bq = new LinkedBlockingQueue<>();
        List<Pair<INDArray,INDArray>> inds = new ArrayList<>();
        int[] shape = new int[]{2};
        Random r = new Random();
        for(int i = 0;i < 400;i++){
            int[] data = new int[]{r.nextInt(100),r.nextInt(100)};
            int[] result = new int[]{data[0] + data[1]};
            DataSet ds = new DataSet(Nd4j.create(data),Nd4j.create(result));
            bq.add(ds);
            inds.add(new Pair<>(Nd4j.create(data),Nd4j.create(result)));
        }

        DataSetIterator dt = new INDArrayDataSetIterator(inds,40);
        MultiLayerConfiguration conf =
                new NeuralNetConfiguration.Builder()
                        .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                        .updater(new Nesterovs(0.85 /*Learning rate*/, 0.9))
                        .list(
                                new DenseLayer.Builder().nIn(4).nOut(8).activation(Activation.RELU).build(),
                                new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD).activation(Activation.SOFTMAX).nIn(8).nOut(4).build()
                        ).backpropType(BackpropType.Standard).build();
        MultiLayerNetwork network = new MultiLayerNetwork(conf);
        network.init();
        EarlyStoppingConfiguration esConf = new EarlyStoppingConfiguration.Builder()
                .epochTerminationConditions(new MaxEpochsTerminationCondition(5))
                .iterationTerminationConditions(new MaxTimeIterationTerminationCondition(20, TimeUnit.MINUTES))
                .scoreCalculator(new DataSetLossCalculator(dt, true))
                .evaluateEveryNEpochs(1)
                .modelSaver(new LocalFileModelSaver(System.getProperty("user.dir")))
                .build();
        EarlyStoppingTrainer trainer = new EarlyStoppingTrainer(esConf, network, dt);
        EarlyStoppingResult result = trainer.fit();
        System.out.println("Termination reason: " + result.getTerminationReason());
        System.out.println("Termination details: " + result.getTerminationDetails());
        System.out.println("Total epochs: " + result.getTotalEpochs());
        System.out.println("Best epoch number: " + result.getBestModelEpoch());
        System.out.println("Score at best epoch: " + result.getBestModelScore());
    }

    public static void main(String[] args) {
        init();
    }
}
