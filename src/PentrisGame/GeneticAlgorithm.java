package PentrisGame;

import java.util.Arrays;

public class GeneticAlgorithm {
    public final double mutationRate = 0.3;
    public final int initialPopulationSize = 100;
    public Bot[] population = new Bot[initialPopulationSize];

    /**
     * Do elitist selection to keep only the most fit individuals
     */
    public void selection() {
        HeapSort.sort(population);
        Bot[] tempPopulation = new Bot[initialPopulationSize];
        for (int i=0; i<tempPopulation.length; i++) {
            tempPopulation[i] = population[i];
        }
        population = tempPopulation;
    }

    /**
     * Reproduce individuals using tournament selection.
     * Selects a random batch od 10% of the population and picks the top 2 in that batch.
     * They then reproduce.
     */
    public void reproduce() {
        Bot[] batch = new Bot[(int) (initialPopulationSize*0.4)];
        if (batch.length < 2) {
            batch = new Bot[2];
        }
        for (int i=0; i<batch.length; i++) {
            batch[i] = population[(int) (Math.random()*population.length)];
        }
        HeapSort.sort(batch);
        crossover(batch[0], batch[1]);
    }

    /**
     * Crossover function for 2 bots.
     * Creates 2 children using weighted average crossover.
     * Adds the child into the populaion.
     * @param bot1 The first parent bot
     * @param bot2 The second parent bot
     */
    public void crossover(Bot bot1, Bot bot2) {
        double fitness1 = bot1.getFitness();
        double fitness2 = bot2.getFitness();

        double average1 = fitness1/(fitness1 + fitness2);
        double average2 = fitness2/(fitness1 + fitness2);

        Bot child1 = new Bot();
        Bot child2 = new Bot();
        double[] geno1 = new double[bot1.getGenotype().length];
        double[] geno2 = new double[bot1.getGenotype().length];
        for (int i=0; i<geno1.length; i++) {
            geno1[i] = bot1.getGenotype()[i]*average1 + bot2.getGenotype()[i]*average2;
        }
        for (int i=0; i<geno2.length; i++) {
            geno2[i] = bot1.getGenotype()[i]*average1 + bot2.getGenotype()[i]*average2;
        }
        child1.setGenotype(geno1);
        child1.mutate(mutationRate);
        child2.setGenotype(geno1);
        child2.mutate(mutationRate);

        Bot[] tempPopulation = new Bot[population.length+2];
        for (int i=0; i<population.length; i++) {
            tempPopulation[i] = population[i];
        }
        tempPopulation[tempPopulation.length-2] = child1;
        tempPopulation[tempPopulation.length-1] = child2;
        population = tempPopulation;
    }

    public double[] bestSolution;

    /**
     * Class to evaluate the bots in Threads
     */
    class BotEvaluator implements Runnable {
        Bot bot;
        boolean done;
        /**
         * Constructor.
         * @param bot The bot to evaluate
         */
        BotEvaluator(Bot bot) {
            this.bot = bot;
            done = false;
        }
        /**
         * Runs the fitness evaluation for this bot
         */
        public void run() {
            bot.calculateFitness(100);
            done = true;
        }
        /**
         * Checks if the evaluation is done
         * @return {@code true} if the evaluation is done and {@code false} otherwise.
         */
        boolean isDone() {
            return done;
        }
    }

    public void runBatch(int batchSize) {
        // Evaluate bot fitnesses in batches
        for (int i=0; i<population.length; i+=batchSize) {
            BotEvaluator[] evaluators = new BotEvaluator[batchSize];
            int pos = 0;
            for (int j=i; j<Math.min(i+batchSize, population.length); j++) {
                BotEvaluator be = new BotEvaluator(population[j]);
                evaluators[pos] = be;
                pos++;
                Thread t = new Thread(be);
                t.start();
            }
            // Stay in while loop while evaluating
            boolean running = true;
            while (running) {
                running = false;
                for (int j=0; j<evaluators.length; j++) {
                    if (evaluators[j] != null && !evaluators[j].isDone()) {
                        running = true;
                    }
                }
            }
        }
    }

    /**
     * Run the genetic algorithm
     */
    public void run() {
        long c = System.currentTimeMillis();
        // Initialize it
        population = new Bot[initialPopulationSize];
        System.out.println("Initializing...");
        for (int i=0; i<initialPopulationSize; i++) {
            population[i] = new Bot();
        }
        runBatch(10);
        // Run the algorithm 10 generations
        for (int generation=0; generation<100; generation++) {
            System.out.println("Generation #"+(generation+1));
            while (population.length < initialPopulationSize*1.5) {
                reproduce();
            }
            long c1 = System.currentTimeMillis();
            runBatch(10);
            selection();
            System.out.println("Best individual: "+Arrays.toString(population[0].getGenotype())+"; --Fitnes: "+population[0].getFitness());
            long timeRun = (System.currentTimeMillis()-c1)/1000;
            System.out.println("Generation runtime: "+timeRun + " s");
            System.out.println("Estimated time remaining: " + timeRun*(99-generation)/60 + " min");
            System.out.println();
        }
        // Print out the best bots weights
        HeapSort.sort(population);
        Bot best = population[0];
        System.out.println(Arrays.toString(best.getGenotype()));
        bestSolution = best.getGenotype();
        System.out.println("Runtime: "+((System.currentTimeMillis() - c)/60000) + " min");
    }
}
