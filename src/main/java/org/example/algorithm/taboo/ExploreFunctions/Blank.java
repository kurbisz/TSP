package org.example.algorithm.taboo.ExploreFunctions;

import org.example.data.Result;

public class Blank implements ExploreFunction {
    @Override
    public boolean shouldExplore(Result current) {
        return false;
    }

    @Override
    public void explore(Result start) {

    }

    @Override
    public ExploreFunction copy() {
        return null;
    }
}
