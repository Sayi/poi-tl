/*
 * Copyright 2014-2021 Sayi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.deepoove.poi.data;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Define the matrix to be merged and the matrix cannot overlap.
 * <p>
 * TODO add boolean value for if merge cells and merge contents at the same time
 * </p>
 * 
 * @author Sayi
 *
 */
public class MergeCellRule implements Serializable {

    private static final long serialVersionUID = 1L;

    private Map<Grid, Grid> mapping = new LinkedHashMap<>();

    private MergeCellRule() {
    }

    /**
     * create builder for {@link MergeCellRule}
     * 
     * @return
     */
    public static MergeCellRuleBuilder builder() {
        return new MergeCellRuleBuilder();
    }

    /**
     * iterator the merged matrix
     * 
     * @return
     */
    public Iterator<Entry<Grid, Grid>> mappingIterator() {
        return mapping.entrySet().iterator();
    }

    public Map<Grid, Grid> getMapping() {
        return mapping;
    }

    @Override
    public String toString() {
        return mapping.toString();
    }

    public static class Grid implements Serializable {
        private static final long serialVersionUID = 1L;
        private int i;
        private int j;

        private Grid() {
        }

        public static Grid of(int i, int j) {
            Grid grid = new Grid();
            grid.i = i;
            grid.j = j;
            return grid;
        }

        public boolean inside(Grid key, Grid value) {
            if (isBetween(i, key.i, value.i) && isBetween(j, key.j, value.j)) {
                return true;
            }
            return false;
        }

        private boolean isBetween(int row, int i, int j) {
            if (row == i) return true;
            if (row == j) return true;
            if (row > i && row < j) return true;
            if (row > j && row < i) return true;
            return false;
        }

        @Override
        public String toString() {
            return i + "-" + j;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + i;
            result = prime * result + j;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (getClass() != obj.getClass()) return false;
            Grid other = (Grid) obj;
            if (i != other.i) return false;
            if (j != other.j) return false;
            return true;
        }

        public int getI() {
            return i;
        }

        public void setI(int i) {
            this.i = i;
        }

        public int getJ() {
            return j;
        }

        public void setJ(int j) {
            this.j = j;
        }

    }

    public static final class MergeCellRuleBuilder {
        private Map<Grid, Grid> map = new LinkedHashMap<>();

        private MergeCellRuleBuilder() {
        }

        public MergeCellRuleBuilder map(Grid from, Grid to) {
            // validate
            if (from.equals(to)) {
                throw new IllegalArgumentException("The merged grid of from and to cannot be same!");
            }
            Iterator<Entry<Grid, Grid>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Entry<Grid, Grid> next = iterator.next();
                Grid key = next.getKey();
                Grid value = next.getValue();
                if (from.inside(key, value) || to.inside(key, value)) {
                    throw new IllegalArgumentException("The grid to be merged overlap! from " + from + " to " + to
                            + " conflict with " + key + " to " + value);
                }
            }

            map.put(from, to);
            return this;
        }

        public MergeCellRule build() {
            MergeCellRule mergeCellRule = new MergeCellRule();
            mergeCellRule.mapping = map;
            return mergeCellRule;
        }

    }
}
