package org.example.algorithm.taboo.ExploreFunctions;

import org.example.data.Result;

public interface ExploreFunction {
    boolean shouldExplore(Result current);
    void explore(Result start);
    ExploreFunction copy();
}
