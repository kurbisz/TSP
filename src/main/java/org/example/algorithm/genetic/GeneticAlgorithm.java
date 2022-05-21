package org.example.algorithm.genetic;

import org.example.algorithm.Algorithm;
import org.example.algorithm.genetic.Evaluations.Evaluator;
import org.example.algorithm.genetic.crossovers.Crossover;
import org.example.algorithm.genetic.mutations.Mutation;
import org.example.algorithm.genetic.selections.Selection;
import org.example.algorithm.genetic.startPopulations.PopulationGenerator;
import org.example.data.Result;
import org.example.data.TspData;

public class GeneticAlgorithm extends Algorithm {

	PopulationGenerator populationGeneratorTemplate;
	Selection selectionTemplate = null;
	Mutation mutationTemplate = null;
	Crossover crossoverTemplate = null;
	Evaluator evaluatorTemplate = null;

	public GeneticAlgorithm(TspData tspData) {
		super(tspData);
	}

	public void setStartPopulation(PopulationGenerator populationGenerator){
		this.populationGeneratorTemplate = populationGenerator;
	}

	public void setSelection(Selection selection){
		this.selectionTemplate = selection;
	}

	public void setMutationTemplate(Mutation mutationTemplate){
		this.mutationTemplate = mutationTemplate;
	}

	public void setCrossoverTemplate(Crossover crossoverTemplate){
		this.crossoverTemplate = crossoverTemplate;
	}

	public void setEvaluatorTemplate(Evaluator evaluatorTemplate){
		this.evaluatorTemplate = evaluatorTemplate;
	}

	@Override
	public Result calculate() {
		//TODO utworzenie i wywołanie wątku (wątków) realizującego algorytm genetyczny
		//parametry:
		/**
		 * schemat tworzenia populacji startowej
		 * schemat selekcji
		 * schemat krzyżowania
		 * schemat mutacji
		 * liczba wątków
		 */


		return null;
	}

	class SingleGeneticThread implements Runnable {
		//todo pojedynczy przebieg algorytmu genetycznego
		//dla zadanych parametrów

		PopulationGenerator populationGenerator;
		Selection selection;
		Mutation mutation;
		Crossover crossover;
		Evaluator evaluator;

		SingleGeneticThread(){
			populationGenerator = populationGeneratorTemplate.copy();
			selection = selectionTemplate.copy();
			mutation = mutationTemplate.copy();
			crossover = crossoverTemplate.copy();
			evaluator = evaluatorTemplate.copy();
		}

		@Override
		public void run() {
		}
	}

}

/**
 * Schemat tworzenia populacji startowej:
 *  - klasa, która zwraca tablicę/listę zawierającą początkową populację
 *  - może wykorzystajmy już istniejące sąsiedztwa + ewentualnie coś nowego
 *
 * Schemat ewaluacji:
 *  -
 */