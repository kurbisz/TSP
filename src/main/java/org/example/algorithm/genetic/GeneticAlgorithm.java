package org.example.algorithm.genetic;

import org.example.algorithm.Algorithm;
import org.example.data.Result;
import org.example.data.TspData;

public class GeneticAlgorithm extends Algorithm {

	public GeneticAlgorithm(TspData tspData) {
		super(tspData);
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

}

/**
 * Schemat tworzenia populacji startowej:
 *  - klasa, która zwraca tablicę/listę zawierającą początkową populację
 *  - może wykorzystajmy już istniejące sąsiedztwa + ewentualnie coś nowego
 *
 * Schemat ewaluacji:
 *  -
 */