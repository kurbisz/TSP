package org.example.data;

public class TspData {

	protected String name;
	protected boolean symmetric;
	protected int n;
	protected int distance[][];

	public TspData(int n, int dist[][], boolean symmetric) {
		this.n = n;
		this.distance = dist;
		this.symmetric = symmetric;
		// Set all distances point to same point to -1
		for(int i = 0; i < n; i++) {
			dist[i][i] = -1;
		}
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public int[][] getDistance() {
		return distance;
	}

	public int getSize() {
		return n;
	}

	/**
	 * Gives distance between two points.
	 * @param from index of 'from point'
	 * @param to index of 'to point'
	 * @return distance if points are valid; -1 when from == to; -2 otherwise
	 */
	public int getDistance(int from, int to) {
		if(from < 0 || from >= n || to < 0 || to >= n) return -2;
		return distance[from][to];
	}

	public boolean isSymmetric() {
		return symmetric;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Type: Normal");
		builder.append(n);
		builder.append('\n');
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				builder.append(distance[i][j]);
				if(distance[i][j] < 1000) builder.append('\t');
				builder.append('\t');
			}
			builder.append('\n');
		}
		return builder.toString();
	}

}
